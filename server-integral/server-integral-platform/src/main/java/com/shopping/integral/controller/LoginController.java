package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.service.ILonginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 类描述：登录，登出接口
 *
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Log4j2
@Api(value = "用户登录，登出操作", tags = {"login"})
@RestController
@RequestMapping("/")
public class LoginController{

    @Autowired
    ILonginService longinService;

    @ApiOperation(value = "积分商城登录", tags = {"login"}, notes = "")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ActionResult<BaseResult> login(HttpServletRequest request, HttpServletResponse response) {
        try{
            String acMail = request.getParameter("mail");
            String acPassword = request.getParameter("password");
            BaseResult baseResult = longinService.getlLoginValidation(acMail,acPassword);
            return ActionResult.ok(baseResult);
        }catch (Exception e){
            log.error("登录异常！",e);
        }
        return ActionResult.error("服务器异常");
    }


    @ApiOperation(value = "积分商城登出", tags = {"login"}, notes = "")
    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public ActionResult<Map> loginOut(HttpServletRequest request, HttpServletResponse response) {

        return ActionResult.error("服务器异常");
    }
}
