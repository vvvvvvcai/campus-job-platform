<template>
  <div class="min-h-screen bg-gray-50">
    <section class="w-full bg-white shadow-sm">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 pt-6 pb-8">
        <nav class="flex items-center gap-1 text-sm text-gray-500 mb-3">
          <router-link to="/profile" class="hover:text-primary transition-colors">个人中心</router-link>
          <span class="material-symbols-outlined text-[16px]">chevron_right</span>
          <router-link to="/applications" class="hover:text-primary transition-colors">我的求职</router-link>
          <span class="material-symbols-outlined text-[16px]">chevron_right</span>
          <span class="text-gray-900 font-semibold">投递记录</span>
        </nav>

        <div class="flex flex-col lg:flex-row lg:items-end justify-between gap-4">
          <div>
            <div class="flex items-center gap-2">
              <span class="inline-flex items-center justify-center w-9 h-9 rounded-xl bg-blue-100 text-primary shadow-sm">
                <span class="material-symbols-outlined text-[22px]">outgoing_mail</span>
              </span>
              <h1 class="text-2xl md:text-3xl text-gray-900 tracking-tight font-bold">我的投递记录</h1>
            </div>
            <p class="text-sm text-gray-500 mt-1 max-w-2xl">
              实时追踪简历送达、企业HR查阅、面试邀请及录用签约全流程，助力高效求职把控。
            </p>
          </div>
          <div class="flex items-center gap-1 bg-gray-100 px-4 py-1 rounded-full">
            <span class="w-2 h-2 rounded-full bg-blue-400 animate-ping"></span>
            <span class="text-xs text-gray-500">同步更新于 {{ lastSyncTime }}</span>
            <button class="ml-1 text-primary hover:text-blue-700 transition-colors flex items-center" title="立即刷新" @click="refreshData">
              <span class="material-symbols-outlined text-[16px]">sync</span>
            </button>
          </div>
        </div>

        <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mt-6">
          <div
            v-for="metric in metrics"
            :key="metric.label"
            class="group relative overflow-hidden bg-gray-50 rounded-xl p-4 transition-all duration-200 hover:shadow-md hover:bg-white"
          >
            <div class="flex items-center justify-between">
              <span class="text-sm text-gray-500 font-medium">{{ metric.label }}</span>
              <span class="material-symbols-outlined text-gray-400 group-hover:text-primary transition-colors">{{ metric.icon }}</span>
            </div>
            <div class="mt-2 flex items-baseline gap-1">
              <span class="text-2xl font-bold" :class="metric.valueColor">{{ metric.value }}</span>
              <span class="text-xs text-gray-500">{{ metric.unit }}</span>
            </div>
            <div class="mt-1 text-[11px] text-gray-500 flex items-center gap-1">
              <span v-if="metric.highlight" :class="metric.highlightColor">{{ metric.highlight }}</span>
              <span v-else>{{ metric.subtext }}</span>
            </div>
            <div class="absolute bottom-0 left-0 right-0 h-1 transition-colors" :class="metric.barColor"></div>
          </div>
        </div>
      </div>
    </section>

    <main class="w-full max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-8">
      <div class="bg-white rounded-xl p-4 shadow-sm mb-6">
        <div class="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
          <div class="flex items-center gap-1 overflow-x-auto pb-1 lg:pb-0 scrollbar-none">
            <button
              v-for="tab in statusTabs"
              :key="tab.key"
              class="px-4 py-1.5 rounded-full text-sm font-semibold transition-all whitespace-nowrap flex items-center gap-1"
              :class="activeTab === tab.key
                ? 'bg-primary text-white shadow-sm'
                : 'text-gray-500 hover:bg-gray-100 hover:text-gray-900'"
              @click="activeTab = tab.key"
            >
              <span>{{ tab.label }}</span>
              <span v-if="tab.hasAlert" class="w-2 h-2 rounded-full bg-red-500"></span>
            </button>
          </div>
          <div class="flex flex-col sm:flex-row items-stretch sm:items-center gap-3 shrink-0">
            <div class="relative min-w-[240px]">
              <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 text-[20px]">search</span>
              <input
                v-model="searchQuery"
                type="text"
                placeholder="输入职位或公司名称检索..."
                class="w-full pl-9 pr-4 py-2 bg-gray-50 rounded-lg text-sm text-gray-900 placeholder:text-gray-400 focus:outline-none focus:bg-white focus:ring-2 focus:ring-primary/20 transition-all"
              />
            </div>
            <div class="relative">
              <select
                v-model="sortOption"
                class="w-full sm:w-auto appearance-none bg-gray-50 pl-4 pr-8 py-2 rounded-lg text-sm text-gray-500 font-medium cursor-pointer focus:outline-none focus:bg-white focus:ring-2 focus:ring-primary/20 transition-all"
              >
                <option value="time-asc">投递时间：近到远</option>
                <option value="status">最新状态变更优先</option>
                <option value="salary-desc">薪资水平：高到低</option>
              </select>
              <span class="material-symbols-outlined absolute right-2.5 top-1/2 -translate-y-1/2 text-gray-400 text-[18px] pointer-events-none">expand_more</span>
            </div>
          </div>
        </div>
      </div>

      <div class="space-y-4">
        <article
          v-for="app in filteredApplications"
          :key="app.id"
          class="group relative bg-white rounded-xl p-5 shadow-sm hover:shadow-md transition-all duration-200"
          :class="app.archived ? 'opacity-80 hover:opacity-100' : ''"
        >
          <div class="flex flex-col xl:flex-row xl:items-start justify-between gap-5">
            <div class="flex-1 min-w-0">
              <div class="flex flex-wrap items-center gap-x-4 gap-y-1">
                <h2
                  class="text-lg font-bold transition-colors truncate"
                  :class="app.archived ? 'text-gray-400 line-through' : 'text-gray-900 group-hover:text-primary cursor-pointer'"
                >
                  {{ app.title }}
                </h2>
                <span
                  class="px-2.5 py-0.5 rounded-full text-xs font-semibold"
                  :class="app.tagColor"
                >
                  {{ app.tag }}
                </span>
                <span
                  class="text-lg font-bold ml-auto xl:ml-0"
                  :class="app.archived ? 'text-gray-400' : 'text-primary'"
                >
                  {{ app.salary }}
                </span>
              </div>
              <div class="flex flex-wrap items-center gap-x-4 gap-y-1 mt-1 text-sm text-gray-500">
                <span class="font-medium flex items-center gap-1" :class="app.archived ? 'text-gray-400' : 'text-gray-900'">
                  <span class="material-symbols-outlined text-[18px]" :class="app.archived ? 'text-gray-400' : 'text-primary'">{{ app.companyIcon }}</span>
                  {{ app.company }}
                </span>
                <span
                  v-if="app.badge"
                  class="inline-flex items-center gap-0.5 text-xs font-medium px-1.5 py-0.5 rounded"
                  :class="app.badgeColor"
                >
                  <span v-if="app.badgeIcon" class="material-symbols-outlined text-[14px]">{{ app.badgeIcon }}</span>
                  {{ app.badge }}
                </span>
                <span class="flex items-center gap-0.5 text-gray-400">
                  <span class="material-symbols-outlined text-[16px]">location_on</span> {{ app.location }}
                </span>
              </div>
              <div class="flex flex-wrap items-center gap-x-6 gap-y-1 mt-2 pt-2 text-xs text-gray-400 border-t border-gray-100">
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[16px]">schedule</span>
                  投递时间：{{ app.applyTime }}
                </span>
                <span class="flex items-center gap-1 text-gray-500">
                  <span class="material-symbols-outlined text-[16px]">article</span>
                  使用简历：<span class="font-medium" :class="app.resumeHighlight ? 'text-primary underline underline-offset-2' : 'text-gray-900'">{{ app.resumeName }}</span>
                </span>
              </div>

              <div
                class="mt-4 p-3 rounded-lg flex items-start sm:items-center gap-3"
                :class="app.statusBannerColor"
              >
                <span
                  class="inline-flex items-center justify-center w-7 h-7 rounded-full shrink-0"
                  :class="app.statusIconBg"
                >
                  <span class="material-symbols-outlined text-[18px]" :class="app.statusIconColor">{{ app.statusIcon }}</span>
                </span>
                <div class="flex-1 min-w-0">
                  <div class="flex flex-wrap items-center gap-2">
                    <span class="text-sm font-bold" :class="app.statusTextColor">{{ app.statusText }}</span>
                    <span class="text-sm" :class="app.statusDescColor">{{ app.statusDesc }}</span>
                  </div>
                </div>
                <span v-if="app.statusExtra" class="hidden sm:inline-flex items-center gap-1 text-xs font-semibold shrink-0" :class="app.statusExtraColor">
                  {{ app.statusExtra }}
                </span>
              </div>
            </div>

            <div class="flex xl:flex-col items-center xl:items-end justify-end gap-2 shrink-0 pt-1 xl:pt-0">
              <button
                v-for="action in app.actions"
                :key="action.label"
                class="w-full sm:w-auto px-4 py-2.5 rounded-lg text-sm shadow-sm transition-all flex items-center justify-center gap-1"
                :class="action.color"
              >
                <span class="material-symbols-outlined text-[18px]" :class="action.iconColor || ''">{{ action.icon }}</span>
                {{ action.label }}
              </button>
              <button
                v-if="app.showViewJob"
                class="hidden xl:flex items-center gap-1 text-gray-400 hover:text-primary text-xs py-1 transition-colors"
              >
                <span>查看原招聘职位</span>
                <span class="material-symbols-outlined text-[14px]">open_in_new</span>
              </button>
            </div>
          </div>
        </article>
      </div>

      <div class="mt-8 bg-gradient-to-r from-gray-50 via-white to-gray-50 rounded-2xl p-5 shadow-sm">
        <div class="flex flex-col md:flex-row items-center justify-between gap-5">
          <div class="flex items-center gap-4">
            <div class="w-12 h-12 rounded-xl bg-primary text-white flex items-center justify-center shrink-0 shadow-sm">
              <span class="material-symbols-outlined text-[28px]">lightbulb</span>
            </div>
            <div>
              <h3 class="text-base text-gray-900 font-bold">高校就业指导中心 · 求职提速小贴士</h3>
              <p class="text-xs text-gray-500 mt-0.5">
                春招黄金期建议同时保持 8-15 家目标岗位在投。若投递后 5 个工作日未查看，建议主动使用"在线沟通"与HR建立连接。
              </p>
            </div>
          </div>
          <router-link
            to="/jobs"
            class="w-full md:w-auto px-5 py-2.5 rounded-lg bg-primary text-white text-sm font-semibold text-center hover:bg-blue-700 transition-all shadow-sm"
          >
            前往【职位搜索】发现更多机会
          </router-link>
        </div>
      </div>

      <div class="mt-8 flex flex-col sm:flex-row items-center justify-between gap-4 py-4">
        <div class="text-sm text-gray-500">
          共 <strong class="text-gray-900 font-semibold">{{ totalCount }}</strong> 条投递记录，每页显示 <strong class="text-gray-900 font-semibold">5</strong> 条，当前第 <strong class="text-gray-900 font-semibold">{{ currentPage }} / {{ totalPages }}</strong> 页
        </div>
        <nav class="flex items-center gap-1">
          <button
            class="px-3 py-2 rounded-lg bg-gray-100 text-gray-400 cursor-not-allowed flex items-center gap-1 text-sm"
            disabled
          >
            <span class="material-symbols-outlined text-[16px]">arrow_back</span>
            上一页
          </button>
          <button
            v-for="page in totalPages"
            :key="page"
            class="w-9 h-9 rounded-lg text-sm font-medium transition-colors flex items-center justify-center"
            :class="currentPage === page
              ? 'bg-primary text-white font-bold shadow-sm'
              : 'bg-gray-100 hover:bg-gray-200 text-gray-900'"
            @click="currentPage = page"
          >
            {{ page }}
          </button>
          <button class="px-3 py-2 rounded-lg bg-gray-100 hover:bg-gray-200 text-gray-900 flex items-center gap-1 text-sm transition-colors">
            下一页
            <span class="material-symbols-outlined text-[16px]">arrow_forward</span>
          </button>
        </nav>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const lastSyncTime = ref('今天 14:32')
