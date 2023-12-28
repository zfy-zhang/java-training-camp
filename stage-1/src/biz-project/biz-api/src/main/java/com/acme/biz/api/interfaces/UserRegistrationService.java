package com.acme.biz.api.interfaces;

import com.acme.biz.api.exeception.UserException;
import com.acme.biz.api.model.User;
import com.acme.biz.api.openfeign.UserServiceFeignClientConfiguration;
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
@FeignClient(name = "user-service", configuration = UserServiceFeignClientConfiguration.class)
@DubboService
public interface UserRegistrationService {

    @PostMapping(value = "/user/register", produces = "application/json;v=3") // V3
    Boolean registerUser(@RequestBody @Validated @Valid User user) throws UserException;


}
