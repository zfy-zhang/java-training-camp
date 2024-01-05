package com.acme.biz.data.fault.tolerance.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.List;
import java.util.Properties;

/**
 * Mybatis {@link Interceptor} resilience4j 实现
 *
 * @Author zfy
 * @Date 2024/1/5
 **/
public class Resilience4jMyBatisInterceptor implements Interceptor {

    private List<ExecutorDecorator> decorators;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 如果当前 Interceptor 采用 Interceptor#plugin 默认实现，即调用 Plugin.wrap(target, this)，当前方法会被执行
        // 如果当前 Interceptor plugin 方法实现采用静态拦截（Wrapper）的方式，那么，本方法不会被执行
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            decorateExecutor((Executor) target);
        }
        return Interceptor.super.plugin(target);
    }

    public Executor decorateExecutor(Executor target) {
        return new ExecutorDecorators(target, decorators);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    public void setDecorators(List<ExecutorDecorator> decorators) {
        this.decorators = decorators;
    }
}
