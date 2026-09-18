-- ============================================
-- 消息模块数据库表
-- ============================================

-- 会话表
CREATE TABLE IF NOT EXISTS `t_conversation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `student_id` BIGINT NOT NULL COMMENT '学生用户ID',
  `hr_id` BIGINT NOT NULL COMMENT 'HR用户ID',
  `job_id` BIGINT DEFAULT NULL COMMENT '关联职位ID',
  `last_message` VARCHAR(200) DEFAULT NULL COMMENT '最后一条消息摘要',
  `last_time` DATETIME DEFAULT NULL COMMENT '最后消息时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_hr_job` (`student_id`, `hr_id`, `job_id`),
  KEY `idx_student` (`student_id`),
  KEY `idx_hr` (`hr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='聊天会话表';

-- 消息表
CREATE TABLE IF NOT EXISTS `t_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `conversation_id` BIGINT NOT NULL COMMENT '关联会话ID',
  `from_user_id` BIGINT NOT NULL COMMENT '发送者ID',
  `to_user_id` BIGINT NOT NULL COMMENT '接收者ID',
  `content` TEXT NOT NULL COMMENT '消息内容',
  `msg_type` TINYINT DEFAULT 1 COMMENT '消息类型：1-文本 2-图片 3-系统通知',
  `is_read` TINYINT DEFAULT 0 COMMENT '已读状态：0-未读 1-已读',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_conversation` (`conversation_id`),
  KEY `idx_to_user` (`to_user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='聊天消息表';

-- 系统通知表
CREATE TABLE IF NOT EXISTS `t_notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `user_id` BIGINT NOT NULL COMMENT '接收者ID',
  `title` VARCHAR(100) NOT NULL COMMENT '通知标题',
  `content` TEXT COMMENT '通知内容',
  `noti_type` TINYINT DEFAULT 1 COMMENT '类型：1-投递通知 2-面试通知 3-系统公告',
  `related_id` BIGINT DEFAULT NULL COMMENT '关联ID（投递ID/面试ID等）',
  `is_read` TINYINT DEFAULT 0 COMMENT '已读状态：0-未读 1-已读',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统通知表';