const searchQuery = ref('')
const sortOption = ref('time-asc')
const activeTab = ref('all')
const currentPage = ref(1)
const totalPages = ref(3)

const metrics = ref([
  {
    label: '全部投递记录',
    icon: 'folder_shared',
    value: 12,
    unit: '份职位',
    highlight: '+2',
    highlightColor: 'text-secondary font-medium',
    subtext: '',
    valueColor: 'text-gray-900',
    barColor: 'bg-primary/20 group-hover:bg-primary',
  },
  {
    label: '待HR查阅',
    icon: 'hourglass_top',
    value: 3,
    unit: '份排队中',
    highlight: '',
    subtext: '平均响应 24-48 小时',
    valueColor: 'text-secondary',
    barColor: 'bg-secondary/30 group-hover:bg-secondary',
  },
  {
    label: '面试与沟通中',
    icon: 'videocam',
    value: 4,
    unit: '场推进',
    highlight: '',
    subtext: '近期有 1 场待参加',
    valueColor: 'text-amber-600',
    barColor: 'bg-amber-500 group-hover:bg-amber-600',
  },
  {
    label: '已获Offer / 录用',
    icon: 'verified',
    value: 1,
    unit: '份录用意向',
    highlight: '待三方网签',
    highlightColor: 'text-primary font-medium flex items-center gap-1',
    subtext: '',
    valueColor: 'text-primary',
    barColor: 'bg-primary group-hover:bg-primary',
  },
])

