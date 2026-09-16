-- 测试数据：用户数据
-- 密码统一为 123456 (BCrypt加密)

-- 学生用户 (role=0)
INSERT INTO t_user (phone, password, username, role, real_name, gender, email, status) VALUES
('13800000001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'zhangsan', 0, '张三', 1, 'zhangsan@edu.cn', 1),
('13800000002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'lisi', 0, '李四', 1, 'lisi@edu.cn', 1),
('13800000003', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'wangwu', 0, '王五', 1, 'wangwu@edu.cn', 1),
('13800000004', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'zhaoliu', 0, '赵六', 2, 'zhaoliu@edu.cn', 1),
('13800000005', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'sunqi', 0, '孙七', 2, 'sunqi@edu.cn', 1),
('13800000006', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'zhouba', 0, '周八', 1, 'zhouba@edu.cn', 1),
('13800000007', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'wujiu', 0, '吴九', 1, 'wujiu@edu.cn', 1),
('13800000008', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'zhengshi', 0, '郑十', 2, 'zhengshi@edu.cn', 1);

-- 企业HR用户 (role=1)
INSERT INTO t_user (phone, password, username, role, real_name, gender, email, status) VALUES
('13800000011', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'hr_zhang', 1, '张经理', 1, 'hr_zhang@company.com', 1),
('13800000012', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'hr_li', 1, '李经理', 2, 'hr_li@company.com', 1),
('13800000013', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'hr_wang', 1, '王总监', 1, 'hr_wang@company.com', 1);

-- 企业数据 (关联HR用户)
INSERT INTO t_company (user_id, company_name, industry, scale, nature, address, description, audit_status) VALUES
(9, '阿里巴巴集团', '互联网', '10000人以上', '上市公司', '杭州市余杭区文一西路969号', '阿里巴巴集团是全球领先的电子商务公司', 1),
(10, '腾讯科技', '互联网', '10000人以上', '上市公司', '深圳市南山区科技中一路腾讯大厦', '腾讯是中国领先的互联网增值服务提供商', 1),
(11, '字节跳动', '互联网', '10000人以上', '民营企业', '北京市海淀区北三环西路甲18号', '字节跳动是全球领先的短视频和信息分发平台', 1);

-- 职位数据
INSERT INTO t_job (company_id, title, category, city, salary_min, salary_max, education, experience, job_type, description, requirement, benefits, headcount, status, audit_status) VALUES
(1, 'Java开发工程师', '后端开发', '杭州', 15000, 25000, '本科', '1-3年', 1, '负责公司核心业务系统的开发和维护', '1. 扎实的Java基础\n2. 熟悉Spring Boot/Cloud\n3. 了解MySQL/Redis', '["五险一金","带薪年假","年终奖金","股票期权"]', 5, 1, 1),
(1, '前端开发工程师', '前端开发', '杭州', 12000, 20000, '本科', '1-3年', 1, '负责公司产品的前端开发工作', '1. 精通Vue/React\n2. 熟悉TypeScript\n3. 了解Node.js', '["五险一金","带薪年假","年终奖金"]', 3, 1, 1),
(2, '产品经理', '产品', '深圳', 18000, 30000, '本科', '3-5年', 1, '负责公司核心产品的规划和设计', '1. 3年以上产品经验\n2. 有社交产品经验优先\n3. 优秀的沟通能力', '["五险一金","带薪年假","年终奖金","租房补贴"]', 2, 1, 1),
(2, '数据分析师', '数据', '深圳', 15000, 25000, '本科', '1-3年', 1, '负责数据分析和挖掘工作', '1. 熟悉SQL/Python\n2. 了解数据仓库\n3. 良好的业务理解能力', '["五险一金","带薪年假","年终奖金"]', 3, 1, 1),
(3, '算法工程师', '人工智能', '北京', 20000, 40000, '硕士', '1-3年', 1, '负责推荐算法的研究和开发', '1. 扎实的机器学习基础\n2. 熟悉深度学习框架\n3. 有推荐系统经验优先', '["五险一金","带薪年假","年终奖金","期权"]', 2, 1, 1),
(3, '产品运营', '运营', '北京', 10000, 18000, '本科', '1-3年', 1, '负责产品的日常运营工作', '1. 1年以上运营经验\n2. 熟悉短视频平台\n3. 优秀的文案能力', '["五险一金","带薪年假","年终奖金"]', 4, 1, 1);

-- 简历数据
INSERT INTO t_resume (user_id, title, name, gender, birth_date, phone, email, education, school, major, graduation_year, work_status, expect_city, expect_industry, expect_job_type, expect_salary, self_introduction, skills, status, is_default) VALUES
(1, '张三的简历', '张三', 1, '2002-05-15', '13800000001', 'zhangsan@edu.cn', '本科', '浙江大学', '计算机科学与技术', 2025, 1, '杭州', '互联网', '后端开发', '15000-20000', '热爱编程，有扎实的Java基础', '["Java","Spring Boot","MySQL","Redis"]', 1, 1),
(2, '李四的简历', '李四', 1, '2002-08-20', '13800000002', 'lisi@edu.cn', '本科', '杭州电子科技大学', '软件工程', 2025, 1, '杭州,上海', '互联网', '前端开发', '12000-18000', '对前端技术有浓厚兴趣', '["Vue","React","TypeScript","JavaScript"]', 1, 1),
(3, '王五的简历', '王五', 1, '2001-12-10', '13800000003', 'wangwu@edu.cn', '硕士', '清华大学', '人工智能', 2025, 1, '北京', '人工智能', '算法工程师', '25000-35000', 'AI方向研究生，有丰富的项目经验', '["Python","TensorFlow","PyTorch","机器学习"]', 1, 1);

-- 投递记录
INSERT INTO t_application (user_id, job_id, company_id, resume_id, status) VALUES
(1, 1, 1, 1, 0),
(2, 2, 1, 2, 1),
(3, 5, 3, 3, 0);

-- 收藏记录
INSERT INTO t_favorite (user_id, job_id) VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 5);
