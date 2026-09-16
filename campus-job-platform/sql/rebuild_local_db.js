/**
 * 本地库重建脚本：在本地 MySQL(127.0.0.1:3306 root/123456) 重建 campus_job
 * 表结构按被删前的实际结构（含 industry/publish_time 等），数据回填我们已知的全部数据
 * 用法：node rebuild_local_db.js   （幂等：先 DROP 后重建）
 */
const mysql = require('mysql2/promise')
const bcrypt = require('bcryptjs')

const DB = { host: '127.0.0.1', port: 3306, user: 'root', password: '123456' }
const DBNAME = 'campus_job'

const now = () => new Date().toISOString().slice(0, 19).replace('T', ' ')
const d = (n, h = 10) => { const t = new Date(Date.now() - n * 86400e3); t.setHours(h, 30, 0, 0); const p = x => String(x).padStart(2, '0'); return `${t.getFullYear()}-${p(t.getMonth() + 1)}-${p(t.getDate())} ${p(t.getHours())}:${p(t.getMinutes())}:00` }

const SCHEMA = `
CREATE TABLE t_user (
  id BIGINT NOT NULL AUTO_INCREMENT, phone VARCHAR(20) NOT NULL, password VARCHAR(255) NOT NULL,
  username VARCHAR(50) NOT NULL, role TINYINT NOT NULL DEFAULT 0, avatar VARCHAR(255),
  email VARCHAR(100), real_name VARCHAR(50), gender TINYINT DEFAULT 0,
  status TINYINT NOT NULL DEFAULT 1, create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id), UNIQUE KEY uk_phone (phone), KEY idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_company (
  id BIGINT NOT NULL AUTO_INCREMENT, user_id BIGINT NOT NULL, company_name VARCHAR(100) NOT NULL,
  industry VARCHAR(50), scale VARCHAR(50), nature VARCHAR(50), address VARCHAR(255),
  website VARCHAR(255), logo VARCHAR(255), description TEXT, license_url VARCHAR(255),
  audit_status TINYINT NOT NULL DEFAULT 0, audit_time DATETIME, audit_remark VARCHAR(255),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id), UNIQUE KEY uk_user_id (user_id), KEY idx_audit_status (audit_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_job (
  id BIGINT NOT NULL AUTO_INCREMENT, company_id BIGINT NOT NULL, title VARCHAR(100) NOT NULL,
  category VARCHAR(50), city VARCHAR(50), industry VARCHAR(50),
  salary_min INT, salary_max INT, education VARCHAR(20), experience VARCHAR(50),
  job_type TINYINT DEFAULT 1, description TEXT, requirement TEXT, benefits VARCHAR(500),
  headcount INT DEFAULT 1, status TINYINT NOT NULL DEFAULT 0,
  audit_status TINYINT NOT NULL DEFAULT 0, audit_time DATETIME, audit_remark VARCHAR(255),
  publish_time DATETIME, view_count INT DEFAULT 0, apply_count INT DEFAULT 0,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id), KEY idx_company_id (company_id), KEY idx_category (category), KEY idx_city (city), KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_favorite (
  id BIGINT NOT NULL AUTO_INCREMENT, user_id BIGINT NOT NULL, job_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id), UNIQUE KEY uk_user_job (user_id, job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_resume (
  id BIGINT NOT NULL AUTO_INCREMENT, user_id BIGINT NOT NULL, title VARCHAR(100) NOT NULL,
  name VARCHAR(50) NOT NULL, gender TINYINT, birth_date DATE, phone VARCHAR(20), email VARCHAR(100),
  education VARCHAR(20), school VARCHAR(100), major VARCHAR(100), graduation_year INT,
  work_status TINYINT DEFAULT 0, expect_city VARCHAR(100), expect_industry VARCHAR(100),
  expect_job_type VARCHAR(100), expect_salary VARCHAR(50), self_introduction TEXT,
  skills VARCHAR(500), experiences TEXT, projects TEXT, awards TEXT, attachments VARCHAR(500),
  status TINYINT NOT NULL DEFAULT 1, is_default TINYINT DEFAULT 0,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id), KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_application (
  id BIGINT NOT NULL AUTO_INCREMENT, user_id BIGINT NOT NULL, job_id BIGINT NOT NULL,
  company_id BIGINT NOT NULL, resume_id BIGINT NOT NULL,
  status TINYINT NOT NULL DEFAULT 0, hr_remark VARCHAR(500),
  interview_time DATETIME, interview_address VARCHAR(255),
  interview_contact VARCHAR(50), interview_contact_phone VARCHAR(20), interview_result TINYINT,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id), UNIQUE KEY uk_user_job (user_id, job_id),
  KEY idx_job_id (job_id), KEY idx_company_id (company_id), KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_user_profile (
  id BIGINT NOT NULL AUTO_INCREMENT, user_id BIGINT NOT NULL,
  intent_city VARCHAR(500), intent_industry VARCHAR(500), intent_job_type VARCHAR(500),
  browse_count INT DEFAULT 0, apply_count INT DEFAULT 0, favorite_count INT DEFAULT 0,
  last_browse_time DATETIME, create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id), UNIQUE KEY uk_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_recommend_log (
  id BIGINT NOT NULL AUTO_INCREMENT, user_id BIGINT NOT NULL, job_id BIGINT NOT NULL,
  recommend_score INT DEFAULT 0, recommend_reason VARCHAR(255),
  is_clicked TINYINT DEFAULT 0, is_applied TINYINT DEFAULT 0,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id), KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE t_message (
  id BIGINT NOT NULL AUTO_INCREMENT, from_user_id BIGINT, to_user_id BIGINT,
  content TEXT, is_read TINYINT DEFAULT 0,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id), KEY idx_to_user (to_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
`

