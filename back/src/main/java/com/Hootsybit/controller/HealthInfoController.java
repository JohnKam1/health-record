package com.Hootsybit.controller;

import com.Hootsybit.common.Result;
import com.Hootsybit.pojo.entity.HealthInfo;
import com.Hootsybit.service.HealthInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-info")
@Tag(name = "健康信息管理", description = "用户健康信息相关的API")
public class HealthInfoController extends BaseController {

    @Autowired
    private HealthInfoService healthInfoService;

    @GetMapping
    @Operation(summary = "获取当前用户健康信息", description = "获取当前登录用户的健康信息")
    public Result<HealthInfo> getCurrentUserHealthInfo() {
        // 获取当前用户ID
        Long userId = getUserId();
        HealthInfo healthInfo = healthInfoService.getHealthInfoByUserId(userId);
        return Result.success(healthInfo);
    }

    @PostMapping
    @Operation(summary = "保存或更新用户健康信息", description = "保存或更新当前登录用户的健康信息")
    public Result<Boolean> saveOrUpdateHealthInfo(@RequestBody HealthInfo healthInfo) {
        // 获取当前用户ID
        Long userId = getUserId();
        healthInfo.setUserId(userId);
        
        boolean result = healthInfoService.saveOrUpdateHealthInfo(healthInfo);
        return Result.success(result);
    }
}