package com.campus.application.controller;

import com.campus.application.service.NotificationService;
import com.campus.application.vo.NotificationVO;
import com.campus.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知控制器
 */
@Api(tags = "通知管理")
@RestController
@RequestMapping("/api/message/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @ApiOperation("获取通知列表")
    @GetMapping("/list")
    public Result<List<NotificationVO>> getNotificationList(
            @RequestHeader("X-User-Id") Long userId,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return notificationService.getNotificationList(userId, page, size);
    }

    @ApiOperation("标记单条已读")
    @PutMapping("/read/{id}")
    public Result<Void> markAsRead(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        return notificationService.markAsRead(userId, id);
    }

    @ApiOperation("全部标记已读")
    @PutMapping("/read-all")
    public Result<Void> markAllAsRead(
            @RequestHeader("X-User-Id") Long userId) {
        return notificationService.markAllAsRead(userId);
    }

    @ApiOperation("获取未读通知数")
    @GetMapping("/unread-count")
    public Result<Integer> getUnreadCount(
            @RequestHeader("X-User-Id") Long userId) {
        return notificationService.getUnreadCount(userId);
    }
}
