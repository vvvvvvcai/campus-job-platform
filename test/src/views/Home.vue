<template>
  <div class="min-h-screen bg-background">
    <!-- Hero Section -->
    <section class="max-w-7xl mx-auto px-6 pt-10 pb-6">
      <div class="flex items-start justify-between gap-8">
        <div class="flex-1">
          <div class="flex items-center gap-2 mb-3">
            <span class="material-symbols-outlined text-primary text-lg">verified</span>
            <span class="text-xs text-on-surface-variant font-medium">教育部全国高校就业协作网络互联单位</span>
          </div>
          <h1 class="text-3xl lg:text-4xl font-bold text-on-surface leading-tight mb-3">
            探索优质校招职位，启航未来职业生涯
          </h1>
          <p class="text-sm text-on-surface-variant">提供直连名企HR、导师背书推荐、线上智能网签的三维一体化智慧就业保障系统。</p>
        </div>
        <!-- Resume Score Card -->
        <div class="hidden md:flex items-center gap-4 bg-surface-container-lowest rounded-xl border border-surface-container-high px-5 py-4 shrink-0">
          <div class="relative w-14 h-14">
            <svg class="w-14 h-14 -rotate-90" viewBox="0 0 56 56">
              <circle cx="28" cy="28" r="24" fill="none" stroke="#e8e8e8" stroke-width="4"/>
              <circle cx="28" cy="28" r="24" fill="none" stroke="#1a56db" stroke-width="4" stroke-linecap="round" :stroke-dasharray="150.8" :stroke-dashoffset="150.8 - (150.8 * 0.92)"/>
            </svg>
            <span class="absolute inset-0 flex items-center justify-center text-sm font-bold text-primary">92%</span>
          </div>
          <div>
            <p class="text-sm font-semibold text-on-surface">林晨的在线简历</p>
            <p class="text-xs text-on-surface-variant mt-0.5">竞争力评估：极高，击败 <span class="text-primary font-semibold">94%</span> 同级同学</p>
          </div>
          <button class="px-3 py-1.5 text-xs font-semibold text-primary border border-primary rounded-lg hover:bg-primary/5 transition-colors shrink-0">优化建议</button>
        </div>
      </div>
    </section>

    <!-- Search Section -->
    <section class="max-w-7xl mx-auto px-6 pb-4">
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
        <div class="flex gap-3 items-center">
          <div class="relative flex-1">
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant">search</span>
            <input v-model="searchQuery" type="text" placeholder="算法工程师"
              class="w-full h-11 pl-10 pr-10 bg-surface-container-low text-on-surface placeholder:text-on-surface-variant text-sm rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 border border-surface-container-high" />
            <button v-if="searchQuery" @click="searchQuery = ''" class="absolute right-3 top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface">
              <span class="material-symbols-outlined text-[18px]">close</span>
            </button>
          </div>
          <div class="h-11 w-px bg-surface-container-high hidden sm:block"></div>
          <div class="relative hidden sm:block">
            <select class="h-11 pl-9 pr-8 bg-surface-container-low text-on-surface text-sm rounded-xl appearance-none cursor-pointer focus:outline-none border border-surface-container-high">
              <option>央企/国企重点单位</option>
              <option>互联网技术先锋</option>
              <option>跨国金融科技</option>
            </select>
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">apartment</span>
            <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">arrow_drop_down</span>
          </div>
          <div class="relative hidden sm:block">
            <select class="h-11 pl-9 pr-8 bg-surface-container-low text-on-surface text-sm rounded-xl appearance-none cursor-pointer focus:outline-none border border-surface-container-high">
              <option>北京及周边</option>
              <option>上海</option>
              <option>深圳</option>
              <option>杭州</option>
            </select>
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">location_on</span>
            <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">arrow_drop_down</span>
          </div>
          <button class="h-11 px-6 bg-primary text-on-primary font-semibold text-sm rounded-xl hover:bg-primary/90 transition-colors flex items-center gap-2 shrink-0">
            <span class="material-symbols-outlined text-[18px]">tune</span>
            精准检索
          </button>
        </div>
        <!-- Hot Tags -->
        <div class="flex items-center gap-2 mt-3 flex-wrap">
          <span class="text-xs text-on-surface-variant shrink-0">🔥 热门搜索标签：</span>
          <button v-for="tag in hotTags" :key="tag" class="px-3 py-1 text-xs rounded-full border transition-colors"
            :class="tag === '央企专招专场' ? 'border-primary bg-primary/5 text-primary font-semibold' : 'border-surface-container-high text-on-surface-variant hover:border-primary hover:text-primary'">
            {{ tag }}
          </button>
        </div>
      </div>
    </section>

    <!-- Job Recommendations -->
    <section class="max-w-7xl mx-auto px-6 py-8">
      <div class="flex items-start justify-between mb-6">
        <div>
          <div class="flex items-center gap-2 mb-1">
            <span class="w-2 h-2 rounded-full bg-primary"></span>
            <span class="text-xs text-on-surface-variant font-medium">精准人岗匹配算法推荐</span>
          </div>
          <h2 class="text-2xl font-bold text-on-surface">名企校招与留用实习严选</h2>
        </div>
        <div class="flex gap-1 bg-surface-container-low rounded-lg p-1 overflow-x-auto">
          <button v-for="tab in jobTabs" :key="tab" @click="activeJobTab = tab"
            class="px-3 py-1.5 text-xs font-medium rounded-md whitespace-nowrap transition-all"
            :class="activeJobTab === tab ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface'">
            {{ tab }}
          </button>
        </div>
      </div>

      <div class="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
        <div v-for="job in jobs" :key="job.id"
          class="bg-surface-container-lowest rounded-xl border border-surface-container-high p-5 hover:shadow-lg hover:border-primary/30 transition-all group cursor-pointer flex flex-col">
          <div class="flex items-start justify-between mb-3">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-lg flex items-center justify-center text-white font-bold text-sm shrink-0" :style="{ background: job.logoBg }">
                <span v-if="job.logoText">{{ job.logoText }}</span>
                <span v-else class="material-symbols-outlined text-lg">{{ job.logoIcon }}</span>
              </div>
              <div class="min-w-0">
                <h3 class="text-sm font-bold text-on-surface truncate group-hover:text-primary transition-colors">{{ job.title }}</h3>
                <p class="text-xs text-on-surface-variant truncate">{{ job.company }}</p>
              </div>
            </div>
            <span v-if="job.badge" class="px-2 py-0.5 rounded text-[10px] font-semibold shrink-0 ml-2" :class="job.badgeClass">{{ job.badge }}</span>
          </div>

          <div class="mb-3">
            <span class="text-lg font-bold text-primary">{{ job.salary }}</span>
            <span v-if="job.salaryNote" class="text-xs text-on-surface-variant ml-1">{{ job.salaryNote }}</span>
            <span v-if="job.salaryExtra" class="text-xs text-on-surface-variant ml-2">{{ job.salaryExtra }}</span>
          </div>

          <div class="flex flex-wrap gap-1.5 mb-3">
            <span v-for="tag in job.tags" :key="tag" class="px-2 py-0.5 bg-surface-container-low text-on-surface-variant text-[11px] rounded">{{ tag }}</span>
          </div>

          <p class="text-xs text-on-surface-variant leading-relaxed mb-4 flex-1 line-clamp-3">{{ job.desc }}</p>

          <div class="flex items-center justify-between pt-3 border-t border-surface-container-high">
            <span class="text-[11px] text-on-surface-variant">{{ job.meta }}</span>
            <button @click.stop="requireAuth" class="px-4 py-1.5 bg-primary text-on-primary text-xs font-semibold rounded-lg hover:bg-primary/90 transition-colors">投递简历</button>
          </div>
        </div>
      </div>
    </section>

    <!-- 护航 Section -->
    <section class="bg-surface-container-low mt-8">
      <div class="max-w-7xl mx-auto px-6 py-12">
        <div class="flex items-start justify-between mb-8">
          <div>
            <div class="text-xs text-primary font-semibold mb-2">就业护航</div>
            <h2 class="text-2xl font-bold text-on-surface">数字化校招全流程护航体系</h2>
          </div>
          <p class="text-sm text-on-surface-variant max-w-md text-right hidden md:block">
            从简历投递到教育部三方协议电子互认网签，全程由高校就业中心背书监控，保障学生合法权益。
          </p>
        </div>

        <div class="grid grid-cols-2 md:grid-cols-5 gap-4">
          <div v-for="(step, i) in steps" :key="i"
            class="bg-surface-container-lowest rounded-xl p-5 border border-surface-container-high hover:border-primary/30 hover:shadow-md transition-all">
            <div class="w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold mb-3"
              :class="i === 0 ? 'bg-primary text-on-primary' : 'bg-primary/10 text-primary'">
              {{ i + 1 }}
            </div>
            <h4 class="text-sm font-bold text-on-surface mb-1.5">{{ step.title }}</h4>
            <p class="text-xs text-on-surface-variant leading-relaxed">{{ step.desc }}</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAppStore } from '../stores/app'

