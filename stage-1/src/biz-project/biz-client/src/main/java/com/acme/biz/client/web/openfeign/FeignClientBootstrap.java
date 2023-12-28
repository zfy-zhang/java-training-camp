package com.acme.biz.client.web.openfeign;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientSpecification;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Feign 客户端引导类
 *
 * @Author zfy
 * @Date 2023/12/27
 **/
@EnableFeignClients(clients = UserRegistrationService.class)
public class FeignClientBootstrap {

    @Autowired(required = false)
    private List<FeignClientSpecification> configurations = new ArrayList<>();

    @Bean
    public FeignContext feignContext() {
        FeignContext feignContext = new FeignContext();
        feignContext.setConfigurations(this.configurations);
        return feignContext;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(FeignClientBootstrap.class);

        context.refresh();

        UserRegistrationService userRegistrationService = context.getBean(UserRegistrationService.class);

        User user = new User();
        user.setId(1L);
        user.setName("elisha");
        userRegistrationService.registerUser(user);

        context.close();
    }
}
