package com.campus.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * 简历保存请求DTO
 */
@Data
@ApiModel("简历保存请求")
public class ResumeSaveDTO {

    @ApiModelProperty("简历ID（更新时必填）")
    private Long id;

    @NotBlank(message = "简历标题不能为空")
    @ApiModelProperty(value = "简历标题", required = true)
    private String title;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @ApiModelProperty("性别：0-未知，1-男，2-女")
    private Integer gender;

    @ApiModelProperty("出生日期")
    private LocalDate birthDate;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("学历：0-大专，1-本科，2-硕士，3-博士")
    private Integer education;

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
}
