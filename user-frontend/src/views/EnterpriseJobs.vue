<template>
  <div class="min-h-screen bg-background">
    <!-- 统计卡片区域 -->
    <div class="max-w-7xl mx-auto px-6 pt-6">
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5">
        <!-- 在招职位 -->
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-body-sm font-medium text-on-surface-variant">在招职位</span>
            <div class="w-10 h-10 rounded-xl bg-primary/10 flex items-center justify-center">
              <span class="material-symbols-outlined text-primary text-xl">work</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-on-surface">12</span>
            <span class="text-body-sm text-on-surface-variant">个职位在线</span>
          </div>
          <div class="flex items-center justify-between mt-3 pt-3 border-t border-surface-container-high">
            <div class="flex items-center gap-1.5 text-body-sm text-primary">
              <span class="material-symbols-outlined text-base">check_circle</span>
              <span>全部符合合规标准</span>
            </div>
            <span class="text-body-sm text-on-surface-variant">总配额: 20</span>
          </div>
        </div>

        <!-- 收到简历总数 -->
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-body-sm font-medium text-on-surface-variant">收到简历总数</span>
            <div class="w-10 h-10 rounded-xl bg-secondary/10 flex items-center justify-center">
              <span class="material-symbols-outlined text-secondary text-xl">mail</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-on-surface">1,428</span>
            <span class="text-body-sm text-on-surface-variant">份历史投递</span>
          </div>
          <div class="flex items-center gap-2 mt-3 pt-3 border-t border-surface-container-high">
            <span class="material-symbols-outlined text-base text-primary">show_chart</span>
            <span class="text-body-sm text-primary font-medium">+14.2%</span>
            <span class="text-body-sm text-on-surface-variant">周环比</span>
          </div>
        </div>

        <!-- 今日新增投递 -->
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-body-sm font-medium text-on-surface-variant">今日新增投递</span>
            <div class="w-10 h-10 rounded-xl bg-emerald-50 flex items-center justify-center">
              <span class="material-symbols-outlined text-emerald-600 text-xl">trending_up</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-on-surface">46</span>
            <span class="text-body-sm text-on-surface-variant">份</span>
            <span class="px-2 py-0.5 bg-emerald-50 text-emerald-600 text-xs font-medium rounded-full">实时</span>
          </div>
          <div class="flex items-center justify-between mt-3 pt-3 border-t border-surface-container-high">
            <span class="text-body-sm text-on-surface-variant">待初筛: 32 份</span>
            <span class="text-body-sm text-primary font-medium cursor-pointer hover:underline">快速甄选 →</span>
          </div>
        </div>

        <!-- 已暂停/归档 -->
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-body-sm font-medium text-on-surface-variant">已暂停 / 归档</span>
            <div class="w-10 h-10 rounded-xl bg-surface-container flex items-center justify-center">
              <span class="material-symbols-outlined text-on-surface-variant text-xl">pause_circle</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-on-surface">4</span>
            <span class="text-body-sm text-on-surface-variant">个职位休眠</span>
          </div>
          <div class="flex items-center justify-between mt-3 pt-3 border-t border-surface-container-high">
            <span class="text-body-sm text-on-surface-variant">3 暂停 · 1 已关闭</span>
            <span class="text-body-sm text-on-surface-variant">随时可一键重启</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 工具栏 -->
    <div class="max-w-7xl mx-auto px-6 py-5">
      <div class="flex flex-col lg:flex-row lg:items-center gap-4">
        <!-- 搜索框 -->
        <div class="relative flex-1">
          <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">search</span>
          <input v-model="search" type="text" placeholder="按职位名称、发布人、类别搜索..."
            class="w-full h-10 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/60 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all" />
        </div>

        <!-- 状态筛选标签 -->
        <div class="flex items-center bg-surface-container-lowest border border-surface-container-high rounded-xl p-1">
          <button v-for="tab in statusTabs" :key="tab.key"
            @click="activeTab = tab.key"
            :class="[
              'px-4 py-2 rounded-lg text-sm font-medium transition-all whitespace-nowrap',
              activeTab === tab.key
                ? 'bg-primary/10 text-primary'
                : 'text-on-surface-variant hover:text-on-surface hover:bg-surface-container'
            ]">
            {{ tab.label }}
            <span class="ml-1 text-xs" :class="activeTab === tab.key ? 'text-primary/70' : 'text-on-surface-variant/50'">({{ tab.count }})</span>
          </button>
        </div>

        <!-- 职位类别下拉 -->
        <select v-model="categoryFilter"
          class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 cursor-pointer min-w-[140px]">
          <option value="">全部职位类别</option>
          <option>前端研发</option>
          <option>后端研发</option>
          <option>人工智能</option>
          <option>产品策划</option>
        </select>

        <!-- 操作按钮 -->
        <div class="flex items-center gap-3">
          <button class="h-10 px-4 flex items-center gap-2 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium hover:bg-surface-container transition-colors">
            <span class="material-symbols-outlined text-lg">grid_view</span>
            批量暂停
          </button>
          <button class="h-10 px-4 flex items-center gap-2 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium hover:bg-surface-container transition-colors">
            <span class="material-symbols-outlined text-lg">download</span>
            导出职位报表
          </button>
          <button class="h-10 px-5 flex items-center gap-2 rounded-xl bg-primary text-on-primary text-sm font-semibold hover:bg-primary-container transition-colors shadow-sm">
            <span class="material-symbols-outlined text-lg">add</span>
            发布新职位
          </button>
        </div>
      </div>
    </div>

    <!-- 职位列表表格 -->
    <div class="max-w-7xl mx-auto px-6 pb-8">
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-surface-container-low/50 border-b border-surface-container-high">
                <th class="text-left px-5 py-3.5 w-10">
                  <input type="checkbox" class="w-4 h-4 rounded border-surface-container-high text-primary focus:ring-primary/30 cursor-pointer" />
                </th>
                <th class="text-left px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">职位名称与类别</th>
                <th class="text-left px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">工作类型与城市</th>
                <th class="text-left px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">薪资范围</th>
                <th class="text-left px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">发布时间</th>
                <th class="text-left px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">收到的投递 (新投递)</th>
                <th class="text-left px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">浏览量</th>
                <th class="text-left px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">职位状态</th>
                <th class="text-right px-5 py-3.5 text-label-md text-on-surface-variant font-semibold">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="job in filteredJobs" :key="job.id"
                class="border-b border-surface-container-high/50 hover:bg-surface-container-low/30 transition-colors">
                <!-- 复选框 -->
                <td class="px-5 py-4">
                  <input type="checkbox" class="w-4 h-4 rounded border-surface-container-high text-primary focus:ring-primary/30 cursor-pointer" />
                </td>
                <!-- 职位名称与类别 -->
                <td class="px-5 py-4">
                  <div class="flex items-center gap-2 flex-wrap">
                    <span class="font-semibold text-on-surface">{{ job.title }}</span>
                    <span v-if="job.tag" class="px-2 py-0.5 rounded-full text-xs font-semibold" :class="getTagClass(job.tag)">{{ job.tag }}</span>
                  </div>
                  <div class="flex items-center gap-2 mt-1">
                    <span class="px-2 py-0.5 bg-surface-container rounded text-xs font-medium text-on-surface-variant">{{ job.category }}</span>
                    <span class="text-xs text-on-surface-variant">编号: {{ job.code }}</span>
                  </div>
                  <div class="text-xs text-on-surface-variant mt-0.5">· 发布人: {{ job.publisher }}</div>
                </td>
                <!-- 工作类型与城市 -->
                <td class="px-5 py-4">
                  <div class="flex items-center gap-1.5 text-on-surface">
                    <span class="material-symbols-outlined text-on-surface-variant text-base">location_on</span>
                    {{ job.city }}
                  </div>
                  <div class="text-xs text-on-surface-variant mt-0.5">{{ job.workType }}</div>
                </td>
                <!-- 薪资范围 -->
                <td class="px-5 py-4">
                  <div class="font-semibold text-primary">{{ job.salary }}</div>
                  <div class="text-xs text-on-surface-variant mt-0.5">{{ job.salaryDetail }}</div>
                </td>
                <!-- 发布时间 -->
                <td class="px-5 py-4">
                  <div class="text-on-surface">{{ job.publishDate }}</div>
                  <div class="text-xs text-on-surface-variant mt-0.5">{{ job.publishRelative }}</div>
                </td>
                <!-- 收到的投递 -->
                <td class="px-5 py-4">
                  <div class="text-on-surface">{{ job.applications }} <span class="text-on-surface-variant font-normal">份</span></div>
                  <div v-if="job.newApplications" class="text-xs text-emerald-600 font-medium mt-0.5">↑ 今日新增 {{ job.newApplications }} 份</div>
                  <div v-else-if="job.applicationsNote" class="text-xs text-on-surface-variant mt-0.5">{{ job.applicationsNote }}</div>
                </td>
                <!-- 浏览量 -->
                <td class="px-5 py-4">
                  <div class="text-on-surface">{{ job.views.toLocaleString() }}</div>
                  <div class="text-xs text-on-surface-variant mt-0.5">人次浏览</div>
                </td>
                <!-- 职位状态 -->
                <td class="px-5 py-4">
                  <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-sm font-medium" :class="getStatusClass(job.status)">
                    <span class="w-1.5 h-1.5 rounded-full" :class="getStatusDotClass(job.status)"></span>
                    {{ job.status }}
                  </span>
                </td>
                <!-- 操作 -->
                <td class="px-5 py-4">
                  <div class="flex items-center justify-end gap-1">
                    <button class="px-2.5 py-1.5 rounded-lg text-sm font-medium text-primary hover:bg-primary/10 transition-colors">编辑</button>
                    <button v-if="job.status === '招聘中'" @click="toggleJobStatus(job)"
                      class="px-2.5 py-1.5 rounded-lg text-sm font-medium text-amber-600 hover:bg-amber-50 transition-colors">暂停</button>
                    <button v-if="job.status === '已暂停'" @click="toggleJobStatus(job)"
                      class="px-2.5 py-1.5 rounded-lg text-sm font-medium text-emerald-600 hover:bg-emerald-50 transition-colors">重启招聘</button>
                    <button v-if="job.status !== '已关闭'"
                      class="px-2.5 py-1.5 rounded-lg text-sm font-medium text-primary hover:bg-primary/10 transition-colors">查看投递</button>
                    <button v-if="job.status === '已关闭'" @click="toggleJobStatus(job)"
                      class="px-2.5 py-1.5 rounded-lg text-sm font-medium text-primary hover:bg-primary/10 transition-colors">重新激活</button>
                  </div>
                </td>
              </tr>
              <!-- 空状态 -->
              <tr v-if="filteredJobs.length === 0">
                <td colspan="9" class="px-5 py-16 text-center">
                  <span class="material-symbols-outlined text-5xl text-on-surface-variant/30 mb-3 block">work_off</span>
                  <p class="text-on-surface-variant">暂无匹配的职位数据</p>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- 底部工具栏 -->
        <div class="px-5 py-3.5 border-t border-surface-container-high bg-surface-container-low/30 flex flex-col sm:flex-row items-center justify-between gap-4">
          <div class="flex items-center gap-4">
            <label class="flex items-center gap-2 text-sm text-on-surface-variant cursor-pointer">
              <input type="checkbox" class="w-4 h-4 rounded border-surface-container-high text-primary focus:ring-primary/30 cursor-pointer" />
              全选所有项
            </label>
            <div class="flex items-center gap-2">
              <button class="px-3 py-1.5 rounded-lg text-sm font-medium text-primary hover:bg-primary/10 transition-colors">批量开启</button>
              <button class="px-3 py-1.5 rounded-lg text-sm font-medium text-primary hover:bg-primary/10 transition-colors">批量暂停</button>
              <button class="px-3 py-1.5 rounded-lg text-sm font-medium text-primary hover:bg-primary/10 transition-colors flex items-center gap-1">
                <span class="material-symbols-outlined text-base">swap_vert</span>
                批量刷新排序
              </button>
            </div>
          </div>
          <div class="flex items-center gap-4">
            <div class="flex items-center gap-2 text-sm text-on-surface-variant">
              <span>每页显示</span>
              <select v-model="pageSize" class="h-8 px-2 border border-surface-container-high rounded-lg text-sm bg-surface-container-lowest focus:outline-none focus:ring-2 focus:ring-primary/20">
                <option :value="10">10 条</option>
                <option :value="20">20 条</option>
                <option :value="50">50 条</option>
              </select>
            </div>
            <div class="flex items-center gap-1 text-sm text-on-surface-variant">
              <span>共 {{ filteredJobs.length }} 条 · 第 {{ currentPage }} / {{ totalPages }} 页</span>
              <button :disabled="currentPage <= 1" @click="currentPage--"
                class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high hover:bg-surface-container transition-colors disabled:opacity-40 disabled:cursor-not-allowed">
                <span class="material-symbols-outlined text-base">chevron_left</span>
              </button>
              <button v-for="page in totalPages" :key="page" @click="currentPage = page"
                :class="[
                  'w-8 h-8 flex items-center justify-center rounded-lg text-sm font-medium transition-colors',
                  currentPage === page ? 'bg-primary text-on-primary' : 'border border-surface-container-high hover:bg-surface-container text-on-surface-variant'
                ]">
                {{ page }}
              </button>
              <button :disabled="currentPage >= totalPages" @click="currentPage++"
                class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high hover:bg-surface-container transition-colors disabled:opacity-40 disabled:cursor-not-allowed">
                <span class="material-symbols-outlined text-base">chevron_right</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const search = ref('')
