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
                <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=120&h=120&fit=crop&crop=face" alt="avatar" class="w-full h-full object-cover" />
              </div>
              <button class="absolute -bottom-1 -right-1 w-7 h-7 bg-primary rounded-full flex items-center justify-center shadow-md hover:shadow-lg transition-shadow">
                <span class="material-symbols-outlined text-white text-[14px]">photo_camera</span>
              </button>
            </div>
            <!-- Name + Tags + Bio -->
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-2 flex-wrap">
                <h1 class="text-xl md:text-2xl font-bold text-gray-900">{{ store.user?.name || '林晨' }}</h1>
                <span class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full bg-blue-50 text-blue-700 text-[11px] font-semibold border border-blue-100">
                  <span class="material-symbols-outlined text-[12px]">school</span>
                  2025届毕业生
                </span>
                <span class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full bg-purple-50 text-purple-700 text-[11px] font-semibold border border-purple-100">
                  计算机科学与技术
                </span>
                <span class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full bg-green-50 text-green-700 text-[11px] font-semibold border border-green-100">
                  <span class="material-symbols-outlined text-[12px]">verified</span>
                  全国高校实名认证
                </span>
              </div>
              <p class="text-sm text-gray-500 mt-2 italic leading-relaxed">
                "期望从事全栈开发/大模型算法研发实习，热爱技术，渴望实战，代码重构偏执狂"
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
                    <button v-for="status in jobStatuses" :key="status.key" @click="currentStatus = status.key; showStatusMenu = false"
                      class="w-full flex items-center gap-2.5 px-4 py-2.5 text-sm text-left hover:bg-gray-50 transition-colors"
                      :class="currentStatus === status.key ? 'text-primary font-medium' : 'text-gray-700'">
                      <span class="w-2 h-2 rounded-full" :class="currentStatus === status.key ? 'bg-primary' : 'bg-gray-300'"></span>
                      {{ status.label }}
                    </button>
                  </div>
                </div>
                <span class="text-xs text-gray-400 ml-2">求职信箱已自动订阅匹配岗位通知</span>
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
                <p class="text-sm font-semibold text-gray-800">简历完善度优秀</p>
                <p class="text-xs text-gray-400 mt-0.5">补齐「项目经历」可冲刺100%</p>
              </div>
            </div>
            <!-- Action Buttons -->
            <div class="flex items-center gap-3">
              <router-link to="/resume/editor" class="flex items-center gap-2 px-5 py-2.5 bg-primary text-white rounded-xl text-sm font-semibold hover:bg-primary-container transition-colors shadow-sm">
                <span class="material-symbols-outlined text-[18px]">edit</span>
                编辑资料
              </router-link>
              <button class="flex items-center gap-2 px-5 py-2.5 bg-gray-50 text-gray-700 rounded-xl text-sm font-semibold hover:bg-gray-100 transition-colors border border-gray-200">
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
              <button @click="editingBasic = !editingBasic" class="text-sm text-[var(--primary)] hover:underline flex items-center gap-1">
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
              <button class="text-sm text-[var(--primary)] hover:underline flex items-center gap-1">
                <span class="material-symbols-outlined text-base">settings</span>
                设置偏好
              </button>
            </div>
            <div class="space-y-4">
              <!-- Job Type + Salary in 2 cols -->
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div class="bg-gray-50 rounded-xl p-4">
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望工作性质</label>
                  <div class="flex flex-wrap gap-1.5">
                    <span v-for="jt in career.jobTypes" :key="jt" class="px-3 py-1.5 rounded-lg bg-white text-xs font-medium text-gray-700 border border-gray-200 flex items-center gap-1">
                      <span class="material-symbols-outlined text-[14px] text-primary">work</span>
                      {{ jt }}
                    </span>
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
              <!-- Cities -->
              <div>
                <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望工作地点（支持多选）</label>
                <div class="flex flex-wrap gap-2">
                  <span v-for="city in career.cities" :key="city" class="px-3 py-1.5 rounded-lg bg-gray-50 text-xs text-gray-700 border border-gray-200 flex items-center gap-1">
                    <span class="material-symbols-outlined text-[14px] text-primary">location_on</span>
                    {{ city }}
                  </span>
                </div>
              </div>
              <!-- Industries -->
              <div>
                <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望行业领域</label>
                <div class="flex flex-wrap gap-2">
                  <span v-for="ind in career.industries" :key="ind" class="px-3 py-1.5 rounded-lg bg-blue-50 text-xs text-blue-700 font-medium border border-blue-100">
                    {{ ind }}
                  </span>
                </div>
              </div>
              <!-- Target Jobs -->
              <div>
                <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">目标职位标签</label>
                <div class="flex flex-wrap gap-2">
                  <span v-for="job in career.targetJobs" :key="job" class="px-3 py-1.5 rounded-lg bg-gray-50 text-xs text-gray-700 border border-gray-200">
                    {{ job }}
                  </span>
                </div>
              </div>
              <!-- Available Date -->
              <div class="flex items-center gap-3 bg-gray-50 rounded-xl p-4">
                <span class="material-symbols-outlined text-[var(--primary)] text-[20px]">calendar_today</span>
                <div>
                  <span class="text-xs text-[var(--on-surface-variant)]">最快到岗时间</span>
                  <p class="text-sm font-medium text-[var(--on-surface)] mt-0.5">{{ career.availableDate }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Education Timeline -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <div class="flex items-center justify-between mb-5">
              <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2">
                <span class="material-symbols-outlined text-[var(--primary)]">school</span>
                教育背景
              </h3>
              <button class="text-sm text-[var(--primary)] hover:underline flex items-center gap-1">
                <span class="material-symbols-outlined text-base">add_circle</span>
                新增阶段
              </button>
            </div>
            <div class="space-y-6">
              <div v-for="(edu, index) in education" :key="index" class="relative pl-8">
                <div v-if="index < education.length - 1" class="absolute left-3 top-8 bottom-0 w-px bg-gray-200"></div>
                <div class="absolute left-0 top-1 w-6 h-6 rounded-full bg-primary flex items-center justify-center">
                  <span class="material-symbols-outlined text-white text-[14px]">school</span>
                </div>
                <div class="pb-2">
                  <div class="flex items-start justify-between">
                    <div>
                      <div class="flex items-center gap-2 flex-wrap">
                        <h4 class="font-bold text-[var(--on-surface)] text-sm">{{ edu.school }}</h4>
                        <span v-for="tag in edu.tags" :key="tag" class="px-2 py-0.5 rounded-full bg-blue-50 text-blue-600 text-[10px] font-semibold border border-blue-100">{{ tag }}</span>
                      </div>
                      <p class="text-xs text-[var(--primary)] mt-1 font-medium">{{ edu.degree }}</p>
                    </div>
                    <span class="text-xs text-[var(--on-surface-variant)] whitespace-nowrap">{{ edu.period }}</span>
                  </div>
                  <!-- GPA + English -->
                  <div class="flex items-center gap-6 mt-3">
                    <div class="flex items-center gap-2">
                      <span class="material-symbols-outlined text-amber-500 text-[16px]">star</span>
                      <span class="text-xs text-[var(--on-surface-variant)]">GPA 学分绩点</span>
                      <span class="text-sm font-bold text-[var(--on-surface)]">{{ edu.gpa }}</span>
                    </div>
                    <div class="flex items-center gap-2">
                      <span class="material-symbols-outlined text-blue-500 text-[16px]">translate</span>
                      <span class="text-xs text-[var(--on-surface-variant)]">四六级与语言能力</span>
                      <span class="text-sm font-semibold text-[var(--on-surface)]">{{ edu.english }}</span>
                    </div>
                  </div>
                  <!-- Honors -->
                  <div v-if="edu.honors.length" class="mt-3 pl-6 space-y-1.5">
                    <div v-for="h in edu.honors" :key="h" class="flex items-start gap-1.5">
                      <span class="w-1 h-1 rounded-full bg-primary mt-1.5 shrink-0"></span>
                      <span class="text-xs text-[var(--on-surface-variant)] leading-relaxed">{{ h }}</span>
                    </div>
                  </div>
                </div>
              </div>
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
              <router-link to="/resume-editor" class="mt-5 w-full py-2.5 bg-[var(--primary)] text-white rounded-xl text-sm font-medium text-center hover:bg-[var(--primary-container)] transition-colors block">
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
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useAppStore } from '../stores/app'

