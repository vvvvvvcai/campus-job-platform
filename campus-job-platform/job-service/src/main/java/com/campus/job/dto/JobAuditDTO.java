package com.campus.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 职位审核操作DTO
 */
@Data
@ApiModel("职位审核操作")
public class JobAuditDTO {

    @NotNull(message = "审核状态不能为空")
    @ApiModelProperty("审核状态：1-通过，2-拒绝")
    private Integer auditStatus;

    @ApiModelProperty("审核备注（拒绝时必填）")
    private String remark;
}
