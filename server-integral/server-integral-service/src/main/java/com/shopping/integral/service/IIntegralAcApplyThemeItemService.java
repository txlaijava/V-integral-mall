package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApplyThemeItem;
import com.shopping.integral.dao.model.IntegralTheme;

import java.util.List;
import java.util.Map;


/**
 * 类描述：IntegralAcApplyThemeItemService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月08日 15:25:03.
 */
public abstract interface IIntegralAcApplyThemeItemService extends IBaseService<IntegralAcApplyThemeItem, Long> {

    /**
     * 获取应用下的主题模板
     *
     * @param applyId        应用编号
     * @param themeId        主题编号
     * @param themeItemState 模板状态
     * @return
     */
    BaseResult getApplyThemeItem(Long applyId, Long themeId, int themeItemState);

    /**
     * 编辑应用下的主题模板
     *
     * @param applyId
     * @param integralTheme
     * @return
     */
    BaseResult saveApplyThemeItem(Long applyId, IntegralTheme integralTheme);

    /**
     * 修改应用主题模板排序和显示
     *
     * @param applyId        修改的应用编号
     * @param applyThemeItem 修改的模板
     * @return
     */
    BaseResult upApplyThemeItem(Long applyId, List<Map> applyThemeItem);
}