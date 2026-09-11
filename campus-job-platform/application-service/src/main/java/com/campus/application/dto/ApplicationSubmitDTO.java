package com.campus.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 投递提交请求DTO
 */
@Data
@ApiModel("投递提交请求")
public class ApplicationSubmitDTO {

    @NotNull(message = "职位ID不能为空")
    @ApiModelProperty(value = "职位ID", required = true)
    private Long jobId;

    @NotNull(message = "企业ID不能为空")
    @ApiModelProperty(value = "企业ID", required = true)
    private Long companyId;

    @NotNull(message = "简历ID不能为空")
    @ApiModelProperty(value = "简历ID", required = true)
    private Long resumeId;
}
