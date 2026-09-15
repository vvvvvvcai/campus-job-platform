<script setup lang="ts">
import { ref, computed } from 'vue'
import {
  Shield,
  Search,
  ChevronDown,
  Calendar,
  Filter,
  RefreshCw,
  FileText,
  BadgeCheck,
  AlertTriangle,
  CheckCircle,
  ChevronLeft,
  ChevronRight,
  ClipboardCheck,
  X,
  SlidersHorizontal,
} from 'lucide-vue-next'
import type { AuditStatus, Enterprise, EnterpriseDetail } from '@/types'
import { enterprises as initialEnterprises, enterpriseDetails, industryOptions } from '@/mock/enterprises'

const enterprises = ref<Enterprise[]>([...initialEnterprises])
const activeTab = ref<AuditStatus | 'all'>('all')
const searchQuery = ref('未来智能科技')
const selectedIndustry = ref('it')
const dateRange = ref('2024-10-18 ~ 2024-10-25')
const currentPage = ref(1)
const pageSize = 10

const selectedEnterprise = ref<EnterpriseDetail | null>(enterpriseDetails[1])

const tabs = computed(() => [
  { key: 'all' as const, label: '全部企业', count: enterprises.value.length },
  { key: 'pending' as const, label: '待审核', count: enterprises.value.filter((e) => e.status === 'pending').length, active: true },
  { key: 'approved' as const, label: '已认证', count: enterprises.value.filter((e) => e.status === 'approved').length },
  { key: 'rejected' as const, label: '已驳回', count: enterprises.value.filter((e) => e.status === 'rejected').length },
])

const filteredEnterprises = computed(() => {
  let list = enterprises.value
  if (activeTab.value !== 'all') {
    list = list.filter((e) => e.status === activeTab.value)
  }
  if (searchQuery.value) {
    const q = searchQuery.value.toLowerCase()
    list = list.filter(
      (e) => e.name.toLowerCase().includes(q) || e.creditCode.toLowerCase().includes(q),
    )
  }
  return list
})

const totalFiltered = computed(() => filteredEnterprises.value.length)

const paginatedEnterprises = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredEnterprises.value.slice(start, start + pageSize)
})

const totalPages = computed(() => Math.max(1, Math.ceil(totalFiltered.value / pageSize)))

function selectEnterprise(detail: EnterpriseDetail) {
  selectedEnterprise.value = detail
}

function handleApprove(id: number) {
  const ent = enterprises.value.find((e) => e.id === id)
  if (ent) {
    ent.status = 'approved'
    ent.waitTime = '管理员通过'
    ent.licenseOcr = 'matched'
    ent.licenseTag = '特级互信'
  }
}

function handleReject(id: number) {
  const ent = enterprises.value.find((e) => e.id === id)
  if (ent) {
    ent.status = 'rejected'
    ent.waitTime = '已驳回并通知'
  }
}

function resetFilters() {
  searchQuery.value = ''
  selectedIndustry.value = ''
  dateRange.value = ''
  currentPage.value = 1
}

function getStatusClass(status: AuditStatus) {
  switch (status) {
    case 'pending':
      return 'bg-secondary-fixed text-on-secondary-fixed-variant'
    case 'approved':
      return 'bg-tertiary-fixed/30 text-tertiary-container'
    case 'rejected':
      return 'bg-error-container text-on-error-container'
  }
}

function getStatusDot(status: AuditStatus) {
  switch (status) {
    case 'pending':
      return 'bg-secondary'
    case 'approved':
      return 'bg-tertiary'
    case 'rejected':
      return 'bg-error'
  }
}

function getStatusLabel(status: AuditStatus) {
  switch (status) {
    case 'pending':
      return '待审核'
    case 'approved':
      return '已认证'
    case 'rejected':
      return '已驳回'
  }
}

function getOcrIcon(status: Enterprise['licenseOcr']) {
  switch (status) {
    case 'matched':
      return { icon: FileText, bg: 'bg-surface-container-high', color: 'text-primary' }
    case 'unverified':
      return { icon: FileText, bg: 'bg-surface-container-high', color: 'text-primary' }
    case 'expired':
      return { icon: AlertTriangle, bg: 'bg-error-container', color: 'text-error' }
  }
}

function getOcrTagClass(status: Enterprise['licenseOcr']) {
  switch (status) {
    case 'matched':
      return 'bg-tertiary-fixed/30 text-tertiary-container'
    case 'unverified':
      return 'bg-surface-container-highest text-on-surface-variant'
    case 'expired':
      return 'bg-error-container text-on-error-container'
  }
}

