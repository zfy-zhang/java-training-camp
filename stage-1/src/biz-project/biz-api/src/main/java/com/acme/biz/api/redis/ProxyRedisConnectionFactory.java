package com.acme.biz.api.redis;

import com.acme.biz.api.redis.invocation.RedisCommandExecutor;
import com.acme.biz.api.redis.invocation.RedisCommandInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * TODO
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since 1.0.0
 **/
public class ProxyRedisConnectionFactory implements RedisConnectionFactory {

    private final RedisConnectionFactory delegate;
    protected final ObjectProvider<RedisCommandInterceptor> interceptors;
    private final RedisSerializer<?> keySerializer;
    private final RedisSerializer<?> valueSerializer;

    public ProxyRedisConnectionFactory(RedisConnectionFactory delegate, ObjectProvider<RedisCommandInterceptor> interceptors, RedisSerializer<?> keySerializer, RedisSerializer<?> valueSerializer) {
        Assert.notNull(delegate, "redis connection factory cannot be null");
        this.delegate = delegate;
        this.interceptors = interceptors;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    @Override
    public RedisConnection getConnection() {
        RedisConnection connection = delegate.getConnection();
        return newRedisConnectionProxy(connection);
    }

    @Override
    public RedisClusterConnection getClusterConnection() {
        return null;
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return false;
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return null;
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return null;
    }

    private RedisConnection newRedisConnectionProxy(RedisConnection redisConnection) {
        ClassLoader classLoader = redisConnection.getClass().getClassLoader();
        InvocationHandler invocationHandler = new RedisCommandExecutor(redisConnection, this.interceptors, keySerializer, valueSerializer);
        return (RedisConnection) Proxy.newProxyInstance(classLoader, new Class[]{RedisConnection.class}, invocationHandler);
    }

}
