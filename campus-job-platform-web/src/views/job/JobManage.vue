<template>
  <div class="relative w-full max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-lg">
    <!-- Subtle Ambient Glow -->
    <div class="absolute top-10 left-1/3 w-96 h-96 bg-primary-container/5 rounded-full blur-3xl pointer-events-none -z-10"></div>
    <div class="absolute top-40 right-1/4 w-80 h-80 bg-secondary-container/10 rounded-full blur-3xl pointer-events-none -z-10"></div>

    <!-- 1. Breadcrumb & Section Subtitle -->
    <div class="flex flex-col md:flex-row md:items-center justify-between gap-space-sm mb-space-lg">
      <div class="flex items-center gap-space-xs font-label-md text-label-md text-on-surface-variant">
        <router-link to="/enterprise" class="hover:text-primary transition-colors flex items-center gap-1">
          <span class="material-symbols-outlined text-[16px]">domain</span>
          <span>企业管理中心</span>
        </router-link>
        <span class="material-symbols-outlined text-[14px] text-outline">chevron_right</span>
        <span class="text-on-surface font-semibold">职位管理列表</span>
        <span class="ml-space-2xs px-space-xs py-0.5 rounded-full bg-surface-container-high text-primary font-label-sm text-label-sm font-semibold">春招专属季</span>
      </div>
      <!-- Quick Export & Primary Actions Mobile/Desktop Fallback Trigger -->
      <div class="flex items-center gap-space-xs">
        <span class="font-label-sm text-label-sm text-outline hidden sm:inline-block">系统数据已同步至最新</span>
        <span class="inline-block w-2 h-2 rounded-full bg-secondary-container animate-ping hidden sm:inline-block"></span>
      </div>
    </div>

    <!-- 2. KPI Metrics Grid (Bento Metric Badges) -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-space-md mb-space-xl">
      <!-- KPI 1: Active Jobs -->
      <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all duration-200 flex flex-col justify-between group">
        <div class="flex items-center justify-between">
          <span class="font-label-md text-label-md text-on-surface-variant font-medium">在招职位</span>
          <div class="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center text-primary group-hover:scale-110 transition-transform">
            <span class="material-symbols-outlined text-[22px]">work</span>
          </div>
        </div>
        <div class="mt-space-md flex items-baseline gap-space-xs">
          <span class="font-display-hero-mobile md:font-display-hero text-display-hero-mobile md:text-display-hero text-on-surface font-bold tracking-tight">{{ kpi.activeJobs }}</span>
          <span class="font-label-md text-label-md text-on-surface-variant">个职位在线</span>
        </div>
        <div class="mt-space-sm pt-space-xs flex items-center justify-between">
          <span class="font-label-sm text-label-sm text-secondary flex items-center gap-0.5">
            <span class="material-symbols-outlined text-[14px]">check_circle</span>
            全部符合合规标准
          </span>
          <span class="font-label-sm text-label-sm text-on-surface-variant">总配额: {{ kpi.totalQuota }}</span>
        </div>
      </div>

      <!-- KPI 2: Total Resumes -->
      <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all duration-200 flex flex-col justify-between group">
        <div class="flex items-center justify-between">
          <span class="font-label-md text-label-md text-on-surface-variant font-medium">收到简历总数</span>
          <div class="w-10 h-10 rounded-lg bg-primary-fixed flex items-center justify-center text-primary group-hover:scale-110 transition-transform">
            <span class="material-symbols-outlined text-[22px]">drafts</span>
          </div>
        </div>
        <div class="mt-space-md flex items-baseline gap-space-xs">
          <span class="font-display-hero-mobile md:font-display-hero text-display-hero-mobile md:text-display-hero text-on-surface font-bold tracking-tight">{{ kpi.totalResumes }}</span>
          <span class="font-label-md text-label-md text-on-surface-variant">份历史投递</span>
        </div>
        <div class="mt-space-sm pt-space-xs flex items-center justify-between">
          <div class="flex items-center gap-1.5">
            <svg class="w-16 h-5 text-primary" fill="none" viewBox="0 0 60 20">
              <path d="M2 14L14 10L24 16L34 6L46 11L58 3" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></path>
            </svg>
            <span class="font-label-sm text-label-sm text-primary font-semibold">+14.2% 周环比</span>
          </div>
        </div>
      </div>

      <!-- KPI 3: Today Submissions -->
      <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all duration-200 flex flex-col justify-between group relative overflow-hidden">
        <div class="absolute -right-6 -bottom-6 w-24 h-24 rounded-full bg-secondary-container/15 pointer-events-none"></div>
        <div class="flex items-center justify-between">
          <span class="font-label-md text-label-md text-on-surface-variant font-medium">今日新增投递</span>
          <div class="w-10 h-10 rounded-lg bg-secondary-fixed flex items-center justify-center text-secondary group-hover:scale-110 transition-transform">
            <span class="material-symbols-outlined text-[22px]">trending_up</span>
          </div>
        </div>
        <div class="mt-space-md flex items-baseline gap-space-xs">
          <span class="font-display-hero-mobile md:font-display-hero text-display-hero-mobile md:text-display-hero text-secondary font-bold tracking-tight">{{ kpi.todaySubmissions }}</span>
          <span class="font-label-md text-label-md text-on-surface-variant">份</span>
          <span class="ml-1 px-1.5 py-0.5 rounded-full bg-secondary-fixed-dim text-on-secondary-fixed font-label-sm text-label-sm font-semibold animate-pulse">实时</span>
        </div>
        <div class="mt-space-sm pt-space-xs flex items-center justify-between">
          <span class="font-label-sm text-label-sm text-on-surface-variant">待初筛: {{ kpi.pendingScreening }} 份</span>
          <router-link to="/enterprise/screening" class="font-label-sm text-label-sm text-primary hover:underline font-semibold">快速甄选 →</router-link>
        </div>
      </div>

      <!-- KPI 4: Paused / Archived -->
      <div class="bg-surface-container-lowest p-space-lg rounded-xl shadow-sm hover:shadow-md transition-all duration-200 flex flex-col justify-between group">
        <div class="flex items-center justify-between">
          <span class="font-label-md text-label-md text-on-surface-variant font-medium">已暂停 / 归档</span>
          <div class="w-10 h-10 rounded-lg bg-surface-container-high flex items-center justify-center text-outline group-hover:scale-110 transition-transform">
            <span class="material-symbols-outlined text-[22px]">pause_circle</span>
          </div>
        </div>
        <div class="mt-space-md flex items-baseline gap-space-xs">
          <span class="font-display-hero-mobile md:font-display-hero text-display-hero-mobile md:text-display-hero text-on-surface-variant font-bold tracking-tight">{{ kpi.pausedArchived }}</span>
          <span class="font-label-md text-label-md text-on-surface-variant">个职位休眠</span>
        </div>
        <div class="mt-space-sm pt-space-xs flex items-center justify-between">
          <span class="font-label-sm text-label-sm text-outline">3 暂停 · 1 已关闭</span>
          <span class="font-label-sm text-label-sm text-on-surface-variant">随时可一键重启</span>
        </div>
      </div>
    </div>

    <!-- 3. Action Toolbar & Filter Hub -->
    <div class="bg-surface-container-lowest rounded-2xl shadow-sm p-space-md mb-space-md flex flex-col lg:flex-row lg:items-center justify-between gap-space-md">
      <!-- Left Filters: Search, Status Tabs, Category Selector -->
      <div class="flex flex-col md:flex-row md:items-center gap-space-md flex-wrap lg:flex-nowrap">
        <!-- Search Input with Icon -->
        <div class="relative w-full md:w-72">
          <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-outline text-[20px]">search</span>
          <input
            v-model="searchQuery"
            type="text"
            class="w-full h-10 pl-10 pr-space-md rounded-lg bg-surface font-body-md text-body-md text-on-surface placeholder:text-outline focus:outline-none focus:bg-surface-container-lowest transition-colors shadow-inner"
            placeholder="按职位名称、发布人、类别搜索..."
          />
        </div>
        <!-- Status Filter Tabs -->
        <div class="flex items-center bg-surface p-1 rounded-lg self-start md:self-auto overflow-x-auto max-w-full">
          <button
            v-for="tab in statusTabs"
            :key="tab.key"
            class="px-space-sm py-1.5 rounded-md font-label-md text-label-md transition-all whitespace-nowrap"
            :class="activeTab === tab.key ? 'bg-surface-container-lowest text-primary font-semibold shadow-sm' : 'text-on-surface-variant hover:text-primary'"
            @click="activeTab = tab.key"
          >
            {{ tab.label }} ({{ tab.count }})
          </button>
        </div>
        <!-- Category Dropdown Select -->
        <div class="relative min-w-[140px]">
          <select
            v-model="selectedCategory"
            class="w-full h-10 appearance-none bg-surface text-on-surface font-body-md text-body-md rounded-lg pl-3 pr-8 focus:outline-none cursor-pointer"
          >
            <option value="">全部职位类别</option>
            <option value="ai">人工智能研发</option>
            <option value="fe">前端技术研发</option>
            <option value="be">后端架构研发</option>
            <option value="pm">产品策划与运营</option>
            <option value="qa">质量保障与测试</option>
          </select>
          <span class="material-symbols-outlined absolute right-2.5 top-1/2 -translate-y-1/2 text-outline pointer-events-none text-[18px]">expand_more</span>
        </div>
      </div>
      <!-- Right Actions: Post Job, Batch Pause, Export -->
      <div class="flex items-center gap-space-xs shrink-0 self-end lg:self-auto">
        <button class="h-10 px-space-md rounded-lg bg-surface-container-low hover:bg-surface-container text-on-surface font-label-lg text-label-lg flex items-center gap-1.5 transition-colors">
          <span class="material-symbols-outlined text-[18px] text-outline">pause</span>
          <span>批量暂停</span>
        </button>
        <button class="h-10 px-space-md rounded-lg bg-surface-container-low hover:bg-surface-container text-on-surface font-label-lg text-label-lg flex items-center gap-1.5 transition-colors">
          <span class="material-symbols-outlined text-[18px] text-outline">file_download</span>
          <span>导出职位报表</span>
        </button>
        <router-link
          to="/enterprise/jobs/create"
          class="h-10 px-space-lg rounded-lg bg-primary-container hover:bg-primary text-on-primary font-label-lg text-label-lg font-semibold flex items-center gap-1.5 shadow-sm hover:shadow transition-all"
        >
          <span class="material-symbols-outlined text-[20px]">add</span>
          <span>发布新职位</span>
        </router-link>
      </div>
    </div>

    <!-- 4. Job Management Data Table Container -->
    <div class="bg-surface-container-lowest rounded-2xl shadow-sm overflow-hidden mb-space-md">
      <div class="w-full overflow-x-auto">
        <table class="w-full text-left border-collapse min-w-[1040px]">
          <!-- Table Head -->
          <thead>
            <tr class="bg-surface-container-low text-on-surface-variant font-label-md text-label-md">
              <th class="py-space-md pl-space-lg pr-space-xs w-12 text-center" scope="col">
                <input
                  v-model="selectAll"
                  type="checkbox"
                  class="w-4 h-4 rounded text-primary-container focus:ring-0 cursor-pointer accent-primary-container"
                />
              </th>
              <th class="py-space-md px-space-md font-semibold min-w-[260px]" scope="col">职位名称与类别</th>
              <th class="py-space-md px-space-md font-semibold min-w-[140px]" scope="col">工作类型与城市</th>
              <th class="py-space-md px-space-md font-semibold min-w-[150px]" scope="col">薪资范围</th>
              <th class="py-space-md px-space-md font-semibold min-w-[130px]" scope="col">发布时间</th>
              <th class="py-space-md px-space-md font-semibold min-w-[150px]" scope="col">收到的投递 (新投递)</th>
              <th class="py-space-md px-space-md font-semibold min-w-[100px]" scope="col">浏览量</th>
              <th class="py-space-md px-space-md font-semibold min-w-[110px]" scope="col">职位状态</th>
              <th class="py-space-md pr-space-lg pl-space-md text-right font-semibold min-w-[200px]" scope="col">操作</th>
            </tr>
          </thead>
          <!-- Table Body -->
          <tbody class="divide-y-0 text-on-surface font-body-md text-body-md">
            <tr
              v-for="(job, index) in filteredJobs"
              :key="job.id"
              class="hover:bg-surface-container-low/70 transition-colors group"
              :class="index % 2 === 1 ? 'bg-surface/40' : ''"
            >
              <td class="py-space-lg pl-space-lg pr-space-xs text-center">
                <input
                  v-model="selectedJobs"
                  :value="job.id"
                  type="checkbox"
                  class="w-4 h-4 rounded text-primary-container cursor-pointer accent-primary-container"
                />
              </td>
              <td class="py-space-lg px-space-md">
                <div class="flex flex-col gap-1">
                  <div class="flex items-center gap-space-xs">
                    <router-link
                      :to="`/enterprise/jobs/${job.id}`"
                      class="font-headline-sm text-headline-sm text-on-surface font-semibold hover:text-primary transition-colors line-clamp-1"
                    >
                      {{ job.title }}
                    </router-link>
                    <span
                      v-if="job.tags.recruitment"
                      class="px-2 py-0.5 rounded-full bg-primary-fixed text-primary font-label-sm text-label-sm font-semibold"
                    >
                      {{ job.tags.recruitment }}
                    </span>
                    <span
                      v-if="job.tags.permanent"
                      class="px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed-variant font-label-sm text-label-sm"
                    >
                      {{ job.tags.permanent }}
                    </span>
                  </div>
                  <div class="flex items-center gap-space-xs font-label-sm text-label-sm text-outline">
                    <span class="px-1.5 py-0.5 bg-surface-container rounded text-on-surface-variant font-medium">{{ job.category }}</span>
                    <span>编号: {{ job.code }}</span>
                    <span>· 发布人: {{ job.publisher }}</span>
                  </div>
                </div>
              </td>
              <td class="py-space-lg px-space-md">
                <div class="flex flex-col">
                  <span class="font-semibold text-on-surface flex items-center gap-1">
                    <span class="material-symbols-outlined text-[16px] text-outline">location_on</span>
                    {{ job.location }}
                  </span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant mt-0.5">{{ job.workType }}</span>
                </div>
              </td>
              <td class="py-space-lg px-space-md">
                <div class="flex flex-col">
                  <span class="font-semibold" :class="job.status === 'closed' ? 'text-on-surface-variant' : 'text-primary'">{{ job.salary }}</span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant">{{ job.salaryNote }}</span>
                </div>
              </td>
              <td class="py-space-lg px-space-md">
                <div class="flex flex-col">
                  <span class="text-on-surface">{{ job.publishDate }}</span>
                  <span class="font-label-sm text-label-sm text-outline">{{ job.publishTimeAgo }}</span>
                </div>
              </td>
              <td class="py-space-lg px-space-md">
                <div class="flex flex-col">
                  <div class="flex items-center gap-1.5">
                    <span class="font-semibold text-headline-sm" :class="job.status === 'closed' ? 'text-on-surface-variant' : 'text-on-surface'">{{ job.applications }}</span>
                    <span class="font-label-sm text-label-sm" :class="job.status === 'closed' ? 'text-outline' : 'text-on-surface-variant'">份</span>
                  </div>
                  <span
                    v-if="job.todayNew > 0"
                    class="inline-flex items-center gap-0.5 font-label-sm text-label-sm text-secondary font-semibold"
                  >
                    <span class="material-symbols-outlined text-[14px]">arrow_upward</span>
                    今日新增 {{ job.todayNew }} 份
                  </span>
                  <span v-else class="font-label-sm text-label-sm text-outline">{{ job.applicationsNote }}</span>
                </div>
              </td>
              <td class="py-space-lg px-space-md">
                <div class="flex flex-col">
                  <span class="font-medium" :class="job.status === 'closed' ? 'text-on-surface-variant' : 'text-on-surface'">{{ job.views }}</span>
                  <span class="font-label-sm text-label-sm text-outline">人次浏览</span>
                </div>
              </td>
              <td class="py-space-lg px-space-md">
                <span
                  class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full font-label-sm text-label-sm font-semibold"
                  :class="statusBadgeClass(job.status)"
                >
                  <span
                    class="w-2 h-2 rounded-full"
                    :class="{
                      'bg-emerald-500 animate-pulse': job.status === 'active',
                      'bg-amber-500': job.status === 'paused',
                      'bg-outline': job.status === 'closed'
                    }"
                  ></span>
                  {{ statusLabel(job.status) }}
                </span>
              </td>
              <td class="py-space-lg pr-space-lg pl-space-md text-right">
                <div class="inline-flex items-center gap-space-2xs">
                  <button class="px-2 py-1 rounded-md text-primary hover:bg-surface-container font-label-md text-label-md font-medium transition-colors">编辑</button>
                  <button
                    v-if="job.status === 'active'"
                    class="px-2 py-1 rounded-md text-on-surface-variant hover:bg-surface-container font-label-md text-label-md transition-colors"
                  >暂停</button>
                  <button
                    v-if="job.status === 'paused'"
                    class="px-2 py-1 rounded-md text-secondary font-semibold hover:bg-surface-container font-label-md text-label-md transition-colors"
                  >重启招聘</button>
                  <router-link
                    :to="`/enterprise/jobs/${job.id}/applications`"
                    class="px-2 py-1 rounded-md text-primary font-semibold hover:bg-surface-container font-label-md text-label-md transition-colors"
                  >查看投递</router-link>
                  <div v-if="job.status === 'active'" class="relative inline-block text-left group/more">
                    <button class="p-1 rounded-md text-outline hover:text-on-surface hover:bg-surface-container transition-colors">
                      <span class="material-symbols-outlined text-[20px]">more_horiz</span>
                    </button>
                    <div class="absolute right-0 top-full mt-1 w-28 bg-surface-container-lowest rounded-xl shadow-lg py-1 opacity-0 invisible group-hover/more:opacity-100 group-hover/more:visible transition-all duration-150 z-20">
                      <button class="w-full text-left px-3 py-1.5 font-label-md text-label-md text-on-surface-variant hover:bg-surface-container hover:text-on-surface flex items-center gap-1.5">
                        <span class="material-symbols-outlined text-[16px]">visibility_off</span> 关闭
                      </button>
                      <button class="w-full text-left px-3 py-1.5 font-label-md text-label-md text-error hover:bg-error-container/30 flex items-center gap-1.5">
                        <span class="material-symbols-outlined text-[16px]">delete</span> 删除
                      </button>
                    </div>
                  </div>
                  <button
                    v-if="job.status === 'paused'"
                    class="px-2 py-1 rounded-md text-on-surface-variant hover:bg-surface-container font-label-md text-label-md transition-colors"
                  >查看投递</button>
                  <button
                    v-if="job.status === 'paused'"
                    class="px-2 py-1 rounded-md text-error hover:bg-error-container/30 font-label-md text-label-md transition-colors"
                  >删除</button>
                  <router-link
                    v-if="job.status === 'closed'"
                    :to="`/enterprise/jobs/${job.id}/applications`"
                    class="px-2 py-1 rounded-md text-on-surface-variant hover:bg-surface-container font-label-md text-label-md transition-colors"
                  >查看投递</router-link>
                  <button
                    v-if="job.status === 'closed'"
                    class="px-2 py-1 rounded-md text-primary font-semibold hover:bg-surface-container font-label-md text-label-md transition-colors"
                  >重新激活</button>
                  <button
                    v-if="job.status === 'closed'"
                    class="px-2 py-1 rounded-md text-error hover:bg-error-container/30 font-label-md text-label-md transition-colors"
                  >删除</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- 5. Bottom Bulk Action Bar & Pagination -->
      <div class="p-space-md bg-surface-container-low/60 flex flex-col md:flex-row md:items-center justify-between gap-space-md">
        <!-- Left: Bulk Options -->
        <div class="flex items-center gap-space-md">
          <label class="flex items-center gap-2 cursor-pointer font-label-md text-label-md text-on-surface-variant select-none">
            <input
              v-model="selectAll"
              type="checkbox"
              class="w-4 h-4 rounded text-primary-container cursor-pointer accent-primary-container"
            />
            <span>全选所有项</span>
          </label>
          <div class="flex items-center gap-space-xs">
            <button class="px-space-sm py-1.5 bg-surface-container hover:bg-surface-container-high text-on-surface font-label-sm text-label-sm rounded-lg transition-colors">批量开启</button>
            <button class="px-space-sm py-1.5 bg-surface-container hover:bg-surface-container-high text-on-surface font-label-sm text-label-sm rounded-lg transition-colors">批量暂停</button>
            <button class="px-space-sm py-1.5 bg-surface-container hover:bg-surface-container-high text-on-surface font-label-sm text-label-sm rounded-lg transition-colors flex items-center gap-1">
              <span class="material-symbols-outlined text-[14px]">sync</span>
              批量刷新排序
            </button>
          </div>
        </div>
        <!-- Right: Professional Pagination Controls -->
        <div class="flex items-center gap-space-md justify-between md:justify-end">
          <div class="flex items-center gap-space-xs font-label-sm text-label-sm text-on-surface-variant">
            <span>每页显示</span>
            <select
              v-model="pageSize"
              class="bg-surface-container-lowest px-2 py-1 rounded-md text-on-surface font-label-sm text-label-sm focus:outline-none cursor-pointer"
            >
              <option :value="10">10条</option>
              <option :value="20">20条</option>
              <option :value="50">50条</option>
            </select>
            <span>共 {{ totalJobs }} 条 · 第 {{ currentPage }} / {{ totalPages }} 页</span>
          </div>
          <nav aria-label="分页导航" class="flex items-center gap-1">
            <button
              aria-label="上一页"
              class="w-8 h-8 rounded-lg flex items-center justify-center text-outline bg-surface-container-lowest/50 transition-colors"
              :class="currentPage === 1 ? 'opacity-40 cursor-not-allowed' : 'hover:bg-surface-container'"
              :disabled="currentPage === 1"
              @click="currentPage = Math.max(1, currentPage - 1)"
            >
              <span class="material-symbols-outlined text-[18px]">chevron_left</span>
            </button>
            <button
              v-for="page in totalPages"
              :key="page"
              class="w-8 h-8 rounded-lg flex items-center justify-center font-label-md text-label-md transition-colors"
              :class="currentPage === page ? 'bg-primary-container text-on-primary font-bold shadow-sm' : 'text-on-surface-variant hover:bg-surface-container hover:text-on-surface'"
              @click="currentPage = page"
            >
              {{ page }}
            </button>
            <button
              aria-label="下一页"
              class="w-8 h-8 rounded-lg flex items-center justify-center text-on-surface bg-surface-container-lowest hover:bg-surface-container transition-colors shadow-sm"
              :disabled="currentPage === totalPages"
              @click="currentPage = Math.min(totalPages, currentPage + 1)"
            >
              <span class="material-symbols-outlined text-[18px]">chevron_right</span>
            </button>
          </nav>
        </div>
      </div>
    </div>

    <!-- 6. Quick Guidance & Enterprise Policy Tip Card -->
    <div class="bg-surface-container-low rounded-xl p-space-md flex items-start gap-space-md">
      <div class="w-8 h-8 rounded-lg bg-surface-container-high flex items-center justify-center text-primary shrink-0">
        <span class="material-symbols-outlined text-[20px]">info</span>
      </div>
      <div class="flex-1 text-on-surface-variant">
        <p class="font-label-md text-label-md font-semibold text-on-surface">校园春季联合双选会职位同步提醒</p>
        <p class="font-body-sm text-body-sm mt-0.5 leading-relaxed">
          标记为"2025届校招"的招聘中岗位已自动同步至清华、北大、北航等52所联盟高校就业宣讲网络。若需要临时控制投递流量，可通过"暂停职位"或调整每日接收限额；职位关闭后可随时在"已关闭"归档中重新激活。
        </p>
      </div>
      <router-link to="/enterprise/recruitment-rules" class="font-label-sm text-label-sm text-primary hover:underline shrink-0 self-center font-semibold">了解校招规则 →</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'

