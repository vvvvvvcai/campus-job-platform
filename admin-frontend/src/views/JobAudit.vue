<script setup lang="ts">
import { ref, computed } from 'vue'
import {
  ChevronRight,
  ChevronLeft,
  BadgeCheck,
  Search,
  Brain,
  Check,
  X,
  CheckCheck,
  Download,
  AlertTriangle,
  CheckCircle,
  HelpCircle,
  FileText,
} from 'lucide-vue-next'
import type { JobAudit, JobAuditStatus } from '@/types'
import {
  jobAudits as initialJobs,
  rejectReasons,
  jobCategoryOptions,
  cityOptions,
  salaryOptions,
} from '@/mock/jobs'

const jobs = ref<JobAudit[]>([...initialJobs])
const activeTab = ref<JobAuditStatus | 'all'>('pending')
const searchQuery = ref('')
const selectedCategory = ref('')
const selectedCity = ref('')
const selectedSalary = ref('')
const sensitiveFilter = ref(true)
const selectedJobs = ref<Set<string>>(new Set(['JOB-202410-098']))
const currentPage = ref(1)
const pageSize = 10

// Reject modal
const showRejectModal = ref(false)
const rejectTarget = ref('')
const rejectReason = ref(rejectReasons[0])
const rejectComment = ref('')

// Toast
const showToast = ref(false)
const toastMessage = ref('')
const toastSuccess = ref(true)

const tabs = computed(() => [
  { key: 'pending' as const, label: '待审核职位', count: jobs.value.filter((j) => j.status === 'pending').length, icon: true },
  { key: 'approved' as const, label: '已上架发布', count: jobs.value.filter((j) => j.status === 'approved').length },
  { key: 'offline' as const, label: '已主动下架', count: jobs.value.filter((j) => j.status === 'offline').length },
  { key: 'rejected' as const, label: '审核未通过', count: jobs.value.filter((j) => j.status === 'rejected').length },
])

const filteredJobs = computed(() => {
  let list = jobs.value
  if (activeTab.value !== 'all') {
    list = list.filter((j) => j.status === activeTab.value)
  }
  if (searchQuery.value) {
    const q = searchQuery.value.toLowerCase()
    list = list.filter(
      (j) =>
        j.title.toLowerCase().includes(q) ||
        j.companyName.toLowerCase().includes(q) ||
        j.id.toLowerCase().includes(q),
    )
  }
  return list
})

const totalFiltered = computed(() => filteredJobs.value.length)

const paginatedJobs = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredJobs.value.slice(start, start + pageSize)
})

const totalPages = computed(() => Math.max(1, Math.ceil(totalFiltered.value / pageSize)))

const selectedCount = computed(() => selectedJobs.value.size)

function toggleSelectAll() {
  if (selectedJobs.value.size === paginatedJobs.value.length) {
    selectedJobs.value.clear()
  } else {
    paginatedJobs.value.forEach((j) => selectedJobs.value.add(j.id))
  }
}

function toggleSelect(id: string) {
  if (selectedJobs.value.has(id)) {
    selectedJobs.value.delete(id)
  } else {
    selectedJobs.value.add(id)
  }
}

function isSelected(id: string) {
  return selectedJobs.value.has(id)
}

function isAllSelected() {
  return paginatedJobs.value.length > 0 && paginatedJobs.value.every((j) => selectedJobs.value.has(j.id))
}

function handleQuickPass(id: string) {
  const job = jobs.value.find((j) => j.id === id)
  if (job) {
    job.status = 'approved'
    selectedJobs.value.delete(id)
    triggerToast(`职位【${job.title}】已通过审核并上架`)
  }
}

function openRejectModal(id: string) {
  const job = jobs.value.find((j) => j.id === id)
  if (job) {
    rejectTarget.value = job.title
    showRejectModal.value = true
    rejectReason.value = rejectReasons[0]
    rejectComment.value = ''
  }
}

