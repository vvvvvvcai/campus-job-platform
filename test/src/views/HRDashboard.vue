<template>
  <div class="min-h-screen bg-background">
    <!-- Welcome Header -->
    <div class="bg-gradient-to-r from-primary to-secondary py-8 px-6">
      <div class="max-w-7xl mx-auto flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-on-primary">欢迎回来，张经理</h1>
          <p class="text-on-primary/75 mt-1">今天是2026年9月14日，祝工作顺利</p>
        </div>
        <div class="flex items-center gap-4">
          <button class="relative p-2 text-on-primary/80 hover:text-on-primary transition-colors">
            <span class="material-symbols-outlined text-2xl">notifications</span>
            <span class="absolute top-1 right-1 w-2.5 h-2.5 bg-error rounded-full"></span>
          </button>
          <div class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center text-on-primary font-bold">张</div>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-8">
      <!-- Metric Cards -->
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-5 mb-8">
        <div v-for="metric in metrics" :key="metric.label" class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between mb-3">
            <div class="w-10 h-10 rounded-xl flex items-center justify-center" :class="metric.iconBg">
              <span class="material-symbols-outlined text-xl" :class="metric.iconColor">{{ metric.icon }}</span>
            </div>
            <span class="text-xs font-medium px-2 py-1 rounded-full" :class="metric.changeClass">{{ metric.change }}</span>
          </div>
          <div class="text-2xl font-bold text-on-surface">{{ metric.value }}</div>
          <div class="text-on-surface-variant text-sm mt-1">{{ metric.label }}</div>
        </div>
      </div>

      <div class="grid lg:grid-cols-3 gap-6">
        <!-- Candidate Feed -->
        <div class="lg:col-span-2">
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high shadow-sm">
            <div class="p-5 border-b border-surface-container-high flex items-center justify-between">
              <h2 class="text-lg font-bold text-on-surface">最新候选人</h2>
              <button class="text-primary text-sm font-medium hover:underline">查看全部</button>
            </div>
            <div class="divide-y divide-surface-container-high">
              <div v-for="candidate in candidates" :key="candidate.id" class="p-5 hover:bg-surface-container-low/50 transition-colors">
                <div class="flex items-start gap-4">
                  <div class="w-11 h-11 rounded-full flex items-center justify-center text-white font-bold text-sm shrink-0" :style="{ background: candidate.avatarColor }">
                    {{ candidate.avatarText }}
                  </div>
                  <div class="flex-1 min-w-0">
                    <div class="flex items-center gap-2 mb-1">
                      <h3 class="font-bold text-on-surface">{{ candidate.name }}</h3>
                      <span class="px-2 py-0.5 rounded-full text-xs font-medium" :class="candidate.matchClass">
                        匹配度 {{ candidate.match }}%
                      </span>
                    </div>
                    <p class="text-on-surface-variant text-sm">{{ candidate.school }} · {{ candidate.major }}</p>
                    <p class="text-on-surface-variant text-sm mt-0.5">应聘：<span class="text-primary font-medium">{{ candidate.position }}</span></p>
                  </div>
                  <div class="flex gap-2 shrink-0">
                    <button class="px-3 py-1.5 bg-primary/10 text-primary text-xs font-medium rounded-lg hover:bg-primary/20 transition-colors">
                      预览
                    </button>
                    <button class="px-3 py-1.5 bg-secondary/10 text-secondary text-xs font-medium rounded-lg hover:bg-secondary/20 transition-colors">
                      邀约
                    </button>
                    <button class="px-3 py-1.5 bg-error/10 text-error text-xs font-medium rounded-lg hover:bg-error/20 transition-colors">
                      拒绝
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Sidebar -->
        <div class="space-y-6">
          <!-- Recruitment Funnel -->
          <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm">
            <h2 class="text-lg font-bold text-on-surface mb-5">招聘漏斗</h2>
            <div class="space-y-4">
              <div v-for="stage in funnelStages" :key="stage.label">
                <div class="flex justify-between text-sm mb-1.5">
                  <span class="text-on-surface-variant">{{ stage.label }}</span>
                  <span class="text-on-surface font-medium">{{ stage.count }}</span>
                </div>
                <div class="w-full h-2.5 bg-surface-container-high rounded-full overflow-hidden">
                  <div class="h-full rounded-full transition-all" :class="stage.barClass" :style="{ width: stage.percent + '%' }"></div>
                </div>
              </div>
            </div>
          </div>

          <!-- Pending Schedule -->
          <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm">
            <h2 class="text-lg font-bold text-on-surface mb-5">待安排面试</h2>
            <div class="space-y-3">
              <div v-for="schedule in pendingSchedule" :key="schedule.id" class="flex items-center gap-3 p-3 bg-surface-container rounded-xl">
                <div class="w-9 h-9 rounded-full flex items-center justify-center text-white text-xs font-bold" :style="{ background: schedule.color }">
                  {{ schedule.initial }}
                </div>
                <div class="flex-1 min-w-0">
                  <p class="text-on-surface text-sm font-medium truncate">{{ schedule.name }}</p>
                  <p class="text-on-surface-variant text-xs">{{ schedule.position }} · {{ schedule.time }}</p>
                </div>
                <button class="text-primary">
                  <span class="material-symbols-outlined text-lg">calendar_today</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const metrics = ref([
  { label: '在招职位', value: '12', icon: 'work', iconBg: 'bg-primary/10', iconColor: 'text-primary', change: '+2', changeClass: 'bg-primary/10 text-primary' },
  { label: '收到简历', value: '328', icon: 'description', iconBg: 'bg-secondary/10', iconColor: 'text-secondary', change: '+45', changeClass: 'bg-secondary/10 text-secondary' },
  { label: '待处理', value: '23', icon: 'pending', iconBg: 'bg-tertiary/10', iconColor: 'text-tertiary', change: '-5', changeClass: 'bg-error/10 text-error' },
  { label: '今日新增', value: '18', icon: 'trending_up', iconBg: 'bg-primary/10', iconColor: 'text-primary', change: '+8', changeClass: 'bg-primary/10 text-primary' }
])

