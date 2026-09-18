package com.campus.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.application.dto.ConversationDTO;
import com.campus.application.dto.SendMessageDTO;
import com.campus.application.entity.Conversation;
import com.campus.application.entity.Message;
import com.campus.application.feign.UserFeignClient;
import com.campus.application.mapper.ConversationMapper;
import com.campus.application.mapper.MessageMapper;
import com.campus.application.service.MessageService;
import com.campus.application.vo.ConversationVO;
import com.campus.application.vo.MessageVO;
import com.campus.common.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserFeignClient userFeignClient;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public Result<ConversationVO> getOrCreateConversation(Long userId, ConversationDTO dto) {
        // 查找已有会话
        LambdaQueryWrapper<Conversation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Conversation::getStudentId, Math.min(userId, dto.getToUserId()))
                .eq(Conversation::getHrId, Math.max(userId, dto.getToUserId()));
        if (dto.getJobId() != null) {
            wrapper.eq(Conversation::getJobId, dto.getJobId());
        } else {
            wrapper.isNull(Conversation::getJobId);
        }

        Conversation conversation = conversationMapper.selectOne(wrapper);

        // 如果没有则创建
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setStudentId(Math.min(userId, dto.getToUserId()));
            conversation.setHrId(Math.max(userId, dto.getToUserId()));
            conversation.setJobId(dto.getJobId());
            conversation.setLastMessage("");
            conversation.setLastTime(LocalDateTime.now());
            conversationMapper.insert(conversation);
        }

        return Result.success(convertToConversationVO(conversation, userId));
    }

    @Override
    public Result<List<ConversationVO>> getConversationList(Long userId) {
        LambdaQueryWrapper<Conversation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Conversation::getStudentId, userId)
                .or()
                .eq(Conversation::getHrId, userId)
                .orderByDesc(Conversation::getLastTime);

        List<Conversation> conversations = conversationMapper.selectList(wrapper);

        List<ConversationVO> voList = conversations.stream()
                .map(c -> convertToConversationVO(c, userId))
                .collect(Collectors.toList());

        return Result.success(voList);
    }

    @Override
    public Result<List<MessageVO>> getMessageHistory(Long userId, Long conversationId, Integer page, Integer size) {
        // 验证会话归属
        Conversation conversation = conversationMapper.selectById(conversationId);
        if (conversation == null) {
            return Result.success(List.of());
        }
        if (!conversation.getStudentId().equals(userId) && !conversation.getHrId().equals(userId)) {
            return Result.success(List.of());
        }

        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getConversationId, conversationId)
                .orderByDesc(Message::getCreateTime);

        Page<Message> pageResult = messageMapper.selectPage(new Page<>(page, size), wrapper);

        List<MessageVO> voList = pageResult.getRecords().stream()
                .map(this::convertToMessageVO)
                .collect(Collectors.toList());

        // 倒序转正序（最新的在后面）
        java.util.Collections.reverse(voList);

        return Result.success(voList);
    }

    @Override
    @Transactional
    public Result<MessageVO> sendMessage(Long userId, SendMessageDTO dto) {
        log.info("发送消息请求: userId={}, conversationId={}, content={}", userId, dto.getConversationId(), dto.getContent());

        Conversation conversation = conversationMapper.selectById(dto.getConversationId());
        if (conversation == null) {
            log.warn("会话不存在: conversationId={}", dto.getConversationId());
            return Result.fail("会话不存在");
        }

        Long studentId = conversation.getStudentId();
        Long hrId = conversation.getHrId();
        if (studentId == null || hrId == null) {
            log.warn("会话数据异常: conversationId={}, studentId={}, hrId={}", dto.getConversationId(), studentId, hrId);
            return Result.fail("会话数据异常");
        }

        if (!studentId.equals(userId) && !hrId.equals(userId)) {
            log.warn("用户无权访问此会话: userId={}, conversationId={}", userId, dto.getConversationId());
            return Result.fail("无权访问此会话");
        }

        // 确定接收者
        Long toUserId = studentId.equals(userId) ? hrId : studentId;

        // 保存消息
        Message message = new Message();
        message.setConversationId(dto.getConversationId());
        message.setFromUserId(userId);
        message.setToUserId(toUserId);
        message.setContent(dto.getContent());
        message.setMsgType(dto.getMsgType() != null ? dto.getMsgType() : 1);
        message.setIsRead(0);
        messageMapper.insert(message);
        log.info("消息已保存: messageId={}, conversationId={}", message.getId(), dto.getConversationId());

        // 更新会话最后消息
        String preview = dto.getContent();
        if (preview.length() > 50) {
            preview = preview.substring(0, 50) + "...";
        }
        conversation.setLastMessage(preview);
        conversation.setLastTime(LocalDateTime.now());
        conversationMapper.updateById(conversation);

        // 通过 WebSocket 推送给接收者
        MessageVO vo = convertToMessageVO(message);
        try {
            messagingTemplate.convertAndSendToUser(
                    String.valueOf(toUserId),
                    "/queue/messages",
                    vo
            );
            log.info("WebSocket推送成功: to={}", toUserId);
        } catch (Exception e) {
            log.warn("WebSocket推送失败，消息已保存: to={}, error={}", toUserId, e.getMessage());
        }

        log.info("消息发送成功: from={}, to={}, conversation={}", userId, toUserId, dto.getConversationId());
        return Result.success(vo);
    }

    @Override
    public Result<Integer> getUnreadCount(Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getToUserId, userId)
                .eq(Message::getIsRead, 0);
        long count = messageMapper.selectCount(wrapper);
        return Result.success((int) count);
    }

    @Override
    @Transactional
    public Result<Void> markAsRead(Long userId, Long conversationId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getConversationId, conversationId)
                .eq(Message::getToUserId, userId)
                .eq(Message::getIsRead, 0);

        Message update = new Message();
        update.setIsRead(1);
        messageMapper.update(update, wrapper);

        return Result.success();
    }

    private ConversationVO convertToConversationVO(Conversation conversation, Long currentUserId) {
        ConversationVO vo = new ConversationVO();
        vo.setId(conversation.getId());
        vo.setLastMessage(conversation.getLastMessage());
        vo.setLastTime(conversation.getLastTime());

        // 确定对方用户
        Long toUserId = conversation.getStudentId().equals(currentUserId)
                ? conversation.getHrId() : conversation.getStudentId();
        vo.setToUserId(toUserId);

        // 通过Feign获取对方用户信息
        try {
            Result<Map<String, Object>> userResult = userFeignClient.getUserInfo(toUserId);
            if (userResult != null && userResult.getData() != null) {
                Map<String, Object> userData = userResult.getData();
                String realName = (String) userData.get("realName");
                String username = (String) userData.get("username");
                vo.setToUserName(realName != null && !realName.isEmpty() ? realName : username);
                vo.setToUserAvatar((String) userData.get("avatar"));
            }
        } catch (Exception e) {
            log.warn("获取用户信息失败: userId={}, error={}", toUserId, e.getMessage());
        }

        // 查询未读数
        LambdaQueryWrapper<Message> unreadWrapper = new LambdaQueryWrapper<>();
        unreadWrapper.eq(Message::getConversationId, conversation.getId())
                .eq(Message::getToUserId, currentUserId)
                .eq(Message::getIsRead, 0);
        long unreadCount = messageMapper.selectCount(unreadWrapper);
        vo.setUnreadCount((int) unreadCount);

        return vo;
    }

    private MessageVO convertToMessageVO(Message message) {
        MessageVO vo = new MessageVO();
        vo.setId(message.getId());
        vo.setConversationId(message.getConversationId());
        vo.setFromUserId(message.getFromUserId());
        vo.setToUserId(message.getToUserId());
        vo.setContent(message.getContent());
        vo.setMsgType(message.getMsgType());
        vo.setIsRead(message.getIsRead());
        vo.setCreateTime(message.getCreateTime());
        return vo;
    }
}
