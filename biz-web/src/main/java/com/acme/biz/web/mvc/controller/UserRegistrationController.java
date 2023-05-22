package com.acme.biz.web.mvc.controller;

import com.acme.biz.api.interfaces.UserRegistrationService;
import com.acme.biz.api.model.User;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@RestController
public class UserRegistrationController implements UserRegistrationService {

    // REST -> { body : {}}

    @Override
    @ResponseBody
    public Boolean registerUser(User user) {
        return Boolean.TRUE;
    }
}