const activeTab = ref('all')
const categoryFilter = ref('')
const currentPage = ref(1)
const pageSize = ref(10)

const statusTabs = computed(() => [
  { key: 'all', label: '全部', count: jobs.value.length },
  { key: '招聘中', label: '招聘中', count: jobs.value.filter(j => j.status === '招聘中').length },
  { key: '已暂停', label: '已暂停', count: jobs.value.filter(j => j.status === '已暂停').length },
  { key: '已关闭', label: '已关闭', count: jobs.value.filter(j => j.status === '已关闭').length },
])

const jobs = ref([
  {
    id: 1,
    title: '大模型算法工程师',
    tag: '2025届校招',
    category: '人工智能',
    code: 'REQ-202503-010',
    publisher: '张立 (校招组)',
    city: '北京 · 海淀',
    workType: '全职 / 实习双轨',
    salary: '25k - 40k',
    salaryDetail: '16薪 · 顶尖学术奖金',
    publishDate: '2025-03-01',
    publishRelative: '10天前',
    applications: 342,
    newApplications: 18,
    applicationsNote: '',
    views: 4890,
    status: '招聘中'
  },
  {
    id: 2,
    title: '前端开发实习生',
    tag: '可转正',
    category: '前端研发',
    code: 'REQ-202503-008',
    publisher: '李洁',
    city: '北京 · 海淀',
    workType: '日常实习 / 3个月+',
    salary: '400 - 500 元/天',
    salaryDetail: '包三餐 · 房补1500',
    publishDate: '2025-03-05',
    publishRelative: '6天前',
    applications: 189,
    newApplications: 8,
    applicationsNote: '',
    views: 2310,
    status: '招聘中'
  },
  {
    id: 3,
    title: '后端开发工程师',
    tag: '校招',
    category: '后端研发',
    code: 'REQ-202502-019',
    publisher: '王雷(深圳研发中心)',
    city: '深圳 · 南山',
    workType: '全职正式编制',
    salary: '18k - 28k',
    salaryDetail: '15薪 · 弹性打卡',
    publishDate: '2025-02-20',
    publishRelative: '19天前',
    applications: 512,
    newApplications: 12,
    applicationsNote: '',
    views: 6420,
    status: '招聘中'
  },
  {
    id: 4,
    title: 'AI 产品经理实习生',
    tag: '',
    category: '产品策划',
    code: 'REQ-202501-004',
    publisher: '赵晴',
    city: '上海 · 徐汇',
    workType: '日常实习',
    salary: '300 - 450 元/天',
    salaryDetail: '大牛导师1v1带教',
    publishDate: '2025-01-15',
    publishRelative: '约2个月前',
    applications: 226,
    newApplications: 0,
    applicationsNote: '简历库已饱和',
    views: 3150,
    status: '已暂停'
  },
  {
    id: 5,
    title: '计算机视觉研究员',
    tag: '',
    category: '人工智能',
    code: 'REQ-202411-002',
    publisher: '陆教授专班',
    city: '北京 · 海淀',
    workType: '全职博士站',
    salary: '30k - 50k',
    salaryDetail: '16薪 · 专项科研经费',
    publishDate: '2024-11-10',
    publishRelative: '往期职位',
    applications: 159,
    newApplications: 0,
    applicationsNote: '流程已结束',
    views: 2800,
    status: '已关闭'
  }
])

