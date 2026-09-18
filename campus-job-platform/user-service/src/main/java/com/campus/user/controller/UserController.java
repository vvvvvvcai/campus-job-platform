package com.campus.user.controller;

import com.campus.common.result.Result;
import com.campus.user.dto.UserLoginDTO;
import com.campus.user.dto.UserRegisterDTO;
import com.campus.user.service.UserService;
import com.campus.user.vo.UserInfoVO;
import com.campus.user.vo.UserListVO;
import com.campus.user.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户控制器
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody @Valid UserLoginDTO dto) {
        return userService.login(dto);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo(@RequestHeader("X-User-Id") Long userId) {
        return userService.getUserInfo(userId);
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/info")
    public Result<Void> updateUserInfo(@RequestHeader("X-User-Id") Long userId,
                                       @RequestBody UserInfoVO vo) {
        return userService.updateUserInfo(userId, vo);
    }

    @ApiOperation("修改密码")
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestHeader("X-User-Id") Long userId,
                                       @RequestParam String oldPassword,
                                       @RequestParam String newPassword) {
        return userService.updatePassword(userId, oldPassword, newPassword);
    }

    @ApiOperation("管理员获取用户列表")
    @GetMapping("/list")
    public Result<UserListVO> getUserList(
            @RequestHeader("X-User-Id") Long adminId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer role,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return userService.getUserList(keyword, role, status, page, pageSize);
    }

    @ApiOperation("管理员禁用/启用用户")
    @PutMapping("/status/{id}")
    public Result<Void> updateUserStatus(
            @RequestHeader("X-User-Id") Long adminId,
            @PathVariable Long id,
            @RequestParam Integer status) {
        return userService.updateUserStatus(id, status);
    }

    @ApiOperation("管理员获取用户统计数据")
    @GetMapping("/count")
    public Result<Object> getUserCount(@RequestHeader("X-User-Id") Long adminId) {
        return userService.getUserCount();
    }

    @ApiOperation("管理员获取用户详情")
    @GetMapping("/admin/detail/{id}")
    public Result<UserInfoVO> getUserAdminDetail(
            @RequestHeader("X-User-Id") Long adminId,
            @PathVariable Long id) {
        return userService.getUserAdminDetail(id);
    }

    @ApiOperation("管理员获取近7天用户注册趋势")
    @GetMapping("/count/trend")
    public Result<Object> getUserTrend(@RequestHeader("X-User-Id") Long adminId) {
        return userService.getUserTrend();
    }
}
