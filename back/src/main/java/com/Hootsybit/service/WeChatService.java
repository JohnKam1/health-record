package com.Hootsybit.service;

public interface WeChatService {
    /**
     * 获取微信用户唯一id
     *
     * @return
     */
    String wechatLogin(String code);
}