function confirmReject() {
  const job = jobs.value.find((j) => j.title === rejectTarget.value)
  if (job) {
    job.status = 'rejected'
    selectedJobs.value.delete(job.id)
    showRejectModal.value = false
    triggerToast(`已驳回职位【${job.title}】，整改通知已下发`, false)
  }
}

function handleBatchApprove() {
  const count = selectedCount.value
  selectedJobs.value.forEach((id) => {
    const job = jobs.value.find((j) => j.id === id)
    if (job && job.status === 'pending') {
      job.status = 'approved'
    }
  })
  selectedJobs.value.clear()
  triggerToast(`成功批量核准上架 ${count} 个合规校企职位！`)
}

function triggerToast(message: string, success = true) {
  toastMessage.value = message
  toastSuccess.value = success
  showToast.value = true
  setTimeout(() => {
    showToast.value = false
  }, 2800)
}

function getScoreColor(score: number) {
  if (score >= 90) return 'bg-tertiary/10 text-tertiary'
  if (score >= 70) return 'bg-secondary-container/20 text-secondary-container'
  return 'bg-error-container text-error'
}

function getCompanyCertIcon(cert: string) {
  switch (cert) {
    case 'certified':
      return { icon: BadgeCheck, color: 'text-tertiary' }
    case 'partner':
      return { icon: BadgeCheck, color: 'text-tertiary' }
    case 'startup':
      return { icon: HelpCircle, color: 'text-outline' }
    default:
      return { icon: HelpCircle, color: 'text-outline' }
  }
}
</script>

