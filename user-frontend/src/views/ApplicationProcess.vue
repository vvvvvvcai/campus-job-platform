<template>
  <div class="min-h-screen bg-background">
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6">
        <h1 class="text-2xl font-bold text-on-surface">招聘流程</h1>
        <p class="text-on-surface-variant mt-1">跟踪候选人招聘进度</p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-8">
      <!-- Horizontal Stepper -->
      <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm mb-8">
        <div class="flex items-center justify-between relative">
          <div class="absolute top-5 left-0 right-0 h-0.5 bg-surface-container-high z-0">
            <div class="h-full bg-primary transition-all duration-500" :style="{ width: ((currentStage) / (stages.length - 1)) * 100 + '%' }"></div>
          </div>
          <div v-for="(stage, index) in stages" :key="index" class="relative z-10 flex flex-col items-center">
            <div
              class="w-10 h-10 rounded-full flex items-center justify-center text-sm font-bold transition-all cursor-pointer"
              :class="currentStage > index ? 'bg-primary text-on-primary' : currentStage === index ? 'bg-primary text-on-primary ring-4 ring-primary/20' : 'bg-surface-container-high text-on-surface-variant'"
              @click="currentStage = index"
            >
              <span v-if="currentStage > index" class="material-symbols-outlined text-lg">check</span>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <span class="mt-2 text-xs font-medium text-center" :class="currentStage >= index ? 'text-primary' : 'text-on-surface-variant'">{{ stage }}</span>
          </div>
        </div>
      </div>

      <div class="grid lg:grid-cols-3 gap-6">
        <!-- Candidate Info -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Candidate Card -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <div class="flex items-start gap-4">
              <div class="w-16 h-16 rounded-full flex items-center justify-center text-white font-bold text-xl shrink-0" style="background: #0037b0">
                李
              </div>
              <div class="flex-1">
                <div class="flex items-center gap-3 mb-1">
                  <h2 class="text-xl font-bold text-on-surface">李明</h2>
                  <span class="px-3 py-1 rounded-full text-xs font-medium bg-primary/10 text-primary">匹配度 95%</span>
                </div>
                <p class="text-on-surface-variant">清华大学 · 计算机科学与技术 · 2026届</p>
                <div class="flex flex-wrap gap-3 mt-3 text-sm text-on-surface-variant">
                  <span class="flex items-center gap-1"><span class="material-symbols-outlined text-base">work</span> 前端开发工程师</span>
                  <span class="flex items-center gap-1"><span class="material-symbols-outlined text-base">schedule</span> 投递于 2026-09-10</span>
                  <span class="flex items-center gap-1"><span class="material-symbols-outlined text-base">school</span> GPA 3.8/4.0</span>
                </div>
              </div>
              <div class="flex gap-2 shrink-0">
                <button class="px-4 py-2 bg-primary/10 text-primary text-sm font-medium rounded-xl hover:bg-primary/20 transition-colors">
                  <span class="material-symbols-outlined text-base align-middle mr-1">download</span>下载简历
                </button>
              </div>
            </div>
          </div>

          <!-- Stage Actions -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h3 class="text-lg font-bold text-on-surface mb-4">{{ stages[currentStage] }} - 操作面板</h3>
            <div class="grid md:grid-cols-3 gap-3 mb-5">
              <button
                v-for="action in stageActions"
                :key="action.label"
                @click="handleAction(action.type)"
                class="p-4 rounded-xl border-2 text-center transition-all hover:shadow-md"
                :class="selectedAction === action.type ? 'border-primary bg-primary/5' : 'border-surface-container-high hover:border-primary/30'"
              >
                <span class="material-symbols-outlined text-2xl mb-1" :class="selectedAction === action.type ? 'text-primary' : 'text-on-surface-variant'">{{ action.icon }}</span>
                <p class="text-sm font-medium" :class="selectedAction === action.type ? 'text-primary' : 'text-on-surface'">{{ action.label }}</p>
              </button>
            </div>
          </div>

          <!-- Notes -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h3 class="text-lg font-bold text-on-surface mb-4 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">edit_note</span>
              备注记录
            </h3>
            <div class="space-y-4 mb-4">
              <div v-for="note in notes" :key="note.id" class="p-4 bg-surface-container rounded-xl">
                <div class="flex items-center justify-between mb-2">
                  <span class="text-sm font-medium text-on-surface">{{ note.author }}</span>
                  <span class="text-xs text-on-surface-variant">{{ note.time }}</span>
                </div>
                <p class="text-sm text-on-surface-variant">{{ note.content }}</p>
              </div>
            </div>
            <div class="flex gap-3">
              <input
                v-model="newNote"
                type="text"
                placeholder="添加备注..."
                class="flex-1 px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm"
                @keyup.enter="addNote"
              />
              <button @click="addNote" class="px-5 py-2.5 bg-primary text-on-primary font-medium rounded-xl hover:bg-primary-container transition-all text-sm">
                添加
              </button>
            </div>
          </div>
        </div>

        <!-- Right Sidebar -->
        <div class="space-y-6">
          <!-- Timeline -->
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

          <!-- Quick Info -->
          <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm">
            <h3 class="text-lg font-bold text-on-surface mb-4">快速信息</h3>
            <div class="space-y-3 text-sm">
              <div class="flex justify-between">
                <span class="text-on-surface-variant">投递时间</span>
                <span class="text-on-surface font-medium">2026-09-10</span>
              </div>
              <div class="flex justify-between">
                <span class="text-on-surface-variant">简历更新</span>
                <span class="text-on-surface font-medium">2026-09-12</span>
              </div>
              <div class="flex justify-between">
                <span class="text-on-surface-variant">笔试成绩</span>
                <span class="text-primary font-medium">92 分</span>
              </div>
              <div class="flex justify-between">
                <span class="text-on-surface-variant">一面评分</span>
                <span class="text-primary font-medium">优秀</span>
              </div>
              <div class="flex justify-between">
                <span class="text-on-surface-variant">面试官</span>
                <span class="text-on-surface font-medium">王工</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const stages = ['投递成功', '简历筛选', '笔试', '一面', '二面', 'Offer']
