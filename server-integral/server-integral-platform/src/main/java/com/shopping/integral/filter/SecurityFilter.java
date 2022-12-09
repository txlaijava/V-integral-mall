package com.shopping.integral.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.foundation.result.Message;
import com.shopping.base.foundation.util.RequestResponseUtil;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.constant.RedisCons;
import com.shopping.integral.dao.IntegralAccountDAO;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.config.Constants;
import com.shopping.integral.token.config.ResultStatus;
import com.shopping.integral.token.model.CheckResult;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@Log4j
public class SecurityFilter implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IntegralAccountDAO integralAccountDAO;

    private static Set<String> GreenUrlSet = new HashSet<String>();


    public void init() throws ServletException {
        // TODO Auto-generated method stub
        GreenUrlSet.add("/orderManage/orderListExport");
        GreenUrlSet.add("/demo/testCancel");
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 只有返回true才会继续向下执行，返回false取消当前请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Token");
        String uri = request.getRequestURI();
        this.init();
        if(greenUrlCheck(uri)){
            return true;
        }
        if (StringUtils.isEmpty(authHeader)) {
            log.info("Signature verification does not exist.");
            print(response, ResultStatus.JWT_ERRCODE_NULL.getCode(), ResultStatus.JWT_ERRCODE_NULL.getEgMessage());
            return false;
        } else {
            //验证JWT的签名，返回CheckResult对象
            CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
            if (checkResult.isSuccess()) {
                String getKey = checkResult.getClaims().getId();
            } else {
                ResultStatus resultStatus = ResultStatus.getEnumByCode(checkResult.getErrCode());
                if (Utils.isNotEmpty(resultStatus)) {
                    // 如果是JWT过期
                    if (resultStatus.getCode() == ResultStatus.JWT_ERRCODE_EXPIRE.getCode()) {
                        // 这里初始方案先抛出令牌过期，之后设计为在Redis中查询当前appId对应令牌，其设置的过期时间是JWT的两倍，此作为JWT的refresh时间
                        // 当JWT的有效时间过期后，查询其refresh时间，refresh时间有效即重新派发新的JWT给客户端，
                        // refresh也过期则告知客户端JWT时间过期重新认证

                        // 当存储在redis的JWT没有过期，即refresh time 没有过期
                        String appId = request.getHeader("Uid");
                        String refreshJwt = redisService.get(RedisCons.JWT_SESSION_PLATFORM + appId);
                        if(refreshJwt!=null){refreshJwt = refreshJwt.replace("\"", "");}
                        if (null != refreshJwt && refreshJwt.equals(authHeader)) {
                            IntegralAccount integralAccount = integralAccountDAO.findById(CommUtils.null2Long(appId));
                            // 重新申请新的JWT
                            // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
                            long refreshPeriodTime = Constants.JWT_TTL;
                            String Token = JwtTokenUtils.createJWT(CommUtils.null2String(integralAccount.getId()), integralAccount.getAcMail(), Constants.JWT_TTL >> 1);
                            // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
                            redisService.set(RedisCons.JWT_SESSION_PLATFORM + integralAccount.getId(), Token, refreshPeriodTime);

                            Message message = new Message().ok(1005, "new jwt").addData("jwt", Token);
                            RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                            return false;
                        }
                    }

                    print(response, checkResult.getErrCode(), resultStatus.getEgMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void print(HttpServletResponse response, Object errorCode, String errorMesg) throws IOException {
        JSONObject json = new JSONObject();
        json.put("error", errorCode);
        json.put("error_msg", errorMesg);
        response.getWriter().write(JSON.toJSONString(json));
    }

    /**
     * 检查无需登录前缀
     * @param url
     * @return
     */
    private boolean greenUrlCheck(String url){
        for(String greenUrl : GreenUrlSet){
            if(url.startsWith(greenUrl)){
                return true;
            }
        }
        return false;
    }
    /**
     * @param url
     * @return
     * @author neo
     */
    private boolean containsSuffix(String url) {
        if (url.endsWith(".js")
                || url.endsWith(".css")
                || url.endsWith(".jpg")
                || url.endsWith(".gif")
                || url.endsWith(".png")
                || url.endsWith(".html")
                || url.endsWith(".eot")
                || url.endsWith(".svg")
                || url.endsWith(".ttf")
                || url.endsWith(".woff")
                || url.endsWith(".ico")
                || url.endsWith(".woff2")) {
            return true;
        } else {
            return false;
        }
    }
}