<template>
  <div class="min-h-screen bg-background">
    <div class="max-w-7xl mx-auto px-6 py-8">
      <!-- Header -->
      <div class="flex items-start justify-between mb-8">
        <div>
          <div class="flex items-center gap-2 mb-2">
            <span class="material-symbols-outlined text-primary text-lg">auto_awesome</span>
            <span class="text-xs text-on-surface-variant font-medium">智谱GLM大模型驱动</span>
            <span class="px-2 py-0.5 bg-primary/10 text-primary text-[10px] font-semibold rounded-full">AI</span>
          </div>
          <h1 class="text-2xl font-bold text-on-surface">AI智能推荐职位</h1>
          <p class="text-sm text-on-surface-variant mt-1">基于您的求职意向、浏览行为和职位特征，为您个性化匹配最优职位</p>
        </div>
        <button @click="handleRefresh" :disabled="refreshing"
          class="flex items-center gap-2 px-4 py-2.5 bg-primary text-on-primary text-sm font-semibold rounded-xl hover:bg-primary/90 transition-colors disabled:opacity-50">
          <span class="material-symbols-outlined text-[18px]" :class="refreshing ? 'animate-spin' : ''">refresh</span>
          {{ refreshing ? '刷新中...' : '刷新推荐' }}
        </button>
      </div>

      <!-- Filter Bar -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-4 mb-6">
        <div class="flex gap-3 items-center flex-wrap">
          <div class="relative">
            <select v-model="filterCity" class="h-10 pl-9 pr-8 bg-surface-container-low text-on-surface text-sm rounded-xl appearance-none cursor-pointer focus:outline-none border border-surface-container-high">
              <option value="">全部城市</option>
              <option v-for="c in cities" :key="c" :value="c">{{ c }}</option>
            </select>
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">location_on</span>
            <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">arrow_drop_down</span>
          </div>
          <div class="relative">
            <select v-model="filterIndustry" class="h-10 pl-9 pr-8 bg-surface-container-low text-on-surface text-sm rounded-xl appearance-none cursor-pointer focus:outline-none border border-surface-container-high">
              <option value="">全部行业</option>
              <option v-for="ind in industries" :key="ind" :value="ind">{{ ind }}</option>
            </select>
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">apartment</span>
            <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">arrow_drop_down</span>
          </div>
          <div class="relative">
            <select v-model="filterJobType" class="h-10 pl-9 pr-8 bg-surface-container-low text-on-surface text-sm rounded-xl appearance-none cursor-pointer focus:outline-none border border-surface-container-high">
              <option value="">全部类型</option>
              <option value="全职">全职</option>
              <option value="实习">实习</option>
              <option value="兼职">兼职</option>
            </select>
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">work</span>
            <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">arrow_drop_down</span>
          </div>
        </div>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div v-for="i in 6" :key="i" class="bg-surface-container-lowest rounded-xl border border-surface-container-high p-5 animate-pulse">
          <div class="flex items-center gap-3 mb-3">
            <div class="w-10 h-10 rounded-lg bg-surface-container-low"></div>
            <div class="flex-1">
              <div class="h-4 bg-surface-container-low rounded w-3/4 mb-1"></div>
              <div class="h-3 bg-surface-container-low rounded w-1/2"></div>
            </div>
          </div>
          <div class="h-6 bg-surface-container-low rounded w-1/3 mb-3"></div>
          <div class="h-3 bg-surface-container-low rounded w-full mb-2"></div>
          <div class="h-3 bg-surface-container-low rounded w-2/3"></div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-else-if="jobs.length === 0" class="text-center py-20">
        <span class="material-symbols-outlined text-6xl text-on-surface-variant/40 mb-4 block">search_off</span>
        <h3 class="text-lg font-semibold text-on-surface mb-2">暂无符合条件的职位</h3>
        <p class="text-sm text-on-surface-variant mb-6">当前筛选条件下没有推荐职位，您可以尝试调整筛选条件或重新获取推荐</p>
        <div class="flex justify-center gap-3">
          <button @click="clearFilters" class="px-6 py-2.5 border border-primary text-primary text-sm font-semibold rounded-xl hover:bg-primary/5 transition-colors">
            清除筛选
          </button>
          <button @click="handleRefresh" class="px-6 py-2.5 bg-primary text-on-primary text-sm font-semibold rounded-xl hover:bg-primary/90 transition-colors">
            按当前条件重新推荐
          </button>
        </div>
      </div>

      <!-- Job Cards -->
      <div v-else class="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
        <router-link v-for="job in jobs" :key="job.jobId" :to="`/jobs/${job.jobId}`"
          class="bg-surface-container-lowest rounded-xl border border-surface-container-high p-5 hover:shadow-lg hover:border-primary/30 transition-all group flex flex-col">
          <!-- AI Score -->
          <div class="flex items-center justify-between mb-3">
            <span class="inline-flex items-center gap-1 px-2.5 py-1 bg-primary/10 text-primary text-[11px] font-semibold rounded-full">
              <span class="material-symbols-outlined text-[14px]">auto_awesome</span>
              匹配度 {{ job.recommendScore }}%
            </span>
            <span class="w-10 h-10 rounded-lg flex items-center justify-center text-white font-bold text-sm shrink-0" :style="{ background: getLogoColor(job.jobId) }">
              {{ job.companyName ? job.companyName.charAt(0) : '企' }}
            </span>
          </div>

          <!-- Title & Company -->
          <h3 class="text-base font-bold text-on-surface group-hover:text-primary transition-colors mb-1">{{ job.title }}</h3>
          <p class="text-sm text-on-surface-variant mb-3">{{ job.companyName || '未知企业' }}</p>

          <!-- Salary -->
          <div class="mb-3">
            <span class="text-xl font-bold text-primary">{{ formatSalary(job.salaryMin, job.salaryMax) }}</span>
          </div>

          <!-- Tags -->
          <div class="flex flex-wrap gap-1.5 mb-3">
            <span v-if="job.industry" class="px-2 py-0.5 bg-primary/10 text-primary text-[11px] rounded font-medium">{{ job.industry }}</span>
            <span v-if="job.jobType" class="px-2 py-0.5 bg-surface-container-low text-on-surface-variant text-[11px] rounded">{{ {1:'全职',2:'实习',3:'兼职'}[job.jobType] }}</span>
            <span v-if="job.city" class="px-2 py-0.5 bg-surface-container-low text-on-surface-variant text-[11px] rounded">{{ job.city }}</span>
          </div>

          <!-- AI Reason -->
          <div class="flex-1 mb-4">
            <div class="bg-primary/5 rounded-lg p-3">
              <p class="text-xs text-primary/80 italic leading-relaxed">
                "{{ job.recommendReason }}"
              </p>
            </div>
          </div>

          <!-- Action -->
          <div class="flex items-center justify-between pt-3 border-t border-surface-container-high">
            <span class="text-[11px] text-on-surface-variant flex items-center gap-1">
              <span class="material-symbols-outlined text-xs">schedule</span>
              AI智能推荐
            </span>
            <button @click.prevent="applyJob(job)" class="px-4 py-1.5 bg-primary text-on-primary text-xs font-semibold rounded-lg hover:bg-primary/90 transition-colors">
              投递简历
            </button>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getRecommendJobs, refreshRecommend } from '../api/recommend'
