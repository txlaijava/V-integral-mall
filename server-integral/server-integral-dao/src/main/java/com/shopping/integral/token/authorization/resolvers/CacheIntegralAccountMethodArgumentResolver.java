package com.shopping.integral.token.authorization.resolvers;


import com.shopping.base.utils.BeanUtilEx;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.constant.RedisCons;
import com.shopping.integral.dao.IntegralAccountDAO;
import com.shopping.integral.dao.model.IntegralAccount;
import com.shopping.integral.token.authorization.annotation.CacheIntegralAccount;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.config.Constants;
import com.shopping.integral.token.model.CheckResult;
import com.shopping.integral.util.BeanUtil;
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
 * @see CacheIntegralAccount
 */
@Component
public class CacheIntegralAccountMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IntegralAccountDAO integralAccountDAO;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CacheIntegralAccount.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authHeader = webRequest.getHeader("Token");
        System.err.println("Token ===============> "+authHeader+" <===============");
        if (StringUtils.isNotBlank(authHeader)) {
            CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
            if (checkResult.isSuccess()) {
                String key = checkResult.getClaims().getId();
                if(Utils.isNotEmpty(redisService.hmget(RedisCons.INTEGRAL_PLATFORM_ACCOUNT + key))){
                    IntegralAccount account = BeanUtilEx.map2Bean(redisService.hmget(RedisCons.INTEGRAL_PLATFORM_ACCOUNT + key), IntegralAccount.class);
                    return account;
                }else{
                    // 如果redis数据过期了。继续存进去,因为JWT还没有失效
                    IntegralAccount integralAccount = integralAccountDAO.findById(CommUtils.null2Long(key));
                    redisService.hmset(RedisCons.INTEGRAL_PLATFORM_ACCOUNT + CommUtils.null2String(integralAccount.getId()), BeanUtil.transBean2Map(integralAccount), Constants.JWT_TTL);
                    return integralAccount;
                }
            }
        }
        return null;
    }
}
