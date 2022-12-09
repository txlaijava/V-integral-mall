package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralThemeItem;


/**
* 类描述：IntegralThemeItemService 接口
*
* @author：GuoFuJun
* @date：2018年05月08日 15:25:03.
*/
public abstract interface IIntegralThemeItemService extends IBaseService<IntegralThemeItem,Long> {

    /**
     * 获取指定主题下的模板
     * @param themeId 主题编号
     * @return
     */
    BaseResult getByThemeIdItems(Long themeId);
}