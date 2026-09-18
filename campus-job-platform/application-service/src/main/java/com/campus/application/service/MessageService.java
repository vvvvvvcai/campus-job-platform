package com.campus.application.service;

import com.campus.application.dto.ConversationDTO;
import com.campus.application.dto.SendMessageDTO;
import com.campus.application.vo.ConversationVO;
import com.campus.application.vo.MessageVO;
import com.campus.common.result.Result;

import java.util.List;

/**
 * 消息服务接口
 */
public interface MessageService {

    /**
     * 创建/获取会话
     */
    Result<ConversationVO> getOrCreateConversation(Long userId, ConversationDTO dto);

    /**
     * 获取会话列表
     */
    Result<List<ConversationVO>> getConversationList(Long userId);

    /**
     * 获取会话历史消息
     */
    Result<List<MessageVO>> getMessageHistory(Long userId, Long conversationId, Integer page, Integer size);

    /**
     * 发送消息
     */
    Result<MessageVO> sendMessage(Long userId, SendMessageDTO dto);

    /**
     * 获取未读消息总数
     */
    Result<Integer> getUnreadCount(Long userId);

    /**
     * 标记会话消息已读
     */
    Result<Void> markAsRead(Long userId, Long conversationId);
}
