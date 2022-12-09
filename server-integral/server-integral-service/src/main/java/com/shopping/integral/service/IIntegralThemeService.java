package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralTheme;


/**
 * 类描述：IntegralThemeService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月08日 15:25:03.
 */
public abstract interface IIntegralThemeService extends IBaseService<IntegralTheme, Long> {

    /**
     * 获取主题集合
     *
     * @return
     */
    BaseResult getThemeList();
}