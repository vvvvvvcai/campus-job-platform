package com.campus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息相关枚举
 */
public class MessageEnum {

    /**
     * 消息类型
     */
    @Getter
    @AllArgsConstructor
    public enum Type {
        SYSTEM(1, "系统通知"),
        APPLICATION(2, "投递通知"),
        INTERVIEW(3, "面试通知"),
        OTHER(4, "其他");

        private final Integer code;
        private final String message;
    }

    /**
     * 已读状态
     */
    @Getter
    @AllArgsConstructor
    public enum ReadStatus {
        UNREAD(0, "未读"),
        READ(1, "已读");

        private final Integer code;
        private final String message;
    }
}
