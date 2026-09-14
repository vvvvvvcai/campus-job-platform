<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-6xl mx-auto px-4 md:px-6 lg:px-10 py-8">
      <!-- Header -->
      <div class="flex items-center justify-between mb-6">
        <div>
          <div class="flex items-center gap-3 mb-1">
            <span class="material-symbols-outlined text-[var(--primary)] text-3xl">mail</span>
            <h1 class="text-2xl md:text-3xl font-bold text-[var(--on-surface)]">我的投递记录</h1>
          </div>
          <p class="text-sm text-[var(--on-surface-variant)]">实时追踪简历送达、企业HR查阅、面试邀请及录用签约全流程，助力高效求职把控。</p>
        </div>
        <div class="hidden md:flex items-center gap-2 text-xs text-[var(--on-surface-variant)] bg-white px-4 py-2 rounded-xl border border-gray-100 shadow-sm">
          <span class="material-symbols-outlined text-sm text-gray-400">sync</span>
          同步更新于 今天 14:32
        </div>
      </div>

      <!-- Stat Cards -->
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        <div v-for="stat in statCards" :key="stat.label"
          class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 relative overflow-hidden">
          <div class="flex items-center justify-between mb-2">
            <span class="text-xs text-[var(--on-surface-variant)] font-medium">{{ stat.label }}</span>
            <span :class="['material-symbols-outlined text-xl', stat.iconColor]">{{ stat.icon }}</span>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-3xl font-bold" :class="stat.valueColor">{{ stat.value }}</span>
            <span class="text-xs text-[var(--on-surface-variant)]">份职位</span>
          </div>
          <p class="text-xs mt-1.5" :class="stat.subColor">{{ stat.sub }}</p>
          <div v-if="stat.highlight" class="mt-2">
            <span class="text-xs text-amber-600 font-medium flex items-center gap-1">
              <span class="w-1.5 h-1.5 rounded-full bg-amber-500"></span>
              {{ stat.highlight }}
            </span>
          </div>
        </div>
      </div>

      <!-- Filter Tabs + Search -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-4 mb-6">
        <div class="flex flex-col md:flex-row md:items-center gap-4">
          <!-- Tabs -->
          <div class="flex items-center gap-1 flex-1 overflow-x-auto">
            <button v-for="tab in filterTabs" :key="tab.key" @click="activeFilter = tab.key"
              :class="['px-4 py-2 rounded-xl text-sm font-medium whitespace-nowrap transition-all relative',
                activeFilter === tab.key ? 'bg-[var(--primary)] text-white shadow-sm' : 'text-[var(--on-surface-variant)] hover:bg-gray-50']">
              {{ tab.label }} ({{ tab.count }})
              <span v-if="tab.dot" class="absolute top-1.5 right-1.5 w-2 h-2 rounded-full bg-amber-500"></span>
            </button>
          </div>
          <!-- Search + Sort -->
          <div class="flex items-center gap-3">
            <div class="relative">
              <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 text-[18px]">search</span>
              <input v-model="searchQuery" type="text" placeholder="输入职位或公司名称检索..."
                class="w-56 h-10 pl-10 pr-4 bg-gray-50 text-sm rounded-xl border border-gray-200 focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10 outline-none" />
            </div>
            <select class="h-10 px-4 bg-gray-50 text-sm rounded-xl border border-gray-200 appearance-none cursor-pointer outline-none pr-8 text-[var(--on-surface-variant)]">
              <option>投递时间：最近到最早</option>
              <option>投递时间：最早到最近</option>
            </select>
          </div>
        </div>
      </div>

      <!-- Application Cards -->
      <div class="space-y-4 mb-6">
        <div v-for="app in filteredApplications" :key="app.id"
          class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6 hover:shadow-md transition-shadow">
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <!-- Title + Tags + Salary -->
              <div class="flex items-center gap-3 flex-wrap">
                <h3 class="font-bold text-[var(--on-surface)] text-base">{{ app.position }}</h3>
                <span v-for="tag in app.tags" :key="tag"
                  class="px-2.5 py-0.5 rounded-full text-[11px] font-semibold"
                  :class="tag.class">{{ tag.label }}</span>
                <span class="text-base font-bold text-[var(--primary)] ml-2">{{ app.salary }}</span>
              </div>
              <!-- Company + Location -->
              <div class="flex items-center gap-3 mt-2 text-sm text-[var(--on-surface-variant)]">
                <span class="flex items-center gap-1">
                  <span class="text-base">🏢</span> {{ app.company }}
                </span>
                <span v-if="app.companyTag" class="px-2 py-0.5 bg-blue-50 text-blue-600 text-[11px] rounded font-medium">{{ app.companyTag }}</span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[14px]">location_on</span> {{ app.location }}
                </span>
              </div>
              <!-- Time + Resume -->
              <div class="flex items-center gap-4 mt-3 text-xs text-[var(--on-surface-variant)]">
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[14px]">schedule</span>
                  投递时间：{{ app.appliedAt }}
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[14px]">description</span>
                  使用简历：{{ app.resume }}
                </span>
              </div>
            </div>
            <!-- Right Actions -->
            <div class="flex flex-col items-end gap-2 shrink-0 ml-4">
              <button class="flex items-center gap-2 px-5 py-2.5 bg-[var(--primary)] text-white rounded-xl text-sm font-semibold hover:bg-[var(--primary-container)] transition-colors shadow-sm">
                <span class="material-symbols-outlined text-[16px]">visibility</span>
                查看进度
              </button>
              <button class="text-xs text-[var(--on-surface-variant)] hover:text-[var(--primary)] flex items-center gap-1 transition-colors">
                查看原招聘职位
                <span class="material-symbols-outlined text-[14px]">open_in_new</span>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div class="flex items-center justify-between text-sm text-[var(--on-surface-variant)]">
        <span>共 {{ totalApplications }} 条投递记录，每页显示 5 条，当前第 {{ currentPage }} / {{ totalPages }} 页</span>
        <div class="flex items-center gap-2">
          <button @click="currentPage = Math.max(1, currentPage - 1)" :disabled="currentPage === 1"
            class="px-4 py-2 rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors disabled:opacity-40 disabled:cursor-not-allowed flex items-center gap-1">
            <span class="material-symbols-outlined text-[16px]">chevron_left</span> 上一页
          </button>
          <button v-for="p in totalPages" :key="p" @click="currentPage = p"
            :class="['w-9 h-9 rounded-lg text-sm font-medium transition-all',
              p === currentPage ? 'bg-[var(--primary)] text-white shadow-sm' : 'border border-gray-200 hover:bg-gray-50']">
            {{ p }}
          </button>
          <button @click="currentPage = Math.min(totalPages, currentPage + 1)" :disabled="currentPage === totalPages"
            class="px-4 py-2 rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors disabled:opacity-40 disabled:cursor-not-allowed flex items-center gap-1">
            下一页 <span class="material-symbols-outlined text-[16px]">chevron_right</span>
          </button>
        </div>
      </div>

      <!-- 求职推进轨迹 Section -->
      <div class="mt-10 bg-white rounded-2xl border border-gray-100 shadow-sm p-8">
        <div class="flex items-start justify-between mb-8">
          <div>
            <h2 class="text-xl font-bold text-[var(--on-surface)] mb-1">求职推进轨迹</h2>
            <p class="text-sm text-[var(--on-surface-variant)]">系统全程存证保障，关键节点实时推送通知并同步高校就业系统</p>
          </div>
          <div class="hidden md:flex items-center gap-2 text-sm text-[var(--primary)] bg-blue-50 px-4 py-2 rounded-xl font-medium">
            <span class="w-2 h-2 rounded-full bg-[var(--primary)]"></span>
            流程时效正常（平均流转率超 92% 同类岗位）
          </div>
        </div>

        <!-- Timeline -->
        <div class="relative">
          <!-- Connection Line -->
          <div class="absolute top-6 left-0 right-0 h-0.5 bg-gray-200"></div>
          <div class="absolute top-6 left-0 h-0.5 bg-[var(--primary)]" :style="{ width: '58%' }"></div>

          <!-- Steps -->
          <div class="grid grid-cols-2 md:grid-cols-6 gap-4">
            <div v-for="(step, index) in progressSteps" :key="index" class="relative flex flex-col items-center text-center">
              <!-- Icon Circle -->
              <div :class="['relative z-10 w-12 h-12 rounded-full flex items-center justify-center border-4 border-white shadow-sm mb-4',
                step.done ? 'bg-[var(--primary)]' : step.current ? 'bg-[var(--primary)]' : 'bg-gray-100']">
                <span v-if="step.done" class="material-symbols-outlined text-white text-xl">check</span>
                <span v-else-if="step.current" class="material-symbols-outlined text-white text-xl">{{ step.icon }}</span>
                <span v-else class="material-symbols-outlined text-gray-400 text-xl">{{ step.icon }}</span>
              </div>
              <!-- Badge -->
              <span v-if="step.badge" :class="['px-3 py-1 rounded-full text-[11px] font-semibold mb-3',
                step.done ? 'bg-[var(--primary)]/10 text-[var(--primary)]' : step.current ? 'bg-[var(--primary)]/10 text-[var(--primary)]' : 'bg-gray-100 text-gray-500']">
                {{ step.badge }}
              </span>
              <!-- Title -->
              <h4 :class="['font-bold text-sm mb-1',
                step.done || step.current ? 'text-[var(--on-surface)]' : 'text-gray-400']">{{ step.title }}</h4>
              <!-- Date -->
              <p :class="['text-[11px] mb-2', step.done || step.current ? 'text-[var(--on-surface-variant)]' : 'text-gray-400']">{{ step.date }}</p>
              <!-- Description -->
              <p :class="['text-[11px] leading-relaxed', step.done || step.current ? 'text-[var(--on-surface-variant)]' : 'text-gray-400']">{{ step.desc }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const activeFilter = ref('all')
const searchQuery = ref('')
const currentPage = ref(1)
const totalPages = ref(3)
const totalApplications = ref(12)

const filterTabs = [
  { key: 'all', label: '全部', count: 12 },
  { key: 'pending', label: '待查看', count: 3 },
  { key: 'viewed', label: '已查看', count: 2 },
  { key: 'interview', label: '面试邀请', count: 4, dot: true },
  { key: 'offer', label: '已录用', count: 1 },
  { key: 'rejected', label: '不合适', count: 2 }
]

const statCards = [
  {
    label: '全部投递记录',
    value: 12,
    sub: '+ 2 本周新投递',
    icon: 'description',
    iconColor: 'text-[var(--primary)]',
    valueColor: 'text-[var(--on-surface)]',
    subColor: 'text-[var(--on-surface-variant)]'
  },
  {
    label: '待HR查阅',
    value: 3,
    sub: '平均响应 24-48 小时',
    icon: 'schedule',
    iconColor: 'text-amber-500',
    valueColor: 'text-amber-600',
    subColor: 'text-[var(--on-surface-variant)]'
  },
  {
    label: '面试与沟通中',
    value: 4,
    sub: '场推进',
    icon: 'chat_bubble',
    iconColor: 'text-emerald-500',
    valueColor: 'text-emerald-600',
    subColor: 'text-[var(--on-surface-variant)]',
    highlight: '近期有 1 场待参加'
  },
  {
    label: '已获Offer / 录用',
    value: 1,
    sub: '份录用意向',
    icon: 'check_circle',
    iconColor: 'text-blue-500',
    valueColor: 'text-blue-600',
    subColor: 'text-[var(--on-surface-variant)]',
    highlight: '📥 待三方网签'
  }
]

const applications = ref([
  {
    id: 1,
    position: '大模型算法工程师 (2025届校招)',
    company: '智维未来科技有限公司',
    companyTag: '高新技术企业',
    location: '北京 · 海淀区',
    salary: '25k-40k · 16薪',
    appliedAt: '2025-03-08 10:15',
    resume: '2025届计算机研发与算法专向简历.pdf',
    tags: [{ label: '算法研发专项', class: 'bg-amber-50 text-amber-700 border border-amber-200' }],
    status: 'interview'
  },
  {
    id: 2,
    position: '全栈开发工程师（管培生计划）',
    company: '字节跳动互联网研发中心',
    companyTag: '独角兽企业',
    location: '北京 · 朝阳区',
    salary: '18k-28k · 15薪',
    appliedAt: '2025-02-28 16:30',
    resume: '林晨_2025届全栈开发通用简历.pdf',
    tags: [{ label: '管培生计划', class: 'bg-blue-50 text-blue-700 border border-blue-200' }],
    status: 'viewed'
  },
  {
    id: 3,
    position: '电网自动化与新能源系统工程师',
    company: '国家电网 · 全球能源互联网研究院',
    companyTag: '重点科研院所',
    location: '北京 · 昌平区',
    salary: '22k-32k/月',
    appliedAt: '2025-03-05 09:20',
    resume: '林晨_嵌入式系统与智能电气方向.pdf',
    tags: [{ label: '央企专项校招', class: 'bg-purple-50 text-purple-700 border border-purple-200' }],
    status: 'interview'
  },
  {
    id: 4,
    position: 'Azure 云原生开发实习生',
    company: '微软中国 · 亚太研发集团',
    companyTag: '外资研发中心',
    location: '上海 · 徐汇区',
    salary: '25k-35k/月',
    appliedAt: '2025-03-07 14:10',
    resume: '林晨_Cloud_Native_EN_CN.pdf',
    tags: [{ label: '春季日常实习', class: 'bg-emerald-50 text-emerald-700 border border-emerald-200' }],
    status: 'pending'
  },
  {
    id: 5,
    position: '量化策略研究员',
    company: '幻方量化投资中心',
    location: '杭州 · 西湖区',
    salary: '40k-60k',
    appliedAt: '2025-02-15 11:05',
    resume: '林晨_量化策略_幻方专向.pdf',
    tags: [{ label: '秋招已归档', class: 'bg-gray-100 text-gray-500 border border-gray-200' }],
    status: 'rejected'
  }
])

const statusConfig = {
  pending: { label: '待查看', bg: 'bg-gray-50', text: 'text-gray-600' },
  viewed: { label: '已查看', bg: 'bg-blue-50', text: 'text-blue-600' },
  interview: { label: '面试中', bg: 'bg-emerald-50', text: 'text-emerald-600' },
  offer: { label: '已录用', bg: 'bg-amber-50', text: 'text-amber-600' },
  rejected: { label: '不合适', bg: 'bg-gray-50', text: 'text-gray-500' }
}

const filteredApplications = computed(() => {
  if (activeFilter.value === 'all') return applications.value
  return applications.value.filter(a => a.status === activeFilter.value)
})

const progressSteps = [
  {
    title: '投递成功',
    date: '2025-03-08 10:15',
    desc: '投递《算法专向简历》，系统校验学籍档案无误，直通核心库。',
    badge: '已完成',
    done: true,
    icon: 'check'
  },
  {
    title: 'HR 初筛通过',
    date: '2025-03-08 16:30',
    desc: '张经理查阅，评语："专业对口，具备高质量顶会成果与实习经历"。',
    badge: '匹配度 96%',
    done: true,
    icon: 'check'
  },
  {
    title: '技术一面通过',
    date: '2025-03-10 15:00',
    desc: '李博士评定："算法功底敏捷，分布式工程视野宽广，推荐进入二面"。',
    badge: '技术评分 A+',
    done: true,
    icon: 'check'
  },
  {
    title: '技术二面邀约',
    date: '2025-03-11 09:30',
    desc: '邀约确认已送达，定于03月12日15:30进行，首席科学家主持。',
    badge: '进行中',
    current: true,
    icon: 'pending'
  },
  {
    title: 'HR 综合面谈',
    date: '预计 03-14 开展',
    desc: '文化契合度综合考察及期望薪酬、福利保障意向沟通。',
    badge: '待解锁',
    done: false,
    icon: 'hourglass_empty'
  },
  {
    title: 'Offer 与网签',
    date: '待推进',
    desc: '生成正式录用函并直联教育部全国高校毕业去向登记系统。',
    badge: '最终环节',
    done: false,
    icon: 'school'
  }
]
</script>
