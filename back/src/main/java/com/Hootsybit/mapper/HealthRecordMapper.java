package com.Hootsybit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Hootsybit.pojo.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {
}