package com.campus.company.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 企业认证请求DTO
 */
@Data
@ApiModel("企业认证请求")
public class CompanyAuditDTO {

    @NotBlank(message = "企业名称不能为空")
    @ApiModelProperty(value = "企业名称", required = true)
    private String companyName;

    @NotBlank(message = "营业执照URL不能为空")
    @ApiModelProperty(value = "营业执照URL", required = true)
    private String licenseUrl;

    @NotBlank(message = "企业简介不能为空")
    @ApiModelProperty(value = "企业简介", required = true)
    private String description;
}