const store = useAppStore()
const searchQuery = ref('')
const activeJobTab = ref('全部推荐 (28)')

function requireAuth() {
  if (!store.isLoggedIn) {
    window.dispatchEvent(new CustomEvent('open-login-modal'))
  }
}

const hotTags = ['Java实习', '产品经理管培生', '央企专招专场', '留用转正率90%+', '新能源与半导体', '海外留学生回国绿色通道']

const jobTabs = ['全部推荐 (28)', '央企国企名录', '互联网技术先锋', '跨国金融科技', '应届保研/管培生']

const jobs = ref([
  {
    id: 1,
    title: '大模型算法工...',
    company: '字节跳动 · 基础架构',
    logoBg: '#1a56db',
    logoText: '字',
    badge: '急聘',
    badgeClass: 'bg-red-50 text-red-600 border border-red-200',
    salary: '400-500/天',
    salaryNote: '转正概率 85%',
    tags: ['北京·海淀', '5天/周', '至少实习4个月', '硕士优先'],
    desc: '参与海量分布式模型训练优化平台研发，提供行业顶尖导师1对1带教和算力集群支持。',
    meta: '2小时前更新 · HR在线'
  },
  {
    id: 2,
    title: '全栈开发工程...',
    company: '腾讯科技 · IEG互动娱乐',
    logoBg: '#006591',
    logoText: '腾',
    badge: '正式春招',
    badgeClass: 'bg-emerald-50 text-emerald-600 border border-emerald-200',
    salary: '18k-28k',
    salaryNote: '16薪',
    salaryExtra: '③安居借款50万',
    tags: ['深圳·南山', '全职校招', '本科及以上', '带薪年假'],
    desc: '负责全球顶级自研游戏微服务架构搭建与高可用网络服务优化，团队极具极客创新氛...',
    meta: '昨日发布 · 直通初筛'
  },
  {
    id: 3,
    title: '电力自动化与...',
    company: '国家电网 · 全球能源互联网院',
    logoBg: '#0a8754',
    logoText: '国',
    badge: '央企编内',
    badgeClass: 'bg-amber-50 text-amber-700 border border-amber-200',
    salary: '22k-32k',
    salaryNote: '月+京户',
    salaryExtra: '⑥险二金·全额落户',
    tags: ['北京·昌平', '应届硕士/博士', '电气/计算机', '国家重点研发'],
    desc: '承担特高压电网数字孪生仿真国家重点工程研究，享受人才公寓与全额住房公积金补贴。',
    meta: '已报录 420人 · 统招'
  },
  {
    id: 4,
    title: 'Azure 云原生...',
    company: '微软中国 · 亚太研发集团',
    logoBg: '#0078d4',
    logoText: 'M',
    badge: '外企WLB',
    badgeClass: 'bg-blue-50 text-blue-600 border border-blue-200',
    salary: '25k-35k',
    salaryNote: '月+期权',
    salaryExtra: '弹性混合办公',
    tags: ['上海·徐汇', '2025/2026届', '远程办公两天', '英语工作流'],
    desc: '为跨国500强企业提供微服务、容器化与智能云架构赋能，提供全球调动发展通道。',
    meta: '3小时前更新 · 快速响应'
  }
])

const steps = [
  { title: '在线一键投递', desc: '支持标准求职简历一键投递，直连企业HR后台无信息壁垒。' },
  { title: '智能简历快筛', desc: '企业HR在24小时内查看并反馈结果，求职进度实时微信通知。' },
  { title: '空中视频面试', desc: '高清视频面试房间，带在线代码编辑与作品白板共享支持。' },
  { title: '电子录用Offer', desc: '合规线上意向书发放，薪资构成与各项福利条目透明规范。' },
  { title: '全国三方网签', desc: '一键直通全国高校毕业生毕业去向登记系统，一站式完给派遣。' }
]
</script>

<style scoped>
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
