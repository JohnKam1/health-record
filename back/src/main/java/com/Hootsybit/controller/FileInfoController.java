package com.Hootsybit.controller;

import com.Hootsybit.pojo.entity.FileInfo;
import com.Hootsybit.service.FileInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@Tag(name = "文件信息管理", description = "文件信息相关的API")
public class FileInfoController {

    @Autowired
    private FileInfoService fileInfoService;

    @PostMapping
    @Operation(summary = "创建文件信息", description = "根据提供的文件信息创建新的文件记录")
    public boolean createFileInfo(@RequestBody FileInfo fileInfo) {
        return fileInfoService.save(fileInfo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取文件信息", description = "根据提供的文件ID获取文件详细信息")
    public FileInfo getFileInfo(@PathVariable Long id) {
        return fileInfoService.getById(id);
    }

    @GetMapping
    @Operation(summary = "获取所有文件信息", description = "获取系统中所有文件记录的列表")
    public List<FileInfo> getAllFileInfos() {
        return fileInfoService.list();
    }

    @PutMapping
    @Operation(summary = "更新文件信息", description = "根据提供的文件信息更新文件记录")
    public boolean updateFileInfo(@RequestBody FileInfo fileInfo) {
        return fileInfoService.updateById(fileInfo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文件信息", description = "根据提供的文件ID删除文件记录")
    public boolean deleteFileInfo(@PathVariable Long id) {
        return fileInfoService.removeById(id);
    }
}