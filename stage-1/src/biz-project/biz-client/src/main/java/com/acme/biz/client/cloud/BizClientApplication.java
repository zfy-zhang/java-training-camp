package com.acme.biz.client.cloud;

import com.acme.biz.api.interfaces.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * biz-client 应用启动类
 *
 * @Author zfy
 * @since 1.0.0
 **/
//@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient
//@EnableFeignClients(clients = UserRegistrationService.class, defaultConfiguration = DefaultFeignClientsConfiguration.class)
//@LoadBalancerClient(name = "user-service", configuration = CpuUsageBalancerConfiguration.class)
//@EnableScheduling
//@Import({MicrometerConfiguration.class, FeignCallCounterMetrics.class})
public class BizClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizClientApplication.class, args);
    }

//    @Autowired
//    private BizClientFeignController bizClientFeignController;
//
//    @Scheduled(fixedRate = 10 * 1000L)
//    public void registerUser() {
//        bizClientFeignController.registerUser();
//    }
}
