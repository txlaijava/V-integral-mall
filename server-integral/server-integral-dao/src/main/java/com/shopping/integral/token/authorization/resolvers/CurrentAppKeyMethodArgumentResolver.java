package com.shopping.integral.token.authorization.resolvers;


import com.shopping.integral.token.authorization.annotation.CurrentAppKey;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.model.CheckResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 增加方法注入，注入token包含的appkey
 */
@Component
public class CurrentAppKeyMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentAppKey.class)) {
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
                String appKey = checkResult.getClaims().getSubject();
                return appKey;
            }
        }
        return null;
    }
}
