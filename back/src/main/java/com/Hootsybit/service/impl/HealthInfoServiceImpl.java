package com.Hootsybit.service.impl;

import com.Hootsybit.mapper.HealthInfoMapper;
import com.Hootsybit.pojo.entity.HealthInfo;
import com.Hootsybit.service.HealthInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HealthInfoServiceImpl extends ServiceImpl<HealthInfoMapper, HealthInfo> implements HealthInfoService {

    @Override
    public HealthInfo getHealthInfoByUserId(Long userId) {
        QueryWrapper<HealthInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean saveOrUpdateHealthInfo(HealthInfo healthInfo) {
        // 设置更新时间
        healthInfo.setUpdateTime(LocalDateTime.now());
        
        // 查询是否已存在该用户的健康信息
        HealthInfo existingInfo = getHealthInfoByUserId(healthInfo.getUserId());
        
        if (existingInfo != null) {
            // 如果存在，则更新
            healthInfo.setId(existingInfo.getId());
            healthInfo.setCreateTime(existingInfo.getCreateTime());
            return this.updateById(healthInfo);
        } else {
            // 如果不存在，则新增
            healthInfo.setCreateTime(LocalDateTime.now());
            return this.save(healthInfo);
        }
    }
}