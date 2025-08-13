package com.Hootsybit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("health_record")
public class HealthRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private LocalDateTime recordDate;
    private Double temperature;
    private String symptoms;
    private String medicine;
    private String note;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}