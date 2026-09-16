<template>
  <div class="min-h-screen bg-[var(--background)]">
    <!-- Profile Header Card -->
    <div class="max-w-6xl mx-auto px-4 md:px-6 lg:px-10 pt-6">
      <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 md:p-8">
        <div class="flex flex-col md:flex-row md:items-start gap-6">
          <!-- Left: Avatar + Info -->
          <div class="flex items-start gap-5 flex-1">
            <!-- Avatar -->
            <div class="relative shrink-0">
              <div class="w-20 h-20 md:w-24 md:h-24 rounded-2xl bg-gradient-to-br from-blue-100 to-blue-50 flex items-center justify-center overflow-hidden border-2 border-blue-100">
                <span class="text-3xl font-bold text-blue-400">{{ initialChar }}</span>
              </div>
            </div>
            <!-- Name + Tags + Bio -->
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-2 flex-wrap">
                <h1 class="text-xl md:text-2xl font-bold text-gray-900">{{ displayName }}</h1>
                <span v-if="resume.graduationYear" class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full bg-blue-50 text-blue-700 text-[11px] font-semibold border border-blue-100">
                  <span class="material-symbols-outlined text-[12px]">school</span>
                  {{ resume.graduationYear }}届毕业生
                </span>
                <span v-if="resume.major" class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full bg-purple-50 text-purple-700 text-[11px] font-semibold border border-purple-100">
                  {{ resume.major }}
                </span>
              </div>
              <p class="text-sm text-gray-500 mt-2 italic leading-relaxed">
                "{{ resume.selfIntroduction || '还没有自我介绍，去简历编辑页写一段吧' }}"
              </p>
              <!-- Job Status Dropdown -->
              <div class="mt-3">
                <div class="relative inline-block">
                  <button @click="showStatusMenu = !showStatusMenu" class="flex items-center gap-2 px-4 py-2 bg-gray-50 hover:bg-gray-100 rounded-xl text-sm font-medium text-gray-700 transition-colors border border-gray-200">
                    <span class="w-2 h-2 rounded-full bg-emerald-500 animate-pulse"></span>
                    {{ jobStatuses.find(s => s.key === currentStatus)?.label }}
                    <span class="material-symbols-outlined text-[16px] text-gray-400">expand_more</span>
                  </button>
                  <div v-if="showStatusMenu" class="absolute left-0 top-full mt-1 w-56 bg-white rounded-xl shadow-xl border border-gray-100 py-1 z-50">
                    <button v-for="status in jobStatuses" :key="status.key" @click="setJobStatus(status.key)"
                      class="w-full flex items-center gap-2.5 px-4 py-2.5 text-sm text-left hover:bg-gray-50 transition-colors"
                      :class="currentStatus === status.key ? 'text-primary font-medium' : 'text-gray-700'">
                      <span class="w-2 h-2 rounded-full" :class="currentStatus === status.key ? 'bg-primary' : 'bg-gray-300'"></span>
                      {{ status.label }}
                    </button>
                  </div>
                </div>
                <span class="text-xs text-gray-400 ml-2">求职状态仅保存在本地，用于展示</span>
              </div>
            </div>
          </div>
          <!-- Right: Resume Score + Actions -->
          <div class="flex flex-col items-center md:items-end gap-4 shrink-0">
            <div class="flex items-center gap-4">
              <!-- Resume Score Circle -->
              <div class="relative w-20 h-20">
                <svg class="w-full h-full -rotate-90" viewBox="0 0 80 80">
                  <circle cx="40" cy="40" r="34" fill="none" stroke="#e5e7eb" stroke-width="5" />
                  <circle cx="40" cy="40" r="34" fill="none" stroke="var(--primary)" stroke-width="5" stroke-linecap="round"
                    :stroke-dasharray="`${213.63 * resumeCompleteness / 100} 213.63`" />
                </svg>
                <div class="absolute inset-0 flex flex-col items-center justify-center">
                  <span class="text-lg font-bold text-primary">{{ resumeCompleteness }}%</span>
                </div>
              </div>
              <div>
                <p class="text-sm font-semibold text-gray-800">{{ completenessText }}</p>
                <p class="text-xs text-gray-400 mt-0.5">{{ completenessHint }}</p>
              </div>
            </div>
            <!-- Action Buttons -->
            <div class="flex items-center gap-3">
              <router-link :to="editResumeLink" class="flex items-center gap-2 px-5 py-2.5 bg-primary text-white rounded-xl text-sm font-semibold hover:bg-primary-container transition-colors shadow-sm">
                <span class="material-symbols-outlined text-[18px]">edit</span>
                编辑简历
              </router-link>
              <button @click="exportUnavailable" class="flex items-center gap-2 px-5 py-2.5 bg-gray-50 text-gray-700 rounded-xl text-sm font-semibold hover:bg-gray-100 transition-colors border border-gray-200">
                <span class="material-symbols-outlined text-[18px]">download</span>
                简历导出
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content Area -->
    <div class="max-w-6xl mx-auto px-4 md:px-6 lg:px-10 py-6">

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div class="lg:col-span-2 space-y-6">
          <!-- Basic Info Card -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <div class="flex items-center justify-between mb-5">
              <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2">
                <span class="material-symbols-outlined text-[var(--primary)]">person</span>
                基本信息
              </h3>
              <button @click="toggleEditBasic" class="text-sm text-[var(--primary)] hover:underline flex items-center gap-1">
                <span class="material-symbols-outlined text-base">{{ editingBasic ? 'check' : 'edit' }}</span>
                {{ editingBasic ? '保存' : '编辑' }}
              </button>
            </div>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div v-for="field in basicFields" :key="field.key">
                <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">{{ field.label }}</label>
                <input v-if="editingBasic" v-model="basicInfo[field.key]" type="text"
                  class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10 bg-[var(--surface-container-lowest)]" />
                <p v-else class="text-sm text-[var(--on-surface)] py-2">{{ basicInfo[field.key] || '未填写' }}</p>
              </div>
            </div>
          </div>

          <!-- Career Preference Card -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <div class="flex items-center justify-between mb-5">
              <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2">
                <span class="material-symbols-outlined text-[var(--primary)]">target</span>
                求职意向
              </h3>
              <router-link :to="editResumeLink" class="text-sm text-[var(--primary)] hover:underline flex items-center gap-1">
                <span class="material-symbols-outlined text-base">settings</span>
                设置偏好
              </router-link>
            </div>
            <div class="space-y-4">
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div class="bg-gray-50 rounded-xl p-4">
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望岗位</label>
                  <div class="flex flex-wrap gap-1.5">
                    <template v-if="career.jobTypes.length">
                      <span v-for="jt in career.jobTypes" :key="jt" class="px-3 py-1.5 rounded-lg bg-white text-xs font-medium text-gray-700 border border-gray-200 flex items-center gap-1">
                        <span class="material-symbols-outlined text-[14px] text-primary">work</span>
                        {{ jt }}
                      </span>
                    </template>
                    <span v-else class="text-xs text-gray-400">未填写</span>
                  </div>
                </div>
                <div class="bg-gray-50 rounded-xl p-4">
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望薪资范围</label>
                  <div class="flex items-center gap-1.5">
                    <span class="px-3 py-1.5 rounded-lg bg-white text-xs font-medium text-gray-700 border border-gray-200 flex items-center gap-1">
                      <span class="material-symbols-outlined text-[14px] text-primary">payments</span>
                      {{ career.salary }}
                    </span>
                  </div>
                </div>
              </div>
              <div>
                <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望工作地点</label>
                <div class="flex flex-wrap gap-2">
                  <template v-if="career.cities.length">
                    <span v-for="city in career.cities" :key="city" class="px-3 py-1.5 rounded-lg bg-gray-50 text-xs text-gray-700 border border-gray-200 flex items-center gap-1">
                      <span class="material-symbols-outlined text-[14px] text-primary">location_on</span>
                      {{ city }}
                    </span>
                  </template>
                  <span v-else class="text-xs text-gray-400">未填写</span>
                </div>
              </div>
              <div>
                <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望行业领域</label>
                <div class="flex flex-wrap gap-2">
                  <template v-if="career.industries.length">
                    <span v-for="ind in career.industries" :key="ind" class="px-3 py-1.5 rounded-lg bg-blue-50 text-xs text-blue-700 font-medium border border-blue-100">
                      {{ ind }}
                    </span>
                  </template>
                  <span v-else class="text-xs text-gray-400">未填写</span>
                </div>
              </div>
              <div class="flex items-center gap-3 bg-gray-50 rounded-xl p-4">
                <span class="material-symbols-outlined text-[var(--primary)] text-[20px]">calendar_today</span>
                <div>
                  <span class="text-xs text-[var(--on-surface-variant)]">求职状态</span>
                  <p class="text-sm font-medium text-[var(--on-surface)] mt-0.5">{{ workStatusText }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Education -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <div class="flex items-center justify-between mb-5">
              <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2">
                <span class="material-symbols-outlined text-[var(--primary)]">school</span>
                教育背景
              </h3>
              <router-link :to="editResumeLink" class="text-sm text-[var(--primary)] hover:underline flex items-center gap-1">
                <span class="material-symbols-outlined text-base">add_circle</span>
                去简历编辑完善
              </router-link>
            </div>
            <div class="space-y-6">
              <div v-if="resume.school" class="relative pl-8">
                <div class="absolute left-0 top-1 w-6 h-6 rounded-full bg-primary flex items-center justify-center">
                  <span class="material-symbols-outlined text-white text-[14px]">school</span>
                </div>
                <div class="pb-2">
                  <div class="flex items-start justify-between">
                    <div>
                      <div class="flex items-center gap-2 flex-wrap">
                        <h4 class="font-bold text-[var(--on-surface)] text-sm">{{ resume.school }}</h4>
                        <span v-if="educationText" class="px-2 py-0.5 rounded-full bg-blue-50 text-blue-600 text-[10px] font-semibold border border-blue-100">{{ educationText }}</span>
                      </div>
                      <p class="text-xs text-[var(--primary)] mt-1 font-medium">{{ resume.major || '专业未填写' }}</p>
                    </div>
                    <span class="text-xs text-[var(--on-surface-variant)] whitespace-nowrap">{{ resume.graduationYear ? resume.graduationYear + ' 届毕业' : '' }}</span>
                  </div>
                </div>
              </div>
              <p v-else class="text-xs text-[var(--on-surface-variant)]">暂无教育背景，去简历编辑页填写。</p>
            </div>
          </div>
        </div>

        <!-- Right Column -->
        <div class="space-y-6">
          <!-- Resume Completeness -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-5">
              <span class="material-symbols-outlined text-[var(--primary)]">description</span>
              简历完整度
            </h3>
            <div class="flex flex-col items-center">
              <div class="relative w-36 h-36">
                <svg class="w-full h-full -rotate-90" viewBox="0 0 120 120">
                  <circle cx="60" cy="60" r="52" fill="none" stroke="#e2e7ff" stroke-width="8" />
                  <circle cx="60" cy="60" r="52" fill="none" stroke="var(--primary)" stroke-width="8" stroke-linecap="round"
                    :stroke-dasharray="`${326.73 * resumeCompleteness / 100} 326.73`" />
                </svg>
                <div class="absolute inset-0 flex flex-col items-center justify-center">
                  <span class="text-3xl font-bold text-[var(--primary)]">{{ resumeCompleteness }}%</span>
                  <span class="text-xs text-[var(--on-surface-variant)]">完成度</span>
                </div>
              </div>
              <div class="w-full mt-5 space-y-2.5">
                <div v-for="item in completenessItems" :key="item.label" class="flex items-center justify-between">
                  <div class="flex items-center gap-2">
                    <span :class="['material-symbols-outlined text-lg', item.done ? 'text-emerald-500' : 'text-gray-300']">
                      {{ item.done ? 'check_circle' : 'radio_button_unchecked' }}
                    </span>
                    <span class="text-xs text-[var(--on-surface-variant)]">{{ item.label }}</span>
                  </div>
                  <span :class="['text-xs font-medium', item.done ? 'text-emerald-500' : 'text-gray-400']">{{ item.done ? '已完成' : '待完善' }}</span>
                </div>
              </div>
              <router-link :to="editResumeLink" class="mt-5 w-full py-2.5 bg-[var(--primary)] text-white rounded-xl text-sm font-medium text-center hover:bg-[var(--primary-container)] transition-colors block">
                编辑简历
              </router-link>
            </div>
          </div>

          <!-- Quick Stats -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-4">
              <span class="material-symbols-outlined text-[var(--primary)]">analytics</span>
              求职数据
            </h3>
            <div class="space-y-3">
              <div v-for="stat in stats" :key="stat.label" class="flex items-center justify-between py-2 border-b border-gray-50 last:border-0">
                <span class="text-sm text-[var(--on-surface-variant)]">{{ stat.label }}</span>
                <span class="font-semibold text-[var(--on-surface)]">{{ stat.value }}</span>
              </div>
            </div>
            <router-link to="/applications" class="text-xs text-[var(--primary)] hover:underline mt-2 inline-block">查看投递记录 →</router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-[var(--on-surface)] text-white rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useAppStore } from '../stores/app'