// 用户（密码统一 123456，BCrypt 加密）
const PWD = bcrypt.hashSync('123456', 10)
const USERS = [
  // [phone, username, role, status, email, realName]
  ['13900000001', 'admin', 2, 1, 'admin@campus.edu.cn', '平台管理员'],
  ['13800000001', 'zhangsan', 0, 0, 'zhangsan@edu.cn', '张三'],
  ['13800000002', 'lisi', 0, 1, 'lisi@edu.cn', '李四'],
  ['13800000003', 'wangwu', 0, 1, 'wangwu@edu.cn', '王五'],
  ['13800000004', 'zhaoliu', 0, 1, 'zhaoliu@edu.cn', '赵六'],
  ['13800000005', 'sunqi', 0, 0, 'sunqi@edu.cn', '孙七'],
  ['13800000006', 'zhouba', 0, 0, 'zhouba@edu.cn', '周八'],
  ['13800000007', 'wujiu', 0, 1, 'wujiu@edu.cn', '吴九'],
  ['13800000008', 'zhengshi', 0, 1, 'zhengshi@edu.cn', '郑十'],
  ['13900000011', 'company_ali', 1, 1, 'hr@alibaba-demo.com', '阿里HR'],
  ['13900000012', 'company_tencent', 1, 1, 'hr@tencent-demo.com', '腾讯HR'],
  ['13900000013', 'company_bytedance', 1, 1, 'hr@bytedance-demo.com', '字节HR'],
  ['13800000011', 'hr_zhang', 1, 1, 'hr_zhang@company.com', '张HR'],
  ['13800000012', 'hr_li', 1, 1, 'hr_li@company.com', '李HR'],
  ['13800000013', 'hr_wang', 1, 1, 'hr_wang@company.com', '王HR']
]

// 企业（company_id 与 user_id 对应）
const COMPANIES = [
  // [companyName, userIdPhone, industry, scale, address, auditStatus, remark]
  ['阿里巴巴集团', '13900000011', '互联网', '10000人以上', '杭州市余杭区文一西路969号', 0, null],
  ['腾讯科技', '13900000012', '互联网', '10000人以上', '深圳市南山区科技园', 2, '企业信息待补充'],
  ['字节跳动', '13900000013', '互联网', '10000人以上', '北京市海淀区知春路', 0, null],
  ['hr_zhang科技有限公司', '13800000011', '互联网', '100-499人', '杭州市西湖区文一西路', 1, null]
]

