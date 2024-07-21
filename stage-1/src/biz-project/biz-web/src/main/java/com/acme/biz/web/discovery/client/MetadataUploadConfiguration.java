package com.acme.biz.web.discovery.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@ConditionalOnClass(name = {
        "org.springframework.cloud.client.discovery.DiscoveryClient",
        "org.springframework.cloud.client.serviceregistry.ServiceRegistry"
})
@Configuration(proxyBeanMethods = false)
public class MetadataUploadConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MetadataUploadConfiguration.class);

    @Autowired
    private ServiceRegistry serviceRegistry;

    @Autowired
    private Registration registration;

    @Scheduled(fixedDelay = 5000, initialDelay = 10L)
    public void upload() {
        Map<String, String> metadata = registration.getMetadata();
        metadata.put("timestamp", String.valueOf(System.currentTimeMillis()));
        serviceRegistry.deregister(registration);
        serviceRegistry.register(registration);
        logger.info("Upload Registration's metadata");
    }
}
