package com.acme.biz.web;

//import com.acme.biz.api.redis.EnableRedisIntercepting;
import com.acme.biz.web.servlet.mvc.interceptor.ResourceBulkheadHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
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
})
//@EnableRedisIntercepting
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
