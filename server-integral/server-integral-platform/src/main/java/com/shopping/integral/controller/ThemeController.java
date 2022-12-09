package com.shopping.integral.controller;

import com.alibaba.fastjson.JSON;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.form.IntegralAcApplyThemeItemDataForm;
import com.shopping.integral.service.*;
import com.shopping.integral.token.authorization.annotation.CacheIntegralAccount;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import com.shopping.integral.util.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 类描述：应用主题、模块操作
 *
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Log4j2
@Api(value = "应用主题、模块操作", tags = {"theme"})
@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Autowired
    IIntegralThemeService integralThemeService;

    @Autowired
    IIntegralThemeItemService integralThemeItemService;

    @Autowired
    IIntegralAcApplyThemeItemService integralAcApplyThemeItemService;

    @Autowired
    IIntegralAcApplyThemeItemDataService integralAcApplyThemeItemDataService;

    @ApiOperation(value = "获取主题列表", tags = {"theme"}, notes = "")
    @GetMapping(value = "/getThemeList")
    public ActionResult<BaseResult> getThemeList(@CacheIntegralAccount IntegralAccount integralAccount, @CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        if (Utils.isNotEmpty(integralAccount)) {
            BaseResult baseResult = integralThemeService.getThemeList();
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取主题模板列表", tags = {"theme"}, notes = "")
    @GetMapping(value = "/getThemeItemList/{themeId}")
    public ActionResult<BaseResult> getThemeItemList(Long themeId) {

        if (Utils.isNotEmpty(themeId)) {
            BaseResult baseResult = integralThemeItemService.getByThemeIdItems(themeId);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取应用主题模板", tags = {"theme"}, notes = "")
    @GetMapping(value = "/getApplyThemeItem")
    public ActionResult<BaseResult> getApplyThemeItem(@CurrentIntegralAcApply IntegralAcApply integralAcApply,
                                                      @RequestParam(value="themeItemState",required=false,defaultValue="-1") int themeItemState) {
        try{
            if (Utils.isNotEmpty(integralAcApply)) {
                integralAcApply = integralAcApplyService.getObjById(integralAcApply.getId());
                BaseResult baseResult = integralAcApplyThemeItemService.getApplyThemeItem(integralAcApply.getId(),integralAcApply.getTheme().getId(),themeItemState);
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("获取应用主题模板接口异常！{}",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "修改应用主题模板排序和显示", tags = {"theme"}, notes = "")
    @PutMapping(value = "/putApplyThemeItem")
    public ActionResult<BaseResult> putApplyThemeItem(HttpServletRequest request, @CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        try{
            if (Utils.isNotEmpty(integralAcApply)) {
                Map params = ParamUtils.getparams(request);

                List<Map> applyThemeItem = JSON.parseArray(CommUtils.null2String(params.get("module")),Map.class);
                integralAcApply = integralAcApplyService.getObjById(integralAcApply.getId());
                BaseResult baseResult = integralAcApplyThemeItemService.upApplyThemeItem(integralAcApply.getId(),applyThemeItem);
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("获取应用主题模板接口异常！{}",e);
        }
        return ActionResult.error("服务器异常");
    }


    @ApiOperation(value = "获取应用主题模板数据", tags = {"theme"}, notes = "")
    @GetMapping(value = "/getApplyThemeItemData/{themeItemId}")
    public ActionResult<BaseResult> getApplyThemeItemData(@PathVariable("themeItemId") Long themeItemId,@CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        try{
            if (Utils.isNotEmpty(integralAcApply)) {
                BaseResult baseResult = integralAcApplyThemeItemDataService.getThemeItemDataByThemeItemId(themeItemId,integralAcApply.getId());
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("获取应用主题模板数据接口异常！{}",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "编辑、添加应用主题模板数据", tags = {"theme"}, notes = "")
    @PostMapping(value = "/saveApplyThemeItemData/{themeItemId}")
    public ActionResult<BaseResult> putApplyThemeItemData(@PathVariable("themeItemId") Long themeItemId, @CurrentIntegralAcApply IntegralAcApply integralAcApply,
                                                          IntegralAcApplyThemeItemDataForm integralAcApplyThemeItemDataForm,Long goodsId) {
        try{
            if (Utils.isNotEmpty(integralAcApply)) {
                BaseResult baseResult = integralAcApplyThemeItemDataService.upThemeItemData(themeItemId,integralAcApply.getId(),goodsId,integralAcApplyThemeItemDataForm);
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("编辑、添加应用主题模板数据接口异常！{}",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "删除应用主题模板数据", tags = {"theme"}, notes = "")
    @DeleteMapping(value = "/delApplyThemeItemData/{dataId}")
    public ActionResult<BaseResult> delApplyThemeItemData(@PathVariable("dataId") Long dataId,@CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        try{
            if (Utils.isNotEmpty(integralAcApply) && Utils.isNotEmpty(dataId)) {
                integralAcApplyThemeItemDataService.delete(dataId);
                return ActionResult.ok(BaseResult.SUCCESS("删除成功！",null));
            }
        }catch (Exception e){
            log.error("删除应用主题模板数据接口异常！{}",e);
        }
        return ActionResult.error("服务器异常");
    }
}