import { getJobCategories } from '../api/job'
import { formatSalary } from '../utils/format'

const router = useRouter()
const store = useAppStore()

const jobs = ref([])
const originalJobs = ref([])
const isFiltering = ref(false)
const loading = ref(true)
const refreshing = ref(false)
const filterCity = ref('')
const filterIndustry = ref('')
const filterJobType = ref('')

const cities = ['北京', '上海', '广州', '深圳', '杭州', '南京', '成都', '武汉', '西安', '苏州']
const industries = ref([])

const LOGO_COLORS = ['#1a56db', '#006591', '#0a8754', '#0078d4', '#e74c3c', '#8e44ad', '#e67e22', '#1abc9c', '#2c3e50', '#d35400']

function getLogoColor(id) {
  return LOGO_COLORS[(id || 0) % LOGO_COLORS.length]
}

function requireAuth() {
  if (!store.isLoggedIn) {
    window.dispatchEvent(new CustomEvent('open-login-modal'))
  }
}

function applyJob(job) {
  if (!store.isLoggedIn) {
    requireAuth()
    return
  }
  router.push(`/jobs/${job.jobId}`)
}

async function loadJobs() {
  loading.value = true
  try {
    const res = await getRecommendJobs()
    console.log('推荐返回数据:', res)
    if (Array.isArray(res) && res.length > 0) {
      console.log('第一条职位完整数据:', JSON.stringify(res[0]))
    }
    if (Array.isArray(res)) {
      originalJobs.value = res
      filterJobs()
    } else {
      originalJobs.value = []
      jobs.value = []
    }
  } catch (e) {
    console.error('获取AI推荐失败:', e)
    originalJobs.value = []
    jobs.value = []
  } finally {
    loading.value = false
  }
}

async function handleRefresh() {
  refreshing.value = true
  try {
    const data = {}
    if (filterCity.value) data.city = filterCity.value
    if (filterIndustry.value) data.industry = filterIndustry.value
    if (filterJobType.value) data.jobType = filterJobType.value

    const res = await refreshRecommend(data)
    if (Array.isArray(res)) {
      originalJobs.value = res
      filterJobs()
    }
  } catch (e) {
    console.error('刷新推荐失败:', e)
  } finally {
    refreshing.value = false
  }
}

async function loadIndustries() {
  try {
    const res = await getJobCategories()
    if (res && res.industries) {
      industries.value = res.industries
    }
  } catch (e) {
    console.error('获取行业类别失败:', e)
  }
}

function filterJobs() {
  let filtered = [...originalJobs.value]
  if (filterCity.value) {
    filtered = filtered.filter(j => j.city === filterCity.value)
  }
  if (filterIndustry.value) {
    filtered = filtered.filter(j => j.industry === filterIndustry.value)
  }
  if (filterJobType.value) {
    const typeMap = { '全职': 1, '实习': 2, '兼职': 3 }
    const targetJobType = typeMap[filterJobType.value]
    filtered = filtered.filter(j => j.jobType === targetJobType)
  }
  jobs.value = filtered
}

function clearFilters() {
  filterCity.value = ''
  filterIndustry.value = ''
  filterJobType.value = ''
}

watch([filterCity, filterIndustry, filterJobType], () => {
  filterJobs()
})

onMounted(() => {
  loadJobs()
  loadIndustries()
})
</script>
