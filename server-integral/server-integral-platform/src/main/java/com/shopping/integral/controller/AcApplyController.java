package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralAccessory;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.queryform.IntegralAcApplyAddForm;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IIntegralAccessoryService;
import com.shopping.integral.service.IIntegralAccountService;
import com.shopping.integral.token.authorization.annotation.CacheIntegralAccount;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import com.shopping.integral.util.ParamUtils;
import com.shopping.integral.view.IntegralAcApplyView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 类描述：操作应用信息
 *
 * @author：GuoFuJun
 * @date：2018年7月13日 上午9:27:46
 */
@Log4j2
@Api(value = "操作应用信息", tags = {"ac_apply"})
@RestController
@RequestMapping("/ac_apply")
public class AcApplyController {

    @Autowired
    IIntegralAcApplyService integralAcApplyService;

    @Autowired
    IIntegralAccessoryService integralAccessoryService;

    @Autowired
    IIntegralAccountService integralAccountService;


    @ApiOperation(value = "获取账号下的应用", tags = {"ac_apply"}, notes = "")
    @GetMapping(value = "/get_ac_apply_list")
    public ActionResult<BaseResult> getAcApplyList(@CacheIntegralAccount IntegralAccount integralAccount) {

        try{
            if(Utils.isNotEmpty(integralAccount)){
                BaseResult baseResult = integralAcApplyService.getAcApplyByAcId(integralAccount.getId());
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("获取账号下的应用异常！",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "添加应用", tags = {"ac_apply"}, notes = "")
    @PostMapping(value = "/save_ac_apply")
    public ActionResult<BaseResult> saveAcApply(HttpServletRequest request, @CacheIntegralAccount IntegralAccount integralAccount,Long icon) {

        try{
            if(Utils.isNotEmpty(integralAccount)){
                Map params = ParamUtils.getparams(request);
                IntegralAccessory integralAccessory = null;
                if(Utils.isNotEmpty(icon)){
                    integralAccessory = integralAccessoryService.getObjById(icon);
                }
                BaseResult baseResult = integralAcApplyService.setIntegralAcApply(params,integralAccount,integralAccessory);

                if(baseResult.getSuccess()){
                    integralAccountService.switchAcApply(integralAccount, CommUtils.null2Long(baseResult.getData()));
                }

                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("获取账号下的应用异常！",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "获取当前应用信息", tags = {"ac_apply"}, notes = "")
    @GetMapping(value = "/get_ac_apply")
    public ActionResult<BaseResult> getAcApply(@CurrentIntegralAcApply IntegralAcApply integralAcApply) {

        try{
            if(Utils.isNotEmpty(integralAcApply)){
                IntegralAcApplyView integralAcApplyView = BeanViewUtils.getView(integralAcApply,IntegralAcApplyView.class);
                integralAcApplyView.setThemeId(integralAcApply.getTheme().getId());
                BaseResult baseResult = BaseResult.SUCCESS("获取数据成功！",integralAcApplyView);

                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("获取账号下的应用异常！",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "修改应用", tags = {"ac_apply"}, notes = "")
    @PutMapping(value = "/update_ac_apply/{applyId}")
    public ActionResult<BaseResult> updateAcApply(@CacheIntegralAccount IntegralAccount integralAccount, @PathVariable Long applyId, IntegralAcApplyAddForm integralAcApplyAddForm) {

        try{
            if(Utils.isNotEmpty(integralAccount) && Utils.isNotEmpty(applyId)){
                integralAcApplyAddForm.setId(applyId);
                BaseResult baseResult = integralAcApplyService.updateAcApply(integralAcApplyAddForm);
                return ActionResult.ok(baseResult);
            }
        }catch (Exception e){
            log.error("获取账号下的应用异常！",e);
        }
        return ActionResult.error("服务器异常");
    }
}
