<template>
  <div class="min-h-screen bg-background">
    <!-- 页面头部 -->
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6 flex items-start justify-between">
        <div>
          <div class="flex items-center gap-3">
            <h1 class="text-2xl font-bold text-on-surface">{{ companyName || '企业' }}招聘看板</h1>
            <span class="px-2.5 py-0.5 rounded-full text-xs font-medium"
              :class="auditStatus === 1 ? 'bg-emerald-50 text-emerald-600' : auditStatus === 0 ? 'bg-amber-50 text-amber-600' : 'bg-gray-100 text-gray-500'">
              企业认证：{{ auditStatusText }}
            </span>
          </div>
          <p class="text-on-surface-variant mt-1">数据来自投递服务接口实时统计。趋势图表类聚合数据需后端提供看板统计接口，暂未展示。</p>
        </div>
        <div class="flex items-center gap-3">
          <router-link to="/enterprise/post-job" class="h-10 px-4 flex items-center gap-2 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium hover:bg-surface-container transition-colors">
            <span class="material-symbols-outlined text-lg">add</span>发布职位
          </router-link>
          <router-link to="/enterprise/candidates" class="h-10 px-5 flex items-center gap-2 rounded-xl bg-primary text-on-primary text-sm font-semibold hover:bg-primary-container transition-colors shadow-sm">
            <span class="material-symbols-outlined text-lg">group</span>候选人管理
          </router-link>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-6 space-y-6">
      <!-- 统计卡片 -->
      <div class="grid grid-cols-2 lg:grid-cols-5 gap-4">
        <div v-for="card in statCards" :key="card.label"
          class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">{{ card.label }}</span>
            <span class="material-symbols-outlined text-lg" :class="card.color">{{ card.icon }}</span>
          </div>
          <span class="text-3xl font-bold" :class="card.color">{{ card.value }}</span>
          <p class="text-[10px] text-on-surface-variant mt-1">{{ card.sub }}</p>
        </div>
      </div>

      <!-- 主内容区：左侧候选人列表 + 右侧消息模块 -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- 左侧：候选人列表（占2/3） -->
        <div class="lg:col-span-2 space-y-6">
          <!-- 筛选 Tab -->
          <div class="flex items-center gap-1 bg-surface-container-low rounded-xl p-1 w-fit">
            <button v-for="tab in filterTabs" :key="tab.key" @click="activeTab = tab.key"
              :class="['px-4 py-2 rounded-lg text-xs font-medium transition-all',
                activeTab === tab.key ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface']">
              {{ tab.label }} ({{ tab.count }})
            </button>
          </div>

          <!-- 候选人列表 -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead>
                  <tr class="bg-surface-container-low/50 border-b border-surface-container-high">
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
                      <div class="flex items-center gap-3">
                        <div class="w-9 h-9 rounded-full bg-primary/10 flex items-center justify-center text-primary font-bold text-sm shrink-0">
                          {{ (c.resumeTitle || '简').charAt(0) }}
                        </div>
                        <span class="font-medium text-on-surface text-sm">{{ c.resumeTitle || '未命名简历' }}</span>
                      </div>
                    </td>
                    <td class="px-5 py-4 text-sm text-on-surface">{{ c.jobName || '-' }}</td>
                    <td class="px-5 py-4 text-xs text-on-surface-variant">{{ formatDateTime(c.createTime) }}</td>
                    <td class="px-5 py-4">
                      <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-medium" :class="c.statusClass">
                        <span class="w-1.5 h-1.5 rounded-full" :class="c.dotClass"></span>
                        {{ c.statusLabel }}
                      </span>
                    </td>
                    <td class="px-5 py-4 text-xs text-on-surface-variant max-w-[180px] truncate">{{ c.hrRemark || '-' }}</td>
                    <td class="px-5 py-4">
                      <div class="flex items-center justify-end gap-1">
                        <router-link :to="{ path: '/enterprise/applications', query: { id: c.id } }" class="px-3 py-1.5 rounded-lg bg-primary text-on-primary text-xs font-semibold hover:bg-primary-container transition-colors">
                          处理流转
                        </router-link>
                        <button v-if="c.statusRaw === 0" @click="quickReject(c)" class="px-3 py-1.5 rounded-lg text-error hover:bg-error/10 text-xs font-medium transition-colors">
                          不合适
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div v-if="applications.length === 0" class="p-12 text-center">
              <span class="material-symbols-outlined text-5xl text-surface-container-high mb-3 block">analytics</span>
              <p class="text-sm text-on-surface-variant">暂无投递数据</p>
              <router-link to="/enterprise/post-job" class="mt-4 inline-block px-5 py-2 bg-primary text-on-primary rounded-xl text-sm font-medium hover:bg-primary-container transition-colors">
                发布职位
              </router-link>
            </div>
          </div>
        </div>

        <!-- 右侧：消息模块（占1/3） -->
        <div class="lg:col-span-1">
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden sticky top-24">
            <!-- 消息模块头部 -->
            <div class="px-5 py-4 border-b border-surface-container-high flex items-center justify-between">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-lg text-primary">chat</span>
                <h3 class="text-sm font-semibold text-on-surface">学生消息</h3>
                <span v-if="totalUnreadMsg > 0" class="px-1.5 py-0.5 bg-red-500 text-white text-[10px] font-semibold rounded-full">{{ totalUnreadMsg > 99 ? '99+' : totalUnreadMsg }}</span>
              </div>
              <router-link to="/messages" class="text-xs text-primary hover:underline">查看全部</router-link>
            </div>

            <!-- 会话列表 -->
            <div class="max-h-[480px] overflow-y-auto">
              <div v-if="conversations.length" class="divide-y divide-surface-container-high/50">
                <div v-for="conv in conversations" :key="conv.id"
                  @click="goChat(conv)"
                  class="px-5 py-4 hover:bg-surface-container-low/50 transition-colors cursor-pointer group">
                  <div class="flex items-start gap-3">
                    <div class="relative shrink-0">
                      <div class="w-10 h-10 rounded-full flex items-center justify-center text-white font-bold text-sm"
                        :style="{ background: getLogoColor(conv.toUserId) }">
                        {{ (conv.toUserName || '学').charAt(0) }}
                      </div>
                      <span v-if="conv.unreadCount > 0" class="absolute -top-1 -right-1 w-4 h-4 bg-red-500 text-white text-[9px] font-bold rounded-full flex items-center justify-center">{{ conv.unreadCount > 99 ? '99+' : conv.unreadCount }}</span>
                    </div>
                    <div class="flex-1 min-w-0">
                      <div class="flex items-center justify-between mb-1">
                        <span class="text-sm font-medium text-on-surface truncate">{{ conv.toUserName || '学生' }}</span>
                        <span class="text-[10px] text-on-surface-variant shrink-0 ml-2">{{ formatTime(conv.lastTime) }}</span>
                      </div>
                      <p class="text-xs text-on-surface-variant truncate">{{ conv.lastMessage || '暂无消息' }}</p>
                      <p v-if="conv.jobTitle" class="text-[10px] text-primary mt-1 truncate">关于：{{ conv.jobTitle }}</p>
                    </div>
                  </div>
                </div>
              </div>
              <div v-else class="p-8 text-center">
                <span class="material-symbols-outlined text-4xl text-surface-container-high mb-2 block">forum</span>
                <p class="text-xs text-on-surface-variant">暂无学生消息</p>
                <p class="text-[10px] text-on-surface-variant/60 mt-1">学生投递后可在此回复沟通</p>
              </div>
            </div>

            <!-- 底部快速入口 -->
            <div class="px-5 py-3 border-t border-surface-container-high bg-surface-container-low/30">
              <router-link to="/messages" class="flex items-center justify-center gap-2 py-2 rounded-lg bg-primary/10 text-primary text-xs font-medium hover:bg-primary/20 transition-colors">
                <span class="material-symbols-outlined text-[16px]">open_in_new</span>
                进入消息中心
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getCompanyInfo } from '../api/company'
import { getReceivedApplications, handleApplication } from '../api/application'
import { getConversationList, getUnreadCount, markAsRead } from '../api/message'
import { connectWebSocket, disconnectWebSocket } from '../utils/websocket'
import { APPLICATION_STATUS, formatDateTime } from '../utils/format'