const store = useAppStore()

const currentStatus = ref('seeking')
const showStatusMenu = ref(false)
const editingBasic = ref(false)
const editingCareer = ref(false)
const resumeCompleteness = ref(88)

const jobStatuses = [
  { key: 'seeking', label: '正在找工作' },
  { key: 'interviewing', label: '面试中' },
  { key: 'not-looking', label: '暂不考虑' },
  { key: 'employed', label: '已入职' }
]

const basicFields = [
  { key: 'name', label: '真实姓名' },
  { key: 'gender', label: '性别' },
  { key: 'birth', label: '出生年月' },
  { key: 'phone', label: '联系手机' },
  { key: 'email', label: '电子邮箱' },
  { key: 'wechat', label: '微信号' },
  { key: 'location', label: '所在城市' },
  { key: 'political', label: '政治面貌' },
  { key: 'hukou', label: '生源户籍地' }
]

const basicInfo = reactive({
  name: '林晨', gender: '女', birth: '2003年8月（21岁）', phone: '188****6820', email: 'linchen.cs@campus.edu.cn', wechat: 'linchen_dev', location: '北京·海淀区中关村', political: '中共党员', hukou: '浙江省·杭州市'
})

const jobTypes = ['前端开发', '后端开发', '全栈开发', '算法工程师', '产品经理', 'UI设计', '数据分析', '运维']
const cities = ['北京', '上海', '广州', '深圳', '杭州', '成都', '南京', '武汉']
const industries = ['互联网', '金融', '教育', '医疗', '新能源', '游戏', '电商', '企业服务']

