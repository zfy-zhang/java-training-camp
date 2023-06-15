package com.acme.biz.api.micrometer.binder.redis.timer;

import com.acme.biz.api.micrometer.binder.redis.AbstractRedisMetrics;
import com.acme.biz.api.redis.invocation.RedisCommandExecutionChain;
import com.acme.biz.api.redis.invocation.RedisCommandExecutionContext;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.RedisStringCommands;

import java.lang.reflect.Method;

/**
 * 记录{@link RedisStringCommands#set}方法调用时长
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since 1.0.0
 **/
public class RedisSetTimerMetrics extends AbstractRedisMetrics<Timer> {

    /**
     * set timer 模板
     */
    private final String GENERIC_TIMER_NAME = "TIMER.redis.value.keys.set";

    @Override
    protected boolean determineMonitoring(RedisCommandExecutionContext context) {
        Method method = context.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();
        String methodName = method.getName();
        return RedisStringCommands.class.equals(declaringClass) && "set".equals(methodName) && method.getParameterCount()  == 2;
    }

    @Override
    public Object execute(RedisCommandExecutionContext executionContext, RedisCommandExecutionChain executionChain) {
        //判断是否拦截
        if (!determineMonitoring(executionContext)) {
            return executionChain.execute(executionContext);
        }
        // 构建 Meter
        final Timer timer = builderMeter(executionContext, getMeterRegistry());
        return timer.record(() -> executionChain.execute(executionContext));
    }

    @Override
    protected Timer builderMeter(RedisCommandExecutionContext executionContext, MeterRegistry meterRegistry) {
        Object key = getKey(executionContext, 0);
        return Timer.builder(String.format(GENERIC_TIMER_NAME))
                .tag("key", key.toString())
                .register(meterRegistry);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
