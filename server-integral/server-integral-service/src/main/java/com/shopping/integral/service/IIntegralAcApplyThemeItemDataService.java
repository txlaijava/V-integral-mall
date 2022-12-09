package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApplyThemeItemData;
import com.shopping.integral.form.IntegralAcApplyThemeItemDataForm;


/**
 * 类描述：IntegralAcApplyThemeItemDataService 接口
 *
 * @author：GuoFuJun
 * @date：2018年05月08日 15:25:03.
 */
public abstract interface IIntegralAcApplyThemeItemDataService extends IBaseService<IntegralAcApplyThemeItemData, Long> {

    /**
     * 获取模板对应的数据
     *
     * @param themeItemId 模板编号
     * @param applyId     应用编号
     * @return
     */
    BaseResult getThemeItemDataByThemeItemId(Long themeItemId, Long applyId);

    /**
     * 修改模板对应的数据
     *
     * @param themeItemId                      模板编号
     * @param applyId                          应用编号
     * @param goodsId                          商品编号（可为空）
     * @param integralAcApplyThemeItemDataForm 修改的数据
     * @return
     */
    BaseResult upThemeItemData(Long themeItemId, Long applyId, Long goodsId, IntegralAcApplyThemeItemDataForm integralAcApplyThemeItemDataForm);

}