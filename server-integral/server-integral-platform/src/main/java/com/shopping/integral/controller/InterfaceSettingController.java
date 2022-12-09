package com.shopping.integral.controller;

import com.alibaba.fastjson.JSONObject;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.service.IIntegralInterfaceSettingService;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 类描述：接口配置操作
 *
 * @author：GuoFuJun
 * @date：2018年7月2日 上午11:13:46
 */
@Log4j2
@Api(value = "商品操作", tags = {"interface"})
@RestController
@RequestMapping("/interface")
public class InterfaceSettingController {

    @Autowired
    IIntegralInterfaceSettingService integralInterfaceSettingService;

    @ApiOperation(value = "获取应用接口配置", tags = {"interface"}, notes = "")
    @GetMapping(value = "/get_interface_setting")
    public ActionResult<BaseResult> getInterfaceSetting(@CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        try {
            if (Utils.isNotEmpty(integralAcApply)) {
                BaseResult baseResult = integralInterfaceSettingService.getByApplyIdList(integralAcApply.getId());
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取应用接口配置异常", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "修改应用接口配置", tags = {"interface"}, notes = "")
    @PostMapping(value = "/update_interface_setting")
    public ActionResult<BaseResult> updateInterfaceSetting(HttpServletRequest request,@CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        try {
            if (Utils.isNotEmpty(integralAcApply)) {
                String params = request.getParameter("params");
                Map obj = JSONObject.parseObject(params,Map.class);
                BaseResult baseResult = integralInterfaceSettingService.updateInterfaceSetting(integralAcApply.getId(),obj);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("修改应用接口配置异常", e);
        }
        return ActionResult.error("服务器异常");
    }
}
