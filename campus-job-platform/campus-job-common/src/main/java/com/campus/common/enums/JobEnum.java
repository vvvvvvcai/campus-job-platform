package com.campus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 职位相关枚举
 */
public class JobEnum {

    /**
     * 工作类型
     */
    @Getter
    @AllArgsConstructor
    public enum JobType {
        FULL_TIME(1, "全职"),
        INTERN(2, "实习"),
        PART_TIME(3, "兼职");

        private final Integer code;
        private final String message;
    }

    /**
     * 职位状态
     */
    @Getter
    @AllArgsConstructor
    public enum Status {
        PENDING(0, "待审核"),
        RECRUITING(1, "招聘中"),
        CLOSED(2, "已关闭"),
        REJECTED(3, "已拒绝");

        private final Integer code;
        private final String message;
    }

    /**
     * 职位审核状态
     */
    @Getter
    @AllArgsConstructor
    public enum AuditStatus {
        PENDING(0, "待审核"),
        APPROVED(1, "已通过"),
        REJECTED(2, "已拒绝");

        private final Integer code;
        private final String message;
    }
}
