package com.campus.company.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 企业审核操作DTO
 */
@Data
@ApiModel("企业审核操作")
public class CompanyAuditHandleDTO {

    @ApiModelProperty("审核备注（拒绝时必填）")
    private String remark;
}
