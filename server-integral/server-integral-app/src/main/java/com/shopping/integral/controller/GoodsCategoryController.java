package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.service.IIntegralGoodsCategoryService;
import com.shopping.integral.token.authorization.annotation.CurrentAppKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：商品类别操作
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Log4j2
@Api(value = "商品类别操作", tags = {"category"})
@RestController
@RequestMapping("/app/category")
public class GoodsCategoryController {

    @Autowired
    IIntegralGoodsCategoryService integralGoodsCategoryService;

    @Autowired
    IndexController indexController;

    @ApiOperation(value = "获取类别详情", tags = {"category"}, notes = "")
    @GetMapping(value = "/get_category_details/{categoryId}")
    public ActionResult<BaseResult> getCategoryDetails(@CurrentAppKey String appKey, @PathVariable("categoryId") Long categoryId) {
        try {
            IntegralAcApply acApply = indexController.integralAcApply(appKey);
            if(Utils.isNotEmpty(acApply)){
                BaseResult baseResult = integralGoodsCategoryService.getCategoryById(acApply.getId(),categoryId);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取类别详情异常", e);
        }
        return ActionResult.error("服务器异常");
    }
}
