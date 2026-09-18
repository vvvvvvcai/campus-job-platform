package com.campus.application.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知列表项VO
 */
@Data
@ApiModel("通知列表项")
public class NotificationVO {

    @ApiModelProperty("通知ID")
    private Long id;

    @ApiModelProperty("通知标题")
    private String title;

    @ApiModelProperty("通知内容")
    private String content;

    @ApiModelProperty("类型：1-投递通知 2-面试通知 3-系统公告")
    private Integer notiType;

    @ApiModelProperty("关联ID")
    private Long relatedId;

    @ApiModelProperty("已读状态：0-未读 1-已读")
    private Integer isRead;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
