<template>
  <div class="min-h-screen bg-gray-50">
    <div class="bg-white/60 py-8">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <nav class="flex items-center gap-1 text-sm text-gray-500 mb-3">
          <router-link to="/profile" class="hover:text-primary transition-colors flex items-center gap-1">
            <span class="material-symbols-outlined text-[16px]">home</span>
            <span>个人中心</span>
          </router-link>
          <span class="material-symbols-outlined text-[14px]">chevron_right</span>
          <router-link to="/applications" class="hover:text-primary transition-colors">我的求职</router-link>
          <span class="material-symbols-outlined text-[14px]">chevron_right</span>
          <span class="text-gray-900 font-semibold">我的收藏职位</span>
        </nav>
        <div class="flex flex-col lg:flex-row lg:items-end justify-between gap-4">
          <div>
            <div class="flex items-center gap-2">
              <h1 class="text-2xl md:text-3xl text-gray-900 tracking-tight font-bold">我的收藏职位</h1>
              <span class="px-2 py-0.5 rounded-full bg-blue-50 text-primary text-sm font-bold">{{ filteredList.length }}</span>
            </div>
            <p class="text-sm text-gray-500 mt-1">
              随时关注心仪职位变动，支持一键快捷投递与批量申请，精准把握毕业季每一次面试良机。
            </p>
          </div>
          <div class="flex flex-wrap items-center gap-2 shrink-0">
            <button
              class="px-4 py-2 bg-primary text-white text-sm rounded-lg shadow-sm hover:bg-blue-700 transition-all flex items-center gap-1.5 active:scale-95"
              @click="batchApply"
            >
              <span class="material-symbols-outlined text-[18px]">send_and_archive</span>
              <span>一键投递所有选中 ({{ selectedIds.size }})</span>
            </button>
            <button
              class="px-4 py-2 bg-gray-100 text-gray-600 text-sm rounded-lg hover:bg-red-50 hover:text-red-600 transition-all flex items-center gap-1.5"
              @click="batchRemove"
            >
              <span class="material-symbols-outlined text-[18px]">delete_sweep</span>
              <span>批量取消收藏</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto w-full px-4 md:px-6 lg:px-10 py-8">
      <div class="grid grid-cols-1 lg:grid-cols-12 gap-8">
        <div class="lg:col-span-8 flex flex-col space-y-4">
          <div class="bg-white p-3 rounded-xl shadow-sm flex flex-col md:flex-row md:items-center justify-between gap-3">
            <div class="flex items-center gap-2 overflow-x-auto pb-1 md:pb-0">
              <button
                v-for="tab in statusTabs"
                :key="tab.key"
                class="px-4 py-1.5 rounded-lg text-sm transition-colors whitespace-nowrap"
                :class="activeTab === tab.key ? 'bg-primary text-white' : 'text-gray-500 hover:bg-gray-100'"
                @click="activeTab = tab.key"
              >
                {{ tab.label }}
              </button>
            </div>
            <div class="relative min-w-[260px]">
              <span class="material-symbols-outlined absolute left-3 top-2.5 text-gray-400 text-[18px]">search</span>
              <input
                v-model="searchQuery"
                type="text"
                placeholder="搜索已收藏的职位或公司名称..."
                class="w-full pl-9 pr-4 py-2 text-sm bg-gray-50 rounded-lg focus:outline-none focus:bg-white focus:ring-2 focus:ring-primary/20 text-gray-900 placeholder:text-gray-400"
              />
            </div>
          </div>

          <div class="flex items-center justify-between px-1 py-1 text-gray-500 text-sm">
            <label class="flex items-center gap-2 cursor-pointer select-none">
              <input
                v-model="selectAll"
                type="checkbox"
                class="w-4 h-4 rounded accent-primary cursor-pointer"
              />
              <span>全选当前页可用职位</span>
            </label>
            <div class="flex items-center gap-3">
              <span class="flex items-center gap-1"><span class="w-2 h-2 rounded-full bg-blue-500"></span> 算法/研发生效中</span>
              <span class="flex items-center gap-1"><span class="w-2 h-2 rounded-full bg-gray-300"></span> 灰标下线不可选</span>
            </div>
          </div>

          <div class="flex flex-col space-y-4">
            <article
              v-for="job in filteredList"
              :key="job.id"
              class="group rounded-xl p-5 shadow-sm hover:shadow-md transition-all duration-200 relative overflow-hidden"
              :class="job.status === 'closed' ? 'bg-gray-100 opacity-70 hover:opacity-100' : 'bg-white'"
            >
              <div
                class="absolute left-0 top-0 bottom-0 w-1.5"
                :class="job.status === 'closed' ? 'bg-gray-300' : job.accentColor"
              ></div>
              <div class="flex flex-col md:flex-row md:items-start justify-between gap-4">
                <div class="flex items-start gap-3 min-w-0">
                  <input
                    v-if="job.status !== 'closed'"
                    v-model="job.checked"
                    type="checkbox"
                    class="mt-1 w-4 h-4 rounded accent-primary cursor-pointer"
                  />
                  <input
                    v-else
                    type="checkbox"
                    disabled
                    class="mt-1 w-4 h-4 rounded accent-gray-300 cursor-not-allowed opacity-40"
                  />
                  <div
                    v-if="job.status !== 'closed'"
                    class="w-12 h-12 rounded-xl bg-blue-50 flex items-center justify-center shrink-0 shadow-sm overflow-hidden"
                  >
                    <img
                      class="w-full h-full object-cover"
                      :src="job.logo"
                      :alt="job.company"
                    />
                  </div>
                  <div
                    v-else
                    class="w-12 h-12 rounded-xl bg-gray-200 flex items-center justify-center shrink-0 grayscale"
                  >
                    <span class="material-symbols-outlined text-gray-400 text-[28px]">domain_disabled</span>
                  </div>
                  <div class="min-w-0 space-y-1">
                    <div class="flex items-center gap-2 flex-wrap">
                      <h2
                        class="text-base font-bold truncate transition-colors"
                        :class="job.status === 'closed' ? 'text-gray-400 line-through' : 'text-gray-900 group-hover:text-primary'"
                      >
                        {{ job.title }}
                      </h2>
                      <span
                        class="px-2 py-0.5 rounded-md text-xs font-semibold"
                        :class="job.tagColor"
                      >
                        {{ job.tag }}
                      </span>
                    </div>
                    <div class="flex items-center gap-2 text-sm" :class="job.status === 'closed' ? 'text-gray-400' : 'text-gray-500'">
                      <span class="font-medium" :class="job.status === 'closed' ? '' : 'text-gray-900'">{{ job.company }}</span>
                      <span v-if="job.verified" class="flex items-center text-primary" :title="job.verifiedTip">
                        <span class="material-symbols-outlined text-[16px]">{{ job.verifiedIcon }}</span>
                      </span>
                      <span>·</span>
                      <span>{{ job.location }}</span>
                      <span v-if="job.education">·</span>
                      <span v-if="job.education">{{ job.education }}</span>
                      <span v-if="job.year">·</span>
                      <span v-if="job.year">{{ job.year }}</span>
                    </div>
                  </div>
                </div>
                <div class="md:text-right shrink-0">
                  <div class="text-lg font-bold" :class="job.status === 'closed' ? 'text-gray-400' : 'text-primary'">
                    {{ job.salary }}
                    <span v-if="job.salarySuffix" class="text-sm text-gray-500 font-normal">{{ job.salarySuffix }}</span>
                  </div>
                  <p v-if="job.status !== 'closed'" class="text-xs font-medium mt-0.5" :class="job.highlightColor">{{ job.highlight }}</p>
                  <p v-else class="text-xs font-medium mt-0.5 text-red-500">{{ job.highlight }}</p>
                </div>
              </div>
              <div class="flex flex-wrap gap-2 mt-3">
                <span
                  v-for="(benefit, i) in job.benefits"
                  :key="i"
                  class="px-2 py-0.5 rounded text-xs"
                  :class="benefit.color"
                >
                  {{ benefit.text }}
                </span>
              </div>
              <div
                class="mt-4 pt-3 flex flex-col sm:flex-row sm:items-center justify-between gap-3 -mx-5 -mb-5 px-5 py-3"
                :class="job.status === 'closed' ? 'bg-gray-200/60' : 'bg-gray-50'"
              >
                <div class="flex items-center gap-2 text-gray-400 text-xs">
                  <span class="material-symbols-outlined text-[16px]">
                    {{ job.status === 'closed' ? 'info' : 'bookmark' }}
                  </span>
                  <span>{{ job.status === 'closed' ? job.closedInfo : '收藏于 ' + job.savedDate }}</span>
                  <template v-if="job.status !== 'closed'">
                    <span>·</span>
                    <span class="text-secondary font-semibold">{{ job.updateInfo }}</span>
                  </template>
                </div>
                <div class="flex items-center gap-2">
                  <template v-if="job.status !== 'closed'">
                    <button class="px-3 py-1.5 text-gray-600 hover:text-primary text-sm flex items-center gap-1 rounded hover:bg-gray-100 transition-colors">
                      <span class="material-symbols-outlined text-[16px]">chat</span>
                      <span>在线沟通</span>
                    </button>
                    <button
                      class="px-3 py-1.5 text-gray-400 hover:text-red-500 text-sm rounded hover:bg-red-50 transition-colors"
                      @click="removeFavorite(job)"
                    >
                      取消收藏
                    </button>
                    <button
                      class="px-4 py-1.5 bg-primary text-white rounded-lg text-sm hover:bg-blue-700 shadow-sm transition-all flex items-center gap-1"
                      @click="applyJob(job)"
                    >
                      <span class="material-symbols-outlined text-[16px]">outgoing_mail</span>
                      <span>立即投递简历</span>
                    </button>
                  </template>
                  <template v-else>
                    <button
                      class="px-3 py-1.5 text-red-500 text-sm rounded hover:bg-red-50 transition-colors"
                      @click="removeFavorite(job)"
                    >
                      移除收藏
                    </button>
                    <button
                      class="px-4 py-1.5 bg-gray-200 text-gray-400 rounded-lg text-sm cursor-not-allowed"
                      disabled
                    >
                      已停止投递
                    </button>
                  </template>
                </div>
              </div>
            </article>
          </div>

          <div class="bg-white p-4 rounded-xl shadow-sm flex flex-col sm:flex-row items-center justify-between gap-4 mt-6">
            <p class="text-sm text-gray-500">
              共 <span class="font-semibold text-gray-900">{{ filteredList.length }}</span> 条收藏记录，当前显示第 1 / 1 页
            </p>
            <div class="flex items-center gap-2">
              <button class="px-3 py-1.5 rounded-lg bg-gray-100 text-gray-500 text-sm hover:bg-gray-200 transition-colors disabled:opacity-50 disabled:cursor-not-allowed" disabled>
                <span class="material-symbols-outlined text-[16px] align-middle">chevron_left</span>
                <span>上一页</span>
              </button>
              <button class="w-8 h-8 rounded-lg bg-primary text-white text-sm font-semibold flex items-center justify-center">1</button>
              <button class="px-3 py-1.5 rounded-lg bg-gray-100 text-gray-500 text-sm hover:bg-gray-200 transition-colors">
                <span>下一页</span>
                <span class="material-symbols-outlined text-[16px] align-middle">chevron_right</span>
              </button>
            </div>
          </div>
        </div>

        <div class="lg:col-span-4 flex flex-col space-y-4">
          <div class="bg-white rounded-xl p-5 shadow-sm">
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-[22px]">auto_awesome</span>
                <h3 class="text-base text-gray-900 font-bold">求职助手小贴士</h3>
              </div>
              <span class="px-2 py-0.5 rounded-full bg-blue-50 text-primary text-xs">AI 赋能</span>
            </div>
            <div class="space-y-4">
              <div class="p-3 rounded-lg bg-gray-50 flex items-start gap-3 hover:bg-gray-100 transition-colors cursor-pointer">
                <div class="p-2 bg-blue-50 text-primary rounded-lg shrink-0">
                  <span class="material-symbols-outlined text-[20px]">document_scanner</span>
                </div>
                <div class="space-y-0.5">
                  <h4 class="text-sm text-gray-900 font-semibold">智能简历诊断与岗位匹配</h4>
                  <p class="text-sm text-gray-500 leading-relaxed">
                    你收藏的职位平均要求在大模型部署与微调技能，诊断你的简历匹配度已达 88%。
                  </p>
                  <router-link to="/resume/edit" class="text-primary text-xs font-semibold inline-flex items-center gap-0.5 pt-1 hover:underline">
                    前往一键优化 <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
                  </router-link>
                </div>
              </div>
              <div class="p-3 rounded-lg bg-gray-50 flex items-start gap-3 hover:bg-gray-100 transition-colors cursor-pointer">
                <div class="p-2 bg-blue-50 text-secondary rounded-lg shrink-0">
                  <span class="material-symbols-outlined text-[20px]">timeline</span>
                </div>
                <div class="space-y-0.5">
                  <h4 class="text-sm text-gray-900 font-semibold">投递全流程进度追踪</h4>
                  <p class="text-sm text-gray-500 leading-relaxed">
                    已有 2 个关联职位的笔试预约将于本周四截止，及时查看避免错过笔试邀请。
                  </p>
                  <router-link to="/applications" class="text-secondary text-xs font-semibold inline-flex items-center gap-0.5 pt-1 hover:underline">
                    查看投递进度 <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
                  </router-link>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-xl p-5 shadow-sm">
            <div class="flex items-center justify-between mb-2">
              <h3 class="text-base text-gray-900 font-bold">收藏夹为空预览与演示</h3>
              <button class="text-primary text-xs hover:underline" @click="showEmptyPreview = !showEmptyPreview">
                {{ showEmptyPreview ? '折叠预览' : '展开预览' }}
              </button>
            </div>
            <p class="text-sm text-gray-500 mb-4">
              测试与查看当学生收藏夹完全清空时的友善兜底引导与推荐：
            </p>
            <div
              v-show="showEmptyPreview"
              class="p-4 rounded-xl bg-gray-50 text-center flex flex-col items-center"
            >
              <div class="w-20 h-20 rounded-full bg-gray-200 flex items-center justify-center text-gray-400 mb-3">
                <span class="material-symbols-outlined text-[40px]">favorite_border</span>
              </div>
              <h4 class="text-base text-gray-900 font-bold">暂无收藏职位</h4>
              <p class="text-sm text-gray-500 mt-1 max-w-[240px] leading-relaxed">
                快去职位大厅探索优质校招与名企实习，心仪职位随时加藏备选！
              </p>
              <router-link
                to="/jobs"
                class="mt-4 px-5 py-2 bg-primary text-white rounded-lg text-sm hover:bg-blue-700 shadow-sm transition-all flex items-center gap-1"
              >
                <span class="material-symbols-outlined text-[16px]">travel_explore</span>
                <span>前往职位大厅</span>
              </router-link>
            </div>
          </div>

          <div class="bg-white rounded-xl p-5 shadow-sm">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-base text-gray-900 font-bold">高校校友都在投</h3>
              <router-link to="/jobs" class="text-primary text-xs hover:underline">更多推荐</router-link>
            </div>
            <div class="space-y-2">
              <div
                v-for="rec in recommendedJobs"
                :key="rec.title"
                class="p-2 rounded-lg hover:bg-gray-50 transition-colors flex items-center justify-between"
              >
                <div>
                  <p class="text-sm text-gray-900 font-semibold truncate max-w-[170px]">{{ rec.title }}</p>
                  <p class="text-sm text-gray-500">{{ rec.company }}</p>
                </div>
                <div class="text-right">
                  <p class="text-sm text-primary font-bold">{{ rec.salary }}</p>
                  <span class="text-xs text-secondary">{{ rec.applicants }}名校友已投</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <transition name="fade">
      <div
        v-if="toast.visible"
        class="fixed bottom-6 right-6 z-50 flex items-center gap-2 px-4 py-3 bg-gray-800 text-white rounded-xl shadow-lg"
      >
        <span class="material-symbols-outlined text-blue-300 text-[20px]">{{ toast.icon }}</span>
        <span class="text-sm">{{ toast.message }}</span>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'

