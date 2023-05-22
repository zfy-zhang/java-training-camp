package com.acme.biz.api;

import com.acme.biz.api.enums.StatusCode;

import javax.validation.Valid;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class ApiResponse<T> {

    private int code;

    private String message;

    @Valid
    private T body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static <T> ApiResponse<T> ok(T body) {
        return of(body, StatusCode.OK);
    }

    public static <T> ApiResponse<T> failed(T body) {
        return of(body, StatusCode.FAILED);
    }

    public static <T> ApiResponse<T> failed(T body, String errorMessage) {
        ApiResponse<T> response = of(body, StatusCode.FAILED);
        response.setMessage(errorMessage);
        return response;
    }

    public static <T> ApiResponse<T> of(T body, StatusCode statusCode) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setBody(body);
        response.setCode(statusCode.getCode());
        response.setMessage(statusCode.getLocalizedMessage());
        return response;
    }

    public static class Builder<T> {

        private int code;

        private String message;

        public Builder<T> code(int code) {
            this.code = code;
            return this;
        }

    }
}
