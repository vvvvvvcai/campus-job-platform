<template>
  <div class="min-h-screen bg-background flex flex-col">
    <!-- Header -->
    <div class="bg-white border-b border-gray-200 shadow-sm shrink-0 z-10">
      <div class="max-w-3xl mx-auto px-4 h-14 flex items-center gap-3">
        <button @click="router.back()" class="w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors">
          <span class="material-symbols-outlined text-gray-500 text-[20px]">arrow_back</span>
        </button>
        <div class="w-9 h-9 rounded-full bg-gradient-to-br from-blue-500 to-blue-600 flex items-center justify-center text-white font-bold text-sm shrink-0 shadow-sm">
          {{ (toUserName || '对').charAt(0) }}
        </div>
        <div class="flex-1 min-w-0">
          <h1 class="text-sm font-bold text-gray-900 truncate">{{ toUserName }}</h1>
          <p v-if="jobTitle" class="text-[11px] text-gray-400 truncate">{{ jobTitle }}</p>
        </div>
      </div>
    </div>

    <!-- Error Banner -->
    <div v-if="errorMsg" class="shrink-0 bg-red-50 border-b border-red-200 px-4 py-2 flex items-center gap-2">
      <span class="material-symbols-outlined text-red-500 text-[16px]">error</span>
      <span class="text-xs text-red-600 flex-1">{{ errorMsg }}</span>
      <button @click="retryInit" class="text-xs text-red-500 font-medium hover:underline">重试</button>
    </div>

    <!-- Messages Area -->
    <div ref="messageContainer" class="flex-1 overflow-y-auto px-4 py-4">
      <div class="max-w-3xl mx-auto space-y-3">
        <!-- Loading -->
        <div v-if="loading" class="flex justify-center py-8">
          <div class="flex items-center gap-2 px-4 py-2 bg-white/80 rounded-full shadow-sm">
            <span class="material-symbols-outlined text-blue-500 text-lg animate-spin">progress_activity</span>
            <span class="text-xs text-gray-500">加载消息中...</span>
          </div>
        </div>

        <!-- Messages -->
        <template v-for="(msg, index) in messages" :key="msg.id || index">
          <div v-if="shouldShowTime(msg, index)" class="flex justify-center my-2">
            <span class="px-3 py-1 bg-gray-200/80 text-gray-500 text-[10px] rounded-full">{{ formatTime(msg.createTime) }}</span>
          </div>

          <!-- My message (right) -->
          <div v-if="isMyMessage(msg)" class="flex justify-end items-end gap-2">
            <div class="max-w-[75%]">
              <div class="bg-gradient-to-br from-blue-500 to-blue-600 text-white px-4 py-2.5 rounded-2xl rounded-br-md text-sm leading-relaxed whitespace-pre-wrap shadow-sm">
                {{ msg.content }}
              </div>
            </div>
            <div class="w-8 h-8 rounded-full bg-gradient-to-br from-blue-500 to-blue-600 flex items-center justify-center text-white font-bold text-[11px] shrink-0 shadow-sm">
              {{ myName.charAt(0) }}
            </div>
          </div>

          <!-- Other's message (left) -->
          <div v-else class="flex justify-start items-end gap-2">
            <div class="w-8 h-8 rounded-full bg-gradient-to-br from-gray-400 to-gray-500 flex items-center justify-center text-white font-bold text-[11px] shrink-0 shadow-sm">
              {{ (toUserName || '对').charAt(0) }}
            </div>
            <div class="max-w-[75%]">
              <div class="bg-white text-gray-800 px-4 py-2.5 rounded-2xl rounded-bl-md text-sm leading-relaxed whitespace-pre-wrap shadow-sm border border-gray-100">
                {{ msg.content }}
              </div>
            </div>
          </div>
        </template>

        <!-- Empty State -->
        <div v-if="!loading && messages.length === 0" class="flex flex-col items-center justify-center py-16">
          <div class="w-20 h-20 rounded-full bg-blue-50 flex items-center justify-center mb-3">
            <span class="material-symbols-outlined text-4xl text-blue-300">chat</span>
          </div>
          <p class="text-sm text-gray-400 mb-1">开始聊天吧</p>
          <p class="text-xs text-gray-300">发送消息与对方开始沟通</p>
        </div>
      </div>
    </div>

    <!-- Input Area -->
    <div class="bg-white border-t border-gray-200 shrink-0 z-10">
      <div class="max-w-3xl mx-auto px-4 py-3">
        <div class="flex gap-2 items-end">
          <div class="flex-1 relative">
            <textarea
              v-model="inputText"
              @keydown.enter.exact.prevent="handleSend"
              @input="autoResize"
              ref="textareaRef"
              rows="1"
              placeholder="输入消息..."
              class="w-full resize-none px-4 py-2.5 bg-gray-50 text-gray-800 text-sm rounded-xl border border-gray-200 focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-400 transition-all max-h-24 overflow-y-auto placeholder:text-gray-400"
              :style="{ minHeight: '40px' }"
            ></textarea>
          </div>
          <button
            @click="handleSend"
            :disabled="!inputText.trim() || sending"
            class="h-10 px-5 bg-gradient-to-r from-blue-500 to-blue-600 text-white text-sm font-semibold rounded-xl hover:from-blue-600 hover:to-blue-700 transition-all disabled:opacity-40 disabled:cursor-not-allowed shrink-0 shadow-sm flex items-center gap-1.5"
          >
            <span v-if="sending" class="material-symbols-outlined text-[16px] animate-spin">progress_activity</span>
            <span v-else class="material-symbols-outlined text-[16px]">send</span>
            发送
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getMessageHistory, sendMessage as sendMsgApi, getOrCreateConversation, markAsRead } from '../api/message'
import { connectWebSocket, disconnectWebSocket } from '../utils/websocket'

