<template>
  <div class="min-h-screen bg-background">
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6">
        <h1 class="text-2xl font-bold text-on-surface">职位管理</h1>
        <p class="text-on-surface-variant mt-1">管理企业所有招聘职位</p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-8">
      <!-- KPI Cards -->
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        <div v-for="kpi in kpis" :key="kpi.label" class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm">
          <div class="flex items-center gap-3 mb-2">
            <div class="w-9 h-9 rounded-xl flex items-center justify-center" :class="kpi.iconBg">
              <span class="material-symbols-outlined text-lg" :class="kpi.iconColor">{{ kpi.icon }}</span>
            </div>
            <span class="text-xs font-medium" :class="kpi.changeClass">{{ kpi.change }}</span>
          </div>
          <div class="text-2xl font-bold text-on-surface">{{ kpi.value }}</div>
          <div class="text-on-surface-variant text-sm mt-0.5">{{ kpi.label }}</div>
        </div>
      </div>

      <!-- Search & Filter Bar -->
      <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high shadow-sm mb-6">
        <div class="flex flex-col md:flex-row gap-4">
          <div class="relative flex-1">
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">search</span>
            <input v-model="search" type="text" placeholder="搜索职位名称..." class="w-full pl-10 pr-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm" />
          </div>
          <select v-model="statusFilter" class="px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/30">
            <option value="">全部状态</option>
            <option>在线</option>
            <option>暂停</option>
            <option>已下线</option>
          </select>
          <button class="px-5 py-2.5 bg-primary text-on-primary font-semibold rounded-xl hover:bg-primary-container transition-all text-sm whitespace-nowrap">
            <span class="material-symbols-outlined text-base align-middle mr-1">add</span>
            发布新职位
          </button>
        </div>
      </div>

      <!-- Job Table -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high shadow-sm overflow-hidden">
        <div class="overflow-x-auto">
          <table class="w-full text-sm">
            <thead>
              <tr class="border-b border-surface-container-high bg-surface-container-low/50">
                <th class="text-left px-5 py-3 font-semibold text-on-surface-variant">职位名称</th>
                <th class="text-left px-5 py-3 font-semibold text-on-surface-variant">部门</th>
                <th class="text-left px-5 py-3 font-semibold text-on-surface-variant">类型</th>
                <th class="text-left px-5 py-3 font-semibold text-on-surface-variant">薪资</th>
                <th class="text-left px-5 py-3 font-semibold text-on-surface-variant">投递数</th>
                <th class="text-left px-5 py-3 font-semibold text-on-surface-variant">状态</th>
                <th class="text-left px-5 py-3 font-semibold text-on-surface-variant">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="job in filteredJobs" :key="job.id" class="border-b border-surface-container-high/50 hover:bg-surface-container-low/30 transition-colors">
                <td class="px-5 py-4">
                  <div class="font-medium text-on-surface">{{ job.title }}</div>
                  <div class="text-on-surface-variant text-xs mt-0.5">{{ job.location }}</div>
                </td>
                <td class="px-5 py-4 text-on-surface-variant">{{ job.department }}</td>
                <td class="px-5 py-4">
                  <span class="px-2 py-1 rounded-lg text-xs font-medium bg-surface-container text-on-surface-variant">{{ job.type }}</span>
                </td>
                <td class="px-5 py-4 text-on-surface font-medium">{{ job.salary }}</td>
                <td class="px-5 py-4 text-on-surface">{{ job.resumes }}</td>
                <td class="px-5 py-4">
                  <span class="px-2.5 py-1 rounded-full text-xs font-medium" :class="getStatusClass(job.status)">{{ job.status }}</span>
                </td>
                <td class="px-5 py-4">
                  <div class="flex items-center gap-1">
                    <button class="p-1.5 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="编辑">
                      <span class="material-symbols-outlined text-lg">edit</span>
                    </button>
                    <button
                      @click="toggleStatus(job)"
                      class="p-1.5 rounded-lg hover:bg-surface-container transition-colors"
                      :class="job.status === '在线' ? 'text-tertiary' : 'text-primary'"
                      :title="job.status === '在线' ? '暂停' : '上线'"
                    >
                      <span class="material-symbols-outlined text-lg">{{ job.status === '在线' ? 'pause' : 'play_arrow' }}</span>
                    </button>
                    <button @click="deleteJob(job.id)" class="p-1.5 rounded-lg hover:bg-error/10 transition-colors text-error" title="删除">
                      <span class="material-symbols-outlined text-lg">delete</span>
                    </button>
                  </div>
                </td>
              </tr>
              <tr v-if="filteredJobs.length === 0">
                <td colspan="7" class="px-5 py-12 text-center text-on-surface-variant">
                  <span class="material-symbols-outlined text-4xl text-on-surface-variant/30 mb-2 block">work_off</span>
                  暂无职位数据
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div class="px-5 py-4 border-t border-surface-container-high flex items-center justify-between text-sm">
          <span class="text-on-surface-variant">共 {{ filteredJobs.length }} 条记录</span>
          <div class="flex gap-1">
            <button class="px-3 py-1.5 rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container transition-colors">上一页</button>
            <button class="px-3 py-1.5 rounded-lg bg-primary text-on-primary font-medium">1</button>
            <button class="px-3 py-1.5 rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container transition-colors">2</button>
            <button class="px-3 py-1.5 rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container transition-colors">下一页</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const search = ref('')
