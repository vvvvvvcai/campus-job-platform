<template>
  <div class="min-h-screen bg-background">
    <!-- Loading State -->
    <div v-if="loading" class="max-w-6xl mx-auto px-6 pt-12 flex flex-col items-center justify-center min-h-[50vh]">
      <span class="material-symbols-outlined text-4xl text-primary animate-spin mb-4">progress_activity</span>
      <p class="text-on-surface-variant text-sm">加载职位详情中...</p>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="max-w-6xl mx-auto px-6 pt-12 flex flex-col items-center justify-center min-h-[50vh]">
      <span class="material-symbols-outlined text-4xl text-error mb-4">error</span>
      <p class="text-on-surface-variant text-sm mb-4">{{ error }}</p>
      <button @click="fetchJobDetail" class="px-4 py-2 bg-primary text-on-primary rounded-xl text-sm font-medium hover:bg-primary/90 transition-colors">
        重试
      </button>
    </div>

    <!-- Job Detail Content -->
    <template v-else-if="jobData">
    <!-- Hero Card -->
    <section class="max-w-6xl mx-auto px-6 pt-6">
      <div class="bg-gradient-to-br from-surface-container-lowest via-surface-container-lowest to-primary/5 rounded-3xl border border-surface-container-high p-6 lg:p-8">
        <div class="flex gap-8">
          <!-- Left: Job Info -->
          <div class="flex-1 min-w-0 flex flex-col justify-between">
            <!-- Top Tags -->
            <div class="flex flex-wrap items-center gap-2 mb-4">
              <span class="px-3 py-1 rounded-full text-xs font-semibold bg-primary text-on-primary">{{ job.type }}</span>
              <span v-if="job.industry" class="px-3 py-1 rounded-full text-xs font-medium border border-primary/30 text-primary">{{ job.industry }}</span>
              <span class="px-3 py-1 rounded-full text-xs font-medium border border-surface-container-high text-on-surface-variant">{{ job.city }} · 校招</span>
              <span v-if="job.education" class="px-3 py-1 rounded-full text-xs font-medium border border-surface-container-high text-on-surface-variant flex items-center gap-1">
                <span class="material-symbols-outlined text-sm">school</span>
                {{ job.education }}及以上
              </span>
            </div>

            <!-- Title -->
            <h1 class="text-2xl lg:text-[28px] font-bold text-on-surface mb-4 leading-tight">{{ job.title }}</h1>

            <!-- Salary -->
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl font-extrabold text-primary leading-none">{{ job.salary }}</span>
              <span class="text-sm text-on-surface-variant self-end mb-0.5">· 元/月</span>
            </div>

            <!-- Info Row -->
            <div class="flex flex-wrap items-center gap-x-4 gap-y-2 text-sm text-on-surface-variant mb-2">
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">apartment</span>
                {{ jobData.companyName || '未知企业' }}
              </span>
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">location_on</span>
                {{ job.city }}
              </span>
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">group</span>
                招聘 {{ job.headcount }} 人
              </span>
              <span v-if="job.experience" class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">work</span>
                {{ job.experience }}
              </span>
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">schedule</span>
                {{ formatDateTime(jobData.publishTime || jobData.createTime) }} 发布
              </span>
            </div>

            <!-- Stats -->
            <div class="flex items-center gap-6 text-sm text-on-surface-variant mb-5">
              <span>{{ job.viewCount }} 人看过</span>
              <span>{{ job.applyCount }} 人已投递</span>
              <span class="px-2.5 py-1 bg-secondary-container text-on-secondary-container rounded-full text-xs font-medium">高校毕业生特别增益计划</span>
            </div>
          </div>

          <!-- Right: Apply Card -->
          <div class="w-72 shrink-0 hidden lg:flex flex-col gap-4">
            <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
              <div class="flex items-center gap-3 mb-3">
                <div class="w-11 h-11 rounded-xl bg-gradient-to-br from-primary to-primary-container flex items-center justify-center text-on-primary font-bold text-sm">
                  {{ (jobData.companyName || '企').charAt(0) }}
                </div>
                <div>
                  <div class="flex items-center gap-1.5">
                    <span class="text-sm font-bold text-on-surface">{{ jobData.companyName || '未知企业' }}</span>
                  </div>
                  <p class="text-xs text-on-surface-variant">校招职位 · 简历直投 HR 后台</p>
                </div>
              </div>
              <p v-if="applied" class="text-xs text-amber-600 bg-amber-50 border border-amber-100 rounded-lg px-3 py-2 mb-2 flex items-center gap-1.5">
                <span class="material-symbols-outlined text-sm">check_circle</span>
                你已投递过该职位，可在「我的投递记录」查看进度
              </p>
              <button @click="openApply" class="w-full py-2.5 bg-primary text-on-primary rounded-xl text-sm font-semibold hover:bg-primary/90 transition-colors flex items-center justify-center gap-2 mb-2" :disabled="applied">
                <span class="material-symbols-outlined text-base">send</span>
                {{ applied ? '已投递' : '立即投递简历' }}
              </button>
              <button @click="goChat" class="w-full py-2.5 border border-primary text-primary rounded-xl text-sm font-semibold hover:bg-primary/5 transition-colors flex items-center justify-center gap-2 mb-3">
                <span class="material-symbols-outlined text-base">chat</span>
                与HR直聊
              </button>
              <button @click="toggleFavorite"
                class="w-full py-1.5 text-on-surface-variant text-xs flex items-center justify-center gap-1.5 hover:text-primary transition-colors">
                <span class="material-symbols-outlined text-sm">{{ isFavorited ? 'bookmark' : 'bookmark_border' }}</span>
                {{ isFavorited ? '已收藏' : '收藏职位' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <div class="max-w-6xl mx-auto px-6 py-6">
      <div class="flex gap-6">
        <!-- Left Content -->
        <div class="flex-1 space-y-6">
          <!-- 职位描述 -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6">
            <h2 class="text-lg font-bold text-on-surface flex items-center gap-2 mb-4">
              <span class="w-1 h-5 bg-primary rounded-full"></span>
              职位描述
            </h2>
            <p class="text-sm text-on-surface-variant leading-relaxed whitespace-pre-line">{{ jobData.description || '暂无描述' }}</p>
          </div>

          <!-- 任职要求 -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6">
            <h2 class="text-lg font-bold text-on-surface flex items-center gap-2 mb-4">
              <span class="w-1 h-5 bg-primary rounded-full"></span>
              任职要求
            </h2>
            <p class="text-sm text-on-surface-variant leading-relaxed whitespace-pre-line">{{ jobData.requirement || '暂无要求' }}</p>
          </div>

          <!-- 福利待遇 -->
          <div v-if="benefitItems.length" class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6">
            <h2 class="text-lg font-bold text-on-surface flex items-center gap-2 mb-4">
              <span class="w-1 h-5 bg-primary rounded-full"></span>
              福利待遇
            </h2>
            <div class="flex flex-wrap gap-2">
              <span v-for="(b, i) in benefitItems" :key="i"
                class="px-3 py-1.5 bg-primary/5 border border-primary/10 rounded-lg text-xs text-primary font-medium">
                {{ b }}
              </span>
            </div>
          </div>
        </div>

        <!-- Right Sidebar -->
        <div class="w-72 shrink-0 hidden lg:block space-y-4">
          <!-- Similar Jobs -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-sm font-bold text-on-surface">相似职位推荐</h3>
              <button @click="refreshSimilar" class="text-xs text-primary font-medium hover:underline">换一批</button>
            </div>
            <div v-if="similarJobs.length" class="space-y-3">
              <div v-for="sj in similarJobs" :key="sj.id" @click="goToJob(sj.id)"
                class="block py-3 border-b border-surface-container-high last:border-0 hover:bg-surface-container-low -mx-2 px-2 rounded-lg transition-colors cursor-pointer">
                <p class="text-xs font-medium text-on-surface">{{ sj.title }}</p>
                <p class="text-[10px] text-on-surface-variant mt-0.5">{{ sj.companyName }} · {{ sj.city }}</p>
                <div class="flex items-center justify-between mt-1.5">
                  <span class="text-[10px] text-primary font-medium">{{ formatSalary(sj.salaryMin, sj.salaryMax) }}</span>
                  <span class="text-[10px] text-on-surface-variant flex items-center gap-0.5">查看<span class="material-symbols-outlined text-xs">open_in_new</span></span>
                </div>
              </div>
            </div>
            <p v-else class="text-xs text-on-surface-variant py-3 text-center">暂无相似职位</p>
            <button @click="moreSimilar" class="w-full mt-3 py-2 text-xs text-on-surface-variant hover:text-primary transition-colors text-center">
              探索更多职位
              <span class="material-symbols-outlined text-sm align-middle">arrow_forward</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile Sticky Bottom Bar -->
    <div class="lg:hidden fixed bottom-0 left-0 right-0 bg-surface-container-lowest border-t border-surface-container-high px-6 py-3 flex items-center gap-3 z-40">
      <button @click="toggleFavorite"
        :class="['w-12 h-12 rounded-xl border flex items-center justify-center shrink-0 transition-colors',
          isFavorited ? 'border-red-200 bg-red-50 text-red-500' : 'border-surface-container-high text-on-surface-variant']">
        <span class="material-symbols-outlined">{{ isFavorited ? 'favorite' : 'favorite_border' }}</span>
      </button>
      <button @click="openApply" class="flex-1 py-3 bg-primary text-on-primary rounded-xl font-medium text-sm hover:bg-primary/90 transition-colors">
        {{ applied ? '已投递 · 查看进度' : '立即投递' }}
      </button>
    </div>

    <!-- Apply Modal -->
    <Teleport to="body">
      <Transition name="modal">
        <div v-if="showApplyModal" class="fixed inset-0 z-50 flex items-center justify-center p-6" @click.self="showApplyModal = false">
          <div class="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>
          <div class="relative bg-surface-container-lowest rounded-2xl shadow-xl w-full max-w-md p-6">
            <div class="flex items-center justify-between mb-5">
              <h3 class="font-bold text-on-surface">确认投递</h3>
              <button @click="showApplyModal = false" class="text-on-surface-variant hover:text-on-surface">
                <span class="material-symbols-outlined">close</span>
              </button>
            </div>
            <div class="bg-surface-container-low rounded-xl p-4 mb-5">
              <p class="text-sm font-medium text-on-surface">{{ job.title }}</p>
              <p class="text-xs text-on-surface-variant mt-0.5">{{ jobData.companyName }} · {{ job.city }}</p>
            </div>
            <div class="space-y-3 mb-5">
              <div class="flex items-center gap-3">
                <span class="material-symbols-outlined text-primary text-lg">description</span>
                <div class="flex-1">
                  <p class="text-sm text-on-surface">使用简历：<strong>{{ applyResumeTitle || '未选择简历' }}</strong></p>
                </div>
                <button v-if="resumeOptions.length > 1" @click="cycleResume" class="text-xs text-primary hover:underline shrink-0">切换</button>
              </div>
              <div v-if="resumeOptions.length === 0" class="text-xs text-amber-600 bg-amber-50 border border-amber-100 rounded-lg px-3 py-2">
                你还没有简历，<router-link to="/resume/editor" class="text-primary underline">先去创建一份简历</router-link>
              </div>
              <div v-if="applyResumePhone" class="flex items-center gap-3">
                <span class="material-symbols-outlined text-primary text-lg">mail</span>
                <div class="flex-1">
                  <p class="text-sm text-on-surface">联系电话：<strong>{{ maskPhone(applyResumePhone) }}</strong></p>
                </div>
              </div>
            </div>
            <div class="flex gap-3">
              <button @click="showApplyModal = false" class="flex-1 py-3 border border-surface-container-high rounded-xl text-sm font-medium text-on-surface-variant hover:bg-surface-container-low transition-colors">
                取消
              </button>
              <button @click="handleApply" :disabled="applying || resumeOptions.length === 0"
                class="flex-1 py-3 bg-primary text-on-primary rounded-xl text-sm font-medium hover:bg-primary/90 transition-colors disabled:opacity-50">
                {{ applying ? '投递中...' : '确认投递' }}
              </button>
            </div>
          </div>
        </div>
      </Transition>
    </Teleport>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getJobDetail, searchJobs } from '../api/job'
