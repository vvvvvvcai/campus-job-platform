<template>
  <div class="min-h-screen bg-surface">
    <!-- Main Content -->
    <main>
      <!-- Top Ambient Glow -->
      <div class="relative w-full overflow-hidden bg-surface-container-low pb-space-2xl pt-space-lg">
        <div class="absolute -right-24 -top-24 h-96 w-96 rounded-full bg-primary/5 blur-3xl pointer-events-none"></div>
        <div class="absolute -left-20 top-1/2 h-72 w-72 rounded-full bg-secondary-container/10 blur-3xl pointer-events-none"></div>
        <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 relative z-10 space-y-space-lg">
          <!-- Breadcrumb -->
          <nav aria-label="Breadcrumb" class="flex items-center gap-space-xs font-body-sm text-body-sm text-on-surface-variant">
            <router-link class="hover:text-primary transition-colors flex items-center gap-1" to="/home">
              <span class="material-symbols-outlined text-[16px]">home</span>
              首页
            </router-link>
            <span class="material-symbols-outlined text-[14px] text-outline">chevron_right</span>
            <span class="text-primary font-semibold flex items-center gap-1">
              <span class="material-symbols-outlined text-[16px] text-secondary">auto_awesome</span>
              智能AI人岗撮合
            </span>
          </nav>

          <!-- AI Recommendation Hero Card -->
          <div class="relative overflow-hidden rounded-xl bg-surface-container-lowest shadow-md p-space-lg md:p-space-xl">
            <div class="flex flex-col lg:flex-row lg:items-center justify-between gap-space-xl">
              <div class="space-y-space-sm max-w-3xl">
                <div class="inline-flex items-center gap-space-xs px-space-sm py-1 rounded-full bg-surface-container text-primary font-label-sm text-label-sm font-semibold">
                  <span class="material-symbols-outlined text-[16px] animate-pulse">psychology</span>
                  <span>知识图谱双向撮合引擎 v4.2 已生效</span>
                  <span class="w-1.5 h-1.5 rounded-full bg-secondary"></span>
                  <span class="text-on-surface-variant font-normal">每日 10:00 动态重排</span>
                </div>
                <h1 class="font-headline-lg text-headline-lg md:font-headline-xl md:text-headline-xl text-on-surface font-bold tracking-tight">
                  根据您的意向与学术背景，实时撮合高匹配度职位
                </h1>
                <div class="space-y-1">
                  <p class="font-body-md text-body-md text-on-surface flex items-center gap-2 flex-wrap">
                    <span class="px-2 py-0.5 rounded bg-surface-container font-medium text-primary">大模型算法 / 全栈开发</span>
                    <span class="px-2 py-0.5 rounded bg-surface-container font-medium text-secondary">北京 · 上海 · 深圳</span>
                    <span class="px-2 py-0.5 rounded bg-surface-container font-medium text-tertiary">25k - 40k · 校招/转正</span>
                  </p>
                  <p class="font-body-sm text-body-sm text-on-surface-variant pt-1 leading-relaxed">
                    基于双向知识图谱匹配，系统已综合分析计算机系课程学分、GitHub开源项目深度与大厂历届录用画像。匹配度高达 90%+ 岗位已自动为您开通<span class="text-primary font-semibold">企业HR免筛绿色通道</span>。
                  </p>
                </div>
                <!-- Action Buttons -->
                <div class="flex items-center gap-space-sm pt-space-xs flex-wrap">
                  <button
                    class="inline-flex items-center gap-2 px-space-md h-10 rounded-lg bg-primary text-on-primary font-label-lg text-label-lg shadow-sm hover:bg-primary-container transition-all active:scale-95"
                    :class="{ 'opacity-80': isRefreshing }"
                    @click="handleRefresh"
                  >
                    <span class="material-symbols-outlined text-[18px] transition-transform duration-700" :class="{ 'rotate-180': isRefreshing }">sync</span>
                    <span>换一批 / 刷新推荐</span>
                  </button>
                  <button class="inline-flex items-center gap-1.5 px-space-md h-10 rounded-lg bg-surface-container text-on-surface font-label-lg text-label-lg hover:bg-surface-container-high transition-colors">
                    <span class="material-symbols-outlined text-[18px] text-on-surface-variant">tune</span>
                    <span>调整求职偏好</span>
                  </button>
                  <button class="inline-flex items-center gap-1 px-space-sm h-10 rounded-lg text-secondary font-label-md text-label-md hover:bg-secondary-fixed/30 transition-colors">
                    <span class="material-symbols-outlined text-[18px]">help_outline</span>
                    <span>匹配规则说明</span>
                  </button>
                </div>
              </div>
              <!-- Micro Analytics Stats Bento in Hero -->
              <div class="grid grid-cols-3 lg:grid-cols-1 gap-space-sm shrink-0 lg:w-64 bg-surface-container-low p-space-md rounded-xl">
                <div class="flex flex-col">
                  <span class="font-label-sm text-label-sm text-on-surface-variant">当前专属推荐池</span>
                  <div class="flex items-baseline gap-1 mt-0.5">
                    <span class="font-headline-lg text-headline-lg font-bold text-primary">24</span>
                    <span class="font-label-sm text-label-sm text-on-surface-variant">个在招岗位</span>
                  </div>
                </div>
                <div class="flex flex-col">
                  <span class="font-label-sm text-label-sm text-on-surface-variant">超 95% 卓越契合</span>
                  <div class="flex items-baseline gap-1 mt-0.5">
                    <span class="font-headline-lg text-headline-lg font-bold text-secondary">8</span>
                    <span class="font-label-sm text-label-sm text-on-surface-variant">个享直推</span>
                  </div>
                </div>
                <div class="flex flex-col">
                  <span class="font-label-sm text-label-sm text-on-surface-variant">名企HR已探视画像</span>
                  <div class="flex items-baseline gap-1 mt-0.5">
                    <span class="font-headline-lg text-headline-lg font-bold text-on-surface">16</span>
                    <span class="font-label-sm text-label-sm text-on-surface-variant">次本周访问</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Content Section -->
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-space-xl space-y-space-xl">
        <!-- Filter Bar, View Switcher & Sorting -->
        <div class="flex flex-col md:flex-row md:items-center justify-between gap-space-md pb-space-xs">
          <!-- Tag Pills Filter -->
          <div class="flex items-center gap-space-xs overflow-x-auto pb-1 md:pb-0 scrollbar-none">
            <button
              v-for="filter in filters"
              :key="filter.key"
              class="filter-pill shrink-0 px-space-md h-9 rounded-full font-label-md text-label-md font-medium shadow-sm transition-colors"
              :class="activeFilter === filter.key
                ? 'bg-primary text-on-primary'
                : 'bg-surface-container-lowest text-on-surface-variant hover:bg-surface-container hover:text-on-surface'"
              @click="activeFilter = filter.key"
            >
              {{ filter.label }}
            </button>
          </div>
          <!-- Controls: Sort & Grid/List Toggle -->
          <div class="flex items-center gap-space-sm self-end md:self-auto shrink-0">
            <div class="flex items-center gap-2 bg-surface-container-lowest px-3 py-1.5 rounded-lg shadow-sm">
              <span class="material-symbols-outlined text-[18px] text-outline">sort</span>
              <select
                v-model="sortBy"
                class="bg-transparent font-label-md text-label-md text-on-surface font-medium focus:outline-none cursor-pointer"
              >
                <option value="match">契合度最高 (默认)</option>
                <option value="salary">薪资水平最高</option>
                <option value="recent">最新发布优先</option>
              </select>
            </div>
            <div class="inline-flex bg-surface-container p-1 rounded-lg">
              <button
                class="p-1.5 rounded transition-all"
                :class="viewMode === 'grid' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface'"
                title="网格视图"
                @click="viewMode = 'grid'"
              >
                <span class="material-symbols-outlined text-[18px] leading-none">grid_view</span>
              </button>
              <button
                class="p-1.5 rounded transition-all"
                :class="viewMode === 'list' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface'"
                title="紧凑列表视图"
                @click="viewMode = 'list'"
              >
                <span class="material-symbols-outlined text-[18px] leading-none">view_list</span>
              </button>
            </div>
          </div>
        </div>

        <!-- Recommended Job Grid -->
        <div
          class="grid gap-space-lg transition-all duration-300"
          :class="viewMode === 'grid' ? 'grid-cols-1 md:grid-cols-2 lg:grid-cols-3' : 'grid-cols-1 gap-space-md'"
        >
          <article
            v-for="job in jobs"
            :key="job.id"
            class="job-card group relative flex flex-col justify-between bg-surface-container-lowest rounded-xl p-space-lg shadow-sm hover:shadow-md transition-all duration-200 hover:-translate-y-1"
          >
            <div class="space-y-space-md">
              <!-- Top Row: Match Gauge Badge + Action Bookmark -->
              <div class="flex items-center justify-between">
                <div
                  class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full font-label-md text-label-md font-bold"
                  :class="job.matchBadgeClass"
                >
                  <span
                    class="material-symbols-outlined text-[16px]"
                    :class="job.matchIconClass"
                    :style="job.matchFill ? { 'font-variation-settings': 'FILL 1' } : {}"
                  >{{ job.matchIcon }}</span>
                  <span>{{ job.matchLabel }}</span>
                </div>
                <button
                  aria-label="收藏职位"
                  class="favorite-btn p-1 transition-colors"
                  :class="job.isFavorite ? 'text-error' : 'text-outline hover:text-error'"
                  @click="toggleFavorite(job)"
                >
                  <span
                    class="material-symbols-outlined text-[22px]"
                    :style="job.isFavorite ? { 'font-variation-settings': 'FILL 1' } : { 'font-variation-settings': 'FILL 0' }"
                  >favorite</span>
                </button>
              </div>
              <!-- Job Title & Salary -->
              <div class="space-y-1">
                <router-link :to="`/jobs/${job.id}`">
                  <h2 class="font-headline-sm text-headline-sm font-bold text-on-surface group-hover:text-primary transition-colors cursor-pointer">
                    {{ job.title }}
                  </h2>
                </router-link>
                <div class="flex items-baseline gap-2">
                  <span class="font-headline-md text-headline-md font-bold text-primary">{{ job.salary }}</span>
                  <span class="font-label-md text-label-md" :class="job.salaryExtraClass">{{ job.salaryExtra }}</span>
                </div>
              </div>
              <!-- Company Meta -->
              <div class="flex items-center gap-3">
                <img class="w-11 h-11 rounded-lg object-cover bg-surface-container" :src="job.companyLogo" :alt="job.companyName" />
                <div class="flex flex-col min-w-0">
                  <span class="font-label-lg text-label-lg font-semibold text-on-surface truncate">{{ job.companyName }}</span>
                  <span class="font-body-sm text-body-sm text-on-surface-variant truncate">{{ job.companyDesc }}</span>
                </div>
              </div>
              <!-- Location & Tags -->
              <div class="flex items-center gap-3 text-on-surface-variant font-body-sm text-body-sm">
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[16px] text-outline">location_on</span>
                  {{ job.location }}
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[16px] text-outline">{{ job.tagIcon }}</span>
                  {{ job.tag }}
                </span>
              </div>
              <!-- Reason Chips -->
              <div class="flex flex-wrap gap-1.5 pt-1">
                <span
                  v-for="(chip, ci) in job.chips"
                  :key="ci"
                  class="px-2 py-0.5 rounded-full font-label-sm text-label-sm font-medium"
                  :class="chip.class"
                >{{ chip.text }}</span>
              </div>
            </div>
            <!-- Footer Card Operations -->
            <div class="pt-space-md mt-space-md border-t-0 flex items-center justify-between bg-surface-container-low -mx-space-lg -mb-space-lg px-space-lg py-3 rounded-b-xl">
              <a class="inline-flex items-center gap-1 font-label-md text-label-md text-secondary hover:text-primary transition-colors" href="#">
                <span class="material-symbols-outlined text-[16px]">chat</span>
                <span>{{ job.chatLabel }}</span>
              </a>
              <button
                class="apply-btn px-4 py-2 rounded-lg font-label-md text-label-md font-semibold shadow-sm transition-all active:scale-95"
                :class="job.applied
                  ? 'bg-surface-container text-on-surface-variant cursor-default'
                  : 'bg-primary text-on-primary hover:bg-primary-container'"
                :disabled="job.applied"
                @click="handleApply(job)"
              >
                {{ job.applied ? '已投递' : '立即投递简历' }}
              </button>
            </div>
          </article>
        </div>

        <!-- Optimization & Fallback Guidance Section (2-Column Bento) -->
        <div class="grid grid-cols-1 lg:grid-cols-12 gap-space-lg pt-space-md">
          <!-- Resume Polish & Accuracy Elevation Card (7 cols) -->
          <div class="lg:col-span-7 bg-surface-container-lowest rounded-xl p-space-lg shadow-sm flex flex-col md:flex-row items-start md:items-center justify-between gap-space-lg">
            <div class="space-y-space-xs max-w-md">
              <div class="flex items-center gap-2">
                <span class="p-1 rounded bg-secondary-fixed text-on-secondary-fixed">
                  <span class="material-symbols-outlined text-[18px]">upgrade</span>
                </span>
                <h2 class="font-headline-sm text-headline-sm font-bold text-on-surface">
                  觉得推荐还不够准？完善画像解锁更高精度
                </h2>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed">
                林晨同学，您当前的在线简历完整度为 <strong class="text-primary font-semibold">88%</strong>。补充上传<span class="text-on-surface font-medium">「竞赛获奖证明」</span>与<span class="text-on-surface font-medium">「GitHub开源项目」</span>后，推荐算法的专业技能匹配度将可再提升 <strong class="text-secondary font-semibold">15%</strong>。
              </p>
              <div class="w-full bg-surface-container-low h-2 rounded-full overflow-hidden mt-2">
                <div class="bg-primary h-full rounded-full transition-all duration-1000" style="width: 88%"></div>
              </div>
            </div>
            <div class="flex flex-col gap-2 shrink-0 w-full md:w-auto">
              <router-link
                to="/resume/edit"
                class="px-space-md h-10 rounded-lg bg-primary text-on-primary font-label-md text-label-md font-semibold shadow-sm hover:bg-primary-container transition-colors text-center inline-flex items-center justify-center"
              >
                立即润色简历
              </router-link>
              <router-link
                to="/profile"
                class="px-space-md h-10 rounded-lg bg-surface-container text-on-surface font-label-md text-label-md hover:bg-surface-container-high transition-colors text-center inline-flex items-center justify-center"
              >
                更新期望意向
              </router-link>
            </div>
          </div>
          <!-- Graceful Empty/Broaden Parameters Notice (5 cols) -->
          <div class="lg:col-span-5 bg-surface-container-low rounded-xl p-space-lg flex flex-col justify-between">
            <div class="flex items-start gap-3">
              <div class="w-10 h-10 rounded-lg bg-surface-container-lowest flex items-center justify-center shrink-0 shadow-sm text-secondary">
                <span class="material-symbols-outlined text-[24px]">filter_alt_off</span>
              </div>
              <div class="space-y-1">
                <h3 class="font-headline-sm text-headline-sm font-bold text-on-surface">想探索更多优质机遇？</h3>
                <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed">
                  若在特定严苛筛选下无匹配职位，您可以适度放宽薪资带宽或增加如「杭州、苏州、成都」等新一线城市，系统将为您扩增 40+ 精选校招岗位。
                </p>
              </div>
            </div>
            <div class="pt-space-sm mt-space-sm flex justify-end">
              <button class="inline-flex items-center gap-1 font-label-md text-label-md text-primary font-semibold hover:underline">
                <span>一键扩展跨城推荐</span>
                <span class="material-symbols-outlined text-[16px]">arrow_forward</span>
              </button>
            </div>
          </div>
        </div>

        <!-- Pagination & Feedback Section -->
        <div class="pt-space-md pb-space-2xl flex flex-col md:flex-row items-center justify-between gap-space-lg">
          <!-- Feedback Widget -->
          <div class="flex items-center gap-3 bg-surface-container-lowest px-space-md py-2.5 rounded-xl shadow-sm">
            <span class="font-body-sm text-body-sm text-on-surface-variant flex items-center gap-1">
              <span class="material-symbols-outlined text-[18px] text-outline">rate_review</span>
              本次推荐满意吗？
            </span>
            <div class="flex items-center gap-1">
              <button
                v-for="fb in feedbackOptions"
                :key="fb.key"
                class="feedback-btn px-2.5 py-1 rounded text-label-sm font-label-sm transition-colors"
                :class="activeFeedback === fb.key
                  ? 'bg-primary text-on-primary'
                  : 'text-on-surface-variant hover:bg-surface-container ' + fb.hoverClass"
                @click="handleFeedback(fb.key)"
              >
                {{ fb.label }}
              </button>
            </div>
          </div>
          <!-- Pagination Controls -->
          <nav aria-label="Page navigation" class="flex items-center gap-1.5">
            <button
              class="w-9 h-9 rounded-lg bg-surface-container-lowest text-on-surface-variant hover:bg-surface-container hover:text-on-surface flex items-center justify-center shadow-sm transition-colors"
              :disabled="currentPage === 1"
              @click="currentPage = Math.max(1, currentPage - 1)"
            >
              <span class="material-symbols-outlined text-[18px]">chevron_left</span>
            </button>
            <button
              v-for="page in visiblePages"
              :key="page"
              class="w-9 h-9 rounded-lg font-label-md text-label-md font-medium flex items-center justify-center shadow-sm transition-colors"
              :class="page === currentPage
                ? 'bg-primary text-on-primary font-semibold'
                : 'bg-surface-container-lowest text-on-surface hover:bg-surface-container'"
              @click="currentPage = page"
            >
              {{ page }}
            </button>
            <span v-if="totalPages > 5" class="px-1 text-outline font-label-md">...</span>
            <button
              v-if="totalPages > 5"
              class="w-9 h-9 rounded-lg bg-surface-container-lowest text-on-surface hover:bg-surface-container font-label-md text-label-md font-medium flex items-center justify-center shadow-sm transition-colors"
              @click="currentPage = totalPages"
            >
              {{ totalPages }}
            </button>
            <button
              class="w-9 h-9 rounded-lg bg-surface-container-lowest text-on-surface-variant hover:bg-surface-container hover:text-on-surface flex items-center justify-center shadow-sm transition-colors"
              :disabled="currentPage === totalPages"
              @click="currentPage = Math.min(totalPages, currentPage + 1)"
            >
              <span class="material-symbols-outlined text-[18px]">chevron_right</span>
            </button>
          </nav>
        </div>
      </div>
    </main>

    <!-- Toast Notification -->
    <Transition
      enter-active-class="transition-all duration-300 ease-out"
      enter-from-class="translate-y-20 opacity-0"
      enter-to-class="translate-y-0 opacity-100"
      leave-active-class="transition-all duration-300 ease-in"
      leave-from-class="translate-y-0 opacity-100"
      leave-to-class="translate-y-20 opacity-0"
    >
      <div
        v-if="toast.visible"
        class="fixed bottom-6 right-6 z-50 flex items-center gap-3 bg-inverse-surface text-inverse-on-surface px-space-md py-3 rounded-xl shadow-lg"
      >
        <span class="material-symbols-outlined text-secondary-fixed-dim text-[20px]">{{ toast.icon }}</span>
        <span class="font-body-md text-body-md">{{ toast.message }}</span>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const isRefreshing = ref(false)
