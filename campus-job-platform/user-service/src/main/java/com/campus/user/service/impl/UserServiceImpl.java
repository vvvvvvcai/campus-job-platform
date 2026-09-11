package com.campus.user.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.common.enums.UserEnum;
import com.campus.common.exception.BusinessException;
import com.campus.common.result.Result;
import com.campus.common.result.ResultCode;
import com.campus.common.utils.JwtUtils;
import com.campus.common.utils.PhoneUtils;
import com.campus.user.dto.UserLoginDTO;
import com.campus.user.dto.UserRegisterDTO;
import com.campus.user.entity.User;
import com.campus.user.mapper.UserMapper;
import com.campus.user.service.UserService;
import com.campus.user.vo.UserInfoVO;
import com.campus.user.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public Result<Void> register(UserRegisterDTO dto) {
        // 验证手机号格式
        if (!PhoneUtils.isValid(dto.getPhone())) {
            throw new BusinessException(ResultCode.PHONE_ERROR);
        }

        // 检查手机号是否已注册
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, dto.getPhone());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.USER_EXISTS);
        }

        // 创建用户
        User user = new User();
        user.setPhone(dto.getPhone());
        user.setPassword(BCrypt.hashpw(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole() != null ? dto.getRole() : UserEnum.Role.STUDENT.getCode());
        user.setStatus(UserEnum.Status.NORMAL.getCode());
        user.setGender(UserEnum.Gender.UNKNOWN.getCode());

        userMapper.insert(user);
        log.info("用户注册成功: {}", dto.getPhone());

        return Result.success();
    }

    @Override
    public Result<UserLoginVO> login(UserLoginDTO dto) {
        // 验证手机号格式
        if (!PhoneUtils.isValid(dto.getPhone())) {
            throw new BusinessException(ResultCode.PHONE_ERROR);
        }

        // 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, dto.getPhone());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 验证密码
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
        }

        // 检查用户状态
        if (user.getStatus().equals(UserEnum.Status.DISABLED.getCode())) {
            throw new BusinessException(ResultCode.USER_DISABLED);
        }

        // 生成Token
        String token = JwtUtils.generateToken(user.getId(), user.getPhone(), user.getRole());

        // 构建响应
        UserLoginVO vo = new UserLoginVO();
        vo.setToken(token);
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRole(user.getRole());
        vo.setAvatar(user.getAvatar());

        log.info("用户登录成功: {}", dto.getPhone());
        return Result.success(vo);
    }

    @Override
    public Result<UserInfoVO> getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        // 手机号脱敏
        vo.setPhone(PhoneUtils.desensitize(user.getPhone()));

        return Result.success(vo);
    }

    @Override
    public Result<Void> updateUserInfo(Long userId, UserInfoVO vo) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 更新允许的字段
        user.setUsername(vo.getUsername());
        user.setAvatar(vo.getAvatar());
        user.setEmail(vo.getEmail());
        user.setRealName(vo.getRealName());
        user.setGender(vo.getGender());

        userMapper.updateById(user);
        return Result.success();
    }

    @Override
    public Result<Void> updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 验证旧密码
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
        }

        // 更新密码
        user.setPassword(BCrypt.hashpw(newPassword));
        userMapper.updateById(user);

        return Result.success();
    }
}
