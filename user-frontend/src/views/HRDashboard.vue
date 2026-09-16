<template>
  <div class="min-h-screen bg-background">
    <!-- Company Header -->
    <section class="w-full bg-surface-container-low px-4 md:px-6 lg:px-10 py-8">
      <div class="max-w-7xl mx-auto flex flex-col lg:flex-row lg:items-center justify-between gap-6">
        <div class="space-y-2 max-w-2xl">
          <div class="flex flex-wrap items-center gap-2">
            <div class="w-10 h-10 rounded-lg bg-primary-container flex items-center justify-center text-on-primary shadow-sm">
              <span class="material-symbols-outlined text-[24px]">corporate_fare</span>
            </div>
            <h1 class="font-headline-lg text-headline-lg text-on-surface tracking-tight font-bold">智航未来科技有限公司</h1>
            <span class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed-variant font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">verified</span>
              已通过高校联合认证 · 优质名企雇主
            </span>
          </div>
          <p class="font-body-lg text-body-lg text-on-surface-variant pt-1">
            下午好，张经理！您今天有 <span class="font-headline-sm text-headline-sm text-primary font-bold">8 份</span> 新投递的应届生简历待查阅与评估。
          </p>
          <div class="flex items-center gap-4 text-on-surface-variant pt-0.5">
            <span class="flex items-center gap-1 font-label-md text-label-md">
              <span class="material-symbols-outlined text-[16px] text-secondary">workspace_premium</span>
              校招等级: 钻石合作伙伴
            </span>
            <span class="inline-block w-1 h-1 rounded-full bg-outline-variant"></span>
            <span class="flex items-center gap-1 font-label-md text-label-md">
              <span class="material-symbols-outlined text-[16px] text-primary">schedule</span>
              平均简历初筛耗时: 1.4 工作日
            </span>
          </div>
        </div>
        <div class="flex flex-wrap sm:flex-nowrap items-center gap-3 shrink-0">
          <button class="group flex items-center justify-center gap-2 px-4 py-2.5 rounded-lg bg-primary-container hover:bg-primary text-on-primary font-label-lg text-label-lg shadow-md hover:shadow-lg transition-all transform hover:-translate-y-0.5">
            <span class="material-symbols-outlined text-[20px] text-secondary-container animate-pulse">bolt</span>
            + 发布新校招/实习职位
          </button>
          <button class="flex items-center justify-center gap-2 px-4 py-2.5 rounded-lg bg-surface-container-lowest text-on-surface hover:bg-surface-container font-label-lg text-label-lg shadow-sm hover:shadow-md transition-all">
            <span class="material-symbols-outlined text-[18px] text-on-surface-variant">folder_shared</span>
            查看所有投递简历
          </button>
        </div>
      </div>
    </section>

    <!-- Metric Cards -->
    <section class="w-full px-4 md:px-6 lg:px-10 py-6">
      <div class="max-w-7xl mx-auto grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <!-- Card 1: Active Jobs -->
        <div class="bg-surface-container-lowest rounded-xl p-4 shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">在招职位数</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-primary">
              <span class="material-symbols-outlined text-[20px]">work</span>
            </div>
          </div>
          <div class="mt-2 flex items-baseline gap-2">
            <span class="font-display-hero text-headline-xl text-on-surface font-bold">18</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">个</span>
            <span class="ml-auto inline-flex items-center gap-0.5 px-2 py-0.5 rounded-full bg-surface-container-high text-primary font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">arrow_upward</span>
              +3 较上月
            </span>
          </div>
          <div class="mt-3 pt-2 flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm bg-surface-container-low px-2 py-1 rounded">
            <span>全职校招: <strong class="text-on-surface">12</strong></span>
            <span class="inline-block w-1 h-1 rounded-full bg-outline-variant"></span>
            <span>实习转正: <strong class="text-on-surface">6</strong></span>
          </div>
        </div>

        <!-- Card 2: Cumulative Applications -->
        <div class="bg-surface-container-lowest rounded-xl p-4 shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">累计收到投递</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-secondary">
              <span class="material-symbols-outlined text-[20px]">assignment_ind</span>
            </div>
          </div>
          <div class="mt-2 flex items-baseline justify-between">
            <div>
              <div class="flex items-baseline gap-1">
                <span class="font-display-hero text-headline-xl text-on-surface font-bold">1,428</span>
                <span class="font-label-sm text-label-sm text-on-surface-variant">份</span>
              </div>
              <p class="font-label-sm text-label-sm text-secondary mt-0.5 font-medium">本周新增 +156 份</p>
            </div>
            <div class="w-20 h-10 flex items-end justify-between gap-1 pl-2">
              <div class="w-2 bg-secondary-fixed rounded-t h-[35%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[48%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[62%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[50%]"></div>
              <div class="w-2 bg-secondary-fixed rounded-t h-[80%]"></div>
              <div class="w-2 bg-secondary rounded-t h-[100%] shadow-sm"></div>
            </div>
          </div>
          <div class="mt-3 pt-2 flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm">
            <span>重点985/211占比</span>
            <span class="font-label-md text-label-md text-on-surface font-semibold">68.4%</span>
          </div>
        </div>

        <!-- Card 3: Pending Action Items -->
        <div class="bg-surface-container-lowest rounded-xl p-4 shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">待处理投递</span>
            <div class="w-8 h-8 rounded-lg bg-tertiary-fixed flex items-center justify-center text-tertiary">
              <span class="material-symbols-outlined text-[20px]">hourglass_top</span>
            </div>
          </div>
          <div class="mt-2 flex items-baseline gap-2">
            <span class="font-display-hero text-headline-xl text-on-surface font-bold">32</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">份</span>
            <span class="ml-auto inline-flex items-center gap-1 px-2 py-0.5 rounded-full bg-tertiary-fixed text-tertiary font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">notification_important</span>
              8 份需紧急响应
            </span>
          </div>
          <div class="mt-3 pt-2 flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm bg-surface-container-low px-2 py-1 rounded">
            <span class="text-tertiary font-medium">48h待初筛预警</span>
            <span class="font-label-sm text-label-sm text-on-surface">尽快推进邀约</span>
          </div>
        </div>

        <!-- Card 4: Today's Intake -->
        <div class="bg-surface-container-lowest rounded-xl p-4 shadow-sm hover:shadow-md transition-all">
          <div class="flex items-center justify-between">
            <span class="font-label-md text-label-md text-on-surface-variant">今日新增投递</span>
            <div class="w-8 h-8 rounded-lg bg-surface-container flex items-center justify-center text-primary-container">
              <span class="material-symbols-outlined text-[20px]">mark_email_unread</span>
            </div>
          </div>
          <div class="mt-2 flex items-baseline gap-2">
            <span class="font-display-hero text-headline-xl text-on-surface font-bold">46</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant">份</span>
            <span class="ml-auto inline-flex items-center gap-0.5 px-2 py-0.5 rounded-full bg-surface-container text-primary font-label-sm text-label-sm font-semibold">
              <span class="material-symbols-outlined text-[14px]">trending_up</span>
              较昨日 +24%
            </span>
          </div>
          <div class="mt-3 pt-2 flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm">
            <span>今日HR响应率</span>
            <span class="font-label-md text-label-md text-primary font-bold">96.5%</span>
          </div>
        </div>
      </div>
    </section>

    <!-- Main Content -->
    <section class="w-full px-4 md:px-6 lg:px-10 py-4 mb-8">
      <div class="max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
        <!-- Left: Candidates (8 cols) -->
        <div class="lg:col-span-8 flex flex-col gap-4">
          <!-- Filter Tabs -->
          <div class="bg-surface-container-lowest rounded-xl p-4 shadow-sm flex flex-col sm:flex-row sm:items-center justify-between gap-4">
            <div class="flex flex-wrap items-center gap-1">
              <button v-for="tab in filterTabs" :key="tab.key"
                :class="['px-3 py-1.5 rounded-full font-label-sm text-label-sm font-semibold transition-all', activeTab === tab.key ? 'bg-primary text-on-primary shadow-sm' : 'bg-surface-container hover:bg-surface-container-high text-on-surface-variant font-medium']">
                {{ tab.label }} ({{ tab.count }})
              </button>
            </div>
            <div class="relative min-w-[240px]">
              <span class="material-symbols-outlined absolute left-3 top-2.5 text-on-surface-variant text-[18px]">search</span>
              <input class="w-full pl-9 pr-3 py-1.5 text-body-md font-body-md bg-surface-container-low rounded-lg focus:outline-none focus:bg-surface-container-lowest text-on-surface shadow-inner placeholder:text-on-surface-variant/70 transition-all" placeholder="搜索候选人/学校/意向岗位..." type="text">
            </div>
          </div>

          <!-- Candidate List -->
          <div class="flex flex-col gap-3">
            <div v-for="candidate in candidates" :key="candidate.id"
              class="bg-surface-container-lowest rounded-xl p-4 shadow-sm hover:shadow-md transition-all flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div class="flex items-start gap-3 min-w-0">
                <img :src="candidate.avatar" class="w-12 h-12 rounded-full object-cover shrink-0 shadow-sm" />
                <div class="min-w-0 space-y-1">
                  <div class="flex flex-wrap items-center gap-2">
                    <span class="font-headline-sm text-headline-sm text-on-surface font-bold">{{ candidate.name }}</span>
                    <span class="font-label-sm text-label-sm px-2 py-0.5 rounded bg-surface-container-high text-primary font-semibold">{{ candidate.school }} · {{ candidate.major }}</span>
                    <span class="font-label-sm text-label-sm text-on-surface-variant">{{ candidate.year }}</span>
                  </div>
                  <div class="flex flex-wrap items-center gap-2 font-body-md text-body-md text-on-surface-variant">
                    <span class="text-on-surface font-medium">投递：{{ candidate.position }}</span>
                    <span class="inline-block w-1 h-1 rounded-full bg-outline-variant"></span>
                    <span class="font-body-sm text-body-sm">{{ candidate.time }}</span>
                  </div>
                  <div class="flex items-center gap-2 pt-0.5">
                    <div :class="['flex items-center gap-1 px-2 py-0.5 rounded-full font-label-sm text-label-sm font-bold', candidate.matchClass]">
                      <span class="material-symbols-outlined text-[14px]">auto_awesome</span>
                      {{ candidate.match }}
                    </div>
                    <span v-for="tag in candidate.tags" :key="tag"
                      :class="['px-2 py-0.5 rounded-full font-label-sm text-label-sm', tag.class]">
                      {{ tag.text }}
                    </span>
                  </div>
                </div>
              </div>
              <div class="flex flex-row md:flex-col lg:flex-row items-center justify-between md:justify-end gap-2 shrink-0 pt-2 md:pt-0">
                <span :class="['inline-flex px-2.5 py-1 rounded-full font-label-sm text-label-sm font-semibold', candidate.statusClass]">
                  {{ candidate.status }}
                </span>
                <div class="flex items-center gap-1">
                  <button v-for="action in candidate.actions" :key="action.label"
                    :class="['px-3 py-1.5 rounded-lg font-label-md text-label-md font-medium transition-colors', action.class]">
                    {{ action.label }}
                  </button>
                  <button v-if="candidate.showClose" class="p-1.5 rounded-lg hover:bg-surface-container text-on-surface-variant transition-colors" title="标记不合适">
                    <span class="material-symbols-outlined text-[18px]">close</span>
                  </button>
                  <button v-if="candidate.showSend" class="p-1.5 rounded-lg hover:bg-surface-container text-primary transition-colors" title="发送签约提醒">
                    <span class="material-symbols-outlined text-[18px]">send</span>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Pagination -->
          <div class="flex items-center justify-between bg-surface-container-lowest rounded-xl px-4 py-3 shadow-sm">
            <span class="font-body-sm text-body-sm text-on-surface-variant">显示 1 至 5 项，共 32 份应聘简历</span>
            <div class="flex items-center gap-1">
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface-variant hover:bg-surface-container font-label-sm text-label-sm">上一页</button>
              <button class="px-2.5 py-1 rounded bg-primary text-on-primary font-label-sm text-label-sm font-bold">1</button>
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface hover:bg-surface-container font-label-sm text-label-sm">2</button>
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface hover:bg-surface-container font-label-sm text-label-sm">3</button>
              <button class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface-variant hover:bg-surface-container font-label-sm text-label-sm">下一页</button>
            </div>
          </div>
        </div>

        <!-- Right: Funnel (4 cols) -->
        <div class="lg:col-span-4 flex flex-col gap-4">
          <div class="bg-surface-container-lowest rounded-xl p-4 shadow-sm">
            <div class="flex items-center justify-between mb-3">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-[20px]">filter_alt</span>
                <h2 class="font-headline-sm text-headline-sm text-on-surface font-bold">招聘漏斗转化数据</h2>
              </div>
              <span class="font-label-sm text-label-sm text-on-surface-variant">2025届春招季</span>
            </div>
            <div class="space-y-3 pt-2">
              <div>
                <div class="flex justify-between font-label-md text-label-md mb-1">
                  <span class="text-on-surface">投递简历</span>
                  <span class="font-bold text-on-surface">1,428 份 (100%)</span>
                </div>
                <div class="w-full h-2.5 bg-surface-container rounded-full overflow-hidden">
                  <div class="h-full bg-primary rounded-full w-full"></div>
                </div>
              </div>
              <div>
                <div class="flex justify-between font-label-md text-label-md mb-1">
                  <span class="text-on-surface">初筛通过</span>
                  <span class="font-bold text-secondary">482 份 (33.7%)</span>
                </div>
                <div class="w-full h-2.5 bg-surface-container rounded-full overflow-hidden">
                  <div class="h-full bg-secondary rounded-full" style="width: 33.7%"></div>
                </div>
              </div>
              <div>
                <div class="flex justify-between font-label-md text-label-md mb-1">
                  <span class="text-on-surface">面试推进中</span>
                  <span class="font-bold text-secondary-container">126 份 (8.8%)</span>
                </div>
                <div class="w-full h-2.5 bg-surface-container rounded-full overflow-hidden">
                  <div class="h-full bg-secondary-container rounded-full" style="width: 8.8%"></div>
                </div>
              </div>
              <div>
                <div class="flex justify-between font-label-md text-label-md mb-1">
                  <span class="text-on-surface">已发 / 待签 Offer</span>
                  <span class="font-bold text-primary">28 份 (2.0%)</span>
                </div>
                <div class="w-full h-2.5 bg-surface-container rounded-full overflow-hidden">
                  <div class="h-full bg-primary-container rounded-full" style="width: 2%"></div>
                </div>
              </div>
            </div>
            <div class="mt-4 p-2 bg-surface-container-low rounded-lg flex items-center justify-between text-on-surface-variant font-body-sm text-body-sm">
              <span>当前Offer承接率: <strong class="text-primary font-bold">89.3%</strong></span>
              <a class="text-primary hover:underline font-label-sm text-label-sm font-semibold" href="#">分析报表 →</a>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeTab = ref('all')

