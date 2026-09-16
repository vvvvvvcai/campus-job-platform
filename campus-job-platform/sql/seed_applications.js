/**
 * 学生端「我的投递记录」演示数据入库脚本
 *
 * 用法：
 *   1. 安装依赖：npm i mysql2   （或复用已有 mysql2 目录）
 *   2. 运行：node seed_applications.js <userId> [条数]
 *      例：node seed_applications.js 4 10
 *
 * 说明：
 *   - 目标库：47.83.173.83:3306/campus_job（与 application-service 配置一致）
 *   - 优先投递「招聘中(status=1)」的职位，不足时补充其他未删除职位
 *   - t_application 有 uk_user_job 唯一键（同一学生对同一职位只能投一次），脚本自动跳过已投递职位
 *   - 该学生没有简历时会自动创建一份默认简历（t_resume.resume_id 非空约束）
 *   - 可重复执行，幂等
 */
const mysql = require('mysql2/promise')

const DB = { host: '47.83.173.83', port: 3306, user: 'root', password: '123456', database: 'campus_job' }

// 10 条记录的状态分布：3 待查看 / 2 已查看 / 2 面试邀请 / 1 不合适 / 2 已录用
const STATUS_PLAN = [0, 0, 0, 1, 1, 2, 2, 3, 4, 4]

const HR_REMARKS = {
  1: '已查阅简历，正在评估匹配度。',
  2: '简历匹配度较高，请尽快确认面试时间。',
  3: '感谢投递，目前暂无合适岗位，已为您保留人才库。',
  4: '恭喜通过全部面试环节，期待你的加入！'
}

function daysAgo(n, hour = 10) {
  const d = new Date(Date.now() - n * 86400_000)
  d.setHours(hour, 30, 0, 0)
  return d
}

function format(d) {
  const p = n => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${p(d.getMonth() + 1)}-${p(d.getDate())} ${p(d.getHours())}:${p(d.getMinutes())}:00`
}

async function main() {
  const userId = Number(process.argv[2] || 4)
  const target = Math.min(Number(process.argv[3] || 10), STATUS_PLAN.length)
  const conn = await mysql.createConnection(DB)

  try {
    // 1. 查该学生的简历，没有则创建
    let [resumes] = await conn.query(
      'SELECT id FROM t_resume WHERE user_id = ? AND deleted = 0 ORDER BY is_default DESC, id ASC', [userId])
    let resumeId
    if (resumes.length === 0) {
      const [users] = await conn.query(
        'SELECT username, real_name FROM t_user WHERE id = ? AND deleted = 0', [userId])
      if (users.length === 0) throw new Error(`用户 ${userId} 不存在`)
      const name = users[0].real_name || users[0].username || '未命名'
      const [r] = await conn.query(
        `INSERT INTO t_resume (user_id, title, name, status, is_default)
         VALUES (?, ?, ?, 1, 1)`, [userId, `${name}的简历`, name])
      resumeId = r.insertId
      console.log(`[resume] 已为学生 ${userId} 创建默认简历 id=${resumeId}`)
    } else {
      resumeId = resumes[0].id
      console.log(`[resume] 使用已有简历 id=${resumeId}`)
    }

    // 2. 已投递的职位（唯一键避重）
    const [applied] = await conn.query('SELECT job_id FROM t_application WHERE user_id = ?', [userId])
    const appliedSet = new Set(applied.map(a => a.job_id))

    // 3. 候选职位：招聘中优先，其次其他未删除职位
    const [jobs] = await conn.query(
      'SELECT id, company_id, title, status FROM t_job WHERE deleted = 0 ORDER BY (status = 1) DESC, id ASC')
    const candidates = jobs.filter(j => !appliedSet.has(j.id))
    console.log(`[job] 全部未删除职位 ${jobs.length} 个，可用（未投递）${candidates.length} 个`)
    if (candidates.length === 0) throw new Error('没有可投递的职位，无法插入数据')

    // 4. 插入投递记录
    const insertCount = Math.min(target, candidates.length)
    const inserted = []
    for (let i = 0; i < insertCount; i++) {
      const job = candidates[i]
      const status = STATUS_PLAN[i]
      const create = daysAgo((insertCount - i) * 3 + 1, 9 + (i % 8)) // 投递时间倒序错开
      const row = {
        user_id: userId, job_id: job.id, company_id: job.company_id,
        resume_id: resumeId, status,
        hr_remark: HR_REMARKS[status] || null,
        interview_time: null, interview_address: null,
        interview_contact: null, interview_contact_phone: null,
        interview_result: status === 4 ? 1 : (status === 2 ? 0 : null),
        create_time: format(create)
      }
      if (status === 2) {
        row.interview_time = format(daysAgo(-3, 14)) // 3 天后
        row.interview_address = '北京市海淀区中关村软件园 12 号楼 3 层（可远程）'
        row.interview_contact = 'HR 张女士'
        row.interview_contact_phone = '010-88886666'
      }
      const [r] = await conn.query('INSERT INTO t_application SET ?', row)
      inserted.push({ id: r.insertId, job: job.title, status })
    }

    console.log(`[done] 成功插入 ${inserted.length} 条投递记录（目标 ${target} 条）`)
    inserted.forEach(a => console.log(`  #${a.id} ${a.job}  status=${a.status}`))
    if (inserted.length < target) {
      console.log(`[warn] 可用职位不足：仅插入 ${inserted.length} 条。剩余 ${jobs.length} 个职位中 ${candidates.length} 个可投，如需更多请先在 t_job 增加职位。`)
    }
  } finally {
    await conn.end()
  }
}

main().catch(e => { console.error('[fail]', e.message); process.exit(1) })
