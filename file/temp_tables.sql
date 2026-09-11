-- ============================================
-- 澶у鐢熷疄涔犲氨涓氱患鍚堟湇鍔″钩鍙?鏁版嵁搴撳垵濮嬪寲鑴氭湰
-- 鏁版嵁搴撳悕: campus_job_platform
-- ============================================

-- 鍒涘缓鏁版嵁搴?

USE `campus_job_platform`;

-- ============================================
-- 1. 鐢ㄦ埛琛?(t_user)
-- ============================================
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '鐢ㄦ埛ID',
    `phone` VARCHAR(20) NOT NULL COMMENT '鎵嬫満鍙?,
    `password` VARCHAR(255) NOT NULL COMMENT '瀵嗙爜(BCrypt鍔犲瘑)',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '鏄电О',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '澶村儚URL',
    `role` TINYINT NOT NULL DEFAULT 1 COMMENT '瑙掕壊: 1-瀛︾敓 2-浼佷笟 3-绠＄悊鍛?,
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '鐘舵€? 0-绂佺敤 1-姝ｅ父',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '鏈€鍚庣櫥褰曟椂闂?,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '閫昏緫鍒犻櫎: 0-鏈垹闄?1-宸插垹闄?,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_role` (`role`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鐢ㄦ埛鍩烘湰淇℃伅琛?;

-- ============================================
-- 2. 浼佷笟淇℃伅琛?(t_company)
-- ============================================
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '浼佷笟ID',
    `user_id` BIGINT NOT NULL COMMENT '鍏宠仈鐢ㄦ埛ID',
    `company_name` VARCHAR(100) NOT NULL COMMENT '浼佷笟鍚嶇О',
    `industry` VARCHAR(50) DEFAULT NULL COMMENT '鎵€灞炶涓?,
    `company_size` VARCHAR(30) DEFAULT NULL COMMENT '浼佷笟瑙勬ā(濡? 100-499浜?',
    `province` VARCHAR(30) DEFAULT NULL COMMENT '鐪佷唤',
    `city` VARCHAR(30) DEFAULT NULL COMMENT '鍩庡競',
    `address` VARCHAR(255) DEFAULT NULL COMMENT '璇︾粏鍦板潃',
    `logo` VARCHAR(255) DEFAULT NULL COMMENT '浼佷笟Logo',
    `description` TEXT COMMENT '浼佷笟绠€浠?,
    `contact_person` VARCHAR(50) DEFAULT NULL COMMENT '鑱旂郴浜?,
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '鑱旂郴鐢佃瘽',
    `contact_email` VARCHAR(100) DEFAULT NULL COMMENT '鑱旂郴閭',
    `license_url` VARCHAR(255) DEFAULT NULL COMMENT '钀ヤ笟鎵х収鍥剧墖URL',
    `audit_status` TINYINT NOT NULL DEFAULT 0 COMMENT '璁よ瘉鐘舵€? 0-寰呭鏍?1-璁よ瘉閫氳繃 2-璁よ瘉澶辫触',
    `audit_remark` VARCHAR(255) DEFAULT NULL COMMENT '瀹℃牳澶囨敞',
    `audit_time` DATETIME DEFAULT NULL COMMENT '瀹℃牳鏃堕棿',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '閫昏緫鍒犻櫎: 0-鏈垹闄?1-宸插垹闄?,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    KEY `idx_company_name` (`company_name`),
    KEY `idx_industry` (`industry`),
    KEY `idx_city` (`city`),
    KEY `idx_audit_status` (`audit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='浼佷笟淇℃伅琛?;

