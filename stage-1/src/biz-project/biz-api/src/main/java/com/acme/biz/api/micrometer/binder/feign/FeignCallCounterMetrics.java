package com.acme.biz.api.micrometer.binder.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

import static com.acme.biz.api.micrometer.Micrometers.async;


/**
 * Feign 调用计数 Metrics
 * @author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since: 1.0.0
 */
public class FeignCallCounterMetrics implements RequestInterceptor, MeterBinder {

    private static MeterRegistry registry;

    private static Counter totalCounter;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 异步执行
        async(() -> {
            // 方法统计
            String feignMethod = requestTemplate.methodMetadata().configKey();
            Counter counter = Counter.builder("feign.call")
                    .tags("method", feignMethod)            // Feign 调用方法（接口 + 方法） Tag
                    .register(registry);
            counter.increment();
            // 全局统计
            totalCounter.increment();
        });
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.registry = meterRegistry;
        this.totalCounter = Counter.builder("fegin.total-calls")
                .register(registry);
    }

}
