/**
 * 为「至昂科技有限公司」添加多行业职位 + 其他企业补充职位
 * 用法：node seed_more_jobs.js
 * 目标库：本地 127.0.0.1:3306/campus_job
 */
const mysql = require('mysql2/promise')

const DB = { host: '127.0.0.1', port: 3306, user: 'root', password: '123456', database: 'campus_job' }

function fmt(d) {
  const p = n => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${p(d.getMonth() + 1)}-${p(d.getDate())} ${p(d.getHours())}:${p(d.getMinutes())}:00`
}
const now = () => fmt(new Date())

// ========== 至昂科技有限公司的职位 ==========
const ZA_JOBS = [
  // --- 互联网/IT ---
  { title: 'Java后端开发工程师', category: '后端开发', city: '北京', industry: '互联网', salaryMin: 18000, salaryMax: 30000, education: '本科', experience: '1-3年', jobType: 1, headcount: 5,
    description: '1. 负责公司核心业务系统后端架构设计与开发；\n2. 参与微服务拆分与中间件选型；\n3. 优化系统性能，保障高并发场景下的稳定性。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 扎实的 Java 基础，熟悉 Spring Boot/Cloud、MySQL、Redis；\n3. 有微服务或分布式系统经验者优先。' },
  { title: '前端开发工程师', category: '前端开发', city: '北京', industry: '互联网', salaryMin: 15000, salaryMax: 25000, education: '本科', experience: '1-3年', jobType: 1, headcount: 3,
    description: '1. 负责公司 B 端/C 端产品前端开发；\n2. 参与前端工程化与组件库建设；\n3. 优化页面性能与用户体验。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 熟练掌握 Vue3/React，了解 TypeScript；\n3. 有实际项目经验者优先。' },
  { title: 'Python数据分析师', category: '数据分析', city: '北京', industry: '互联网', salaryMin: 15000, salaryMax: 25000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 2,
    description: '1. 负责业务数据采集、清洗与分析；\n2. 搭建数据看板，输出分析报告；\n3. 协助业务团队做数据驱动决策。',
    requirement: '1. 本科及以上学历，统计学/计算机/数学相关专业；\n2. 熟练使用 Python/SQL，了解 Pandas、Matplotlib；\n3. 逻辑清晰，善于沟通。' },
  { title: 'UI/UX设计师', category: '设计', city: '北京', industry: '互联网', salaryMin: 12000, salaryMax: 20000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 2,
    description: '1. 负责产品界面视觉设计与交互优化；\n2. 维护设计规范与组件库；\n3. 与研发协作保障设计还原度。',
    requirement: '1. 本科及以上学历，设计相关专业；\n2. 熟练使用 Figma/Sketch，了解基本交互设计原则；\n3. 有作品集者优先。' },

  // --- 金融 ---
  { title: '量化分析实习生', category: '金融分析', city: '上海', industry: '金融', salaryMin: 10000, salaryMax: 18000, education: '硕士', experience: '应届', jobType: 2, headcount: 3,
    description: '1. 协助构建量化交易策略模型；\n2. 进行金融市场数据清洗与因子分析；\n3. 参与回测框架开发与策略评估。',
    requirement: '1. 硕士及以上学历，金融工程/数学/计算机相关专业；\n2. 熟悉 Python，了解金融衍生品或统计模型；\n3. 有量化实习经验者优先。' },
  { title: '风控工程师', category: '风险管理', city: '上海', industry: '金融', salaryMin: 18000, salaryMax: 30000, education: '本科', experience: '1-3年', jobType: 1, headcount: 2,
    description: '1. 负责信贷风控模型开发与迭代；\n2. 搭建风险监控指标体系；\n3. 参与反欺诈策略制定。',
    requirement: '1. 本科及以上学历，数学/统计/计算机相关专业；\n2. 熟悉逻辑回归、决策树等常用模型；\n3. 有金融风控经验者优先。' },
  { title: '产品经理（金融科技）', category: '产品', city: '上海', industry: '金融', salaryMin: 20000, salaryMax: 35000, education: '本科', experience: '3-5年', jobType: 1, headcount: 1,
    description: '1. 负责金融科技产品规划与设计；\n2. 深入业务场景挖掘用户需求；\n3. 协调研发团队推进产品落地。',
    requirement: '1. 本科及以上学历；\n2. 3年以上金融或支付类产品经验；\n3. 优秀的沟通协调与项目推动能力。' },

  // --- 教育 ---
  { title: '课程研发工程师', category: '教育研发', city: '杭州', industry: '教育', salaryMin: 15000, salaryMax: 25000, education: '本科', experience: '1-3年', jobType: 1, headcount: 3,
    description: '1. 负责在线教育课程内容研发与设计；\n2. 结合技术趋势更新课程体系；\n3. 与讲师团队协作打磨课程质量。',
    requirement: '1. 本科及以上学历，教育技术/计算机相关专业；\n2. 对教学设计有热情，表达能力强；\n3. 有教育行业经验者优先。' },
  { title: 'AI教学产品运营', category: '运营', city: '杭州', industry: '教育', salaryMin: 12000, salaryMax: 20000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 2,
    description: '1. 负责 AI 教学产品的用户运营与增长；\n2. 策划线上活动提升用户活跃度；\n3. 分析用户数据优化产品体验。',
    requirement: '1. 本科及以上学历；\n2. 对 AI/教育行业有热情；\n3. 有运营或新媒体经验者优先。' },

  // --- 制造/新能源 ---
  { title: '嵌入式软件工程师', category: '嵌入式开发', city: '深圳', industry: '新能源', salaryMin: 16000, salaryMax: 28000, education: '本科', experience: '1-3年', jobType: 1, headcount: 4,
    description: '1. 负责新能源设备嵌入式软件开发；\n2. 进行底层驱动编写与调试；\n3. 参与产品联调与性能优化。',
    requirement: '1. 本科及以上学历，电子/通信/计算机相关专业；\n2. 熟悉 C/C++，了解 RTOS 或 Linux 嵌入式开发；\n3. 有嵌入式项目经验者优先。' },
  { title: '电池系统测试工程师', category: '测试', city: '深圳', industry: '新能源', salaryMin: 14000, salaryMax: 22000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 3,
    description: '1. 负责动力电池/储能系统的测试方案设计；\n2. 执行性能测试并输出报告；\n3. 参与测试自动化建设。',
    requirement: '1. 本科及以上学历，电化学/材料/机械相关专业；\n2. 了解电池基本原理，动手能力强；\n3. 有实验室或测试实习经验者优先。' },
  { title: '光伏系统设计工程师', category: '技术', city: '西安', industry: '新能源', salaryMin: 15000, salaryMax: 25000, education: '本科', experience: '1-3年', jobType: 1, headcount: 2,
    description: '1. 负责分布式光伏项目方案设计；\n2. 进行发电量模拟与经济性分析；\n3. 协调项目施工与验收。',
    requirement: '1. 本科及以上学历，电气/能源相关专业；\n2. 熟悉 PVsyst 等光伏设计软件；\n3. 有光伏行业经验者优先。' },

  // --- 医疗/生物 ---
  { title: '生物信息分析工程师', category: '生物信息', city: '北京', industry: '医疗', salaryMin: 18000, salaryMax: 30000, education: '硕士', experience: '应届/1年', jobType: 1, headcount: 2,
    description: '1. 负责高通量测序数据处理与分析；\n2. 开发生信分析流程与工具；\n3. 参与科研项目数据分析支持。',
    requirement: '1. 硕士及以上学历，生物信息/计算生物学相关专业；\n2. 熟练使用 Python/R，了解 Linux 操作；\n3. 有生信分析项目经验者优先。' },
  { title: '医疗器械注册专员', category: '法规事务', city: '苏州', industry: '医疗', salaryMin: 12000, salaryMax: 20000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 1,
    description: '1. 负责医疗器械产品注册申报材料准备；\n2. 跟进注册审批流程；\n3. 维护注册相关文档与数据库。',
    requirement: '1. 本科及以上学历，生物医学/药学相关专业；\n2. 熟悉 NMPA/FDA 注册流程者优先；\n3. 细致耐心，文字功底好。' },

  // --- 媒体/文化 ---
  { title: '短视频编导', category: '内容创作', city: '成都', industry: '传媒', salaryMin: 10000, salaryMax: 18000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 2,
    description: '1. 负责短视频选题策划与脚本撰写；\n2. 协调拍摄团队完成视频制作；\n3. 跟进视频数据表现并优化内容方向。',
    requirement: '1. 本科及以上学历，新闻/编导/传媒相关专业；\n2. 网感好，熟悉抖音/B站等平台内容风格；\n3. 有作品者优先。' },
  { title: '新媒体运营专员', category: '运营', city: '成都', industry: '传媒', salaryMin: 8000, salaryMax: 15000, education: '本科', experience: '应届', jobType: 1, headcount: 2,
    description: '1. 负责公司社交媒体账号日常运营；\n2. 策划线上活动提升粉丝互动；\n3. 分析运营数据并输出周报。',
    requirement: '1. 本科及以上学历；\n2. 文笔好，对热点敏感；\n3. 有自媒体运营经验者优先。' },

  // --- 汽车/自动驾驶 ---
  { title: '自动驾驶感知算法工程师', category: '算法', city: '北京', industry: '汽车', salaryMin: 25000, salaryMax: 45000, education: '硕士', experience: '1-3年', jobType: 1, headcount: 3,
    description: '1. 负责自动驾驶感知模块算法开发（目标检测/跟踪）；\n2. 优化模型在车载平台的推理性能；\n3. 参与数据标注规范与闭环迭代。',
    requirement: '1. 硕士及以上学历，计算机/电子/自动化相关专业；\n2. 熟悉 C++/Python，了解深度学习框架（PyTorch/TensorRT）；\n3. 有自动驾驶或计算机视觉项目经验者优先。' },
  { title: '车联网后端开发工程师', category: '后端开发', city: '上海', industry: '汽车', salaryMin: 18000, salaryMax: 30000, education: '本科', experience: '1-3年', jobType: 1, headcount: 2,
    description: '1. 负责车联网平台后端服务开发；\n2. 设计高并发实时数据处理架构；\n3. 保障车端与云端数据通信安全稳定。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 熟悉 Java/Go，了解 Kafka/MQ 等消息中间件；\n3. 有物联网或车联网经验者优先。' },

  // --- 电商/零售 ---
  { title: '电商运营管培生', category: '运营', city: '杭州', industry: '电商', salaryMin: 10000, salaryMax: 16000, education: '本科', experience: '应届', jobType: 1, headcount: 5,
    description: '1. 参与电商平台店铺运营全流程；\n2. 协助策划促销活动与内容营销；\n3. 分析销售数据并提出优化建议。',
    requirement: '1. 本科及以上学历，市场营销/电子商务相关专业；\n2. 对电商行业有热情，学习能力强；\n3. 有电商实习经验者优先。' },
  { title: '供应链管理专员', category: '供应链', city: '杭州', industry: '电商', salaryMin: 12000, salaryMax: 20000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 2,
    description: '1. 负责供应商管理与采购协调；\n2. 跟进库存周转与物流效率；\n3. 参与供应链数字化优化项目。',
    requirement: '1. 本科及以上学历，物流/供应链管理相关专业；\n2. 数据分析能力强，熟练使用 Excel/SQL；\n3. 有供应链实习经验者优先。' },
]

// ========== 其他企业补充职位 ==========
const EXTRA_JOBS = [
  // 阿里巴巴
  { companyPhone: '13900000011', title: '大数据开发工程师', category: '大数据', city: '杭州', industry: '互联网', salaryMin: 22000, salaryMax: 38000, education: '本科', experience: '1-3年', jobType: 1, headcount: 4,
    description: '1. 负责大数据平台开发与维护；\n2. 优化数据 pipeline 性能；\n3. 参与数据治理与质量保障。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 熟悉 Hadoop/Spark/Flink，了解 SQL；\n3. 有大数据项目经验者优先。' },
  { companyPhone: '13900000011', title: '云原生架构师', category: '架构', city: '杭州', industry: '互联网', salaryMin: 30000, salaryMax: 50000, education: '本科', experience: '3-5年', jobType: 1, headcount: 2,
    description: '1. 负责云原生技术架构设计与落地；\n2. 推进容器化与微服务治理；\n3. 主导技术选型与性能优化。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 精通 Kubernetes/Docker，熟悉 Service Mesh；\n3. 有大规模分布式系统架构经验。' },

  // 腾讯
  { companyPhone: '13900000012', title: '游戏客户端开发工程师', category: '游戏开发', city: '深圳', industry: '游戏', salaryMin: 20000, salaryMax: 35000, education: '本科', experience: '1-3年', jobType: 1, headcount: 3,
    description: '1. 负责游戏客户端核心模块开发；\n2. 优化游戏性能与用户体验；\n3. 与策划/美术团队紧密协作。',
    requirement: '1. 本科及以上学历，计算机相关专业；\n2. 熟悉 C++/Unity/Unreal Engine；\n3. 热爱游戏，有游戏开发经验者优先。' },
  { companyPhone: '13900000012', title: '安全研究员', category: '信息安全', city: '深圳', industry: '互联网', salaryMin: 25000, salaryMax: 40000, education: '硕士', experience: '1-3年', jobType: 1, headcount: 2,
    description: '1. 负责安全漏洞挖掘与分析；\n2. 开发安全检测工具与平台；\n3. 参与安全应急响应。',
    requirement: '1. 硕士及以上学历，信息安全/计算机相关专业；\n2. 熟悉 Web 安全/二进制安全，有 CTF 经验者优先；\n3. 责任心强，善于学习。' },

  // 字节跳动
  { companyPhone: '13900000013', title: '推荐系统工程师', category: '推荐系统', city: '北京', industry: '互联网', salaryMin: 25000, salaryMax: 45000, education: '本科', experience: '1-3年', jobType: 1, headcount: 3,
    description: '1. 负责推荐系统核心算法研发；\n2. 优化推荐效果与用户留存；\n3. 参与大规模特征工程与模型训练。',
    requirement: '1. 本科及以上学历，计算机/数学相关专业；\n2. 熟悉推荐系统常用算法，了解 TensorFlow/PyTorch；\n3. 有推荐系统项目经验者优先。' },
  { companyPhone: '13900000013', title: '内容审核策略运营', category: '运营', city: '北京', industry: '互联网', salaryMin: 12000, salaryMax: 20000, education: '本科', experience: '应届/1年', jobType: 1, headcount: 4,
    description: '1. 负责内容安全审核策略制定与优化；\n2. 分析违规内容特征，完善审核规则；\n3. 与技术团队协作提升审核效率。',
    requirement: '1. 本科及以上学历；\n2. 对内容安全有认知，原则性强；\n3. 有审核或运营经验者优先。' },
]

async function main() {
  const conn = await mysql.createConnection(DB)
  try {
    const ts = now()

    // ===== 1. 确保「至昂科技有限公司」存在 =====
    let [zaCompany] = await conn.query(
      "SELECT id FROM t_company WHERE company_name = '至昂科技有限公司' AND deleted = 0")
    let zaCompanyId
    if (zaCompany.length > 0) {
      zaCompanyId = zaCompany[0].id
      console.log(`[至昂科技] 已存在 id=${zaCompanyId}`)
    } else {
      // 找一个可用的 HR 用户（hr_li 或 hr_wang）
      let [hrUser] = await conn.query(
        "SELECT id FROM t_user WHERE phone = '13800000012' AND deleted = 0")
      if (hrUser.length === 0) {
        hrUser = await conn.query(
          "SELECT id FROM t_user WHERE phone = '13800000013' AND deleted = 0")
      }
      const userId = hrUser[0][0].id
      const [r] = await conn.query(
        `INSERT INTO t_company (user_id, company_name, industry, scale, nature, address, audit_status, audit_time, create_time, update_time, deleted)
         VALUES (?, '至昂科技有限公司', '互联网', '500-999人', '民营企业', '北京市海淀区中关村科技园', 1, NOW(), ?, ?, 0)`,
        [userId, ts, ts])
      zaCompanyId = r.insertId
      console.log(`[至昂科技] 已创建 id=${zaCompanyId} (user_id=${userId})`)
    }

    // ===== 2. 插入至昂科技的职位 =====
    const [existZa] = await conn.query(
      'SELECT COUNT(*) cnt FROM t_job WHERE company_id = ? AND deleted = 0', [zaCompanyId])
    if (existZa[0].cnt >= ZA_JOBS.length) {
      console.log(`[至昂科技] 已有 ${existZa[0].cnt} 条职位，跳过`)
    } else {
      for (const j of ZA_JOBS) {
        const [r] = await conn.query(
          `INSERT INTO t_job (company_id, title, category, city, industry, salary_min, salary_max,
             education, experience, job_type, description, requirement, benefits, headcount,
             status, audit_status, audit_time, publish_time, view_count, apply_count, create_time, update_time, deleted)
           VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '五险一金,带薪年假,弹性工时,节日福利,团建活动', ?, 1, 1, NOW(), NOW(), ?, ?, ?, ?, 0)`,
          [zaCompanyId, j.title, j.category, j.city, j.industry, j.salaryMin, j.salaryMax,
           j.education, j.experience, j.jobType, j.description, j.requirement, j.headcount,
           Math.floor(Math.random() * 200) + 50, Math.floor(Math.random() * 30), ts, ts])
        console.log(`  [至昂] #${r.insertId} ${j.title}（${j.city} ${j.industry}）`)
      }
      console.log(`[至昂科技] 插入 ${ZA_JOBS.length} 条职位`)
    }

    // ===== 3. 其他企业补充职位 =====
    for (const j of EXTRA_JOBS) {
      const [comp] = await conn.query(
        'SELECT id FROM t_company WHERE user_id = (SELECT id FROM t_user WHERE phone = ? AND deleted = 0) AND deleted = 0',
        [j.companyPhone])
      if (comp.length === 0) { console.log(`[skip] 找不到 phone=${j.companyPhone} 的企业`); continue }
      const companyId = comp[0].id
      // 避免重复插入同名职位
      const [dup] = await conn.query(
        'SELECT id FROM t_job WHERE company_id = ? AND title = ? AND deleted = 0', [companyId, j.title])
      if (dup.length > 0) { console.log(`[skip] ${j.title} 已存在`) ; continue }
      const [r] = await conn.query(
        `INSERT INTO t_job (company_id, title, category, city, industry, salary_min, salary_max,
           education, experience, job_type, description, requirement, benefits, headcount,
           status, audit_status, audit_time, publish_time, view_count, apply_count, create_time, update_time, deleted)
         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '五险一金,带薪年假,年终奖金,员工旅游,培训机会', ?, 1, 1, NOW(), NOW(), ?, ?, ?, ?, 0)`,
         [companyId, j.title, j.category, j.city, j.industry, j.salaryMin, j.salaryMax,
          j.education, j.experience, j.jobType, j.description, j.requirement, j.headcount,
          Math.floor(Math.random() * 300) + 100, Math.floor(Math.random() * 50), ts, ts])
      console.log(`  [extra] #${r.insertId} ${j.title}（${j.city}）`)
    }

    console.log(`\n[done] 共插入至昂科技 ${ZA_JOBS.length} 条 + 其他企业 ${EXTRA_JOBS.length} 条职位`)
  } finally {
    await conn.end()
  }
}

main().catch(e => { console.error('[fail]', e.message); process.exit(1) })
