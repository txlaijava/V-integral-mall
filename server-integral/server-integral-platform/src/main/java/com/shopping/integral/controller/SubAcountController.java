package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.service.IIntegralAccountService;
import com.shopping.integral.service.ISubAccountService;
import com.shopping.integral.token.authorization.annotation.CacheIntegralAccount;
import com.shopping.integral.util.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 类描述：子账号操作
 *
 * @author：GuoFuJun
 * @date：2018年6月12日 上午9:27:46
 */
@Log4j2
@Api(value = "子账号操作", tags = {"child"})
@RestController
@RequestMapping("/child")
public class SubAcountController {

    @Autowired
    ISubAccountService subAccountService;

    @Autowired
    IIntegralAccountService integralAccountService;

    @ApiOperation(value = "添加子账户", tags = {"child"}, notes = "")
    @PostMapping(value = "/setIntegralChildAc")
    public ActionResult<BaseResult> setIntegralChildAc(HttpServletRequest request, @CacheIntegralAccount IntegralAccount integralAccount) {
        try {
            if(Utils.isNotEmpty(integralAccount)){
                Map params = ParamUtils.getparams(request);
                params.put("acParentId",integralAccount.getId());
                BaseResult baseResult = integralAccountService.setIntegralAccount(params);
                return ActionResult.ok(baseResult);
            }

        } catch (Exception e) {
            log.error("添加子账户异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取子账户列表", tags = {"child"}, notes = "")
    @GetMapping(value = "/getSubAccountList")
    public ActionResult<BaseResult> getSubAccountList(@CacheIntegralAccount IntegralAccount integralAccount) {
        if (Utils.isNotEmpty(integralAccount)) {
            BaseResult baseResult = subAccountService.getSubAccountList(integralAccount.getId());
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取子账户信息", tags = {"child"}, notes = "")
    @GetMapping(value = "/getSubAccount")
    public ActionResult<BaseResult> getSubAccount(Long subAcId) {
        if (Utils.isNotEmpty(subAcId)) {
            BaseResult baseResult = subAccountService.getSubAccount(subAcId);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "删除子账号", tags = {"child"}, notes = "")
    @DeleteMapping(value = "/delIntegralChildAc")
    public ActionResult<BaseResult> delIntegralChildAc(@CacheIntegralAccount IntegralAccount integralAccount,Long subAcId) {
        if (Utils.isNotEmpty(subAcId)) {
            BaseResult baseResult = subAccountService.delSubAccount(integralAccount.getId(),subAcId);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "修改子账号密码", tags = {"child"}, notes = "")
    @DeleteMapping(value = "/upIntegralChildAcPwd")
    public ActionResult<BaseResult> upIntegralChildAcPwd(@CacheIntegralAccount IntegralAccount integralAccount,Long subAcId) {
        if (Utils.isNotEmpty(subAcId)) {
            BaseResult baseResult = subAccountService.delSubAccount(integralAccount.getId(),subAcId);
            return ActionResult.ok(baseResult);
        }
        return ActionResult.error("服务器异常");
    }
}