import { addFavorite, removeFavorite } from '../api/favorite'
import { getResumeList } from '../api/resume'
import { submitApplication, getApplicationList } from '../api/application'
import { formatSalary, formatDateTime, maskPhone } from '../utils/format'

const route = useRoute()
const router = useRouter()
const store = useAppStore()

const loading = ref(true)
const error = ref('')
const jobData = ref(null)
const isFavorited = ref(false)
const applied = ref(false)
const showApplyModal = ref(false)
const applying = ref(false)
const toast = ref('')
const similarJobs = ref([])
const similarPage = ref(1)
const resumeOptions = ref([])
const resumeIndex = ref(0)

const JOB_TYPE_MAP = { 1: '全职', 2: '实习', 3: '兼职' }

const job = computed(() => {
  const d = jobData.value
  if (!d) return {}
  return {
    title: d.title || '',
    salary: formatSalary(d.salaryMin, d.salaryMax),
    city: d.city || '',
    education: d.education || '',
    headcount: d.headcount || 0,
    type: JOB_TYPE_MAP[d.jobType] || '全职',
    industry: d.industry || '',
    experience: d.experience || '',
    viewCount: d.viewCount || 0,
    applyCount: d.applyCount || 0
  }
})

const benefitItems = computed(() => {
  const b = jobData.value?.benefits || ''
  if (!b) return []
  return b.split(/[,，、\n]/).map(s => s.trim()).filter(Boolean)
})

