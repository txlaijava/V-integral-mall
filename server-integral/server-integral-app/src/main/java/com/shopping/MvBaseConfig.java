package com.shopping;


import com.shopping.integral.token.authorization.resolvers.CurrentIntegralAcApplyMethodArgumentResolver;
import com.shopping.integral.token.authorization.resolvers.CacheIntegralAccountMethodArgumentResolver;
import com.shopping.integral.token.authorization.resolvers.CurrentAppKeyMethodArgumentResolver;
import com.shopping.integral.token.authorization.resolvers.CurrentIntegralAccountMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 配置类，增加自定义拦截器和解析器
 */
@Configuration
public class MvBaseConfig extends WebMvcConfigurerAdapter {

    @Bean
    public CacheIntegralAccountMethodArgumentResolver cacheIntegralAccountMethodArgumentResolver(){
        return new CacheIntegralAccountMethodArgumentResolver();
    }

    @Bean
    public CurrentIntegralAcApplyMethodArgumentResolver cacheIntegralAcApplyMethodArgumentResolver(){
        return new CurrentIntegralAcApplyMethodArgumentResolver();
    }

    @Bean
    public CurrentIntegralAccountMethodArgumentResolver currentIntegralAccountMethodArgumentResolver(){
        return new CurrentIntegralAccountMethodArgumentResolver();
    }

    @Bean
    public CurrentAppKeyMethodArgumentResolver currentAppKeyMethodArgumentResolver(){
        return new CurrentAppKeyMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(cacheIntegralAccountMethodArgumentResolver());
        argumentResolvers.add(cacheIntegralAcApplyMethodArgumentResolver());
        argumentResolvers.add(currentIntegralAccountMethodArgumentResolver());
        argumentResolvers.add(currentAppKeyMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}
