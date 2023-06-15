package com.acme.biz.api.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since 1.0.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
        RedisCommandInterceptorSelector.class,
        RedisTemplateBeanPostProcessor.class
})
public @interface EnableRedisIntercepting {
}
