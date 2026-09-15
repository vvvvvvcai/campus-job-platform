package com.campus.user.service;

import com.campus.common.result.Result;
import com.campus.user.dto.UserLoginDTO;
import com.campus.user.dto.UserRegisterDTO;
import com.campus.user.vo.UserInfoVO;
import com.campus.user.vo.UserListVO;
import com.campus.user.vo.UserLoginVO;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     */
    Result<Void> register(UserRegisterDTO dto);

    /**
     * 用户登录
     */
    Result<UserLoginVO> login(UserLoginDTO dto);

    /**
     * 获取用户信息
     */
    Result<UserInfoVO> getUserInfo(Long userId);

    /**
     * 更新用户信息
     */
    Result<Void> updateUserInfo(Long userId, UserInfoVO vo);

    /**
     * 修改密码
     */
    Result<Void> updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 管理员：获取用户列表
     */
    Result<UserListVO> getUserList(String keyword, Integer role, Integer status, Integer page, Integer pageSize);

    /**
     * 管理员：禁用/启用用户
     */
    Result<Void> updateUserStatus(Long userId, Integer status);

    /**
     * 管理员：获取用户统计数据
     */
    Result<Object> getUserCount();
}
