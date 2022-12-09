package com.shopping;

import com.shopping.integral.filter.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 关键，将拦截器作为bean写入配置中 （拦截器中注入Server为NULL）
     * @return
     */
    @Bean
    public SecurityFilter securityFilter(){
        return new SecurityFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        // addPathPatterns用于添加拦截规则
        // excludePathPatterns用户排除拦截
        // 对来自/** 全路径请求进行拦截
        registry.addInterceptor(securityFilter()).addPathPatterns("/**")
        .excludePathPatterns("/login","/common/**","/register/setIntegralAc","/druid");
    }
 
}



