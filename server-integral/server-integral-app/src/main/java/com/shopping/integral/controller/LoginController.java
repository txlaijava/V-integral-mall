package com.shopping.integral.controller;

import com.alibaba.fastjson.JSON;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.AppBaseController;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.base.LogEntity;
import com.shopping.integral.dao.model.IntegralAcApply;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.sdk.CreditTool;
import com.shopping.integral.sdk.entity.CreditConsumeParams;
import com.shopping.integral.sdk.result.CreditConsumeResult;
import com.shopping.integral.service.IIntegralAcApplyService;
import com.shopping.integral.service.IUserService;
import com.shopping.integral.service.VistDateService;
import com.shopping.integral.token.authorization.annotation.CacheUser;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Api(value = "用户登录，登出操作", tags = {"login"})
@RestController
@RequestMapping("/autoLogin")
public class LoginController extends AppBaseController {

    @Autowired
    private IIntegralAcApplyService integralAcApplyService;

    @Autowired
    private IUserService userService;

    @Autowired
    VistDateService vistDateService;

    @Value("${app.path}")
    private String appPath;

    @ApiOperation(value = "登陆接口", tags = {"login"}, notes = "")
    @RequestMapping(value = "/autologin")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/plain");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            String appKey = request.getParameter("appKey");
            String redirect = request.getParameter("redirect");
            String remark = request.getParameter("remark") != null ? URLDecoder.decode(request.getParameter("remark"), "UTF-8") : "";
            log.info("autoLogin " + appKey + " redirect: " + redirect + " remark:" + remark);
            if (Utils.isNotEmpty(appKey)) {
                IntegralAcApply acApply = this.integralAcApplyService.getOneObjByProperty("appKey", appKey);
                if (acApply != null) {
                    //应用状态为正常
                    if (acApply.getAc().getAc_status() == 0) {
                        CreditTool tool = new CreditTool(appKey, acApply.getAppSecret());
                        //利用tool来解析这个请求
                        CreditConsumeParams params = tool.parseCreditConsume(request);
                        if (params.getSuccess()) {
                            log.info("验签成功");
                            //利用这个来返回处理状态
                            CreditConsumeResult result = new CreditConsumeResult();
                            //用户id
                            String uid = params.getUid();
                            Long credits = params.getCredits();
                            String openid = params.getOpenid();
                            if ("".equals(CommUtils.null2String(uid)) || params.getCredits() == null) {
                                log.info("参数有误");
                                this.errorPage(response, "参数有误");
                                return;
                            }
                            Date timestamp = params.getTimestamp();

                            //TODO 平台用户同步开发者用户id操作
                            IntegralUser user = this.userService.autoLogin(acApply.getId(), appKey, uid, credits, "", openid, remark);

                            //进行登录跳转
                            String token = JwtTokenUtils.createJWT(user.getId() + "", appKey, -1);
                            log.info("Create token:{}", token);
                            if ("wxApp".equals(params.getRedirect())) {
                                //记录访问UV
                                vistDateService.VIST_UV(appKey, token, "wxApp");
                                Map param = new HashMap();
                                param.put("token", token);
                                PrintWriter writer = response.getWriter();
                                writer.print(JSON.toJSON(param));
                                log.info("返回Token:" + token);
                                return;
                            } else {
                                //记录访问UV
                                vistDateService.VIST_UV(appKey, token, "h5");
                            }
                            response.sendRedirect(appPath + "/index/home?token=" + token);
                            return;
                        } else {
                            log.info("解析失败,失败原因：" + params.getMsg());
                            if ("wxApp".equals(redirect)) {
                                Map param = new HashMap();
                                param.put("token", "");
                                param.put("error", params.getMsg());
                                PrintWriter writer = response.getWriter();
                                writer.print(JSON.toJSON(param));
                                return;
                            } else {
                                this.errorPage(response, params.getMsg());
                                return;
                            }
                        }
                    } else {
                        log.info("账号状态异常");
                        this.errorPage(response, "账号状态异常");
                        return;
                    }
                } else {
                    log.info("未知的appKey");
                    if ("wxApp".equals(redirect)) {
                        Map param = new HashMap();
                        param.put("token", "");
                        param.put("error", "未知的appKey");
                        PrintWriter writer = response.getWriter();
                        writer.print(JSON.toJSON(param));
                        return;
                    } else {
                        LogEntity loger = new LogEntity();
                        loger.setName("调用登陆接口错误");
                        loger.setAppkey(appKey);
                        loger.setInfo("aaaa未知的appKey");
                        log.info(JSON.toJSONString(loger));
                        log.error(JSON.toJSONString(loger));
                        //log.info();
                        this.errorPage(response, "未知的appKey");
                        return;
                    }
                }
            } else {
                this.errorPage(response, "参数错误");
                return;
            }
        } catch (Exception e) {
            log.error("登录异常！", e);
        }
    }

    @ApiOperation(value = "获取登录用户", tags = {"login"}, notes = "")
    @GetMapping(value = "/login_user")
    public ActionResult<BaseResult> loginUser(@CacheUser IntegralUser integralUser) {
        if (Utils.isEmpty(integralUser)) {
            return ActionResult.ok(BaseResult.ERROR(ResultErrorCodeEnum.APP_USER_NOT_LOGIN_ERROR));
        }
        return ActionResult.ok(BaseResult.SUCCESS("获取成功！", integralUser));
    }

}
