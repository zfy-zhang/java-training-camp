package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@FeignClient("${user-login.service.name}")
@RequestMapping("/user")
public interface UserLoginService {

    @PostMapping("/login")
    @Deprecated
    User login(Map<String, Object> context);
}
