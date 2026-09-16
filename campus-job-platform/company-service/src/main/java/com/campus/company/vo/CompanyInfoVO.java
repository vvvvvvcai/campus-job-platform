package com.campus.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 企业信息响应VO
 */
@Data
@ApiModel("企业信息响应")
public class CompanyInfoVO {

    @ApiModelProperty("企业ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("所属行业")
    private String industry;

    @ApiModelProperty("企业规模")
    private String scale;

    @ApiModelProperty("企业性质")
    private String nature;

    @ApiModelProperty("企业地址")
    private String address;

    @ApiModelProperty("企业网站")
    private String website;

    @ApiModelProperty("企业Logo")
    private String logo;

    @ApiModelProperty("企业简介")
    private String description;

    @ApiModelProperty("营业执照URL")
    private String licenseUrl;

    @ApiModelProperty("审核状态：0-待审核，1-已通过，2-已拒绝")
    private Integer auditStatus;

    @ApiModelProperty("审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty("审核备注")
    private String auditRemark;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("HR联系人姓名")
    private String hrName;

    @ApiModelProperty("HR联系电话")
    private String hrPhone;

    @ApiModelProperty("HR电子邮箱")
    private String hrEmail;
}
