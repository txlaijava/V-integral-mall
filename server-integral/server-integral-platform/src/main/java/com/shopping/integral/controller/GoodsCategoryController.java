package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.form.IntegralGoodsCategoryForm;
import com.shopping.integral.queryform.IntegralGoodsCategoryQueryForm;
import com.shopping.integral.service.IIntegralGoodsCategoryService;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 类描述：商品类别操作
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Log4j2
@Api(value = "商品类别操作", tags = {"category"})
@RestController
@RequestMapping("/category")
public class GoodsCategoryController {

    @Autowired
    IIntegralGoodsCategoryService integralGoodsCategoryService;

    @ApiOperation(value = "类别信息编辑", tags = {"category"}, notes = "")
    @PostMapping(value = "/set_category")
    public ActionResult<BaseResult> setCategory(@CurrentIntegralAcApply IntegralAcApply acApply, IntegralGoodsCategoryForm integralGoodsCategoryForm) {
        try {

            if(Utils.isNotEmpty(acApply)){
                BaseResult baseResult = integralGoodsCategoryService.updateGoodsCategory(acApply,integralGoodsCategoryForm);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("类别信息编辑异常", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取类别列表", tags = {"category"}, notes = "")
    @GetMapping(value = "/get_category_list")
    public ActionResult<BaseResult> getCategoryList(@CurrentIntegralAcApply IntegralAcApply acApply,IntegralGoodsCategoryQueryForm integralGoodsCategoryQueryForm) {
        try {
            if(Utils.isNotEmpty(acApply)){
                integralGoodsCategoryQueryForm.setAcApplyId(acApply.getId());
                BaseResult baseResult = integralGoodsCategoryService.getPageCategoryList(integralGoodsCategoryQueryForm);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取类别列表异常", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "删除类别", tags = {"category"}, notes = "")
    @DeleteMapping(value = "/del_category/{categoryId}")
    public ActionResult<BaseResult> delCategory(@CurrentIntegralAcApply IntegralAcApply acApply,@PathVariable("categoryId") Long categoryId) {
        try {
            if(Utils.isNotEmpty(acApply)){
                BaseResult baseResult = integralGoodsCategoryService.delCategoryById(acApply.getId(),categoryId);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("删除类别异常", e);
        }
        return ActionResult.error("服务器异常");
    }
}
