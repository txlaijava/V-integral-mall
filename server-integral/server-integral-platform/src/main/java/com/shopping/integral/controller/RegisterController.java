package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralAccountService;
import com.shopping.integral.token.authorization.annotation.CacheIntegralAccount;
import com.shopping.integral.util.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 类描述：注册账户，应用相关
 *
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Log4j2
@Api(value = "注册账户，应用", tags = {"register"})
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    IIntegralAccountService integralAccountService;

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @ApiOperation(value = "注册账户", tags = {"register"}, notes = "")
    @PostMapping(value = "/setIntegralAc")
    public ActionResult<BaseResult> setIntegralAc(HttpServletRequest request) {
        try {
            Map params = ParamUtils.getparams(request);
            BaseResult baseResult = integralAccountService.setIntegralAccount(params);
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("注册账户异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "添加应用", tags = {"register"}, notes = "")
    @PostMapping(value = "/setIntegralApply")
    public ActionResult<Map> setIntegralApply(HttpServletRequest request,@CacheIntegralAccount IntegralAccount account) {
        try {
            Map params = ParamUtils.getparams(request);
            integralAcApplyService.setIntegralAcApply(params, account, null);
        } catch (Exception e) {
            log.error("添加应用异常！", e);
        }
        return ActionResult.error("服务器异常");
    }
}
