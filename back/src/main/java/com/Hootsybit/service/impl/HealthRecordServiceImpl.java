package com.Hootsybit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Hootsybit.pojo.entity.HealthRecord;
import com.Hootsybit.mapper.HealthRecordMapper;
import com.Hootsybit.service.HealthRecordService;
import org.springframework.stereotype.Service;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord> implements HealthRecordService {
}