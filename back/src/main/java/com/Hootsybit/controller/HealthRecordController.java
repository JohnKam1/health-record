package com.Hootsybit.controller;

import com.Hootsybit.pojo.entity.HealthRecord;
import com.Hootsybit.service.HealthRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/record")
@Tag(name = "健康记录管理", description = "健康记录相关的API")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @PostMapping
    @Operation(summary = "创建健康记录", description = "根据提供的健康信息创建新的健康记录")
    public boolean createHealthRecord(@RequestBody HealthRecord healthRecord) {
        return healthRecordService.save(healthRecord);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取健康记录", description = "根据提供的记录ID获取健康记录详细信息")
    public HealthRecord getHealthRecord(@PathVariable Long id) {
        return healthRecordService.getById(id);
    }

    @GetMapping
    @Operation(summary = "获取所有健康记录", description = "获取系统中所有健康记录的列表")
    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordService.list();
    }

    @PutMapping
    @Operation(summary = "更新健康记录", description = "根据提供的健康信息更新健康记录")
    public boolean updateHealthRecord(@RequestBody HealthRecord healthRecord) {
        return healthRecordService.updateById(healthRecord);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除健康记录", description = "根据提供的记录ID删除健康记录")
    public boolean deleteHealthRecord(@PathVariable Long id) {
        return healthRecordService.removeById(id);
    }
}