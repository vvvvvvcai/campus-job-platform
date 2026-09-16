<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-5xl mx-auto px-4 md:px-6 py-8">
      <!-- Header + Tabs -->
      <div class="flex items-center justify-between mb-6">
        <div>
          <h1 class="text-2xl font-bold text-[var(--on-surface)]">我的收藏职位</h1>
          <p class="text-sm text-[var(--on-surface-variant)] mt-1">收藏心仪职位，随时一键投递，不错过任何校招机会。</p>
        </div>
        <div class="flex bg-[var(--surface-container-low)] rounded-xl p-1">
          <router-link to="/applications" class="px-4 py-2 rounded-lg text-xs font-medium text-[var(--on-surface-variant)] hover:text-[var(--on-surface)] transition-all">
            我的投递
          </router-link>
          <span class="px-4 py-2 rounded-lg text-xs font-semibold bg-white text-[var(--primary)] shadow-sm">我的收藏</span>
        </div>
      </div>

      <!-- Search -->
      <div class="relative mb-5">
        <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-[var(--on-surface-variant)] text-lg">search</span>
        <input v-model="searchQuery" type="text" placeholder="收藏的职位或公司名称..."
          class="pl-10 pr-4 py-2.5 w-full md:w-96 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
      </div>

      <!-- Batch Bar -->
      <div class="flex items-center justify-between mb-5">
        <label class="flex items-center gap-2 cursor-pointer">
          <input type="checkbox" :checked="isAllSelected" @change="toggleSelectAll"
            class="w-4 h-4 rounded border-gray-300 text-[var(--primary)] focus:ring-[var(--primary)]" />
          <span class="text-sm text-[var(--on-surface-variant)]">全选（已选 <span class="font-semibold text-[var(--on-surface)]">{{ selectedIds.length }}</span> 项）</span>
        </label>
        <div v-if="selectedIds.length > 0" class="flex items-center gap-2">
          <button @click="batchApply" :disabled="batchApplying"
            class="px-4 py-2 bg-[var(--primary)] text-white rounded-xl text-xs font-semibold hover:bg-[var(--primary-container)] transition-colors disabled:opacity-50 flex items-center gap-1.5">
            <span class="material-symbols-outlined text-[16px]">outgoing_mail</span>
            {{ batchApplying ? '投递中...' : `投递所有选中 (${selectedIds.length})` }}
          </button>
          <button @click="batchRemove" class="px-4 py-2 border border-red-200 text-red-500 rounded-xl text-xs font-medium hover:bg-red-50 transition-colors">
            批量取消
          </button>
        </div>
      </div>

      <!-- Job Cards -->
      <div class="space-y-4">
        <article v-for="job in filteredJobs" :key="job.id"
          class="job-card group bg-white rounded-xl p-6 shadow-sm hover:shadow-md transition-all duration-200 relative overflow-hidden border border-gray-100">
          <!-- 左侧色条 -->
          <div class="absolute left-0 top-0 bottom-0 w-1.5" :class="job.status === 'open' ? 'bg-[var(--primary)]' : 'bg-gray-300'"></div>

          <div class="flex flex-col md:flex-row md:items-start justify-between gap-4">
            <div class="flex items-start gap-4 min-w-0">
              <input type="checkbox" :checked="selectedIds.includes(job.id)" @change="toggleSelect(job.id)" @click.stop
                class="mt-1 w-4 h-4 rounded border-gray-300 text-[var(--primary)] focus:ring-[var(--primary)] shrink-0" />
              <div class="w-12 h-12 rounded-xl bg-[var(--surface-container-high)] flex items-center justify-center shrink-0 shadow-sm overflow-hidden">
                <span class="text-lg font-bold text-[var(--primary)]">{{ job.logo }}</span>
              </div>

              <div class="min-w-0 space-y-1">
                <div class="flex items-center gap-2 flex-wrap">
                  <h2 @click="$router.push(`/jobs/${job.id}`)"
                    class="font-bold text-[var(--on-surface)] truncate group-hover:text-[var(--primary)] transition-colors cursor-pointer">
                    {{ job.title }}
                  </h2>
                  <span v-if="job.status === 'open'" class="px-2 py-0.5 rounded-md bg-[var(--secondary-fixed)]/40 text-[var(--primary)] text-[10px] font-semibold">
                    投递反馈活跃
                  </span>
                </div>
                <div class="flex items-center gap-2 text-xs text-[var(--on-surface-variant)]">
                  <span class="font-medium text-[var(--on-surface)]">{{ job.company }}</span>
                  <span class="flex items-center text-[var(--primary)]" title="官方认证企业">
                    <span class="material-symbols-outlined text-[16px]">verified</span>
                  </span>
                </div>
                <div class="flex flex-wrap gap-1.5 pt-0.5">
                  <span v-for="tag in job.tags" :key="tag"
                    class="px-2 py-0.5 rounded-md text-[11px] bg-[var(--surface-container-low)] text-[var(--on-surface-variant)]">{{ tag }}</span>
                </div>
              </div>
            </div>

            <!-- Right: salary + actions -->
            <div class="md:text-right shrink-0 md:min-w-[200px]">
              <div class="flex md:justify-end items-center gap-2 mb-1">
                <span class="text-lg font-bold text-[var(--primary)]">{{ job.salary }}</span>
                <span class="text-xs text-[var(--on-surface-variant)]">· 月</span>
              </div>
              <p class="text-[11px] text-[var(--on-surface-variant)] mb-3">
                <span :class="job.status === 'open' ? 'text-emerald-600' : 'text-gray-500'">{{ job.status === 'open' ? '投递开放中' : '已招满' }}</span>
                <span class="mx-1">·</span>{{ job.city }}
              </p>
              <div class="flex items-center gap-2 md:justify-end">
                <button @click="chatUnavailable" class="px-3 py-1.5 text-[var(--on-surface-variant)] hover:text-[var(--primary)] text-xs font-medium flex items-center gap-1 rounded-lg hover:bg-[var(--surface-container-low)] transition-colors">
                  <span class="material-symbols-outlined text-[16px]">chat</span>
                  在线沟通
                </button>
                <button @click="removeFavorite(job.id)" class="px-3 py-1.5 text-gray-400 hover:text-red-500 text-xs font-medium rounded-lg hover:bg-red-50 transition-colors">
                  取消收藏
                </button>
                <button @click="applySingle(job)" :disabled="job.status !== 'open'"
                  class="px-4 py-1.5 bg-[var(--primary)] text-white rounded-lg text-xs font-semibold hover:bg-[var(--primary-container)] shadow-sm transition-all flex items-center gap-1 disabled:opacity-40">
                  <span class="material-symbols-outlined text-[16px]">outgoing_mail</span>
                  立即投递简历
                </button>
              </div>
            </div>
          </div>
        </article>
      </div>

      <!-- Empty State -->
      <div v-if="filteredJobs.length === 0" class="bg-white rounded-2xl border border-gray-100 shadow-sm p-12 text-center">
        <span class="material-symbols-outlined text-5xl text-gray-200 mb-4 block">bookmark_border</span>
        <p class="text-sm text-[var(--on-surface-variant)]">{{ searchQuery ? '没有找到匹配的收藏职位' : '收藏夹为空，去发现心仪职位吧' }}</p>
        <router-link to="/jobs" class="mt-4 inline-block px-5 py-2 bg-[var(--primary)] text-white rounded-xl text-sm font-medium hover:bg-[var(--primary-container)] transition-colors">
          去发现职位
        </router-link>
      </div>

      <!-- 求职助手小贴士 -->
      <div class="mt-8 bg-[var(--primary)]/5 rounded-2xl border border-[var(--primary)]/10 p-5">
        <h3 class="text-sm font-bold text-[var(--on-surface)] flex items-center gap-2 mb-2">
          <span class="material-symbols-outlined text-[var(--primary)] text-lg">lightbulb</span>
          求职助手小贴士
        </h3>
        <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed">
          收藏的职位建议在 24 小时内完成投递——校招岗位「投递反馈活跃」状态说明企业 HR 正在高频处理简历，此时投递进入面试环节的概率更高；投递后可到「我的投递」查看全流程进度追踪。
        </p>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-[var(--on-surface)] text-white rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getFavoriteList, removeFavorite as apiRemoveFavorite } from '../api/favorite'
