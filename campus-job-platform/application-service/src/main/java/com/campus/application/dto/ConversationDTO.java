package com.campus.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 创建/获取会话请求DTO
 */
@Data
@ApiModel("创建会话请求")
public class ConversationDTO {

    @NotNull(message = "对方用户ID不能为空")
    @ApiModelProperty("对方用户ID（HR或学生）")
    private Long toUserId;

    @ApiModelProperty("关联职位ID")
    private Long jobId;
}
