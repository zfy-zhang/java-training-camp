package com.acme.biz.web.servlet.filter;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.catalina.core.ApplicationFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源 {@link CircuitBreaker} Filter（细粒度）
 *
 * @Author zfy
 * @Date 2023/10/26
 **/
@WebFilter(filterName = "resourceCircuitBreakerFilter", urlPatterns = "/*",
        dispatcherTypes = {
            DispatcherType.REQUEST
        })
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ResourceCircuitBreakerFilter implements Filter {

    /**
     * org.apache.catalina.core.ApplicationFilterFactory#createFilterChain(javax.servlet.ServletRequest, org.apache.catalina.Wrapper, javax.servlet.Servlet)
     */
    private final static String FILTER_CHAIN_IMPL_NAME = "org.apache.catalina.core.ApplicationFilterChain";

    private final static Class<?> FILTER_CHAIN_IMPL_CLASS = ClassUtils.resolveClassName(FILTER_CHAIN_IMPL_NAME, null);

    private CircuitBreakerRegistry circuitBreakerRegistry;

    private Map<String, CircuitBreaker> circuitBreakersMap;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .build();

        this.circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        this.circuitBreakersMap = new ConcurrentHashMap<>();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServlet = (HttpServletRequest) request;
        // /abc, /a/b/c
        String requestURI = httpServlet.getRequestURI();

        String servletName = getFilterName(httpServlet, chain);

        // Servlet 未提供 API 来解决 HTTP 请求映射到了哪个 Servlet

        CircuitBreaker circuitBreaker = circuitBreakersMap.computeIfAbsent(requestURI, circuitBreakerRegistry::circuitBreaker);
        try {
            circuitBreaker.decorateCheckedRunnable(() -> chain.doFilter(request, response)).run();
        } catch (Throwable e) {
            throw new ServletException(e);
        }
    }

    private String getFilterName(HttpServletRequest httpServlet, FilterChain chain) throws ServletException {
        String servletName = null;
        if (FILTER_CHAIN_IMPL_CLASS != null) {
            ApplicationFilterChain filterChain = (ApplicationFilterChain) chain;
            try {
                Field field = FILTER_CHAIN_IMPL_CLASS.getDeclaredField("servlet");
                field.setAccessible(true);
                Servlet servlet = (Servlet) field.get(filterChain);
                if (servlet != null) {
                    servletName = servlet.getServletConfig().getServletName();
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        return servletName;
    }

    @Override
    public void destroy() {
    }
}