const applyResume = computed(() => resumeOptions.value[resumeIndex.value] || null)
const applyResumeTitle = computed(() => applyResume.value?.title || '')
const applyResumePhone = computed(() => applyResume.value?.phone || '')

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

async function fetchJobDetail() {
  const id = route.params.id
  if (!id) return
  loading.value = true
  error.value = ''
  try {
    const data = await getJobDetail(id)
    jobData.value = data
    isFavorited.value = !!data.isFavorite
    await Promise.all([loadSimilar(), checkApplied(id)])
  } catch (e) {
    error.value = e.message || '加载职位详情失败'
  } finally {
    loading.value = false
  }
}

async function checkApplied(jobId) {
  try {
    const res = await getApplicationList({ page: 1, size: 100 })
    const records = (res && res.records) || []
    applied.value = records.some(a => Number(a.jobId) === Number(jobId))
  } catch (e) {
    console.error('查询投递状态失败:', e)
  }
}

async function loadSimilar() {
  const d = jobData.value
  if (!d) return
  try {
    const params = { pageNum: similarPage.value, pageSize: 4 }
    if (d.industry) params.industry = d.industry
    else if (d.category) params.category = d.category
    const res = await searchJobs(params)
    if (res && res.records) {
      similarJobs.value = res.records.filter(j => String(j.id) !== String(route.params.id)).slice(0, 3)
    }
  } catch (e) {
    console.error('获取相似职位失败:', e)
  }
}

