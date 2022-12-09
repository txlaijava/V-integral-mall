package com.shopping.integral.token.authorization.resolvers;

import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.dao.IntegralAccountDAO;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.token.authorization.annotation.CurrentIntegralAcApply;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.model.CheckResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 增加方法注入，将含有CacheUser注解的方法参数注入当前缓存的登录User对象
 *
 * @author guofujun
 * @date 2016/10/31
 * @see CurrentIntegralAcApply
 */
@Component
public class CurrentIntegralAcApplyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IntegralAccountDAO integralAccountDAO;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentIntegralAcApply.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authHeader = webRequest.getHeader("Token");
        if (StringUtils.isNotBlank(authHeader)) {
            CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
            if (checkResult.isSuccess()) {
                String key = checkResult.getClaims().getId();
                IntegralAccount account = integralAccountDAO.findById(CommUtils.null2Long(key));
                if(Utils.isEmpty(account)){
                    return null;
                }
                return account.getAcApply();
            }
        }
        return null;
    }
}