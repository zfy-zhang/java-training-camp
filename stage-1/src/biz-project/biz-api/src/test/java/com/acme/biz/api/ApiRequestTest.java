package com.acme.biz.api;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class ApiRequestTest extends BaseTest {

    @Test
    public void testValidateBody() {
        ApiRequest request = new ApiRequest();

        Set<ConstraintViolation<ApiRequest>> constraintViolations = validate(request);

        constraintViolations.forEach(cv -> System.out.println(cv.getMessage()));
    }
}
