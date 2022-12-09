package com.shopping.integral.controller;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.conf.wxPayConfig;
import com.shopping.framework.util.PayUtil;
import com.shopping.framework.util.wxPayUtil;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.queryform.PayChargeForm;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralOrderService;
import com.shopping.integral.service.IPayService;
import com.shopping.integral.token.authorization.annotation.CacheUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：支付操作
 *
 * @author：GuoFuJun
 * @date：2018年7月10日 上午15:34:46
 */
@Log4j2
@Api(value = "支付操作", tags = {"pay"})
@RestController
@RequestMapping("/app/pay")
public class PayController {

    private static final String CHARGE_SUCCEEDED = "charge.succeeded";

    @Autowired
    IPayService payService;

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Autowired
    wxPayConfig wxPayConfig;

    Object lock = new Object();

    @ApiOperation(value = "创建支付请求", tags = {"pay"}, notes = "")
    @PostMapping(value = "/post_create_pay")
    public ActionResult<BaseResult> postCreatePay(@CacheUser IntegralUser integralUser, PayChargeForm payChargeForm) {
        try {
            integralOrderService.setOrderPayLock(payChargeForm.getOrderId(),true);

            File targetFile = new File("files/rsa_private_key.pem");
            if(!targetFile.exists()) {
                InputStream stream = getClass().getClassLoader().getResourceAsStream("rsa_private_key.pem");
                FileUtils.copyInputStreamToFile(stream, targetFile);
            }
            payChargeForm.setRsaPrivatePath(targetFile.getPath());

            BaseResult baseResult = payService.createPay(integralUser, payChargeForm);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("创建支付请求异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "支付成功回调请求", tags = {"pay"}, notes = "")
    @RequestMapping({"/paySuccessHooks.htm"})
    public void paySuccessHooks(HttpServletRequest request, HttpServletResponse response) {
        try {
            synchronized (lock) {
                Map param = new HashMap();
                request.setCharacterEncoding("UTF8");
                // 获取原始数据
                String dataString = PayUtil.getDataString(request);
                log.info("支付成功回调请求数据:{}" , dataString);
                /************************* 签名验证 ****************************/
                boolean signatureVerify = PayUtil.signatureVerify(request, dataString);
                /************************* 签名验证 ****************************/
                //解析异步通知数据
                Event event = Webhooks.eventParse(dataString);
                log.info("event Data : {}" , event!=null ? event.getData().getObject() : "");
                if(event != null &&  event.getData() != null && event.getData().getObject() != null) {
                    JSONObject obj = JSONObject.parseObject(event.getData().getObject().toString());
                    // 支付订单号
                    String oid = CommUtils.null2String(obj.get("order_no"));
                    // 签名验证没过
                    if (!signatureVerify) {
                        /***************************订单日志**************************/
                        log.error("ping++签名验证没过,订单号: " + oid);
                        /***************************订单日志**************************/
                    }
                    if (!CommUtils.null2Boolean(obj.get("paid"))) {
                        /***************************订单日志**************************/
                        log.error("ping++内容有误，paid为true (订单已支付),订单号: " + oid);
                        /***************************订单日志**************************/
                    }
                    // 支付金额（ping++ 金额已分为单位）转换金额为正常金额
                    BigDecimal amount = new BigDecimal(obj.get("amount").toString()).divide(new BigDecimal(100)).setScale(2, 4);
                    if (CHARGE_SUCCEEDED.equals(event.getType())) {
                        param.put("id", obj.get("id"));
                        param.put("oid", oid);
                        param.put("amount", amount);

                        BaseResult baseResult = payService.paySuccessHooks(param);
                        if (Utils.isNotEmpty(baseResult) && baseResult.getSuccess()) {
                            response.setStatus(200);
                        } else {
                            response.setStatus(500);
                        }
                    }
                } else {
                    response.setStatus(200);
                }
            }
        } catch (Exception e) {
            log.error("支付成功回调请求异常！", e);
            response.setStatus(500);
        }
    }

    @PostMapping({"/WXpaySuccessHooks.htm"})
    public void wxpaySuccessHooks(HttpServletRequest request, HttpServletResponse response) {
        try {
            synchronized (lock) {
                Map param = new HashMap();
                System.out.println("进入支付回调");
                BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                br.close();
                //sb为微信返回的xml
                String notityXml = sb.toString();
                String resXml = "";
                System.out.println("接收到的报文：" + notityXml);

                Map map = wxPayUtil.doXMLParse(notityXml);
                String returnCode = (String) map.get("return_code");
                if(returnCode != null && "SUCCESS".equals(returnCode)) {
                    //验证签名是否正确
                    //回调验签时需要去除sign和空值参数
                    Map<String, String> validParams = wxPayUtil.paraFilter(map);
                    //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
                    String validStr = wxPayUtil.createLinkString(validParams);
                    // 解密所需的Key
                    String KEY = wxPayConfig.getKEY();
                    if (Utils.isNotEmpty(map.get("attach"))) {
                        IntegralAcApply acApply = integralAcApplyService.getOneObjByProperty("appKey", map.get("attach"));
                        if (acApply != null) {
                            KEY = acApply.getWxPaySecretKey();
                        }
                    }
                    //拼装生成服务器端验证的签名
                    String sign = wxPayUtil.sign(validStr, KEY, "utf-8").toUpperCase();
                    //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
                    if(sign.equals(map.get("sign"))){
                        BigDecimal amount = new BigDecimal(map.get("total_fee").toString()).divide(new BigDecimal(100)).setScale(2, 4);
                        param.put("oid",map.get("out_trade_no"));
                        param.put("amount",amount);
                        param.put("id",map.get("transaction_id"));

                        BaseResult baseResult = payService.paySuccessHooks(param);
                        if (Utils.isNotEmpty(baseResult) && baseResult.getSuccess()) {
                            //通知微信服务器已经支付成功
                            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                            response.setStatus(200);
                        } else {
                            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                            response.setStatus(500);
                        }
                    }else{
                        log.info("支付签名验证失败,attach:{},k:{}",map.get("attach"),KEY);
                    }
                }

                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            log.error("支付成功回调请求异常！", e);
            response.setStatus(500);
        }
    }
}
