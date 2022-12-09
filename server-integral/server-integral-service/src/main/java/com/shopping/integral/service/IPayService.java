package com.shopping.integral.service;

import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.queryform.PayChargeForm;

import java.util.Map;

/**
 * 类描述：IPayService 接口
 *
 * @author：GuoFuJun
 * @date：2018年06月06日 10:50:30.
 */
public interface IPayService {

    /**
     * 发起支付请求
     *
     * @param integralUser  支付用户
     * @param payChargeForm 支付信息
     * @return
     */
    BaseResult createPay(IntegralUser integralUser, PayChargeForm payChargeForm);

    /**
     * 支付成功(异步)
     *
     * @param param
     * @return
     * @throws Exception
     */
    BaseResult paySuccessHooks(Map param) throws Exception;
}
