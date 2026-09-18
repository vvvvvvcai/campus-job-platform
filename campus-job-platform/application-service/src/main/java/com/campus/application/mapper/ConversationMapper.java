package com.campus.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.application.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会话Mapper
 */
@Mapper
public interface ConversationMapper extends BaseMapper<Conversation> {
}
