package com.Hootsybit.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int code;
    private String msg;
    private T data;
    
    public Result() {
    }
    
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }
    
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }
    
    public static <T> Result<T> error() {
        return new Result<>(500, "error", null);
    }
    
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }
    
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }
    
    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(500, msg, data);
    }
    
    public static <T> Result<T> error(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}