const candidates = ref([
  { id: 1, name: '李明', school: '清华大学', major: '计算机科学', position: '前端开发工程师', match: 95, matchClass: 'bg-primary/10 text-primary', avatarColor: '#0037b0', avatarText: '李' },
  { id: 2, name: '王芳', school: '北京大学', major: '软件工程', position: '后端开发工程师', match: 88, matchClass: 'bg-secondary/10 text-secondary', avatarColor: '#006591', avatarText: '王' },
  { id: 3, name: '张伟', school: '浙江大学', major: '人工智能', position: '算法工程师', match: 92, matchClass: 'bg-primary/10 text-primary', avatarColor: '#623c00', avatarText: '张' },
  { id: 4, name: '陈静', school: '复旦大学', major: '数据科学', position: '数据分析师', match: 85, matchClass: 'bg-tertiary/10 text-tertiary', avatarColor: '#ba1a1a', avatarText: '陈' },
  { id: 5, name: '刘洋', school: '上海交通大学', major: '信息安全', position: '安全工程师', match: 78, matchClass: 'bg-on-surface-variant/10 text-on-surface-variant', avatarColor: '#434655', avatarText: '刘' }
])

const funnelStages = ref([
  { label: '投递', count: 328, percent: 100, barClass: 'bg-primary' },
  { label: '筛选', count: 186, percent: 57, barClass: 'bg-secondary' },
  { label: '笔试', count: 92, percent: 28, barClass: 'bg-tertiary' },
  { label: '面试', count: 45, percent: 14, barClass: 'bg-primary-container' },
  { label: 'Offer', count: 12, percent: 4, barClass: 'bg-tertiary-container' }
])

const pendingSchedule = ref([
  { id: 1, name: '李明', initial: '李', position: '前端开发', time: '明天 14:00', color: '#0037b0' },
  { id: 2, name: '王芳', initial: '王', position: '后端开发', time: '明天 15:30', color: '#006591' },
  { id: 3, name: '张伟', initial: '张', position: '算法工程师', time: '后天 10:00', color: '#623c00' }
])
</script>
