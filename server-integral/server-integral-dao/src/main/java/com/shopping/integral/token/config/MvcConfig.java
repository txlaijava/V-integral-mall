package com.shopping.integral.token.config;


import com.shopping.integral.token.authorization.resolvers.CacheUserMethodArgumentResolver;
import com.shopping.integral.token.authorization.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 配置类，增加自定义拦截器和解析器
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Autowired
    private CacheUserMethodArgumentResolver cacheUserMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
        argumentResolvers.add(cacheUserMethodArgumentResolver);
    }
}
