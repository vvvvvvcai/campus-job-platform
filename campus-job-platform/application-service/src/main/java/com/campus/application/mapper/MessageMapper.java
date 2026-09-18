package com.campus.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.application.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息Mapper
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