const router = useRouter()
const store = useAppStore()

const companyName = ref('')
const auditStatus = ref(null)
const activeTab = ref('all')
const applications = ref([])
const total = ref(0)
const toast = ref('')

// 消息相关
const conversations = ref([])
const totalUnreadMsg = ref(0)

const LOGO_COLORS = ['#1a56db', '#006591', '#0a8754', '#0078d4', '#e74c3c', '#8e44ad']

function getLogoColor(id) {
  return LOGO_COLORS[(id || 0) % LOGO_COLORS.length]
}

function formatTime(dateStr) {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  const now = new Date()
  const isToday = d.toDateString() === now.toDateString()
  const hhmm = String(d.getHours()).padStart(2, '0') + ':' + String(d.getMinutes()).padStart(2, '0')
  if (isToday) return hhmm
  const yesterday = new Date(now)
  yesterday.setDate(yesterday.getDate() - 1)
  if (d.toDateString() === yesterday.toDateString()) return '昨天 ' + hhmm
  return (d.getMonth() + 1) + '/' + d.getDate() + ' ' + hhmm
}

const auditStatusText = computed(() => ({ 0: '待审核', 1: '已通过', 2: '已拒绝' }[auditStatus.value] ?? '未提交'))

const filterTabs = computed(() => [
  { key: 'all', label: '全部', count: total.value },
  { key: '0', label: '待查看', count: applications.value.filter(a => a.status === 0).length },
  { key: '1', label: '已查看', count: applications.value.filter(a => a.status === 1).length },
  { key: '2', label: '面试邀请', count: applications.value.filter(a => a.status === 2).length },
  { key: '4', label: '已录用', count: applications.value.filter(a => a.status === 4).length },
  { key: '3', label: '不合适', count: applications.value.filter(a => a.status === 3).length }
])

