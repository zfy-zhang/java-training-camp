package com.acme.biz.client.loadbalancer.ribbon;

import com.acme.biz.client.loadbalancer.ribbon.eureka.EurekaDiscoveryEventServerListUpdater;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.ServerListUpdater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link com.acme.biz.api.interfaces.UserService}
 * {@link RibbonClientConfiguration}
 *
 * @Description:
 * @Author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @Modify:
 * @see RibbonClientConfiguration
 * @since:
 */
public class UserServiceRibbonClientConfiguration {

    @Bean
    @ConditionalOnClass(EurekaClient.class)
    @ConditionalOnMissingBean
    public ServerListUpdater eurekaDiscoveryEventServerListUpdater(EurekaClient eurekaClient) {
        return new EurekaDiscoveryEventServerListUpdater(eurekaClient);
    }
}