const activeFilter = ref('all')
const sortBy = ref('match')
const viewMode = ref('grid')
const currentPage = ref(1)
const totalPages = ref(8)
const activeFeedback = ref(null)

const toast = reactive({ visible: false, message: '', icon: 'check_circle' })
let toastTimer = null

const filters = [
  { key: 'all', label: '全部智能推荐 (24)' },
  { key: 'ai', label: '算法与AI前沿 (8)' },
  { key: 'fullstack', label: '核心研发全栈 (6)' },
  { key: 'top50', label: '985校友名企直招 (5)' },
  { key: 'convert', label: '留用转正率>90% (5)' }
]

const feedbackOptions = [
  { key: 'good', label: '很精准 👍', hoverClass: 'hover:text-primary' },
  { key: 'ok', label: '一般 👌', hoverClass: 'hover:text-primary' },
  { key: 'bad', label: '偏差较大 👎', hoverClass: 'hover:text-error' }
]

const jobs = reactive([
  {
    id: 1,
    matchLabel: 'AI 匹配度 98% 卓越契合',
    matchBadgeClass: 'bg-primary-fixed text-on-primary-fixed',
    matchIconClass: 'text-primary',
    matchIcon: 'stars',
    matchFill: true,
    title: '大模型预训练与推理算法工程师',
    salary: '28k - 45k',
    salaryExtra: '· 16薪',
    salaryExtraClass: 'text-on-surface-variant',
    companyLogo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuA2xOrvuivHzPnrANEAJ1B67gINX188tboxrUvyozxxGmB2i41C6l6Tj_2mU4YlRf7Jkk-mJX79hevnrS5SEAN5wYLfZLb-ZBs5UEIz_pgPm-mFImtg45oOfD-XRmm3iHvQem_vHd53_GUT6nzYlwtj5LIRYsLRL-ncK4SaH93H9BXSaoTIdfAv2ja5i8t4U5kkvfOiF3cP-jwcwTX40fsxz0mJuii5nalljtJ6Do4-He5a6peQ8FvOSw',
    companyName: '智维未来人工智能研究院',
    companyDesc: '国家高新技术企业 · C轮独角兽',
    location: '北京 · 海淀中关村',
    tagIcon: 'school',
    tag: '硕士及以上 · 2025届',
    chips: [
      { text: '计算机 / 软件对口', class: 'bg-surface-container text-primary' },
      { text: '技能命中: PyTorch, DeepSpeed, LLaMA', class: 'bg-secondary-fixed/50 text-on-secondary-fixed' },
      { text: '985校友都在投', class: 'bg-surface-container-high text-on-surface-variant' }
    ],
    chatLabel: '技术导师在线聊',
    isFavorite: false,
    applied: false
  },
  {
    id: 2,
    matchLabel: 'AI 匹配度 96% 极高匹配',
    matchBadgeClass: 'bg-secondary-fixed text-on-secondary-fixed',
    matchIconClass: 'text-secondary',
    matchIcon: 'verified',
    matchFill: true,
    title: '具身智能机器人控制算法研究员',
    salary: '30k - 50k',
    salaryExtra: '· 15薪',
    salaryExtraClass: 'text-on-surface-variant',
    companyLogo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAtJUfcl7eERa2r0ySChSt-SKqitpPbrwKV5DB-dEa7LTs1JtxwP5V77iuhKJhfLeajp-cP8Q3cctGtawEGB99k3ylCmIsBsZkT2bSQsTi70OCO5aOGZ0CDznnxkXly5m-nGxsylgKZ3r1ze21JxyZQlC1UZo8Wck3F6IDGizxxWqLgAN-C1gplzqupmYoW9Sw6yGAMbv_vM5YcJJCPmARzkW8Nh81ZB_wYj0li0mDe_WrotJ5yGRk_4w',
    companyName: '智元创新机器人科技',
    companyDesc: '人工智能前沿独角兽企业',
    location: '上海 · 浦东张江',
    tagIcon: 'apartment',
    tag: '校招专属 · 免租公寓',
    chips: [
      { text: '学术成果强相关', class: 'bg-surface-container text-primary' },
      { text: '强化学习 / 运控SLAM', class: 'bg-secondary-fixed/50 text-on-secondary-fixed' },
      { text: '留用转正率 95%', class: 'bg-surface-container-high text-on-surface-variant' }
    ],
    chatLabel: 'HR总监在线',
    isFavorite: false,
    applied: false
  },
  {
    id: 3,
    matchLabel: 'AI 匹配度 94% 深度推荐',
    matchBadgeClass: 'bg-surface-container text-primary',
    matchIconClass: 'text-primary',
    matchIcon: 'task_alt',
    matchFill: false,
    title: '全栈平台架构工程师 (管培生计划)',
    salary: '22k - 35k',
    salaryExtra: '· 15薪',
    salaryExtraClass: 'text-on-surface-variant',
    companyLogo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAUg2uBsG1-TVebRDAZFhQFBZYJCR9tXLRuQHdTV3VNjvPV00mhePKc4-6tWLBPRHsKpwcdxdU2N5C8opkmlBmQNgh2Us8nO0yPOLvBLVnXSQ2zn2y6J-47QECR8SVcBLtuf0rynJl4Z-yjl5HukSbai2w-gEuqrzxLyXEd69UwH6vtPgHsppEr0OEaH-cj554YfhP6_wYnSmL0jko2CJzSARCdVvUBb39upbICxUArpILxvbUAkQ7daw',
    companyName: '字节跳动互联网研发中心',
    companyDesc: '头部互联网大厂 · 核心电商技术',
    location: '北京 · 朝阳区',
    tagIcon: 'restaurant',
    tag: '免费三餐 · 房补 1500/月',
    chips: [
      { text: '免笔试直通一面', class: 'bg-surface-container text-primary' },
      { text: '技能命中: Go, React, K8s', class: 'bg-secondary-fixed/50 text-on-secondary-fixed' },
      { text: '导师1v1带教', class: 'bg-surface-container-high text-on-surface-variant' }
    ],
    chatLabel: '校友HR热聊中',
    isFavorite: false,
    applied: false
  },
  {
    id: 4,
    matchLabel: 'AI 匹配度 93% 优质外企',
    matchBadgeClass: 'bg-surface-container text-primary',
    matchIconClass: 'text-primary',
    matchIcon: 'task_alt',
    matchFill: false,
    title: '云原生与高并发分布式后端研发',
    salary: '25k - 38k',
    salaryExtra: '· 14薪 + 期权',
    salaryExtraClass: 'text-on-surface-variant',
    companyLogo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAxe9N2Y8Ymv-AdF0utB6a42eEVp-oND5cW3qinIxzWCz0rGtgxLUYp0WcehiIxfpy2SeQB4sRJLn_88PF-oJUIPNt_rQ18iCiptI2r27aDjAOLywzkepkKvtLyTvpiiktMCetffRfGFeAySTNcmWw8OO5USqNokEDFh_sU7p4C_njpnsu0xLFF9YRWzwl6FLvhZZEJZ2MFTs7yufzNSrCQQ0VspgpGXzksJ7XghFLNMu6TCmF5sHiYdw',
    companyName: '微软中国 · 亚太研发集团',
    companyDesc: '全球知名外企 · 春招补录专列',
    location: '上海 · 徐汇区',
    tagIcon: 'schedule',
    tag: '弹性不打卡 · 15天年假',
    chips: [
      { text: '外企WLB文化', class: 'bg-surface-container text-primary' },
      { text: '英语技术面试通道', class: 'bg-secondary-fixed/50 text-on-secondary-fixed' },
      { text: '混合办公(每周2天远程)', class: 'bg-surface-container-high text-on-surface-variant' }
    ],
    chatLabel: '招聘顾问在线',
    isFavorite: false,
    applied: false
  },
  {
    id: 5,
    matchLabel: 'AI 匹配度 91% 央企编制',
    matchBadgeClass: 'bg-surface-container text-primary',
    matchIconClass: 'text-primary',
    matchIcon: 'policy',
    matchFill: false,
    title: '新能源储能电力电子系统软件架构师',
    salary: '20k - 30k',
    salaryExtra: '· 解决北京户口',
    salaryExtraClass: 'text-tertiary font-semibold',
    companyLogo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDo6LhOn0GMjCp-thoNRWJELO9gk6koeMez8pvGyAJcR56ZtrD5KxZ60kApCWdAAq8_HadwRxXCz6eEbJCoRLKNfljvv6NPU6XZSSsC87KIy8YMvxevFU2Qq-OtiHdzmmbFFcqq8JRqYZmB5wAmdhZNb8R1NUjktFR0fw7Za71Mqvn7DN_Dpzhmo12OxCHjLb5bYX1e9NAkzlhnARi6eoZvwXS4iyQJwKyU1mEpcCSiznc025Q0pqUw5Q',
    companyName: '国家电网 · 全球能源互联网研究院',
    companyDesc: '国家重点科研院所 · 央企直属单位',
    location: '北京 · 昌平未来科学城',
    tagIcon: 'security',
    tag: '六险二金 · 稳定发展',
    chips: [
      { text: '100% 解决应届落户', class: 'bg-surface-container text-primary' },
      { text: '重点学科契合', class: 'bg-secondary-fixed/50 text-on-secondary-fixed' },
      { text: '央企事业编制通道', class: 'bg-surface-container-high text-on-surface-variant' }
    ],
    chatLabel: '招聘办公室咨询',
    isFavorite: false,
    applied: false
  },
  {
    id: 6,
    matchLabel: 'AI 匹配度 90% 高薪量化',
    matchBadgeClass: 'bg-surface-container text-primary',
    matchIconClass: 'text-primary',
    matchIcon: 'trending_up',
    matchFill: false,
    title: '高频量化策略研发实习生 (留用通道)',
    salary: '500 - 800元/天',
    salaryExtra: '(转正 50w-90w/年)',
    salaryExtraClass: 'text-on-surface-variant font-medium',
    companyLogo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBzYVThSK59QVh162XPhqhWkoczyI93e0oGAVF8v21TBWjOAvfj1YAdg5npAV7xvRuflgRrfWkqZVv0A6MMbUJFuu0kP0Km80B4hv-3nztmmUdnWpC-_BZg77Sc2BJ-poxdwqE6idewfepJ9cclOv2mKqR90ypOmkTdI3EaWyoPOEPAkXlpBI8xkXwtFv1mEAa5UUxBwWUrxrY8B0i4fi_7cTMUVreJxr427GafPuECxz0OWGxaNVcQ6w',
    companyName: '幻方量化投资中心',
    companyDesc: '百亿量化私募 · 万卡超算集群',
    location: '杭州 · 西湖区',
    tagIcon: 'coffee',
    tag: '顶级自研算力 · 日常下午茶',
    chips: [
      { text: '极客代码文化', class: 'bg-surface-container text-primary' },
      { text: 'C++ / Linux高性能调优', class: 'bg-secondary-fixed/50 text-on-secondary-fixed' },
      { text: '转正率 85%', class: 'bg-surface-container-high text-on-surface-variant' }
    ],
    chatLabel: '资深量化顾问聊',
    isFavorite: false,
    applied: false
  }
])

