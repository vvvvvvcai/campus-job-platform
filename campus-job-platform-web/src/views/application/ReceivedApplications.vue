<template>
  <section class="w-full bg-surface-container-low pb-space-2xl">
    <div class="max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop pt-space-lg flex flex-col gap-space-lg">

      <!-- Breadcrumb & Header -->
      <div class="flex flex-col gap-space-xs">
        <nav class="flex items-center gap-space-2xs text-on-surface-variant font-label-md text-label-md">
          <router-link to="/company" class="hover:text-primary cursor-pointer transition-colors">企业管理中心</router-link>
          <span class="material-symbols-outlined text-[16px] text-outline">chevron_right</span>
          <router-link to="/company/recruitment" class="hover:text-primary cursor-pointer transition-colors">招聘管理</router-link>
          <span class="material-symbols-outlined text-[16px] text-outline">chevron_right</span>
          <span class="text-primary font-semibold">简历投递管理</span>
        </nav>
        <div class="flex flex-col md:flex-row md:items-end justify-between gap-space-md mt-space-2xs">
          <div class="space-y-space-2xs">
            <h1 class="font-headline-xl text-headline-xl text-on-surface font-bold tracking-tight">候选人投递管理</h1>
            <p class="font-body-md text-body-md text-on-surface-variant max-w-2xl">实时查阅海内外重点高校毕业生简历，支持AI多维评分与批量推进面试/发Offer，构筑高效精准校招流水线。</p>
          </div>
          <div class="flex items-center gap-space-sm self-start md:self-auto">
            <button class="flex items-center gap-space-xs px-space-md py-space-xs rounded-lg bg-surface-container-lowest shadow-sm hover:shadow-md text-on-surface font-label-lg text-label-lg transition-all">
              <span class="material-symbols-outlined text-secondary text-[20px]">tune</span>
              <span>AI 评分规则设置</span>
            </button>
            <router-link
              to="/company/jobs/create"
              class="flex items-center gap-space-xs px-space-md py-space-xs rounded-lg bg-primary text-on-primary shadow-sm hover:bg-primary-container hover:shadow-md font-label-lg text-label-lg transition-all"
            >
              <span class="material-symbols-outlined text-[20px]">add_circle</span>
              <span>发布新校招职位</span>
            </router-link>
          </div>
        </div>
      </div>

      <!-- KPI Metrics Cards -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-space-md">
        <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all flex flex-col justify-between relative overflow-hidden group">
          <div class="absolute -right-6 -top-6 w-24 h-24 bg-primary/5 rounded-full blur-xl group-hover:bg-primary/10 transition-colors"></div>
          <div class="flex items-center justify-between">
            <span class="font-label-lg text-label-lg text-on-surface-variant font-medium">总投递数</span>
            <span class="p-space-2xs rounded-lg bg-surface-container text-primary material-symbols-outlined text-[20px]">folder_shared</span>
          </div>
          <div class="mt-space-md flex items-baseline gap-space-sm">
            <span class="font-display-hero text-display-hero text-on-surface tracking-tight font-extrabold">1,428</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">份</span>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between text-on-surface-variant">
            <span class="flex items-center gap-0.5 text-secondary font-label-md text-label-md font-semibold">
              <span class="material-symbols-outlined text-[16px]">trending_up</span>+14.2%
            </span>
            <span class="font-label-sm text-label-sm">周环比增长</span>
          </div>
        </div>

        <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all flex flex-col justify-between relative overflow-hidden group">
          <div class="absolute -right-6 -top-6 w-24 h-24 bg-secondary/5 rounded-full blur-xl group-hover:bg-secondary/10 transition-colors"></div>
          <div class="flex items-center justify-between">
            <span class="font-label-lg text-label-lg text-on-surface-variant font-medium">待处理初筛</span>
            <span class="p-space-2xs rounded-lg bg-surface-container text-secondary material-symbols-outlined text-[20px]">mark_email_unread</span>
          </div>
          <div class="mt-space-md flex items-baseline gap-space-sm">
            <span class="font-display-hero text-display-hero text-primary tracking-tight font-extrabold">32</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">份未评</span>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between">
            <span class="px-space-xs py-0.5 rounded-full bg-error-container text-error font-label-sm text-label-sm font-semibold flex items-center gap-1">
              <span class="w-1.5 h-1.5 rounded-full bg-error animate-pulse"></span> 8份需48h内响应
            </span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">防流失警戒</span>
          </div>
        </div>

        <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all flex flex-col justify-between relative overflow-hidden group">
          <div class="absolute -right-6 -top-6 w-24 h-24 bg-secondary-fixed/30 rounded-full blur-xl group-hover:bg-secondary-fixed/50 transition-colors"></div>
          <div class="flex items-center justify-between">
            <span class="font-label-lg text-label-lg text-on-surface-variant font-medium">今日新增投递</span>
            <span class="p-space-2xs rounded-lg bg-surface-container text-secondary material-symbols-outlined text-[20px]">today</span>
          </div>
          <div class="mt-space-md flex items-baseline gap-space-sm">
            <span class="font-display-hero text-display-hero text-on-surface tracking-tight font-extrabold">46</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">份今日入池</span>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between text-on-surface-variant">
            <span class="font-label-md text-label-md text-on-surface font-medium flex items-center gap-1">
              <span class="material-symbols-outlined text-[16px] text-secondary">pace</span> 1.4 工作日
            </span>
            <span class="font-label-sm text-label-sm">平均响应时效</span>
          </div>
        </div>

        <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all flex flex-col justify-between relative overflow-hidden group">
          <div class="absolute -right-6 -top-6 w-24 h-24 bg-tertiary-fixed/30 rounded-full blur-xl group-hover:bg-tertiary-fixed/50 transition-colors"></div>
          <div class="flex items-center justify-between">
            <span class="font-label-lg text-label-lg text-on-surface-variant font-medium">已发 Offer / 签约</span>
            <span class="p-space-2xs rounded-lg bg-surface-container text-tertiary material-symbols-outlined text-[20px]">verified</span>
          </div>
          <div class="mt-space-md flex items-baseline gap-space-sm">
            <span class="font-display-hero text-display-hero text-on-surface tracking-tight font-extrabold">28</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">人通过三方</span>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between text-on-surface-variant">
            <span class="text-primary font-label-md text-label-md font-semibold flex items-center gap-1">
              <span class="material-symbols-outlined text-[16px]">thumb_up</span>89.3%
            </span>
            <span class="font-label-sm text-label-sm">签约意向达成率</span>
          </div>
        </div>
      </div>

      <!-- Search & Filter Panel -->
      <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm flex flex-col gap-space-md">
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-12 gap-space-sm items-center">
          <div class="lg:col-span-3">
            <div class="relative">
              <select v-model="filterJob" class="w-full h-10 pl-space-md pr-space-xl bg-surface-container-low text-on-surface rounded-lg font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary appearance-none cursor-pointer">
                <option value="">全部在招职位 (12个职位)</option>
                <option value="大模型算法工程师">大模型算法工程师</option>
                <option value="前端开发工程师">前端开发工程师</option>
                <option value="后端开发工程师">后端开发工程师</option>
                <option value="AI产品经理">AI产品经理</option>
              </select>
              <span class="material-symbols-outlined absolute right-3 top-2.5 text-on-surface-variant pointer-events-none text-[18px]">expand_more</span>
            </div>
          </div>
          <div class="lg:col-span-2">
            <div class="relative">
              <select v-model="filterStatus" class="w-full h-10 pl-space-md pr-space-xl bg-surface-container-low text-on-surface rounded-lg font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary appearance-none cursor-pointer">
                <option value="">全部状态</option>
                <option value="待处理/待查看">待处理/待查看</option>
                <option value="待邀约面试">待邀约面试</option>
                <option value="面试进行中">面试进行中</option>
                <option value="已发Offer">已发Offer</option>
                <option value="不合适/人才库">不合适/人才库</option>
              </select>
              <span class="material-symbols-outlined absolute right-3 top-2.5 text-on-surface-variant pointer-events-none text-[18px]">expand_more</span>
            </div>
          </div>
          <div class="lg:col-span-2">
            <div class="relative">
              <select v-model="filterSchool" class="w-full h-10 pl-space-md pr-space-xl bg-surface-container-low text-on-surface rounded-lg font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary appearance-none cursor-pointer">
                <option value="">院校层次: 不限</option>
                <option value="985/211">985 / 211 院校</option>
                <option value="双一流">双一流高校</option>
                <option value="QS前100">海外QS前100</option>
              </select>
              <span class="material-symbols-outlined absolute right-3 top-2.5 text-on-surface-variant pointer-events-none text-[18px]">expand_more</span>
            </div>
          </div>
          <div class="lg:col-span-2">
            <div class="relative">
              <select v-model="filterEducation" class="w-full h-10 pl-space-md pr-space-xl bg-surface-container-low text-on-surface rounded-lg font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary appearance-none cursor-pointer">
                <option value="">学历要求: 不限</option>
                <option value="硕士及以上">硕士及以上</option>
                <option value="本科">本科</option>
                <option value="博士研究生">博士研究生</option>
              </select>
              <span class="material-symbols-outlined absolute right-3 top-2.5 text-on-surface-variant pointer-events-none text-[18px]">expand_more</span>
            </div>
          </div>
          <div class="lg:col-span-3">
            <div class="relative flex items-center">
              <span class="material-symbols-outlined absolute left-3 text-on-surface-variant text-[18px]">search</span>
              <input
                v-model="searchQuery"
                class="w-full h-10 pl-9 pr-space-md bg-surface-container-low text-on-surface placeholder:text-on-surface-variant/70 rounded-lg font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="搜索候选人姓名、毕业院校或技能..."
                type="text"
              />
            </div>
          </div>
        </div>
        <div class="flex flex-wrap items-center justify-between gap-space-sm pt-space-xs">
          <div class="flex flex-wrap items-center gap-space-xs text-on-surface-variant font-label-md text-label-md">
            <span>快速标签:</span>
            <button class="px-space-sm py-1 bg-surface-container text-primary rounded-full hover:bg-primary hover:text-on-primary transition-colors">今日投递 (46)</button>
            <button class="px-space-sm py-1 bg-surface-container rounded-full hover:bg-primary hover:text-on-primary transition-colors">AI评分90分以上 (184)</button>
            <button class="px-space-sm py-1 bg-surface-container rounded-full hover:bg-primary hover:text-on-primary transition-colors">国家奖学金获得者 (38)</button>
            <button class="px-space-sm py-1 bg-surface-container rounded-full hover:bg-primary hover:text-on-primary transition-colors">ACM / 顶级竞赛获奖 (19)</button>
          </div>
          <div class="flex items-center gap-space-sm">
            <button class="px-space-md py-space-2xs text-on-surface-variant hover:text-primary font-label-md text-label-md transition-colors">清空筛选</button>
            <button class="px-space-md py-space-2xs bg-primary text-on-primary rounded-lg font-label-md text-label-md hover:bg-primary-container transition-colors shadow-sm">开始检索</button>
          </div>
        </div>
      </div>

      <!-- Batch Operations Toolbar -->
      <div class="bg-surface-container px-space-lg py-space-sm rounded-xl flex flex-wrap items-center justify-between gap-space-sm">
        <div class="flex items-center gap-space-md">
          <label class="flex items-center gap-space-xs cursor-pointer select-none">
            <input
              v-model="selectAll"
              class="w-4 h-4 rounded text-primary focus:ring-primary border-outline-variant cursor-pointer"
              type="checkbox"
            />
            <span class="font-label-md text-label-md text-on-surface font-medium">全选</span>
          </label>
          <span class="text-on-surface-variant font-label-sm text-label-sm bg-surface-container-lowest px-space-xs py-0.5 rounded-md">
            已选择 <strong class="text-primary">{{ selectedCount }}</strong> 份简历
          </span>
        </div>
        <div class="flex flex-wrap items-center gap-space-xs">
          <button class="flex items-center gap-1 px-space-sm py-1.5 rounded-lg bg-surface-container-lowest text-on-surface hover:text-primary hover:shadow-sm font-label-md text-label-md transition-all">
            <span class="material-symbols-outlined text-[16px]">done_all</span>
            <span>批量标为已读</span>
          </button>
          <button class="flex items-center gap-1 px-space-sm py-1.5 rounded-lg bg-surface-container-lowest text-on-surface hover:text-primary hover:shadow-sm font-label-md text-label-md transition-all">
            <span class="material-symbols-outlined text-[16px] text-secondary">send</span>
            <span>批量面试邀请</span>
          </button>
          <button class="flex items-center gap-1 px-space-sm py-1.5 rounded-lg bg-surface-container-lowest text-on-surface hover:text-primary hover:shadow-sm font-label-md text-label-md transition-all">
            <span class="material-symbols-outlined text-[16px]">file_download</span>
            <span>批量打包简历(ZIP)</span>
          </button>
          <button class="flex items-center gap-1 px-space-sm py-1.5 rounded-lg bg-surface-container-lowest text-error hover:bg-error-container/20 font-label-md text-label-md transition-all">
            <span class="material-symbols-outlined text-[16px]">archive</span>
            <span>批量淘汰/入库</span>
          </button>
        </div>
      </div>

      <!-- Data Table -->
      <div class="bg-surface-container-lowest rounded-xl shadow-sm overflow-hidden">
        <div class="overflow-x-auto w-full">
          <table class="w-full text-left border-collapse">
            <thead>
              <tr class="bg-surface-container-low text-on-surface-variant font-label-md text-label-md uppercase tracking-wider">
                <th class="py-space-md px-space-lg w-12 text-center">
                  <span class="sr-only">选择</span>
                </th>
                <th class="py-space-md px-space-md min-w-[240px]">求职者姓名 / 毕业院校</th>
                <th class="py-space-md px-space-md min-w-[170px]">应聘职位</th>
                <th class="py-space-md px-space-md min-w-[190px]">AI 人岗契合度</th>
                <th class="py-space-md px-space-md min-w-[130px]">投递时间</th>
                <th class="py-space-md px-space-md min-w-[140px]">当前流转状态</th>
                <th class="py-space-md px-space-md min-w-[170px]">HR 初筛评语/亮点</th>
                <th class="py-space-md px-space-lg text-right min-w-[210px]">快捷流转操作</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-surface-container font-body-md text-body-md">
              <tr
                v-for="app in applications"
                :key="app.id"
                class="hover:bg-surface-container-low/60 transition-colors group"
                :class="{ 'opacity-85': app.dimmed }"
              >
                <td class="py-space-md px-space-lg text-center">
                  <input
                    v-model="selectedIds"
                    :value="app.id"
                    class="w-4 h-4 rounded text-primary focus:ring-primary border-outline-variant cursor-pointer"
                    type="checkbox"
                  />
                </td>
                <td class="py-space-md px-space-md">
                  <div class="flex items-center gap-space-sm">
                    <div class="relative shrink-0">
                      <img class="w-10 h-10 rounded-full object-cover shadow-sm" :src="app.avatar" :alt="app.name" />
                      <span
                        class="absolute -bottom-1 -right-1 rounded-full p-0.5 flex items-center justify-center"
                        :class="app.verified ? 'bg-primary text-on-primary' : 'bg-outline text-surface-container-lowest'"
                        :title="app.verified ? '教育部学籍学历在线认证' : '未认证'"
                      >
                        <span class="material-symbols-outlined text-[10px]">{{ app.verified ? 'verified' : 'school' }}</span>
                      </span>
                    </div>
                    <div class="flex flex-col min-w-0">
                      <div class="flex items-center gap-space-xs">
                        <span class="font-headline-sm text-headline-sm text-on-surface font-semibold truncate">{{ app.name }}</span>
                        <span
                          v-for="(tag, ti) in app.tags"
                          :key="ti"
                          class="px-1.5 py-0.2 bg-surface-container font-label-sm text-label-sm rounded"
                          :class="tag.color"
                        >
                          {{ tag.label }}
                        </span>
                      </div>
                      <span class="font-body-sm text-body-sm text-on-surface-variant truncate">{{ app.school }}</span>
                    </div>
                  </div>
                </td>
                <td class="py-space-md px-space-md">
                  <div class="flex flex-col">
                    <span class="font-label-lg text-label-lg text-on-surface font-medium">{{ app.position }}</span>
                    <span class="font-body-sm text-body-sm text-on-surface-variant">{{ app.location }}</span>
                  </div>
                </td>
                <td class="py-space-md px-space-md">
                  <div class="flex flex-col gap-1">
                    <div class="flex items-center justify-between">
                      <div class="flex items-center gap-1">
                        <span class="material-symbols-outlined text-[16px]" :class="app.aiScoreColor">smart_toy</span>
                        <span class="font-label-md text-label-md font-bold" :class="app.aiScoreColor">{{ app.aiScore }}</span>
                      </div>
                      <span class="font-label-sm text-label-sm text-on-surface-variant">{{ app.aiLabel }}</span>
                    </div>
                    <div class="w-full bg-surface-container h-1.5 rounded-full overflow-hidden">
                      <div class="h-full rounded-full" :class="app.aiBarColor" :style="{ width: app.aiScore }"></div>
                    </div>
                    <span class="font-label-sm text-label-sm text-on-surface-variant truncate">{{ app.aiNote }}</span>
                  </div>
                </td>
                <td class="py-space-md px-space-md">
                  <div class="flex flex-col">
                    <span class="font-label-md text-label-md text-on-surface">{{ app.applyTime }}</span>
                    <span class="font-body-sm text-body-sm font-medium" :class="app.timeAgoColor">{{ app.timeAgo }}</span>
                  </div>
                </td>
                <td class="py-space-md px-space-md">
                  <span
                    class="inline-flex items-center gap-1 px-space-xs py-0.5 rounded-full font-label-sm text-label-sm font-semibold"
                    :class="app.statusBg"
                  >
                    <span class="w-1.5 h-1.5 rounded-full" :class="app.statusDot"></span>
                    {{ app.status }}
                  </span>
                </td>
                <td class="py-space-md px-space-md">
                  <span class="font-body-sm text-body-sm text-on-surface-variant line-clamp-2">{{ app.hrNote }}</span>
                </td>
                <td class="py-space-md px-space-lg text-right">
                  <div class="inline-flex items-center gap-space-xs justify-end">
                    <button
                      v-for="(action, ai) in app.actions"
                      :key="ai"
                      class="px-space-xs py-1 rounded font-label-md text-label-md transition-colors flex items-center gap-1"
                      :class="action.classes"
                    >
                      <span v-if="action.icon" class="material-symbols-outlined text-[14px]">{{ action.icon }}</span>
                      <span>{{ action.label }}</span>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div class="px-space-lg py-space-md bg-surface-container-low flex flex-col sm:flex-row items-center justify-between gap-space-md border-t border-surface-container">
          <div class="flex items-center gap-space-md font-body-sm text-body-sm text-on-surface-variant">
            <span>共 <strong class="text-on-surface font-semibold">1,428</strong> 条投递记录</span>
            <div class="flex items-center gap-space-2xs">
              <span>每页显示:</span>
              <select v-model="pageSize" class="bg-surface-container-lowest rounded px-2 py-1 text-on-surface focus:outline-none cursor-pointer">
                <option :value="10">10 条/页</option>
                <option :value="20">20 条/页</option>
                <option :value="50">50 条/页</option>
                <option :value="100">100 条/页</option>
              </select>
            </div>
          </div>
          <div class="flex items-center gap-space-xs">
            <button
              class="w-8 h-8 rounded-lg bg-surface-container-lowest text-on-surface-variant hover:text-primary hover:shadow-sm flex items-center justify-center transition-all disabled:opacity-40"
              :disabled="currentPage === 1"
              @click="currentPage = Math.max(1, currentPage - 1)"
            >
              <span class="material-symbols-outlined text-[18px]">chevron_left</span>
            </button>
            <button
              v-for="page in visiblePages"
              :key="page"
              class="w-8 h-8 rounded-lg flex items-center justify-center font-label-md text-label-md transition-colors"
              :class="currentPage === page ? 'bg-primary text-on-primary font-bold shadow-sm' : 'bg-surface-container-lowest text-on-surface hover:text-primary hover:bg-surface-container'"
              @click="currentPage = page"
            >
              {{ page }}
            </button>
            <span v-if="totalPages > 5" class="px-1 text-on-surface-variant">...</span>
            <button
              v-if="totalPages > 5"
              class="w-8 h-8 rounded-lg bg-surface-container-lowest text-on-surface hover:text-primary hover:bg-surface-container font-label-md text-label-md transition-colors"
              @click="currentPage = totalPages"
            >
              {{ totalPages }}
            </button>
            <button
              class="w-8 h-8 rounded-lg bg-surface-container-lowest text-on-surface-variant hover:text-primary hover:shadow-sm flex items-center justify-center transition-all"
              @click="currentPage = Math.min(totalPages, currentPage + 1)"
            >
              <span class="material-symbols-outlined text-[18px]">chevron_right</span>
            </button>
            <div class="hidden sm:flex items-center gap-1 ml-space-xs font-body-sm text-body-sm text-on-surface-variant">
              <span>前往</span>
              <input
                v-model.number="jumpPage"
                class="w-12 h-8 text-center bg-surface-container-lowest rounded text-on-surface focus:outline-none focus:ring-1 focus:ring-primary"
                :max="totalPages"
                min="1"
                type="number"
              />
              <span>页</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, watch } from 'vue'