const career = reactive({
  jobTypes: ['全职应届 / 实习（可提供转正）'],
  salary: '15k - 25k/月（或 350-500元/天实习）',
  cities: ['北京（海淀/朝阳）', '上海（浦东/徐汇）', '深圳（南山）', '杭州'],
  industries: ['互联网 / 大模型与生成式AI', '高端智能制造与工业互联', '金融科技（量化开发）'],
  targetJobs: ['前端开发工程师 (React/Vue/Web3D)', '全栈研发工程师 (Node.js/Go)', '大模型应用开发 / Prompt Engineer'],
  availableDate: '1周内到岗：每周可全勤实习5天 · 可持续实习6个月'
})

function toggleCareer(field, value) {
  const arr = career[field]
  const idx = arr.indexOf(value)
  if (idx >= 0) arr.splice(idx, 1)
  else arr.push(value)
}

const education = reactive([
  { school: '北京科技大学', major: '计算机科学与技术', degree: '本科 · 工学学士（卓越工程师重点培养班）', period: '2021.09 — 2025.06（2025届）', gpa: '3.82 / 4.0（专业前 5%）', honors: ['国家励志奖学金（连续两年获得，各项综合测评第一名）', 'ACM-ICPC 大学生程序设计竞赛 · 省级一等奖（主力算法与动态规划手）', '全国大学生数学建模竞赛（仿赛社）· 全国二等奖'], tags: ['985/双一流高校', '教育部直属'], english: 'CET-6 612分 | 托福 104' }
])

const completenessItems = [
  { label: '基本信息', done: true },
  { label: '教育经历', done: true },
  { label: '项目经历', done: true },
  { label: '技能证书', done: true },
  { label: '自我评价', done: false },
  { label: '期望岗位', done: true }
]

const stats = [
  { label: '投递简历', value: '23' },
  { label: '被查看', value: '18' },
  { label: '面试邀约', value: '6' },
  { label: '收到Offer', value: '2' }
]
</script>
