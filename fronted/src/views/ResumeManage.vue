<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-[1400px] mx-auto px-4 md:px-6 lg:px-10 py-8">
      <!-- Page Header -->
      <div class="flex items-center justify-between mb-6">
        <div>
          <div class="flex items-center gap-3">
            <h1 class="text-2xl md:text-3xl font-bold text-[var(--on-surface)]">我的简历档案库</h1>
            <span class="px-3 py-1 bg-blue-50 text-blue-600 text-xs font-semibold rounded-full border border-blue-100">{{ resumes.length }}/5 已创建</span>
          </div>
          <p class="text-sm text-[var(--on-surface-variant)] mt-1">支持维护多份针对性简历，根据算法、前端或央国企企精准投递，告别「千人一面」，有效提升面试邀约率。</p>
        </div>
        <div class="flex items-center gap-3">
          <button class="px-4 py-2.5 bg-white text-[var(--on-surface)] rounded-xl text-sm font-medium border border-gray-200 hover:bg-gray-50 transition-colors flex items-center gap-2 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">download</span>
            一键导出 PDF
          </button>
          <button @click="createResume" class="px-5 py-2.5 bg-[var(--primary)] text-white rounded-xl text-sm font-semibold hover:bg-[var(--primary-container)] transition-colors flex items-center gap-2 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">add</span>
            新建简历
          </button>
        </div>
      </div>

      <div class="flex gap-6">
        <!-- Left Sidebar - Resume List -->
        <div class="w-[380px] shrink-0 space-y-4">
          <!-- Section Header -->
          <div class="flex items-center justify-between">
            <span class="text-sm font-bold text-[var(--on-surface)]">简历列表</span>
            <span class="text-xs text-[var(--on-surface-variant)]">点击卡片切换右侧预览</span>
          </div>

          <!-- Resume Cards -->
          <div v-for="resume in resumes" :key="resume.id"
            @click="selectResume(resume)"
            :class="['rounded-2xl border p-5 cursor-pointer transition-all relative',
              selectedResume?.id === resume.id
                ? 'bg-white border-[var(--primary)] ring-2 ring-[var(--primary)]/10 shadow-sm'
                : 'bg-white border-gray-100 hover:border-gray-200 shadow-sm hover:shadow']">
            <!-- Selected Badge -->
            <div v-if="selectedResume?.id === resume.id" class="absolute -top-2 -right-2 px-3 py-1 bg-[var(--primary)] text-white text-[10px] font-bold rounded-full shadow-sm">
              使用中
            </div>
            <!-- Tags -->
            <div class="flex items-center gap-2 mb-3">
              <span v-for="tag in resume.tags" :key="tag.label"
                :class="['px-2.5 py-0.5 rounded-full text-[10px] font-semibold border', tag.class]">
                {{ tag.label }}
              </span>
            </div>
            <!-- Title -->
            <h3 class="font-bold text-[var(--on-surface)] text-sm mb-3">{{ resume.title }}</h3>
            <!-- Completeness Bar -->
            <div class="flex items-center justify-between mb-2">
              <span class="text-[11px] text-[var(--on-surface-variant)]">简历完整度</span>
              <span class="text-xs font-bold" :class="resume.completeness >= 90 ? 'text-[var(--primary)]' : resume.completeness >= 70 ? 'text-emerald-500' : 'text-amber-500'">{{ resume.completeness }}%</span>
            </div>
            <div class="h-1.5 bg-gray-100 rounded-full overflow-hidden mb-3">
              <div :class="['h-full rounded-full transition-all',
                resume.completeness >= 90 ? 'bg-[var(--primary)]' : resume.completeness >= 70 ? 'bg-emerald-500' : 'bg-amber-500']"
                :style="{ width: resume.completeness + '%' }"></div>
            </div>
            <!-- Date -->
            <div class="flex items-center justify-between mb-3">
              <span class="text-[11px] text-[var(--on-surface-variant)] flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">schedule</span>
                {{ resume.updatedAt }}
              </span>
              <span v-if="resume.isDefault" class="text-[11px] text-[var(--primary)] flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">check_circle</span>
                默认简历
              </span>
            </div>
            <!-- Actions -->
            <div class="flex items-center justify-between pt-3 border-t border-gray-50">
              <span v-if="selectedResume?.id === resume.id" class="text-[11px] text-[var(--primary)] font-medium flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">visibility</span>
                当前显示中
              </span>
              <div class="flex items-center gap-3 ml-auto">
                <router-link :to="{ path: '/resume/editor', query: { id: resume.id } }" class="text-[11px] text-[var(--on-surface-variant)] hover:text-[var(--primary)] flex items-center gap-1 transition-colors">
                  <span class="material-symbols-outlined text-[14px]">edit</span>
                  编辑内容
                </router-link>
                <button @click="setDefault(resume.id)" v-if="!resume.isDefault" class="text-[11px] text-[var(--on-surface-variant)] hover:text-[var(--primary)] flex items-center gap-1 transition-colors">
                  <span class="material-symbols-outlined text-[14px]">star</span>
                  设为默认
                </button>
                <button @click="removeResume(resume.id)" v-if="!resume.isDefault" class="text-[11px] text-[var(--on-surface-variant)] hover:text-red-500 flex items-center gap-1 transition-colors">
                  <span class="material-symbols-outlined text-[14px]">delete_outline</span>
                </button>
              </div>
            </div>
          </div>

          <!-- New Resume Card -->
          <button @click="createResume" class="w-full rounded-2xl border-2 border-dashed border-gray-200 p-6 flex flex-col items-center gap-2 hover:border-[var(--primary)]/30 hover:bg-blue-50/30 transition-all">
            <span class="material-symbols-outlined text-3xl text-gray-300">add_circle</span>
            <span class="text-sm font-medium text-[var(--on-surface-variant)]">新建简历版本</span>
            <span class="text-[11px] text-gray-400">还可创建 {{ Math.max(0, 5 - resumes.length) }} 份专用简历（上限 5 份）</span>
          </button>

          <!-- Advisor Tip Box -->
          <div class="bg-blue-50/60 rounded-2xl border border-blue-100 p-5">
            <div class="flex items-center gap-2 mb-2">
              <span class="material-symbols-outlined text-[var(--primary)] text-lg">lightbulb</span>
              <span class="text-sm font-bold text-[var(--on-surface)]">就业导师建议</span>
            </div>
            <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed">算法类岗位重点考察顶会论文、竞赛名次与模型调优实际业务指标；央国企则注重专业课程成绩排位与综合素质认证。建议保持 2 份以上差异化版本！</p>
          </div>
        </div>

        <!-- Right Panel - Resume Preview -->
        <div class="flex-1 min-w-0">
          <div v-if="selectedResume" class="bg-white rounded-2xl border border-gray-100 shadow-sm">
            <!-- Preview Header -->
            <div class="flex items-center justify-between px-6 py-4 border-b border-gray-100">
              <div class="flex items-center gap-3">
                <span class="w-2 h-2 rounded-full bg-[var(--primary)]"></span>
                <span class="text-sm font-medium text-[var(--on-surface)]">实时高清真排版预览（标准 A4 · 紧凑单页）</span>
              </div>
              <div class="flex items-center gap-2 text-xs text-[var(--on-surface-variant)]">
                <span>缩放：100%</span>
              </div>
            </div>

            <!-- Resume Content -->
            <div class="p-8 max-h-[700px] overflow-y-auto">
              <div v-if="detailLoading" class="py-20 text-center text-sm text-[var(--on-surface-variant)]">
                <span class="material-symbols-outlined text-4xl text-gray-200 mb-3 block">hourglass_top</span>
                正在加载简历内容…
              </div>

              <div v-else class="max-w-3xl mx-auto">
                <!-- Profile Header -->
                <div class="flex items-start gap-6 mb-6 pb-6 border-b border-gray-200">
                  <!-- Avatar -->
                  <div class="w-20 h-20 rounded-xl bg-gradient-to-br from-blue-100 to-blue-50 flex items-center justify-center border-2 border-blue-100 shrink-0 overflow-hidden">
                    <span class="text-2xl text-blue-300 font-bold">{{ initialChar }}</span>
                  </div>
                  <!-- Info -->
                  <div class="flex-1">
                    <div class="flex items-center gap-3 mb-1">
                      <h2 class="text-xl font-bold text-[var(--on-surface)]">{{ d.name || '未填写姓名' }}</h2>
                      <span class="px-2.5 py-0.5 bg-emerald-50 text-emerald-700 text-[10px] font-semibold rounded-full border border-emerald-100">{{ workStatusText }}·积极求职中</span>
                    </div>
                    <p class="text-sm font-semibold text-[var(--primary)] mb-2">求职意向：{{ d.expectJobType || d.expectIndustry || '未填写' }}</p>
                    <p class="text-xs text-[var(--on-surface-variant)] mb-2">{{ schoolLine }}</p>
                    <div class="flex items-center gap-4 text-xs text-[var(--on-surface-variant)] flex-wrap">
                      <span v-if="d.phone" class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px]">phone</span> {{ maskPhone(d.phone) }}</span>
                      <span v-if="d.email" class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px]">email</span> {{ d.email }}</span>
                      <span v-if="d.expectCity" class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px]">location_on</span> {{ d.expectCity }}</span>
                    </div>
                  </div>
                  <!-- Score Circle -->
                  <div class="relative w-16 h-16 shrink-0">
                    <svg class="w-full h-full -rotate-90" viewBox="0 0 64 64">
                      <circle cx="32" cy="32" r="28" fill="none" stroke="#e5e7eb" stroke-width="4" />
                      <circle cx="32" cy="32" r="28" fill="none" stroke="var(--primary)" stroke-width="4" stroke-linecap="round"
                        :stroke-dasharray="`${175.93 * (selectedResume.completeness || 0) / 100} 175.93`" />
                    </svg>
                    <div class="absolute inset-0 flex flex-col items-center justify-center">
                      <span class="text-lg font-bold text-[var(--primary)]">{{ selectedResume.completeness || 0 }}</span>
                    </div>
                    <p class="text-[9px] text-[var(--on-surface-variant)] text-center -mt-0.5">简历完整度</p>
                  </div>
                </div>

                <!-- Summary Bar -->
                <div class="grid grid-cols-3 gap-4 bg-gray-50 rounded-xl p-4 mb-8">
                  <div>
                    <span class="text-[11px] text-[var(--on-surface-variant)]">求职状态</span>
                    <p class="text-xs font-semibold text-[var(--on-surface)] mt-1">{{ workStatusText }}{{ d.graduationYear ? '·' + d.graduationYear + '届' : '' }}</p>
                  </div>
                  <div>
                    <span class="text-[11px] text-[var(--on-surface-variant)]">期望城市</span>
                    <p class="text-xs font-semibold text-[var(--on-surface)] mt-1">{{ d.expectCity || '未填写' }}</p>
                  </div>
                  <div>
                    <span class="text-[11px] text-[var(--on-surface-variant)]">期望薪资范围</span>
                    <p class="text-xs font-semibold text-[var(--primary)] mt-1">{{ d.expectSalary || '未填写（可面议）' }}</p>
                  </div>
                </div>

                <!-- Education Background -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    教育背景
                  </h3>
                  <div v-if="d.school || d.major || d.graduationYear" class="flex items-start justify-between">
                    <div>
                      <div class="flex items-center gap-2">
                        <span class="text-sm font-bold text-[var(--on-surface)]">{{ d.school || '未填写院校' }}</span>
                        <span v-if="educationText" class="px-2 py-0.5 bg-blue-50 text-blue-600 text-[10px] font-semibold rounded border border-blue-100">{{ educationText }}</span>
                      </div>
                      <p v-if="d.major" class="text-xs text-[var(--on-surface-variant)] mt-1">专业：{{ d.major }}</p>
                    </div>
                    <span v-if="d.graduationYear" class="text-xs text-[var(--on-surface-variant)]">{{ d.graduationYear }} 届毕业</span>
                  </div>
                  <p v-else class="text-xs text-[var(--on-surface-variant)]">暂未填写教育背景，点击左侧「编辑内容」完善。</p>
                </div>

                <!-- Professional Skills -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    专业技能与特长
                  </h3>
                  <div v-if="skills.length" class="flex flex-wrap gap-2">
                    <span v-for="skill in skills" :key="skill"
                      class="px-3 py-1.5 bg-blue-50 text-blue-700 text-xs font-medium rounded-lg border border-blue-100">
                      {{ skill }}
                    </span>
                  </div>
                  <p v-else class="text-xs text-[var(--on-surface-variant)]">暂未填写技能特长。</p>
                </div>

                <!-- Work / Internship Experience -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    实习与工作经历
                  </h3>
                  <div v-if="workExps.length" class="space-y-6">
                    <div v-for="(exp, i) in workExps" :key="i" :class="{ 'pt-4 border-t border-gray-50': i > 0 }">
                      <div class="flex items-start justify-between mb-2">
                        <div class="flex items-center gap-2">
                          <span class="text-sm font-bold text-[var(--on-surface)]">{{ exp.company || '未填写公司' }}</span>
                          <span v-if="exp.position" class="px-2 py-0.5 bg-emerald-50 text-emerald-700 text-[10px] font-semibold rounded border border-emerald-100">{{ exp.position }}</span>
                        </div>
                        <span v-if="exp.period" class="text-xs text-[var(--on-surface-variant)]">{{ exp.period }}</span>
                      </div>
                      <p v-if="exp.desc" class="text-xs text-[var(--on-surface-variant)] leading-relaxed">{{ exp.desc }}</p>
                    </div>
                  </div>
                  <p v-else class="text-xs text-[var(--on-surface-variant)]">暂未填写实习/工作经历。</p>
                </div>

                <!-- Project Experience -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    项目经历
                  </h3>
                  <div v-if="projectExps.length" class="space-y-6">
                    <div v-for="(proj, i) in projectExps" :key="i" :class="{ 'pt-4 border-t border-gray-50': i > 0 }">
                      <div class="flex items-start justify-between mb-2">
                        <span class="text-sm font-bold text-[var(--on-surface)]">{{ proj.name || '未命名项目' }}</span>
                        <div class="text-right shrink-0 ml-4">
                          <span v-if="proj.role" class="px-2 py-0.5 bg-amber-50 text-amber-700 text-[10px] font-semibold rounded border border-amber-100">{{ proj.role }}</span>
                          <span v-if="proj.period" class="text-xs text-[var(--on-surface-variant)] ml-2">{{ proj.period }}</span>
                        </div>
                      </div>
                      <p v-if="proj.desc" class="text-xs text-[var(--on-surface-variant)] leading-relaxed">{{ proj.desc }}</p>
                    </div>
                  </div>
                  <p v-else class="text-xs text-[var(--on-surface-variant)]">暂未填写项目经历。</p>
                </div>

                <!-- Awards & Certifications -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    荣誉与资质
                  </h3>
                  <div v-if="awardItems.length" class="grid grid-cols-2 gap-3">
                    <div v-for="(a, i) in awardItems" :key="i" class="flex items-center gap-3 bg-gray-50 rounded-xl p-3">
                      <span class="text-lg">🏆</span>
                      <p class="text-xs font-semibold text-[var(--on-surface)]">{{ a }}</p>
                    </div>
                  </div>
                  <p v-else class="text-xs text-[var(--on-surface-variant)]">暂未填写荣誉与资质。</p>
                </div>

                <!-- Self Introduction -->
                <div>
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    自我介绍
                  </h3>
                  <p v-if="d.selfIntroduction" class="text-xs text-[var(--on-surface-variant)] leading-relaxed whitespace-pre-wrap">{{ d.selfIntroduction }}</p>
                  <p v-else class="text-xs text-[var(--on-surface-variant)]">暂未填写自我介绍。</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Empty State -->
          <div v-else class="bg-white rounded-2xl border border-gray-100 shadow-sm p-12 text-center">
            <span class="material-symbols-outlined text-5xl text-gray-200 mb-4 block">description</span>
            <p class="text-sm text-[var(--on-surface-variant)]">选择一份简历查看预览</p>
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
import { getResumeList, getResumeInfo, deleteResume, setDefaultResume } from '../api/resume'

