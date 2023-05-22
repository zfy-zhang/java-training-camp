package com.acme.biz.web.mvc.exception;

import com.acme.biz.api.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@RestControllerAdvice
public class ExceptionHandlerConfiguration {

    @ExceptionHandler(javax.validation.ValidationException.class)
    public ApiResponse<Void> onValidationException(ValidationException e) {
        return ApiResponse.failed(null, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.failed(null, e.getMessage());
    }
}