const searchQuery = ref('')
const filterJob = ref('')
const filterStatus = ref('')
const filterSchool = ref('')
const filterEducation = ref('')
const selectAll = ref(false)
const selectedIds = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const jumpPage = ref(1)

const totalPages = computed(() => Math.max(1, Math.ceil(1428 / pageSize.value)))

const visiblePages = computed(() => {
  const pages = []
  const total = totalPages.value
  const current = currentPage.value
  if (total <= 5) {
    for (let i = 1; i <= total; i++) pages.push(i)
  } else {
    for (let i = 1; i <= Math.min(3, total); i++) pages.push(i)
  }
  return pages
})

const selectedCount = computed(() => selectedIds.value.length)

watch(selectAll, (val) => {
  if (val) {
    selectedIds.value = applications.value.map((a) => a.id)
  } else {
    selectedIds.value = []
  }
})

watch(selectedIds, (val) => {
  selectAll.value = val.length === applications.value.length && applications.value.length > 0
})

watch(jumpPage, (val) => {
  if (val >= 1 && val <= totalPages.value) {
    currentPage.value = val
  }
})

watch(currentPage, (val) => {
  jumpPage.value = val
})

const applications = ref([
  {
    id: 1,
    name: '周明翰',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCXoAphzEArBCxcwlB_nBs3s36wApF7RXh6Koj5nmLDL5aNR-lQRBIzbg-Q9vMnoeuyCpxGnNI-9epfvxtf9p0rK4VP-uqpydmppr2pfw0NCqZkMsVFPiIesuyPQIvd7ZkZ5ESudzHguMQCUdvAo58CV9yU01i6SJRuQd9pTKCcWcNru9Nw43P8UE3yVj9aONT6ASbz9wjjL-83HgXvrJaqEdXGiUf_G4vvcId0R4D44XLOPeM8hTwWbQ',
    verified: true,
    tags: [
      { label: '985/211', color: 'text-primary' },
      { label: '硕士', color: 'text-secondary' },
    ],
    school: '清华大学 · 软件工程 (2025届)',
    position: '大模型算法工程师',
    location: '北京 · 算法预研组',
    aiScore: '96%',
    aiScoreColor: 'text-secondary',
    aiLabel: '卓越契合',
    aiBarColor: 'bg-secondary',
    aiNote: '算法卓越 · ACM区域金牌',
    applyTime: '今日 11:24',
    timeAgo: '1小时前',
    timeAgoColor: 'text-secondary',
    status: '待处理 (初筛)',
    statusBg: 'bg-surface-container text-primary',
    statusDot: 'bg-primary',
    hrNote: '核心竞赛金牌，顶尖工程能力，推荐立即直通一面',
    actions: [
      { label: '查看简历', classes: 'bg-primary text-on-primary hover:bg-primary-container' },
      { label: '发起面试', classes: 'bg-secondary-fixed text-on-secondary-fixed hover:bg-secondary-container' },
    ],
    dimmed: false,
  },
  {
    id: 2,
    name: '李思琪',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuA8erVmAag-gb8JqmEdZOnL9a63J5SDsxKAKR1QEbwZr2Rr8WZMYyeIOnJgoy18BTiAgNYsnBDL2Z-v9pS47OsRGk9dlT0ZH5HlpuSEojsiGQxEVzMjXeY6yF_z3-HlMZUX3gsXpJCDGDHy-hUtPgmzYOBzFjNBA_73kecrmjPwfDL6lwewTn9DCG727mpKNHOHDxilAXSmryBtst5EmFCDAJ6I5ARhV6aK3k7EP8I2bWYBcvsELTp05A',
    verified: true,
    tags: [
      { label: '985/211', color: 'text-primary' },
      { label: '本科', color: 'text-on-surface-variant' },
    ],
    school: '北京航空航天大学 · 计算机系 (2025届)',
    position: '前端开发实习生 (可转正)',
    location: '杭州 · 基础业务线',
    aiScore: '92%',
    aiScoreColor: 'text-secondary',
    aiLabel: '高度匹配',
    aiBarColor: 'bg-secondary',
    aiNote: '一线大厂实习经验 · Vue/React全栈',
    applyTime: '今日 09:40',
    timeAgo: '3小时前',
    timeAgoColor: 'text-on-surface-variant',
    status: '面试邀约中',
    statusBg: 'bg-tertiary-fixed text-on-tertiary-fixed',
    statusDot: 'bg-tertiary',
    hrNote: '已发出线上一面邀请函，待选时段',
    actions: [
      { label: '查看简历', classes: 'bg-surface-container text-on-surface hover:text-primary' },
      { label: '调整时段', classes: 'bg-secondary-fixed text-on-secondary-fixed hover:bg-secondary-container' },
    ],
    dimmed: false,
  },
  {
    id: 3,
    name: '赵程安',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCYETLJxpJkjVYBKq3TYK7UJZ--tRF6zxZSrxLlpCDpobQjHWq5-gZieIWPfcrFyQb3263Vnnqvlkz9LZ9Yc6F_kQan5oDn0QxywY5VU_rrCPQnwjIgo80vJhy5UUmM28AxacQxt8A2NTIQigo9F845UsmWq8PQDbOidD0ECXxKN9KMEw629DNqFPmJV3yul47_ZjULu-ay-LR1rIGDVw0q_s_2C1tVY3qilH5PdC33NvCpMOgBYvvpGw',
    verified: true,
    tags: [
      { label: '985/211', color: 'text-primary' },
      { label: '硕士', color: 'text-secondary' },
    ],
    school: '上海交通大学 · 自动化系 (2025届)',
    position: '大模型算法工程师',
    location: '上海 · 基础模型研发组',
    aiScore: '95%',
    aiScoreColor: 'text-secondary',
    aiLabel: '高度匹配',
    aiBarColor: 'bg-secondary',
    aiNote: 'NeurIPS 2024 一作 · 强化学习方向',
    applyTime: '昨日 17:15',
    timeAgo: '昨天',
    timeAgoColor: 'text-on-surface-variant',
    status: '二面进行中',
    statusBg: 'bg-surface-container-high text-primary',
    statusDot: 'bg-primary animate-ping',
    hrNote: '一面技术官评级 A+，逻辑与推导极深',
    actions: [
      { label: '进入考场', icon: 'videocam', classes: 'bg-primary text-on-primary hover:bg-primary-container' },
      { label: '评估表', classes: 'bg-surface-container text-on-surface hover:text-primary' },
    ],
    dimmed: false,
  },
  {
    id: 4,
    name: '孙楚越',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCu1Qrru0eIlqffR4iID-E7AfKvNKAFGWKjLQM05NiHqKckInTJ8HFbP-oQMT8UsC2tsdBTxNVmGGjkVv6iAAlF3nflNveFz__Z5-zs3j3G9gKmAjzqRobzE5dFkNhYc14brSFWmGoCqC0qVQOnu3RMR7AC4F5cAz9676i6Wo5jtx1ZGVjQjs6yUvSyG4WCMohnW80iY9O621CHQy_VK3-GIsLS9YU8MKLmHkMNZwU48FnEDLhiHCl17Q',
    verified: true,
    tags: [
      { label: '985/211', color: 'text-primary' },
      { label: '硕士', color: 'text-secondary' },
    ],
    school: '华中科技大学 · 人工智能学院 (2025届)',
    position: '计算机视觉研究员',
    location: '深圳 · 智能驾驶组',
    aiScore: '94%',
    aiScoreColor: 'text-secondary',
    aiLabel: '卓越契合',
    aiBarColor: 'bg-secondary',
    aiNote: 'CVPR 顶会录用 · 3D感知前沿',
    applyTime: '前天 18:30',
    timeAgo: '2天前',
    timeAgoColor: 'text-on-surface-variant',
    status: '已发Offer待签约',
    statusBg: 'bg-surface-container text-secondary',
    statusDot: 'bg-secondary',
    hrNote: '已下发年薪Package意向函，双方沟通良好',
    actions: [
      { label: '三方协议', icon: 'description', classes: 'bg-secondary text-on-secondary hover:bg-secondary-container' },
      { label: '催签提醒', classes: 'bg-surface-container text-on-surface hover:text-primary' },
    ],
    dimmed: false,
  },
  {
    id: 5,
    name: '王嘉宁',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDoRnW9J67nc6sbYQcvEkZEOIKTVoDBehpeuusebcoCDHnyeb2uzKVq7R2CScUnoTagmqVYw1vy_VBbayhu-BkQsq5B-35P7-4oNkMb3jiesjiN1G5WRhbdgcbzhj-FeEsb97IudsXMTzZGwJW58pfG68yCUpRmrzR-QpueK7zztsImuDPXVxqxvlZUNgxBd5kW9ypHKjpJRdkIUhJn0TB3ZooDFD3dcKoRBhzo_O930v-Vzpy-dUB3NQ',
    verified: false,
    tags: [
      { label: '211', color: 'text-on-surface-variant' },
      { label: '本科', color: 'text-on-surface-variant' },
    ],
    school: '北京邮电大学 · 通信工程 (2025届)',
    position: '后端开发工程师',
    location: '北京 · 支付系统部',
    aiScore: '82%',
    aiScoreColor: 'text-on-surface-variant',
    aiLabel: '基本匹配',
    aiBarColor: 'bg-outline',
    aiNote: '高并发业务经验尚缺',
    applyTime: '3天前',
    timeAgo: '10月24日',
    timeAgoColor: 'text-on-surface-variant',
    status: '不合适 / 人才库',
    statusBg: 'bg-surface-container-high text-on-surface-variant',
    statusDot: 'bg-outline',
    hrNote: '基础扎实但岗位HC已满，已归档备选池',
    actions: [
      { label: '查看简历', classes: 'bg-surface-container text-on-surface hover:text-primary' },
      { label: '重新激活', classes: 'bg-primary-fixed text-on-primary-fixed hover:bg-primary hover:text-on-primary' },
    ],
    dimmed: true,
  },
])
</script>
