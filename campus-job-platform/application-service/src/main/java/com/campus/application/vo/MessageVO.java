package com.campus.application.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息详情VO
 */
@Data
@ApiModel("消息详情")
public class MessageVO {

    @ApiModelProperty("消息ID")
    private Long id;

    @ApiModelProperty("会话ID")
    private Long conversationId;

    @ApiModelProperty("发送者ID")
    private Long fromUserId;

    @ApiModelProperty("接收者ID")
    private Long toUserId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息类型：1-文本 2-图片 3-系统通知")
    private Integer msgType;

    @ApiModelProperty("已读状态：0-未读 1-已读")
    private Integer isRead;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
