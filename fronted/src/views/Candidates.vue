<template>
  <div class="min-h-screen bg-background">
    <!-- 页面头部 -->
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6 flex items-start justify-between">
        <div>
          <h1 class="text-2xl font-bold text-on-surface">候选人投递管理</h1>
          <p class="text-on-surface-variant mt-1">实时查看收到的简历投递，推进面试邀请、录用与不合适流转。</p>
        </div>
        <div class="flex items-center gap-3">
          <router-link to="/enterprise/post-job" class="h-10 px-5 flex items-center gap-2 rounded-xl bg-primary text-on-primary text-sm font-semibold hover:bg-primary-container transition-colors shadow-sm">
            <span class="material-symbols-outlined text-lg">add</span>
            发布新校招职位
          </router-link>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-6 space-y-5">
      <!-- 统计卡片 -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">总投递数</span>
            <div class="w-9 h-9 rounded-xl bg-primary/10 flex items-center justify-center">
              <span class="material-symbols-outlined text-primary text-lg">folder_open</span>
            </div>
          </div>
          <span class="text-4xl font-bold text-on-surface">{{ stats.total }}</span>
          <p class="text-xs text-on-surface-variant mt-2">份简历投递</p>
        </div>
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">待查看</span>
            <div class="w-9 h-9 rounded-xl bg-amber-50 flex items-center justify-center">
              <span class="material-symbols-outlined text-amber-600 text-lg">mark_email_unread</span>
            </div>
          </div>
          <span class="text-4xl font-bold text-amber-600">{{ stats.pending }}</span>
          <p class="text-xs text-on-surface-variant mt-2">份等待初筛</p>
        </div>
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">面试邀请中</span>
            <div class="w-9 h-9 rounded-xl bg-emerald-50 flex items-center justify-center">
              <span class="material-symbols-outlined text-emerald-600 text-lg">event_available</span>
            </div>
          </div>
          <span class="text-4xl font-bold text-emerald-600">{{ stats.interview }}</span>
          <p class="text-xs text-on-surface-variant mt-2">份推进面试</p>
        </div>
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">已录用</span>
            <div class="w-9 h-9 rounded-xl bg-blue-50 flex items-center justify-center">
              <span class="material-symbols-outlined text-blue-600 text-lg">verified</span>
            </div>
          </div>
          <span class="text-4xl font-bold text-blue-600">{{ stats.offer }}</span>
          <p class="text-xs text-on-surface-variant mt-2">人通过录用</p>
        </div>
      </div>

      <!-- 筛选工具栏 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-4">
        <div class="flex flex-col lg:flex-row lg:items-center gap-3">
          <select v-model="filterStatus" @change="loadList(1)" class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 min-w-[140px]">
            <option :value="null">全部状态</option>
            <option :value="0">待查看</option>
            <option :value="1">已查看</option>
            <option :value="2">面试邀请</option>
            <option :value="3">不合适</option>
            <option :value="4">已录用</option>
          </select>
          <div class="relative flex-1">
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">search</span>
            <input v-model="searchQuery" type="text" placeholder="按职位或简历标题搜索..."
              class="w-full h-10 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/60 text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
          </div>
          <button @click="loadList(1)" class="h-9 px-5 rounded-xl bg-primary text-on-primary text-xs font-semibold hover:bg-primary-container transition-colors">开始检索</button>
        </div>
      </div>

      <!-- 批量操作栏 -->
      <div class="flex items-center justify-between">
        <div class="flex items-center gap-3">
          <label class="flex items-center gap-2 text-sm text-on-surface-variant cursor-pointer">
            <input type="checkbox" :checked="isAllSelected" @change="toggleSelectAll" class="w-4 h-4 rounded border-surface-container-high text-primary" />
            全选
          </label>
          <span class="text-xs text-on-surface-variant">已选 <span class="font-medium text-on-surface">{{ selectedIds.length }}</span> 条</span>
        </div>
        <button @click="batchRead" :disabled="selectedIds.length === 0" class="h-9 px-4 flex items-center gap-1.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-xs font-medium hover:bg-surface-container transition-colors disabled:opacity-40">
          <span class="material-symbols-outlined text-base">drafts</span>
          批量标为已查看
        </button>
      </div>

      <!-- 候选人列表表格 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-surface-container-low/50 border-b border-surface-container-high">
                <th class="text-left px-5 py-3.5 w-10">
                  <input type="checkbox" :checked="isAllSelected" @change="toggleSelectAll" class="w-4 h-4 rounded border-surface-container-high text-primary" />
                </th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">使用简历</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">应聘职位</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">投递时间</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">当前状态</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">HR 备注</th>
                <th class="text-right px-5 py-3.5 text-xs font-semibold text-on-surface-variant">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="c in filteredList" :key="c.id" class="border-b border-surface-container-high/50 hover:bg-surface-container-low/30 transition-colors">
                <td class="px-5 py-4">
                  <input type="checkbox" :checked="selectedIds.includes(c.id)" @change="toggleSelect(c.id)" class="w-4 h-4 rounded border-surface-container-high text-primary" />
                </td>
                <td class="px-5 py-4">
                  <div class="flex items-center gap-3">
                    <div class="w-10 h-10 rounded-full bg-primary/10 flex items-center justify-center text-primary font-bold text-sm shrink-0">
                      {{ (c.resumeTitle || '简').charAt(0) }}
                    </div>
                    <div>
                      <div class="font-semibold text-on-surface">{{ c.resumeTitle || '未命名简历' }}</div>
                      <div class="text-xs text-on-surface-variant mt-0.5">简历编号 #{{ c.resumeId }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-5 py-4">
                  <div class="font-medium text-on-surface text-sm">{{ c.jobName || '未知职位' }}</div>
                </td>
                <td class="px-5 py-4">
                  <div class="text-sm text-on-surface">{{ formatDateTime(c.createTime) }}</div>
                </td>
                <td class="px-5 py-4">
                  <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-medium" :class="c.statusClass">
                    <span class="w-1.5 h-1.5 rounded-full" :class="c.dotClass"></span>
                    {{ c.statusLabel }}
                  </span>
                </td>
                <td class="px-5 py-4">
                  <div class="text-xs text-on-surface max-w-[200px] truncate">{{ c.hrRemark || '-' }}</div>
                </td>
                <td class="px-5 py-4">
                  <div class="flex items-center justify-end gap-1">
                    <button @click="openResume(c.id)" class="p-2 rounded-lg hover:bg-primary/10 transition-colors text-primary" title="查看简历">
                      <span class="material-symbols-outlined text-lg">visibility</span>
                    </button>
                    <router-link :to="{ path: '/enterprise/applications', query: { id: c.id } }" class="p-2 rounded-lg hover:bg-primary/10 transition-colors text-primary" title="处理流转">
                      <span class="material-symbols-outlined text-lg">open_in_new</span>
                    </router-link>
                    <button @click="quickReject(c)" class="p-2 rounded-lg hover:bg-error/10 transition-colors text-error" title="不合适">
                      <span class="material-symbols-outlined text-lg">close</span>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- 空状态 -->
        <div v-if="applications.length === 0" class="p-12 text-center">
          <span class="material-symbols-outlined text-5xl text-surface-container-high mb-3 block">inbox</span>
          <p class="text-sm text-on-surface-variant">暂无投递记录</p>
          <p class="text-xs text-on-surface-variant mt-1">发布职位后，学生的投递会出现在这里</p>
        </div>

        <!-- 分页 -->
        <div class="px-5 py-4 border-t border-surface-container-high flex items-center justify-between">
          <span class="text-xs text-on-surface-variant">共 {{ total }} 条投递记录</span>
          <div class="flex items-center gap-1">
            <button @click="loadList(page - 1)" :disabled="page <= 1" class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high text-on-surface-variant disabled:opacity-40">
              <span class="material-symbols-outlined text-base">chevron_left</span>
            </button>
            <button v-for="p in pageList" :key="p" @click="loadList(p)"
              :class="['w-8 h-8 flex items-center justify-center rounded-lg text-xs font-medium', p === page ? 'bg-primary text-on-primary' : 'border border-surface-container-high text-on-surface-variant']">
              {{ p }}
            </button>
            <button @click="loadList(page + 1)" :disabled="page >= pages" class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high text-on-surface-variant disabled:opacity-40">
              <span class="material-symbols-outlined text-base">chevron_right</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>

    <!-- 候选人简历弹窗 -->
    <CandidateResumeModal :visible="showResume" :application-id="viewResumeAppId" @close="showResume = false" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getReceivedApplications, handleApplication } from '../api/application'
