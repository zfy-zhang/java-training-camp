package com.acme.biz.web.mvc.controller;

import com.acme.biz.api.ApiResponse;
import com.acme.biz.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@RestController
@RequestMapping("/echo")
public class EchoController {

    @Value("${server.port:8080}")
    private Integer port;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/user")
    public ApiResponse<String> echo(@RequestBody User user) {
        return ApiResponse.ok(user.getName());
    }

    @GetMapping("/rest-template/{name}")
    public ApiResponse<String> restTemplateCall(@PathVariable String name) {
        String url = "http://127.0.0.1:{port}/echo/user";
        User user = new User();
        user.setName(name);
        ApiResponse response = restTemplate.postForObject(url, user, ApiResponse.class, port);
        return response;
    }
}
