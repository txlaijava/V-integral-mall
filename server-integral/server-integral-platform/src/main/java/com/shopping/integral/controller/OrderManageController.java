package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.OrderCons;
import com.shopping.integral.dao.IntegralAccountDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.dao.model.IntegralOrder;
import com.shopping.integral.queryform.IntegralOrderQueryForm;
import com.shopping.integral.service.IIntegralOrderService;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.model.CheckResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：后台订单管理操作
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Log4j2
@Api(value = "后台订单操作", tags = {"orderManage"})
@RestController
@RequestMapping("/orderManage")
public class OrderManageController {

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    IntegralAccountDAO integralAccountDAO;

    @ApiOperation(value = "获取订单分页列表", tags = {"orderManage"}, notes = "")
    @RequestMapping("/get_order_list_page")
    public ActionResult<BaseResult> orderListPage(@CurrentIntegralAcApply IntegralAcApply integralAcApply, IntegralOrderQueryForm queryForm, String orderType, String orderId, String timeType, String beginTime, String endTime, String minAmount, String maxAmount) {
        try {
            IntegralOrderQueryForm pageForm = queryForm;
            if (CommUtils.isNotNull(orderType)) {
                //开发者平台订单号
                if (orderType.equals("0")) {
                    pageForm.setBizId(orderId);
                } else if(orderType.equals("1")){
                    //平台订单号
                    pageForm.setOid(orderId);
                } else{
                    //开发者备注
                    pageForm.setIntegralUserRemark(orderId);
                }
            }
            pageForm.setOrderBy(" addTime desc ");
            pageForm.setAcApplyId(integralAcApply.getId());
            if (CommUtils.isNotNull(timeType)) {
                //下单时间
                if (timeType.equals("0")) {
                    pageForm.setBeginAddTime(CommUtils.formatDate(beginTime, "yyyy-MM-dd"));
                    pageForm.setEndAddTime(CommUtils.formatDate(endTime, "yyyy-MM-dd"));
                } else {                          //完成时间
                    pageForm.setBeginCompleteTime(CommUtils.formatDate(beginTime, "yyyy-MM-dd"));
                    pageForm.setEndCompleteTime(CommUtils.formatDate(endTime, "yyyy-MM-dd"));
                }
            }
            //积分最小值判断
            if (CommUtils.isNotNull(minAmount)) {
                pageForm.setMinPrice(CommUtils.null2BigDecimal(minAmount));
            }
            //积分最大值判断
            if (CommUtils.isNotNull(maxAmount)) {
                pageForm.setMinPrice(CommUtils.null2BigDecimal(maxAmount));
            }

            BaseResult baseResult = this.integralOrderService.getIntegralOrderList(queryForm);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("获取订单分页列表异常", e);
        }
        return null;
    }

    @ApiOperation(value = "订单列表导出")
    @GetMapping(value = "/orderListExport")
    public void bargainGoodsListExport(@CurrentIntegralAcApply IntegralAcApply integralAcApply, IntegralOrderQueryForm queryForm, String orderType, String orderId, String timeType, String beginTime, String endTime, String minAmount, String maxAmount,String Token) {
        try {
            if (StringUtils.isNotBlank(Token)) {
                CheckResult checkResult = JwtTokenUtils.validateJWT(Token);
                if (checkResult.isSuccess()) {
                    String key = checkResult.getClaims().getId();
                    IntegralAccount account = integralAccountDAO.findById(CommUtils.null2Long(key));
                    integralAcApply = account.getAcApply();
                }
            }
            IntegralOrderQueryForm pageForm = queryForm;
            if (CommUtils.isNotNull(orderType)) {
                //开发者平台订单号
                if (orderType.equals("0")) {
                    pageForm.setBizId(orderId);
                } else {                          //订单号
                    pageForm.setOid(orderId);
                }
            }
            pageForm.setOrderBy(" addTime desc ");
            pageForm.setAcApplyId(integralAcApply.getId());
            if (CommUtils.isNotNull(timeType)) {
                //下单时间
                if (timeType.equals("0")) {
                    pageForm.setBeginAddTime(CommUtils.formatDate(beginTime, "yyyy-MM-dd"));
                    pageForm.setEndAddTime(CommUtils.formatDate(endTime, "yyyy-MM-dd"));
                } else {                          //完成时间
                    pageForm.setBeginCompleteTime(CommUtils.formatDate(beginTime, "yyyy-MM-dd"));
                    pageForm.setEndCompleteTime(CommUtils.formatDate(endTime, "yyyy-MM-dd"));
                }
            }
            //积分最小值判断
            if (CommUtils.isNotNull(minAmount)) {
                pageForm.setMinPrice(CommUtils.null2BigDecimal(minAmount));
            }
            //积分最大值判断
            if (CommUtils.isNotNull(maxAmount)) {
                pageForm.setMinPrice(CommUtils.null2BigDecimal(maxAmount));
            }

            queryForm.setCurrentPage(1);
            queryForm.setPageSize(-1);

            this.integralOrderService.ExOrderList(queryForm);
        } catch (Exception e) {
            log.error("获取订单分页列表异常", e);
        }
    }

    @ApiOperation(value = "修改订单物流信息", tags = {"orderManage"}, notes = "")
    @PostMapping(value = "/update_logistics")
    public ActionResult<Map> updateLogistics(@CurrentIntegralAcApply IntegralAcApply integralAcApply, String orderId, String logisticsName, String logisticsCode) {
        try {
            Map map = new HashMap();
            IntegralOrder order = this.integralOrderService.getOneObjByProperty("oid", orderId);
            if (order.getAcApply().getId().equals(integralAcApply.getId())) {
                if (order.getOrderStateType() == 1) {
                    order.setLogisticsName(logisticsName);
                    order.setLogisticsCode(logisticsCode);
                }
                order.setOrderState(OrderCons.ALREADY_SEND);
                this.integralOrderService.update(order);
                map.put("status", 1000);
                map.put("message", "修改成功");
            } else {
                map.put("status", 1000);
                map.put("message", "不可操作其他应用订单");
            }
            return ActionResult.ok(map);
        } catch (Exception e) {
            log.error("修改订单物流信息异常", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "取消订单", tags = {"orderManage"}, notes = "")
    @PostMapping(value = "/cancel_order")
    public ActionResult<BaseResult> cancelOrder(String orderId, String reason) {
        try {
            BaseResult baseResult = integralOrderService.cancelOrder(orderId, reason, false);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("取消订单", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "审核订单", tags = {"orderManage"}, notes = "")
    @PostMapping(value = "/audit_order")
    public ActionResult<BaseResult> auditOrder(String orderId, String status) {
        try {
            BaseResult baseResult = integralOrderService.auditOrder(orderId, status);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("审核订单异常", e);
        }
        return ActionResult.error("服务器异常");
    }
}