const searchQuery = ref('')
const activeTab = ref('all')
const selectedIds = ref(new Set())
const showEmptyPreview = ref(false)
const toast = ref({ visible: false, message: '', icon: 'check_circle' })

const statusTabs = [
  { key: 'all', label: '全部收藏 (5)' },
  { key: 'active', label: '投递开放中 (4)' },
  { key: 'closed', label: '已招满/下线 (1)' },
]

const jobList = ref([
  {
    id: 1,
    title: '大模型算法工程师',
    company: '智航未来科技有限公司',
    location: '北京 · 海淀区',
    education: '硕士及以上',
    year: '2025届',
    salary: '25k-40k',
    salarySuffix: '· 16薪',
    tag: '2025届校招/可转正实习',
    tagColor: 'bg-blue-50 text-blue-700',
    status: 'active',
    accentColor: 'bg-primary',
    verified: true,
    verifiedTip: '官方认证企业',
    verifiedIcon: 'verified',
    highlight: '高通过率 · 急聘中',
    highlightColor: 'text-secondary',
    logo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAukZ5Fodt5ck2y-W4_FWcrm3IMHl4HJ6P8072AvxUkpss8gf_zgd0deBVm_DU87kQbZVcbiry8fvdo_0ip8qVS7gZbHJqJOyLGpplt8ldvbK0wfeDm1xNN1NVPv-bD36MpCUvBdwokBnGhwTyXejKnipFZZp0ETFrJb87tkvWB_fxEKY2Fly2zUY57mtPo7qVfCjZlMzDxGfDdiXrvdOgwEArZUZ-iubEJXa_ykU4e-SlIvYAOI_lH8A',
    benefits: [
      { text: '转正率90%+', color: 'bg-gray-100 text-gray-600' },
      { text: '五险二金', color: 'bg-gray-100 text-gray-600' },
      { text: '免息房贷', color: 'bg-gray-100 text-gray-600' },
      { text: '技术大牛带教', color: 'bg-blue-50 text-primary' },
    ],
    savedDate: '2025-03-08',
    updateInfo: '职位更新于今日',
    checked: false,
  },
  {
    id: 2,
    title: '前端开发工程师',
    company: '字节跃动互娱研发中心',
    location: '北京 · 朝阳区',
    education: '本科及以上',
    year: '2025届',
    salary: '18k-28k',
    salarySuffix: '· 15薪',
    tag: '管培生计划',
    tagColor: 'bg-blue-50 text-blue-700',
    status: 'active',
    accentColor: 'bg-blue-400',
    verified: true,
    verifiedTip: '官方认证企业',
    verifiedIcon: 'verified',
    highlight: '校招热度Top3',
    highlightColor: 'text-gray-500',
    logo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDmc1pYRYy-nlJL94UQGJ8pL9CFbh-Bf_L4IjQubdLLRP7B0P6ZDos24OZuIpEqwvswmbM5_v-gd3da0YE1Tv7xdQTWp3o7szksbMwhK_2mdqd-Z4bQq_JElSALHRz-Vy-Woiyz3tWAAPnOF-A1dKj5JJ_rEmn49miStm0jUl-NxBiGEBXM3w4e4CGk1dziNTxp-4T4oZebx4VRqUXkvsBAF_eFTOdEoxUzZ3Ddpkvn01KFJ4NsQfTZLQ',
    benefits: [
      { text: '高成长空间', color: 'bg-gray-100 text-gray-600' },
      { text: '租房补贴', color: 'bg-gray-100 text-gray-600' },
      { text: '免费三餐', color: 'bg-gray-100 text-gray-600' },
      { text: '双休', color: 'bg-blue-50 text-blue-600' },
    ],
    savedDate: '2025-03-07',
    updateInfo: '3天前投递反馈活跃',
    checked: false,
  },
  {
    id: 3,
    title: '电网自动化与新能源系统工程师',
    company: '国家电网 · 全球能源互联研究院',
    location: '北京 · 昌平区',
    education: '应届硕士/博士',
    year: '',
    salary: '22k-32k',
    salarySuffix: '/月 + 京户指标',
    tag: '央企内推通道',
    tagColor: 'bg-orange-50 text-orange-700',
    status: 'active',
    accentColor: 'bg-blue-600',
    verified: true,
    verifiedTip: '重点央企直聘',
    verifiedIcon: 'domain_verification',
    highlight: '解决北京户口',
    highlightColor: 'text-amber-600',
    logo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuArRjgLJoXq0spUUt7qvEllaqVXni5QYLZX4dWBExnygEqymn_aW4C5eXdPY6VOzU_DD_5SGe5GZ_7qkwtAvA6hlVwDvDlw5fYdMY6wp9IkRiIubGDUJTLBJhCM9mkJmGim4xUjXMS7GGvex8mpxuf_bc3w32Bu6Q9Cv4TPlwZ7nFOt2dQKbuU6jm1VistLKaBR_SfPCwbck3qlIUuzqzk1uRApkwMvIhEJDgt9x4F_eev-_2B8vdY18w',
    benefits: [
      { text: '央企编制', color: 'bg-orange-50 text-orange-700' },
      { text: '六险二金', color: 'bg-gray-100 text-gray-600' },
      { text: '安家落户', color: 'bg-gray-100 text-gray-600' },
      { text: '科研专项经费', color: 'bg-gray-100 text-gray-600' },
    ],
    savedDate: '2025-03-05',
    updateInfo: '接收简历截止 2025-04-15',
    checked: false,
  },
  {
    id: 4,
    title: 'Azure 云原生开发实习生',
    company: '微软中国 · 亚太研发集团',
    location: '上海 · 徐汇区',
    education: '',
    year: '2025/2026届',
    salary: '25k-35k',
    salarySuffix: '/月 + 期权',
    tag: '暑期实习可转正',
    tagColor: 'bg-blue-50 text-blue-700',
    status: 'active',
    accentColor: 'bg-secondary',
    verified: true,
    verifiedTip: '跨国500强企业',
    verifiedIcon: 'verified',
    highlight: '实习 400元/天',
    highlightColor: 'text-secondary',
    logo: 'https://lh3.googleusercontent.com/aida-public/AB6AXuD7NS50gtiT12RKjWlD8Tkr6_5nC5Egl5w45VAtkz3VjK-fdRonHw08ABj35IHS8xrzbZWkvNSw_i3ocjd2ch9ajEVG_on8akHiecltS1qGtvYL7wCnKvvkepBxjDwZkx2GyB5XuNLYZajNwxfUzqSkne02Y_dw6qKq0MLOkkhOAsuXCbW5eBRJShJ9L-GS_M-wmehN77_NjsTgNEi-4twn4UC9_KoA1G0c7PnWPNPXVWZOcam41JqGqg',
    benefits: [
      { text: '外企WLB', color: 'bg-gray-100 text-gray-600' },
      { text: '弹性办公', color: 'bg-gray-100 text-gray-600' },
      { text: '英语环境', color: 'bg-gray-100 text-gray-600' },
      { text: '健身房津贴', color: 'bg-gray-100 text-gray-600' },
    ],
    savedDate: '2025-03-02',
    updateInfo: '已直通部门业务面试',
    checked: false,
  },
  {
    id: 5,
    title: '计算机视觉算法实习生',
    company: '商汤科技智能视频部',
    location: '北京',
    education: '计算机/AI相关专业',
    year: '',
    salary: '350-450元/天',
    salarySuffix: '',
    tag: '已招满或暂停招聘',
    tagColor: 'bg-red-50 text-red-700',
    status: 'closed',
    accentColor: 'bg-gray-300',
    verified: false,
    verifiedTip: '',
    verifiedIcon: '',
    highlight: 'HC已满闭招',
    highlightColor: 'text-red-500',
    logo: '',
    benefits: [
      { text: 'CVPR论文学术指导', color: 'bg-gray-200 text-gray-400' },
      { text: '免费夜宵车补', color: 'bg-gray-200 text-gray-400' },
    ],
    savedDate: '',
    updateInfo: '',
    closedInfo: '该职位已于 2025-03-06 终止对外接收简历',
    checked: false,
  },
])