const router = useRouter()
const store = useAppStore()
const selectedResume = ref(null)
const resumes = ref([])
const loading = ref(false)
const detailLoading = ref(false)
const resumeDetail = ref(null)

const workStatusNames = ['在校', '应届生', '往届生']

const d = computed(() => resumeDetail.value || {})

const initialChar = computed(() => (d.value.name || '简').charAt(0))

const educationText = computed(() => {
  const edu = d.value.education
  if (!edu) return ''
  if (typeof edu === 'number') return ['大专', '本科', '硕士', '博士'][edu - 1] || ''
  return String(edu)
})

const workStatusText = computed(() => workStatusNames[d.value.workStatus] || '在校')

const schoolLine = computed(() => {
  const parts = []
  if (d.value.school) parts.push(d.value.school)
  if (d.value.major) parts.push(d.value.major)
  if (d.value.graduationYear) parts.push(d.value.graduationYear + '届')
  return parts.length ? parts.join(' · ') : '暂未填写教育信息'
})

function parseJsonArray(field) {
  if (Array.isArray(field)) return field
  if (typeof field === 'string') {
    try {
      const v = JSON.parse(field)
      return Array.isArray(v) ? v : []
    } catch { /* 非JSON字符串按纯文本处理 */ }
  }
  return []
}

