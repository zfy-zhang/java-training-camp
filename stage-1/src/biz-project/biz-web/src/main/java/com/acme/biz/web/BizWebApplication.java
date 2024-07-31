package com.acme.biz.web;

import com.acme.biz.api.redis.EnableRedisIntercepting;
import com.acme.biz.web.i18.LocalValidatorFactoryBeanPostProcessor;
import com.acme.biz.web.servlet.mvc.interceptor.ResourceBulkheadHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@SpringBootApplication
@ServletComponentScan
@Import(value = {
        ResourceBulkheadHandlerInterceptor.class,
        LocalValidatorFactoryBeanPostProcessor.class,
})
@EnableDiscoveryClient // 激活服务发现客户端
@EnableScheduling
//@EnableRedisIntercepting
@EnableConfigurationProperties
public class BizWebApplication implements WebMvcConfigurer {

    @Autowired
    private List<HandlerInterceptor> handlerInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        handlerInterceptors.forEach(registry::addInterceptor);
    }

    public static void main(String[] args) {
        SpringApplication.run(BizWebApplication.class, args);
    }
}