// 职位（companyId 对应上面顺序：1阿里 2腾讯 3字节 4hr_zhang）
const JOBS = [
  [1, 'Java开发工程师', '后端开发', '杭州', '互联网', 15000, 25000, '本科', '1-3年', 1, 3, 1, 1, 12, 46],
  [1, '前端开发工程师', '前端开发', '杭州', '互联网', 12000, 20000, '本科', '1-3年', 1, 3, 1, 1, 8, 35],
  [2, '产品经理', '产品', '深圳', '互联网', 18000, 30000, '本科', '3-5年', 1, 2, 1, 1, 15, 28],
  [2, '数据分析师', '数据', '深圳', '互联网', 15000, 25000, '本科', '1-3年', 1, 2, 1, 1, 9, 31],
  [3, '算法工程师', '人工智能', '北京', '互联网', 20000, 40000, '硕士', '1-3年', 1, 3, 1, 1, 20, 40],
  [3, '产品运营', '运营', '北京', '互联网', 10000, 18000, '本科', '1-3年', 1, 2, 1, 1, 7, 22],
  [1, '测试工程师', '测试', '杭州', '互联网', 12000, 18000, '本科', '1-3年', 1, 2, 1, 1, 6, 18],
  [2, 'UI设计师', '设计', '深圳', '互联网', 15000, 25000, '本科', '3-5年', 1, 2, 1, 1, 10, 19],
  [3, '运维工程师', '运维', '北京', '互联网', 18000, 28000, '本科', '3-5年', 1, 2, 1, 1, 11, 26],
  [1, '数据产品经理', '产品', '杭州', '互联网', 20000, 35000, '硕士', '3-5年', 1, 2, 1, 1, 13, 30],
  [4, 'Java后端开发工程师', '后端开发', '杭州', '互联网', 18000, 28000, '本科', '1-3年', 1, 5, 1, 1, 3, 8],
  [4, '前端开发工程师', '前端开发', '杭州', '互联网', 15000, 25000, '本科', '1-3年', 1, 3, 1, 1, 2, 5],
  [4, '测试开发工程师', '测试', '北京', '互联网', 15000, 22000, '本科', '应届/1年', 1, 2, 1, 1, 1, 3]
]

const JOB_DESC = {
  1: '1. 负责核心交易系统后端设计与开发；\n2. 参与高并发场景下的服务治理与性能优化；\n3. 与产品、前端团队协作，推进业务需求高质量落地。',
  2: '1. 负责前端核心页面开发与体验优化；\n2. 参与组件库建设与前端工程化体系搭建；\n3. 跟进浏览器性能优化与前端监控。',
  3: '1. 负责产品需求分析与规划；\n2. 协调设计、研发、运营推进产品落地；\n3. 跟踪产品数据并持续迭代。',
  4: '1. 负责业务数据分析与指标体系搭建；\n2. 输出数据洞察支持业务决策；\n3. 建设数据看板与报表。',
  5: '1. 参与大模型预训练、指令微调与对齐；\n2. 负责算法落地与推理优化；\n3. 协助前沿 LLM 算法研发与论文产出。',
  6: '1. 负责产品运营策略制定与执行；\n2. 分析用户数据，优化转化漏斗；\n3. 策划运营活动提升活跃度。',
  7: '1. 负责核心链路质量保障；\n2. 建设接口自动化与持续集成流水线；\n3. 参与全链路压测与稳定性建设。',
  8: '1. 负责产品界面视觉设计；\n2. 维护设计规范与组件库；\n3. 与研发协作保障设计还原度。',
  9: '1. 负责线上服务部署与运维保障；\n2. 建设监控告警与容量规划体系；\n3. 参与故障应急与性能调优。',
  10: '1. 负责数据产品规划与设计；\n2. 深入业务场景挖掘数据价值；\n3. 推动数据产品在业务侧落地。',
  11: '1. 负责校招业务核心系统的后端设计与开发；\n2. 参与高并发投递场景下的服务治理与性能优化；\n3. 与产品、前端团队协作，推进业务需求高质量落地。',
  12: '1. 负责校招平台学生端/企业端核心页面开发与体验优化；\n2. 参与组件库建设与前端工程化体系搭建；\n3. 跟进浏览器性能优化与前端监控。',
  13: '1. 负责校招业务核心链路的质量保障；\n2. 建设接口自动化与持续集成流水线；\n3. 参与全链路压测与稳定性建设。'
}
const JOB_REQ = '1. 本科及以上学历，计算机相关专业；\n2. 基础扎实，有相关实习或项目经验者优先；\n3. 沟通协作良好，热爱技术。'
const JOB_BENEFITS = '五险一金,弹性工时,租房补贴,导师带教,免费三餐'