function normalizeExp(raw) {
  if (typeof raw === 'string') return { company: '', position: '', period: '', desc: raw }
  if (raw && typeof raw === 'object') {
    return {
      company: raw.company || raw.companyName || raw.name || '',
      position: raw.position || raw.title || raw.job || '',
      period: raw.period || raw.time || raw.duration || (raw.start && raw.end ? raw.start + ' — ' + raw.end : (raw.start || '')),
      desc: raw.desc || raw.description || raw.content || raw.detail || ''
    }
  }
  return null
}

const skills = computed(() => {
  const v = parseJsonArray(d.value.skills)
  return v.map(s => typeof s === 'string' ? s : (s && s.name ? s.name : String(s))).filter(Boolean)
})

const workExps = computed(() => parseJsonArray(d.value.experiences).map(normalizeExp).filter(Boolean))

const projectExps = computed(() => parseJsonArray(d.value.projects).map(normalizeExp).map(e => ({
  name: e.company || e.desc,
  role: e.position,
  period: e.period,
  desc: e.company ? e.desc : ''
})).filter(e => e.name))

const awardItems = computed(() => parseJsonArray(d.value.awards).map(a =>
  typeof a === 'string' ? a : (a.name || a.title || String(a))
).filter(Boolean))

function maskPhone(phone) {
  const p = String(phone)
  if (p.length >= 7) return p.slice(0, 3) + '****' + p.slice(-4)
  return p
}