const filteredJobs = computed(() => {
  return jobs.value.filter(j => {
    const matchSearch = !search.value ||
      j.title.includes(search.value) ||
      j.publisher.includes(search.value) ||
      j.category.includes(search.value)
    const matchStatus = activeTab.value === 'all' || j.status === activeTab.value
    const matchCategory = !categoryFilter.value || j.category === categoryFilter.value
    return matchSearch && matchStatus && matchCategory
  })
})

const totalPages = computed(() => Math.max(1, Math.ceil(filteredJobs.value.length / pageSize.value)))

function getTagClass(tag) {
  if (tag === '2025届校招' || tag === '校招') return 'bg-blue-50 text-blue-600'
  if (tag === '可转正') return 'bg-emerald-50 text-emerald-600'
  return 'bg-surface-container text-on-surface-variant'
}

function getStatusClass(status) {
  if (status === '招聘中') return 'bg-emerald-50 text-emerald-600'
  if (status === '已暂停') return 'bg-amber-50 text-amber-600'
  return 'bg-surface-container text-on-surface-variant'
}

function getStatusDotClass(status) {
  if (status === '招聘中') return 'bg-emerald-500'
  if (status === '已暂停') return 'bg-amber-500'
  return 'bg-surface-container-high'
}

function toggleJobStatus(job) {
  if (job.status === '招聘中') job.status = '已暂停'
  else if (job.status === '已暂停') job.status = '招聘中'
  else job.status = '招聘中'
}
</script>
