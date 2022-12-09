package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.service.ILonginService;
import com.shopping.integral.service.VistDateService;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Api(value = "index", tags = {"login"})
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    ILonginService longinService;

    @Autowired
    VistDateService vistDateService;

    @ApiOperation(value = "首页数据", tags = {"login"}, notes = "")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ActionResult<BaseResult> index(@CurrentIntegralAcApply IntegralAcApply integralAcApply) {
        try{

            int pv = this.vistDateService.GET_VIST_PV("2SyfGxQecrSEkrjDpgVEftFdJc16");
            int uv = this.vistDateService.GET_VIST_UV("2SyfGxQecrSEkrjDpgVEftFdJc16");

            return ActionResult.ok();
        }catch (Exception e){
            log.error("登录异常！",e);
        }
        return ActionResult.error("服务器异常");
    }
}