import { getResumeList } from '../api/resume'
import { submitApplication } from '../api/application'
import { formatSalary } from '../utils/format'

const router = useRouter()
const store = useAppStore()
const toast = ref('')
const searchQuery = ref('')
const selectedIds = ref([])
const loading = ref(false)
const batchApplying = ref(false)
const jobs = ref([])

const filteredJobs = computed(() => {
  let result = jobs.value
  if (searchQuery.value.trim()) {
    const q = searchQuery.value.trim().toLowerCase()
    result = result.filter(j => j.title.toLowerCase().includes(q) || j.company.toLowerCase().includes(q))
  }
  return result
})

const isAllSelected = computed(() => filteredJobs.value.length > 0 && filteredJobs.value.every(j => selectedIds.value.includes(j.id)))

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 3000)
}

function chatUnavailable() {
  showToast('在线沟通功能暂未开放')
}

onMounted(async () => {
  if (!store.isLoggedIn) return
  loading.value = true
  try {
    const res = await getFavoriteList()
    if (Array.isArray(res)) {
      jobs.value = res.map(j => ({
        id: j.id,
        companyId: j.companyId,
        title: j.title,
        company: j.companyName || '未知企业',
        logo: j.companyName ? j.companyName.charAt(0) : '企',
        salary: formatSalary(j.salaryMin, j.salaryMax),
        city: j.city || '',
        tags: [j.city, j.education, j.experience, j.jobType === 1 ? '全职' : j.jobType === 2 ? '实习' : '兼职'].filter(Boolean),
        status: j.status === 1 ? 'open' : 'full'
      }))
    }
  } catch (e) {
    console.error('获取收藏列表失败:', e)
    showToast('获取收藏列表失败，请重试')
  } finally {
    loading.value = false
  }
})

