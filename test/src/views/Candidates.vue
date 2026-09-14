<template>
  <div class="min-h-screen bg-background">
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6">
        <h1 class="text-2xl font-bold text-on-surface">候选人管理</h1>
        <p class="text-on-surface-variant mt-1">查看和管理所有候选人信息</p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-8">
      <!-- Metric Cards -->
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        <div v-for="metric in metrics" :key="metric.label" class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm">
          <div class="text-2xl font-bold text-on-surface">{{ metric.value }}</div>
          <div class="text-on-surface-variant text-sm mt-1">{{ metric.label }}</div>
          <div class="text-xs mt-2" :class="metric.changeClass">{{ metric.change }}</div>
        </div>
      </div>

      <!-- Filter Tabs -->
      <div class="flex flex-wrap gap-2 mb-6 border-b border-surface-container-high pb-4">
        <button
          v-for="tab in tabs"
          :key="tab.key"
          @click="activeTab = tab.key"
          class="px-4 py-2 rounded-xl text-sm font-medium transition-all"
          :class="activeTab === tab.key ? 'bg-primary text-on-primary shadow-sm' : 'bg-surface-container text-on-surface-variant hover:bg-surface-container-high'"
        >
          {{ tab.label }}
          <span class="ml-1.5 px-1.5 py-0.5 rounded-full text-xs" :class="activeTab === tab.key ? 'bg-white/20' : 'bg-surface-container-high'">{{ tab.count }}</span>
        </button>
      </div>

      <!-- Batch Actions -->
      <div v-if="selectedIds.length" class="bg-primary/5 border border-primary/20 rounded-xl p-4 mb-6 flex items-center justify-between">
        <span class="text-sm text-on-surface">已选择 {{ selectedIds.length }} 名候选人</span>
        <div class="flex gap-2">
          <button class="px-4 py-1.5 bg-secondary text-on-primary text-sm font-medium rounded-lg hover:bg-secondary/90">批量邀约</button>
          <button class="px-4 py-1.5 bg-error text-on-primary text-sm font-medium rounded-lg hover:bg-error/90">批量拒绝</button>
          <button @click="selectedIds = []" class="px-4 py-1.5 border border-surface-container-high text-on-surface text-sm font-medium rounded-lg hover:bg-surface-container">取消选择</button>
        </div>
      </div>

      <!-- Candidate Cards -->
      <div class="space-y-4">
        <div
          v-for="candidate in filteredCandidates"
          :key="candidate.id"
          class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm hover:shadow-md transition-all"
        >
          <div class="flex items-start gap-4">
            <input
              v-model="selectedIds"
              :value="candidate.id"
              type="checkbox"
              class="mt-1 w-4 h-4 rounded border-surface-container-high text-primary focus:ring-primary/30"
            />
            <div class="w-12 h-12 rounded-full flex items-center justify-center text-white font-bold text-sm shrink-0" :style="{ background: candidate.avatarColor }">
              {{ candidate.avatarText }}
            </div>
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-2 flex-wrap">
                <h3 class="font-bold text-on-surface">{{ candidate.name }}</h3>
                <span class="px-2.5 py-0.5 rounded-full text-xs font-medium" :class="candidate.matchClass">
                  匹配度 {{ candidate.match }}%
                </span>
                <span class="px-2.5 py-0.5 rounded-full text-xs font-medium" :class="candidate.statusClass">
                  {{ candidate.status }}
                </span>
              </div>
              <p class="text-on-surface-variant text-sm mt-1">{{ candidate.school }} · {{ candidate.major }}</p>
              <p class="text-on-surface-variant text-sm">应聘：<span class="text-primary font-medium">{{ candidate.position }}</span></p>
            </div>
            <div class="flex gap-2 shrink-0">
              <button class="p-2 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="查看简历">
                <span class="material-symbols-outlined text-lg">visibility</span>
              </button>
              <button class="p-2 rounded-lg hover:bg-primary/10 transition-colors text-primary" title="邀约面试">
                <span class="material-symbols-outlined text-lg">event</span>
              </button>
              <button class="p-2 rounded-lg hover:bg-error/10 transition-colors text-error" title="不合适">
                <span class="material-symbols-outlined text-lg">close</span>
              </button>
            </div>
          </div>
        </div>

        <div v-if="filteredCandidates.length === 0" class="text-center py-16 text-on-surface-variant">
          <span class="material-symbols-outlined text-5xl text-on-surface-variant/30 mb-3 block">person_off</span>
          暂无候选人数据
        </div>
      </div>

      <!-- Pagination -->
      <div class="mt-6 flex items-center justify-between text-sm">
        <span class="text-on-surface-variant">共 {{ filteredCandidates.length }} 条记录</span>
        <div class="flex gap-1">
          <button class="px-3 py-1.5 rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container transition-colors">上一页</button>
          <button class="px-3 py-1.5 rounded-lg bg-primary text-on-primary font-medium">1</button>
          <button class="px-3 py-1.5 rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container transition-colors">2</button>
          <button class="px-3 py-1.5 rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container transition-colors">下一页</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const activeTab = ref('all')
