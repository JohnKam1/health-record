package com.Hootsybit.entity;

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
    @TableId(type = IdType.AUTO)
    private Long id;
    private String openid;
    private String name;
    private Integer age;
    private String gender;
    private Double height;
    private Double weight;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}