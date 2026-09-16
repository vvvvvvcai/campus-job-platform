<template>
  <div class="min-h-screen bg-background">
    <!-- 页面头部 -->
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6 flex items-start justify-between">
        <div>
          <h1 class="text-2xl font-bold text-on-surface">{{ companyName || '企业' }} · 在招职位</h1>
          <p class="text-on-surface-variant mt-1">管理企业发布的全部职位，支持编辑与上/下架。</p>
        </div>
        <router-link to="/enterprise/post-job" class="h-10 px-5 flex items-center gap-2 rounded-xl bg-primary text-on-primary text-sm font-semibold hover:bg-primary-container transition-colors shadow-sm">
          <span class="material-symbols-outlined text-lg">add</span>
          发布新职位
        </router-link>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-6">
      <!-- 搜索与筛选 -->
      <div class="flex items-center gap-3 mb-6 flex-wrap">
        <div class="relative flex-1 max-w-md">
          <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">search</span>
          <input v-model="search" type="text" placeholder="搜索职位名称..."
            class="w-full h-10 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
        </div>
        <select v-model="statusFilter" class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none">
          <option value="">全部状态</option>
          <option value="1">招聘中</option>
          <option value="0">待审核</option>
          <option value="2">已关闭</option>
        </select>
        <button @click="loadJobs" class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface-variant text-sm hover:bg-surface-container transition-colors flex items-center gap-1">
          <span class="material-symbols-outlined text-base">refresh</span>刷新
        </button>
      </div>

      <!-- 职位卡片 -->
      <div v-if="jobs.length" class="grid md:grid-cols-2 xl:grid-cols-3 gap-4">
        <div v-for="job in filteredJobs" :key="job.id"
          class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5 hover:shadow-md hover:border-primary/30 transition-all flex flex-col">
          <div class="flex items-start justify-between mb-3">
            <h3 class="font-bold text-on-surface text-sm flex-1 min-w-0">{{ job.title }}</h3>
            <span class="px-2 py-0.5 rounded-full text-[11px] font-semibold shrink-0 ml-2" :class="job.statusClass">
              {{ job.statusLabel }}
            </span>
          </div>
          <div class="flex items-center gap-3 text-xs text-on-surface-variant mb-3 flex-wrap">
            <span class="flex items-center gap-1"><span class="material-symbols-outlined text-sm">location_on</span>{{ job.city || '-' }}</span>
            <span class="flex items-center gap-1"><span class="material-symbols-outlined text-sm">payments</span>{{ job.salary }}</span>
            <span class="flex items-center gap-1"><span class="material-symbols-outlined text-sm">work</span>{{ job.type }}</span>
            <span class="flex items-center gap-1"><span class="material-symbols-outlined text-sm">school</span>{{ job.education || '-' }}</span>
          </div>
          <div class="grid grid-cols-3 gap-3 mb-4">
            <div class="bg-surface-container-low rounded-xl p-3 text-center">
              <p class="text-lg font-bold text-primary">{{ job.applyCount }}</p>
              <p class="text-[10px] text-on-surface-variant">收到投递</p>
            </div>
            <div class="bg-surface-container-low rounded-xl p-3 text-center">
              <p class="text-lg font-bold text-on-surface">{{ job.viewCount }}</p>
              <p class="text-[10px] text-on-surface-variant">浏览</p>
            </div>
            <div class="bg-surface-container-low rounded-xl p-3 text-center">
              <p class="text-lg font-bold text-on-surface">{{ job.headcount }}</p>
              <p class="text-[10px] text-on-surface-variant">招聘人数</p>
            </div>
          </div>
          <div class="mt-auto flex gap-2">
            <router-link :to="{ path: '/enterprise/post-job', query: { id: job.id } }" class="flex-1 py-2 border border-surface-container-high text-on-surface text-xs font-semibold text-center rounded-xl hover:bg-surface-container transition-colors">
              编辑
            </router-link>
            <button v-if="job.status === 1" @click="changeStatus(job, 2)" class="flex-1 py-2 border border-amber-200 text-amber-600 text-xs font-semibold rounded-xl hover:bg-amber-50 transition-colors">
              下架
            </button>
            <button v-else-if="job.status === 2" @click="changeStatus(job, 1)" class="flex-1 py-2 border border-emerald-200 text-emerald-600 text-xs font-semibold rounded-xl hover:bg-emerald-50 transition-colors">
              重新上架
            </button>
            <router-link :to="{ path: '/enterprise/candidates' }" class="flex-1 py-2 bg-primary text-on-primary text-xs font-semibold text-center rounded-xl hover:bg-primary-container transition-colors">
              查看投递
            </router-link>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-else class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-12 text-center">
        <span class="material-symbols-outlined text-5xl text-surface-container-high mb-3 block">work_off</span>
        <p class="text-sm text-on-surface-variant">还没有发布过职位</p>
        <router-link to="/enterprise/post-job" class="mt-4 inline-block px-5 py-2 bg-primary text-on-primary rounded-xl text-sm font-medium hover:bg-primary-container transition-colors">
          发布第一个职位
        </router-link>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getCompanyInfo } from '../api/company'