function toggleSelect(id) {
  const idx = selectedIds.value.indexOf(id)
  if (idx >= 0) selectedIds.value.splice(idx, 1)
  else selectedIds.value.push(id)
}

function toggleSelectAll() {
  if (isAllSelected.value) selectedIds.value = []
  else selectedIds.value = filteredJobs.value.map(j => j.id)
}

async function removeFavorite(id) {
  try {
    await apiRemoveFavorite(id)
    jobs.value = jobs.value.filter(j => j.id !== id)
    selectedIds.value = selectedIds.value.filter(i => i !== id)
    showToast('已取消收藏')
  } catch (e) {
    console.error('取消收藏失败:', e)
    showToast(e.message || '取消收藏失败')
  }
}

function batchRemove() {
  const ids = [...selectedIds.value]
  ids.forEach(id => removeFavorite(id))
}

async function getDefaultResume() {
  try {
    const res = await getResumeList()
    const list = Array.isArray(res) ? res : []
    return list.find(r => r.isDefault === 1) || list[0] || null
  } catch (e) {
    console.error('获取简历失败:', e)
    return null
  }
}

async function submitForJobs(targets) {
  let ok = 0
  let fail = 0
  for (const j of targets) {
    try {
      await submitApplication({ jobId: j.id, companyId: j.companyId, resumeId: j._resumeId })
      ok++
    } catch (e) {
      fail++
      console.error(`投递 ${j.title} 失败:`, e.message)
    }
  }
  return { ok, fail }
}

async function applySingle(job) {
  const resume = await getDefaultResume()
  if (!resume) {
    showToast('请先创建简历再投递')
    router.push('/resume/editor')
    return
  }
  const { ok, fail } = await submitForJobs([{ ...job, _resumeId: resume.id }])
  if (ok > 0) showToast('投递成功！可在「我的投递」查看进度')
  else showToast(fail > 0 ? '投递失败（可能已投递过该职位）' : '投递失败，请重试')
}

async function batchApply() {
  const targets = jobs.value.filter(j => selectedIds.value.includes(j.id) && j.status === 'open')
  if (targets.length === 0) return
  const resume = await getDefaultResume()
  if (!resume) {
    showToast('请先创建简历再投递')
    router.push('/resume/editor')
    return
  }
  batchApplying.value = true
  try {
    const { ok, fail } = await submitForJobs(targets.map(j => ({ ...j, _resumeId: resume.id })))
    selectedIds.value = []
    showToast(`批量投递完成：成功 ${ok} 份${fail > 0 ? `，失败 ${fail} 份（可能已投递过）` : ''}`)
  } finally {
    batchApplying.value = false
  }
}
</script>
