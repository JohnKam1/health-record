package com.Hootsybit.exception;

// 创建错误码枚举类
public enum ErrorCode {
    TOKEN_INVALID(1001, "Token无效或已过期"),
    TOKEN_EXPIRED(1002, "Token已过期"),
    TOKEN_MISSING(1003, "缺少Token"),
    USER_NOT_FOUND(1004, "未获取到用户信息，请重试"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}