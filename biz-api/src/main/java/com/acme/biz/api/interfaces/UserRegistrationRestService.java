package com.acme.biz.api.interfaces;

import com.acme.biz.api.ApiRequest;
import com.acme.biz.api.ApiResponse;
import com.acme.biz.api.model.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 用户注册服务 Rest 接口（Open Feign、 Spring MVC 等公用）
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@FeignClient("${user-registration.reset-service.name}")
@DubboService
@Deprecated
//@RequestMapping("/api/user")
public interface UserRegistrationRestService {

////    @PostMapping("/register/v1")
//    @PostMapping(value = "/user/register", produces = "application/json;v=1")
//    ApiResponse<Boolean> registerUser(@RequestBody @Validated User user);
//
////    @PostMapping("/register/v2")
//    @PostMapping(value = "/user/register", produces = "application/json;v=1")
//    ApiResponse<Boolean>  registerUser(@RequestBody @Validated ApiRequest<User> user);

    @PostMapping(path = "/user/register", produces = "application/json;v=1") // V1
    ApiResponse<Boolean> registerUser(@RequestBody @Validated ApiRequest<User> userRequest);

    @PostMapping(path = "/user/register", produces = "application/json;v=2") // V2
    ApiResponse<Boolean> registerUser(@RequestBody @Validated User user);
}
