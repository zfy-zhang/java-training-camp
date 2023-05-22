package com.acme.biz.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@SpringBootApplication
@ServletComponentScan
public class BizWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizWebApplication.class, args);
    }
}