<template>
  <div class="min-h-screen bg-background">
    <!-- Search Bar -->
    <div class="bg-surface-container-lowest border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6 py-5">
        <div class="flex gap-3 items-center">
          <div class="relative flex-1">
            <span class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-on-surface-variant">search</span>
            <input v-model="searchQuery" type="text" placeholder="大模型算法工程师"
              class="w-full h-12 pl-12 pr-4 bg-surface-container-low text-on-surface placeholder:text-on-surface-variant text-sm rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 border border-surface-container-high" />
          </div>
          <button class="h-12 px-5 bg-primary text-on-primary font-semibold text-sm rounded-xl hover:bg-primary/90 transition-colors flex items-center gap-2 shrink-0">
            <span class="material-symbols-outlined text-[18px]">search</span>
            立即搜索
          </button>
          <button class="h-12 px-4 text-on-surface-variant hover:text-on-surface text-sm font-medium rounded-xl hover:bg-surface-container-low transition-colors flex items-center gap-1 shrink-0">
            <span class="material-symbols-outlined text-[18px]">refresh</span>
            重置
          </button>
        </div>

        <!-- City Row -->
        <div class="flex items-center gap-2 mt-4 flex-wrap">
          <span class="text-xs text-on-surface-variant font-medium shrink-0">工作城市：</span>
          <button v-for="city in cities" :key="city" @click="selectedCity = city"
            class="px-3 py-1 text-xs rounded-full transition-colors"
            :class="selectedCity === city ? 'bg-primary text-on-primary font-semibold' : 'text-on-surface-variant hover:text-on-surface hover:bg-surface-container-low'">
            {{ city }}
          </button>
          <button class="text-xs text-primary hover:underline ml-1">更多海外/省市 ▾</button>
        </div>
      </div>
    </div>

    <!-- Filters -->
    <div class="max-w-7xl mx-auto px-6 py-4">
      <div class="grid grid-cols-5 gap-3">
        <div v-for="filter in filters" :key="filter.label" class="relative">
          <select class="w-full h-10 pl-3 pr-8 bg-surface-container-lowest text-on-surface text-xs rounded-lg appearance-none cursor-pointer border border-surface-container-high focus:outline-none focus:border-primary">
            <option v-for="opt in filter.options" :key="opt">{{ opt }}</option>
          </select>
          <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[16px]">arrow_drop_down</span>
        </div>
      </div>

      <!-- Special Tags -->
      <div class="flex items-center gap-3 mt-4 flex-wrap">
        <span class="text-xs text-primary font-semibold flex items-center gap-1 shrink-0">
          <span class="material-symbols-outlined text-[14px]">school</span>
          高校就业专区特色筛选项：
        </span>
        <label v-for="tag in specialTags" :key="tag" class="flex items-center gap-1.5 cursor-pointer select-none">
          <input type="checkbox" class="w-3.5 h-3.5 rounded accent-primary cursor-pointer" />
          <span class="text-xs text-on-surface-variant hover:text-on-surface transition-colors">{{ tag }}</span>
        </label>
      </div>
    </div>

    <!-- Main Content -->
    <div class="max-w-7xl mx-auto px-6 pb-10">
      <div class="flex gap-6">
        <!-- Left: Results -->
        <div class="flex-1 min-w-0">
          <!-- Results Header -->
          <div class="flex items-center justify-between mb-4">
            <div>
              <span class="text-sm text-on-surface-variant">搜索结果 共为您筛选到 <span class="font-bold text-on-surface">1,286</span> 个应届及带薪实习岗位</span>
            </div>
            <div class="flex items-center gap-3">
              <div class="flex bg-surface-container-low rounded-lg p-1">
                <button v-for="tab in sortTabs" :key="tab" @click="activeSort = tab"
                  class="px-3 py-1.5 text-xs font-medium rounded-md transition-all"
                  :class="activeSort === tab ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface'">
                  {{ tab }}
                </button>
              </div>
              <div class="flex border border-surface-container-high rounded-lg overflow-hidden">
                <button class="w-8 h-8 flex items-center justify-center bg-surface-container-low text-primary"><span class="material-symbols-outlined text-[16px]">view_list</span></button>
                <button class="w-8 h-8 flex items-center justify-center text-on-surface-variant hover:bg-surface-container-low"><span class="material-symbols-outlined text-[16px]">grid_view</span></button>
              </div>
            </div>
          </div>

          <!-- Job Cards -->
          <div class="space-y-4">
            <div v-for="job in jobs" :key="job.id"
              class="bg-surface-container-lowest rounded-xl border border-surface-container-high p-5 hover:shadow-md hover:border-primary/30 transition-all cursor-pointer group">
              <div class="flex items-start gap-4">
                <div class="w-11 h-11 rounded-lg flex items-center justify-center text-white font-bold text-sm shrink-0" :style="{ background: job.logoBg }">
                  {{ job.logoText }}
                </div>
                <div class="flex-1 min-w-0">
                  <div class="flex items-start justify-between gap-4">
                    <div class="min-w-0">
                      <div class="flex items-center gap-2 flex-wrap">
                        <h3 class="text-sm font-bold text-on-surface group-hover:text-primary transition-colors">{{ job.title }}</h3>
                        <span v-for="badge in job.badges" :key="badge.text" class="px-1.5 py-0.5 rounded text-[10px] font-semibold" :class="badge.class">{{ badge.text }}</span>
                      </div>
                      <p class="text-xs text-on-surface-variant mt-0.5">{{ job.company }}</p>
                    </div>
                    <div class="text-right shrink-0">
                      <span class="text-lg font-bold text-primary">{{ job.salary }}</span>
                      <p v-if="job.salaryNote" class="text-[11px] text-on-surface-variant mt-0.5">{{ job.salaryNote }}</p>
                    </div>
                  </div>

                  <!-- Tags -->
                  <div class="flex flex-wrap gap-1.5 mt-3">
                    <span v-for="tag in job.tags" :key="tag" class="px-2 py-0.5 text-[11px] rounded bg-surface-container-low text-on-surface-variant border border-surface-container-high">{{ tag }}</span>
                  </div>

                  <!-- Benefits -->
                  <div class="flex flex-wrap gap-1.5 mt-2">
                    <span v-for="b in job.benefits" :key="b" class="text-[11px] text-primary">· {{ b }}</span>
                  </div>

                  <!-- Bottom Row -->
                  <div class="flex items-center justify-between mt-3 pt-3 border-t border-surface-container-high">
                    <span class="text-[11px] text-on-surface-variant">{{ job.meta }}</span>
                    <div class="flex items-center gap-2">
                      <button class="w-8 h-8 rounded-lg flex items-center justify-center text-on-surface-variant hover:text-primary hover:bg-primary/5 transition-colors">
                        <span class="material-symbols-outlined text-[18px]">favorite_border</span>
                      </button>
                      <button class="h-8 px-3 rounded-lg text-xs font-medium border border-surface-container-high text-on-surface-variant hover:border-primary hover:text-primary transition-colors">在线沟通</button>
                      <button @click.stop="requireAuth" class="h-8 px-4 rounded-lg text-xs font-semibold bg-primary text-on-primary hover:bg-primary/90 transition-colors">立即投递</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Pagination -->
          <div class="flex items-center justify-between mt-8">
            <span class="text-xs text-on-surface-variant">显示第 1-5 条，共 1,286 个职位</span>
            <div class="flex items-center gap-1.5">
              <button class="w-8 h-8 rounded-lg border border-surface-container-high flex items-center justify-center text-on-surface-variant hover:border-primary hover:text-primary"><span class="material-symbols-outlined text-[16px]">chevron_left</span></button>
              <button v-for="p in 4" :key="p" class="w-8 h-8 rounded-lg flex items-center justify-center text-xs font-medium transition-colors"
                :class="p === 1 ? 'bg-primary text-on-primary' : 'border border-surface-container-high text-on-surface-variant hover:border-primary hover:text-primary'">{{ p }}</button>
              <span class="text-on-surface-variant text-xs">...</span>
              <button class="w-8 h-8 rounded-lg border border-surface-container-high flex items-center justify-center text-xs text-on-surface-variant hover:border-primary hover:text-primary">42</button>
              <button class="w-8 h-8 rounded-lg border border-surface-container-high flex items-center justify-center text-on-surface-variant hover:border-primary hover:text-primary"><span class="material-symbols-outlined text-[16px]">chevron_right</span></button>
              <span class="text-xs text-on-surface-variant ml-2">到第</span>
              <input type="text" value="1" class="w-8 h-8 text-center text-xs border border-surface-container-high rounded-lg focus:outline-none focus:border-primary" />
              <span class="text-xs text-on-surface-variant">页</span>
              <button class="h-8 px-3 text-xs font-medium text-primary border border-primary rounded-lg hover:bg-primary/5 transition-colors">确认</button>
            </div>
          </div>
        </div>

        <!-- Right Sidebar -->
        <div class="hidden lg:block w-80 shrink-0 space-y-5">
          <!-- AI Resume Diagnosis -->
          <div class="bg-gradient-to-br from-primary to-[#0a2472] rounded-2xl p-6 text-on-primary relative overflow-hidden">
            <div class="absolute -top-10 -right-10 w-32 h-32 rounded-full bg-white/10 blur-2xl pointer-events-none"></div>
            <div class="relative">
              <div class="flex items-center gap-2 mb-2">
                <span class="material-symbols-outlined text-[18px]">auto_awesome</span>
                <span class="text-xs font-semibold bg-white/15 px-2 py-0.5 rounded-full">AI 智能就业助手</span>
              </div>
              <h3 class="text-lg font-bold mb-2">简历匹配度即时诊断</h3>
              <p class="text-xs text-on-primary/80 leading-relaxed mb-4">上传当前在线简历，AI大模型将根据您的主修课程、项目实操及竞赛经历，一键智能推演匹配度高于 90% 的校招高压冲刺岗位。</p>
              <div class="bg-white/10 backdrop-blur-sm rounded-xl p-4 text-center border border-white/15">
                <span class="material-symbols-outlined text-3xl text-on-primary/60 mb-2">cloud_upload</span>
                <p class="text-xs text-on-primary/80">拖拽简历文件或点击选择</p>
                <p class="text-[10px] text-on-primary/60 mt-1">支持 PDF / DOCX（不超过20MB）</p>
              </div>
              <div class="flex items-center justify-between mt-3">
                <span class="text-[11px] text-on-primary/70">✓ 已对本校 8,420 位同学提供匹配</span>
                <button class="text-[11px] text-on-primary hover:underline">查看诊断案例</button>
              </div>
            </div>
          </div>

          <!-- Hot Rankings -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-sm font-bold text-on-surface flex items-center gap-1.5">
                <span class="material-symbols-outlined text-primary text-[18px]">local_fire_department</span>
                高校毕业生求职热度榜
              </h3>
              <span class="text-[10px] text-on-surface-variant">每日10:00更新</span>
            </div>
            <div class="space-y-3">
              <div v-for="(item, i) in hotJobs" :key="i"
                class="flex items-center gap-3 py-1.5 cursor-pointer hover:bg-surface-container-low -mx-2 px-2 rounded-lg transition-colors">
                <span class="w-5 h-5 rounded flex items-center justify-center text-[10px] font-bold shrink-0"
                  :class="i < 3 ? 'bg-primary text-on-primary' : 'bg-surface-container-low text-on-surface-variant'">{{ i + 1 }}</span>
                <div class="flex-1 min-w-0">
                  <p class="text-xs font-medium text-on-surface truncate">{{ item.title }}</p>
                  <p class="text-[10px] text-on-surface-variant">{{ item.company }} · {{ item.city }}</p>
                </div>
                <div class="text-right shrink-0">
                  <span class="text-xs font-bold" :class="i < 3 ? 'text-primary' : 'text-on-surface-variant'">{{ item.score }}</span>
                  <p class="text-[10px] text-on-surface-variant">{{ item.applicants }}人投递</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Safety & Certification -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
            <div class="flex items-center gap-2 mb-3">
              <span class="material-symbols-outlined text-primary text-[18px]">verified_user</span>
              <h3 class="text-sm font-bold text-on-surface">校招安全防诈与企业资质认证</h3>
            </div>
            <p class="text-[11px] text-on-surface-variant mb-3">高校就业指导中心权威背书监督</p>
            <div class="space-y-2.5">
              <div class="flex items-start gap-2">
                <span class="material-symbols-outlined text-primary text-[14px] mt-0.5">check_circle</span>
                <p class="text-[11px] text-on-surface-variant leading-relaxed"><span class="font-semibold text-on-surface">名企资质三重核验：</span>本平台全量入驻企业均已核查工商注册号及高校直签聘用协议。</p>
              </div>
              <div class="flex items-start gap-2">
                <span class="material-symbols-outlined text-primary text-[14px] mt-0.5">check_circle</span>
                <p class="text-[11px] text-on-surface-variant leading-relaxed"><span class="font-semibold text-on-surface">严正防诈警示：</span>正规招聘绝不以"实习押金、岗前培训费、证件代跑"为由向毕业生收取任何费用。</p>
              </div>
            </div>
            <div class="flex items-center justify-between mt-4 pt-3 border-t border-surface-container-high">
              <span class="text-[11px] text-on-surface-variant">高校就业维权热线：400-820-2025</span>
              <button class="text-[11px] text-primary hover:underline font-medium">违规举报通道</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAppStore } from '../stores/app'

