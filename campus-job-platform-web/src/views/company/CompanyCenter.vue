<template>
  <div class="flex flex-col min-h-screen bg-background">
    <!-- Top Identity Header Bar & Welcome Zone -->
    <section class="w-full bg-surface-container-low px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-xl">
      <div class="max-w-7xl mx-auto flex flex-col lg:flex-row lg:items-center justify-between gap-space-lg">
        <!-- Left: Identity & Status Overview -->
        <div class="space-y-space-xs max-w-2xl">
          <div class="flex flex-wrap items-center gap-space-xs">
            <div class="w-10 h-10 rounded-lg bg-primary-container flex items-center justify-center text-on-primary shadow-sm">
              <span class="material-symbols-outlined text-[24px]">corporate_fare</span>
            </div>
            <h1 class="font-headline-lg text-headline-lg text-on-surface tracking-tight font-bold">{{ company.name }}</h1>
            <span class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed-variant font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">verified</span>
              已通过高校联合认证 · 优质名企雇主
            </span>
          </div>
          <p class="font-body-lg text-body-lg text-on-surface-variant pt-1">
            下午好，张经理！您今天有 <span class="font-headline-sm text-headline-sm text-primary font-bold">{{ metrics.todayResumes }} 份</span> 新投递的应届生简历待查阅与评估。
          </p>
          <div class="flex items-center gap-space-md text-on-surface-variant pt-space-2xs">
            <span class="flex items-center gap-1 font-label-md text-label-md">
              <span class="material-symbols-outlined text-[16px] text-secondary">workspace_premium</span>
              校招等级: {{ company.partnerLevel }}
            </span>
            <span class="inline-block w-1 h-1 rounded-full bg-outline-variant"></span>
            <span class="flex items-center gap-1 font-label-md text-label-md">
              <span class="material-symbols-outlined text-[16px] text-primary">schedule</span>
              平均简历初筛耗时: {{ company.avgScreenTime }}
            </span>
          </div>
        </div>
        <!-- Right: Primary & Secondary Actions -->
        <div class="flex flex-wrap sm:flex-nowrap items-center gap-space-sm shrink-0">
          <router-link
            to="/company/post-job"
            class="group flex items-center justify-center gap-space-xs px-space-md py-space-sm rounded-lg bg-primary-container hover:bg-primary text-on-primary font-label-lg text-label-lg shadow-md hover:shadow-lg transition-all transform hover:-translate-y-0.5"
          >
            <span class="material-symbols-outlined text-[20px] text-secondary-container animate-pulse">bolt</span>
            <span>+ 发布新校招/实习职位</span>
          </router-link>
          <router-link
            to="/company/resumes"
            class="flex items-center justify-center gap-space-xs px-space-md py-space-sm rounded-lg bg-surface-container-lowest text-on-surface hover:bg-surface-container font-label-lg text-label-lg shadow-sm hover:shadow-md transition-all"
          >
            <span class="material-symbols-outlined text-[18px] text-on-surface-variant">folder_shared</span>
            <span>查看所有投递简历</span>
          </router-link>
          <router-link
            to="/company/career-fair"
            class="flex items-center justify-center gap-space-xs p-space-sm rounded-lg bg-surface-container-lowest text-secondary hover:bg-secondary-fixed/50 transition-colors shadow-sm"
            title="预约高校宣讲会 / 空中双选会"
          >
            <span class="material-symbols-outlined text-[20px]">event_seat</span>
            <span class="hidden xl:inline font-label-md text-label-md">预约宣讲会 / 空中双选会</span>
          </router-link>
        </div>
      </div>
    </section>

    <!-- Metric Telemetry Cards Grid -->
    <section class="w-full px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-lg">
      <div class="max-w-7xl mx-auto grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-space-md">
        <!-- Card 1: Active Jobs -->
        <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">在招职位数</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-[20px]">work</span>
            </div>
          </div>
          <div class="mt-space-xs flex items-baseline gap-space-xs">
            <span class="font-display-hero text-headline-xl text-on-surface font-bold">{{ metrics.activeJobs }}</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">个</span>
            <span class="ml-auto inline-flex items-center gap-0.5 px-2 py-0.5 rounded-full bg-surface-container-high text-primary font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">arrow_upward</span>
              +3 较上月
            </span>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm bg-surface-container-low px-space-xs py-1 rounded">
            <span>全职校招: <strong class="text-on-surface">{{ metrics.fullTimeJobs }}</strong></span>
            <span class="inline-block w-1 h-1 rounded-full bg-outline-variant"></span>
            <span>实习转正: <strong class="text-on-surface">{{ metrics.internJobs }}</strong></span>
          </div>
        </div>

        <!-- Card 2: Cumulative Applications with Mini Bar Sparkline -->
        <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">累计收到投递</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-secondary">
              <span class="material-symbols-outlined text-[20px]">assignment_ind</span>
            </div>
          </div>
          <div class="mt-space-xs flex items-baseline justify-between">
            <div>
              <div class="flex items-baseline gap-1">
                <span class="font-display-hero text-headline-xl text-on-surface font-bold">{{ formatNumber(metrics.totalApplications) }}</span>
                <span class="font-label-sm text-label-sm text-on-surface-variant">份</span>
              </div>
              <p class="font-label-sm text-label-sm text-secondary mt-0.5 font-medium">本周新增 +156 份</p>
            </div>
            <!-- Inline Sparkline Bar SVG -->
            <div class="w-20 h-10 flex items-end justify-between gap-1 pl-2">
              <div class="w-2 bg-secondary-fixed rounded-t h-[35%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[48%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[62%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[50%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[80%]"></div>
              <div class="w-2 bg-secondary rounded-t h-[100%] shadow-sm"></div>
            </div>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm">
            <span>重点985/211占比</span>
            <span class="font-label-md text-label-md text-on-surface font-semibold">{{ metrics.topUniversityRate }}</span>
          </div>
        </div>

        <!-- Card 3: Pending Action Items with Urgency Alert -->
        <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">待处理投递</span>
            <div class="w-8 h-8 rounded-lg bg-tertiary-fixed flex items-center justify-center text-tertiary">
              <span class="material-symbols-outlined text-[20px]">hourglass_top</span>
            </div>
          </div>
          <div class="mt-space-xs flex items-baseline gap-space-xs">
            <span class="font-display-hero text-headline-xl text-on-surface font-bold">{{ metrics.pendingCount }}</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">份</span>
            <span class="ml-auto inline-flex items-center gap-1 px-2 py-0.5 rounded-full bg-tertiary-fixed text-tertiary font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">notification_important</span>
              {{ metrics.urgentCount }} 份需紧急响应
            </span>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm bg-surface-container-low px-space-xs py-1 rounded">
            <span class="text-tertiary font-medium">48h待初筛预警</span>
            <span class="font-label-sm text-label-sm text-on-surface">尽快推进邀约</span>
          </div>
        </div>

        <!-- Card 4: Today's Intake & Quality Index -->
        <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">今日新增投递</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-primary-container">
              <span class="material-symbols-outlined text-[20px]">mark_email_unread</span>
            </div>
          </div>
          <div class="mt-space-xs flex items-baseline gap-space-xs">
            <span class="font-display-hero text-headline-xl text-on-surface font-bold">{{ metrics.todayResumes }}</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">份</span>
            <span class="ml-auto inline-flex items-center gap-0.5 px-2 py-0.5 rounded-full bg-surface-container text-primary font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">trending_up</span>
              较昨日 +24%
            </span>
          </div>
          <div class="mt-space-sm pt-space-xs flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm">
            <span>今日HR响应率</span>
            <span class="font-label-md text-label-md text-primary font-bold">{{ metrics.hrResponseRate }}</span>
          </div>
        </div>
      </div>
    </section>

    <!-- Main Functional Workstation Area -->
    <section class="w-full px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-md mb-space-2xl">
      <div class="max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-space-lg items-start">
        <!-- Left Column: Candidates Application Workflow (8 Cols) -->
        <div class="lg:col-span-8 flex flex-col gap-space-md">
          <!-- Filter Tabs & Real-time Search Box -->
          <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm flex flex-col sm:flex-row sm:items-center justify-between gap-space-md">
            <div class="flex flex-wrap items-center gap-space-2xs">
              <button
                v-for="tab in filterTabs"
                :key="tab.key"
                :class="[
                  'px-space-sm py-1.5 rounded-full font-label-sm text-label-sm font-medium transition-all',
                  activeTab === tab.key
                    ? 'bg-primary text-on-primary shadow-sm font-semibold'
                    : 'bg-surface-container hover:bg-surface-container-high text-on-surface-variant'
                ]"
                @click="activeTab = tab.key"
              >
                {{ tab.label }} ({{ tab.count }})
              </button>
            </div>
            <div class="relative min-w-[240px]">
              <span class="material-symbols-outlined absolute left-3 top-2.5 text-on-surface-variant text-[18px]">search</span>
              <input
                v-model="searchQuery"
                class="w-full pl-9 pr-3 py-1.5 text-body-md font-body-md bg-surface-container-low rounded-lg focus:outline-none focus:bg-surface-container-lowest text-on-surface shadow-inner placeholder:text-on-surface-variant/70 transition-all"
                placeholder="搜索候选人/学校/意向岗位..."
                type="text"
              />
            </div>
          </div>

          <!-- Candidate Feed / Table Stream -->
          <div class="flex flex-col gap-space-sm">
            <div
              v-for="candidate in filteredCandidates"
              :key="candidate.id"
              class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm hover:shadow-md transition-all flex flex-col md:flex-row md:items-center justify-between gap-space-md"
            >
              <div class="flex items-start gap-space-sm min-w-0">
                <img
                  class="w-12 h-12 rounded-full object-cover shrink-0 shadow-sm"
                  :src="candidate.avatar"
                  :alt="candidate.name"
                />
                <div class="min-w-0 space-y-1">
                  <div class="flex flex-wrap items-center gap-space-xs">
                    <span class="font-headline-sm text-headline-sm text-on-surface font-bold">{{ candidate.name }}</span>
                    <span class="font-label-sm text-label-sm px-2 py-0.5 rounded bg-surface-container-high text-primary font-semibold">{{ candidate.school }} · {{ candidate.major }}</span>
                    <span class="font-label-sm text-label-sm text-on-surface-variant">{{ candidate.year }}</span>
                  </div>
                  <div class="flex flex-wrap items-center gap-space-sm font-body-md text-body-md text-on-surface-variant">
                    <span class="text-on-surface font-medium">投递：{{ candidate.position }}</span>
                    <span class="inline-block w-1 h-1 rounded-full bg-outline-variant"></span>
                    <span class="font-body-sm text-body-sm">{{ candidate.applyTime }}</span>
                  </div>
                  <div class="flex items-center gap-space-xs pt-0.5">
                    <div :class="[
                      'flex items-center gap-1 px-2 py-0.5 rounded-full font-label-sm text-label-sm font-bold',
                      candidate.matchBg,
                      candidate.matchText
                    ]">
                      <span class="material-symbols-outlined text-[14px]">auto_awesome</span>
                      {{ candidate.matchLabel }}
                    </div>
                    <span
                      v-for="(tag, ti) in candidate.tags"
                      :key="ti"
                      :class="[
                        'px-2 py-0.5 rounded-full font-label-sm text-label-sm',
                        tag.bg,
                        tag.text
                      ]"
                    >
                      {{ tag.label }}
                    </span>
                  </div>
                </div>
              </div>
              <div class="flex flex-row md:flex-col lg:flex-row items-center justify-between md:justify-end gap-space-xs shrink-0 pt-space-xs md:pt-0">
                <span :class="[
                  'inline-flex px-2.5 py-1 rounded-full font-label-sm text-label-sm font-semibold',
                  candidate.statusBg,
                  candidate.statusText
                ]">
                  {{ candidate.status }}
                </span>
                <div class="flex items-center gap-space-2xs">
                  <button
                    v-for="(action, ai) in candidate.actions"
                    :key="ai"
                    :class="[
                      'px-3 py-1.5 rounded-lg font-label-md text-label-md font-medium transition-colors',
                      action.primary
                        ? 'bg-primary text-on-primary hover:bg-primary-container shadow-sm font-semibold'
                        : 'bg-surface-container hover:bg-surface-container-high text-on-surface'
                    ]"
                  >
                    {{ action.label }}
                  </button>
                  <button
                    v-if="candidate.showReject"
                    class="p-1.5 rounded-lg hover:bg-surface-container text-on-surface-variant transition-colors"
                    title="标记不合适"
                  >
                    <span class="material-symbols-outlined text-[18px]">close</span>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Pagination & Batch Processing Drawer -->
          <div class="flex items-center justify-between bg-surface-container-lowest rounded-xl px-space-md py-space-sm shadow-sm">
            <span class="font-body-sm text-body-sm text-on-surface-variant">
              显示 1 至 {{ filteredCandidates.length }} 项，共 {{ metrics.pendingCount }} 份应聘简历
            </span>
            <div class="flex items-center gap-1">
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface-variant hover:bg-surface-container font-label-sm text-label-sm">上一页</button>
              <button class="px-2.5 py-1 rounded bg-primary text-on-primary font-label-sm text-label-sm font-bold">1</button>
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface hover:bg-surface-container font-label-sm text-label-sm">2</button>
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface hover:bg-surface-container font-label-sm text-label-sm">3</button>
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface-variant hover:bg-surface-container font-label-sm text-label-sm">下一页</button>
            </div>
          </div>
        </div>

        <!-- Right Column: Funnel Analytics, Schedule, Campus Dispatch (4 Cols) -->
        <div class="lg:col-span-4 flex flex-col gap-space-md">
          <!-- Recruitment Conversion Funnel -->
          <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm">
            <div class="flex items-center justify-between mb-space-sm">
              <div class="flex items-center gap-space-xs">
                <span class="material-symbols-outlined text-primary text-[20px]">filter_alt</span>
                <h2 class="font-headline-sm text-headline-sm text-on-surface font-bold">招聘漏斗转化数据</h2>
              </div>
              <span class="font-label-sm text-label-sm text-on-surface-variant">{{ funnel.season }}</span>
            </div>
            <div class="space-y-space-sm pt-space-xs">
              <div v-for="stage in funnel.stages" :key="stage.label">
                <div class="flex justify-between font-label-md text-label-md mb-1">
                  <span class="text-on-surface">{{ stage.label }}</span>
                  <span :class="['font-bold', stage.color]">{{ stage.value }} ({{ stage.percent }})</span>
                </div>
                <div class="w-full h-2.5 bg-surface-container rounded-full overflow-hidden">
                  <div :class="['h-full rounded-full', stage.barColor]" :style="{ width: stage.barWidth }"></div>
                </div>
              </div>
            </div>
            <div class="mt-space-md p-space-xs bg-surface-container-low rounded-lg flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm">
              <span>当前Offer承接率: <strong class="text-primary font-bold">{{ funnel.offerAcceptRate }}</strong></span>
              <router-link to="/company/analytics" class="text-primary hover:underline font-label-sm text-label-sm font-semibold">分析报表 →</router-link>
            </div>
          </div>

          <!-- Pending Schedule & Upcoming Campus Events -->
          <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm">
            <div class="flex items-center justify-between mb-space-sm">
              <div class="flex items-center gap-space-xs">
                <span class="material-symbols-outlined text-secondary text-[20px]">calendar_month</span>
                <h2 class="font-headline-sm text-headline-sm text-on-surface font-bold">待办日程与即将开始</h2>
              </div>
              <span class="px-2 py-0.5 rounded-full bg-surface-container text-on-surface font-label-sm text-label-sm font-semibold">今日 {{ schedule.todayCount }} 项</span>
            </div>
            <div class="space-y-space-xs">
              <div
                v-for="(event, ei) in schedule.events"
                :key="ei"
                class="p-space-sm rounded-lg bg-surface-container-low hover:bg-surface-container transition-colors flex items-start gap-space-sm"
              >
                <div :class="[
                  'w-10 h-10 rounded-lg flex flex-col items-center justify-center shrink-0 text-on-primary',
                  event.iconBg
                ]">
                  <span class="font-label-sm text-label-sm leading-none font-bold">{{ event.timeLabel }}</span>
                  <span class="font-label-sm text-[9px] opacity-80">{{ event.timeSub }}</span>
                </div>
                <div class="min-w-0">
                  <h3 class="font-headline-sm text-body-md text-on-surface font-semibold truncate">{{ event.title }}</h3>
                  <p class="font-body-sm text-body-sm text-on-surface-variant truncate">{{ event.detail }}</p>
                  <div v-if="event.link" class="mt-1 flex items-center gap-space-xs">
                    <router-link :to="event.link.to" class="inline-flex items-center gap-1 font-label-sm text-label-sm text-primary font-semibold hover:underline">
                      <span class="material-symbols-outlined text-[14px]">{{ event.link.icon }}</span>
                      {{ event.link.label }}
                    </router-link>
                  </div>
                  <div v-if="event.infoText" class="mt-1 flex items-center gap-space-xs">
                    <span class="font-label-sm text-label-sm text-secondary font-semibold">{{ event.infoText }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Official University Liaison Station Notice Card -->
          <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm">
            <div class="flex items-center gap-space-xs mb-space-sm">
              <span class="material-symbols-outlined text-primary text-[20px]">campaign</span>
              <h2 class="font-headline-sm text-headline-sm text-on-surface font-bold">高校就业联络站通知</h2>
            </div>
            <div class="space-y-space-sm">
              <div class="p-space-sm rounded-lg bg-surface-container-low space-y-1">
                <div class="flex items-center justify-between">
                  <span class="font-label-sm text-label-sm text-primary font-bold">官方通知</span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant">2小时前</span>
                </div>
                <h3 class="font-body-md text-body-md text-on-surface font-semibold leading-snug">教育部及全国重点高校2025届春季联合双选会报名正式开放</h3>
                <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed">
                  企业可免费申请高校线上展位并获取跨校统一简历投递通道，支持精准定向匹配对口工科生源。
                </p>
                <div class="pt-1">
                  <router-link to="/company/career-fair" class="inline-flex items-center gap-1 font-label-sm text-label-sm text-primary font-semibold hover:underline">
                    立即申请入驻展位 →
                  </router-link>
                </div>
              </div>
              <div class="p-space-sm rounded-lg bg-surface-container-low space-y-1">
                <div class="flex items-center justify-between">
                  <span class="font-label-sm text-label-sm text-secondary font-bold">政策直通</span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant">昨天</span>
                </div>
                <h3 class="font-body-md text-body-md text-on-surface font-semibold leading-snug">北京市高新技术企业高校应届毕业生引才补贴申领指南</h3>
                <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed">
                  包含社保减免及校企联合培养资金支持，审核进度已接入校招综合系统。
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <footer class="w-full bg-inverse-surface text-inverse-on-surface mt-auto">
      <div class="max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-2xl">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-space-xl">
          <!-- Brand & Description -->
          <div class="space-y-space-md">
            <div class="flex items-center gap-space-xs">
              <span class="material-symbols-outlined text-inverse-primary text-[28px]">school</span>
              <span class="font-headline-sm text-headline-sm text-inverse-on-surface font-bold">校园就业综合服务平台</span>
            </div>
            <p class="font-body-sm text-body-sm text-inverse-on-surface/80 leading-relaxed">服务高校大学生实习求职与校企直聘的数字化一站式就业枢纽平台。构筑产学融合协同桥梁，赋能青年人才高质量就业与职业成长。</p>
          </div>
          <!-- Quick Links -->
          <div>
            <h4 class="font-headline-sm text-headline-sm text-inverse-on-surface font-semibold mb-space-md">快速链接</h4>
            <ul class="space-y-space-xs">
              <li class="font-body-sm text-body-sm"><router-link to="/about" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">关于我们</router-link></li>
              <li class="font-body-sm text-body-sm"><router-link to="/contact" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">联系方式</router-link></li>
              <li class="font-body-sm text-body-sm"><router-link to="/privacy" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">隐私保护政策</router-link></li>
              <li class="font-body-sm text-body-sm"><router-link to="/terms" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">用户服务协议</router-link></li>
            </ul>
          </div>
          <!-- Cooperation Channels -->
          <div>
            <h4 class="font-headline-sm text-headline-sm text-inverse-on-surface font-semibold mb-space-md">合作通道</h4>
            <ul class="space-y-space-xs">
              <li class="font-body-sm text-body-sm"><router-link to="/partner/university" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">高校就业中心入驻</router-link></li>
              <li class="font-body-sm text-body-sm"><router-link to="/partner/enterprise" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">名企专属招聘通告</router-link></li>
              <li class="font-body-sm text-body-sm"><router-link to="/partner/career-fair" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">双选会承办合作</router-link></li>
              <li class="font-body-sm text-body-sm"><router-link to="/partner/internship" class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors">实习基地共建</router-link></li>
            </ul>
          </div>
          <!-- Contact Info -->
          <div>
            <h4 class="font-headline-sm text-headline-sm text-inverse-on-surface font-semibold mb-space-md">联系咨询</h4>
            <div class="space-y-space-xs font-body-sm text-body-sm text-inverse-on-surface/80">
              <p class="flex items-center gap-space-xs">
                <span class="material-symbols-outlined text-[18px] text-inverse-primary">call</span>
                <span>服务热线：400-820-2025</span>
              </p>
              <p class="flex items-center gap-space-xs">
                <span class="material-symbols-outlined text-[18px] text-inverse-primary">mail</span>
                <span>高校合作：career@campus-service.edu.cn</span>
              </p>
              <p class="flex items-center gap-space-xs">
                <span class="material-symbols-outlined text-[18px] text-inverse-primary">business</span>
                <span>企业HR专线：hr-partner@campus-service.com</span>
              </p>
              <p class="flex items-center gap-space-xs">
                <span class="material-symbols-outlined text-[18px] text-inverse-primary">schedule</span>
                <span>服务时间：工作日 09:00 - 18:00</span>
              </p>
            </div>
          </div>
        </div>
        <div class="mt-space-xl pt-space-md border-t border-inverse-on-surface/10 flex flex-col md:flex-row items-center justify-between gap-space-sm font-label-sm text-label-sm text-inverse-on-surface/60">
          <p>© 2025 校园就业综合服务平台 版权所有</p>
          <p>企业招聘信息备案登记号：ICP备20250888号-1 | 全国高校就业指导中心监制认证</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

const searchQuery = ref('')
const activeTab = ref('all')

const company = ref({
  name: '智航未来科技有限公司',
  partnerLevel: '钻石合作伙伴',
  avgScreenTime: '1.4 工作日',
})

const metrics = ref({
  activeJobs: 18,
  fullTimeJobs: 12,
  internJobs: 6,
  totalApplications: 1428,
  pendingCount: 32,
  urgentCount: 8,
  todayResumes: 46,
  hrResponseRate: '96.5%',
  topUniversityRate: '68.4%',
})

const filterTabs = ref([
  { key: 'all', label: '全部投递', count: 32 },
  { key: 'pending', label: '待初筛', count: 14 },
  { key: 'interview', label: '待邀约笔试/面试', count: 9 },
  { key: 'offered', label: '已发Offer', count: 5 },
  { key: 'rejected', label: '不合适', count: 4 },
])

const candidates = ref([
  {
    id: 1,
    name: '周明翰',
    school: '清华大学',
    major: '软件工程',
    year: '2025届硕士',
    position: '算法工程师 (校招)',
    applyTime: '今天 11:24 投递',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCYJdr9Df-Pe_S7IhjSZ_hi7VdGuywsh2p_Nu4BVJPzceDxmJ10rVG3ajTNFwf6vDRVWRLueEul3U4QanVCQWXw7D63pjt-SxnmwAt3Pn3QUsD57RQURCC8dZAMyvXNsUjmOwbT4Ez983PSy9FyMENtPRfg5e_37SpY5fGo4nYhq5vWtECJnIV7p28qqeOC__LmDlTv6EuHNofgM2QGii2zKc9hEx6kgi-zZ84OlwkOiTgxxhQ9SFvC4A',
    matchLabel: '96% AI契合 · 算法卓越',
    matchBg: 'bg-secondary-fixed',
    matchText: 'text-on-secondary-fixed',
    tags: [
      { label: 'ACM区域金牌', bg: 'bg-primary-fixed', text: 'text-on-primary-fixed' },
    ],
    status: '待初筛',
    statusBg: 'bg-surface-container-high',
    statusText: 'text-primary',
    actions: [
      { label: '预览简历', primary: false },
      { label: '发起面试', primary: true },
    ],
    showReject: true,
  },
  {
    id: 2,
    name: '李思琪',
    school: '北京航空航天大学',
    major: '计算机科学',
    year: '2025届本科',
    position: '前端开发实习生 (可转正)',
    applyTime: '今天 09:40 投递',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBdVIQHqSEJeHFiqOlp9_6R2Fwv_VoJkV4bORuMSdZt8N3y78Bwwd4m6jfIKxukP850E8z69zWFdZHhp5c9p47iY45IRlQDm682z1WJtz9bbNF6Wi8EOhX0dzQcdt-lrTcUmxS5VyTKyAHLrXqFw3fcRVnEutRZpvw5goLwDuXp0PCs_Oy6WWT3SUSfLTV-UtU7xEaRr_4DBqNgfAXarTgTSHN4vsFokVqV31uVzC1PTS-N53N4PFuC5A',
    matchLabel: '92% 高度匹配',
    matchBg: 'bg-secondary-fixed',
    matchText: 'text-on-secondary-fixed',
    tags: [
      { label: '大厂实习经验 x 1', bg: 'bg-surface-container', text: 'text-on-surface-variant' },
    ],
    status: '48h紧急待办',
    statusBg: 'bg-tertiary-fixed',
    statusText: 'text-tertiary',
    actions: [
      { label: '预览简历', primary: false },
      { label: '发起笔试', primary: true },
    ],
    showReject: true,
  },
  {
    id: 3,
    name: '赵程安',
    school: '上海交通大学',
    major: '自动化系',
    year: '2025届硕士',
    position: '大模型应用架构师 (青年先锋计划)',
    applyTime: '昨日 17:15 投递',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDnb75pVhxpvkRA_ZdUtZZVjuGjT3e8goyAEinTon_RWezr0xgE-R4PySF_7AZpGnuIJBYJzi815ywgcVNar-P9P0TlGSq3rEVi6DKn_bLTvmdRXRiCc83cv4TbbvMP9gtw9nDmgTU25RgrbjUzPjBhNUdhG95xKWUpreiKZoZRFgYINSOEWqSV41twBZksK0tkt4DFSUjK3ks1E51ozhdMd5nqvtSwzdf1ES72T3j9cUgBW_KpR8Knbw',
    matchLabel: '95% 卓越契合',
    matchBg: 'bg-secondary-fixed',
    matchText: 'text-on-secondary-fixed',
    tags: [
      { label: 'NeurIPS 发表第一作者', bg: 'bg-surface-container', text: 'text-on-surface-variant' },
    ],
    status: '二面进行中',
    statusBg: 'bg-secondary-fixed-dim',
    statusText: 'text-on-secondary-fixed',
    actions: [
      { label: '评估记录', primary: false },
      { label: '录用意向', primary: false },
    ],
    showReject: false,
  },
  {
    id: 4,
    name: '何雨萱',
    school: '浙江大学',
    major: '计算机科学与技术',
    year: '2025届本科',
    position: '后端开发工程师 (校招)',
    applyTime: '昨日 14:02 投递',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAbTEVeefCyq6BI-Z2AQhXtNExbFr6Ifydv7vcKjTfeyBL1uYQ0LAM4IoqKk1qQytmZwVhoT8S9Zo1f0tcuYmVFfUVf4yh4y_XtfGM7ljZUwcF-7Jjl5tZ083goFA_Q5WuR6OIxu3ljdb8qvgsCcWjuX5b5rUj6hDQ7LnsSvTe5mvIL9JezS6ZkdTNMTR44JX3ge6oL09-SniRjzmwAlDhv-P2SyuXcWaycsqq4DA1EIHZTkzHYu2erbA',
    matchLabel: '88% 良好匹配',
    matchBg: 'bg-surface-container-highest',
    matchText: 'text-on-surface',
    tags: [
      { label: '校级一等奖学金', bg: 'bg-surface-container', text: 'text-on-surface-variant' },
    ],
    status: '待初筛',
    statusBg: 'bg-surface-container',
    statusText: 'text-on-surface-variant',
    actions: [
      { label: '预览简历', primary: false },
      { label: '发起面试', primary: true },
    ],
    showReject: true,
  },
  {
    id: 5,
    name: '孙楚越',
    school: '华中科技大学',
    major: '人工智能学院',
    year: '2025届硕士',
    position: '计算机视觉研究员 (校招)',
    applyTime: '前天 18:30 投递',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCBl3mmGdllfN5ctCPejEekF-lEqxAGZbpkW9eG_xDjXAv_afyILp86rj5m-4jaGJYQ7-unVtbPC6y8FMog3KMq_fYyNoVGkknvhWZk7q3z7fNWfNFIr2DjxsokatZ5r_KcLN_6Y7eXvodz2QtH9kZAZUvOvNEjrJ8_X2cwglKusO4YnFprKLUlGTWExJym0RuN18r6Nen2a-rP04BHpP1aSDHDbtsOcX6AdIuzCUkFxMD0xKtRk31a6A',
    matchLabel: '94% 卓越匹配',
    matchBg: 'bg-secondary-fixed',
    matchText: 'text-on-secondary-fixed',
    tags: [
      { label: 'CVPR 录用论文', bg: 'bg-surface-container-high', text: 'text-primary' },
    ],
    status: '已发Offer待签约',
    statusBg: 'bg-primary-container',
    statusText: 'text-on-primary',
    actions: [
      { label: '查看三方协议', primary: false },
    ],
    showReject: false,
  },
])

const filteredCandidates = computed(() => {
  if (!searchQuery.value) return candidates.value
  const q = searchQuery.value.toLowerCase()
  return candidates.value.filter(
    (c) =>
      c.name.toLowerCase().includes(q) ||
      c.school.toLowerCase().includes(q) ||
      c.position.toLowerCase().includes(q)
  )
})

const funnel = ref({
  season: '2025届春招季',
  offerAcceptRate: '89.3%',
  stages: [
    { label: '投递简历', value: '1,428 份', percent: '100%', barWidth: '100%', barColor: 'bg-primary', color: 'text-on-surface' },
    { label: '初筛通过', value: '482 份', percent: '33.7%', barWidth: '33.7%', barColor: 'bg-secondary', color: 'text-secondary' },
    { label: '面试推进中', value: '126 份', percent: '8.8%', barWidth: '8.8%', barColor: 'bg-secondary-container', color: 'text-secondary-container' },
    { label: '已发 / 待签 Offer', value: '28 份', percent: '2.0%', barWidth: '2.0%', barColor: 'bg-primary-container', color: 'text-primary' },
  ],
})

const schedule = ref({
  todayCount: 2,
  events: [
    {
      timeLabel: '15:30',
      timeSub: '今日',
      iconBg: 'bg-primary',
      title: '算法工程师 · 线上远程二面',
      detail: '候选人: 赵程安 · 会议室 A-302',
      link: { to: '/company/interview/video', icon: 'videocam', label: '进入视频考场' },
      infoText: '',
    },
    {
      timeLabel: '10:00',
      timeSub: '明日',
      iconBg: 'bg-secondary',
      title: '清华大学春季专场双选会',
      detail: '综合体育馆 · 展台位 B-14',
      link: null,
      infoText: '带队HR: 2人 · 纸质物资已核验',
    },
    {
      timeLabel: '周五',
      timeSub: '14:00',
      iconBg: 'bg-surface-container-high',
      title: '空宣直播: 智航未来2025应届招募',
      detail: '平台预约人数: 842 人已关注',
      link: null,
      infoText: '',
    },
  ],
})

function formatNumber(num) {
  return num.toLocaleString()
}

onMounted(() => {
  // Initialize any dynamic data fetching here
})
</script>