import { getUserInfo, updateUserInfo } from '../api/user'
import { getResumeList } from '../api/resume'
import { getApplicationList } from '../api/application'

const store = useAppStore()

const currentStatus = ref(localStorage.getItem('campus_job_status') || 'seeking')
const showStatusMenu = ref(false)
const editingBasic = ref(false)
const resumeCompleteness = ref(0)
const toast = ref('')
const resume = reactive({
  id: null, school: '', major: '', graduationYear: null, education: null,
  expectCity: '', expectIndustry: '', expectJobType: '', expectSalary: '',
  selfIntroduction: '', workStatus: null, skills: '', experiences: '', projects: ''
})

const workStatusNames = ['在校', '应届生', '往届生']

const educationText = computed(() => {
  const edu = resume.education
  if (!edu) return ''
  if (typeof edu === 'number') return ['大专', '本科', '硕士', '博士'][edu - 1] || ''
  return String(edu)
})
const workStatusText = computed(() => workStatusNames[resume.workStatus] || '在校')

const jobStatuses = [
  { key: 'seeking', label: '正在找工作' },
  { key: 'interviewing', label: '面试中' },
  { key: 'not-looking', label: '暂不考虑' },
  { key: 'employed', label: '已入职' }
]

const basicFields = [
  { key: 'username', label: '用户名' },
  { key: 'realName', label: '真实姓名' },
  { key: 'phone', label: '联系手机' },
  { key: 'email', label: '电子邮箱' }
]

