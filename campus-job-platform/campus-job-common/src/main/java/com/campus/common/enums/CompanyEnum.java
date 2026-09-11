package com.campus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 企业相关枚举
 */
public class CompanyEnum {

    /**
     * 企业认证状态
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
