<script setup lang="ts">
import { ref, computed } from 'vue'
import {
  Search,
  Filter,
  RefreshCw,
  LockOpen,
  Ban,
  Eye,
  UserLock,
  Lock,
  ChevronLeft,
  ChevronRight,
  TrendingUp,
  CheckCircle,
  Group,
} from 'lucide-vue-next'
import type { User, UserStatus, UserRole } from '@/types'
import { users as initialUsers } from '@/mock/users'
import UserDetailDrawer from '@/components/UserDetailDrawer.vue'
import DisableUserModal from '@/components/DisableUserModal.vue'

const users = ref<User[]>([...initialUsers])
const activeRole = ref<UserRole | 'all'>('all')
const searchQuery = ref('')
const statusFilter = ref<UserStatus | 'all'>('all')
const currentPage = ref(1)
const pageSize = ref(20)
const selectedUids = ref<Set<string>>(new Set())

const drawerUser = ref<User | null>(null)
const disableModalVisible = ref(false)
const disableTarget = ref<{ uid: string; name: string }>({ uid: '', name: '' })

const toastVisible = ref(false)
const toastMessage = ref('')
const toastIcon = ref('check_circle')

const totalCount = computed(() => users.value.length)
const activeCount = computed(() => users.value.filter((u) => u.status === 'active').length)
const studentCount = computed(() => users.value.filter((u) => u.role === 'student').length)
const enterpriseCount = computed(() => users.value.filter((u) => u.role === 'enterprise').length)

const roleTabs = computed(() => [
  { key: 'all' as const, label: '全部账号', count: totalCount.value, icon: 'apps' },
  { key: 'student' as const, label: '学生用户', count: studentCount.value, icon: 'school' },
  { key: 'enterprise' as const, label: '企业HR用户', count: enterpriseCount.value, icon: 'corporate_fare' },
])

const filteredUsers = computed(() => {
  let list = users.value

  if (activeRole.value !== 'all') {
    list = list.filter((u) => u.role === activeRole.value)
  }

  if (statusFilter.value !== 'all') {
    list = list.filter((u) => u.status === statusFilter.value)
  }

  if (searchQuery.value) {
    const q = searchQuery.value.toLowerCase()
    list = list.filter(
      (u) =>
        u.uid.toLowerCase().includes(q) ||
        u.name.toLowerCase().includes(q) ||
        u.maskedPhone.includes(q),
    )
  }

  return list
})

const totalFiltered = computed(() => filteredUsers.value.length)
const totalPages = computed(() => Math.max(1, Math.ceil(totalFiltered.value / pageSize.value)))

const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  return filteredUsers.value.slice(start, start + pageSize.value)
})

const allSelected = computed(() => {
  return paginatedUsers.value.length > 0 && paginatedUsers.value.every((u) => selectedUids.value.has(u.uid))
})

function toggleAll() {
  if (allSelected.value) {
    paginatedUsers.value.forEach((u) => selectedUids.value.delete(u.uid))
  } else {
    paginatedUsers.value.forEach((u) => selectedUids.value.add(u.uid))
  }
}

function toggleSelect(uid: string) {
  if (selectedUids.value.has(uid)) {
    selectedUids.value.delete(uid)
  } else {
    selectedUids.value.add(uid)
  }
}

function batchEnable() {
  if (selectedUids.value.size === 0) {
    showToast('请先勾选需要批量操作的账号条目', 'warning')
    return
  }
  const count = selectedUids.value.size
  users.value.forEach((u) => {
    if (selectedUids.value.has(u.uid)) {
      u.status = 'active'
    }
  })
  selectedUids.value.clear()
  showToast(`已成功批量解冻启用所选 ${count} 位用户`, 'check_circle')
}

function batchDisable() {
  if (selectedUids.value.size === 0) {
    showToast('请先勾选需要批量操作的账号条目', 'warning')
    return
  }
  const count = selectedUids.value.size
  users.value.forEach((u) => {
    if (selectedUids.value.has(u.uid)) {
      u.status = 'disabled'
    }
  })
  selectedUids.value.clear()
  showToast(`已对所选 ${count} 位用户实施安全管控停权`, 'block')
}

