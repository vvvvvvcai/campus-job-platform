package com.campus.application.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 投递实体
 */
@Data
@TableName("t_application")
public class Application {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 企业ID
     */
    private Long companyId;

    /**
     * 简历ID
     */
    private Long resumeId;

    /**
     * 投递状态：0-待查看，1-已查看，2-面试邀请，3-不合适，4-已录用
     */
    private Integer status;

    /**
     * HR备注
     */
    private String hrRemark;

    /**
     * 面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 面试地址
     */
    private String interviewAddress;

    /**
     * 面试联系人
     */
    private String interviewContact;

    /**
     * 面试联系电话
     */
    private String interviewContactPhone;

    /**
     * 面试结果：0-待定，1-通过，2-未通过
     */
    private Integer interviewResult;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