const searchQuery = ref('')
const activeTab = ref('all')
const selectedCategory = ref('')
const selectAll = ref(false)
const selectedJobs = ref([])
const currentPage = ref(1)
const pageSize = ref(10)

const kpi = ref({
  activeJobs: 12,
  totalQuota: 20,
  totalResumes: '1,428',
  todaySubmissions: 46,
  pendingScreening: 32,
  pausedArchived: 4
})

const statusTabs = ref([
  { key: 'all', label: '全部', count: 16 },
  { key: 'active', label: '招聘中', count: 12 },
  { key: 'paused', label: '已暂停', count: 3 },
  { key: 'closed', label: '已关闭', count: 1 }
])

const jobs = ref([
  {
    id: 1,
    title: '大模型算法工程师',
    category: '人工智能',
    code: 'REQ-202503-010',
    publisher: '张立(校招组)',
    location: '北京 · 海淀',
    workType: '全职 / 实习双轨',
    salary: '25k - 40k',
    salaryNote: '16薪 · 顶尖学术奖金',
    publishDate: '2025-03-01',
    publishTimeAgo: '10天前',
    applications: 342,
    todayNew: 18,
    applicationsNote: '',
    views: '4,890',
    status: 'active',
    tags: { recruitment: '2025届校招', permanent: '可转正' }
  },
  {
    id: 2,
    title: '前端开发实习生',
    category: '前端研发',
    code: 'REQ-202503-008',
    publisher: '李浩',
    location: '北京 · 海淀',
    workType: '日常实习 / 3个月+',
    salary: '400 - 500 元/天',
    salaryNote: '包三餐 · 房补1500',
    publishDate: '2025-03-05',
    publishTimeAgo: '6天前',
    applications: 189,
    todayNew: 8,
    applicationsNote: '',
    views: '2,310',
    status: 'active',
    tags: { recruitment: '', permanent: '可转正' }
  },
  {
    id: 3,
    title: '后端开发工程师',
    category: '后端研发',
    code: 'REQ-202502-019',
    publisher: '王雷(深圳研发中心)',
    location: '深圳 · 南山',
    workType: '全职正式编制',
    salary: '18k - 28k',
    salaryNote: '15薪 · 弹性打卡',
    publishDate: '2025-02-20',
    publishTimeAgo: '19天前',
    applications: 512,
    todayNew: 12,
    applicationsNote: '',
    views: '6,420',
    status: 'active',
    tags: { recruitment: '校招', permanent: '' }
  },
  {
    id: 4,
    title: 'AI 产品经理实习生',
    category: '产品策划',
    code: 'REQ-202501-004',
    publisher: '赵晓萌',
    location: '上海 · 徐汇',
    workType: '日常实习',
    salary: '300 - 450 元/天',
    salaryNote: '大牛导师1v1带教',
    publishDate: '2025-01-15',
    publishTimeAgo: '约2个月前',
    applications: 226,
    todayNew: 0,
    applicationsNote: '简历库已饱和',
    views: '3,150',
    status: 'paused',
    tags: { recruitment: '', permanent: '' }
  },
  {
    id: 5,
    title: '计算机视觉研究员',
    category: '人工智能',
    code: 'REQ-202411-002',
    publisher: '陆教授专班',
    location: '北京 · 海淀',
    workType: '全职博士站',
    salary: '30k - 50k',
    salaryNote: '16薪 · 专项科研经费',
    publishDate: '2024-11-10',
    publishTimeAgo: '往期职位',
    applications: 159,
    todayNew: 0,
    applicationsNote: '流程已结束',
    views: '2,800',
    status: 'closed',
    tags: { recruitment: '', permanent: '' }
  }
])

