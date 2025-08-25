package com.Hootsybit.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("health_info")
@Accessors(chain = true)
public class HealthInfo {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID，关联用户表
     */
    private Long userId;

    /**
     * 身高(cm)
     */
    private Double height;

    /**
     * 体重(kg)
     */
    private Double weight;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 是否吸烟(0-否, 1-是)
     */
    private Integer isSmoke;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}