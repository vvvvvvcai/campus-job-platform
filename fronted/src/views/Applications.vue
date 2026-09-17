<template>
  <div class="min-h-screen bg-background">
    <div class="max-w-6xl mx-auto px-6 py-8">
      <!-- Header -->
      <div class="flex items-center justify-between mb-8">
        <div>
          <div class="flex items-center gap-3 mb-2">
            <span class="material-symbols-outlined text-primary text-3xl">mail</span>
            <h1 class="text-2xl lg:text-3xl font-bold text-on-surface">我的投递记录</h1>
          </div>
          <p class="text-sm text-on-surface-variant">实时追踪简历送达、企业HR查阅、面试邀请及录用签约全流程，助力高效求职把控。</p>
        </div>
        <div class="hidden md:flex items-center gap-2 text-xs text-on-surface-variant bg-surface-container-lowest px-4 py-2 rounded-xl border border-surface-container-high">
          <span class="material-symbols-outlined text-sm">sync</span>
          同步更新于 今天 14:32
        </div>
      </div>

      <!-- Stat Cards -->
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        <div v-for="stat in statCards" :key="stat.label"
          class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5 relative overflow-hidden"
          :class="stat.borderClass">
          <div class="flex items-center justify-between mb-2">
            <span class="text-xs text-on-surface-variant font-medium">{{ stat.label }}</span>
            <span :class="['material-symbols-outlined text-xl', stat.iconColor]">{{ stat.icon }}</span>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-3xl font-bold" :class="stat.valueColor">{{ stat.value }}</span>
            <span class="text-xs text-on-surface-variant">{{ stat.unit }}</span>
          </div>
          <p class="text-xs mt-1.5" :class="stat.subColor">{{ stat.sub }}</p>
          <div v-if="stat.highlight" class="mt-2">
            <span class="text-xs font-medium flex items-center gap-1" :class="stat.highlightColor">
              <span class="w-1.5 h-1.5 rounded-full" :class="stat.dotColor"></span>
              {{ stat.highlight }}
            </span>
          </div>
        </div>
      </div>

      <!-- Filter Tabs + Search -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-4 mb-6">
        <div class="flex flex-col md:flex-row md:items-center gap-4">
          <div class="flex items-center gap-1 flex-1 overflow-x-auto">
            <button v-for="tab in filterTabs" :key="tab.key" @click="activeFilter = tab.key"
              :class="['px-4 py-2 rounded-xl text-sm font-medium whitespace-nowrap transition-all relative',
                activeFilter === tab.key ? 'bg-primary text-on-primary shadow-sm' : 'text-on-surface-variant hover:bg-surface-container-low']">
              {{ tab.label }} ({{ tab.count }})
              <span v-if="tab.dot" class="absolute top-1.5 right-1.5 w-2 h-2 rounded-full bg-red-500"></span>
            </button>
          </div>
          <div class="flex items-center gap-3">
            <div class="relative">
              <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">search</span>
              <input v-model="searchQuery" type="text" placeholder="输入职位或公司名称检索..."
                class="w-56 h-10 pl-10 pr-4 bg-surface-container-low text-sm rounded-xl border border-surface-container-high focus:border-primary focus:ring-2 focus:ring-primary/10 outline-none text-on-surface" />
            </div>
            <select class="h-10 px-4 bg-surface-container-low text-sm rounded-xl border border-surface-container-high appearance-none cursor-pointer outline-none pr-8 text-on-surface-variant">
              <option>投递时间：最近到最早</option>
              <option>投递时间：最早到最近</option>
            </select>
          </div>
        </div>
      </div>

      <!-- Application Cards -->
      <div class="space-y-4 mb-6">
        <div v-for="app in filteredApplications" :key="app.id"
          class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6 hover:shadow-md transition-shadow"
          :class="{ 'opacity-60': app.status === 'rejected' }">
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center gap-3 flex-wrap">
                <h3 class="font-bold text-on-surface text-base">{{ app.position }}</h3>
                <span v-for="tag in app.tags" :key="tag.label"
                  class="px-2.5 py-0.5 rounded-full text-[11px] font-semibold"
                  :class="tag.class">{{ tag.label }}</span>
                <span class="text-base font-bold text-primary ml-2">{{ app.salary }}</span>
              </div>
              <div class="flex items-center gap-3 mt-2 text-sm text-on-surface-variant">
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[14px]">apartment</span> {{ app.company }}
                </span>
                <span v-if="app.companyTag" class="px-2 py-0.5 bg-primary/10 text-primary text-[11px] rounded font-medium">{{ app.companyTag }}</span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[14px]">location_on</span> {{ app.location }}
                </span>
              </div>
              <div class="flex items-center gap-4 mt-3 text-xs text-on-surface-variant">
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
            <div class="flex flex-col items-end gap-2 shrink-0 ml-4">
              <div v-if="app.status !== 'rejected'" class="flex items-center gap-1">
                <span class="px-4 py-2 bg-primary text-on-primary rounded-xl text-sm font-semibold shadow-sm">
                  {{ progressTabs.find(s => s.key === app.statusRaw)?.label || '待查看' }}
                </span>
              </div>
              <button v-if="app.status !== 'rejected'" @click="router.push({ name: 'JobDetail', params: { id: app.jobId } })"
                class="text-xs text-on-surface-variant hover:text-primary flex items-center gap-1 transition-colors">
                查看原招聘职位
                <span class="material-symbols-outlined text-[14px]">open_in_new</span>
              </button>
              <button v-if="app.status === 'rejected'" @click="removeApplication(app.id)"
                class="text-xs text-on-surface-variant hover:text-red-500 flex items-center gap-1 transition-colors">
                <span class="material-symbols-outlined text-[14px]">delete_outline</span>
                移除记录
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div class="flex items-center justify-between text-sm text-on-surface-variant">
        <span>共 {{ totalApplications }} 条投递记录，每页显示 5 条，当前第 {{ currentPage }} / {{ totalPages }} 页</span>
        <div class="flex items-center gap-2">
          <button @click="currentPage = Math.max(1, currentPage - 1)" :disabled="currentPage === 1"
            class="px-4 py-2 rounded-lg border border-surface-container-high hover:bg-surface-container-low transition-colors disabled:opacity-40 disabled:cursor-not-allowed flex items-center gap-1 text-on-surface-variant">
            <span class="material-symbols-outlined text-[16px]">chevron_left</span> 上一页
          </button>
          <button v-for="p in totalPages" :key="p" @click="currentPage = p"
            :class="['w-9 h-9 rounded-lg text-sm font-medium transition-all',
              p === currentPage ? 'bg-primary text-on-primary shadow-sm' : 'border border-surface-container-high hover:bg-surface-container-low text-on-surface-variant']">
            {{ p }}
          </button>
          <button @click="currentPage = Math.min(totalPages, currentPage + 1)" :disabled="currentPage === totalPages"
            class="px-4 py-2 rounded-lg border border-surface-container-high hover:bg-surface-container-low transition-colors disabled:opacity-40 disabled:cursor-not-allowed flex items-center gap-1 text-on-surface-variant">
            下一页 <span class="material-symbols-outlined text-[16px]">chevron_right</span>
          </button>
        </div>
      </div>

      <!-- 求职推进轨迹 -->
      <div class="mt-10 bg-surface-container-lowest rounded-2xl border border-surface-container-high p-8">
        <div class="flex items-start justify-between mb-8">
          <div>
            <h2 class="text-xl font-bold text-on-surface mb-1">求职推进轨迹</h2>
            <p class="text-sm text-on-surface-variant">系统全程存证保障，关键节点实时推送通知并同步高校就业系统</p>
          </div>
          <div class="hidden md:flex items-center gap-2 text-sm text-primary bg-primary/10 px-4 py-2 rounded-xl font-medium">
            <span class="w-2 h-2 rounded-full bg-primary"></span>
            流程时效正常（平均流转率超 92% 同类岗位）
          </div>
        </div>

        <div class="relative">
          <div class="absolute top-6 left-0 right-0 h-0.5 bg-surface-container-high"></div>
          <div class="absolute top-6 left-0 h-0.5 bg-primary" :style="{ width: '58%' }"></div>

          <div class="grid grid-cols-2 md:grid-cols-6 gap-4">
            <div v-for="(step, index) in progressSteps" :key="index" class="relative flex flex-col items-center text-center">
              <div :class="['relative z-10 w-12 h-12 rounded-full flex items-center justify-center border-4 border-surface-container-lowest shadow-sm mb-4',
                step.done || step.current ? 'bg-primary' : 'bg-surface-container-high']">
                <span :class="['material-symbols-outlined text-xl', step.done || step.current ? 'text-on-primary' : 'text-on-surface-variant']">{{ step.icon }}</span>
              </div>
              <span :class="['px-3 py-1 rounded-full text-[11px] font-semibold mb-3',
                step.done || step.current ? 'bg-primary/10 text-primary' : 'bg-surface-container-high text-on-surface-variant']">
                {{ step.badge }}
              </span>
              <h4 :class="['font-bold text-sm mb-1',
                step.done || step.current ? 'text-on-surface' : 'text-on-surface-variant/50']">{{ step.title }}</h4>
              <p :class="['text-[11px] mb-2',
                step.done || step.current ? 'text-on-surface-variant' : 'text-on-surface-variant/50']">{{ step.date }}</p>
              <p :class="['text-[11px] leading-relaxed',
                step.done || step.current ? 'text-on-surface-variant' : 'text-on-surface-variant/50']">{{ step.desc }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getApplicationList } from '../api/application'