-- ============================================
-- 3. 鑱屼綅淇℃伅琛?(t_job)
-- ============================================
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '鑱屼綅ID',
    `company_id` BIGINT NOT NULL COMMENT '浼佷笟ID',
    `title` VARCHAR(100) NOT NULL COMMENT '鑱屼綅鍚嶇О',
    `city` VARCHAR(30) NOT NULL COMMENT '宸ヤ綔鍩庡競',
    `industry` VARCHAR(50) DEFAULT NULL COMMENT '鎵€灞炶涓?,
    `job_type` TINYINT NOT NULL COMMENT '宀椾綅绫诲瀷: 1-瀹炰範 2-鍏ㄨ亴',
    `salary_min` DECIMAL(10,2) DEFAULT NULL COMMENT '鏈€浣庤柂璧?鍏?鏈?',
    `salary_max` DECIMAL(10,2) DEFAULT NULL COMMENT '鏈€楂樿柂璧?鍏?鏈?',
    `education` VARCHAR(20) DEFAULT NULL COMMENT '瀛﹀巻瑕佹眰',
    `description` TEXT COMMENT '鑱屼綅鎻忚堪',
    `requirements` TEXT COMMENT '浠昏亴瑕佹眰',
    `headcount` INT DEFAULT 1 COMMENT '鎷涜仒浜烘暟',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '鐘舵€? 0-寰呭鏍?1-鎷涜仒涓?2-宸蹭笅鏋?3-瀹℃牳涓嶉€氳繃',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '娴忚娆℃暟',
    `apply_count` INT NOT NULL DEFAULT 0 COMMENT '鎶曢€掓鏁?,
    `publish_time` DATETIME DEFAULT NULL COMMENT '鍙戝竷鏃堕棿',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '閫昏緫鍒犻櫎: 0-鏈垹闄?1-宸插垹闄?,
    PRIMARY KEY (`id`),
    KEY `idx_company_id` (`company_id`),
    KEY `idx_title` (`title`),
    KEY `idx_city` (`city`),
    KEY `idx_industry` (`industry`),
    KEY `idx_job_type` (`job_type`),
    KEY `idx_status` (`status`),
    KEY `idx_publish_time` (`publish_time`),
    FULLTEXT KEY `ft_title_description` (`title`, `description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鑱屼綅淇℃伅琛?;

-- ============================================
-- 4. 瀛︾敓绠€鍘嗚〃 (t_resume)
-- ============================================
DROP TABLE IF EXISTS `t_resume`;
CREATE TABLE `t_resume` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '绠€鍘咺D',
    `user_id` BIGINT NOT NULL COMMENT '瀛︾敓鐢ㄦ埛ID',
    `name` VARCHAR(50) NOT NULL COMMENT '濮撳悕',
    `gender` TINYINT DEFAULT NULL COMMENT '鎬у埆: 1-鐢?2-濂?,
    `birth_date` DATE DEFAULT NULL COMMENT '鍑虹敓鏃ユ湡',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '鑱旂郴鐢佃瘽',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '閭',
    `education` VARCHAR(20) DEFAULT NULL COMMENT '鏈€楂樺鍘?,
    `school` VARCHAR(100) DEFAULT NULL COMMENT '姣曚笟闄㈡牎',
    `major` VARCHAR(100) DEFAULT NULL COMMENT '涓撲笟',
    `graduation_year` INT DEFAULT NULL COMMENT '姣曚笟骞翠唤',
    `intention_city` VARCHAR(100) DEFAULT NULL COMMENT '鎰忓悜鍩庡競(澶氫釜鐢ㄩ€楀彿鍒嗛殧)',
    `intention_industry` VARCHAR(100) DEFAULT NULL COMMENT '鎰忓悜琛屼笟(澶氫釜鐢ㄩ€楀彿鍒嗛殧)',
    `intention_job_type` VARCHAR(50) DEFAULT NULL COMMENT '鎰忓悜宀椾綅绫诲瀷(澶氫釜鐢ㄩ€楀彿鍒嗛殧)',
    `work_experience` TEXT COMMENT '宸ヤ綔/瀹炰範缁忓巻',
    `project_experience` TEXT COMMENT '椤圭洰缁忓巻',
    `skills` VARCHAR(500) DEFAULT NULL COMMENT '鎶€鑳芥爣绛?閫楀彿鍒嗛殧)',
    `certificates` VARCHAR(500) DEFAULT NULL COMMENT '璇佷功(閫楀彿鍒嗛殧)',
    `self_intro` TEXT COMMENT '鑷垜浠嬬粛',
    `attachment_url` VARCHAR(255) DEFAULT NULL COMMENT '闄勪欢绠€鍘哢RL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '鐘舵€? 0-鑽夌 1-宸插畬鎴?,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '閫昏緫鍒犻櫎: 0-鏈垹闄?1-宸插垹闄?,
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_education` (`education`),
    KEY `idx_school` (`school`),
    KEY `idx_graduation_year` (`graduation_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='瀛︾敓绠€鍘嗚〃';

-- ============================================
-- 5. 鎶曢€掕褰曡〃 (t_application)
-- ============================================
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '鎶曢€扞D',
    `user_id` BIGINT NOT NULL COMMENT '瀛︾敓鐢ㄦ埛ID',
    `job_id` BIGINT NOT NULL COMMENT '鑱屼綅ID',
    `resume_id` BIGINT NOT NULL COMMENT '绠€鍘咺D',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '鐘舵€? 0-寰呮煡鐪?1-宸叉煡鐪?2-闈㈣瘯閭€璇?3-涓嶅悎閫?,
    `interview_time` DATETIME DEFAULT NULL COMMENT '闈㈣瘯鏃堕棿',
    `interview_address` VARCHAR(255) DEFAULT NULL COMMENT '闈㈣瘯鍦扮偣',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '澶囨敞/鍙嶉',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鎶曢€掓椂闂?,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
    KEY `idx_job_id` (`job_id`),
    KEY `idx_resume_id` (`resume_id`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鎶曢€掕褰曡〃';

-- ============================================
-- 6. 鏀惰棌璁板綍琛?(t_favorite)
-- ============================================
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '鏀惰棌ID',
    `user_id` BIGINT NOT NULL COMMENT '鐢ㄦ埛ID',
    `job_id` BIGINT NOT NULL COMMENT '鑱屼綅ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鏀惰棌鏃堕棿',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
    KEY `idx_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鏀惰棌璁板綍琛?;

-- ============================================
-- 7. 绔欏唴淇¤〃 (t_message)
-- ============================================
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '娑堟伅ID',
    `user_id` BIGINT NOT NULL COMMENT '鎺ユ敹鐢ㄦ埛ID',
    `title` VARCHAR(100) NOT NULL COMMENT '娑堟伅鏍囬',
    `content` TEXT COMMENT '娑堟伅鍐呭',
    `type` TINYINT NOT NULL DEFAULT 1 COMMENT '绫诲瀷: 1-绯荤粺閫氱煡 2-闈㈣瘯閭€璇?3-鎶曢€掑弽棣?,
    `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '宸茶鐘舵€? 0-鏈 1-宸茶',
    `related_id` BIGINT DEFAULT NULL COMMENT '鍏宠仈ID(濡傛姇閫扞D銆佽亴浣岻D)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_type` (`type`),
    KEY `idx_is_read` (`is_read`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绔欏唴淇¤〃';

-- ============================================
-- 8. 鐢ㄦ埛鐢诲儚鏍囩琛?(t_user_profile)
-- ============================================
DROP TABLE IF EXISTS `t_user_profile`;
CREATE TABLE `t_user_profile` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '鐢诲儚ID',
    `user_id` BIGINT NOT NULL COMMENT '鐢ㄦ埛ID',
    `intention_city` VARCHAR(100) DEFAULT NULL COMMENT '鎰忓悜鍩庡競(澶氫釜鐢ㄩ€楀彿鍒嗛殧)',
    `intention_industry` VARCHAR(100) DEFAULT NULL COMMENT '鎰忓悜琛屼笟(澶氫釜鐢ㄩ€楀彿鍒嗛殧)',
    `intention_job_type` VARCHAR(50) DEFAULT NULL COMMENT '鎰忓悜宀椾綅绫诲瀷(澶氫釜鐢ㄩ€楀彿鍒嗛殧)',
    `education` VARCHAR(20) DEFAULT NULL COMMENT '瀛﹀巻',
    `school` VARCHAR(100) DEFAULT NULL COMMENT '瀛︽牎',
    `major` VARCHAR(100) DEFAULT NULL COMMENT '涓撲笟',
    `graduation_year` INT DEFAULT NULL COMMENT '姣曚笟骞翠唤',
    `skill_tags` VARCHAR(500) DEFAULT NULL COMMENT '鎶€鑳芥爣绛?,
    `browse_history` TEXT COMMENT '娴忚鍘嗗彶鏍囩(JSON)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鐢ㄦ埛鐢诲儚鏍囩琛?;

-- ============================================
-- 9. 鎺ㄨ崘璁板綍鏃ュ織琛?(t_recommend_log)
-- ============================================
DROP TABLE IF EXISTS `t_recommend_log`;
CREATE TABLE `t_recommend_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '鏃ュ織ID',
    `user_id` BIGINT NOT NULL COMMENT '鐢ㄦ埛ID',
    `job_id` BIGINT NOT NULL COMMENT '鑱屼綅ID',
    `score` DECIMAL(5,2) DEFAULT NULL COMMENT '鎺ㄨ崘寰楀垎',
    `reason` VARCHAR(255) DEFAULT NULL COMMENT '鎺ㄨ崘鐞嗙敱',
    `match_city` TINYINT DEFAULT 0 COMMENT '鏄惁鍩庡競鍖归厤: 0-鍚?1-鏄?,
    `match_industry` TINYINT DEFAULT 0 COMMENT '鏄惁琛屼笟鍖归厤: 0-鍚?1-鏄?,
    `match_job_type` TINYINT DEFAULT 0 COMMENT '鏄惁宀椾綅绫诲瀷鍖归厤: 0-鍚?1-鏄?,
    `is_clicked` TINYINT NOT NULL DEFAULT 0 COMMENT '鏄惁鐐瑰嚮: 0-鏈偣鍑?1-宸茬偣鍑?,
    `is_applied` TINYINT NOT NULL DEFAULT 0 COMMENT '鏄惁鎶曢€? 0-鏈姇閫?1-宸叉姇閫?,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_job_id` (`job_id`),
    KEY `idx_score` (`score`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鎺ㄨ崘璁板綍鏃ュ織琛?;

-- ============================================
-- 鍒濆鍖栫鐞嗗憳璐﹀彿
-- ============================================
INSERT INTO `t_user` (`phone`, `password`, `nickname`, `role`, `status`)
VALUES ('13800000000', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '绯荤粺绠＄悊鍛?, 3, 1);

