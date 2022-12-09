package com.shopping.integral.token.authorization.resolvers;

import com.shopping.base.utils.BeanUtilEx;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.constant.RedisCons;
import com.shopping.integral.dao.IntegralUserDAO;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.token.authorization.annotation.CacheUser;
import com.shopping.integral.token.authorization.manager.JwtTokenUtils;
import com.shopping.integral.token.model.CheckResult;
import com.shopping.integral.util.BeanUtil;
import lombok.extern.log4j.Log4j2;
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
 * @see CacheUser
 */
@Log4j2
@Component
public class CacheUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisService redisService;

    @Autowired
    IntegralUserDAO integralUserDAO;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CacheUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authHeader = webRequest.getHeader("Token");
        log.info("========authHeader=======>"+authHeader);
        if (StringUtils.isNotBlank(authHeader)) {
            CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
            if (checkResult.isSuccess()) {
                String key = checkResult.getClaims().getId();
                IntegralUser user = null;
                try {
                    user = BeanUtilEx.map2Bean(redisService.hmget(RedisCons.INTEGRAL_APP_USER + key), IntegralUser.class);
                } catch (Exception ex){
                    // 删除缓存数据
                    redisService.del(RedisCons.INTEGRAL_APP_USER + key);
                    //设置user到缓存
                    user = this.integralUserDAO.findById(CommUtils.null2Long(key));
                    redisService.hmset(RedisCons.INTEGRAL_APP_USER + CommUtils.null2String(user.getId()), BeanUtil.transBean2Map(user));
                    log.warn("缓存用户数据转换失败，用户ID：" + key, ex);
                }
                if(Utils.isNotEmpty(user) && Utils.isNotEmpty(user.getId())){
                    return user;
                }else{
                    return null;
                }
            } else {
                log.debug("Token验证失败");
            }
        }
        return null;
    }
}