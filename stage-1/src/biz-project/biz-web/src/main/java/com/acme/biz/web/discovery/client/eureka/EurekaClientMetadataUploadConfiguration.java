package com.acme.biz.web.discovery.client.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.Map;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@ConditionalOnClass(name = "com.netflix.discovery.DiscoveryClient")
@Configuration(proxyBeanMethods = false)
public class EurekaClientMetadataUploadConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(EurekaClientMetadataUploadConfiguration.class);

    private static final boolean HOTSPOT_JVM = ClassUtils.isPresent("com.sum.management.OperationSystemMXBean", null);

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private ApplicationInfoManager applicationInfoManager;

    @PostConstruct
    public void init() {
        this.applicationInfoManager = eurekaClient.getApplicationInfoManager();
    }

    @Scheduled(fixedRate = 5000L, initialDelay = 10L)
    public void upload() {
        InstanceInfo instanceInfo = applicationInfoManager.getInfo();
        Map<String, String> metadata = instanceInfo.getMetadata();
        metadata.put("timestamp", String.valueOf(System.currentTimeMillis()));
        metadata.put("cpu-usage", String.valueOf(getCpuUsage()));
        instanceInfo.setIsDirty();
        logger.info("Upload Eureka InstanceInfo's metadata");
    }

    private Integer getCpuUsage() {
        if (HOTSPOT_JVM) {
            OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            Double usage = operatingSystemMXBean.getProcessCpuLoad() * 100 * 100;
            return usage.intValue();
        } else {
            return 0;
        }
    }

}
