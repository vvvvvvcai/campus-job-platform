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
          <button @click="handleSearch" class="h-12 px-5 bg-primary text-on-primary font-semibold text-sm rounded-xl hover:bg-primary/90 transition-colors flex items-center gap-2 shrink-0">
            <span class="material-symbols-outlined text-[18px]">search</span>
            立即搜索
          </button>
          <button @click="resetFilters" class="h-12 px-4 text-on-surface-variant hover:text-on-surface text-sm font-medium rounded-xl hover:bg-surface-container-low transition-colors flex items-center gap-1 shrink-0">
            <span class="material-symbols-outlined text-[18px]">refresh</span>
            重置
          </button>
        </div>

        <!-- City Row -->
        <div class="flex items-center gap-2 mt-4 flex-wrap">
          <span class="text-xs text-on-surface-variant font-medium shrink-0">工作城市：</span>
          <button v-for="city in cities" :key="city" @click="selectedCity = city; handleSearch()"
            class="px-3 py-1 text-xs rounded-full transition-colors"
            :class="selectedCity === city ? 'bg-primary text-on-primary font-semibold' : 'text-on-surface-variant hover:text-on-surface hover:bg-surface-container-low'">
            {{ city }}
          </button>
        </div>
      </div>
    </div>

    <!-- Filters -->
    <div class="max-w-7xl mx-auto px-6 py-4">
      <div class="grid grid-cols-3 gap-3">
        <div class="relative">
          <label class="block text-[11px] text-on-surface-variant mb-1 font-medium">行业类别</label>
          <select v-model="selectedIndustry" @change="handleSearch()" class="w-full h-10 pl-3 pr-8 bg-surface-container-lowest text-on-surface text-xs rounded-lg appearance-none cursor-pointer border border-surface-container-high focus:outline-none focus:border-primary">
            <option v-for="opt in industryOptions" :key="opt" :value="opt">{{ opt }}</option>
          </select>
          <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 mt-1 text-on-surface-variant text-[16px]">arrow_drop_down</span>
        </div>
        <div class="relative">
          <label class="block text-[11px] text-on-surface-variant mb-1 font-medium">工作类型</label>
          <select v-model="selectedJobType" @change="handleSearch()" class="w-full h-10 pl-3 pr-8 bg-surface-container-lowest text-on-surface text-xs rounded-lg appearance-none cursor-pointer border border-surface-container-high focus:outline-none focus:border-primary">
            <option v-for="opt in jobTypeOptions" :key="opt" :value="opt">{{ opt }}</option>
          </select>
          <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 mt-1 text-on-surface-variant text-[16px]">arrow_drop_down</span>
        </div>
        <div class="flex items-end">
          <button @click="resetFilters" class="w-full h-10 px-4 text-xs font-medium text-on-surface-variant bg-surface-container-low hover:bg-surface-container rounded-lg transition-colors">
            重置全部筛选
          </button>
        </div>
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
              <span class="text-sm text-on-surface-variant">搜索结果 共为您筛选到 <span class="font-bold text-on-surface">{{ totalJobs }}</span> 个职位</span>
            </div>
            <div class="flex items-center gap-3">
              <div class="flex bg-surface-container-low rounded-lg p-1">
                <button v-for="tab in sortTabs" :key="tab" @click="activeSort = tab; handleSearch()"
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
              @click="$router.push(`/jobs/${job.id}`)"
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

                  <!-- Description -->
                  <p v-if="job.desc" class="text-xs text-on-surface-variant leading-relaxed mt-2 line-clamp-2">{{ job.desc }}</p>

                  <!-- Bottom Row -->
                  <div class="flex items-center justify-between mt-3 pt-3 border-t border-surface-container-high">
                    <span class="text-[11px] text-on-surface-variant">{{ job.meta }}</span>
                    <div class="flex items-center gap-2">
                      <button @click.stop="toggleFavorite(job)" :class="['w-8 h-8 rounded-lg flex items-center justify-center transition-colors', job.isFav ? 'text-red-500 hover:bg-red-50' : 'text-on-surface-variant hover:text-primary hover:bg-primary/5']">
                        <span class="material-symbols-outlined text-[18px]">{{ job.isFav ? 'favorite' : 'favorite_border' }}</span>
                      </button>
                      <button @click.stop="chatUnavailable" class="h-8 px-3 rounded-lg text-xs font-medium border border-surface-container-high text-on-surface-variant hover:border-primary hover:text-primary transition-colors">在线沟通</button>
                      <button @click.stop="applyJob(job)" class="h-8 px-4 rounded-lg text-xs font-semibold bg-primary text-on-primary hover:bg-primary/90 transition-colors">立即投递</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Pagination -->
          <div v-if="totalPages > 1" class="flex items-center justify-between mt-8">
            <span class="text-xs text-on-surface-variant">第 {{ currentPage }}/{{ totalPages }} 页，共 {{ totalJobs }} 个职位</span>
            <div class="flex items-center gap-1.5">
              <button @click="goToPage(currentPage - 1)" :disabled="currentPage <= 1" class="w-8 h-8 rounded-lg border border-surface-container-high flex items-center justify-center text-on-surface-variant hover:border-primary hover:text-primary disabled:opacity-50"><span class="material-symbols-outlined text-[16px]">chevron_left</span></button>
              <button v-for="p in Math.min(totalPages, 5)" :key="p" @click="goToPage(p)" class="w-8 h-8 rounded-lg flex items-center justify-center text-xs font-medium transition-colors"
                :class="p === currentPage ? 'bg-primary text-on-primary' : 'border border-surface-container-high text-on-surface-variant hover:border-primary hover:text-primary'">{{ p }}</button>
              <button @click="goToPage(currentPage + 1)" :disabled="currentPage >= totalPages" class="w-8 h-8 rounded-lg border border-surface-container-high flex items-center justify-center text-on-surface-variant hover:border-primary hover:text-primary disabled:opacity-50"><span class="material-symbols-outlined text-[16px]">chevron_right</span></button>
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
              <h3 class="text-lg font-bold mb-2">完善简历 · 提高匹配</h3>
              <p class="text-xs text-on-primary/80 leading-relaxed mb-4">完善在线简历中的教育背景、技能特长与项目经历，让 HR 更快发现你，提升面试邀约率。</p>
              <router-link to="/resume/manage" class="block bg-white/10 backdrop-blur-sm rounded-xl p-4 text-center border border-white/15 hover:bg-white/15 transition-colors">
                <span class="material-symbols-outlined text-3xl text-on-primary/80 mb-2 block">description</span>
                <p class="text-xs text-on-primary font-semibold">前往「我的简历档案库」</p>
                <p class="text-[10px] text-on-primary/60 mt-1">支持创建最多 5 份针对性简历</p>
              </router-link>
              <div class="flex items-center justify-between mt-3">
                <span class="text-[11px] text-on-primary/70">简历完整度越高越容易被 HR 关注</span>
                <router-link to="/resume/editor" class="text-[11px] text-on-primary hover:underline">去完善简历</router-link>
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
              <div v-for="(item, i) in hotJobs" :key="i" @click="item.jobId && $router.push(`/jobs/${item.jobId}`)"
                class="flex items-center gap-3 py-1.5 cursor-pointer hover:bg-surface-container-low -mx-2 px-2 rounded-lg transition-colors">
                <span class="w-5 h-5 rounded flex items-center justify-center text-[10px] font-bold shrink-0"
                  :class="i < 3 ? 'bg-primary text-on-primary' : 'bg-surface-container-low text-on-surface-variant'">{{ i + 1 }}</span>
                <div class="flex-1 min-w-0">
                  <p class="text-xs font-medium text-on-surface truncate">{{ item.title }}</p>
                  <p class="text-[10px] text-on-surface-variant">{{ item.company }} · {{ item.city }}</p>
                </div>
                <div class="text-right shrink-0">
                  <span class="text-xs font-bold" :class="i < 3 ? 'text-primary' : 'text-on-surface-variant'">{{ item.applicants }}人投递</span>
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

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { searchJobs, getJobCategories } from '../api/job'
import { addFavorite, removeFavorite, getFavoriteList } from '../api/favorite'
import { formatSalary } from '../utils/format'