function getActionStyle(status: AuditStatus) {
  switch (status) {
    case 'pending':
      return 'bg-primary text-on-primary hover:bg-primary-container shadow-sm'
    case 'approved':
      return 'text-on-surface-variant hover:text-primary'
    case 'rejected':
      return 'text-on-surface-variant hover:text-error'
  }
}

function getActionLabel(status: AuditStatus) {
  switch (status) {
    case 'pending':
      return '审阅'
    case 'approved':
      return '档案'
    case 'rejected':
      return '原因'
  }
}
</script>

<template>
  <div class="py-space-lg">
    <!-- SLA Banner -->
    <div class="mb-space-lg flex items-center justify-between rounded-xl bg-surface-container-low px-space-lg py-space-sm shadow-sm">
      <div class="flex items-center gap-space-md">
        <div class="flex h-8 w-8 items-center justify-center rounded-lg bg-primary/10 text-primary">
          <Shield :size="20" />
        </div>
        <div>
          <p class="font-title-md text-title-md text-on-surface">企业资质认证审批池 (P0)</p>
          <p class="font-body-sm text-body-sm text-on-surface-variant">
            依据校企合作法务准则：营业执照真实性与法人信用记录需在
            <span class="font-code-xs text-code-xs font-semibold text-primary">24h 内</span> 办结。
          </p>
        </div>
      </div>
      <div class="flex items-center gap-space-sm">
        <span class="inline-flex items-center gap-1.5 rounded-full bg-surface-container-highest px-3 py-1 font-label-sm text-label-sm text-on-surface">
          <span class="h-2 w-2 rounded-full bg-tertiary"></span>
          OCR自动核验通道正常
        </span>
        <button class="flex h-8 items-center gap-1 rounded-lg bg-surface-container-high px-3 font-label-sm text-label-sm text-on-surface hover:bg-surface-variant transition-colors">
          <SlidersHorizontal :size="16" />
          规则配置
        </button>
      </div>
    </div>

    <!-- Tabs & Filters -->
    <div class="mb-space-lg flex flex-col gap-space-md rounded-xl bg-surface-container-lowest p-space-lg shadow-sm">
      <!-- Status Tabs -->
      <div class="flex flex-wrap items-center justify-between gap-space-md pb-space-xs">
        <div class="flex items-center gap-space-xs rounded-xl bg-surface-container-low p-1">
          <button
            v-for="tab in tabs"
            :key="tab.key"
            class="group flex items-center gap-2 rounded-lg px-space-md py-1.5 font-label-md text-label-md transition-all"
            :class="
              activeTab === tab.key
                ? 'bg-primary-container text-on-primary shadow-sm'
                : 'text-on-surface-variant hover:text-on-surface'
            "
            @click="activeTab = tab.key; currentPage = 1"
          >
            <span v-if="tab.active && activeTab !== tab.key" class="flex h-2 w-2 relative">
              <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-tertiary-fixed opacity-75"></span>
              <span class="relative inline-flex rounded-full h-2 w-2 bg-tertiary-fixed"></span>
            </span>
            <span>{{ tab.label }}</span>
            <span
              class="rounded-full px-2 py-0.5 font-code-xs text-code-xs"
              :class="
                tab.key === 'pending' && activeTab !== tab.key
                  ? 'bg-error text-on-error font-bold'
                  : 'bg-surface-container-high text-on-surface-variant group-hover:bg-surface-container-highest'
              "
            >
              {{ tab.count }}
            </span>
          </button>
        </div>
        <div class="flex items-center gap-space-sm text-on-surface-variant">
          <span class="font-code-xs text-code-xs">数据同步于 10:42:18</span>
          <button class="flex h-8 w-8 items-center justify-center rounded-lg bg-surface-container hover:bg-surface-container-high text-on-surface transition-colors" title="刷新列表">
            <RefreshCw :size="18" />
          </button>
        </div>
      </div>

      <!-- Filter Fields -->
      <div class="grid grid-cols-1 md:grid-cols-12 gap-space-sm items-center pt-space-xs">
        <div class="md:col-span-4 relative">
          <Search class="absolute left-3 top-1/2 -translate-y-1/2 text-outline" :size="18" />
          <input
            v-model="searchQuery"
            class="w-full h-9 pl-9 pr-3 rounded-lg bg-surface-container-low text-on-surface font-body-sm text-body-sm placeholder:text-outline focus:outline-none focus:bg-surface-container-lowest transition-all"
            placeholder="输入企业名称 / 18位统一社会信用代码..."
          />
        </div>
        <div class="md:col-span-3">
          <div class="relative">
            <select
              v-model="selectedIndustry"
              class="w-full h-9 px-3 pr-8 rounded-lg bg-surface-container-low text-on-surface font-body-sm text-body-sm appearance-none focus:outline-none cursor-pointer"
            >
              <option v-for="opt in industryOptions" :key="opt.value" :value="opt.value">{{ opt.label }}</option>
            </select>
            <ChevronDown class="absolute right-2.5 top-1/2 -translate-y-1/2 pointer-events-none text-outline" :size="18" />
          </div>
        </div>
        <div class="md:col-span-3">
          <div class="relative">
            <Calendar class="absolute left-3 top-1/2 -translate-y-1/2 text-outline" :size="18" />
            <input
              v-model="dateRange"
              class="w-full h-9 pl-9 pr-3 rounded-lg bg-surface-container-low text-on-surface font-body-sm text-body-sm cursor-pointer"
              readonly
            />
          </div>
        </div>
        <div class="md:col-span-2 flex items-center justify-end gap-space-xs">
          <button class="h-9 px-3.5 rounded-lg bg-surface-container text-on-surface-variant font-label-sm text-label-sm hover:bg-surface-container-high transition-colors" @click="resetFilters">
            重置
          </button>
          <button class="h-9 px-4 rounded-lg bg-primary text-on-primary font-label-sm text-label-sm hover:bg-primary-container shadow-sm flex items-center gap-1 transition-colors">
            <Filter :size="16" />
            筛选
          </button>
        </div>
      </div>
    </div>

    <!-- Main Content: Table + Detail Drawer -->
    <div class="grid grid-cols-1 xl:grid-cols-12 gap-space-lg items-start">
      <!-- Left: Table -->
      <div class="xl:col-span-7 flex flex-col rounded-xl bg-surface-container-lowest shadow-sm overflow-hidden">
        <div class="flex items-center justify-between px-space-lg py-space-md bg-surface-container-low">
          <div class="flex items-center gap-2">
            <span class="font-title-md text-title-md text-on-surface">待复核企业序列</span>
            <span class="rounded-full bg-error/10 px-2 py-0.5 font-label-sm text-label-sm font-semibold text-error">优先处置队列</span>
          </div>
          <div class="flex items-center gap-2 text-on-surface-variant font-body-sm text-body-sm">
            <span>共找到 <strong class="text-on-surface">{{ totalFiltered }}</strong> 条待审企业</span>
          </div>
        </div>

        <div class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="bg-surface-container-low text-outline font-label-sm text-label-sm uppercase tracking-wider">
                <th class="py-space-sm px-space-md pl-space-lg">企业全称 / 统一代码</th>
                <th class="py-space-sm px-space-md">行业 / 规模</th>
                <th class="py-space-sm px-space-md">法定代表人</th>
                <th class="py-space-sm px-space-md">营业执照</th>
                <th class="py-space-sm px-space-md">提交时间</th>
                <th class="py-space-sm px-space-md">状态</th>
                <th class="py-space-sm px-space-md pr-space-lg text-right">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="ent in paginatedEnterprises"
                :key="ent.id"
                class="hover:bg-surface-container-low transition-colors cursor-pointer relative"
                :class="selectedEnterprise?.enterprise.id === ent.id ? 'bg-surface-container/60' : ''"
                @click="selectEnterprise(enterpriseDetails[ent.id]!)"
              >
                <!-- Active accent -->
                <div
                  v-if="selectedEnterprise?.enterprise.id === ent.id"
                  class="absolute left-0 top-0 bottom-0 w-1 bg-primary"
                ></div>

                <!-- Enterprise Name -->
                <td class="py-space-md px-space-md pl-space-lg">
                  <div class="flex items-center gap-space-sm">
                    <div class="h-10 w-10 shrink-0 rounded-lg flex items-center justify-center font-bold font-title-md" :class="[ent.avatarBg, ent.avatarColor]">
                      {{ ent.avatarText }}
                    </div>
                    <div class="min-w-0">
                      <div class="flex items-center gap-1.5">
                        <span class="font-title-md text-body-lg font-semibold text-on-surface truncate">{{ ent.name }}</span>
                        <BadgeCheck v-if="ent.verified" class="text-tertiary-container" :size="16" />
                        <span v-for="tag in ent.tags" :key="tag" class="rounded bg-primary-fixed text-on-primary-fixed px-1 py-0.2 font-code-xs text-code-xs">{{ tag }}</span>
                      </div>
                      <div class="font-code-xs text-code-xs text-on-surface-variant font-mono">{{ ent.creditCode }}</div>
                    </div>
                  </div>
                </td>

                <!-- Industry / Scale -->
                <td class="py-space-md px-space-md">
                  <div class="font-body-sm text-body-sm text-on-surface">{{ ent.industry }}</div>
                  <div class="font-label-sm text-label-sm text-on-surface-variant">{{ ent.scale }}</div>
                </td>

                <!-- Legal Person -->
                <td class="py-space-md px-space-md">
                  <div class="font-body-sm text-body-sm text-on-surface">{{ ent.legalPerson }}</div>
                  <div class="font-code-xs text-code-xs text-on-surface-variant">{{ ent.phone }}</div>
                </td>

                <!-- License -->
                <td class="py-space-md px-space-md">
                  <div class="flex items-center gap-1.5">
                    <div class="h-7 w-7 rounded flex items-center justify-center" :class="getOcrIcon(ent.licenseOcr).bg">
                      <component :is="getOcrIcon(ent.licenseOcr).icon" :size="16" :class="getOcrIcon(ent.licenseOcr).color" />
                    </div>
                    <span class="rounded px-1.5 py-0.5 font-code-xs text-code-xs font-semibold" :class="getOcrTagClass(ent.licenseOcr)">
                      {{ ent.licenseTag }}
                    </span>
                  </div>
                </td>

                <!-- Submit Time -->
                <td class="py-space-md px-space-md">
                  <div class="font-body-sm text-body-sm text-on-surface">{{ ent.submitTime }}</div>
                  <div class="font-code-xs text-code-xs font-medium" :class="ent.waitUrgent ? 'text-error' : 'text-on-surface-variant'">{{ ent.waitTime }}</div>
                </td>

                <!-- Status -->
                <td class="py-space-md px-space-md">
                  <span class="inline-flex items-center gap-1 rounded-full px-2.5 py-0.5 font-label-sm text-label-sm font-semibold" :class="getStatusClass(ent.status)">
                    <span class="h-1.5 w-1.5 rounded-full" :class="getStatusDot(ent.status)"></span>
                    {{ getStatusLabel(ent.status) }}
                  </span>
                </td>

                <!-- Action -->
                <td class="py-space-md px-space-md pr-space-lg text-right">
                  <button
                    class="inline-flex items-center gap-1 rounded-lg px-3 py-1 font-label-sm text-label-sm transition-colors"
                    :class="getActionStyle(ent.status)"
                    @click.stop="
                      ent.status === 'pending'
                        ? handleApprove(ent.id)
                        : ent.status === 'rejected'
                          ? handleReject(ent.id)
                          : null
                    "
                  >
                    {{ getActionLabel(ent.status) }}
                    <ChevronRight v-if="ent.status === 'pending'" :size="14" />
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div class="flex items-center justify-between px-space-lg py-space-sm bg-surface-container-low mt-auto">
          <span class="font-label-sm text-label-sm text-on-surface-variant">显示 1 至 {{ Math.min(totalFiltered, pageSize) }} 条，共 {{ totalFiltered }} 条结果</span>
          <div class="flex items-center gap-1">
            <button
              class="h-8 w-8 rounded-lg bg-surface-container flex items-center justify-center text-outline hover:text-on-surface disabled:opacity-40"
              :disabled="currentPage === 1"
              @click="currentPage = Math.max(1, currentPage - 1)"
            >
              <ChevronLeft :size="16" />
            </button>
            <button
              v-for="page in totalPages"
              :key="page"
              class="h-8 w-8 rounded-lg font-code-xs text-code-xs font-bold transition-colors"
              :class="
                page === currentPage
                  ? 'bg-primary text-on-primary'
                  : 'bg-surface-container text-on-surface hover:bg-surface-container-high'
              "
              @click="currentPage = page"
            >
              {{ page }}
            </button>
            <button
              class="h-8 w-8 rounded-lg bg-surface-container flex items-center justify-center text-on-surface hover:bg-surface-container-high"
              :disabled="currentPage === totalPages"
              @click="currentPage = Math.min(totalPages, currentPage + 1)"
            >
              <ChevronRight :size="16" />
            </button>
          </div>
        </div>
      </div>

      <!-- Right: Detail Drawer -->
      <div v-if="selectedEnterprise" class="xl:col-span-5 flex flex-col rounded-xl bg-surface-container-lowest shadow-md overflow-hidden">
        <!-- Drawer Header -->
        <div class="flex items-center justify-between px-space-lg py-space-md bg-surface-container-high">
          <div class="flex items-center gap-2">
            <ClipboardCheck class="text-primary" :size="22" />
            <div>
              <h3 class="font-title-md text-title-md text-on-surface">企业认证审查卡片</h3>
              <span class="font-code-xs text-code-xs text-on-surface-variant">申请流水单号：{{ selectedEnterprise.flowNo }}</span>
            </div>
          </div>
          <div class="flex items-center gap-1.5">
            <span class="rounded-full bg-secondary-fixed text-on-secondary-fixed-variant px-2.5 py-0.5 font-label-sm text-label-sm font-semibold">待初审</span>
            <button class="h-7 w-7 rounded-lg hover:bg-surface-container-highest flex items-center justify-center text-on-surface-variant hover:text-on-surface" @click="selectedEnterprise = null">
              <X :size="18" />
            </button>
          </div>
        </div>

        <div class="p-space-lg space-y-space-lg">
          <!-- Enterprise Meta -->
          <div class="rounded-xl bg-surface-container-low p-space-md space-y-space-sm">
            <div class="flex items-start justify-between">
              <div>
                <h4 class="font-title-lg text-title-lg text-on-surface font-semibold">{{ selectedEnterprise.enterprise.name }}</h4>
                <p class="font-body-sm text-body-sm text-on-surface-variant">{{ selectedEnterprise.companyType }} · {{ selectedEnterprise.industryCategory }}</p>
              </div>
              <span class="rounded bg-tertiary-fixed text-on-tertiary-fixed px-2 py-0.5 font-label-sm text-label-sm font-semibold">{{ selectedEnterprise.statusLabel }}</span>
            </div>
            <div class="grid grid-cols-2 gap-space-sm pt-space-xs font-body-sm text-body-sm">
              <div>
                <span class="text-on-surface-variant">统一信用代码：</span>
                <span class="font-code-xs text-code-xs font-mono font-bold text-on-surface">{{ selectedEnterprise.enterprise.creditCode }}</span>
              </div>
              <div>
                <span class="text-on-surface-variant">法定代表人：</span>
                <span class="text-on-surface font-medium">{{ selectedEnterprise.enterprise.legalPerson }} (中国籍)</span>
              </div>
              <div>
                <span class="text-on-surface-variant">注册资本：</span>
                <span class="text-on-surface font-medium">{{ selectedEnterprise.registeredCapital }}</span>
              </div>
              <div>
                <span class="text-on-surface-variant">成立日期：</span>
                <span class="text-on-surface font-medium">{{ selectedEnterprise.establishedDate }}</span>
              </div>
              <div class="col-span-2" v-if="selectedEnterprise.website">
                <span class="text-on-surface-variant">公司官网：</span>
                <a class="text-primary hover:underline font-code-xs text-code-xs" :href="selectedEnterprise.website" target="_blank">{{ selectedEnterprise.website }}</a>
              </div>
              <div class="col-span-2">
                <span class="text-on-surface-variant">注册经营地址：</span>
                <span class="text-on-surface">{{ selectedEnterprise.address }}</span>
              </div>
            </div>
          </div>

          <!-- Audit Actions -->
          <div v-if="selectedEnterprise.enterprise.status === 'pending'" class="flex items-center gap-space-sm">
            <button
              class="flex-1 h-10 rounded-lg bg-primary text-on-primary font-label-md text-label-md hover:bg-primary-container shadow-sm flex items-center justify-center gap-1.5 transition-colors"
              @click="handleApprove(selectedEnterprise.enterprise.id)"
            >
              <CheckCircle :size="18" />
              通过认证
            </button>
            <button
              class="flex-1 h-10 rounded-lg bg-error-container text-on-error-container font-label-md text-label-md hover:bg-error/10 flex items-center justify-center gap-1.5 transition-colors"
              @click="handleReject(selectedEnterprise.enterprise.id)"
            >
              <AlertTriangle :size="18" />
              驳回申请
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
