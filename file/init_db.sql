-- ============================================
-- 大学生实习就业综合服务平台 数据库初始化脚本
-- 数据库名: campus_job_platform
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `campus_job_platform` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `campus_job_platform`;

-- ============================================
-- 1. 用户表 (t_user)
-- ============================================
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `role` TINYINT NOT NULL DEFAULT 1 COMMENT '角色: 1-学生 2-企业 3-管理员',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用 1-正常',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_role` (`role`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户基本信息表';

-- ============================================
-- 2. 企业信息表 (t_company)
-- ============================================
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '企业ID',
    `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
    `company_name` VARCHAR(100) NOT NULL COMMENT '企业名称',
    `industry` VARCHAR(50) DEFAULT NULL COMMENT '所属行业',
    `company_size` VARCHAR(30) DEFAULT NULL COMMENT '企业规模(如: 100-499人)',
    `province` VARCHAR(30) DEFAULT NULL COMMENT '省份',
    `city` VARCHAR(30) DEFAULT NULL COMMENT '城市',
    `address` VARCHAR(255) DEFAULT NULL COMMENT '详细地址',
    `logo` VARCHAR(255) DEFAULT NULL COMMENT '企业Logo',
    `description` TEXT COMMENT '企业简介',
    `contact_person` VARCHAR(50) DEFAULT NULL COMMENT '联系人',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `contact_email` VARCHAR(100) DEFAULT NULL COMMENT '联系邮箱',
    `license_url` VARCHAR(255) DEFAULT NULL COMMENT '营业执照图片URL',
    `audit_status` TINYINT NOT NULL DEFAULT 0 COMMENT '认证状态: 0-待审核 1-认证通过 2-认证失败',
    `audit_remark` VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    KEY `idx_company_name` (`company_name`),
    KEY `idx_industry` (`industry`),
    KEY `idx_city` (`city`),
    KEY `idx_audit_status` (`audit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业信息表';