const route = useRoute()
const router = useRouter()
const store = useAppStore()
const searchQuery = ref('')
const selectedCity = ref('全国')
const selectedIndustry = ref('不限')
const selectedJobType = ref('不限')
const activeSort = ref('综合排序')
const favoriteIds = ref(new Set())
const toast = ref('')
const sortMap = { '综合排序': '', '最新发布': 'latest', '薪资最高': 'salary', '投递响应最快': 'response' }
const currentPage = ref(1)
const totalPages = ref(1)
const totalJobs = ref(0)
const loading = ref(false)

function requireAuth() {
  if (!store.isLoggedIn) {
    window.dispatchEvent(new CustomEvent('open-login-modal'))
  }
}

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

function applyJob(job) {
  if (!store.isLoggedIn) {
    requireAuth()
    return
  }
  router.push(`/jobs/${job.id}`)
}

function chatUnavailable() {
  showToast('在线沟通功能暂未开放')
}

async function toggleFavorite(job) {
  if (!store.isLoggedIn) {
    requireAuth()
    return
  }
  try {
    if (job.isFav) {
      await removeFavorite(job.id)
      job.isFav = false
      favoriteIds.value.delete(job.id)
      showToast('已取消收藏')
    } else {
      await addFavorite(job.id)
      job.isFav = true
      favoriteIds.value.add(job.id)
      showToast('收藏成功')
    }
  } catch (e) {
    showToast(e.message || '收藏操作失败')
  }
}

