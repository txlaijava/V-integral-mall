package com.shopping.integral.controller;

import com.alibaba.fastjson.JSONArray;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.util.DateUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.oss.OssUploadUtils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.OrderCons;
import com.shopping.integral.dao.IntegralOrderDAO;
import com.shopping.integral.dao.model.IntegralAccessory;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.service.*;
import com.shopping.integral.token.authorization.annotation.CacheIntegralAccount;
import com.shopping.integral.util.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：公共接口
 *
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Log4j2
@Api(value = "公交操作", tags = {"common"})
@RestController
@RequestMapping("/common")
public class BaseController {

    @Autowired
    RedisService redisService;

    @Autowired
    ISmsService smsService;

    @Autowired
    IMailService mailService;

    @Autowired
    IIntegralAccountService integralAccountService;

    @Autowired
    IIntegralAccessoryService iIntegralAccessoryService;

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    IntegralOrderDAO integralOrderDAO;


    @ApiOperation(value = "验证手机号码是否已注册", tags = {"common"}, notes = "")
    @GetMapping(value = "/getValiAc")
    public ActionResult<BaseResult> getValiAc(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mobileNo = request.getParameter("mobileNo");
            String type = request.getParameter("valiType");
            BaseResult baseResult = integralAccountService.getValiAc(type,mobileNo);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("发送短信异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "发送短信", tags = {"common"}, notes = "")
    @GetMapping(value = "/getSMSCode")
    public ActionResult<BaseResult> getSMSCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mobileNo = request.getParameter("mobileNo");
            BaseResult baseResult = smsService.setSendVerCodeSms(mobileNo, "sms_tobuyer_user_register_notify");
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("发送短信异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "验证短信验证码", tags = {"common"}, notes = "")
    @GetMapping(value = "/getValidCode")
    public ActionResult<BaseResult> getValidCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mobileNo = request.getParameter("mobileNo");
            String codeNo = request.getParameter("codeNo");
            BaseResult baseResult = smsService.getValiCode(mobileNo, codeNo);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("验证短信验证码异常！", e);
        }
        return ActionResult.error("服务器异常");
    }


    @ApiOperation(value = "修改账号密码", tags = {"common"}, notes = "")
    @PostMapping(value = "/setAcPwd")
    public ActionResult<BaseResult> setAcPwd(HttpServletRequest request) {
        try {
            String mobileNo = request.getParameter("mobileNo");
            String pwd = request.getParameter("pwd");
            String type = request.getParameter("valiType");

            BaseResult baseResult = integralAccountService.setAcPwd(type, mobileNo, pwd);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("修改账号密码异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "修改子账号密码", tags = {"common"}, notes = "")
    @PostMapping(value = "/setAcChildPwd")
    public ActionResult<BaseResult> setAcChildPwd(HttpServletRequest request) {
        try {
            String acMail = request.getParameter("acMail");
            String pwd = request.getParameter("pwd");
            String type = request.getParameter("valiType");

            BaseResult baseResult = integralAccountService.setAcPwd(type,acMail,pwd);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("修改子账号密码异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "修改账户信息", tags = {"common"}, notes = "")
    @PutMapping(value = "/upAccountInfo/{acId}")
    public ActionResult<BaseResult> upAccountInfo(HttpServletRequest request,@PathVariable Long acId) {
        if (Utils.isNotEmpty(acId)) {

            Map<String,String> params = ParamUtils.getparams(request);
            BaseResult baseResult = integralAccountService.upAccountInfo(acId,params,true);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "发送邮件", tags = {"common"}, notes = "")
    @GetMapping(value = "/sendMail")
    public ActionResult<BaseResult> upAccountInfo(@CacheIntegralAccount IntegralAccount integralAccount) {
        try{
            if(Utils.isNotEmpty(integralAccount)){
                BaseResult baseResult = mailService.sendAppSecretMail(integralAccount.getAcMail(),"AppSecret");
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("发送邮箱接口异常！",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "上传图片", tags = {"common"}, notes = "")
    @PostMapping(value = "/uploadImage")
    public ActionResult<Map> uploadImage(HttpServletRequest request, @RequestParam MultipartFile file, String saveFilePathName, String WHList) {
        try{
            Map map = new HashMap();
            List<Map> list = JSONArray.parseArray(WHList,Map.class);
            map = OssUploadUtils.uploadImageByWH(saveFilePathName,file,list);
            if(CommUtils.null2Int(map.get("status"))!=1002){
                IntegralAccessory accessory = new IntegralAccessory();
                accessory.setAddTime(new Date());
                accessory.setDeleteStatus(false);
                accessory.setExt(map.get("mime").toString());
                accessory.setHeight(CommUtils.null2Int(map.get("height")));
                accessory.setWidth(CommUtils.null2Int(map.get("width")));
                accessory.setName(map.get("fileName").toString());
                accessory.setPath(map.get("savePath").toString());
                accessory.setSize(CommUtils.null2Float(map.get("fileSize")));
                this.iIntegralAccessoryService.save(accessory);
                map.put("id",accessory.getId());
                map.put("path",accessory.getPath());
                map.put("name",accessory.getName());
            }

            return ActionResult.ok(map);
        }catch (Exception e){
            log.error("上传图片异常",e);
        }
        return ActionResult.error("服务器异常");
    }

    @PostMapping(value = "/testCancel")
    public ActionResult testCancel() {
        try{
            Map params = new HashMap<>(2);
            params.put("order_state", OrderCons.WAIT_PAY);
            params.put("pay_lock", false);
            StringBuffer sql = new StringBuffer("SELECT a.oid,a.pay_time FROM integral_order a ");
            sql.append(" WHERE a.biz_id IS NOT NULL AND a.charge_id IS NULL");
            sql.append(" AND a.order_state = :order_state AND (a.pay_lock = :pay_lock or a.pay_lock is null)");
            /*sql.append("AND date_format(a.pay_time,'%Y-%m-%d %H:%i:%s') <= date_format(:curr_time,'%Y-%m-%d %H:%i:%s')");*/
            List<Map<String,Object>> orderList = integralOrderDAO.findBySqlPage(sql.toString(), -1, -1, params);
            if(orderList != null){
                log.info("支付超时数据:" + orderList.size());
            }
            orderList.forEach(item ->{
                Date payTime = CommUtils.formatDate(item.get("pay_time").toString(),"yyyy-MM-dd HH:mm:ss");
                Date curreTime = DateUtils.addMinutes(payTime, 30);
                log.info("支付时间:{},当前时间:{}",curreTime.getTime(),System.currentTimeMillis());
                if (curreTime.getTime() <= System.currentTimeMillis()) {
                    log.info("订单号:{}",item.get("oid"));
                    integralOrderService.cancelOrder(item.get("oid").toString(),"支付超时，自动取消订单",true);
                }
            });

        }catch (Exception e){
            log.error("上传图片异常",e);
        }
        return ActionResult.error("服务器异常");
    }
}
