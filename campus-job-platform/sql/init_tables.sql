-- ============================================
-- user-service 数据库表
-- ============================================
USE `job_user_db`;

CREATE TABLE IF NOT EXISTS `t_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
  `password` VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `role` TINYINT NOT NULL DEFAULT 0 COMMENT '角色：0-学生，1-企业HR，2-管理员',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `gender` TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_role` (`role`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- ============================================
-- company-service 数据库表
-- ============================================
USE `job_company_db`;

CREATE TABLE IF NOT EXISTS `t_company` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '企业ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `company_name` VARCHAR(100) NOT NULL COMMENT '企业名称',
  `industry` VARCHAR(50) DEFAULT NULL COMMENT '行业',
  `scale` VARCHAR(50) DEFAULT NULL COMMENT '企业规模',
  `nature` VARCHAR(50) DEFAULT NULL COMMENT '企业性质',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '企业地址',
  `website` VARCHAR(255) DEFAULT NULL COMMENT '企业官网',
  `logo` VARCHAR(255) DEFAULT NULL COMMENT '企业Logo',
  `description` TEXT COMMENT '企业描述',
  `license_url` VARCHAR(255) DEFAULT NULL COMMENT '营业执照URL',
  `audit_status` TINYINT NOT NULL DEFAULT 0 COMMENT '认证状态：0-待审核，1-已通过，2-已拒绝',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  `audit_remark` VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_industry` (`industry`),
  KEY `idx_audit_status` (`audit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='企业表';

-- ============================================
-- job-service 数据库表
-- ============================================
USE `job_job_db`;

CREATE TABLE IF NOT EXISTS `t_job` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '职位ID',
  `company_id` BIGINT NOT NULL COMMENT '关联企业ID',
  `title` VARCHAR(100) NOT NULL COMMENT '职位名称',
  `category` VARCHAR(50) DEFAULT NULL COMMENT '岗位类型',
  `city` VARCHAR(50) DEFAULT NULL COMMENT '城市',
  `industry` VARCHAR(50) DEFAULT NULL COMMENT '所属行业',
  `salary_min` INT DEFAULT NULL COMMENT '最低薪资（元/月）',
  `salary_max` INT DEFAULT NULL COMMENT '最高薪资（元/月）',
  `education` VARCHAR(20) DEFAULT NULL COMMENT '学历要求',
  `experience` VARCHAR(50) DEFAULT NULL COMMENT '经验要求',
  `job_type` TINYINT DEFAULT 1 COMMENT '工作类型：1-全职，2-实习，3-兼职',
  `description` TEXT COMMENT '职位描述',
  `requirement` TEXT COMMENT '任职要求',
  `benefits` VARCHAR(500) DEFAULT NULL COMMENT '福利待遇（JSON数组）',
  `headcount` INT DEFAULT 1 COMMENT '招聘人数',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待审核，1-招聘中，2-已关闭',
  `audit_status` TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态：0-待审核，1-已通过，2-已拒绝',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  `audit_remark` VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
  `view_count` INT DEFAULT 0 COMMENT '浏览量',
  `apply_count` INT DEFAULT 0 COMMENT '投递量',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_company_id` (`company_id`),
  KEY `idx_category` (`category`),
  KEY `idx_city` (`city`),
  KEY `idx_job_type` (`job_type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='职位表';

CREATE TABLE IF NOT EXISTS `t_favorite` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `job_id` BIGINT NOT NULL COMMENT '关联职位ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='收藏记录表';

-- ============================================
-- application-service 数据库表
-- ============================================
USE `job_application_db`;

CREATE TABLE IF NOT EXISTS `t_resume` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '简历ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `title` VARCHAR(100) NOT NULL COMMENT '简历标题',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `gender` TINYINT DEFAULT NULL COMMENT '性别：0-未知，1-男，2-女',
  `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `education` VARCHAR(20) DEFAULT NULL COMMENT '最高学历',
  `school` VARCHAR(100) DEFAULT NULL COMMENT '毕业院校',
  `major` VARCHAR(100) DEFAULT NULL COMMENT '专业',
  `graduation_year` INT DEFAULT NULL COMMENT '毕业年份',
  `work_status` TINYINT DEFAULT 0 COMMENT '工作状态：0-在校，1-应届生，2-已工作',
  `expect_city` VARCHAR(100) DEFAULT NULL COMMENT '期望城市',
  `expect_industry` VARCHAR(100) DEFAULT NULL COMMENT '期望行业',
  `expect_job_type` VARCHAR(100) DEFAULT NULL COMMENT '期望岗位类型',
  `expect_salary` VARCHAR(50) DEFAULT NULL COMMENT '期望薪资',
  `self_introduction` TEXT COMMENT '自我介绍',
  `skills` VARCHAR(500) DEFAULT NULL COMMENT '技能标签（JSON数组）',
  `experiences` TEXT COMMENT '实习/工作经历（JSON数组）',
  `projects` TEXT COMMENT '项目经历（JSON数组）',
  `awards` TEXT COMMENT '获奖经历（JSON数组）',
  `attachments` VARCHAR(500) DEFAULT NULL COMMENT '附件URL（JSON数组）',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-草稿，1-已完成',
  `is_default` TINYINT DEFAULT 0 COMMENT '是否默认简历：0-否，1-是',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='简历表';

CREATE TABLE IF NOT EXISTS `t_application` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '投递ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID（学生）',
  `job_id` BIGINT NOT NULL COMMENT '关联职位ID',
  `company_id` BIGINT NOT NULL COMMENT '关联企业ID',
  `resume_id` BIGINT NOT NULL COMMENT '关联简历ID',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '投递状态：0-待查看，1-已查看，2-面试邀请，3-不合适，4-已录用',
  `hr_remark` VARCHAR(500) DEFAULT NULL COMMENT 'HR备注',
  `interview_time` DATETIME DEFAULT NULL COMMENT '面试时间',
  `interview_address` VARCHAR(255) DEFAULT NULL COMMENT '面试地点',
  `interview_contact` VARCHAR(50) DEFAULT NULL COMMENT '面试联系人',
  `interview_contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '面试联系电话',
  `interview_result` TINYINT DEFAULT NULL COMMENT '面试结果：0-待定，1-通过，2-未通过',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
  KEY `idx_job_id` (`job_id`),
  KEY `idx_company_id` (`company_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='投递记录表';

-- ============================================
-- recommend-service 数据库表
-- ============================================
USE `job_recommend_db`;

CREATE TABLE IF NOT EXISTS `t_user_profile` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '画像ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `intent_city` VARCHAR(500) DEFAULT NULL COMMENT '意向城市（JSON数组）',
  `intent_industry` VARCHAR(500) DEFAULT NULL COMMENT '意向行业（JSON数组）',
  `intent_job_type` VARCHAR(500) DEFAULT NULL COMMENT '意向岗位类型（JSON数组）',
  `browse_count` INT DEFAULT 0 COMMENT '浏览岗位数',
  `apply_count` INT DEFAULT 0 COMMENT '投递岗位数',
  `favorite_count` INT DEFAULT 0 COMMENT '收藏岗位数',
  `last_browse_time` DATETIME DEFAULT NULL COMMENT '最后浏览时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户画像表';

CREATE TABLE IF NOT EXISTS `t_recommend_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `job_id` BIGINT NOT NULL COMMENT '关联职位ID',
  `recommend_score` INT DEFAULT 0 COMMENT '推荐得分',
  `recommend_reason` VARCHAR(255) DEFAULT NULL COMMENT '推荐理由',
  `is_clicked` TINYINT DEFAULT 0 COMMENT '是否点击：0-未点击，1-已点击',
  `is_applied` TINYINT DEFAULT 0 COMMENT '是否投递：0-未投递，1-已投递',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_job_id` (`job_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='推荐日志表';

-- ============================================
-- 消息模块数据库表
-- ============================================

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
