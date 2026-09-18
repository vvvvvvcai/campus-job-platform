package com.campus.application.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统通知实体
 */
@Data
@TableName("t_notification")
public class Notification {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接收者ID
     */
    private Long userId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 类型：1-投递通知 2-面试通知 3-系统公告
     */
    private Integer notiType;

    /**
     * 关联ID（投递ID/面试ID等）
     */
    private Long relatedId;

    /**
     * 已读状态：0-未读 1-已读
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
