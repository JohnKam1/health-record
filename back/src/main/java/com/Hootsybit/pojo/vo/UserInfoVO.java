package com.Hootsybit.pojo.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    /**
     * 头像路径
     */
    private String avatar;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 信息是否完整
     */
    private Boolean complete;
}