import { getCompanyJobList, updateJobStatus } from '../api/job'
import { getReceivedApplications } from '../api/application'
import { formatSalary, formatDateTime, JOB_TYPE_MAP } from '../utils/format'

const companyName = ref('')
const search = ref('')
const statusFilter = ref('')
const jobs = ref([])
const toast = ref('')

const STATUS_MAP = {
  0: { label: '待审核', class: 'bg-amber-50 text-amber-600' },
  1: { label: '招聘中', class: 'bg-emerald-50 text-emerald-600' },
  2: { label: '已关闭', class: 'bg-gray-100 text-gray-500' }
}

const filteredJobs = computed(() => {
  let result = jobs.value
  if (statusFilter.value !== '') result = result.filter(j => String(j.status) === statusFilter.value)
  if (search.value.trim()) {
    const q = search.value.trim().toLowerCase()
    result = result.filter(j => (j.title || '').toLowerCase().includes(q))
  }
  return result
})

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

async function loadJobs() {
  try {
    let companyId = null
    try {
      const info = await getCompanyInfo()
      companyId = info && info.id
      companyName.value = (info && info.companyName) || ''
    } catch (e) {
      console.error('获取企业信息失败:', e)
    }
    if (!companyId) {
      showToast('请先完成企业认证')
      return
    }
    // 职位列表 + 各职位投递数
    const [jobRes, appRes] = await Promise.all([
      getCompanyJobList(companyId),
      getReceivedApplications({ companyId, page: 1, size: 200 })
    ])
    const applyMap = new Map()
    for (const a of ((appRes && appRes.records) || [])) {
      applyMap.set(a.jobId, (applyMap.get(a.jobId) || 0) + 1)
    }
    const list = Array.isArray(jobRes) ? jobRes : []
    jobs.value = list.map(j => {
      const st = STATUS_MAP[j.status] || { label: '未知', class: 'bg-gray-100 text-gray-500' }
      return {
        id: j.id,
        title: j.title,
        city: j.city,
        salary: formatSalary(j.salaryMin, j.salaryMax),
        type: JOB_TYPE_MAP[j.jobType] || '全职',
        education: j.education,
        viewCount: j.viewCount || 0,
        applyCount: applyMap.get(j.id) || 0,
        headcount: j.headcount || 1,
        status: j.status,
        statusLabel: st.label,
        statusClass: st.class
      }
    })
  } catch (e) {
    showToast('加载职位失败：' + (e.message || '请重试'))
  }
}

async function changeStatus(job, status) {
  try {
    await updateJobStatus(job.id, status)
    showToast(status === 1 ? '职位已重新上架' : '职位已下架')
    loadJobs()
  } catch (e) {
    showToast(e.message || '操作失败')
  }
}

onMounted(loadJobs)
</script>