function refreshSimilar() {
  similarPage.value = similarPage.value >= 3 ? 1 : similarPage.value + 1
  loadSimilar()
}

function moreSimilar() {
  const d = jobData.value || {}
  router.push({ path: '/jobs', query: d.industry ? { industry: d.industry } : (d.category ? { keyword: d.category } : {}) })
}

async function toggleFavorite() {
  const id = route.params.id
  try {
    if (isFavorited.value) {
      await removeFavorite(id)
      isFavorited.value = false
      showToast('已取消收藏')
    } else {
      await addFavorite(id)
      isFavorited.value = true
      showToast('收藏成功')
    }
  } catch (e) {
    showToast(e.message || '收藏操作失败')
  }
}

async function openApply() {
  if (applied.value) {
    router.push('/applications')
    return
  }
  showApplyModal.value = true
  if (resumeOptions.value.length === 0) {
    try {
      const res = await getResumeList()
      resumeOptions.value = Array.isArray(res) ? res : []
      const defIdx = resumeOptions.value.findIndex(r => r.isDefault === 1)
      resumeIndex.value = defIdx >= 0 ? defIdx : 0
    } catch (e) {
      console.error('获取简历列表失败:', e)
    }
  }
}

function cycleResume() {
  resumeIndex.value = (resumeIndex.value + 1) % resumeOptions.value.length
}

async function handleApply() {
  const d = jobData.value
  const resume = applyResume.value
  if (!d || !resume) {
    showToast('请先创建简历')
    return
  }
  applying.value = true
  try {
    await submitApplication({ jobId: d.id, companyId: d.companyId, resumeId: resume.id })
    applying.value = false
    showApplyModal.value = false
    applied.value = true
    showToast('投递成功！可在「我的投递记录」查看进度')
  } catch (e) {
    applying.value = false
    showToast(e.message || '投递失败，请重试')
  }
}

function goChat() {
  const d = jobData.value
  if (!d) return
  if (!store.isLoggedIn) {
    window.dispatchEvent(new CustomEvent('open-login-modal'))
    return
  }
  const hrUserId = d.publisherId || d.companyId
  router.push({ name: 'ChatRoom', params: { toUserId: hrUserId }, query: { jobId: d.id, name: d.companyName || '企业', jobTitle: d.title || '' } })
}

function goToJob(id) {
  router.push({ name: 'JobDetail', params: { id } })
}

onMounted(fetchJobDetail)

watch(() => route.params.id, (newId) => {
  if (newId) fetchJobDetail()
})
</script>

<style scoped>
.modal-enter-active, .modal-leave-active { transition: all 0.3s ease; }
.modal-enter-from, .modal-leave-to { opacity: 0; }
</style>