async function loadFavorites() {
  if (!store.isLoggedIn) return
  try {
    const res = await getFavoriteList()
    if (Array.isArray(res)) {
      favoriteIds.value = new Set(res.map(f => f.id))
      jobs.value.forEach(j => { j.isFav = favoriteIds.value.has(j.id) })
    }
  } catch (e) {
    console.error('获取收藏列表失败:', e)
  }
}

async function loadHotJobs() {
  try {
    const res = await searchJobs({ sortBy: 'response', pageNum: 1, pageSize: 5 })
    if (res && res.records) {
      hotJobs.value = res.records.map(j => ({
        jobId: j.id,
        title: j.title,
        company: j.companyName || '未知企业',
        city: j.city || '',
        applicants: j.applyCount || 0
      }))
    }
  } catch (e) {
    console.error('获取热度榜失败:', e)
  }
}

const ALL_CITIES = ['全国', '北京', '上海', '广州', '深圳', '杭州', '南京', '成都', '武汉', '西安', '苏州', '天津', '重庆', '长沙', '郑州', '青岛', '大连', '宁波', '厦门', '合肥', '佛山', '东莞', '无锡', '昆明', '福州', '济南', '哈尔滨', '沈阳', '长春', '贵阳', '南宁', '太原', '石家庄', '兰州', '海口', '银川', '西宁', '拉萨', '呼和浩特', '乌鲁木齐', '台北', '香港', '澳门']
const cities = ALL_CITIES

const industryOptions = ref(['不限'])
const jobTypeOptions = ['不限', '全职', '实习', '兼职']

const sortTabs = ['综合排序', '最新发布', '薪资最高', '投递响应最快']
const jobs = ref([])
const hotJobs = ref([])

const LOGO_COLORS = ['#1a56db', '#006591', '#0a8754', '#0078d4', '#e74c3c', '#8e44ad', '#e67e22', '#1abc9c', '#2c3e50', '#d35400', '#27ae60', '#c0392b']

function handleSearch() {
  currentPage.value = 1
  fetchJobs()
}

function resetFilters() {
  searchQuery.value = ''
  selectedCity.value = '全国'
  selectedIndustry.value = '不限'
  selectedJobType.value = '不限'
  currentPage.value = 1
  fetchJobs()
}

function goToPage(page) {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  fetchJobs()
}

function selectCity(city) {
  selectedCity.value = city
  handleSearch()
}

async function fetchJobs() {
  loading.value = true
  try {
    const params = {
      keyword: searchQuery.value || undefined,
      city: selectedCity.value !== '全国' ? selectedCity.value : undefined,
      industry: selectedIndustry.value !== '不限' ? selectedIndustry.value : undefined,
      jobType: selectedJobType.value === '全职' ? 1 : selectedJobType.value === '实习' ? 2 : selectedJobType.value === '兼职' ? 3 : undefined,
      sortBy: sortMap[activeSort.value] || undefined,
      pageNum: currentPage.value,
      pageSize: 10
    }
    const res = await searchJobs(params)
    if (res && res.records) {
      jobs.value = res.records.map((j, i) => ({
        id: j.id,
        title: j.title,
        company: j.companyName || '未知企业',
        logoBg: LOGO_COLORS[i % LOGO_COLORS.length],
        logoText: j.companyName ? j.companyName.charAt(0) : '企',
        badges: [],
        salary: formatSalary(j.salaryMin, j.salaryMax),
        tags: [j.city, j.education, j.experience, j.jobType === 1 ? '全职' : j.jobType === 2 ? '实习' : '兼职'].filter(Boolean),
        benefits: j.benefits ? j.benefits.split(/[,，、]/).filter(Boolean) : [],
        desc: j.description ? j.description.substring(0, 60) + (j.description.length > 60 ? '...' : '') : '',
        meta: `${j.viewCount || 0}人看过 · ${j.applyCount || 0}人投递`,
        isFav: favoriteIds.value.has(j.id)
      }))
      totalJobs.value = res.total || 0
      totalPages.value = res.totalPages || 1
    }
  } catch (e) {
    console.error('搜索职位失败:', e)
    jobs.value = []
    totalJobs.value = 0
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  if (route.query.keyword) searchQuery.value = route.query.keyword
  if (route.query.city) selectedCity.value = route.query.city
  if (route.query.industry) selectedIndustry.value = route.query.industry

  try {
    const res = await getJobCategories()
    console.log('search categories:', res)
    if (res && res.industries) {
      industryOptions.value = ['不限', ...res.industries]
    }
  } catch (e) {
    console.error('获取行业类别失败:', e)
  }
  fetchJobs()
  loadFavorites()
  loadHotJobs()
})

watch(() => route.query, (q) => {
  if (q.keyword !== undefined) searchQuery.value = q.keyword || ''
  if (q.city !== undefined) selectedCity.value = q.city || '全国'
  if (q.industry !== undefined) selectedIndustry.value = q.industry || '不限'
  currentPage.value = 1
  fetchJobs()
})
</script>