const statusTabs = ref([
  { key: 'all', label: '全部 (12)', hasAlert: false },
  { key: 'pending', label: '待查看 (3)', hasAlert: false },
  { key: 'reviewed', label: '已查看 (2)', hasAlert: false },
  { key: 'interview', label: '面试邀请 (4)', hasAlert: true },
  { key: 'offered', label: '已录用 (1)', hasAlert: false },
  { key: 'rejected', label: '不合适 (2)', hasAlert: false },
])

const applications = ref([
  {
    id: 1,
    title: '大模型算法工程师 (2025届校招)',
    tag: '算法研发专项',
    tagColor: 'bg-amber-100 text-amber-700',
    salary: '25k-40k · 16薪',
    company: '智维未来科技有限公司',
    companyIcon: 'corporate_fare',
    badge: '国高新技术企业',
    badgeColor: 'bg-blue-50 text-secondary font-medium text-xs',
    badgeIcon: 'verified',
    location: '北京 · 海淀区',
    applyTime: '2025-03-08 10:15',
    resumeName: '2025届计算机研发与算法专向简历.pdf',
    resumeHighlight: true,
    archived: false,
    statusBannerColor: 'bg-amber-50',
    statusIconBg: 'bg-amber-600 text-white',
    statusIconColor: '',
    statusIcon: 'event_available',
    statusText: '面试邀请已送达',
    statusTextColor: 'text-amber-700',
    statusDesc: 'HR已发送「线上技术二面」邀请，请于 03-12 15:30 准时进入腾讯会议房间',
    statusDescColor: 'text-gray-900',
    statusExtra: '房间号: 882-901-443',
    statusExtraColor: 'text-amber-700',
    showViewJob: true,
    actions: [
      { label: '查看投递与面试详情', icon: 'calendar_month', color: 'bg-blue-600 text-white hover:bg-primary' },
      { label: '在线沟通HR', icon: 'chat', iconColor: 'text-secondary', color: 'bg-gray-100 hover:bg-gray-200 text-gray-900' },
    ],
  },
  {
    id: 2,
    title: '全栈开发工程师 (管培生计划)',
    tag: '管培生计划',
    tagColor: 'bg-blue-50 text-blue-700',
    salary: '18k-28k · 15薪',
    company: '字节跃动互联网研发中心',
    companyIcon: 'apartment',
    badge: '独角兽平台',
    badgeColor: 'bg-gray-100 text-gray-500 font-medium text-xs',
    badgeIcon: '',
    location: '北京 · 朝阳区',
    applyTime: '2025-02-28 16:30',
    resumeName: '林晨_2025届全栈开发通用简历.pdf',
    resumeHighlight: false,
    archived: false,
    statusBannerColor: 'bg-blue-50',
    statusIconBg: 'bg-secondary text-white',
    statusIconColor: '',
    statusIcon: 'verified',
    statusText: '已录用 · 录用函已下发',
    statusTextColor: 'text-secondary',
    statusDesc: '恭喜！企业已通过校招终审并发放正式校招录用意向书 (Offer Letter)，待发起高校三方协议网签。',
    statusDescColor: 'text-gray-900',
    statusExtra: '签署有效期至 2025-03-20',
    statusExtraColor: 'bg-white px-2 py-1 rounded text-primary font-semibold',
    showViewJob: true,
    actions: [
      { label: '发起三方协议网签', icon: 'draw', color: 'bg-secondary text-white hover:opacity-90' },
      { label: '查看详情与Offer', icon: 'description', color: 'bg-gray-100 hover:bg-gray-200 text-gray-900' },
    ],
  },
  {
    id: 3,
    title: '电网自动化与新能源系统工程师',
    tag: '央企专项校招',
    tagColor: 'bg-blue-100 text-blue-800',
    salary: '22k-32k/月',
    company: '国家电网 · 全球能源互联网研究院',
    companyIcon: 'hub',
    badge: '重点科研院所',
    badgeColor: 'bg-blue-50 text-blue-700 font-medium text-xs',
    badgeIcon: '',
    location: '北京 · 昌平区',
    applyTime: '2025-03-05 09:20',
    resumeName: '林晨_嵌入式系统与智能电气方向.pdf',
    resumeHighlight: false,
    archived: false,
    statusBannerColor: 'bg-blue-50',
    statusIconBg: 'bg-primary text-white',
    statusIconColor: '',
    statusIcon: 'visibility',
    statusText: 'HR已查阅简历',
    statusTextColor: 'text-primary',
    statusDesc: '用人部门技术负责人正在进行综合技术背景评审，预计 3 个工作日内更新初面安排。',
    statusDescColor: 'text-gray-900',
    statusExtra: '查阅于 03-06 11:24',
    statusExtraColor: 'text-gray-500',
    showViewJob: true,
    actions: [
      { label: '催一下HR', icon: 'notifications_active', color: 'bg-gray-100 hover:bg-gray-200 text-gray-900' },
      { label: '查看投递详情', icon: 'fact_check', color: 'bg-gray-100 hover:bg-gray-200 text-gray-900' },
    ],
  },
  {
    id: 4,
    title: 'Azure 云原生开发实习生',
    tag: '春季日常实习',
    tagColor: 'bg-gray-100 text-gray-600',
    salary: '25k-35k/月',
    company: '微软中国 · 亚太研发集团',
    companyIcon: 'cloud_sync',
    badge: '外资研发中心',
    badgeColor: 'bg-gray-100 text-gray-500 font-medium text-xs',
    badgeIcon: '',
    location: '上海 · 徐汇区',
    applyTime: '2025-03-07 14:10',
    resumeName: '林晨_Cloud_Native_EN_CN.pdf',
    resumeHighlight: false,
    archived: false,
    statusBannerColor: 'bg-gray-50',
    statusIconBg: 'bg-gray-300 text-gray-900',
    statusIconColor: '',
    statusIcon: 'mark_email_read',
    statusText: '等待企业查阅',
    statusTextColor: 'text-gray-500',
    statusDesc: '简历已通过高校双选直通专线安全送达企业招聘系统，HR未进入处理流程前可支持自主撤回。',
    statusDescColor: 'text-gray-500',
    statusExtra: '送达用时 0.2s',
    statusExtraColor: 'text-gray-400',
    showViewJob: true,
    actions: [
      { label: '查看详情', icon: 'pageview', color: 'bg-gray-100 hover:bg-gray-200 text-gray-900' },
      { label: '撤回申请', icon: 'undo', color: 'bg-red-50 hover:bg-red-100 text-red-600' },
    ],
  },
  {
    id: 5,
    title: '量化策略研究员',
    tag: '校招已归档',
    tagColor: 'bg-gray-200 text-gray-400',
    salary: '40k-60k',
    company: '幻方量化投资中心',
    companyIcon: 'finance',
    badge: '',
    badgeColor: '',
    badgeIcon: '',
    location: '杭州 · 西湖区',
    applyTime: '2025-02-15 11:05',
    resumeName: '',
    resumeHighlight: false,
    archived: true,
    statusBannerColor: 'bg-gray-100',
    statusIconBg: 'bg-gray-200 text-gray-400',
    statusIconColor: '',
    statusIcon: 'folder_off',
    statusText: '暂不匹配 / 已归入人才库',
    statusTextColor: 'text-gray-400',
    statusDesc: '该岗位校招招聘HC已满，企业已将您的完整材料纳入长效青年人才储备库，有新职位时将优先推荐。',
    statusDescColor: 'text-gray-500',
    statusExtra: '',
    statusExtraColor: '',
    showViewJob: false,
    actions: [
      { label: '查看反馈', icon: 'rate_review', color: 'bg-gray-100 hover:bg-gray-200 text-gray-500' },
      { label: '移除记录', icon: 'delete_outline', color: 'text-gray-400 hover:text-red-500 hover:bg-red-50' },
    ],
  },
])

const totalCount = computed(() => applications.value.length)

const filteredApplications = computed(() => {
  let list = applications.value
  if (activeTab.value === 'pending') {
    list = list.filter((a) => a.statusText === '等待企业查阅')
  } else if (activeTab.value === 'reviewed') {
    list = list.filter((a) => a.statusText === 'HR已查阅简历')
  } else if (activeTab.value === 'interview') {
    list = list.filter((a) => a.statusText.includes('面试'))
  } else if (activeTab.value === 'offered') {
    list = list.filter((a) => a.statusText.includes('已录用'))
  } else if (activeTab.value === 'rejected') {
    list = list.filter((a) => a.archived)
  }
  if (searchQuery.value.trim()) {
    const q = searchQuery.value.trim().toLowerCase()
    list = list.filter(
      (a) =>
        a.title.toLowerCase().includes(q) ||
        a.company.toLowerCase().includes(q)
    )
  }
  return list
})

function refreshData() {
  lastSyncTime.value = new Date().toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
}
</script>
