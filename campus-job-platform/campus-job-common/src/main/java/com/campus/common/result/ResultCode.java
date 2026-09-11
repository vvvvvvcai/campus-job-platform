package com.campus.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一响应状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),

    // 认证相关
    UNAUTHORIZED(401, "未登录或token已过期"),
    FORBIDDEN(403, "无权限访问"),
    TOKEN_ERROR(401, "token无效"),
    TOKEN_EXPIRED(401, "token已过期"),

    // 参数相关
    PARAM_ERROR(400, "参数错误"),
    PARAM_MISSING(400, "参数缺失"),
    PARAM_TYPE_ERROR(400, "参数类型错误"),

    // 业务相关
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_PASSWORD_ERROR(1002, "密码错误"),
    USER_DISABLED(1003, "用户已被禁用"),
    USER_EXISTS(1004, "用户已存在"),
    PHONE_ERROR(1005, "手机号格式错误"),
    SMS_CODE_ERROR(1006, "验证码错误"),
    SMS_CODE_EXPIRED(1007, "验证码已过期"),

    // 企业相关
    COMPANY_NOT_FOUND(2001, "企业不存在"),
    COMPANY_AUDIT_PENDING(2002, "企业认证审核中"),
    COMPANY_AUDIT_REJECTED(2003, "企业认证已拒绝"),

    // 职位相关
    JOB_NOT_FOUND(3001, "职位不存在"),
    JOB_CLOSED(3002, "职位已关闭"),
    JOB_AUDIT_PENDING(3003, "职位审核中"),

    // 简历相关
    RESUME_NOT_FOUND(4001, "简历不存在"),
    RESUME_DEFAULT_ERROR(4002, "默认简历不能删除"),

    // 投递相关
    APPLICATION_EXISTS(5001, "已投递过该职位"),
    APPLICATION_NOT_FOUND(5002, "投递记录不存在"),

    // 收藏相关
    FAVORITE_EXISTS(6001, "已收藏该职位"),
    FAVORITE_NOT_FOUND(6002, "收藏记录不存在");

    private final Integer code;
    private final String message;
}