<template>
  <div class="flex flex-col gap-space-lg py-space-md">
    <!-- Header with breadcrumb and metrics -->
    <div class="flex flex-col md:flex-row md:items-center justify-between gap-space-md bg-surface-container-lowest p-space-lg rounded-xl shadow-sm">
      <div class="flex flex-col">
        <div class="flex items-center gap-space-xs text-outline font-label-sm text-label-sm">
          <span>校企协同治理</span>
          <ChevronRight :size="14" />
          <span>岗位安全核验中心</span>
          <ChevronRight :size="14" />
          <span class="text-primary font-medium">职位发布合规审核 (t_job)</span>
        </div>
        <div class="flex items-baseline gap-space-sm mt-space-xs">
          <h1 class="font-display-md text-display-md text-on-surface">职位信息合规审核</h1>
          <span class="font-label-sm text-label-sm text-on-surface-variant">严格执行高校毕业生就业招聘反欺诈与劳动合规标准</span>
        </div>
      </div>
      <div class="flex items-center gap-space-md">
        <div class="flex items-center gap-space-sm bg-surface-container-low px-space-md py-space-xs rounded-lg">
          <div class="w-8 h-8 rounded-full bg-secondary-container/20 flex items-center justify-center text-secondary-container">
            <BadgeCheck :size="18" />
          </div>
          <div>
            <div class="font-code-xs text-code-xs text-outline">今日已过审</div>
            <div class="font-metric-md text-metric-md text-on-surface leading-tight">46 <span class="text-[12px] font-normal text-tertiary">↑ 12%</span></div>
          </div>
        </div>
        <div class="flex items-center gap-space-sm bg-surface-container-low px-space-md py-space-xs rounded-lg">
          <div class="w-8 h-8 rounded-full bg-error-container/40 flex items-center justify-center text-error">
            <AlertTriangle :size="18" />
          </div>
          <div>
            <div class="font-code-xs text-code-xs text-outline">拦截敏感违规</div>
            <div class="font-metric-md text-metric-md text-error leading-tight">3 <span class="text-[11px] font-normal text-on-surface-variant">歧视/薪资异常</span></div>
          </div>
        </div>
      </div>
    </div>

    <!-- Tabs & Filters -->
    <div class="flex flex-col bg-surface-container-lowest rounded-xl shadow-sm overflow-hidden">
      <!-- Status Tabs -->
      <div class="flex items-center justify-between px-space-lg pt-space-md bg-surface-container-low/40">
        <div class="flex items-center gap-space-sm">
          <button
            v-for="tab in tabs"
            :key="tab.key"
            class="relative flex items-center gap-space-xs pb-space-md px-space-sm transition-all"
            :class="
              activeTab === tab.key
                ? 'font-title-md text-title-md text-primary font-semibold'
                : 'font-body-md text-body-md text-on-surface-variant hover:text-on-surface'
            "
            @click="activeTab = tab.key; currentPage = 1"
          >
            <span v-if="tab.icon" class="flex items-center gap-space-xs">
              <FileText :size="20" class="text-primary" />
            </span>
            <span>{{ tab.label }}</span>
            <span
              class="px-2 py-0.5 rounded-full font-label-sm text-label-sm"
              :class="
                tab.key === 'pending'
                  ? 'bg-secondary-container text-on-secondary-container font-bold shadow-sm'
                  : tab.key === 'rejected'
                    ? 'bg-error-container/60 text-error'
                    : 'bg-surface-container-high text-on-surface-variant'
              "
            >
              {{ tab.count }}
            </span>
            <span v-if="activeTab === tab.key" class="absolute bottom-0 left-0 right-0 h-0.5 bg-primary rounded-full"></span>
          </button>
        </div>
        <div class="flex items-center gap-space-xs pb-space-md">
          <button
            v-if="selectedCount > 0"
            class="flex items-center gap-space-xs px-space-md py-1.5 rounded-lg bg-primary text-on-primary font-label-md text-label-md hover:bg-primary-container shadow-sm transition-all"
            @click="handleBatchApprove"
          >
            <CheckCheck :size="16" />
            <span>批量通过({{ selectedCount }})</span>
          </button>
          <button class="flex items-center gap-space-xs px-space-sm py-1.5 rounded-lg bg-surface-container text-on-surface-variant font-label-sm text-label-sm hover:bg-surface-container-high transition-all">
            <Download :size="16" />
            <span>导出报表</span>
          </button>
        </div>
      </div>

      <!-- Filter Bar -->
      <div class="p-space-lg grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-12 gap-space-md items-center">
        <div class="lg:col-span-4 relative">
          <Search class="absolute left-space-sm top-1/2 -translate-y-1/2 text-outline" :size="18" />
          <input
            v-model="searchQuery"
            class="w-full h-9 pl-9 pr-space-md rounded-lg bg-surface-container-low text-on-surface placeholder:text-outline font-body-sm text-body-sm focus:outline-none focus:bg-surface-container-lowest transition-all"
            placeholder="搜索职位名称、职责关键字、企业统一信用代码..."
          />
        </div>
        <div class="lg:col-span-2">
          <select v-model="selectedCategory" class="w-full h-9 px-space-sm rounded-lg bg-surface-container-low text-on-surface font-body-sm text-body-sm focus:outline-none focus:bg-surface-container-lowest cursor-pointer">
            <option v-for="opt in jobCategoryOptions" :key="opt.value" :value="opt.value">{{ opt.label }}</option>
          </select>
        </div>
        <div class="lg:col-span-2">
          <select v-model="selectedCity" class="w-full h-9 px-space-sm rounded-lg bg-surface-container-low text-on-surface font-body-sm text-body-sm focus:outline-none focus:bg-surface-container-lowest cursor-pointer">
            <option v-for="opt in cityOptions" :key="opt.value" :value="opt.value">{{ opt.label }}</option>
          </select>
        </div>
        <div class="lg:col-span-2">
          <select v-model="selectedSalary" class="w-full h-9 px-space-sm rounded-lg bg-surface-container-low text-on-surface font-body-sm text-body-sm focus:outline-none focus:bg-surface-container-lowest cursor-pointer">
            <option v-for="opt in salaryOptions" :key="opt.value" :value="opt.value">{{ opt.label }}</option>
          </select>
        </div>
        <div class="lg:col-span-2 flex items-center justify-end">
          <label class="flex items-center gap-space-xs cursor-pointer select-none bg-surface-container-low px-space-sm py-1.5 rounded-lg hover:bg-surface-container transition-all">
            <input v-model="sensitiveFilter" type="checkbox" class="w-4 h-4 rounded text-primary focus:ring-0 accent-primary cursor-pointer" />
            <Brain :size="16" class="text-secondary" />
            <span class="font-label-sm text-label-sm text-on-surface font-medium">高薪敏感词筛查</span>
          </label>
        </div>
      </div>
    </div>

    <!-- Table -->
    <div class="w-full bg-surface-container-lowest rounded-xl shadow-sm overflow-hidden flex flex-col">
      <!-- Table Header -->
      <div class="p-space-md flex items-center justify-between bg-surface-container-low/30">
        <div class="flex items-center gap-space-sm">
          <span class="font-title-md text-title-md text-on-surface">待审职位清单</span>
          <span class="font-code-xs text-code-xs text-outline">共 {{ totalFiltered }} 个待研判岗位待确认</span>
        </div>
        <div class="flex items-center gap-space-xs text-label-sm text-outline">
          <span class="w-2 h-2 rounded-full bg-tertiary"></span>
          <span>合规分 &gt; 90 建议直接上架</span>
        </div>
      </div>

      <!-- Table Content -->
      <div class="overflow-x-auto w-full">
        <table class="w-full text-left table-auto">
          <thead>
            <tr class="bg-surface-container-low text-outline font-label-sm text-label-sm uppercase tracking-wider">
              <th class="py-3 px-space-md w-10 text-center">
                <input
                  type="checkbox"
                  class="rounded text-primary accent-primary cursor-pointer"
                  :checked="isAllSelected()"
                  @change="toggleSelectAll"
                />
              </th>
              <th class="py-3 px-space-md font-medium">职位基础信息</th>
              <th class="py-3 px-space-md font-medium">招聘企业与背调</th>
              <th class="py-3 px-space-md font-medium">薪酬待遇 / 城市</th>
              <th class="py-3 px-space-md font-medium">合规研判评分</th>
              <th class="py-3 px-space-md text-right font-medium pr-gutter">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-surface-container-low">
            <tr
              v-for="job in paginatedJobs"
              :key="job.id"
              class="cursor-pointer transition-all"
              :class="
                job.selected || selectedJobs.has(job.id)
                  ? 'bg-surface-container-high/60 hover:bg-surface-container-high/80'
                  : job.riskLevel === 'high'
                    ? 'bg-error-container/10 hover:bg-error-container/15'
                    : 'hover:bg-surface-container-low'
              "
            >
              <td class="py-space-md px-space-md text-center" @click.stop>
                <input
                  type="checkbox"
                  class="row-checkbox rounded text-primary accent-primary cursor-pointer"
                  :checked="isSelected(job.id)"
                  @change="toggleSelect(job.id)"
                />
              </td>

              <!-- Job Info -->
              <td class="py-space-md px-space-md">
                <div class="flex flex-col">
                  <div class="flex items-center gap-space-xs">
                    <span class="font-title-md text-title-md text-on-surface font-semibold hover:text-primary transition-colors">{{ job.title }}</span>
                    <span
                      class="px-1.5 py-0.5 rounded font-code-xs text-code-xs font-bold"
                      :class="job.jobType === 'intern' ? 'bg-primary/10 text-primary' : 'bg-secondary/10 text-secondary'"
                    >
                      {{ job.jobType === 'intern' ? '实习' : '校招' }}
                    </span>
                  </div>
                  <div class="flex items-center gap-space-xs mt-0.5 text-on-surface-variant font-label-sm text-label-sm">
                    <span>招募 {{ job.recruitCount }} 人</span>
                    <span>·</span>
                    <span>{{ job.education }}</span>
                    <span>·</span>
                    <span class="font-code-xs text-code-xs text-outline">{{ job.publishTime }}</span>
                  </div>
                </div>
              </td>

              <!-- Company -->
              <td class="py-space-md px-space-md">
                <div class="flex flex-col">
                  <span class="font-body-md text-body-md text-on-surface font-medium">{{ job.companyName }}</span>
                  <div class="flex items-center gap-1 mt-0.5">
                    <component :is="getCompanyCertIcon(job.companyCert).icon" :size="14" :class="getCompanyCertIcon(job.companyCert).color" />
                    <span class="font-code-xs text-code-xs" :class="getCompanyCertIcon(job.companyCert).color">{{ job.companyCertLabel }}</span>
                  </div>
                </div>
              </td>

              <!-- Salary -->
              <td class="py-space-md px-space-md">
                <div class="flex flex-col">
                  <span class="font-title-md text-title-md font-semibold" :class="job.salaryHighlight ? 'text-error' : 'text-primary'">{{ job.salary }}</span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant">{{ job.city }}</span>
                </div>
              </td>

              <!-- Score -->
              <td class="py-space-md px-space-md">
                <div class="flex items-center gap-space-xs">
                  <div class="w-8 h-8 rounded-full flex items-center justify-center font-bold font-code-xs text-code-xs" :class="getScoreColor(job.score)">
                    {{ job.score }}
                  </div>
                  <div class="flex flex-col">
                    <span class="font-label-sm text-label-sm font-medium" :class="job.riskLevel === 'high' ? 'text-error' : job.riskLevel === 'low' ? 'text-tertiary' : 'text-tertiary'">
                      {{ job.riskLabel }}
                    </span>
                    <span class="font-code-xs text-code-xs" :class="job.riskLevel === 'high' ? 'text-error' : 'text-outline'">
                      {{ job.riskDetail }}
                    </span>
                  </div>
                </div>
              </td>

              <!-- Actions -->
              <td class="py-space-md px-space-md text-right pr-gutter" @click.stop>
                <div class="flex items-center justify-end gap-1">
                  <button
                    v-if="job.status === 'pending'"
                    class="p-1.5 rounded hover:bg-surface-container text-tertiary transition-colors"
                    title="直接通过"
                    @click="handleQuickPass(job.id)"
                  >
                    <Check :size="18" />
                  </button>
                  <button
                    v-if="job.status === 'pending'"
                    class="p-1.5 rounded hover:bg-error-container/40 text-error transition-colors"
                    title="驳回"
                    @click="openRejectModal(job.id)"
                  >
                    <X :size="18" />
                  </button>
                  <span v-if="job.status === 'approved'" class="font-label-sm text-label-sm text-tertiary">已通过</span>
                  <span v-if="job.status === 'rejected'" class="font-label-sm text-label-sm text-error">已驳回</span>
                  <span v-if="job.status === 'offline'" class="font-label-sm text-label-sm text-on-surface-variant">已下架</span>
                  <ChevronRight :size="18" class="text-outline" />
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Pagination -->
      <div class="p-space-md bg-surface-container-low/40 flex items-center justify-between font-label-sm text-label-sm text-on-surface-variant">
        <span>显示第 1 至 {{ Math.min(totalFiltered, pageSize) }} 条，共 {{ totalFiltered }} 条待审</span>
        <div class="flex items-center gap-1">
          <button
            class="w-7 h-7 rounded bg-surface-container flex items-center justify-center hover:bg-surface-container-high text-on-surface transition-all disabled:opacity-40"
            :disabled="currentPage === 1"
            @click="currentPage = Math.max(1, currentPage - 1)"
          >
            <ChevronLeft :size="16" />
          </button>
          <button
            v-for="page in totalPages"
            :key="page"
            class="w-7 h-7 rounded font-bold flex items-center justify-center transition-all"
            :class="
              page === currentPage
                ? 'bg-primary text-on-primary'
                : 'bg-surface-container flex items-center justify-center hover:bg-surface-container-high text-on-surface transition-all'
            "
            @click="currentPage = page"
          >
            {{ page }}
          </button>
          <button
            class="w-7 h-7 rounded bg-surface-container flex items-center justify-center hover:bg-surface-container-high text-on-surface transition-all disabled:opacity-40"
            :disabled="currentPage === totalPages"
            @click="currentPage = Math.min(totalPages, currentPage + 1)"
          >
            <ChevronRight :size="16" />
          </button>
        </div>
      </div>
    </div>

    <!-- Reject Modal -->
    <Teleport to="body">
      <div
        v-if="showRejectModal"
        class="fixed inset-0 z-50 flex items-center justify-center bg-inverse-surface/40 backdrop-blur-sm"
      >
        <div class="bg-surface-container-lowest rounded-2xl shadow-xl w-full max-w-lg overflow-hidden flex flex-col mx-4">
          <!-- Modal Header -->
          <div class="px-space-lg py-space-md bg-error-container/30 flex items-center justify-between">
            <div class="flex items-center gap-space-xs text-error">
              <AlertTriangle :size="24" />
              <span class="font-title-lg text-title-lg font-bold">审核驳回原因判定</span>
            </div>
            <button class="text-outline hover:text-on-surface" @click="showRejectModal = false">
              <X :size="20" />
            </button>
          </div>

          <!-- Modal Body -->
          <div class="p-space-lg flex flex-col gap-space-md">
            <div>
              <span class="font-label-sm text-label-sm text-outline">驳回标的职位：</span>
              <span class="font-title-md text-title-md text-on-surface font-bold ml-1">{{ rejectTarget }}</span>
            </div>

            <!-- Rejection Reasons -->
            <div class="flex flex-col gap-space-xs">
              <span class="font-label-md text-label-md text-on-surface font-medium">请勾选违规或驳回分类：</span>
              <div class="space-y-space-xs">
                <label
                  v-for="(reason, index) in rejectReasons"
                  :key="index"
                  class="flex items-center gap-space-sm p-space-sm rounded-lg bg-surface-container-low hover:bg-surface-container cursor-pointer"
                >
                  <input
                    v-model="rejectReason"
                    type="radio"
                    :value="reason"
                    class="accent-primary"
                    name="reject_reason"
                  />
                  <span class="font-body-sm text-body-sm text-on-surface">{{ reason }}</span>
                </label>
              </div>
            </div>

            <!-- Comment -->
            <div class="flex flex-col gap-space-xs">
              <span class="font-label-md text-label-md text-on-surface font-medium">具体整改批注通知 (企业端HR可见)：</span>
              <textarea
                v-model="rejectComment"
                class="w-full p-space-sm rounded-lg bg-surface-container-low text-on-surface placeholder:text-outline font-body-sm text-body-sm focus:outline-none focus:bg-surface-container-lowest"
                placeholder="例如：请明确岗位每日基础津贴标准，删除需缴纳实习保密金等违规用语..."
                rows="3"
              ></textarea>
            </div>
          </div>

          <!-- Modal Footer -->
          <div class="px-space-lg py-space-md bg-surface-container-low flex items-center justify-end gap-space-sm">
            <button class="px-space-md py-2 rounded-lg bg-surface-container-high text-on-surface font-label-md text-label-md hover:bg-outline-variant transition-all" @click="showRejectModal = false">
              取消
            </button>
            <button class="px-space-lg py-2 rounded-lg bg-error text-on-error font-label-md text-label-md hover:bg-error/90 font-semibold shadow-sm transition-all" @click="confirmReject">
              确认下发驳回通知
            </button>
          </div>
        </div>
      </div>
    </Teleport>

    <!-- Toast -->
    <Teleport to="body">
      <div
        class="fixed bottom-6 right-6 z-50 flex items-center gap-space-sm px-space-lg py-space-md bg-inverse-surface text-inverse-on-surface rounded-xl shadow-xl transition-all duration-300"
        :class="showToast ? 'translate-y-0 opacity-100' : 'translate-y-20 opacity-0 pointer-events-none'"
      >
        <CheckCircle v-if="toastSuccess" :size="20" class="text-tertiary-fixed" />
        <AlertTriangle v-else :size="20" class="text-error" />
        <span class="font-body-md text-body-md">{{ toastMessage }}</span>
      </div>
    </Teleport>
  </div>
</template>
