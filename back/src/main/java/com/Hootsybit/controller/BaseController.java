package com.Hootsybit.controller;

import com.Hootsybit.config.JwtInterceptor;

/**
 * Controller基类
 */
public class BaseController {

    /**
     * 获取用户id
     *
     * @return
     */
    public static Long getUserId() {
        return Long.valueOf(JwtInterceptor.getCurrentUserId());
    }
}