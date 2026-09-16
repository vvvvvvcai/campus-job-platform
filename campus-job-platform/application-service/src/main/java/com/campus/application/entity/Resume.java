package com.campus.application.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 简历实体
 */
@Data
@TableName("t_resume")
public class Resume {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 简历标题
     */
    private String title;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学历（如：大专、本科、硕士、博士）
     */
    private String education;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 专业
     */
    private String major;

    /**
     * 毕业年份
     */
    private Integer graduationYear;

    /**
     * 工作状态：0-在校，1-应届，2-往届
     */
    private Integer workStatus;

    /**
     * 期望城市
     */
    private String expectCity;

    /**
     * 期望行业
     */
    private String expectIndustry;

    /**
     * 期望职位类型
     */
    private String expectJobType;

    /**
     * 期望薪资
     */
    private String expectSalary;

    /**
     * 自我介绍
     */
    private String selfIntroduction;

    /**
     * 专业技能（JSON格式）
     */
    private String skills;

    /**
     * 工作经历（JSON格式）
     */
    private String experiences;

    /**
     * 项目经历（JSON格式）
     */
    private String projects;

    /**
     * 获奖情况（JSON格式）
     */
    private String awards;

    /**
     * 附件（JSON格式）
     */
    private String attachments;

    /**
     * 状态：0-草稿，1-已完成
     */
    private Integer status;

    /**
     * 是否默认简历：0-否，1-是
     */
    private Integer isDefault;

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
