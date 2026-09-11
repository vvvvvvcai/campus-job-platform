package com.campus.job.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 职位实体
 */
@Data
@TableName("t_job")
public class Job {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 企业ID
     */
    private Long companyId;

    /**
     * 职位标题
     */
    private String title;

    /**
     * 职位类别
     */
    private String category;

    /**
     * 工作城市
     */
    private String city;

    /**
     * 最低薪资
     */
    private BigDecimal salaryMin;

    /**
     * 最高薪资
     */
    private BigDecimal salaryMax;

    /**
     * 学历要求
     */
    private String education;

    /**
     * 经验要求
     */
    private String experience;

    /**
     * 工作类型：1-全职，2-实习，3-兼职
     */
    private Integer jobType;

    /**
     * 职位描述
     */
    private String description;

    /**
     * 任职要求
     */
    private String requirement;

    /**
     * 福利待遇
     */
    private String benefits;

    /**
     * 招聘人数
     */
    private Integer headcount;

    /**
     * 职位状态：0-待审核，1-招聘中，2-已关闭
     */
    private Integer status;

    /**
     * 审核状态：0-待审核，1-已通过，2-已拒绝
     */
    private Integer auditStatus;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 申请次数
     */
    private Integer applyCount;

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

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
}
