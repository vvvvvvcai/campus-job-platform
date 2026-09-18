package com.campus.application.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 会话列表项VO
 */
@Data
@ApiModel("会话列表项")
public class ConversationVO {

    @ApiModelProperty("会话ID")
    private Long id;

    @ApiModelProperty("对方用户ID")
    private Long toUserId;

    @ApiModelProperty("对方用户名")
    private String toUserName;

    @ApiModelProperty("对方头像")
    private String toUserAvatar;

    @ApiModelProperty("关联职位名称")
    private String jobTitle;

    @ApiModelProperty("最后一条消息摘要")
    private String lastMessage;

    @ApiModelProperty("最后消息时间")
    private LocalDateTime lastTime;

    @ApiModelProperty("未读消息数")
    private Integer unreadCount;
}
