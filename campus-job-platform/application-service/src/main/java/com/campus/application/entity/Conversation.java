package com.campus.application.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天会话实体
 */
@Data
@TableName("t_conversation")
public class Conversation {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生用户ID
     */
    private Long studentId;

    /**
     * HR用户ID
     */
    private Long hrId;

    /**
     * 关联职位ID
     */
    private Long jobId;

    /**
     * 最后一条消息摘要
     */
    private String lastMessage;

    /**
     * 最后消息时间
     */
    private LocalDateTime lastTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
