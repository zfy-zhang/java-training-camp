package com.acme.biz.api.exeception;

/**
 * @Description: TODO Comment
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class UserException extends RuntimeException {

    public UserException() {
        // 序列化使用
    }

    public UserException(String message, Throwable cause) {
        super(message, cause, false, false);
    }
}