const router = useRouter()
const store = useAppStore()
const activeFilter = ref('all')
const searchQuery = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalApplications = ref(0)
const loading = ref(false)

const filterTabs = [
  { key: 'all', label: '全部', count: 0 },
  { key: '0', label: '待查看', count: 0 },
  { key: '1', label: '已查看', count: 0 },
  { key: '2', label: '面试邀请', count: 0, dot: true },
  { key: '4', label: '已录用', count: 0 },
  { key: '3', label: '不合适', count: 0 }
]

const progressTabs = [
  { key: 0, label: '待查看' },
  { key: 1, label: '已查看' },
  { key: 2, label: '面试邀请' },
  { key: 4, label: '已录用' }
]

const statCards = [
  { label: '全部投递记录', value: 0, unit: '份职位', sub: '', icon: 'description', iconColor: 'text-primary', valueColor: 'text-on-surface', subColor: 'text-on-surface-variant', borderClass: 'border-b-2 border-b-primary' },
  { label: '待HR查阅', value: 0, unit: '份排队中', sub: '平均响应 24-48 小时', icon: 'schedule', iconColor: 'text-amber-500', valueColor: 'text-amber-600', subColor: 'text-on-surface-variant', borderClass: 'border-b-2 border-b-amber-400' },
  { label: '面试与沟通中', value: 0, unit: '场推进', sub: '', icon: 'chat_bubble', iconColor: 'text-emerald-500', valueColor: 'text-emerald-600', subColor: 'text-on-surface-variant', borderClass: 'border-b-2 border-b-emerald-400' },
  { label: '已获Offer / 录用', value: 0, unit: '份录用意向', sub: '', icon: 'check_circle', iconColor: 'text-blue-500', valueColor: 'text-blue-600', subColor: 'text-on-surface-variant', borderClass: 'border-b-2 border-b-primary' }
]

