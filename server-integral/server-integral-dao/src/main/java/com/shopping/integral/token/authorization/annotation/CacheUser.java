package com.shopping.integral.token.authorization.annotation;

import com.shopping.integral.token.authorization.resolvers.CacheUserMethodArgumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Controller的方法参数中使用此注解，该方法在映射时会注入当前缓存的登录User对象
 * @see CacheUserMethodArgumentResolver
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheUser {
}
