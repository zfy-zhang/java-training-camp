package com.acme.biz.api;

import com.acme.biz.api.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;

import javax.validation.*;
import javax.validation.bootstrap.GenericBootstrap;
import java.util.Set;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public abstract class BaseTest {

    private Validator validator;

    @BeforeEach
    public void init() {
        GenericBootstrap bootstrap = Validation.byDefaultProvider();

        Configuration<?> configuration = bootstrap.configure();

        MessageInterpolator targetInterpolator = configuration.getDefaultMessageInterpolator();

        configuration.messageInterpolator(new LocaleContextMessageInterpolator(targetInterpolator));

        ValidatorFactory validatorFactory = configuration.buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();

        this.validator = validator;
    }

    protected <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return validator.validate(object, groups);
    }
}