const filteredList = computed(() => {
  let list = jobList.value
  if (activeTab.value === 'active') {
    list = list.filter((j) => j.status === 'active')
  } else if (activeTab.value === 'closed') {
    list = list.filter((j) => j.status === 'closed')
  }
  if (searchQuery.value.trim()) {
    const q = searchQuery.value.trim().toLowerCase()
    list = list.filter(
      (j) =>
        j.title.toLowerCase().includes(q) ||
        j.company.toLowerCase().includes(q)
    )
  }
  return list
})

const selectAll = computed({
  get() {
    const activeJobs = filteredList.value.filter((j) => j.status !== 'closed')
    return activeJobs.length > 0 && activeJobs.every((j) => j.checked)
  },
  set(val) {
    filteredList.value.forEach((j) => {
      if (j.status !== 'closed') j.checked = val
    })
  },
})

watch(
  () => filteredList.value.map((j) => j.checked),
  () => {
    selectedIds.value = new Set(
      jobList.value.filter((j) => j.checked).map((j) => j.id)
    )
  },
  { deep: true }
)

function showToast(message, icon = 'check_circle') {
  toast.value = { visible: true, message, icon }
  setTimeout(() => {
    toast.value.visible = false
  }, 2500)
}

function batchApply() {
  const count = jobList.value.filter((j) => j.checked).length
  if (count === 0) {
    showToast('请先勾选需要批量申请的有效职位', 'warning')
  } else {
    showToast(`已成功为选中的 ${count} 个职位同步投递校招通用简历！`, 'task_alt')
  }
}

function batchRemove() {
  const count = jobList.value.filter((j) => j.checked).length
  if (count === 0) {
    showToast('请先选择要取消收藏的职位卡片', 'warning')
  } else {
    showToast(`已将选中的 ${count} 个职位从收藏列表移除`, 'delete')
    jobList.value = jobList.value.filter((j) => !j.checked)
  }
}

function removeFavorite(job) {
  jobList.value = jobList.value.filter((j) => j.id !== job.id)
  showToast('已取消该职位的收藏', 'bookmark_remove')
}

function applyJob(job) {
  showToast('投递成功！HR与业务主管将尽快审核你的简历。', 'mark_email_read')
}

const recommendedJobs = [
  { title: '后端研发管培生', company: '美团 · 科技研发平台', salary: '22k-32k', applicants: 38 },
  { title: '芯片硬件验证工程师', company: '华为技术 · 海思半导体', salary: '26k-38k', applicants: 52 },
]
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
