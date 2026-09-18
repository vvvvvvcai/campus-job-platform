package com.campus.application.controller;

import com.campus.application.dto.ConversationDTO;
import com.campus.application.dto.SendMessageDTO;
import com.campus.application.service.MessageService;
import com.campus.application.vo.ConversationVO;
import com.campus.application.vo.MessageVO;
import com.campus.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 消息控制器
 */
@Api(tags = "消息管理")
@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @ApiOperation("创建/获取会话")
    @PostMapping("/conversation")
    public Result<ConversationVO> getOrCreateConversation(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid ConversationDTO dto) {
        return messageService.getOrCreateConversation(userId, dto);
    }

    @ApiOperation("获取会话列表")
    @GetMapping("/conversations")
    public Result<List<ConversationVO>> getConversationList(
            @RequestHeader("X-User-Id") Long userId) {
        return messageService.getConversationList(userId);
    }

    @ApiOperation("获取会话历史消息")
    @GetMapping("/history/{conversationId}")
    public Result<List<MessageVO>> getMessageHistory(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long conversationId,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam("每页大小") @RequestParam(defaultValue = "20") Integer size) {
        return messageService.getMessageHistory(userId, conversationId, page, size);
    }

    @ApiOperation("发送消息")
    @PostMapping("/send")
    public Result<MessageVO> sendMessage(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid SendMessageDTO dto) {
        return messageService.sendMessage(userId, dto);
    }

    @ApiOperation("获取未读消息总数")
    @GetMapping("/unread-count")
    public Result<Integer> getUnreadCount(
            @RequestHeader("X-User-Id") Long userId) {
        return messageService.getUnreadCount(userId);
    }

    @ApiOperation("标记会话消息已读")
    @PutMapping("/read/{conversationId}")
    public Result<Void> markAsRead(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long conversationId) {
        return messageService.markAsRead(userId, conversationId);
    }
}