function openDrawer(user: User) {
  drawerUser.value = user
}

function closeDrawer() {
  drawerUser.value = null
}

function openDisableModal(uid: string, name: string) {
  disableTarget.value = { uid, name }
  disableModalVisible.value = true
}

function closeDisableModal() {
  disableModalVisible.value = false
}

function confirmDisable(reason: string, _note: string) {
  const user = users.value.find((u) => u.uid === disableTarget.value.uid)
  if (user) {
    user.status = 'disabled'
  }
  closeDisableModal()
  showToast(`已成功封禁账号 ${disableTarget.value.uid}：${reason}`, 'gavel')
}

function enableUser(uid: string) {
  const user = users.value.find((u) => u.uid === uid)
  if (user) {
    user.status = 'active'
    showToast(`用户 ${user.name} (${uid}) 账号已恢复启用，已放行其登录鉴权`, 'check_circle')
  }
}

function resetPassword(uid: string) {
  const user = users.value.find((u) => u.uid === uid)
  if (user) {
    const confirmed = window.confirm(`确定要协助重置用户 [${user.name}] 的密码吗？\n系统将生成高强度随机临时密码并通过其脱敏绑定手机号下发。`)
    if (confirmed) {
      showToast(`密码已重置，初始临时鉴权密钥已推送至该绑定手机`, 'lock_reset')
    }
  }
}

function resetFilters() {
  searchQuery.value = ''
  statusFilter.value = 'all'
  currentPage.value = 1
  showToast('已重置所有查询条件', 'refresh')
}

function showToast(msg: string, icon: string = 'check_circle') {
  toastMessage.value = msg
  toastIcon.value = icon
  toastVisible.value = true
  setTimeout(() => {
    toastVisible.value = false
  }, 3200)
}

function getStatusClass(status: UserStatus) {
  return status === 'active'
    ? 'bg-tertiary-fixed text-on-tertiary-fixed-variant'
    : 'bg-error-container text-on-error-container'
}

function getStatusDot(status: UserStatus) {
  return status === 'active' ? 'bg-tertiary' : 'bg-error'
}

function getStatusLabel(status: UserStatus) {
  return status === 'active' ? '正常' : '已禁用'
}

function goToPage(page: number) {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
  }
}
</script>

