package com.Hootsybit.exception;

// 修改类名为GlobalException，并添加code属性
public class GlobalException extends RuntimeException {
    private int code;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public GlobalException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}