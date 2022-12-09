package com.shopping.integral.service.impl;

import com.pingplusplus.model.Charge;
import com.shopping.base.foundation.util.DateUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.conf.PayCharge;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.constant.OrderCons;
import com.shopping.integral.dao.model.IntegralOrder;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.queryform.PayChargeForm;
import com.shopping.integral.service.IIntegralAccountIncomeService;
import com.shopping.integral.service.IIntegralOrderService;
import com.shopping.integral.service.IPayService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 类描述：PayServiceImpl 接口
 * 支付处理
 *
 * @author：GuoFuJun
 * @date：2018年06月06日 10:50:30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class PayServiceImpl implements IPayService {

    private static final String CHARGE_SUCCEEDED = "charge.succeeded";

    @Autowired
    IIntegralOrderService integralOrderService;

    @Autowired
    IIntegralAccountIncomeService accountIncomeService;

    @Override
    public BaseResult createPay(IntegralUser integralUser, PayChargeForm payChargeForm) {
        try {
            BaseResult verify = paramsVerify(integralUser, payChargeForm);
            if (!verify.getSuccess()) {
                return verify;
            }
            PayCharge payCharge = new PayCharge();
            BeanUtils.copyProperties(payChargeForm, payCharge, "id");
            Charge charge = null;
            if (Utils.isEmpty(charge)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PAY_ERROR);
            }
            return BaseResult.SUCCESS("创建支付请求成功！", charge);
        } catch (Exception e) {
            log.error("创建支付请求异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult paySuccessHooks(Map param) throws Exception {
        String oid  = CommUtils.null2String(param.get("oid"));
        BigDecimal amount = CommUtils.null2BigDecimal(param.get("amount"));
        String id = CommUtils.null2String(param.get("id"));
        log.info("支付成功webHooks通知,订单号: " + oid);
        /*收入账户明细记录*/
        this.accountIncomeService.adAccountIncome(2,"订单支付回调结算并且添加收入账户明细记录：oid："+oid+"，金额："+amount,oid,amount);
        /*收入账户明细记录*/
        return integralOrderService.dispostDepartmentSuccess(id, amount, oid);
    }

    /**
     * 验证支付信息是否合法
     *
     * @param integralUser  支付用户
     * @param payChargeForm 支付信息
     * @return
     * @throws Exception
     */
    private BaseResult paramsVerify(IntegralUser integralUser, PayChargeForm payChargeForm) throws Exception {

        if (Utils.isEmpty(integralUser)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_USER_NULL_ERROR);
        }

        if (Utils.isEmpty(payChargeForm)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PARM_NULL_ERROR);
        }

        if (Utils.isEmpty(payChargeForm.getChannel())) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_CHANNEL_NULL_ERROR);
        }

        if (Utils.isEmpty(payChargeForm.getOrderId())) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_ORDER_NULL_ERROR);
        }
        // 查询支付订单
        IntegralOrder integralOrder = integralOrderService.getOneObjByProperty("oid", payChargeForm.getOrderId());
        if (Utils.isEmpty(integralOrder)) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_ORDER_NULL_ERROR);
        }
        payChargeForm.setAmount(integralOrder.getOrderTotalPrice());
        // 获取订单创建时间（后半个小时）和当前时间对对比，如果创建时间小于当前时间则支付超时
        Date curreTime = DateUtils.addMinutes(integralOrder.getPayTime(), 30);
        if (curreTime.getTime() <= System.currentTimeMillis()) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_TIMEOUT_ERROR);
        }

        // 开发者平台订单号为空（该订单积分未扣除成功）
        if (Utils.isEmpty(integralOrder.getBizId())) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_NOT_INTEGRAL_ERROR);
        }

        if (integralOrder.getOrderState() > OrderCons.WAIT_AUDIT) {
            return BaseResult.ERROR(ResultErrorCodeEnum.PAY_STATE_ERROR);
        }

        return BaseResult.SUCCESS("验证成功!", "");
    }
}
