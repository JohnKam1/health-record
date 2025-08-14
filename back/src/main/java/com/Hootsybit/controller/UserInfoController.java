package com.Hootsybit.controller;

import com.Hootsybit.common.Result;
import com.Hootsybit.pojo.entity.UserInfo;
import com.Hootsybit.pojo.vo.UserInfoVO;
import com.Hootsybit.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "用户信息管理", description = "用户信息相关的API")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping
    @Operation(summary = "创建用户信息", description = "根据提供的用户信息创建新的用户记录")
    public boolean createUserInfo(@RequestBody UserInfo userInfo) {
        return userInfoService.save(userInfo);
    }

    @GetMapping("/info")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    public Result<UserInfoVO> getCurrentUserInfo() {
        // 获取当前用户ID
        Long userId = getUserId();
        return Result.success(userInfoService.getUserInfoVO(userId));
    }

    @GetMapping
    @Operation(summary = "获取所有用户信息", description = "获取系统中所有用户的列表")
    public List<UserInfo> getAllUserInfo() {
        return userInfoService.list();
    }

    @PutMapping
    @Operation(summary = "更新用户信息", description = "根据提供的用户信息更新用户记录")
    public boolean updateUserInfo(@RequestBody UserInfo userInfo) {
        return userInfoService.updateById(userInfo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户信息", description = "根据提供的用户ID删除用户记录")
    public boolean deleteUserInfo(@PathVariable Long id) {
        return userInfoService.removeById(id);
    }
}