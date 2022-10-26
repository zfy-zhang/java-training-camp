package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@FeignClient("${user-register.service.name}")
@RequestMapping("/user")
public interface UserRegistrationService {

    @PostMapping("/register")
    Boolean registerUser(User user);
}