async function main() {
  const conn = await mysql.createConnection(DB)
  try {
    console.log('[1/5] 重建数据库 campus_job ...')
    await conn.query(`DROP DATABASE IF EXISTS ${DBNAME}`)
    await conn.query(`CREATE DATABASE ${DBNAME} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci`)
    await conn.changeUser({ database: DBNAME })

    console.log('[2/5] 建表 ...')
    for (const stmt of SCHEMA.split(';').map(s => s.trim()).filter(Boolean)) {
      await conn.query(stmt)
    }

    console.log('[3/5] 插入用户 ...')
    for (const [phone, username, role, status, email, realName] of USERS) {
      await conn.query(
        `INSERT INTO t_user (phone, password, username, role, status, email, real_name, gender, create_time, update_time, deleted)
         VALUES (?, ?, ?, ?, ?, ?, ?, 1, ?, ?, 0)`,
        [phone, PWD, username, role, status, email, realName, now(), now()])
    }
    const [userMap] = await conn.query('SELECT id, phone FROM t_user')

    console.log('[4/5] 插入企业与职位 ...')
    const companyIds = {}
    for (const [name, phone, industry, scale, address, auditStatus, remark] of COMPANIES) {
      const u = userMap.find(x => x.phone === phone)
      const [r] = await conn.query(
        `INSERT INTO t_company (user_id, company_name, industry, scale, nature, address, audit_status, audit_remark, create_time, update_time, deleted)
         VALUES (?, ?, ?, ?, '民营企业', ?, ?, ?, ?, ?, 0)`,
        [u.id, name, industry, scale, address, auditStatus, remark, now(), now()])
      companyIds[name] = r.insertId
      console.log(`  企业 ${name} -> id=${r.insertId} (user_id=${u.id})`)
    }
    const cidByIndex = [null, companyIds['阿里巴巴集团'], companyIds['腾讯科技'], companyIds['字节跳动'], companyIds['hr_zhang科技有限公司']]
    const jobIds = []
    for (let i = 0; i < JOBS.length; i++) {
      const [ci, title, category, city, industry, smin, smax, edu, exp, jt, hc, status, audit, views, applies] = JOBS[i]
      const [r] = await conn.query(
        `INSERT INTO t_job (company_id, title, category, city, industry, salary_min, salary_max, education, experience,
           job_type, description, requirement, benefits, headcount, status, audit_status, audit_time, publish_time,
           view_count, apply_count, create_time, update_time, deleted)
         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)`,
        [cidByIndex[ci], title, category, city, industry, smin, smax, edu, exp, jt,
         JOB_DESC[i + 1] || '联调测试职位描述',
         JOB_REQ, JOB_BENEFITS, hc, status, audit, now(), now(), views, applies, now(), now()])
      jobIds.push(r.insertId)
      console.log(`  职位 #${r.insertId} ${title}（company_id=${cidByIndex[ci]}）`)
    }
    const jobIdAt = i => jobIds[i] // 表自增顺序 == 插入顺序，id 从 1 开始
    const JID = {}; jobIds.forEach((id, i) => JID[i + 1] = id)

    console.log('[5/5] 插入简历/收藏/投递 ...')
    const lisi = userMap.find(x => x.phone === '13800000002')
    const zhangsan = userMap.find(x => x.phone === '13800000001')
    const wangwu = userMap.find(x => x.phone === '13800000003')
    // 简历
    const [r1] = await conn.query(
      `INSERT INTO t_resume (user_id, title, name, gender, birth_date, phone, email, education, school, major,
         graduation_year, work_status, expect_city, expect_industry, expect_job_type, expect_salary,
         self_introduction, skills, experiences, projects, awards, attachments, status, is_default, create_time, update_time, deleted)
       VALUES (?, '张三的简历', '张三', 1, '2001-05-12', '13800000001', 'zhangsan@edu.cn', '本科', '浙江大学', '计算机科学与技术',
         2025, 1, '杭州', '互联网', '后端开发', '15000-22000', '对后端技术有浓厚兴趣', '["Java","Spring Boot","MySQL","Redis"]',
         '[]', '[]', '[]', '[]', 1, 1, ?, ?, 0)`,
      [zhangsan.id, d(60), d(60)])
    const [r2] = await conn.query(
      `INSERT INTO t_resume (user_id, title, name, gender, birth_date, phone, email, education, school, major,
         graduation_year, work_status, expect_city, expect_industry, expect_job_type, expect_salary,
         self_introduction, skills, experiences, projects, awards, attachments, status, is_default, create_time, update_time, deleted)
       VALUES (?, '李四的简历', '李四', 1, '2002-08-20', '13800000002', 'lisi@edu.cn', '本科', '杭州电子科技大学', '软件工程',
         2025, 1, '杭州,上海', '互联网', '前端开发', '12000-18000', '对前端技术有浓厚兴趣', '["Vue","React","TypeScript","JavaScript"]',
         '[]', '[]', '[]', '[]', 1, 1, ?, ?, 0)`,
      [lisi.id, d(60), d(60)])
    const [r3] = await conn.query(
      `INSERT INTO t_resume (user_id, title, name, gender, birth_date, phone, email, education, school, major,
         graduation_year, work_status, expect_city, expect_industry, expect_job_type, expect_salary,
         self_introduction, skills, experiences, projects, awards, attachments, status, is_default, create_time, update_time, deleted)
       VALUES (?, '王五的简历', '王五', 1, '2002-11-03', '13800000003', 'wangwu@edu.cn', '硕士', '南京大学', '人工智能',
         2026, 1, '北京', '人工智能', '算法工程师', '20000-30000', '深耕 NLP 方向，参与过多模态项目', '["Python","PyTorch","Transformer","NLP"]',
         '[]', '[]', '[]', '[]', 1, 1, ?, ?, 0)`,
      [wangwu.id, d(60), d(60)])
    console.log(`  简历 id=${r1.insertId}(张三) ${r2.insertId}(李四) ${r3.insertId}(王五)`)

    // 收藏：lisi 收藏 job2、job5
    await conn.query(`INSERT INTO t_favorite (user_id, job_id, create_time) VALUES (?, ?, ?), (?, ?, ?)`,
      [lisi.id, JID[2], d(20), lisi.id, JID[5], d(10)])
    console.log(`  收藏：lisi -> 职位#${JID[2]}、#${JID[5]}`)

    // 投递：lisi 10 条（原库状态分布）+ 1 条 hr_zhang 新职位测试投递
    const APPS = [
      // [jobIndex, status, hrRemark, interviewTime, interviewAddress, interviewContact, interviewPhone, interviewResult, daysAgo]
      [2, 1, null, null, null, null, null, null, 0],
      [5, 0, null, null, null, null, null, null, 27],
      [6, 0, null, null, null, null, null, null, 24],
      [8, 0, null, null, null, null, null, null, 21],
      [9, 1, null, null, null, null, null, null, 18],
      [10, 1, null, null, null, null, null, null, 15],
      [1, 2, '简历匹配度较高，请尽快确认面试时间。', d(-3, 14), '线上视频面试', 'HR 张女士', '0571-88886666', 0, 12],
      [3, 2, '简历匹配度较高，请尽快确认面试时间。', d(-4, 15), '深圳市南山区科技园（可远程）', 'HR 李女士', '0755-66668888', 0, 9],
      [4, 3, '感谢投递，目前暂无合适岗位，已为您保留人才库。', null, null, null, null, null, 6],
      [7, 4, '恭喜通过全部面试环节，期待你的加入！', null, null, null, null, 1, 3],
      [11, 1, null, null, null, null, null, null, 1]
    ]
    for (const [ji, status, remark, it, ia, ic, ip, ir, daysAgo] of APPS) {
      await conn.query(
        `INSERT INTO t_application (user_id, job_id, company_id, resume_id, status, hr_remark,
           interview_time, interview_address, interview_contact, interview_contact_phone, interview_result,
           create_time, update_time)
         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
        [lisi.id, JID[ji], (await conn.query('SELECT company_id FROM t_job WHERE id=?', [JID[ji]]))[0][0].company_id,
         r2.insertId, status, remark, it, ia, ic, ip, ir, d(daysAgo, 9 + (daysAgo % 8)), now()])
    }
    console.log(`  投递：lisi -> ${APPS.length} 条`)

    console.log('\n=== 本地库重建完成 ===')
    console.log('登录账号（密码统一 123456）：')
    console.log('  管理员: 13900000001 / admin')
    console.log('  学生:   13800000002 / lisi（李四，10+1 条投递、2 条收藏）')
    console.log('  企业HR: 13800000011 / hr_zhang（企业 id=4，3 条职位）')
    console.log('  其余学生: 13800000001/3/4/5/6/7/8（zhangsan..zhengshi）')
  } finally {
    await conn.end()
  }
}

main().catch(e => { console.error('[fail]', e); process.exit(1) })
