package com.Hootsybit.controller;

import com.Hootsybit.common.Result;
import com.Hootsybit.config.JwtInterceptor;
import com.Hootsybit.pojo.entity.FileInfo;
import com.Hootsybit.service.FileInfoService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/file")
@Tag(name = "文件上传", description = "文件上传相关API")
public class FileUploadController {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private FileInfoService fileInfoService;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传文件到MinIO服务器")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 获取当前用户ID
            String userId = JwtInterceptor.getCurrentUserId();
            
            // 检查文件是否为空
            if (file.isEmpty()) {
                return Result.error("文件不能为空");
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replace("-", "") + fileExtension;

            // 上传文件到MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 保存文件信息到数据库
            FileInfo fileInfo = new FileInfo();
            fileInfo.setUserId(Long.valueOf(userId));
            fileInfo.setFileName(originalFilename);
            fileInfo.setFileType(file.getContentType());
            fileInfo.setFileSize(file.getSize());
            fileInfo.setFilePath(fileName);
            fileInfo.setUploadTime(LocalDateTime.now());
            fileInfoService.save(fileInfo);

            // 返回文件访问URL
            String fileUrl = minioEndpoint + "/" + bucketName + "/" + fileName;
            return Result.success(fileUrl);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
}