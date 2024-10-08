package com.acme.biz.api.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cloud.client.serviceregistry.Registration;

import static java.util.Arrays.asList;

/**
 * Micrometer 配置
 * @author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since: 1.0.0
 */
public class MicrometerConfiguration implements MeterRegistryCustomizer {

    @Value("${spring.application.name:default}")
    private String applicationName;

    @Autowired
    Registration registration;

    /**
     * 应用部署环境：TEST（测试）、STAGING（预发）、PROD（生产）
     */
    @Value("${env:TEST}")
    private String env;

    @Override
    public void customize(MeterRegistry registry) {
        registry.config().commonTags(asList(
                Tag.of("application", applicationName),         // 应用维度的 Tag
                Tag.of("host", registration.getHost()),         // 应用 Host 的 Tag
                Tag.of("env", env)                              // 应用部署环境
        ));
    }
}
