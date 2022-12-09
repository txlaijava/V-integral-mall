package com.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

/**
 * 应用启动器
 *
 * @since 1.0.0.RELEASE
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {


    public static void main(String[] args) throws Exception {
        System.setProperty("dubbo.application.logger","log4j2");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

}
