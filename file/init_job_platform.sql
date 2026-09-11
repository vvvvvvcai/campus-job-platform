-- ============================================
-- 大学生实习就业综合服务平台 数据库初始化脚本
-- 数据库名: job_platform
-- 基于 database_design.md
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `job_platform` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `job_platform`;

-- ============================================
-- 1. 用户表（t_user）
-- ============================================
CREATE TABLE `t_user` (
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
-- 2. 企业表（t_company）
-- ============================================
CREATE TABLE `t_company` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '企业ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `company_name` VARCHAR(100) NOT NULL COMMENT '企业名称',
  `industry` VARCHAR(50) DEFAULT NULL COMMENT '行业（关联数据字典）',
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
-- 3. 职位表（t_job）
-- ============================================
CREATE TABLE `t_job` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '职位ID',
  `company_id` BIGINT NOT NULL COMMENT '关联企业ID',
  `title` VARCHAR(100) NOT NULL COMMENT '职位名称',
  `category` VARCHAR(50) DEFAULT NULL COMMENT '岗位类型（关联数据字典）',
  `city` VARCHAR(50) DEFAULT NULL COMMENT '城市（关联数据字典）',
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

-- ============================================
-- 4. 简历表（t_resume）
-- ============================================
CREATE TABLE `t_resume` (
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
  `expect_city` VARCHAR(100) DEFAULT NULL COMMENT '期望城市（多个用逗号分隔）',
  `expect_industry` VARCHAR(100) DEFAULT NULL COMMENT '期望行业（多个用逗号分隔）',
  `expect_job_type` VARCHAR(100) DEFAULT NULL COMMENT '期望岗位类型（多个用逗号分隔）',
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

-- ============================================
-- 5. 投递记录表（t_application）
-- ============================================
CREATE TABLE `t_application` (
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
-- 6. 收藏记录表（t_favorite）
-- ============================================
CREATE TABLE `t_favorite` (
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
-- 7. 消息表（t_message）
-- ============================================
CREATE TABLE `t_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
  `title` VARCHAR(100) NOT NULL COMMENT '消息标题',
  `content` TEXT NOT NULL COMMENT '消息内容',
  `type` TINYINT NOT NULL COMMENT '消息类型：1-系统通知，2-投递通知，3-面试通知，4-其他',
  `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
  `related_id` BIGINT DEFAULT NULL COMMENT '关联业务ID（如投递ID）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_type` (`type`),
  KEY `idx_is_read` (`is_read`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息表';

-- ============================================
-- 8. 用户画像表（t_user_profile）
-- ============================================
CREATE TABLE `t_user_profile` (
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

-- ============================================
-- 9. 推荐日志表（t_recommend_log）
-- ============================================
CREATE TABLE `t_recommend_log` (
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
-- 10. 数据字典表（t_dict）
-- ============================================
CREATE TABLE `t_dict` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_type` VARCHAR(50) NOT NULL COMMENT '字典类型',
  `dict_code` VARCHAR(50) NOT NULL COMMENT '字典编码',
  `dict_name` VARCHAR(100) NOT NULL COMMENT '字典名称',
  `dict_value` VARCHAR(255) NOT NULL COMMENT '字典值',
  `sort_order` INT DEFAULT 0 COMMENT '排序号',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_code` (`dict_type`, `dict_code`),
  KEY `idx_dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据字典表';

-- ============================================
-- 初始化数据：数据字典
-- ============================================

-- 行业字典
INSERT INTO `t_dict` (`dict_type`, `dict_code`, `dict_name`, `dict_value`, `sort_order`) VALUES
('industry', 'IT', '互联网/IT', 'IT', 1),
('industry', 'FINANCE', '金融', 'FINANCE', 2),
('industry', 'EDUCATION', '教育', 'EDUCATION', 3),
('industry', 'MANUFACTURING', '制造业', 'MANUFACTURING', 4),
('industry', 'HEALTHCARE', '医疗健康', 'HEALTHCARE', 5),
('industry', 'RETAIL', '零售/电商', 'RETAIL', 6),
('industry', 'MEDIA', '媒体/广告', 'MEDIA', 7),
('industry', 'REAL_ESTATE', '房地产', 'REAL_ESTATE', 8),
('industry', 'CONSULTING', '咨询', 'CONSULTING', 9),
('industry', 'OTHER', '其他', 'OTHER', 10);

-- 城市字典
INSERT INTO `t_dict` (`dict_type`, `dict_code`, `dict_name`, `dict_value`, `sort_order`) VALUES
('city', 'BEIJING', '北京', 'BEIJING', 1),
('city', 'SHANGHAI', '上海', 'SHANGHAI', 2),
('city', 'GUANGZHOU', '广州', 'GUANGZHOU', 3),
('city', 'SHENZHEN', '深圳', 'SHENZHEN', 4),
('city', 'HANGZHOU', '杭州', 'HANGZHOU', 5),
('city', 'CHENGDU', '成都', 'CHENGDU', 6),
('city', 'NANJING', '南京', 'NANJING', 7),
('city', 'WUHAN', '武汉', 'WUHAN', 8),
('city', 'XI_AN', '西安', 'XI_AN', 9),
('city', 'CHANGSHA', '长沙', 'CHANGSHA', 10);

-- 岗位类型字典
INSERT INTO `t_dict` (`dict_type`, `dict_code`, `dict_name`, `dict_value`, `sort_order`) VALUES
('job_category', 'DEVELOPMENT', '开发', 'DEVELOPMENT', 1),
('job_category', 'TEST', '测试', 'TEST', 2),
('job_category', 'PRODUCT', '产品', 'PRODUCT', 3),
('job_category', 'DESIGN', '设计', 'DESIGN', 4),
('job_category', 'OPERATION', '运营', 'OPERATION', 5),
('job_category', 'MARKET', '市场', 'MARKET', 6),
('job_category', 'SALES', '销售', 'SALES', 7),
('job_category', 'HR', '人力资源', 'HR', 8),
('job_category', 'FINANCE', '财务', 'FINANCE', 9),
('job_category', 'ADMIN', '行政', 'ADMIN', 10);

-- 企业规模字典
INSERT INTO `t_dict` (`dict_type`, `dict_code`, `dict_name`, `dict_value`, `sort_order`) VALUES
('company_scale', 'STARTUP', '初创（1-50人）', 'STARTUP', 1),
('company_scale', 'SMALL', '小型（51-200人）', 'SMALL', 2),
('company_scale', 'MEDIUM', '中型（201-500人）', 'MEDIUM', 3),
('company_scale', 'LARGE', '大型（501-1000人）', 'LARGE', 4),
('company_scale', 'ENTERPRISE', '超大（1000人以上）', 'ENTERPRISE', 5);

-- 企业性质字典
INSERT INTO `t_dict` (`dict_type`, `dict_code`, `dict_name`, `dict_value`, `sort_order`) VALUES
('company_nature', 'PRIVATE', '民营企业', 'PRIVATE', 1),
('company_nature', 'STATE_OWNED', '国有企业', 'STATE_OWNED', 2),
('company_nature', 'FOREIGN', '外资企业', 'FOREIGN', 3),
('company_nature', 'JOINT_VENTURE', '合资企业', 'JOINT_VENTURE', 4),
('company_nature', 'GOVERNMENT', '政府/事业单位', 'GOVERNMENT', 5);

-- 学历字典
INSERT INTO `t_dict` (`dict_type`, `dict_code`, `dict_name`, `dict_value`, `sort_order`) VALUES
('education', 'COLLEGE', '大专', 'COLLEGE', 1),
('education', 'BACHELOR', '本科', 'BACHELOR', 2),
('education', 'MASTER', '硕士', 'MASTER', 3),
('education', 'PHD', '博士', 'PHD', 4);

-- ============================================
-- 初始化管理员账号
-- ============================================
INSERT INTO `t_user` (`phone`, `password`, `username`, `role`, `status`)
VALUES ('13800000000', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'admin', 2, 1);
