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
        <div v-if="store.isLoggedIn" class="hidden md:flex items-center gap-4 bg-surface-container-lowest rounded-xl border border-surface-container-high px-5 py-4 shrink-0">
          <div class="relative w-14 h-14">
            <svg class="w-14 h-14 -rotate-90" viewBox="0 0 56 56">
              <circle cx="28" cy="28" r="24" fill="none" stroke="#e8e8e8" stroke-width="4"/>
              <circle cx="28" cy="28" r="24" fill="none" stroke="#1a56db" stroke-width="4" stroke-linecap="round"
                :stroke-dasharray="150.8" :stroke-dashoffset="150.8 - (150.8 * resumeCompleteness / 100)"/>
            </svg>
            <span class="absolute inset-0 flex items-center justify-center text-sm font-bold text-primary">{{ resumeCompleteness }}%</span>
          </div>
          <div>
            <p class="text-sm font-semibold text-on-surface">{{ defaultResumeTitle }}</p>
            <p class="text-xs text-on-surface-variant mt-0.5">简历完整度 {{ resumeCompleteness }}%，完整度越高越容易被 HR 关注</p>
          </div>
          <button @click="router.push('/resume/manage')" class="px-3 py-1.5 text-xs font-semibold text-primary border border-primary rounded-lg hover:bg-primary/5 transition-colors shrink-0">优化简历</button>
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
            <select v-model="selectedIndustry" class="h-11 pl-9 pr-8 bg-surface-container-low text-on-surface text-sm rounded-xl appearance-none cursor-pointer focus:outline-none border border-surface-container-high">
              <option value="">全部行业</option>
              <option v-for="ind in industryOptions" :key="ind" :value="ind">{{ ind }}</option>
            </select>
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">apartment</span>
            <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">arrow_drop_down</span>
          </div>
          <div class="relative hidden sm:block">
            <select v-model="selectedCity" class="h-11 pl-9 pr-8 bg-surface-container-low text-on-surface text-sm rounded-xl appearance-none cursor-pointer focus:outline-none border border-surface-container-high">
              <option value="">全部城市</option>
              <option v-for="c in cityOptions" :key="c" :value="c">{{ c }}</option>
            </select>
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">location_on</span>
            <span class="material-symbols-outlined pointer-events-none absolute right-2 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">arrow_drop_down</span>
          </div>
          <button @click="handleSearch" class="h-11 px-6 bg-primary text-on-primary font-semibold text-sm rounded-xl hover:bg-primary/90 transition-colors flex items-center gap-2 shrink-0">
            <span class="material-symbols-outlined text-[18px]">tune</span>
            精准检索
          </button>
        </div>
        <!-- Hot Tags -->
        <div class="flex items-center gap-2 mt-3 flex-wrap">
          <span class="text-xs text-on-surface-variant shrink-0">🔥 热门行业：</span>
          <button v-for="tag in hotIndustryTags" :key="tag" @click="searchByIndustry(tag)"
            class="px-3 py-1 text-xs rounded-full border border-primary bg-primary/5 text-primary font-semibold hover:bg-primary/10 transition-colors cursor-pointer">
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
          <button v-for="tab in jobTabs" :key="tab" @click="searchByTab(tab)"
            class="px-3 py-1.5 text-xs font-medium rounded-md whitespace-nowrap transition-all"
            :class="activeJobTab === tab ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface'">
            {{ tab }}
          </button>
        </div>
      </div>

      <div class="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
        <router-link v-for="job in jobs" :key="job.id" :to="`/jobs/${job.id}`"
          class="bg-surface-container-lowest rounded-xl border border-surface-container-high p-5 hover:shadow-lg hover:border-primary/30 transition-all group flex flex-col">
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
            <button @click.prevent="applyJob(job)" class="px-4 py-1.5 bg-primary text-on-primary text-xs font-semibold rounded-lg hover:bg-primary/90 transition-colors">投递简历</button>
          </div>
        </router-link>
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { searchJobs, getJobCategories } from '../api/job'
import { getResumeList } from '../api/resume'
import { formatSalary } from '../utils/format'