const selectedIds = ref([])

const tabs = [
  { key: 'all', label: '全部投递', count: 156 },
  { key: 'pending', label: '待初筛', count: 42 },
  { key: 'invite', label: '待邀约', count: 28 },
  { key: 'offer', label: '已发Offer', count: 12 },
  { key: 'reject', label: '不合适', count: 35 }
]

const metrics = ref([
  { label: '总投递', value: '156', change: '+23 本周', changeClass: 'text-primary' },
  { label: '待初筛', value: '42', change: '+8 今日', changeClass: 'text-tertiary' },
  { label: '待邀约', value: '28', change: '5 即将过期', changeClass: 'text-error' },
  { label: '已发Offer', value: '12', change: '+3 本月', changeClass: 'text-primary' }
])

const candidates = ref([
  { id: 1, name: '李明', school: '清华大学', major: '计算机科学', position: '前端开发工程师', match: 95, status: '待初筛', statusKey: 'pending', avatarColor: '#0037b0', avatarText: '李', matchClass: 'bg-primary/10 text-primary', statusClass: 'bg-tertiary/10 text-tertiary' },
  { id: 2, name: '王芳', school: '北京大学', major: '软件工程', position: '后端开发工程师', match: 88, status: '待邀约', statusKey: 'invite', avatarColor: '#006591', avatarText: '王', matchClass: 'bg-secondary/10 text-secondary', statusClass: 'bg-primary/10 text-primary' },
  { id: 3, name: '张伟', school: '浙江大学', major: '人工智能', position: '算法工程师', match: 92, status: '已发Offer', statusKey: 'offer', avatarColor: '#623c00', avatarText: '张', matchClass: 'bg-primary/10 text-primary', statusClass: 'bg-primary-container/10 text-primary-container' },
  { id: 4, name: '陈静', school: '复旦大学', major: '数据科学', position: '数据分析师', match: 85, status: '待初筛', statusKey: 'pending', avatarColor: '#ba1a1a', avatarText: '陈', matchClass: 'bg-tertiary/10 text-tertiary', statusClass: 'bg-tertiary/10 text-tertiary' },
  { id: 5, name: '刘洋', school: '上海交通大学', major: '信息安全', position: '安全工程师', match: 78, status: '不合适', statusKey: 'reject', avatarColor: '#434655', avatarText: '刘', matchClass: 'bg-on-surface-variant/10 text-on-surface-variant', statusClass: 'bg-error/10 text-error' },
  { id: 6, name: '赵雪', school: '南京大学', major: '软件工程', position: '前端开发工程师', match: 90, status: '待邀约', statusKey: 'invite', avatarColor: '#0037b0', avatarText: '赵', matchClass: 'bg-primary/10 text-primary', statusClass: 'bg-primary/10 text-primary' },
  { id: 7, name: '孙磊', school: '中国科学技术大学', major: '计算机科学', position: '算法工程师', match: 82, status: '待初筛', statusKey: 'pending', avatarColor: '#006591', avatarText: '孙', matchClass: 'bg-secondary/10 text-secondary', statusClass: 'bg-tertiary/10 text-tertiary' },
  { id: 8, name: '周婷', school: '武汉大学', major: '软件工程', position: '测试工程师', match: 75, status: '已发Offer', statusKey: 'offer', avatarColor: '#623c00', avatarText: '周', matchClass: 'bg-on-surface-variant/10 text-on-surface-variant', statusClass: 'bg-primary-container/10 text-primary-container' }
])

const filteredCandidates = computed(() => {
  if (activeTab.value === 'all') return candidates.value
  return candidates.value.filter(c => c.statusKey === activeTab.value)
})
</script>
