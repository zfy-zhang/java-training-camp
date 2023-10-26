package com.acme.biz.web.servlet.filter;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 全局 {@link CircuitBreaker} Filter
 *
 * @Author zfy
 * @Date 2023/10/26
 **/
@WebFilter(filterName = "globalCircuitBreakerFilter", urlPatterns = "/*",
        dispatcherTypes = {
            DispatcherType.REQUEST
        })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalCircuitBreakerFilter implements Filter {

    private CircuitBreaker circuitBreaker;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);

        String filterName = filterConfig.getFilterName();
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("CircuitBreaker-" + filterName);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 粗粒度实现
        circuitBreaker.acquirePermission();
        long start = circuitBreaker.getCurrentTimestamp();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            long duration = circuitBreaker.getCurrentTimestamp() - start;
            circuitBreaker.onSuccess(duration, circuitBreaker.getTimestampUnit());
        } catch (Throwable e) {
            // Do not handle java.lang.Error
            long duration = circuitBreaker.getCurrentTimestamp() - start;
            circuitBreaker.onError(duration, circuitBreaker.getTimestampUnit(), e);
            throw e;
        }
    }

//        try {
//            circuitBreaker.decorateCheckedRunnable(() -> chain.doFilter(request, response)).run();
//        } catch (Throwable e) {
//            throw new ServletException(e);
//        }

    @Override
    public void destroy() {
    }
}
