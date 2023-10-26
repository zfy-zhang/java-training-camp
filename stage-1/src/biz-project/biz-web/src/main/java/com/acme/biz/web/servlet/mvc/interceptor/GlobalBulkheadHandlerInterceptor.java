package com.acme.biz.web.servlet.mvc.interceptor;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.internal.SemaphoreBulkhead;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Semaphore;

/**
 * 全局 Spring Web MVC 限流
 *
 * @Author zfy
 * @Date 2023/10/26
 * @see Bulkhead
 * @see Semaphore
 * @see SemaphoreBulkhead
 **/
public class GlobalBulkheadHandlerInterceptor implements HandlerInterceptor, InitializingBean, DisposableBean {

    private Bulkhead bulkhead;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否需要限流
        // 正常执行 postHandle 方法
        // 异常执行 afterCompletion 方法
        bulkhead.acquirePermission();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 记录
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除状态
        bulkhead.releasePermission();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BulkheadConfig bulkheadConfig = BulkheadConfig.custom().build();
        this.bulkhead = Bulkhead.of("globalBulkheadHandlerInterceptor", bulkheadConfig);
    }

    @Override
    public void destroy() throws Exception {
    }
}
