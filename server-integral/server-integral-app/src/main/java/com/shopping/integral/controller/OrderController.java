package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.core.WxPay;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralOrder;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.queryform.IntegralOrderAddForm;
import com.shopping.integral.queryform.IntegralOrderQueryForm;
import com.shopping.integral.service.IIntegralOrderService;
import com.shopping.integral.token.authorization.annotation.CacheUser;
import com.shopping.integral.token.authorization.annotation.CurrentAppKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：订单操作
 *
 * @author：GuoFuJun
 * @date：2018年7月10日 上午15:34:46
 */
@Log4j2
@Api(value = "订单操作", tags = {"order"})
@RestController
@RequestMapping("/app/order")
public class OrderController {

    @Autowired
    IndexController indexController;

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    WxPay wxPay;

    @ApiOperation(value = "创建兑换订单", tags = {"order"}, notes = "")
    @PostMapping(value = "/set_order")
    public ActionResult<BaseResult> setOrder(HttpServletRequest request,@CurrentAppKey String appKey, @CacheUser IntegralUser integralUser, IntegralOrderAddForm integralOrderAddForm) {
        try {
            if (Utils.isNotEmpty(integralOrderAddForm) && Utils.isNotEmpty(appKey) && Utils.isNotEmpty(integralUser)) {
                IntegralAcApply acApply = indexController.integralAcApply(appKey);
                BaseResult baseResult = integralOrderService.setIntegralOrder(acApply.getId(), integralUser.getId(), integralOrderAddForm);
                if(baseResult.getSuccess() && baseResult.getMessage().indexOf("现金待付款")!=-1){
                    String oid = baseResult.getData().toString();
                    IntegralOrder order = this.integralOrderService.getOneObjByProperty("oid",oid);
                    if(order.getChannel().equals("WXAPP")){
                        //返回微信预付款订单
                        Map param = new HashMap();
                        param.put("body",order.getOrderGoods().getOrderGoodsName());
                        //将金额乘以100。变成单位分
                        BigDecimal num  = order.getOrderTotalPrice();
                        param.put("total",num.toString());
                        param.put("openid",integralUser.getOpenid());
                        param.put("oid", oid);
                        param.put("ip", CommUtils.getIpAddrV2(request));
                        if(!"".equals(acApply.getWxappAppid())){
                            param.put("appId", acApply.getWxappAppid());
                            param.put("payId", acApply.getWxPayUid());
                            param.put("SecretKey", acApply.getWxPaySecretKey());
                        }
                        // 保存,用于回调
                        param.put("attach",appKey);
                        Map reParam = new HashMap();
                        reParam = wxPay.chargeWxPay(param,"integral");
                        reParam.put("oid",oid);
                        reParam.put("signType","MD5");
                        baseResult = BaseResult.SUCCESS("积分支付成功,现金待付款(微信小程序)", reParam);
                        return ActionResult.ok(baseResult);
                    }
                }

                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("创建兑换订单接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取订单详情", tags = {"order"}, notes = "")
    @GetMapping(value = "/get_order_details/{orderId}")
    public ActionResult<BaseResult> getOrderDetails(@CacheUser IntegralUser integralUser, @PathVariable("orderId") String orderId) {
        try {
            if (Utils.isNotEmpty(orderId) && Utils.isNotEmpty(integralUser)) {
                BaseResult baseResult = integralOrderService.getIntegralOrderDetails(integralUser.getId(),orderId,null);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取订单详情接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "通过核销码获取订单详情", tags = {"order"}, notes = "")
    @GetMapping(value = "/get_order_details_by_VerifyCode/{acApplyId}/{verifyCode}")
    public ActionResult<BaseResult> getOrderDetailsByVerifyCode(@CacheUser IntegralUser integralUser,@PathVariable("acApplyId") String acApplyId,@PathVariable("verifyCode") String verifyCode) {
        try {
            if (Utils.isNotEmpty(verifyCode)) {
                BaseResult baseResult = integralOrderService.getIntegralOrderDetailsByVerifyCode(verifyCode, CommUtils.null2Long(acApplyId));
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取订单详情接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取订单列表详情", tags = {"order"}, notes = "")
    @GetMapping(value = "/get_order_list")
    public ActionResult<BaseResult> getOrderList(@CacheUser IntegralUser integralUser,String currentPage,String pageSize) {
        try {
            if (Utils.isNotEmpty(integralUser)) {
                IntegralOrderQueryForm integralOrderQueryForm = new IntegralOrderQueryForm();
                if(CommUtils.isNotNull(currentPage) && CommUtils.isNotNull(pageSize)){
                    integralOrderQueryForm.setCurrentPage(CommUtils.null2Int(currentPage));
                    integralOrderQueryForm.setPageSize(CommUtils.null2Int(pageSize));
                }
                integralOrderQueryForm.setOrderUserId(integralUser.getId());
                integralOrderQueryForm.setOrderBy("addTime desc");
                BaseResult baseResult = integralOrderService.getIntegralOrderList(integralOrderQueryForm);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取订单详情接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "取消订单", tags = {"order"}, notes = "")
    @PostMapping(value = "/set_order_cancel/{orderId}")
    public ActionResult<BaseResult> setOrderCancel(@CacheUser IntegralUser integralUser, @PathVariable("orderId") String orderId) {
        try {
            if (Utils.isNotEmpty(integralUser)) {
                BaseResult baseResult = integralOrderService.cancelOrder(orderId,"支付取消",true);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取订单详情接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "核销订单", tags = {"order"}, notes = "")
    @PostMapping(value = "/verify_order/{orderId}")
    public ActionResult<BaseResult> verifyOrder(@CacheUser IntegralUser integralUser, @PathVariable("orderId") String orderId) {
        try {
            BaseResult baseResult = integralOrderService.verifyOrder(orderId);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("获取订单详情接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }
}