const basicInfo = reactive({
  username: '', realName: '', phone: '', email: ''
})

const displayName = computed(() => store.user?.name || basicInfo.realName || basicInfo.username || '未登录')
const initialChar = computed(() => (displayName.value || '用').charAt(0))
const editResumeLink = computed(() => resume.id ? { path: '/resume/editor', query: { id: resume.id } } : '/resume/editor')

const career = reactive({
  jobTypes: [],
  salary: '面议',
  cities: [],
  industries: []
})

const completenessItems = computed(() => [
  { label: '基本信息', done: !!(basicInfo.username && basicInfo.phone) },
  { label: '教育经历', done: !!resume.school },
  { label: '项目经历', done: !!(resume.projects && resume.projects !== '[]') },
  { label: '技能特长', done: !!(resume.skills && resume.skills !== '[]') },
  { label: '自我评价', done: !!resume.selfIntroduction },
  { label: '期望岗位', done: !!(resume.expectJobType || resume.expectCity) }
])

const completenessText = computed(() => {
  const n = resumeCompleteness.value
  return n >= 90 ? '简历完善度优秀' : n >= 60 ? '简历完善度良好' : '简历待完善'
})

const completenessHint = computed(() => {
  const missing = completenessItems.value.filter(i => !i.done).map(i => i.label)
  return missing.length ? `补齐「${missing[0]}」可提升完整度` : '简历已完善'
})

