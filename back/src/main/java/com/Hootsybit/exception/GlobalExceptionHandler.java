package com.Hootsybit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public Map<String, Object> handleGlobalException(GlobalException e) {
        log.error("GlobalException occurred: ", e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", e.getCode());
        result.put("message", e.getMessage());
        result.put("data", null);
        result.put("timestamp", System.currentTimeMillis());
        
        return result;
    }
}