import { getCompanyInfo } from '../api/company'
import { APPLICATION_STATUS, formatDateTime } from '../utils/format'
import CandidateResumeModal from '../components/CandidateResumeModal.vue'

const applications = ref([])
const total = ref(0)
const page = ref(1)
const pages = ref(1)
const filterStatus = ref(null)
const searchQuery = ref('')
const selectedIds = ref([])
const toast = ref('')
let companyId = null

const stats = ref({ total: 0, pending: 0, interview: 0, offer: 0 })

const filteredList = computed(() => {
  if (!searchQuery.value.trim()) return applications.value
  const q = searchQuery.value.trim().toLowerCase()
  return applications.value.filter(a =>
    (a.jobName || '').toLowerCase().includes(q) || (a.resumeTitle || '').toLowerCase().includes(q))
})

const isAllSelected = computed(() => applications.value.length > 0 && applications.value.every(a => selectedIds.value.includes(a.id)))

const pageList = computed(() => {
  const arr = []
  for (let i = 1; i <= Math.min(pages.value, 5); i++) arr.push(i)
  return arr
})

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

function mapRecord(a) {
  const st = APPLICATION_STATUS[a.status] || { label: '未知', class: 'bg-gray-100 text-gray-600', dot: 'bg-gray-400' }
  return {
    id: a.id,
    resumeId: a.resumeId,
    jobName: a.jobName || '未知职位',
    resumeTitle: a.resumeTitle || '未命名简历',
    createTime: a.createTime,
    hrRemark: a.hrRemark,
    statusRaw: a.status,
    statusLabel: st.label,
    statusClass: st.class,
    dotClass: st.dot
  }
}

