package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @Description: 用户注册服务 接口（Open Feign、Dubbo 等公用）
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@FeignClient("${user-register.service.name}")
@RequestMapping("/user")
@DubboService
public interface UserRegistrationService {

    @PostMapping("/register")
    Boolean registerUser(@RequestBody @Validated @Valid User user);
}
