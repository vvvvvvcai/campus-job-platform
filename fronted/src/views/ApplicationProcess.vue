<template>
  <div class="min-h-screen bg-background">
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6 flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-on-surface">投递处理与流转</h1>
          <p class="text-on-surface-variant mt-1">查看候选人投递详情，推进面试 / 录用 / 不合适等流转状态</p>
        </div>
        <router-link to="/enterprise/candidates" class="px-4 py-2 border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium rounded-xl hover:bg-surface-container transition-colors">
          ← 返回候选人列表
        </router-link>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-8">
      <!-- 候选人选择 -->
      <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm mb-6">
        <label class="block text-xs font-semibold text-on-surface-variant mb-2">选择候选人</label>
        <select v-model="selectedId" @change="loadDetail" class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20">
          <option :value="null" disabled>选择一条投递记录…</option>
          <option v-for="app in applications" :key="app.id" :value="app.id">
            [{{ statusLabel(app.status) }}] {{ app.jobName }} · {{ app.resumeTitle }}（投递于 {{ formatDate(app.createTime) }}）
          </option>
        </select>
      </div>

      <div v-if="detail" class="grid lg:grid-cols-3 gap-6">
        <!-- Left: Detail + Handle -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Info Card -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <div class="flex items-start gap-4">
              <div class="w-14 h-14 rounded-xl bg-primary/10 flex items-center justify-center text-primary font-bold text-xl shrink-0">
                {{ (detail.resumeTitle || '简').charAt(0) }}
              </div>
              <div class="flex-1">
                <div class="flex items-center gap-3 mb-1 flex-wrap">
                  <h2 class="text-xl font-bold text-on-surface">{{ detail.jobName || '未知职位' }}</h2>
                  <span class="px-3 py-1 rounded-full text-xs font-semibold" :class="APPLICATION_STATUS[detail.status]?.class || 'bg-gray-100 text-gray-600'">
                    {{ APPLICATION_STATUS[detail.status]?.label || '未知状态' }}
                  </span>
                </div>
                <p class="text-on-surface-variant">使用简历：{{ detail.resumeTitle || '未填写' }} · 应聘企业：{{ detail.companyName || '未知企业' }}</p>
                <div class="flex flex-wrap gap-4 mt-3 text-sm text-on-surface-variant">
                  <span class="flex items-center gap-1"><span class="material-symbols-outlined text-base">schedule</span> 投递于 {{ formatDateTime(detail.createTime) }}</span>
                  <span v-if="detail.interviewTime" class="flex items-center gap-1"><span class="material-symbols-outlined text-base">event</span> 面试时间：{{ formatDateTime(detail.interviewTime) }}</span>
                </div>
                <p v-if="detail.hrRemark" class="text-xs text-on-surface-variant mt-3 bg-surface-container rounded-lg px-3 py-2">HR备注：{{ detail.hrRemark }}</p>
              </div>
              <button @click="showResume = true" class="shrink-0 px-4 py-2 border border-primary text-primary rounded-xl text-sm font-medium hover:bg-primary/5 transition-colors flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">visibility</span>
                查看简历
              </button>
            </div>
          </div>

          <!-- 处理面板 -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h3 class="text-lg font-bold text-on-surface mb-4">处理流转</h3>
            <div class="flex flex-wrap gap-3 mb-5">
              <button v-for="opt in flowOptions" :key="opt.key" @click="form.status = opt.key"
                class="px-4 py-2.5 rounded-xl border-2 text-sm font-medium transition-all"
                :class="form.status === opt.key ? 'border-primary bg-primary/5 text-primary' : 'border-surface-container-high text-on-surface hover:border-gray-300'">
                {{ opt.label }}
              </button>
            </div>

            <!-- 面试安排（状态=面试邀请时展示） -->
            <div v-if="form.status === 2" class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-5">
              <div>
                <label class="block text-xs text-on-surface-variant mb-1.5">面试时间</label>
                <input v-model="form.interviewTime" type="datetime-local" class="w-full h-11 px-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
              </div>
              <div>
                <label class="block text-xs text-on-surface-variant mb-1.5">面试地点</label>
                <input v-model="form.interviewAddress" type="text" placeholder="线下地址或视频面试链接" class="w-full h-11 px-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
              </div>
              <div>
                <label class="block text-xs text-on-surface-variant mb-1.5">面试联系人</label>
                <input v-model="form.interviewContact" type="text" placeholder="HR 姓名" class="w-full h-11 px-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
              </div>
              <div>
                <label class="block text-xs text-on-surface-variant mb-1.5">联系电话</label>
                <input v-model="form.interviewContactPhone" type="text" placeholder="联系电话" class="w-full h-11 px-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
              </div>
            </div>

            <div class="mb-5">
              <label class="block text-xs text-on-surface-variant mb-1.5">HR 备注（对候选人可见）</label>
              <textarea v-model="form.hrRemark" rows="3" placeholder="如：简历匹配度较高，请尽快确认面试时间"
                class="w-full px-3 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20"></textarea>
            </div>

            <div class="flex justify-end gap-3">
              <button @click="resetForm" class="h-11 px-6 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface font-medium hover:bg-surface-container transition-colors text-sm">重置</button>
              <button @click="submitHandle" :disabled="submitting" class="h-11 px-6 rounded-xl bg-primary text-white font-semibold hover:bg-primary-container transition-colors shadow-sm text-sm disabled:opacity-50">
                {{ submitting ? '提交中...' : '确认处理' }}
              </button>
            </div>
          </div>
        </div>

        <!-- Right: Timeline -->
        <div class="space-y-6">
          <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm">
            <h3 class="text-lg font-bold text-on-surface mb-5">流程时间线</h3>
            <div class="relative pl-6">
              <div class="absolute left-2 top-2 bottom-2 w-0.5 bg-surface-container-high"></div>
              <div v-for="(event, index) in timeline" :key="index" class="relative mb-5 last:mb-0">
                <div class="absolute -left-4 top-1 w-3 h-3 rounded-full border-2 border-white" :class="event.done ? 'bg-primary' : 'bg-surface-container-high'"></div>
                <div>
                  <p class="text-sm font-medium" :class="event.done ? 'text-on-surface' : 'text-on-surface-variant'">{{ event.stage }}</p>
                  <p v-if="event.time" class="text-xs text-on-surface-variant mt-0.5">{{ event.time }}</p>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm">
            <h3 class="text-lg font-bold text-on-surface mb-4">快速信息</h3>
            <div class="space-y-3 text-sm">
              <div class="flex justify-between">
                <span class="text-on-surface-variant">投递时间</span>
                <span class="text-on-surface font-medium">{{ formatDateTime(detail.createTime) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-on-surface-variant">最近更新</span>
                <span class="text-on-surface font-medium">{{ formatDateTime(detail.updateTime) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-on-surface-variant">应聘职位</span>
                <span class="text-on-surface font-medium">{{ detail.jobName || '-' }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-else class="bg-surface-container-lowest rounded-2xl p-12 border border-surface-container-high text-center">
        <span class="material-symbols-outlined text-5xl text-surface-container-high mb-4 block">person_search</span>
        <p class="text-sm text-on-surface-variant">请选择一条投递记录开始处理</p>
        <p v-if="applications.length === 0" class="text-xs text-on-surface-variant mt-2">暂无收到任何投递，先去发布职位吸引候选人吧</p>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>

    <!-- 候选人简历弹窗 -->
    <CandidateResumeModal :visible="showResume" :application-id="selectedId" @close="showResume = false" />
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getReceivedApplications, handleApplication } from '../api/application'
import { getCompanyInfo } from '../api/company'
import { APPLICATION_STATUS, formatDate, formatDateTime } from '../utils/format'
import CandidateResumeModal from '../components/CandidateResumeModal.vue'

const route = useRoute()
const applications = ref([])
const selectedId = ref(null)
const detail = ref(null)
const submitting = ref(false)
const toast = ref('')
const showResume = ref(false)

const flowOptions = [
  { key: 1, label: '标为已查看' },
  { key: 2, label: '面试邀请' },
  { key: 3, label: '不合适' },
  { key: 4, label: '已录用' }
]

const form = reactive({
  status: 1,
  hrRemark: '',
  interviewTime: '',
  interviewAddress: '',
  interviewContact: '',
  interviewContactPhone: ''
})

const stages = [
  { label: '投递成功' },
  { label: 'HR 已查看' },
  { label: '面试邀请' },
  { label: '录用 / 不合适' }
]

const timeline = computed(() => {
  if (!detail.value) return []
  const s = detail.value.status
  return stages.map((st, index) => {
    let done = false
    let time = ''
    if (index === 0) { done = true; time = formatDateTime(detail.value.createTime) }
    else if (index === 1) done = s >= 1
    else if (index === 2) done = s >= 2
    else if (index === 3) done = s >= 3
    if (done && index === 2 && detail.value.interviewTime) time = formatDateTime(detail.value.interviewTime)
    return { stage: st.label, done, time }
  })
})

function statusLabel(s) {
  return APPLICATION_STATUS[s]?.label || '未知'
}

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

async function loadList() {
  try {
    const info = await getCompanyInfo()
    const companyId = info && info.id
    if (!companyId) {
      showToast('请先完成企业认证')
      return
    }
    const res = await getReceivedApplications({ companyId, page: 1, size: 100 })
    applications.value = (res && res.records) || []
    // 支持从候选人列表跳转 ?id=
    const target = route.query.id ? Number(route.query.id) : null
    if (target && applications.value.some(a => a.id === target)) {
      selectedId.value = target
      await loadDetail()
    } else if (applications.value.length > 0 && selectedId.value == null) {
      selectedId.value = applications.value[0].id
      await loadDetail()
    }
  } catch (e) {
    showToast('加载投递列表失败：' + (e.message || '请重试'))
  }
}

async function loadDetail() {
  if (!selectedId.value) { detail.value = null; return }
  // 收到的投递列表（联表查询）已包含详情所需全部字段，
  // 且 /application/detail/{id} 有学生归属校验（HR 调会报"投递记录不存在"），故直接从列表取
  const found = applications.value.find(a => a.id === selectedId.value)
  if (!found) {
    showToast('投递记录不存在或不在当前列表')
    return
  }
  detail.value = found
  form.status = found.status ?? 1
  form.hrRemark = found.hrRemark || ''
  form.interviewTime = found.interviewTime ? found.interviewTime.substring(0, 16) : ''
  form.interviewAddress = found.interviewAddress || ''
  form.interviewContact = found.interviewContact || ''
  form.interviewContactPhone = found.interviewContactPhone || ''
  // 打开详情即视为已查看：待查看(0)自动标记为已查看(1)
  if (found.status === 0) {
    try {
      await handleApplication(selectedId.value, { status: 1 })
      detail.value.status = 1
      form.status = 1
      showToast('已自动标记为已查看')
      loadList()
    } catch (e) {
      console.error('标记已查看失败:', e)
    }
  }
}

function resetForm() {
  if (!detail.value) return
  form.status = detail.value.status ?? 1
  form.hrRemark = detail.value.hrRemark || ''
  form.interviewTime = detail.value.interviewTime ? detail.value.interviewTime.substring(0, 16) : ''
  form.interviewAddress = detail.value.interviewAddress || ''
  form.interviewContact = detail.value.interviewContact || ''
  form.interviewContactPhone = detail.value.interviewContactPhone || ''
}

async function submitHandle() {
  if (!selectedId.value) return
  submitting.value = true
  try {
    const payload = {
      status: form.status,
      hrRemark: form.hrRemark,
      interviewTime: form.interviewTime || null,
      interviewAddress: form.interviewAddress || null,
      interviewContact: form.interviewContact || null,
      interviewContactPhone: form.interviewContactPhone || null
    }
    await handleApplication(selectedId.value, payload)
    showToast('处理成功，状态已更新')
    await loadDetail()
    await loadList()
  } catch (e) {
    showToast(e.message || '处理失败，请重试')
  } finally {
    submitting.value = false
  }
}

onMounted(loadList)
</script>
