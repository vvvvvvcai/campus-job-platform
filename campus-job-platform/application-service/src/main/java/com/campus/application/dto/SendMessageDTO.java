package com.campus.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发送消息请求DTO
 */
@Data
@ApiModel("发送消息请求")
public class SendMessageDTO {

    @NotNull(message = "会话ID不能为空")
    @ApiModelProperty("会话ID")
    private Long conversationId;

    @NotBlank(message = "消息内容不能为空")
    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息类型：1-文本 2-图片 3-系统通知")
    private Integer msgType = 1;
}