const router = useRouter()
const store = useAppStore()
const searchQuery = ref('')
const selectedIndustry = ref('')
const selectedCity = ref('')
const industryOptions = ref([])
const hotIndustryTags = ref([])
const jobTabs = ref(['全部推荐'])
const activeJobTab = ref('全部推荐')
const resumeCompleteness = ref(0)
const defaultResumeTitle = ref('我的在线简历')

const ALL_CITIES = ['北京', '上海', '广州', '深圳', '杭州', '南京', '成都', '武汉', '西安', '苏州', '天津', '重庆', '长沙', '郑州', '青岛', '大连', '宁波', '厦门', '合肥', '佛山', '东莞', '无锡', '昆明', '福州', '济南', '哈尔滨', '沈阳', '长春', '贵阳', '南宁', '太原', '石家庄', '兰州', '海口', '银川', '西宁', '拉萨', '呼和浩特', '乌鲁木齐', '台北', '香港', '澳门']
const cityOptions = ALL_CITIES

const LOGO_COLORS = ['#1a56db', '#006591', '#0a8754', '#0078d4', '#e74c3c', '#8e44ad', '#e67e22', '#1abc9c', '#2c3e50', '#d35400', '#27ae60', '#c0392b']

function shuffle(arr) {
  const a = [...arr]
  for (let i = a.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [a[i], a[j]] = [a[j], a[i]]
  }
  return a
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
  router.push(`/jobs/${job.id}`)
}

async function loadResumeCard() {
  if (!store.isLoggedIn) return
  try {
    const res = await getResumeList()
    if (Array.isArray(res) && res.length > 0) {
      const def = res.find(r => r.isDefault === 1) || res[0]
      defaultResumeTitle.value = def.title || '我的在线简历'
      const fields = [def.title, def.name, def.phone, def.email, def.school, def.major, def.selfIntroduction, def.skills, def.experiences, def.projects]
      const filled = fields.filter(Boolean).length
      resumeCompleteness.value = Math.round(filled / fields.length * 100)
    }
  } catch (e) {
    console.error('获取简历信息失败:', e)
  }
}

function handleSearch() {
  const query = {}
  if (searchQuery.value) query.keyword = searchQuery.value
  if (selectedIndustry.value) query.industry = selectedIndustry.value
  if (selectedCity.value) query.city = selectedCity.value
  router.push({ path: '/jobs', query })
}

function searchByIndustry(industry) {
  router.push({ path: '/jobs', query: { industry } })
}

function searchByTab(tab) {
  activeJobTab.value = tab
  if (tab === '全部推荐') {
    fetchJobs({ pageNum: 1, pageSize: 4 })
  } else {
    router.push({ path: '/jobs', query: { industry: tab } })
  }
}

const jobs = ref([])

async function fetchJobs(params = { pageNum: 1, pageSize: 4 }) {
  try {
    const res = await searchJobs(params)
    if (res && res.records) {
      const shuffled = params.pageSize === 4 ? shuffle(res.records).slice(0, 4) : res.records
      jobs.value = shuffled.map((j, i) => ({
        id: j.id,
        title: j.title,
        company: j.companyName || '未知企业',
        logoBg: LOGO_COLORS[i % LOGO_COLORS.length],
        logoText: j.companyName ? j.companyName.charAt(0) : '企',
        salary: formatSalary(j.salaryMin, j.salaryMax),
        tags: [j.city, j.education, j.experience, j.jobType === 2 ? '实习' : '全职'].filter(Boolean),
        desc: j.description ? j.description.substring(0, 80) + (j.description.length > 80 ? '...' : '') : '暂无描述',
        meta: `${j.viewCount || 0}人看过 · ${j.applyCount || 0}人投递`
      }))
    }
  } catch (e) {
    console.error('获取推荐职位失败:', e)
  }
}

onMounted(async () => {
  try {
    const res = await getJobCategories()
    console.log('categories:', res)
    if (res && res.industries && res.industries.length > 0) {
      industryOptions.value = res.industries
      hotIndustryTags.value = shuffle(res.industries).slice(0, 4)
      jobTabs.value = ['全部推荐', ...shuffle(res.industries).slice(0, 4)]
    }
  } catch (e) {
    console.error('获取行业类别失败:', e)
  }
  fetchJobs()
  loadResumeCard()
})

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
