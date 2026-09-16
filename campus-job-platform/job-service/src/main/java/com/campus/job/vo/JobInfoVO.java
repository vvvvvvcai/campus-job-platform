package com.campus.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 职位信息响应VO
 */
@Data
@ApiModel("职位信息响应")
public class JobInfoVO {

    @ApiModelProperty("职位ID")
    private Long id;

    @ApiModelProperty("企业ID")
    private Long companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("职位标题")
    private String title;

    @ApiModelProperty("职位类别")
    private String category;

    @ApiModelProperty("工作城市")
    private String city;

    @ApiModelProperty("最低薪资")
    private BigDecimal salaryMin;

    @ApiModelProperty("最高薪资")
    private BigDecimal salaryMax;

    @ApiModelProperty("学历要求")
    private String education;

    @ApiModelProperty("经验要求")
    private String experience;

    @ApiModelProperty("工作类型：1-全职，2-实习，3-兼职")
    private Integer jobType;

    @ApiModelProperty("职位描述")
    private String description;

    @ApiModelProperty("任职要求")
    private String requirement;

    @ApiModelProperty("福利待遇")
    private String benefits;

    @ApiModelProperty("招聘人数")
    private Integer headcount;

    @ApiModelProperty("职位状态：0-待审核，1-招聘中，2-已关闭")
    private Integer status;

    @ApiModelProperty("审核状态：0-待审核，1-已通过，2-已拒绝")
    private Integer auditStatus;

    @ApiModelProperty("审核备注")
    private String auditRemark;

    @ApiModelProperty("审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty("浏览次数")
    private Integer viewCount;

    @ApiModelProperty("申请次数")
    private Integer applyCount;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否已收藏")
    private Boolean isFavorite;
}