onMounted(async () => {
  if (!store.isLoggedIn) return
  loading.value = true
  try {
    const res = await getResumeList()
    if (Array.isArray(res)) {
      resumes.value = res.map(r => ({
        id: r.id,
        title: r.title || '未命名简历',
        updatedAt: r.updateTime ? r.updateTime.substring(0, 10) : (r.createTime ? r.createTime.substring(0, 10) : ''),
        completeness: calcCompleteness(r),
        isDefault: r.isDefault === 1,
        tags: r.isDefault === 1
          ? [{ label: '默认投递', class: 'bg-[var(--primary)]/10 text-[var(--primary)] border-[var(--primary)]/20' }]
          : [{ label: r.status === 1 ? '已完成' : '草稿', class: 'bg-gray-100 text-gray-500 border-gray-200' }]
      }))
      if (resumes.value.length > 0) await selectResume(resumes.value[0])
    }
  } catch (e) {
    console.error('获取简历列表失败:', e)
  } finally {
    loading.value = false
  }
})

function calcCompleteness(r) {
  let filled = 0
  const fields = [r.title, r.name, r.phone, r.email, r.school, r.major, r.selfIntroduction, r.skills, r.experiences, r.projects]
  fields.forEach(f => { if (f) filled++ })
  return Math.round(filled / fields.length * 100)
}

async function selectResume(resume) {
  selectedResume.value = resume
  resumeDetail.value = null
  if (!resume || !resume.id) return
  detailLoading.value = true
  try {
    const detail = await getResumeInfo(resume.id)
    resumeDetail.value = detail
  } catch (e) {
    console.error('获取简历详情失败:', e)
    resumeDetail.value = null
  } finally {
    detailLoading.value = false
  }
}

function createResume() {
  router.push('/resume/editor')
}

async function removeResume(id) {
  try {
    await deleteResume(id)
    resumes.value = resumes.value.filter(r => r.id !== id)
    if (selectedResume.value?.id === id) {
      selectedResume.value = resumes.value[0] || null
      if (selectedResume.value) await selectResume(selectedResume.value)
    }
  } catch (e) {
    console.error('删除简历失败:', e)
  }
}

async function setDefault(id) {
  try {
    await setDefaultResume(id)
    resumes.value.forEach(r => r.isDefault = r.id === id)
  } catch (e) {
    console.error('设置默认简历失败:', e)
  }
}
</script>
