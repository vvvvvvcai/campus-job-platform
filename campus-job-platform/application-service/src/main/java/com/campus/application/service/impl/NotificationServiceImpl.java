package com.campus.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.application.entity.Notification;
import com.campus.application.mapper.NotificationMapper;
import com.campus.application.service.NotificationService;
import com.campus.application.vo.NotificationVO;
import com.campus.common.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Result<List<NotificationVO>> getNotificationList(Long userId, Integer page, Integer size) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
                .orderByDesc(Notification::getCreateTime);

        Page<Notification> pageResult = notificationMapper.selectPage(new Page<>(page, size), wrapper);

        List<NotificationVO> voList = pageResult.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return Result.success(voList);
    }

    @Override
    public Result<Void> markAsRead(Long userId, Long notificationId) {
        LambdaUpdateWrapper<Notification> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Notification::getId, notificationId)
                .eq(Notification::getUserId, userId)
                .set(Notification::getIsRead, 1);
        notificationMapper.update(null, wrapper);
        return Result.success();
    }

    @Override
    public Result<Void> markAllAsRead(Long userId) {
        LambdaUpdateWrapper<Notification> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
                .eq(Notification::getIsRead, 0)
                .set(Notification::getIsRead, 1);
        notificationMapper.update(null, wrapper);
        return Result.success();
    }

    @Override
    public Result<Integer> getUnreadCount(Long userId) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId)
                .eq(Notification::getIsRead, 0);
        long count = notificationMapper.selectCount(wrapper);
        return Result.success((int) count);
    }

    @Override
    public void createNotification(Long userId, String title, String content, Integer notiType, Long relatedId) {
        try {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setTitle(title);
            notification.setContent(content);
            notification.setNotiType(notiType);
            notification.setRelatedId(relatedId);
            notification.setIsRead(0);
            notificationMapper.insert(notification);

            // 通过 WebSocket 推送通知
            NotificationVO vo = convertToVO(notification);
            messagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notifications",
                    vo
            );

            log.info("通知创建成功: userId={}, title={}", userId, title);
        } catch (Exception e) {
            log.error("创建通知失败: {}", e.getMessage());
        }
    }

    private NotificationVO convertToVO(Notification notification) {
        NotificationVO vo = new NotificationVO();
        vo.setId(notification.getId());
        vo.setTitle(notification.getTitle());
        vo.setContent(notification.getContent());
        vo.setNotiType(notification.getNotiType());
        vo.setRelatedId(notification.getRelatedId());
        vo.setIsRead(notification.getIsRead());
        vo.setCreateTime(notification.getCreateTime());
        return vo;
    }
}
