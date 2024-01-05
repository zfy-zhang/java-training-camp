package com.acme.biz.data.fault.tolerance.mybatis;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Executor 静态拦截 {@link CircuitBreaker} 实现（包装器）
 *
 * @Author zfy
 * @Date 2024/1/5
 **/
public class CircuitBreakerExecutorDecorator extends ExecutorDecorator{
    @Override
    protected void before(MappedStatement mappedStatement) {
        String resourceName = getResourceName(mappedStatement);
    }

    @Override
    protected void after(MappedStatement mappedStatement) {
        String resourceName = getResourceName(mappedStatement);
    }

    private String getResourceName(MappedStatement ms) {
        return ms.getId();
    }
}