const applications = ref([])

const filteredApplications = computed(() => {
  if (activeFilter.value === 'all') return applications.value
  return applications.value.filter(a => String(a.statusRaw) === activeFilter.value)
})

async function fetchApplications() {
  if (!store.isLoggedIn) return
  loading.value = true
  try {
    const res = await getApplicationList({ page: currentPage.value, size: 10 })
    const records = (res && res.records) || []
    applications.value = records.map(a => ({
      id: a.id,
      jobId: a.jobId,
      position: a.jobName || '未知职位',
      company: a.companyName || '未知企业',
      location: '',
      salary: '',
      appliedAt: a.createTime ? a.createTime.substring(0, 10) : '',
      resume: a.resumeTitle || '在线简历',
      tags: [],
      status: ['pending', 'viewed', 'interview', 'rejected', 'offer'][a.status] || 'pending',
      statusRaw: a.status
    }))
    totalApplications.value = (res && res.total) || 0
    totalPages.value = (res && res.pages) || 1
    // 同步统计卡与筛选 Tab 计数（基于已加载数据）
    statCards[0].value = totalApplications.value
    statCards[1].value = records.filter(a => a.status === 0).length
    statCards[2].value = records.filter(a => a.status === 2).length
    statCards[3].value = records.filter(a => a.status === 4).length
    filterTabs.forEach(tab => {
      tab.count = tab.key === 'all' ? totalApplications.value : records.filter(a => String(a.status) === tab.key).length
    })
  } catch (e) {
    console.error('获取投递列表失败:', e)
  } finally {
    loading.value = false
  }
}

onMounted(() => fetchApplications())

function removeApplication(id) {
  applications.value = applications.value.filter(a => a.id !== id)
}

const progressSteps = [
  { title: '投递成功', date: '', desc: '简历已投递，等待HR查阅。', badge: '已完成', done: true, icon: 'check' },
  { title: 'HR 初筛', date: '', desc: 'HR查阅简历并评估匹配度。', badge: '进行中', current: true, icon: 'pending' },
  { title: '技术面试', date: '', desc: '技术能力评估。', badge: '待解锁', done: false, icon: 'hourglass_empty' },
  { title: '综合面谈', date: '', desc: '文化契合度考察。', badge: '待解锁', done: false, icon: 'hourglass_empty' },
  { title: 'Offer 与网签', date: '', desc: '生成录用函并办理网签。', badge: '最终环节', done: false, icon: 'school' }
]
</script>
