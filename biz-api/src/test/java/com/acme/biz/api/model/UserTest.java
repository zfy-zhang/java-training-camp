package com.acme.biz.api.model;

import com.acme.biz.api.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.*;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class UserTest extends BaseTest {

    @Test
    public void testValidateUser() {



        User user = new User();

        Set<ConstraintViolation<User>> validate = validate(user);

        validate.forEach(cv -> {
            System.out.println(cv.getMessage());
        });
    }
}
