package com.acme.biz.client.web.openfeign;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import com.acme.biz.client.loadblancer.UserServiceLoadBalancerConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Feign 客户端引导类
 *
 * @Author zfy
 * @Date 2023/12/27
 **/
@EnableAutoConfiguration
@EnableFeignClients(clients = UserRegistrationService.class)
@LoadBalancerClient(name = "user-service", configuration = UserServiceLoadBalancerConfiguration.class)
public class FeignClientSpringBootBoostrap {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(FeignClientSpringBootBoostrap.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);

        UserRegistrationService userRegistrationService = context.getBean(UserRegistrationService.class);

        User user = new User();
        user.setId(1L);
        user.setName("elisha");
        System.out.println("userRegistrationService.registerUser : "+userRegistrationService.registerUser(user));

        context.close();
    }
}
