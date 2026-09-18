<template>
  <div class="min-h-screen bg-background">
    <div class="max-w-4xl mx-auto px-6 py-6">
      <h1 class="text-2xl font-bold text-on-surface mb-6">消息中心</h1>

      <!-- Tabs -->
      <div class="flex gap-1 mb-6 bg-surface-container-low rounded-xl p-1">
        <button @click="activeTab = 'chat'" :class="['flex-1 py-2.5 rounded-lg text-sm font-medium transition-colors', activeTab === 'chat' ? 'bg-primary text-on-primary' : 'text-on-surface-variant hover:bg-surface-container']">
          聊天消息
          <span v-if="unreadMsgCount > 0" class="ml-1 px-1.5 py-0.5 bg-red-500 text-white text-[10px] rounded-full">{{ unreadMsgCount > 99 ? '99+' : unreadMsgCount }}</span>
        </button>
        <button @click="activeTab = 'notification'" :class="['flex-1 py-2.5 rounded-lg text-sm font-medium transition-colors', activeTab === 'notification' ? 'bg-primary text-on-primary' : 'text-on-surface-variant hover:bg-surface-container']">
          系统通知
          <span v-if="unreadNotiCount > 0" class="ml-1 px-1.5 py-0.5 bg-red-500 text-white text-[10px] rounded-full">{{ unreadNotiCount > 99 ? '99+' : unreadNotiCount }}</span>
        </button>
      </div>

      <!-- Chat List -->
      <div v-if="activeTab === 'chat'">
        <div v-if="conversations.length" class="space-y-1">
          <div v-for="conv in conversations" :key="conv.id"
            @click="goChat(conv)"
            class="flex items-center gap-4 p-4 bg-surface-container-lowest rounded-xl border border-surface-container-high hover:border-primary/30 hover:shadow-sm transition-all cursor-pointer">
            <div class="w-11 h-11 rounded-full flex items-center justify-center text-white font-bold text-sm shrink-0" :style="{ background: getLogoColor(conv.toUserId) }">
              {{ (conv.toUserName || '用').charAt(0) }}
            </div>
            <div class="flex-1 min-w-0">
              <div class="flex items-center justify-between mb-1">
                <span class="text-sm font-semibold text-on-surface truncate">{{ conv.toUserName || '用户' }}</span>
                <span class="text-[10px] text-on-surface-variant shrink-0 ml-2">{{ formatTime(conv.lastTime) }}</span>
              </div>
              <p class="text-xs text-on-surface-variant truncate">{{ conv.lastMessage || '暂无消息' }}</p>
            </div>
            <span v-if="conv.unreadCount > 0" class="w-5 h-5 bg-red-500 text-white text-[10px] font-semibold rounded-full flex items-center justify-center shrink-0">{{ conv.unreadCount > 99 ? '99+' : conv.unreadCount }}</span>
          </div>
        </div>
        <div v-else class="text-center py-16">
          <span class="material-symbols-outlined text-5xl text-on-surface-variant/30 mb-3 block">forum</span>
          <p class="text-sm text-on-surface-variant">暂无聊天记录</p>
        </div>
      </div>

      <!-- Notification List -->
      <div v-if="activeTab === 'notification'">
        <div v-if="notifications.length" class="space-y-1">
          <div v-for="noti in notifications" :key="noti.id"
            class="p-4 bg-surface-container-lowest rounded-xl border border-surface-container-high">
            <div class="flex items-start gap-3">
              <span class="material-symbols-outlined text-xl mt-0.5" :class="notiIconClass(noti.notiType)">{{ notiIcon(noti.notiType) }}</span>
              <div class="flex-1 min-w-0">
                <div class="flex items-center justify-between mb-1">
                  <span class="text-sm font-semibold text-on-surface">{{ noti.title }}</span>
                  <span class="text-[10px] text-on-surface-variant shrink-0 ml-2">{{ formatTime(noti.createTime) }}</span>
                </div>
                <p class="text-xs text-on-surface-variant leading-relaxed">{{ noti.content }}</p>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="text-center py-16">
          <span class="material-symbols-outlined text-5xl text-on-surface-variant/30 mb-3 block">notifications</span>
          <p class="text-sm text-on-surface-variant">暂无通知</p>
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
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getConversationList, getNotificationList, getUnreadCount, getNotificationUnreadCount } from '../api/message'
import { formatDateTime } from '../utils/format'

const router = useRouter()
const route = useRoute()
const activeTab = ref('chat')
const conversations = ref([])
const notifications = ref([])
const unreadMsgCount = ref(0)
const unreadNotiCount = ref(0)
const toast = ref('')

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

function notiIcon(type) {
  return { 1: 'description', 2: 'video_chat', 3: 'campaign' }[type] || 'info'
}

function notiIconClass(type) {
  return { 1: 'text-primary', 2: 'text-emerald-500', 3: 'text-amber-500' }[type] || 'text-on-surface-variant'
}

function goChat(conv) {
  router.push({ name: 'ChatRoom', params: { toUserId: conv.toUserId }, query: { name: conv.toUserName, jobTitle: conv.jobTitle || '' } })
}

async function loadData() {
  try {
    const [convRes, unreadRes] = await Promise.all([
      getConversationList(),
      getUnreadCount()
    ])
    conversations.value = convRes || []
    unreadMsgCount.value = unreadRes || 0
  } catch (e) {
    console.error('加载聊天列表失败:', e)
  }
}

async function loadNotifications() {
  try {
    const [notiRes, unreadRes] = await Promise.all([
      getNotificationList({ page: 1, size: 50 }),
      getNotificationUnreadCount()
    ])
    notifications.value = notiRes || []
    unreadNotiCount.value = unreadRes || 0
  } catch (e) {
    console.error('加载通知失败:', e)
  }
}

watch(activeTab, (tab) => {
  if (tab === 'chat') loadData()
  else loadNotifications()
})

onMounted(() => {
  loadData()
})

// 路由变化时重新加载数据（从聊天页返回时刷新已读状态）
watch(() => route.path, (newPath) => {
  if (newPath === '/messages') {
    if (activeTab.value === 'chat') loadData()
    else loadNotifications()
  }
})
</script>
