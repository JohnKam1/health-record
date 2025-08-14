package com.Hootsybit.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("user_info")
@Accessors(chain = true)
public class UserInfo {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信唯一id
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像路径
     */
    private String avatar;

    private String name;
    private Integer age;
    private String gender;
    private Double height;
    private Double weight;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}