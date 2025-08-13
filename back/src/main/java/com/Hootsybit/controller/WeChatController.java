package com.Hootsybit.controller;

import com.Hootsybit.common.Result;
import com.Hootsybit.service.WeChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wechat")
@Tag(name = "微信接口", description = "微信相关接口")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    @PostMapping("/login")
    @Operation(summary = "微信登录", description = "使用临时code获取微信session信息")
    public Result<String> wechatLogin(@RequestParam String code) {
        return Result.success(weChatService.wechatLogin(code));
    }
}