const filteredJobs = computed(() => {
  let result = jobs.value

  if (activeTab.value !== 'all') {
    result = result.filter(job => job.status === activeTab.value)
  }

  if (selectedCategory.value) {
    const categoryMap = {
      ai: '人工智能',
      fe: '前端研发',
      be: '后端研发',
      pm: '产品策划',
      qa: '质量保障与测试'
    }
    result = result.filter(job => job.category === categoryMap[selectedCategory.value])
  }

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(job =>
      job.title.toLowerCase().includes(query) ||
      job.publisher.toLowerCase().includes(query) ||
      job.category.toLowerCase().includes(query)
    )
  }

  return result
})

const totalJobs = computed(() => filteredJobs.value.length)
const totalPages = computed(() => Math.max(1, Math.ceil(totalJobs.value / pageSize.value)))

function statusBadgeClass(status) {
  switch (status) {
    case 'active': return 'bg-emerald-50 text-emerald-700'
    case 'paused': return 'bg-amber-50 text-amber-700'
    case 'closed': return 'bg-surface-container-high text-outline font-medium'
    default: return ''
  }
}

function statusLabel(status) {
  switch (status) {
    case 'active': return '招聘中'
    case 'paused': return '已暂停'
    case 'closed': return '已关闭'
    default: return ''
  }
}

watch(selectAll, (val) => {
  if (val) {
    selectedJobs.value = filteredJobs.value.map(job => job.id)
  } else {
    selectedJobs.value = []
  }
})

watch(selectedJobs, (val) => {
  selectAll.value = val.length === filteredJobs.value.length && filteredJobs.value.length > 0
})

onMounted(() => {
  document.title = '职位管理 - 校园就业综合服务平台'
})
</script>
