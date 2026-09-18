package com.campus.application.service;

import com.campus.application.vo.NotificationVO;
import com.campus.common.result.Result;

import java.util.List;

/**
 * 通知服务接口
 */
public interface NotificationService {

    /**
     * 获取通知列表
     */
    Result<List<NotificationVO>> getNotificationList(Long userId, Integer page, Integer size);

    /**
     * 标记单条已读
     */
    Result<Void> markAsRead(Long userId, Long notificationId);

    /**
     * 全部标记已读
     */
    Result<Void> markAllAsRead(Long userId);

    /**
     * 获取未读通知数
     */
    Result<Integer> getUnreadCount(Long userId);

    /**
     * 创建通知
     */
    void createNotification(Long userId, String title, String content, Integer notiType, Long relatedId);
}
