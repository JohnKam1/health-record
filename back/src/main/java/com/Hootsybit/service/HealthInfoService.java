package com.Hootsybit.service;

import com.Hootsybit.pojo.entity.HealthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface HealthInfoService extends IService<HealthInfo> {

    /**
     * 根据用户ID获取健康信息
     *
     * @param userId 用户ID
     * @return 健康信息实体
     */
    HealthInfo getHealthInfoByUserId(Long userId);

    /**
     * 保存或更新用户健康信息
     *
     * @param healthInfo 健康信息实体
     * @return 是否操作成功
     */
    boolean saveOrUpdateHealthInfo(HealthInfo healthInfo);
}