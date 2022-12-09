package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralAcApplyThemeItemDataService;
import com.shopping.integral.service.IIntegralAcApplyThemeItemService;
import com.shopping.integral.service.VistDateService;
import com.shopping.integral.token.authorization.annotation.CurrentAppKey;
import com.shopping.integral.view.IntegralAcApplyView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：应用主题、模块操作
 *
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Log4j2
@Api(value = "应用主题、模块操作", tags = {"theme"})
@RestController
@RequestMapping("/app/index")
public class IndexController {

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Autowired
    IIntegralAcApplyThemeItemService integralAcApplyThemeItemService;

    @Autowired
    IIntegralAcApplyThemeItemDataService integralAcApplyThemeItemDataService;

    @Autowired
    VistDateService vistDateService;

    /**
     * 通过应用Key获取应用
     *
     * @param appKey 应用Key
     * @return
     */
    public IntegralAcApply integralAcApply(String appKey) {
        try {
            return integralAcApplyService.getOneObjByProperty("appKey", appKey);
        } catch (Exception e) {
            log.error("获取应用异常！", e);
        }
        return null;
    }

    @ApiOperation(value = "获取应用主题模板", tags = {"theme"}, notes = "")
    @GetMapping(value = "/get_apply")
    public ActionResult<BaseResult> getApply(@CurrentAppKey String appKey){
        try {
            if (Utils.isNotEmpty(appKey)) {
                IntegralAcApply integralAcApply = integralAcApply(appKey);
                if (Utils.isNotEmpty(integralAcApply)) {
                    IntegralAcApplyView integralAcApplyView = BeanViewUtils.getView(integralAcApply,IntegralAcApplyView.class);
                    BaseResult baseResult = BaseResult.SUCCESS("获取成功",integralAcApplyView);
                    return ActionResult.ok(baseResult);
                }
            }
        } catch (Exception e) {
            log.error("获取应用主题模板接口异常！{}", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取应用主题模板", tags = {"theme"}, notes = "")
    @GetMapping(value = "/get_apply_theme_item")
    public ActionResult<BaseResult> getApplyThemeItem(@CurrentAppKey String appKey) {
        try {
            if (Utils.isNotEmpty(appKey)) {
                IntegralAcApply integralAcApply = integralAcApply(appKey);
                if (Utils.isNotEmpty(integralAcApply)) {
                    //记录访问PV
                    vistDateService.VIST_PV(integralAcApply.getAppKey());
                    //返回模板数据
                    BaseResult baseResult = integralAcApplyThemeItemService.getApplyThemeItem(integralAcApply.getId(), integralAcApply.getTheme().getId(), -1);
                    return ActionResult.ok(baseResult);
                }
            }
        } catch (Exception e) {
            log.error("获取应用主题模板接口异常！{}", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取应用主题模板数据", tags = {"theme"}, notes = "")
    @GetMapping(value = "/get_apply_theme_item_data/{themeItemId}")
    public ActionResult<BaseResult> getApplyThemeItemData(@CurrentAppKey String appKey,@PathVariable("themeItemId") Long themeItemId) {
        try {
            if (Utils.isNotEmpty(appKey)) {
                IntegralAcApply integralAcApply = integralAcApply(appKey);
                if (Utils.isNotEmpty(integralAcApply)) {
                    BaseResult baseResult = integralAcApplyThemeItemDataService.getThemeItemDataByThemeItemId(themeItemId, integralAcApply.getId());
                    return ActionResult.ok(baseResult);
                }

            }
        } catch (Exception e) {
            log.error("获取应用主题模板数据接口异常！{}", e);
        }
        return ActionResult.error("服务器异常");
    }
}
