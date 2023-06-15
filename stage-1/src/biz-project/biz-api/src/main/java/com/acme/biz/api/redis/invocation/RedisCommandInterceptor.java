package com.acme.biz.api.redis.invocation;

import org.springframework.core.Ordered;

/**
 * Redis Command 拦截器
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since 1.0.0
 **/
public interface RedisCommandInterceptor extends Ordered {

    Object execute(RedisCommandExecutionContext executionContext, RedisCommandExecutionChain executionChain);

    default int getOrder() {
        return 100;
    }

}