const stats = reactive([
  { label: '投递简历', value: 0 },
  { label: '被查看', value: 0 },
  { label: '面试邀约', value: 0 },
  { label: '收到Offer', value: 0 }
])

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

function setJobStatus(key) {
  currentStatus.value = key
  showStatusMenu.value = false
  localStorage.setItem('campus_job_status', key)
}

function exportUnavailable() {
  showToast('简历导出功能暂未开放')
}

onMounted(async () => {
  // 用户基本信息
  try {
    const info = await getUserInfo()
    basicInfo.username = info.username || ''
    basicInfo.realName = info.realName || ''
    basicInfo.phone = info.phone || ''
    basicInfo.email = info.email || ''
  } catch (e) {
    console.error('获取用户信息失败:', e)
    basicInfo.username = store.user?.name || '用户'
  }
  // 默认简历 → 求职意向 / 教育 / 完整度
  try {
    const list = await getResumeList()
    const arr = Array.isArray(list) ? list : []
    if (arr.length > 0) {
      const def = arr.find(r => r.isDefault === 1) || arr[0]
      Object.assign(resume, {
        id: def.id, school: def.school || '', major: def.major || '',
        graduationYear: def.graduationYear || null, education: def.education,
        expectCity: def.expectCity || '', expectIndustry: def.expectIndustry || '',
        expectJobType: def.expectJobType || '', expectSalary: def.expectSalary || '',
        selfIntroduction: def.selfIntroduction || '', workStatus: def.workStatus,
        skills: def.skills || '[]', experiences: def.experiences || '[]', projects: def.projects || '[]'
      })
      const fields = [def.title, def.name, def.phone, def.email, def.school, def.major, def.selfIntroduction, def.skills, def.experiences, def.projects]
      const filled = fields.filter(Boolean).length
      resumeCompleteness.value = Math.round(filled / fields.length * 100)
      career.jobTypes = def.expectJobType ? def.expectJobType.split(/[\/，,、]/).map(s => s.trim()).filter(Boolean) : []
      career.salary = def.expectSalary || '面议'
      career.cities = def.expectCity ? def.expectCity.split(/[\/，,、]/).map(s => s.trim()).filter(Boolean) : []
      career.industries = def.expectIndustry ? def.expectIndustry.split(/[\/，,、]/).map(s => s.trim()).filter(Boolean) : []
    }
  } catch (e) {
    console.error('获取简历失败:', e)
  }
  // 投递统计
  try {
    const res = await getApplicationList({ page: 1, size: 100 })
    const records = (res && res.records) || []
    stats[0].value = res ? res.total || 0 : records.length
    stats[1].value = records.filter(a => a.status === 1).length
    stats[2].value = records.filter(a => a.status === 2).length
    stats[3].value = records.filter(a => a.status === 4).length
  } catch (e) {
    console.error('获取投递统计失败:', e)
  }
})

async function toggleEditBasic() {
  if (editingBasic.value) {
    await saveBasicInfo()
  } else {
    editingBasic.value = true
  }
}

async function saveBasicInfo() {
  try {
    await updateUserInfo({
      username: basicInfo.username,
      realName: basicInfo.realName,
      email: basicInfo.email,
      phone: basicInfo.phone
    })
    editingBasic.value = false
    showToast('基本信息已保存')
  } catch (e) {
    console.error('更新用户信息失败:', e)
    showToast(e.message || '保存失败，请重试')
  }
}
</script>
