package com.campus.user.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.campus.user.vo.UserListVO;
import com.campus.user.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Override
    public Result<UserListVO> getUserList(String keyword, Integer role, Integer status, Integer page, Integer pageSize) {
        // 参数默认值
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 20;

        // 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getDeleted, 0);

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w
                .like(User::getUsername, keyword)
                .or().like(User::getPhone, keyword)
            );
        }
        if (role != null) {
            wrapper.eq(User::getRole, role);
        }
        if (status != null) {
            wrapper.eq(User::getStatus, status);
        }

        wrapper.orderByDesc(User::getCreateTime);

        // 执行分页查询
        Page<User> pageResult = userMapper.selectPage(new Page<>(page, pageSize), wrapper);

        // 构建响应
        UserListVO vo = new UserListVO();
        vo.setTotal(pageResult.getTotal());
        vo.setPage(page);
        vo.setPageSize(pageSize);
        vo.setTotalPages((int) Math.ceil((double) pageResult.getTotal() / pageSize));
        vo.setRecords(pageResult.getRecords().stream().map(user -> {
            UserListVO.UserItemVO item = new UserListVO.UserItemVO();
            item.setId(user.getId());
            item.setPhone(PhoneUtils.desensitize(user.getPhone()));
            item.setUsername(user.getUsername());
            item.setRole(user.getRole());
            item.setRoleLabel(getRoleLabel(user.getRole()));
            item.setAvatar(user.getAvatar());
            item.setEmail(user.getEmail());
            item.setStatus(user.getStatus());
            item.setStatusLabel(user.getStatus() == 1 ? "正常" : "禁用");
            item.setCreateTime(user.getCreateTime());
            return item;
        }).collect(Collectors.toList()));

        return Result.success(vo);
    }

    @Override
    public Result<Void> updateUserStatus(Long userId, Integer status) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 不能禁用管理员
        if (user.getRole().equals(UserEnum.Role.ADMIN.getCode()) && status == 0) {
            throw new BusinessException(ResultCode.FAIL.getCode(), "不能禁用管理员账户");
        }

        user.setStatus(status);
        userMapper.updateById(user);

        log.info("管理员更新用户状态: userId={}, status={}", userId, status);
        return Result.success();
    }

    @Override
    public Result<Object> getUserCount() {
        Map<String, Object> result = new HashMap<>();

        // 总用户数
        LambdaQueryWrapper<User> totalWrapper = new LambdaQueryWrapper<>();
        totalWrapper.eq(User::getDeleted, 0);
        long total = userMapper.selectCount(totalWrapper);
        result.put("total", total);

        // 按角色统计
        Map<String, Long> byRole = new HashMap<>();
        for (UserEnum.Role role : UserEnum.Role.values()) {
            LambdaQueryWrapper<User> roleWrapper = new LambdaQueryWrapper<>();
            roleWrapper.eq(User::getDeleted, 0).eq(User::getRole, role.getCode());
            byRole.put(role.name().toLowerCase(), userMapper.selectCount(roleWrapper));
        }
        result.put("byRole", byRole);

        // 按状态统计
        Map<String, Long> byStatus = new HashMap<>();
        for (UserEnum.Status status : UserEnum.Status.values()) {
            LambdaQueryWrapper<User> statusWrapper = new LambdaQueryWrapper<>();
            statusWrapper.eq(User::getDeleted, 0).eq(User::getStatus, status.getCode());
            byStatus.put(status == UserEnum.Status.NORMAL ? "active" : "disabled", userMapper.selectCount(statusWrapper));
        }
        result.put("byStatus", byStatus);

        return Result.success(result);
    }

    @Override
    public Result<UserInfoVO> getUserAdminDetail(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        vo.setPhone(PhoneUtils.desensitize(user.getPhone()));
        vo.setRoleLabel(getRoleLabel(user.getRole()));
        vo.setStatusLabel(user.getStatus() == 1 ? "正常" : "禁用");
        vo.setGenderLabel(getGenderLabel(user.getGender()));

        return Result.success(vo);
    }

    private String getRoleLabel(Integer role) {
        if (role == null) return "未知";
        switch (role) {
            case 0: return "学生";
            case 1: return "企业HR";
            case 2: return "管理员";
            default: return "未知";
        }
    }

    private String getGenderLabel(Integer gender) {
        if (gender == null) return "未知";
        switch (gender) {
            case 1: return "男";
            case 2: return "女";
            default: return "未知";
        }
    }
}
