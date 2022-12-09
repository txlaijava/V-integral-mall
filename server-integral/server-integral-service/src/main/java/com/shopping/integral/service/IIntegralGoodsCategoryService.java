package com.shopping.integral.service;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralGoodsCategory;
import com.shopping.integral.form.IntegralGoodsCategoryForm;
import com.shopping.integral.queryform.IntegralGoodsCategoryQueryForm;


/**
 * 类描述：IntegralGoodsCategoryService 接口
 *
 * @author：GuoFuJun
 * @date：2018年08月07日 16:01:49.
 */
public interface IIntegralGoodsCategoryService extends IBaseService<IntegralGoodsCategory, Long> {

    /**
     * 编辑分类（包含添加，修改）
     *
     * @param acApply                   操作应用
     * @param integralGoodsCategoryForm 分类数据
     * @return
     */
    BaseResult updateGoodsCategory(IntegralAcApply acApply, IntegralGoodsCategoryForm integralGoodsCategoryForm);

    /**
     * 获取类别分页
     *
     * @param integralGoodsCategoryQueryForm 查询条件
     * @return
     */
    BaseResult getPageCategoryList(IntegralGoodsCategoryQueryForm integralGoodsCategoryQueryForm);

    /**
     * 删除类别
     *
     * @param applyId    应用编号
     * @param categoryId 类别编号
     * @return
     */
    BaseResult delCategoryById(Long applyId, Long categoryId);

    /**
     * 类别详情
     *
     * @param applyId    应用编号
     * @param categoryId 类别编号
     * @return
     */
    BaseResult getCategoryById(Long applyId, Long categoryId);
}