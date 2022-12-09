package com.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.servlet.MultipartConfigElement;

/**
 * 应用启动器
 *
 * @since 1.0.0.RELEASE
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages={"com.shopping"})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 上传文件新增临时存放路径
     报错信息： java.io.IOException: java.io.FileNotFoundException:
     /tmp/tomcat.273391201583741210.8080/work/Tomcat/localhost/ROOT/tmp/source/IMG_20160129_132623.jpg (No such file or directory)
     * @return
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/data/tmp");
        return factory.createMultipartConfig();
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

}