const route = useRoute()
const router = useRouter()
const store = useAppStore()

const currentUserId = ref(null)
const messages = ref([])
const inputText = ref('')
const loading = ref(true)
const sending = ref(false)
const messageContainer = ref(null)
const textareaRef = ref(null)
const toUserName = ref('')
const jobTitle = ref('')
const conversationId = ref(null)
const myName = ref('我')
const errorMsg = ref('')

function isMyMessage(msg) {
  return Number(msg.fromUserId) === Number(currentUserId.value)
}

function autoResize() {
  const el = textareaRef.value
  if (!el) return
  el.style.height = 'auto'
  el.style.height = Math.min(el.scrollHeight, 96) + 'px'
}

function parseDate(dateStr) {
  if (!dateStr) return null
  if (Array.isArray(dateStr)) {
    const [y, m, d, h = 0, min = 0, s = 0] = dateStr
    return new Date(y, m - 1, d, h, min, s)
  }
  return new Date(dateStr)
}

function formatTime(dateStr) {
  const d = parseDate(dateStr)
  if (!d || isNaN(d.getTime())) return ''
  const now = new Date()
  const hhmm = String(d.getHours()).padStart(2, '0') + ':' + String(d.getMinutes()).padStart(2, '0')
  const isToday = d.toDateString() === now.toDateString()
  if (isToday) return hhmm
  const yesterday = new Date(now)
  yesterday.setDate(yesterday.getDate() - 1)
  if (d.toDateString() === yesterday.toDateString()) return '昨天 ' + hhmm
  return (d.getMonth() + 1) + '月' + d.getDate() + '日 ' + hhmm
}

function shouldShowTime(msg, index) {
  if (index === 0) return true
  const prev = messages.value[index - 1]
  if (!prev) return true
  const d1 = parseDate(msg.createTime)
  const d2 = parseDate(prev.createTime)
  if (!d1 || !d2) return true
  return (d1 - d2) > 300000
}

function scrollToBottom() {
  nextTick(() => {
    if (messageContainer.value) {
      messageContainer.value.scrollTop = messageContainer.value.scrollHeight
    }
  })
}

async function handleSend() {
  const text = inputText.value.trim()
  if (!text || sending.value) return

  if (!conversationId.value) {
    errorMsg.value = '会话未建立，请等待加载完成后重试'
    return
  }

  sending.value = true
  inputText.value = ''
  if (textareaRef.value) textareaRef.value.style.height = 'auto'

  const optimisticMsg = {
    id: 'tmp_' + Date.now(),
    fromUserId: Number(currentUserId.value),
    toUserId: Number(route.params.toUserId),
    content: text,
    createTime: new Date().toISOString(),
    conversationId: conversationId.value,
    msgType: 1,
    isRead: 0
  }
  messages.value.push(optimisticMsg)
  scrollToBottom()

  try {
    const res = await sendMsgApi({ conversationId: conversationId.value, content: text, msgType: 1 })
    if (res && res.id) {
      const idx = messages.value.findIndex(m => m.id === optimisticMsg.id)
      if (idx !== -1) messages.value[idx] = res
    }
  } catch (e) {
    console.error('发送失败:', e)
    const idx = messages.value.findIndex(m => m.id === optimisticMsg.id)
    if (idx !== -1) messages.value.splice(idx, 1)
    inputText.value = text
    errorMsg.value = '发送失败: ' + (e.message || '网络错误')
    setTimeout(() => { errorMsg.value = '' }, 5000)
  } finally {
    sending.value = false
  }
}

function onNewMessage(msg) {
  if (Number(msg.conversationId) === Number(conversationId.value)) {
    messages.value.push(msg)
    scrollToBottom()
  }
}

async function initChat() {
  loading.value = true
  errorMsg.value = ''
  currentUserId.value = store.userId
  myName.value = (store.user && store.user.name) || '我'
  const toUserId = Number(route.params.toUserId)
  const jobId = route.query.jobId ? Number(route.query.jobId) : null
  toUserName.value = route.query.name || '对方'
  jobTitle.value = route.query.jobTitle || ''

  console.log('[ChatRoom] initChat', { currentUserId: currentUserId.value, toUserId, jobId })

  if (!toUserId) {
    errorMsg.value = '无效的聊天对象'
    loading.value = false
    return
  }

  try {
    const conv = await getOrCreateConversation({ toUserId, jobId })
    console.log('[ChatRoom] conversation:', conv)
    if (conv && conv.id) {
      conversationId.value = conv.id
      toUserName.value = conv.toUserName || toUserName.value
      const res = await getMessageHistory(conv.id, { page: 1, size: 100 })
      console.log('[ChatRoom] history:', res)
      if (Array.isArray(res)) {
        messages.value = res
      } else if (res && Array.isArray(res.records)) {
        messages.value = res.records
      } else {
        messages.value = []
      }
      scrollToBottom()
      await markAsRead(conv.id)
      connectWebSocket(currentUserId.value, onNewMessage, null)
    } else {
      errorMsg.value = '会话创建失败，请稍后重试'
    }
  } catch (e) {
    console.error('[ChatRoom] 初始化失败:', e)
    errorMsg.value = '加载聊天失败: ' + (e.message || '网络错误')
  } finally {
    loading.value = false
  }
}

function retryInit() {
  initChat()
}

onMounted(initChat)

onUnmounted(() => {
  disconnectWebSocket()
})
</script>
