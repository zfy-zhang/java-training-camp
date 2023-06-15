package com.acme.biz.api.micrometer.binder.redis;

import com.acme.biz.api.redis.invocation.RedisCommandExecutionChain;
import com.acme.biz.api.redis.invocation.RedisCommandExecutionContext;
import com.acme.biz.api.redis.invocation.RedisCommandInterceptor;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

import java.util.Map;

public abstract class AbstractRedisMetrics<M extends Meter> implements RedisCommandInterceptor, MeterBinder {

    private MeterRegistry meterRegistry;

    @Override
    public Object execute(RedisCommandExecutionContext executionContext, RedisCommandExecutionChain executionChain) {
        if (!determineMonitoring(executionContext)) {
            return executionChain.execute(executionContext);
        }
        doRecordBeforeExecute(builderMeter(executionContext, this.meterRegistry), executionContext);
        // 链路继续传递下去
        Object result = executionChain.execute(executionContext);
        doRecordAfterExecute(builderMeter(executionContext, this.meterRegistry), executionContext);
        return result;
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     *
     * @param context 执行上下文
     * @return 判断当前操作是否应该被监控
     */
    protected abstract boolean determineMonitoring(RedisCommandExecutionContext context);

    /**
     * 根据执行上下文构建 {@link Meter}, 并注册到 {@link MeterRegistry}
     *
     * @param executionContext redis 执行上下文
     * @param meterRegistry 注册上下文
     * @return {@link Meter} 具体实例
     */
    protected abstract M builderMeter(RedisCommandExecutionContext executionContext, MeterRegistry meterRegistry);

    /**
     * 获取 Redis 操作的 key，从参数列表中指定位置参数，反序列化得到
     * @param executionContext 执行上下文
     * @param keyParameterIndex key 参数位置
     * @return Redis 操作 key
     */
    protected Object getKey(RedisCommandExecutionContext executionContext, int keyParameterIndex) {
        final String keyAttribute = "redis.execute.key";
        Map<String, Object> attributes = executionContext.getAttributes();
        if (attributes.containsKey(keyAttribute)) {
            return attributes.get(keyAttribute);
        }

        Object[] parameters = executionContext.getParameters();
        if (parameters == null && parameters.length == 0) {
            return null; // no key
        }

        if (keyParameterIndex < 0 || keyParameterIndex >= parameters.length) {
            throw new IllegalArgumentException("illegal key parameter index : " + keyParameterIndex);
        }

        byte[] bytes = (byte[]) parameters[keyParameterIndex];
        Object key = executionContext.getKeySerializer().deserialize(bytes);
        executionContext.setAttribute(keyAttribute, key);
        return key;
    }

    /**
     * 执行前的记录操作，通常可能是数据初始化
     * @param meter meter
     * @param executionContext 执行上下文
     */
    protected void doRecordAfterExecute(M meter, RedisCommandExecutionContext executionContext) {
    }

    /**
     * 执行后的记录操作，一般是记录结果，应该考虑异步操作
     * @param meter meter
     * @param executionContext 执行上下文
     */
    protected void doRecordBeforeExecute(M meter, RedisCommandExecutionContext executionContext) {

    }

    public MeterRegistry getMeterRegistry() {
        return meterRegistry;
    }

}
