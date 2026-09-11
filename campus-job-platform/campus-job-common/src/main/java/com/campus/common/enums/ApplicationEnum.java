package com.campus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 投递相关枚举
 */
public class ApplicationEnum {

    /**
     * 投递状态
     */
    @Getter
    @AllArgsConstructor
    public enum Status {
        PENDING(0, "待查看"),
        VIEWED(1, "已查看"),
        INTERVIEW(2, "面试邀请"),
        REJECTED(3, "不合适"),
        ACCEPTED(4, "已录用");

        private final Integer code;
        private final String message;
    }

    /**
     * 面试结果
     */
    @Getter
    @AllArgsConstructor
    public enum InterviewResult {
        PENDING(0, "待定"),
        PASSED(1, "通过"),
        FAILED(2, "未通过");

        private final Integer code;
        private final String message;
    }
}
