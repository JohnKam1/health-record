package com.Hootsybit.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI healthRecordOpenAPI() {
        // 创建服务器配置，便于区分不同环境
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("开发环境");

        Server prodServer = new Server();
        prodServer.setUrl("https://api.healthrecord.com");
        prodServer.setDescription("生产环境");

        // 创建联系人信息
        Contact contact = new Contact();
        contact.setName("健康档案管理系统团队");
        contact.setEmail("healthrecord@example.com");
        contact.setUrl("https://www.healthrecord.com/contact");

        // 创建许可证信息
        License license = new License();
        license.setName("Apache 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0.html");

        // 创建 API 文档基本信息
        Info info = new Info()
                .title("健康档案管理系统 API")
                .version("1.0.0")
                .description("健康档案管理系统的后端API文档，提供完整的健康数据管理功能")
                .contact(contact)
                .license(license);

        // 配置安全方案
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // 构建 OpenAPI 实例
        return new OpenAPI()
                .servers(Arrays.asList(devServer, prodServer))
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", securityScheme));
    }
}