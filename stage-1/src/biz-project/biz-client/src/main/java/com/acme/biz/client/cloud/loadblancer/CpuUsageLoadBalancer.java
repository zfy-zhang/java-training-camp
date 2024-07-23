package com.acme.biz.client.cloud.loadblancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @Modify:
 * @since:
 */
public class CpuUsageLoadBalancer implements ReactorServiceInstanceLoadBalancer {


    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public CpuUsageLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier serviceInstanceListSupplier = serviceInstanceListSupplierProvider.getIfAvailable();
        Flux<List<ServiceInstance>> flux = serviceInstanceListSupplier.get();
        List<ServiceInstance> serviceInstances = flux.blockFirst();
        for (ServiceInstance serviceInstance : serviceInstances) {
            Map<String, String> metadata = serviceInstance.getMetadata();
            String cpuUsage = metadata.get("cpu-usage");
            Integer usage = Integer.valueOf(cpuUsage);
            // TODO 完成 CPU 利用率的算法实现
        }
        return Mono.justOrEmpty(new DefaultResponse(serviceInstances.get(0)));
    }


    /**
     * 兼容老版本
     *
     * @param request
     * @return
     */
//    public Mono<org.springframework.cloud.client.loadbalancer.reactive.Response<ServiceInstance>> choose(
//            org.springframework.cloud.client.loadbalancer.reactive.Request request) {
//        ServiceInstanceListSupplier serviceInstanceListSupplier = serviceInstanceListSupplierProvider.getIfAvailable();
//        Flux<List<ServiceInstance>> flux = serviceInstanceListSupplier.get();
//        List<ServiceInstance> serviceInstances = flux.blockFirst();
//        return Mono.justOrEmpty(new org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse(serviceInstances.get(0)));
//    }
}
