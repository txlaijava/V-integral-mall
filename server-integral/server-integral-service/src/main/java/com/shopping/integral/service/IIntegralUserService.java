package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralUser;


/**
 * 类描述：IntegralUserService 接口
 *
 * @author：GuoFuJun
 * @date：2018年07月11日 18:07:32.
 */
public interface IIntegralUserService extends IBaseService<IntegralUser, Long> {

    /**
     * 更新用户积分
     *
     * @param userId  更新用户编号
     * @param credits 更新积分数
     * @return
     */
    public BaseResult updateUserCredits(Long userId, Long credits);
}