package com.acme.biz.api.fegin.config;

import com.acme.biz.api.micrometer.binder.feign.FeignCallCounterMetrics;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 默认 FeignClients 配置（扩展 Spring Cloud FeignClientsConfiguration）
 * @author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since: 1.0.0
 */
@Import(FeignClientProperties.FeignClientConfiguration.class)
public class DefaultFeignClientsConfiguration {

    @Bean
    public FeignCallCounterMetrics feignCallCounterMetrics() {
        return new FeignCallCounterMetrics();
    }
}