const currentStage = ref(3)
const selectedAction = ref('')
const newNote = ref('')

const timeline = computed(() => {
  return stages.map((stage, index) => ({
    stage,
    done: index < currentStage.value,
    time: index < currentStage.value ? `2026-09-${10 + index * 2} 14:00` : index === currentStage.value ? '进行中' : ''
  }))
})

const stageActionsMap = {
  0: [{ icon: 'mark_email_read', label: '确认投递', type: 'confirm' }, { icon: 'forward', label: '转交处理', type: 'forward' }, { icon: 'archive', label: '暂存', type: 'archive' }],
  1: [{ icon: 'check_circle', label: '通过筛选', type: 'pass' }, { icon: 'cancel', label: '不通过', type: 'reject' }, { icon: 'rate_review', label: '标记待定', type: 'hold' }],
  2: [{ icon: 'grading', label: '录入成绩', type: 'grade' }, { icon: 'check_circle', label: '通过笔试', type: 'pass' }, { icon: 'cancel', label: '未通过', type: 'reject' }],
  3: [{ icon: 'event_available', label: '通过一面', type: 'pass' }, { icon: 'cancel', label: '未通过', type: 'reject' }, { icon: 'rate_review', label: '标记待定', type: 'hold' }],
  4: [{ icon: 'event_available', label: '通过二面', type: 'pass' }, { icon: 'cancel', label: '未通过', type: 'reject' }, { icon: 'rate_review', label: '标记待定', type: 'hold' }],
  5: [{ icon: 'send', label: '发送Offer', type: 'send' }, { icon: 'edit', label: '修改Offer', type: 'edit' }, { icon: 'cancel', label: '撤回', type: 'revoke' }]
}

const stageActions = computed(() => stageActionsMap[currentStage.value] || [])

const notes = ref([
  { id: 1, author: '张经理', time: '2026-09-12 15:30', content: '简历质量很高，技术栈匹配，推荐进入面试环节。' },
  { id: 2, author: '王工', time: '2026-09-14 10:00', content: '一面表现优秀，基础扎实，沟通能力强，建议安排二面。' }
])

function handleAction(type) {
  selectedAction.value = type
}

function addNote() {
  if (!newNote.value.trim()) return
  notes.value.push({
    id: Date.now(),
    author: '张经理',
    time: new Date().toLocaleString('zh-CN'),
    content: newNote.value.trim()
  })
  newNote.value = ''
}
</script>
