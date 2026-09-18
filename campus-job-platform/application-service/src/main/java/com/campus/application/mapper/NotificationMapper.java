package com.campus.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.application.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知Mapper
 */
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
}
