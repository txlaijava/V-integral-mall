package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralExchangeRules;


/**
 * 类描述：IntegralExchangeRulesService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月03日 21:21:24.
 */
public interface IIntegralExchangeRulesService extends IBaseService<IntegralExchangeRules, Long> {

    /**
     * 验证用户兑现商品的限制
     *
     * @param goodId 商品编号
     * @param userId 兑换用户编号
     * @return
     */
    BaseResult getValiExchangeRules(Long goodId, Long userId) ;
}