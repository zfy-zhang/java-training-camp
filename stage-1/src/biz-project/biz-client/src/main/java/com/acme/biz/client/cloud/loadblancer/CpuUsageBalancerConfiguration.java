package com.acme.biz.client.cloud.loadblancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Description:
 * @Author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @Modify:
 * @since:
 */
@Configuration(proxyBeanMethods = false)
public class CpuUsageBalancerConfiguration {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> cpuUsageLoadBalance(Environment environment,
                                                                    LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CpuUsageLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