const statCards = computed(() => [
  { label: '总投递', value: total.value, sub: '全部收到的简历', icon: 'folder_open', color: 'text-primary' },
  { label: '待查看', value: applications.value.filter(a => a.status === 0).length, sub: '等待初筛处理', icon: 'mark_email_unread', color: 'text-amber-600' },
  { label: '面试邀请', value: applications.value.filter(a => a.status === 2).length, sub: '推进面试环节', icon: 'event_available', color: 'text-emerald-600' },
  { label: '已录用', value: applications.value.filter(a => a.status === 4).length, sub: '完成录用', icon: 'verified', color: 'text-blue-600' },
  { label: '不合适', value: applications.value.filter(a => a.status === 3).length, sub: '已归档人才库', icon: 'archive', color: 'text-gray-500' }
])

const filteredList = computed(() => {
  if (activeTab.value === 'all') return applications.value
  return applications.value.filter(a => String(a.status) === activeTab.value)
})

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

function goChat(conv) {
  router.push({
    name: 'ChatRoom',
    params: { toUserId: conv.toUserId },
    query: { name: conv.toUserName, jobTitle: conv.jobTitle || '' }
  })
}

async function loadApplications() {
  try {
    let companyId = null
    try {
      const info = await getCompanyInfo()
      companyId = info && info.id
      companyName.value = (info && info.companyName) || ''
      auditStatus.value = info ? info.auditStatus : null
    } catch (e) {
      console.error('获取企业信息失败:', e)
    }
    if (!companyId) {
      showToast('请先完成企业认证')
      return
    }
    const res = await getReceivedApplications({ companyId, page: 1, size: 200 })
    const recs = (res && res.records) || []
    total.value = (res && res.total) || recs.length
    applications.value = recs.map(a => {
      const st = APPLICATION_STATUS[a.status] || { label: '未知', class: 'bg-gray-100 text-gray-600', dot: 'bg-gray-400' }
      return {
        id: a.id,
        jobName: a.jobName,
        resumeTitle: a.resumeTitle,
        createTime: a.createTime,
        hrRemark: a.hrRemark,
        status: a.status,
        statusRaw: a.status,
        statusLabel: st.label,
        statusClass: st.class,
        dotClass: st.dot
      }
    })
  } catch (e) {
    showToast('加载看板数据失败：' + (e.message || '请重试'))
  }
}

async function loadConversations() {
  try {
    const [convRes, unreadRes] = await Promise.all([
      getConversationList(),
      getUnreadCount()
    ])
    conversations.value = convRes || []
    totalUnreadMsg.value = unreadRes || 0
  } catch (e) {
    console.error('加载会话列表失败:', e)
  }
}

function handleNewMessage(msg) {
  // 更新会话列表
  const idx = conversations.value.findIndex(c => c.id === msg.conversationId)
  if (idx >= 0) {
    const conv = conversations.value[idx]
    conv.lastMessage = msg.content
    conv.lastTime = msg.createTime
    if (msg.fromUserId !== store.userId) {
      conv.unreadCount = (conv.unreadCount || 0) + 1
      totalUnreadMsg.value++
    }
    // 移到顶部
    conversations.value.splice(idx, 1)
    conversations.value.unshift(conv)
  } else {
    loadConversations()
  }
}

async function quickReject(c) {
  try {
    await handleApplication(c.id, { status: 3, hrRemark: '不合适，已归档' })
    showToast('已标记为不合适')
    loadApplications()
  } catch (e) {
    showToast(e.message || '操作失败')
  }
}

onMounted(() => {
  loadApplications()
  loadConversations()

  // 建立 WebSocket 连接，实时接收消息
  if (store.userId) {
    connectWebSocket(store.userId, handleNewMessage, () => {})
  }
})

onUnmounted(() => {
  disconnectWebSocket()
})
</script>
