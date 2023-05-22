package com.acme.biz.web.servlet.idempotent;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class IdempotentFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 使用 HttpSession Id -> cookie 来自于 headers
        String token = request.getParameter("token");

        // HttpSession 与 Redis 打通，利用 Spring Session
        // HttpSession#setAttribute 它底层利用 Redis Hash
        HttpSession httpSession = request.getSession(false);

        Object value = httpSession.getAttribute(token);

        if (value != null) {
            // 抛出异常
            throw new ServletException("幂等性校验错误");
        }

        // 设置状态
        httpSession.setAttribute(token, token);


        try {
            // 处理
            filterChain.doFilter(request, response);
        } finally {
            // 移除状态
            httpSession.removeAttribute(token);
        }
    }
}
