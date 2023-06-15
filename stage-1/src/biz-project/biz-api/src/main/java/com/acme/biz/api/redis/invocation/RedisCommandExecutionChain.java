package com.acme.biz.api.redis.invocation;

/**
 * Redis Command 调用链路
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since 1.0.0
 **/
public interface RedisCommandExecutionChain {

    Object execute(RedisCommandExecutionContext executionContext);

}
