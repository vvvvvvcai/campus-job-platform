/**
 * 给指定 HR 账号（默认 user_id=27，hr_zhang）增加 3 条职位数据
 * 说明：
 *   - 若该 HR 名下没有企业记录，自动创建一家（职位必须挂在企业下）
 *   - 仅在该企业还没有职位时插入，可重复执行（幂等）
 * 用法：node seed_hr_jobs.js [userId]
 */
const mysql = require('mysql2/promise')

const DB = { host: '47.83.173.83', port: 3306, user: 'root', password: '123456', database: 'campus_job' }

const JOBS = [
  {
    title: 'Java后端开发工程师', category: '后端开发', city: '杭州', industry: '互联网',
    salaryMin: 18000, salaryMax: 28000, education: '本科', experience: '1-3年', jobType: 1,
    description: '1. 负责校招业务核心系统（职位中心/投递链路）的后端设计与开发；\n2. 参与高并发投递场景下的服务治理与性能优化；\n3. 与产品、前端团队协作，推进业务需求高质量落地。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 扎实的 Java 基础，熟悉 Spring Boot / MySQL / Redis；\n3. 有分布式系统或高并发场景实践经验者优先。',
    benefits: '五险一金,弹性工时,租房补贴,导师带教,免费三餐',
    headcount: 5
  },
  {
    title: '前端开发工程师', category: '前端开发', city: '杭州', industry: '互联网',
    salaryMin: 15000, salaryMax: 25000, education: '本科', experience: '1-3年', jobType: 1,
    description: '1. 负责校招平台学生端/企业端核心页面开发与体验优化；\n2. 参与组件库建设与前端工程化体系搭建；\n3. 跟进浏览器性能优化与前端监控。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 熟练掌握 Vue3 / TypeScript，了解工程化构建工具；\n3. 对用户体验与代码质量有追求，有实习或开源项目经历者优先。',
    benefits: '五险一金,弹性工时,租房补贴,导师带教',
    headcount: 3
  },
  {
    title: '测试开发工程师', category: '测试', city: '北京', industry: '互联网',
    salaryMin: 15000, salaryMax: 22000, education: '本科', experience: '应届/1年', jobType: 1,
    description: '1. 负责校招业务核心链路的质量保障，设计并落地自动化测试方案；\n2. 建设接口自动化与持续集成流水线；\n3. 参与全链路压测与稳定性建设。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 熟悉至少一种语言（Java/Python），了解常用测试框架；\n3. 逻辑清晰、细心负责，热爱质量保障工作。',
    benefits: '五险一金,弹性工时,租房补贴,定期体检',
    headcount: 2
  }
]

function format(d) {
  const p = n => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${p(d.getMonth() + 1)}-${p(d.getDate())} ${p(d.getHours())}:${p(d.getMinutes())}:00`
}

async function main() {
  const userId = Number(process.argv[2] || 27)
  const conn = await mysql.createConnection(DB)
  try {
    // 1. 确认 HR 存在
    const [users] = await conn.query(
      'SELECT id, username, role FROM t_user WHERE id = ? AND deleted = 0', [userId])
    if (users.length === 0) throw new Error(`用户 ${userId} 不存在`)
    const hr = users[0]
    console.log(`[user] ${hr.username} (id=${hr.id}, role=${hr.role})`)

    // 2. 企业记录：没有则创建
    let [comps] = await conn.query('SELECT id FROM t_company WHERE user_id = ? AND deleted = 0', [userId])
    let companyId
    if (comps.length === 0) {
      const [r] = await conn.query(
        `INSERT INTO t_company (user_id, company_name, industry, scale, nature, address, audit_status, audit_time)
         VALUES (?, ?, '互联网', '100-499人', '民营企业', '杭州市西湖区文一西路', 1, NOW())`,
        [userId, `${hr.username}科技有限公司`])
      companyId = r.insertId
      console.log(`[company] 已创建企业 id=${companyId}（audit_status=1 已通过）`)
    } else {
      companyId = comps[0].id
      console.log(`[company] 使用已有企业 id=${companyId}`)
    }

    // 3. 职位：该企业没有职位时才插入（幂等）
    const [exist] = await conn.query(
      'SELECT COUNT(*) cnt FROM t_job WHERE company_id = ? AND deleted = 0', [companyId])
    if (exist[0].cnt > 0) {
      console.log(`[skip] 该企业已有 ${exist[0].cnt} 条职位，跳过插入`)
      return
    }

    const now = new Date()
    for (const j of JOBS) {
      const [r] = await conn.query(
        `INSERT INTO t_job (company_id, title, category, city, industry, salary_min, salary_max,
           education, experience, job_type, description, requirement, benefits, headcount,
           status, audit_status, audit_time, publish_time, view_count, apply_count, create_time, update_time, deleted)
         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, 1, NOW(), NOW(), 0, 0, ?, ?, 0)`,
        [companyId, j.title, j.category, j.city, j.industry, j.salaryMin, j.salaryMax,
         j.education, j.experience, j.jobType, j.description, j.requirement, j.benefits, j.headcount,
         format(now), format(now)])
      console.log(`[job] #${r.insertId} ${j.title}（${j.city} ${j.salaryMin}-${j.salaryMax}元/月，招聘中）`)
    }
    console.log(`[done] 完成：企业 company_id=${companyId}，职位 ${JOBS.length} 条（status=1 招聘中，audit_status=1 已通过）`)
  } finally {
    await conn.end()
  }
}

main().catch(e => { console.error('[fail]', e.message); process.exit(1) })
