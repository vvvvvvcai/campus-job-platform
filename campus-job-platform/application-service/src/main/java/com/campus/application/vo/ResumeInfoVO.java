package com.campus.application.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 简历信息响应VO
 */
@Data
@ApiModel("简历信息响应")
public class ResumeInfoVO {

    @ApiModelProperty("简历ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("简历标题")
    private String title;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别：0-未知，1-男，2-女")
    private Integer gender;

    @ApiModelProperty("出生日期")
    private LocalDate birthDate;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("学历（如：大专、本科、硕士、博士）")
    private String education;

    @ApiModelProperty("毕业院校")
    private String school;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("毕业年份")
    private Integer graduationYear;

    @ApiModelProperty("工作状态：0-在校，1-应届，2-往届")
    private Integer workStatus;

    @ApiModelProperty("期望城市")
    private String expectCity;

    @ApiModelProperty("期望行业")
    private String expectIndustry;

    @ApiModelProperty("期望职位类型")
    private String expectJobType;

    @ApiModelProperty("期望薪资")
    private String expectSalary;

    @ApiModelProperty("自我介绍")
    private String selfIntroduction;

    @ApiModelProperty("专业技能（JSON格式）")
    private String skills;

    @ApiModelProperty("工作经历（JSON格式）")
    private String experiences;

    @ApiModelProperty("项目经历（JSON格式）")
    private String projects;

    @ApiModelProperty("获奖情况（JSON格式）")
    private String awards;

    @ApiModelProperty("附件（JSON格式）")
    private String attachments;

    @ApiModelProperty("状态：0-草稿，1-已完成")
    private Integer status;

    @ApiModelProperty("是否默认简历：0-否，1-是")
    private Integer isDefault;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