const visiblePages = computed(() => {
  const pages = []
  const end = Math.min(totalPages.value, 5)
  for (let i = 1; i <= end; i++) pages.push(i)
  return pages
})

function triggerToast(message, icon = 'check_circle') {
  if (toastTimer) clearTimeout(toastTimer)
  toast.message = message
  toast.icon = icon
  toast.visible = true
  toastTimer = setTimeout(() => {
    toast.visible = false
  }, 2600)
}

function handleRefresh() {
  if (isRefreshing.value) return
  isRefreshing.value = true
  setTimeout(() => {
    isRefreshing.value = false
    triggerToast('已为您基于最新大模型权重重新撮合 24 个高契合岗位', 'auto_awesome')
  }, 700)
}

function toggleFavorite(job) {
  if (job.isFavorite) {
    job.isFavorite = false
    triggerToast('已取消收藏该岗位')
  } else {
    job.isFavorite = true
    triggerToast('职位已保存至「我的收藏」', 'favorite')
  }
}

function handleApply(job) {
  if (job.applied) return
  job.applied = true
  triggerToast('简历已直达HR初筛通道！预计48小时内反馈', 'send')
}

function handleFeedback(key) {
  activeFeedback.value = key
  triggerToast('感谢您的反馈，算法模型将在下个计算周期校准权重', 'thumb_up')
}

onMounted(() => {})
</script>
