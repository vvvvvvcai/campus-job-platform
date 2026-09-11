package com.campus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户相关枚举
 */
public class UserEnum {

    /**
     * 用户角色
     */
    @Getter
    @AllArgsConstructor
    public enum Role {
        STUDENT(0, "学生"),
        COMPANY(1, "企业HR"),
        ADMIN(2, "管理员");

        private final Integer code;
        private final String message;
    }

    /**
     * 用户状态
     */
    @Getter
    @AllArgsConstructor
    public enum Status {
        DISABLED(0, "禁用"),
        NORMAL(1, "正常");

        private final Integer code;
        private final String message;
    }

    /**
     * 性别
     */
    @Getter
    @AllArgsConstructor
    public enum Gender {
        UNKNOWN(0, "未知"),
        MALE(1, "男"),
        FEMALE(2, "女");

        private final Integer code;
        private final String message;
    }
}