async function loadList(targetPage) {
  page.value = targetPage
  try {
    if (!companyId) {
      const info = await getCompanyInfo()
      companyId = info && info.id
    }
    if (!companyId) {
      showToast('请先完成企业认证')
      return
    }
    const res = await getReceivedApplications({ companyId, status: filterStatus.value ?? undefined, page: page.value, size: 10 })
    applications.value = (res.records || []).map(mapRecord)
    total.value = res.total || 0
    pages.value = res.pages || 1
    // 用当前页数据估算统计（后端无聚合接口，全量统计以列表为准）
    const all = await getReceivedApplications({ companyId, page: 1, size: 200 })
    const recs = (all.records || [])
    stats.value = {
      total: all.total || 0,
      pending: recs.filter(a => a.status === 0).length,
      interview: recs.filter(a => a.status === 2).length,
      offer: recs.filter(a => a.status === 4).length
    }
  } catch (e) {
    showToast('加载投递失败：' + (e.message || '请重试'))
  }
}

function toggleSelect(id) {
  const idx = selectedIds.value.indexOf(id)
  if (idx >= 0) selectedIds.value.splice(idx, 1)
  else selectedIds.value.push(id)
}

function toggleSelectAll() {
  if (isAllSelected.value) selectedIds.value = []
  else selectedIds.value = applications.value.map(a => a.id)
}

async function batchRead() {
  let ok = 0
  let fail = 0
  for (const id of selectedIds.value) {
    try {
      await handleApplication(id, { status: 1 })
      ok++
    } catch (e) {
      fail++
    }
  }
  selectedIds.value = []
  showToast(`已标记 ${ok} 条为已查看${fail ? `，失败 ${fail} 条` : ''}`)
  loadList(page.value)
}

async function quickReject(c) {
  try {
    await handleApplication(c.id, { status: 3, hrRemark: '不合适，已归档' })
    showToast('已标记为不合适')
    loadList(page.value)
  } catch (e) {
    showToast(e.message || '操作失败')
  }
}

const showResume = ref(false)
const viewResumeAppId = ref(null)

async function openResume(applicationId) {
  viewResumeAppId.value = applicationId
  showResume.value = true
  // 查看简历 = 已查看：待查看(0)的记录自动标记为已查看(1)
  const record = applications.value.find(a => a.id === applicationId)
  if (!record || record.statusRaw !== 0) return
  try {
    await handleApplication(applicationId, { status: 1 })
    record.statusRaw = 1
    const st = APPLICATION_STATUS[1]
    record.statusLabel = st.label
    record.statusClass = st.class
    record.dotClass = st.dot
    showToast('已自动标记为已查看')
    loadList(page.value)
  } catch (e) {
    console.error('标记已查看失败:', e)
  }
}

onMounted(() => loadList(1))
</script>
