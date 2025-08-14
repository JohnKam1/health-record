package com.Hootsybit.exception;

public class TokenValidationException extends RuntimeException {

    // todo 改为枚举类报错
    
    public TokenValidationException(String message) {
        super(message);
    }
    
    public TokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}