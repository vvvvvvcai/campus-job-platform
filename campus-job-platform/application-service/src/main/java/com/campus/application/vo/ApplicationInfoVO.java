package com.campus.application.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 投递信息响应VO
 */
@Data
@ApiModel("投递信息响应")
public class ApplicationInfoVO {

    @ApiModelProperty("投递ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("职位ID")
    private Long jobId;

    @ApiModelProperty("企业ID")
    private Long companyId;

    @ApiModelProperty("简历ID")
    private Long resumeId;

    @ApiModelProperty("投递状态：0-待查看，1-已查看，2-面试邀请，3-不合适，4-已录用")
    private Integer status;

    @ApiModelProperty("HR备注")
    private String hrRemark;

    @ApiModelProperty("面试时间")
    private LocalDateTime interviewTime;

    @ApiModelProperty("面试地址")
    private String interviewAddress;

    @ApiModelProperty("面试联系人")
    private String interviewContact;

    @ApiModelProperty("面试联系电话")
    private String interviewContactPhone;

    @ApiModelProperty("面试结果：0-待定，1-通过，2-未通过")
    private Integer interviewResult;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("职位名称")
    private String jobName;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("企业Logo")
    private String companyLogo;

    @ApiModelProperty("简历标题")
    private String resumeTitle;
}