const statusFilter = ref('')

const kpis = ref([
  { label: '在线职位', value: '12', icon: 'work', iconBg: 'bg-primary/10', iconColor: 'text-primary', change: '+2', changeClass: 'text-primary' },
  { label: '收到简历', value: '328', icon: 'description', iconBg: 'bg-secondary/10', iconColor: 'text-secondary', change: '+45', changeClass: 'text-secondary' },
  { label: '待处理', value: '23', icon: 'pending', iconBg: 'bg-tertiary/10', iconColor: 'text-tertiary', change: '-5', changeClass: 'text-error' },
  { label: '今日新增', value: '18', icon: 'trending_up', iconBg: 'bg-primary/10', iconColor: 'text-primary', change: '+8', changeClass: 'text-primary' }
])

const jobs = ref([
  { id: 1, title: '前端开发工程师', department: '技术研发部', type: '全职', salary: '25-40K', resumes: 86, status: '在线', location: '北京' },
  { id: 2, title: '后端开发工程师', department: '技术研发部', type: '全职', salary: '22-38K', resumes: 64, status: '在线', location: '上海' },
  { id: 3, title: '产品经理', department: '产品部', type: '全职', salary: '20-35K', resumes: 42, status: '暂停', location: '深圳' },
  { id: 4, title: 'UI设计师', department: '设计部', type: '实习', salary: '300/天', resumes: 31, status: '在线', location: '杭州' },
  { id: 5, title: '数据分析师', department: '数据部', type: '全职', salary: '18-30K', resumes: 28, status: '已下线', location: '成都' },
  { id: 6, title: '算法工程师', department: 'AI实验室', type: '全职', salary: '30-50K', resumes: 53, status: '在线', location: '北京' },
  { id: 7, title: '测试工程师', department: '质量保障部', type: '全职', salary: '15-25K', resumes: 19, status: '在线', location: '广州' },
  { id: 8, title: '运维工程师', department: '基础设施部', type: '全职', salary: '18-30K', resumes: 15, status: '暂停', location: '北京' }
])

const filteredJobs = computed(() => {
  return jobs.value.filter(j => {
    const matchSearch = !search.value || j.title.includes(search.value)
    const matchStatus = !statusFilter.value || j.status === statusFilter.value
    return matchSearch && matchStatus
  })
})

function getStatusClass(status) {
  if (status === '在线') return 'bg-primary/10 text-primary'
  if (status === '暂停') return 'bg-tertiary/10 text-tertiary'
  return 'bg-on-surface-variant/10 text-on-surface-variant'
}

function toggleStatus(job) {
  if (job.status === '在线') job.status = '暂停'
  else if (job.status === '暂停') job.status = '在线'
  else job.status = '在线'
}

function deleteJob(id) {
  jobs.value = jobs.value.filter(j => j.id !== id)
}
</script>