const store = useAppStore()
const searchQuery = ref('')
const selectedCity = ref('全国')
const activeSort = ref('综合排序')

function requireAuth() {
  if (!store.isLoggedIn) {
    window.dispatchEvent(new CustomEvent('open-login-modal'))
  }
}

const cities = ['全国', '北京', '上海', '深圳', '杭州', '广州', '成都', '武汉', '南京']

const filters = [
  { label: '职位职能', options: ['人工智能 / 大模型算法'] },
  { label: '期望月薪 / 日薪', options: ['15k-25k · 15薪及以上'] },
  { label: '学历要求', options: ['硕士研究生及以上'] },
  { label: '毕业身份 / 届别', options: ['2025应届毕业生'] },
  { label: '企业性质', options: ['不限性质'] },
]

const specialTags = ['留用转正率 > 80%', '央企国企直属名录', '985/211校友导师通道', '解决户口/提供宿舍', '无需笔试直面业务合伙人']

const sortTabs = ['综合排序', '最新发布', '薪资最高', '投递响应最快']

const jobs = ref([
  {
    id: 1,
    title: '大模型算法工程师（2025届校招/带薪实习）',
    company: '智航未来科技有限公司 · 人工智能独角兽 · 1000-5000人 · 已签约本校学长 14人',
    logoBg: '#1a56db',
    logoText: '智',
    badges: [{ text: '校招直通', class: 'bg-primary/10 text-primary' }, { text: 'HOT 热招', class: 'bg-red-50 text-red-600 border border-red-200' }],
    salary: '25k-40k · 16薪',
    salaryNote: '实习补贴：500-800元/天',
    tags: ['北京·海淀区', '硕士及以上学历', '2025届毕业生', '留用转正率90%+', 'ACM/ICPC竞赛优先', '国家级导师1对1带教'],
    benefits: ['六险二金', '免息首款购房无息借款', '免费自助三餐+健身房', '重点单位北京落户指标支持'],
    meta: '10分钟前发布 · HR今日在线 · 简历处理率 98.4%'
  },
  {
    id: 2,
    title: '前端研发工程师（校招转正 / 核心引擎组）',
    company: '腾跃互娱软件股份有限公司 · 互联网上市企业 · 5000-10000人',
    logoBg: '#006591',
    logoText: 'E',
    badges: [{ text: '校招快转批', class: 'bg-emerald-50 text-emerald-600 border border-emerald-200' }],
    salary: '16k-25k · 15薪',
    salaryNote: '季度绩效奖金 + 项目分红',
    tags: ['深圳·南山区·科技园', '本科及以上学历', 'Vue3 / React / WebGL / TS', '核心自研业务线', 'T10技术大牛带队', '100%校招实习留用率'],
    benefits: ['带薪年假12天', '企业顶配MacBook Pro', '年度出国游团建', '打车全额报销'],
    meta: '1小时前发布 · 招聘负责人 20分钟前在线'
  },
  {
    id: 3,
    title: '新能源电池电芯研发工程师',
    company: '华夏绿动动力股份集团 · 高端装备制造 / 新能源 · 10000人以上',
    logoBg: '#0a8754',
    logoText: '华',
    badges: [{ text: '央企直管', class: 'bg-amber-50 text-amber-700 border border-amber-200' }, { text: '安家费15万', class: 'bg-blue-50 text-blue-600 border border-blue-200' }],
    salary: '18k-28k · 14薪',
    salaryNote: '+ 专项引才补贴15万元',
    tags: ['上海·嘉定工业区', '硕士 / 博士', '材料物理 / 化学化工', '国家自然科学基金联合项目', '提供人才公寓单间', '国家重点实验室依托'],
    benefits: ['事业编制通道', '企业年金', '节日慰问金与健康体检', '公派境外高校交流'],
    meta: '3小时前更新发布 · 央企直聘绿色通道'
  },
  {
    id: 4,
    title: '智能制造管培生（青年领航计划2025）',
    company: '联星智造工业集团 · 工业机器人 / 智造龙头 · 3000-5000人',
    logoBg: '#6b21a8',
    logoText: '联',
    badges: [{ text: '管培体系', class: 'bg-purple-50 text-purple-600 border border-purple-200' }],
    salary: '12k-18k · 13薪',
    salaryNote: '快速晋升通道 + 轮岗绩效',
    tags: ['苏州·工业园区', '本科及以上 (工科/管理优先)', '3年跨部门轮岗实战', '集团VP高管专属带教', '出山定级部门负责人'],
    benefits: [],
    meta: '昨天发布 · 已收到本校投递 38 份'
  },
  {
    id: 5,
    title: '金融量化研究员实习生（秋招留用通道）',
    company: '泛海远资产管理有限公司 · 百亿量化私募 · 100-499人',
    logoBg: '#0ea5e9',
    logoText: '泛',
    badges: [{ text: '带薪日常实习', class: 'bg-primary/10 text-primary' }, { text: '极高留用率', class: 'bg-emerald-50 text-emerald-600 border border-emerald-200' }],
    salary: '600 - 1,000元 / 天',
    salaryNote: '转正全职起薪50万 · 100万+',
    tags: ['北京·朝阳区·国贸CBD', '硕士 / 博士在读', '数学 / 物理 / 计算机 / 金融工程', '提供留用Offer直通卡', '算力集群全额开放'],
    benefits: [],
    meta: '2天前发布 · 合伙人亲自初筛'
  }
])

const hotJobs = [
  { title: '大模型算法与评测实习生', company: '智航未来', city: '北京海淀', score: '99.2 热度', applicants: 320 },
  { title: '央企国家电力工程研究员', company: '华能新能源装备', city: '上海', score: '96.8 热度', applicants: 285 },
  { title: '量化高频交易开发（C++）', company: '博远资产', city: '北京', score: '93.4 热度', applicants: 210 },
  { title: '全栈前端系统工程管培生', company: '腾跃互娱', city: '深圳', score: '88.7 热度', applicants: 176 },
  { title: '青年领航智造轮岗管培生', company: '联星智造', city: '苏州', score: '85.2 热度', applicants: 142 },
]
</script>
