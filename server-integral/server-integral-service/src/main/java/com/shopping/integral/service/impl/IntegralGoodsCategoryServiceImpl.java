package com.shopping.integral.service.impl;

import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralGoodsCategoryDAO;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAccessory;
import com.shopping.integral.dao.model.IntegralGoodsCategory;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.form.IntegralGoodsCategoryForm;
import com.shopping.integral.queryform.IntegralGoodsCategoryQueryForm;
import com.shopping.integral.service.IIntegralAccessoryService;
import com.shopping.integral.service.IIntegralGoodsCategoryService;
import com.shopping.integral.view.IntegralGoodsCategoryView;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：IntegralGoodsCategoryService 实现
 *
 * @author：GuoFuJun
 * @date：2018年08月07日 16:01:49.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class IntegralGoodsCategoryServiceImpl extends BaseServiceImpl<IntegralGoodsCategory, Long> implements IIntegralGoodsCategoryService {

    @Autowired
    IntegralGoodsCategoryDAO integralGoodsCategoryDAO;

    @Autowired
    IIntegralAccessoryService integralAccessoryService;

    @Override
    public BaseResult updateGoodsCategory(IntegralAcApply acApply, IntegralGoodsCategoryForm integralGoodsCategoryForm) {
        try {
            if (Utils.isEmpty(acApply)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.AC_APPLY_NULL_ERROR);
            }
            IntegralGoodsCategory integralGoodsCategory = new IntegralGoodsCategory();
            integralGoodsCategory.setAcApply(acApply);
            BeanUtils.copyProperties(integralGoodsCategoryForm, integralGoodsCategory);
            if (Utils.isEmpty(integralGoodsCategory.getCategoryName())) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_CATEGORY_NAME_NULL_ERROR);
            }
            if (Utils.isEmpty(integralGoodsCategoryForm.getCategoryIconId())) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_CATEGORY_ICON_NULL_ERROR);
            }
            IntegralAccessory icon = integralAccessoryService.getObjById(integralGoodsCategoryForm.getCategoryIconId());
            integralGoodsCategory.setCategoryIcon(icon);
            Boolean bannerShow = Utils.isNotEmpty(integralGoodsCategory.getCategoryBannerShow()) ? integralGoodsCategory.getCategoryBannerShow() : false;
            integralGoodsCategory.setCategoryBannerShow(bannerShow);
            if (bannerShow && Utils.isEmpty(integralGoodsCategoryForm.getCategoryBannerId())) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_CATEGORY_BANNER_NULL_ERROR);
            }
            if (bannerShow) {
                IntegralAccessory banner = integralAccessoryService.getObjById(integralGoodsCategoryForm.getCategoryBannerId());
                integralGoodsCategory.setCategoryIcon(banner);
            }
            super.save(integralGoodsCategory);
            return BaseResult.SUCCESS("编辑成功！", integralGoodsCategory.getId());
        } catch (Exception e) {
            log.error("编辑分类接口异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult getPageCategoryList(IntegralGoodsCategoryQueryForm integralGoodsCategoryQueryForm) {
        try {
            PaginationResult<IntegralGoodsCategory> item = integralGoodsCategoryDAO.paging(integralGoodsCategoryQueryForm);
            return BaseResult.SUCCESS("获取成功！", item.getView(IntegralGoodsCategoryView.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult delCategoryById(Long applyId, Long categoryId) {
        try {
            BaseResult baseResult = getCategoryById(applyId, categoryId);
            if (!baseResult.getSuccess()) {
                return baseResult;
            }
            IntegralGoodsCategoryView categoryView = (IntegralGoodsCategoryView) baseResult.getData();
            Boolean delBol = integralGoodsCategoryDAO.deleteById(categoryView.getId());
            if (delBol) {
                return BaseResult.SUCCESS("删除成功", null);
            } else {
                BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
            }
        } catch (Exception e) {
            log.error("删除类别异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    @Override
    public BaseResult getCategoryById(Long applyId, Long categoryId) {
        try {
            if (Utils.isEmpty(applyId)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_APPLY_ID_NULL_ERROR);
            }
            if (Utils.isEmpty(categoryId)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.PARM_CATEGORY_ID_NULL_ERROR);
            }
            Map params = new HashMap(2);
            params.put("acApply.id", applyId);
            params.put("id", categoryId);
            IntegralGoodsCategory integralGoodsCategory = integralGoodsCategoryDAO.findOneByMap(params);
            if (Utils.isEmpty(integralGoodsCategory)) {
                return BaseResult.ERROR(ResultErrorCodeEnum.CATEGORY_NULL_ERROR);
            }
            IntegralGoodsCategoryView categoryView = BeanViewUtils.getView(integralGoodsCategory, IntegralGoodsCategoryView.class);
            return BaseResult.SUCCESS("删除成功", categoryView);
        } catch (Exception e) {
            log.error("获取类别异常！", e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }
}