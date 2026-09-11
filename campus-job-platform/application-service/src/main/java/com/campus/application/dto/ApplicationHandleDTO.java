package com.campus.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 投递处理请求DTO
 */
@Data
@ApiModel("投递处理请求")
public class ApplicationHandleDTO {

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态：0-待查看，1-已查看，2-面试邀请，3-不合适，4-已录用", required = true)
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
}
