环境切换参数 -Dspring.profiles.active=prod

# 健康档案管理系统后端

## 项目概述

健康档案管理系统后端服务，用于支持医疗健康数据的管理与存储。

## 技术栈

- Spring Boot 2.7.0
- PostgreSQL
- MyBatis Plus 3.5.2
- MinIO Java Client 8.4.3
- OpenAPI (springdoc-openapi 1.6.9)

## API文档访问

项目集成了OpenAPI规范的API文档，可以通过以下路径访问：

### JSON格式API文档
```
http://localhost:8080/v3/api-docs
```

### Swagger UI界面
```
http://localhost:8080/swagger-ui.html
```
或者
```
http://localhost:8080/swagger-ui/index.html
```

## 主要功能模块

1. 用户信息管理：用户注册、信息维护、查询等
2. 健康档案管理：创建、更新、查询健康记录
3. 文件管理：支持文件上传、下载、信息查询，集成MinIO对象存储
4. 数据库操作：基于MyBatis Plus的CRUD操作

## 开发环境搭建

1. 安装JDK 1.8并配置环境变量
2. 安装Maven并配置
3. 启动PostgreSQL并导入schema.sql和data.sql
4. 启动MinIO服务并配置Bucket
5. 使用IDE导入项目或通过命令行构建

## 构建与部署

### 构建项目
```bash
mvn clean package
```

### 本地运行
```bash
mvn spring-boot:run
```
或者运行主类[HealthRecordApplication.java](file://D:/work_space/health-record/back/src/main/java/com/Hootsybit/HealthRecordApplication.java)

### 打包部署
```bash
java -jar back-1.0-SNAPSHOT.jar
```