<template>
  <div class="py-space-lg">
    <!-- Stats Cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-gutter mb-gutter">
      <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm relative overflow-hidden group hover:shadow-md transition-all">
        <div class="flex items-center justify-between">
          <div>
            <div class="font-label-sm text-label-sm text-outline uppercase tracking-wider">平台注册总账号</div>
            <div class="font-metric-xl text-metric-xl text-on-surface mt-space-xs tracking-tight">
              {{ totalCount.toLocaleString() }}
              <span class="font-label-md text-label-md text-outline font-normal">人</span>
            </div>
          </div>
          <div class="w-11 h-11 rounded-xl bg-primary-fixed flex items-center justify-center text-primary shadow-sm group-hover:scale-105 transition-transform">
            <Group :size="24" />
          </div>
        </div>
        <div class="mt-space-md flex items-center gap-space-xs">
          <span class="font-code-xs text-code-xs px-1.5 py-0.5 rounded bg-tertiary-container/10 text-tertiary font-medium flex items-center">
            <TrendingUp :size="12" class="mr-0.5" />+12.4%
          </span>
          <span class="font-body-sm text-body-sm text-outline">较上月新增 142 名注册</span>
        </div>
        <div class="absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r from-primary to-transparent opacity-0 group-hover:opacity-100 transition-opacity"></div>
      </div>

      <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm relative overflow-hidden group hover:shadow-md transition-all">
        <div class="flex items-center justify-between">
          <div>
            <div class="font-label-sm text-label-sm text-outline uppercase tracking-wider">正常使用状态</div>
            <div class="font-metric-xl text-metric-xl text-on-surface mt-space-xs tracking-tight">
              {{ activeCount.toLocaleString() }}
              <span class="font-label-md text-label-md text-outline font-normal">席</span>
            </div>
          </div>
          <div class="w-11 h-11 rounded-xl bg-tertiary-fixed flex items-center justify-center text-on-tertiary-fixed-variant shadow-sm group-hover:scale-105 transition-transform">
            <CheckCircle :size="24" />
          </div>
        </div>
        <div class="mt-space-md flex items-center gap-space-xs">
          <span class="w-2 h-2 rounded-full bg-tertiary"></span>
          <span class="font-body-sm text-body-sm text-tertiary font-medium">98.83%</span>
          <span class="font-body-sm text-body-sm text-outline">健康履约中</span>
        </div>
        <div class="absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r from-tertiary to-transparent opacity-0 group-hover:opacity-100 transition-opacity"></div>
      </div>
    </div>

    <!-- Main Workspace -->
    <div class="bg-surface-container-lowest rounded-xl shadow-sm flex flex-col overflow-hidden">
      <!-- Role Tabs & Batch Actions -->
      <div class="p-space-lg pb-0 flex flex-col lg:flex-row lg:items-center justify-between gap-space-md">
        <div class="flex items-center gap-space-xs bg-surface-container-low p-1 rounded-xl max-w-fit">
          <button
            v-for="tab in roleTabs"
            :key="tab.key"
            class="role-tab px-space-md py-1.5 rounded-lg font-title-md text-title-md transition-all flex items-center gap-space-xs"
            :class="
              activeRole === tab.key
                ? 'bg-surface-container-lowest text-primary shadow-sm'
                : 'text-on-surface-variant hover:text-on-surface'
            "
            @click="activeRole = tab.key; currentPage = 1"
          >
            <span class="material-symbols-outlined text-[18px]">{{ tab.icon }}</span>
            <span>{{ tab.label }}</span>
            <span
              class="ml-1 px-1.5 py-0.5 rounded-full font-code-xs text-code-xs font-semibold"
              :class="activeRole === tab.key ? 'bg-primary/10 text-primary' : 'bg-surface-container-high text-on-surface-variant'"
            >
              {{ tab.count.toLocaleString() }}
            </span>
          </button>
        </div>

        <div class="flex items-center gap-space-sm self-end lg:self-auto">
          <div
            v-if="selectedUids.size > 0"
            class="flex items-center gap-space-xs px-space-md py-1 rounded-lg bg-surface-container-high text-on-surface font-body-sm text-body-sm"
          >
            已选择 <span class="font-semibold text-primary">{{ selectedUids.size }}</span> 项
          </div>
          <button class="px-space-md py-2 rounded-lg bg-surface-container text-tertiary hover:bg-tertiary-fixed transition-all font-label-md text-label-md flex items-center gap-space-xs" @click="batchEnable">
            <LockOpen :size="18" />
            <span>批量启用</span>
          </button>
          <button class="px-space-md py-2 rounded-lg bg-error-container/60 text-error hover:bg-error-container transition-all font-label-md text-label-md flex items-center gap-space-xs" @click="batchDisable">
            <Ban :size="18" />
            <span>批量禁用</span>
          </button>
        </div>
      </div>

      <!-- Filter Toolbar -->
      <div class="p-space-lg pt-space-lg grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-12 gap-space-md">
        <div class="lg:col-span-4 relative">
          <Search class="absolute left-space-md top-1/2 -translate-y-1/2 text-outline" :size="18" />
          <input
            v-model="searchQuery"
            class="w-full h-10 pl-10 pr-space-md bg-surface-container-low text-on-surface placeholder:text-outline font-body-sm text-body-sm rounded-lg focus:outline-none focus:bg-surface-container-lowest shadow-sm transition-all"
            placeholder="检索：用户ID、脱敏手机号(如138****)、姓名..."
          />
        </div>

        <div class="lg:col-span-2 relative">
          <select
            v-model="statusFilter"
            class="w-full h-10 px-space-md bg-surface-container-low text-on-surface font-body-sm text-body-sm rounded-lg appearance-none cursor-pointer focus:outline-none focus:bg-surface-container-lowest shadow-sm pr-8 transition-all"
          >
            <option value="all">全部账号状态</option>
            <option value="active">正常访问 (Active)</option>
            <option value="disabled">已禁用 / 冻结 (Banned)</option>
          </select>
          <span class="material-symbols-outlined absolute right-space-sm top-1/2 -translate-y-1/2 text-outline pointer-events-none text-[18px]">expand_more</span>
        </div>

        <div class="lg:col-span-4 flex items-center gap-space-xs bg-surface-container-low px-space-md rounded-lg h-10 shadow-sm">
          <span class="material-symbols-outlined text-outline text-[18px]">calendar_today</span>
          <input class="w-1/2 bg-transparent text-on-surface text-body-sm font-body-sm focus:outline-none placeholder:text-outline" placeholder="注册起始日" type="text" />
          <span class="text-outline font-code-xs text-code-xs">至</span>
          <input class="w-1/2 bg-transparent text-on-surface text-body-sm font-body-sm focus:outline-none placeholder:text-outline" placeholder="注册截止日" type="text" />
        </div>

        <div class="lg:col-span-2 flex items-center gap-space-sm justify-end">
          <button class="flex-1 h-10 rounded-lg bg-primary-container text-on-primary font-label-md text-label-md flex items-center justify-center gap-space-xs shadow-sm hover:opacity-95 transition-all">
            <Filter :size="16" />
            <span>筛选</span>
          </button>
          <button class="w-10 h-10 rounded-lg bg-surface-container-low text-on-surface-variant hover:bg-surface-container flex items-center justify-center transition-all" title="重置检索" @click="resetFilters">
            <RefreshCw :size="18" />
          </button>
        </div>
      </div>

      <!-- Data Table -->
      <div class="w-full overflow-x-auto">
        <table class="w-full text-left">
          <thead class="bg-surface-container-low">
            <tr>
              <th class="w-12 px-space-md py-3 text-center">
                <input
                  type="checkbox"
                  class="rounded w-4 h-4 text-primary focus:ring-0 cursor-pointer"
                  :checked="allSelected"
                  @change="toggleAll"
                />
              </th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold">用户ID</th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold">用户基本信息</th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold">角色归属与画像</th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold">安全绑定手机号</th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold">注册时间</th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold">最后登录与IP</th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold">账号状态</th>
              <th class="px-space-md py-3 font-label-sm text-label-sm text-outline font-semibold text-right">管理操作</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="user in paginatedUsers"
              :key="user.uid"
              class="user-row hover:bg-surface-container-low/60 transition-colors group"
              :class="user.status === 'disabled' ? 'bg-error-container/10' : ''"
            >
              <td class="px-space-md py-3.5 text-center">
                <input
                  type="checkbox"
                  class="user-checkbox rounded w-4 h-4 text-primary focus:ring-0 cursor-pointer"
                  :checked="selectedUids.has(user.uid)"
                  @change="toggleSelect(user.uid)"
                />
              </td>
              <td class="px-space-md py-3.5 font-code-xs text-code-xs text-outline font-semibold">
                {{ user.uid }}
              </td>
              <td class="px-space-md py-3.5">
                <div class="flex items-center gap-space-md">
                  <div class="w-9 h-9 rounded-full flex items-center justify-center font-bold text-title-md shrink-0 shadow-sm" :class="[user.avatarBg, user.avatarColor]">
                    {{ user.avatarText }}
                  </div>
                  <div class="flex flex-col min-w-0">
                    <div class="font-title-md text-title-md text-on-surface truncate" :class="user.status === 'disabled' ? 'line-through opacity-70' : ''">
                      {{ user.name }}
                    </div>
                    <div class="font-code-xs text-code-xs" :class="user.status === 'disabled' ? 'text-error' : 'text-outline'">
                      {{ user.status === 'disabled' ? '账号已被安全系统管控' : user.email }}
                    </div>
                  </div>
                </div>
              </td>
              <td class="px-space-md py-3.5">
                <div class="flex flex-col gap-1 items-start">
                  <span
                    class="inline-flex items-center gap-1 px-2 py-0.5 rounded-full font-label-sm text-label-sm font-medium"
                    :class="user.role === 'student' ? 'bg-primary-fixed text-primary' : 'bg-secondary-fixed text-secondary'"
                  >
                    <span class="material-symbols-outlined text-[14px]">{{ user.roleIcon }}</span>
                    {{ user.roleLabel }}
                  </span>
                  <span class="font-body-sm text-body-sm text-on-surface-variant">{{ user.affiliation }}</span>
                </div>
              </td>
              <td class="px-space-md py-3.5">
                <div class="flex items-center gap-1 font-code-xs text-code-xs bg-surface-container-low px-2 py-1 rounded w-fit"
                  :class="user.status === 'disabled' ? 'text-on-surface' : 'text-on-surface'">
                  <span class="material-symbols-outlined text-[14px]" :class="user.status === 'disabled' ? 'text-error' : 'text-tertiary'">lock</span>
                  <span>{{ user.maskedPhone }}</span>
                </div>
              </td>
              <td class="px-space-md py-3.5 font-body-sm text-body-sm text-outline">
                {{ user.registerTime }}
              </td>
              <td class="px-space-md py-3.5">
                <div class="flex flex-col">
                  <span class="font-body-sm text-body-sm text-on-surface">{{ user.lastLoginTime }}</span>
                  <span class="font-code-xs text-code-xs text-outline">{{ user.lastLoginIp }} ({{ user.lastLoginLocation }})</span>
                </div>
              </td>
              <td class="px-space-md py-3.5">
                <span class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full font-label-sm text-label-sm font-semibold" :class="getStatusClass(user.status)">
                  <span class="w-1.5 h-1.5 rounded-full" :class="getStatusDot(user.status)"></span>
                  {{ getStatusLabel(user.status) }}
                </span>
              </td>
              <td class="px-space-md py-3.5 text-right">
                <div class="flex items-center justify-end gap-space-xs">
                  <button class="p-1.5 rounded-lg text-primary hover:bg-primary-fixed transition-all" title="查看详情档案" @click="openDrawer(user)">
                    <Eye :size="18" />
                  </button>
                  <button
                    v-if="user.status === 'active'"
                    class="p-1.5 rounded-lg text-error hover:bg-error-container transition-all"
                    title="禁用用户账号"
                    @click="openDisableModal(user.uid, user.name)"
                  >
                    <UserLock :size="18" />
                  </button>
                  <button
                    v-else
                    class="p-1.5 rounded-lg text-tertiary hover:bg-tertiary-fixed transition-all"
                    title="一键解冻启用"
                    @click="enableUser(user.uid)"
                  >
                    <CheckCircle :size="18" />
                  </button>
                  <button class="p-1.5 rounded-lg text-on-surface-variant hover:bg-surface-container transition-all" title="重置初始密码" @click="resetPassword(user.uid)">
                    <Lock :size="18" />
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Empty State -->
      <div v-if="totalFiltered === 0" class="py-16 flex flex-col items-center justify-center text-center">
        <div class="w-14 h-14 rounded-full bg-surface-container flex items-center justify-center text-outline mb-space-sm">
          <span class="material-symbols-outlined text-[28px]">search_off</span>
        </div>
        <div class="font-title-md text-title-md text-on-surface">未匹配到符合条件的用户账号</div>
        <div class="font-body-sm text-body-sm text-outline mt-1">请尝试放宽筛选条件，或确认输入的脱敏手机号与姓名格式无误</div>
        <button class="mt-space-md px-space-md py-1.5 rounded-lg bg-surface-container-high text-on-surface font-label-md text-label-md" @click="resetFilters">
          清除筛选条件
        </button>
      </div>

      <!-- Pagination -->
      <div class="p-space-lg flex flex-col sm:flex-row items-center justify-between gap-space-md bg-surface-container-lowest">
        <div class="flex items-center gap-space-md font-body-sm text-body-sm text-outline">
          <span>共 <strong class="text-on-surface font-semibold">{{ totalFiltered.toLocaleString() }}</strong> 条记录</span>
          <div class="h-3.5 w-px bg-outline-variant"></div>
          <div class="flex items-center gap-space-xs">
            <span>每页显示</span>
            <select v-model="pageSize" class="bg-surface-container-low text-on-surface font-label-sm text-label-sm rounded-lg px-2 py-1 focus:outline-none cursor-pointer">
              <option :value="10">10 条/页</option>
              <option :value="20">20 条/页</option>
              <option :value="50">50 条/页</option>
            </select>
          </div>
        </div>

        <div class="flex items-center gap-space-xs">
          <button
            class="w-8 h-8 rounded-lg bg-surface-container-low text-on-surface-variant flex items-center justify-center hover:bg-surface-container transition-all disabled:opacity-40"
            :disabled="currentPage === 1"
            @click="goToPage(currentPage - 1)"
          >
            <ChevronLeft :size="16" />
          </button>
          <button
            v-for="page in Math.min(totalPages, 3)"
            :key="page"
            class="w-8 h-8 rounded-lg font-label-md text-label-md transition-all"
            :class="page === currentPage ? 'bg-primary-container text-on-primary font-semibold shadow-sm' : 'bg-surface-container-low text-on-surface hover:bg-surface-container'"
            @click="goToPage(page)"
          >
            {{ page }}
          </button>
          <span v-if="totalPages > 3" class="px-1 text-outline font-code-xs text-code-xs">...</span>
          <button
            v-if="totalPages > 3"
            class="w-8 h-8 rounded-lg bg-surface-container-low text-on-surface hover:bg-surface-container font-label-md text-label-md transition-all"
            @click="goToPage(totalPages)"
          >
            {{ totalPages }}
          </button>
          <button
            class="w-8 h-8 rounded-lg bg-surface-container-low text-on-surface-variant flex items-center justify-center hover:bg-surface-container transition-all disabled:opacity-40"
            :disabled="currentPage === totalPages"
            @click="goToPage(currentPage + 1)"
          >
            <ChevronRight :size="16" />
          </button>
          <div class="flex items-center gap-1 font-body-sm text-body-sm text-outline ml-space-sm">
            <span>前往</span>
            <input
              type="number"
              :value="currentPage"
              :min="1"
              :max="totalPages"
              class="w-12 h-8 rounded-lg bg-surface-container-low text-center text-on-surface font-body-sm text-body-sm focus:outline-none"
              @change="(e) => goToPage(Number((e.target as HTMLInputElement).value))"
            />
            <span>页</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Toast -->
    <Transition name="toast">
      <div
        v-if="toastVisible"
        class="fixed bottom-6 right-6 z-50 flex items-center gap-space-sm px-space-md py-space-sm rounded-xl bg-inverse-surface text-inverse-on-surface shadow-xl font-label-md text-label-md"
      >
        <span class="material-symbols-outlined text-[18px] text-tertiary-fixed">{{ toastIcon }}</span>
        <span>{{ toastMessage }}</span>
      </div>
    </Transition>

    <!-- Detail Drawer -->
    <UserDetailDrawer :user="drawerUser" @close="closeDrawer" />

    <!-- Disable Modal -->
    <DisableUserModal
      :visible="disableModalVisible"
      :user-name="disableTarget.name"
      :uid="disableTarget.uid"
      @close="closeDisableModal"
      @confirm="confirmDisable"
    />
  </div>
</template>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}
.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>
