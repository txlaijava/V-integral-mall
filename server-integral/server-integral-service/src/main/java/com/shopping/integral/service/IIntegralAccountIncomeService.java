package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAccountIncome;
import com.shopping.integral.queryform.IntegralAccountIncomeQueryForm;

import java.math.BigDecimal;

/**
 * Created by wangpenglin on 2018/8/23.
 */
public interface IIntegralAccountIncomeService extends IBaseService<IntegralAccountIncome, Long> {

    void adAccountIncome(Integer type, String desc, String oid, BigDecimal amount) throws Exception;

    BaseResult getIncomeList(IntegralAccountIncomeQueryForm accountIncomeQueryForm);
}