-- ============================================
-- 3. 职位信息表 (t_job)
-- ============================================
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '职位ID',
    `company_id` BIGINT NOT NULL COMMENT '企业ID',
    `title` VARCHAR(100) NOT NULL COMMENT '职位名称',
    `city` VARCHAR(30) NOT NULL COMMENT '工作城市',
    `industry` VARCHAR(50) DEFAULT NULL COMMENT '所属行业',
    `job_type` TINYINT NOT NULL COMMENT '岗位类型: 1-实习 2-全职',
    `salary_min` DECIMAL(10,2) DEFAULT NULL COMMENT '最低薪资(元/月)',
    `salary_max` DECIMAL(10,2) DEFAULT NULL COMMENT '最高薪资(元/月)',
    `education` VARCHAR(20) DEFAULT NULL COMMENT '学历要求',
    `description` TEXT COMMENT '职位描述',
    `requirements` TEXT COMMENT '任职要求',
    `headcount` INT DEFAULT 1 COMMENT '招聘人数',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-待审核 1-招聘中 2-已下架 3-审核不通过',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
    `apply_count` INT NOT NULL DEFAULT 0 COMMENT '投递次数',
    `publish_time` DATETIME DEFAULT NULL COMMENT '发布时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_company_id` (`company_id`),
    KEY `idx_title` (`title`),
    KEY `idx_city` (`city`),
    KEY `idx_industry` (`industry`),
    KEY `idx_job_type` (`job_type`),
    KEY `idx_status` (`status`),
    KEY `idx_publish_time` (`publish_time`),
    FULLTEXT KEY `ft_title_description` (`title`, `description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='职位信息表';

-- ============================================
-- 4. 学生简历表 (t_resume)
-- ============================================
DROP TABLE IF EXISTS `t_resume`;
CREATE TABLE `t_resume` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '简历ID',
    `user_id` BIGINT NOT NULL COMMENT '学生用户ID',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` TINYINT DEFAULT NULL COMMENT '性别: 1-男 2-女',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `education` VARCHAR(20) DEFAULT NULL COMMENT '最高学历',
    `school` VARCHAR(100) DEFAULT NULL COMMENT '毕业院校',
    `major` VARCHAR(100) DEFAULT NULL COMMENT '专业',
    `graduation_year` INT DEFAULT NULL COMMENT '毕业年份',
    `intention_city` VARCHAR(100) DEFAULT NULL COMMENT '意向城市(多个用逗号分隔)',
    `intention_industry` VARCHAR(100) DEFAULT NULL COMMENT '意向行业(多个用逗号分隔)',
    `intention_job_type` VARCHAR(50) DEFAULT NULL COMMENT '意向岗位类型(多个用逗号分隔)',
    `work_experience` TEXT COMMENT '工作/实习经历',
    `project_experience` TEXT COMMENT '项目经历',
    `skills` VARCHAR(500) DEFAULT NULL COMMENT '技能标签(逗号分隔)',
    `certificates` VARCHAR(500) DEFAULT NULL COMMENT '证书(逗号分隔)',
    `self_intro` TEXT COMMENT '自我介绍',
    `attachment_url` VARCHAR(255) DEFAULT NULL COMMENT '附件简历URL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-草稿 1-已完成',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_education` (`education`),
    KEY `idx_school` (`school`),
    KEY `idx_graduation_year` (`graduation_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生简历表';

-- ============================================
-- 5. 投递记录表 (t_application)
-- ============================================
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '投递ID',
    `user_id` BIGINT NOT NULL COMMENT '学生用户ID',
    `job_id` BIGINT NOT NULL COMMENT '职位ID',
    `resume_id` BIGINT NOT NULL COMMENT '简历ID',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-待查看 1-已查看 2-面试邀请 3-不合适',
    `interview_time` DATETIME DEFAULT NULL COMMENT '面试时间',
    `interview_address` VARCHAR(255) DEFAULT NULL COMMENT '面试地点',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注/反馈',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '投递时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
    KEY `idx_job_id` (`job_id`),
    KEY `idx_resume_id` (`resume_id`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='投递记录表';

-- ============================================
-- 6. 收藏记录表 (t_favorite)
-- ============================================
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `job_id` BIGINT NOT NULL COMMENT '职位ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
    KEY `idx_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏记录表';

-- ============================================
-- 7. 站内信表 (t_message)
-- ============================================
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `title` VARCHAR(100) NOT NULL COMMENT '消息标题',
    `content` TEXT COMMENT '消息内容',
    `type` TINYINT NOT NULL DEFAULT 1 COMMENT '类型: 1-系统通知 2-面试邀请 3-投递反馈',
    `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '已读状态: 0-未读 1-已读',
    `related_id` BIGINT DEFAULT NULL COMMENT '关联ID(如投递ID、职位ID)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_type` (`type`),
    KEY `idx_is_read` (`is_read`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='站内信表';

-- ============================================
-- 8. 用户画像标签表 (t_user_profile)
-- ============================================
DROP TABLE IF EXISTS `t_user_profile`;
CREATE TABLE `t_user_profile` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '画像ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `intention_city` VARCHAR(100) DEFAULT NULL COMMENT '意向城市(多个用逗号分隔)',
    `intention_industry` VARCHAR(100) DEFAULT NULL COMMENT '意向行业(多个用逗号分隔)',
    `intention_job_type` VARCHAR(50) DEFAULT NULL COMMENT '意向岗位类型(多个用逗号分隔)',
    `education` VARCHAR(20) DEFAULT NULL COMMENT '学历',
    `school` VARCHAR(100) DEFAULT NULL COMMENT '学校',
    `major` VARCHAR(100) DEFAULT NULL COMMENT '专业',
    `graduation_year` INT DEFAULT NULL COMMENT '毕业年份',
    `skill_tags` VARCHAR(500) DEFAULT NULL COMMENT '技能标签',
    `browse_history` TEXT COMMENT '浏览历史标签(JSON)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户画像标签表';

-- ============================================
-- 9. 推荐记录日志表 (t_recommend_log)
-- ============================================
DROP TABLE IF EXISTS `t_recommend_log`;
CREATE TABLE `t_recommend_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `job_id` BIGINT NOT NULL COMMENT '职位ID',
    `score` DECIMAL(5,2) DEFAULT NULL COMMENT '推荐得分',
    `reason` VARCHAR(255) DEFAULT NULL COMMENT '推荐理由',
    `match_city` TINYINT DEFAULT 0 COMMENT '是否城市匹配: 0-否 1-是',
    `match_industry` TINYINT DEFAULT 0 COMMENT '是否行业匹配: 0-否 1-是',
    `match_job_type` TINYINT DEFAULT 0 COMMENT '是否岗位类型匹配: 0-否 1-是',
    `is_clicked` TINYINT NOT NULL DEFAULT 0 COMMENT '是否点击: 0-未点击 1-已点击',
    `is_applied` TINYINT NOT NULL DEFAULT 0 COMMENT '是否投递: 0-未投递 1-已投递',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_job_id` (`job_id`),
    KEY `idx_score` (`score`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推荐记录日志表';

-- ============================================
-- 初始化管理员账号
-- ============================================
INSERT INTO `t_user` (`phone`, `password`, `nickname`, `role`, `status`)
VALUES ('13800000000', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', 3, 1);
