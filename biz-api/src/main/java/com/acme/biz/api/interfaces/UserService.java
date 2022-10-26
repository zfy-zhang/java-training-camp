package com.acme.biz.api.interfaces;

import com.acme.biz.api.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Description: 用户服务接口
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 * @deprecated
 */
@FeignClient("${user.service.name}")
@RequestMapping("/user")
@Deprecated
public interface UserService {

    @PostMapping("/register")
    Boolean registerUser(User user);

    @PostMapping("/login")
    @Deprecated
    User login(Map<String, Object> context);

}
