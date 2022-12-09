package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.service.IIntegralAccountService;
import com.shopping.integral.token.authorization.annotation.CacheIntegralAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：操作账号信息
 *
 * @author：GuoFuJun
 * @date：2018年6月12日 上午9:27:46
 */
@Log4j2
@Api(value = "账户信息操作", tags = {"ac"})
@RestController
@RequestMapping("/ac")
public class AccountController {

    @Autowired
    IIntegralAccountService integralAccountService;

    @ApiOperation(value = "切换应用", tags = {"ac"}, notes = "")
    @PostMapping(value = "/switch_apply/{applyId}")
    public ActionResult<BaseResult> saveAcApply(@PathVariable Long applyId, @CacheIntegralAccount IntegralAccount integralAccount) {

        try{
            if(Utils.isNotEmpty(integralAccount)){
                BaseResult baseResult = integralAccountService.switchAcApply(integralAccount,applyId);
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("切换应用异常！",e);
        }
        return ActionResult.error("服务器异常");
    }
}
