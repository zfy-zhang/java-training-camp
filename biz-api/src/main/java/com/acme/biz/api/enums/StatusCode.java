package com.acme.biz.api.enums;

import org.springframework.http.HttpStatus;

/**
 * @Description: 状态码
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 * @see HttpStatus
 */
public enum StatusCode {

    OK(0, "OK") {
        @Override
        public String getMessage() {
            return super.message;
        }
    },


    FAILED(-1, "Failed"),

    CONTINUE(1, "{status-code.continue}");

    private final int code;

    private final String message; // 可能需要支持国际化

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return getLocalizedMessage();
    }

    public String getLocalizedMessage() {
        // FIXME 增加国际化支持
        // 如果 message 是占位符，翻译成当前 message text
        // 否则，直接返回 message
        return message;
    }
}

class MyEnum {

    public static final MyEnum ONE = new MyEnum() { // # 匿名类

        @Override
        public String getValue() {
            return "ONE";
        }
    };

    private MyEnum() {

    }

    public String getValue() {
        return "";
    }
}