const filterTabs = [
  { key: 'all', label: '全部投递', count: 32 },
  { key: 'pending', label: '待初筛', count: 14 },
  { key: 'interview', label: '待邀约笔试/面试', count: 9 },
  { key: 'offer', label: '已发Offer', count: 5 },
  { key: 'reject', label: '不合适', count: 4 },
]

const candidates = [
  {
    id: 1,
    name: '周明翰',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCYJdr9Df-Pe_S7IhjSZ_hi7VdGuywsh2p_Nu4BVJPzceDxmJ10rVG3ajTNFwf6vDRVWRLueEul3U4QanVCQWXw7D63pjt-SxnmwAt3Pn3QUsD57RQURCC8dZAMyvXNsUjmOwbT4Ez983PSy9FyMENtPRfg5e_37SpY5fGo4nYhq5vWtECJnIV7p28qqeOC__LmDlTv6EuHNofgM2QGii2zKc9hEx6kgi-zZ84OlwkOiTgxxhQ9SFvC4A',
    school: '清华大学',
    major: '软件工程',
    year: '2025届硕士',
    position: '算法工程师 (校招)',
    time: '今天 11:24 投递',
    match: '96% AI契合 · 算法卓越',
    matchClass: 'bg-secondary-fixed text-on-secondary-fixed',
    tags: [{ text: 'ACM区域金牌', class: 'bg-primary-fixed text-on-primary-fixed' }],
    status: '待初筛',
    statusClass: 'bg-surface-container-high text-primary',
    actions: [
      { label: '预览简历', class: 'bg-surface-container hover:bg-surface-container-high text-on-surface' },
      { label: '发起面试', class: 'bg-primary text-on-primary hover:bg-primary-container font-semibold shadow-sm' },
    ],
    showClose: true,
  },
  {
    id: 2,
    name: '李思琪',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBdVIQHqSEJeHFiqOlp9_6R2Fwv_VoJkV4bORuMSdZt8N3y78Bwwd4m6jfIKxukP850E8z69zWFdZHhp5c9p47iY45IRlQDm682z1WJtz9bbNF6Wi8EOhX0dzQcdt-lrTcUmxS5VyTKyAHLrXqFw3fcRVnEutRZpvw5goLwDuXp0PCs_Oy6WWT3SUSfLTV-UtU7xEaRr_4DBqNgfAXarTgTSHN4vsFokVqV31uVzC1PTS-N53N4PFuC5A',
    school: '北京航空航天大学',
    major: '计算机科学',
    year: '2025届本科',
    position: '前端开发实习生 (可转正)',
    time: '今天 09:40 投递',
    match: '92% 高度匹配',
    matchClass: 'bg-secondary-fixed text-on-secondary-fixed',
    tags: [{ text: '大厂实习经验 x 1', class: 'bg-surface-container text-on-surface-variant' }],
    status: '48h紧急待办',
    statusClass: 'bg-tertiary-fixed text-tertiary',
    actions: [
      { label: '预览简历', class: 'bg-surface-container hover:bg-surface-container-high text-on-surface' },
      { label: '发起笔试', class: 'bg-primary text-on-primary hover:bg-primary-container font-semibold shadow-sm' },
    ],
    showClose: true,
  },
  {
    id: 3,
    name: '赵程安',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDnb75pVhxpvkRA_ZdUtZZVjuGjT3e8goyAEinTon_RWezr0xgE-R4PySF_7AZpGnuIJBYJzi815ywgcVNar-P9P0TlGSq3rEVi6DKn_bLTvmdRXRiCc83cv4TbbvMP9gtw9nDmgTU25RgrbjUzPjBhNUdhG95xKWUpreiKZoZRFgYINSOEWqSV41twBZksK0tkt4DFSUjK3ks1E51ozhdMd5nqvtSwzdf1ES72T3j9cUgBW_KpR8Knbw',
    school: '上海交通大学',
    major: '自动化系',
    year: '2025届硕士',
    position: '大模型应用架构师 (青年先锋计划)',
    time: '昨日 17:15 投递',
    match: '95% 卓越契合',
    matchClass: 'bg-secondary-fixed text-on-secondary-fixed',
    tags: [{ text: 'NeurIPS 发表第一作者', class: 'bg-surface-container text-on-surface-variant' }],
    status: '二面进行中',
    statusClass: 'bg-secondary-fixed-dim text-on-secondary-fixed',
    actions: [
      { label: '评估记录', class: 'bg-surface-container hover:bg-surface-container-high text-on-surface' },
      { label: '录用意向', class: 'bg-surface-container-high text-primary hover:bg-primary-fixed font-semibold' },
    ],
    showClose: false,
  },
  {
    id: 4,
    name: '何雨萱',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAbTEVeefCyq6BI-Z2AQhXtNExbFr6Ifydv7vcKjTfeyBL1uYQ0LAM4IoqKk1qQytmZwVhoT8S9Zo1f0tcuYmVFfUVf4yh4y_XtfGM7ljZUwcF-7Jjl5tZ083goFA_Q5WuR6OIxu3ljdb8qvgsCcWjuX5b5rUj6hDQ7LnsSvTe5mvIL9JezS6ZkdTNMTR44JX3ge6oL09-SniRjzmwAlDhv-P2SyuXcWaycsqq4DA1EIHZTkzHYu2erbA',
    school: '浙江大学',
    major: '计算机科学与技术',
    year: '2025届本科',
    position: '后端开发工程师 (校招)',
    time: '昨日 14:02 投递',
    match: '88% 良好匹配',
    matchClass: 'bg-surface-container-highest text-on-surface',
    tags: [{ text: '校级一等奖学金', class: 'bg-surface-container text-on-surface-variant' }],
    status: '待初筛',
    statusClass: 'bg-surface-container text-on-surface-variant',
    actions: [
      { label: '预览简历', class: 'bg-surface-container hover:bg-surface-container-high text-on-surface' },
      { label: '发起面试', class: 'bg-primary text-on-primary hover:bg-primary-container font-semibold shadow-sm' },
    ],
    showClose: true,
  },
  {
    id: 5,
    name: '孙楚越',
    avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCBl3mmGdllfN5ctCPejEekF-lEqxAGZbpkW9eG_xDjXAv_afyILp86rj5m-4jaGJYQ7-unVtbPC6y8FMog3KMq_fYyNoVGkknvhWZk7q3z7fNWfNFIr2DjxsokatZ5r_KcLN_6Y7eXvodz2QtH9kZAZUvOvNEjrJ8_X2cwglKusO4YnFprKLUlGTWExJym0RuN18r6Nen2a-rP04BHpP1aSDHDbtsOcX6AdIuzCUkFxMD0xKtRk31a6A',
    school: '华中科技大学',
    major: '人工智能学院',
    year: '2025届硕士',
    position: '计算机视觉研究员 (校招)',
    time: '前天 18:30 投递',
    match: '94% 卓越匹配',
    matchClass: 'bg-secondary-fixed text-on-secondary-fixed',
    tags: [{ text: 'CVPR 录用论文', class: 'bg-surface-container-high text-primary' }],
    status: '已发Offer待签约',
    statusClass: 'bg-primary-container text-on-primary',
    actions: [
      { label: '查看三方协议', class: 'bg-surface-container hover:bg-surface-container-high text-on-surface' },
    ],
    showClose: false,
    showSend: true,
  },
]
</script>
