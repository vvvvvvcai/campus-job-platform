<template>
  <div class="min-h-screen bg-background">
    <!-- 页面头部 -->
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6 flex items-start justify-between">
        <div>
          <h1 class="text-2xl font-bold text-on-surface">候选人投递管理</h1>
          <p class="text-on-surface-variant mt-1">实时查阅海内外重点高校毕业生简历，支持AI多维评分与批量推进面试/发Offer，构筑高效精准校招流水线。</p>
        </div>
        <div class="flex items-center gap-3">
          <button class="h-10 px-4 flex items-center gap-2 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium hover:bg-surface-container transition-colors">
            <span class="material-symbols-outlined text-lg">tune</span>
            AI 评分规则设置
          </button>
          <button class="h-10 px-5 flex items-center gap-2 rounded-xl bg-primary text-on-primary text-sm font-semibold hover:bg-primary-container transition-colors shadow-sm">
            <span class="material-symbols-outlined text-lg">add</span>
            发布新校招职位
          </button>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-6 space-y-5">
      <!-- 统计卡片 -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">总投递数</span>
            <div class="w-9 h-9 rounded-xl bg-primary/10 flex items-center justify-center">
              <span class="material-symbols-outlined text-primary text-lg">folder_open</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-on-surface">1,428</span>
            <span class="text-xs text-on-surface-variant">份</span>
          </div>
          <div class="flex items-center gap-2 mt-3 pt-3 border-t border-surface-container-high">
            <span class="material-symbols-outlined text-base text-primary">show_chart</span>
            <span class="text-xs text-primary font-medium">+14.2%</span>
            <span class="text-xs text-on-surface-variant">周环比增长</span>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">待处理初筛</span>
            <div class="w-9 h-9 rounded-xl bg-secondary/10 flex items-center justify-center">
              <span class="material-symbols-outlined text-secondary text-lg">mark_email_unread</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-primary">32</span>
            <span class="text-xs text-on-surface-variant">份未评</span>
          </div>
          <div class="flex items-center gap-2 mt-3 pt-3 border-t border-surface-container-high">
            <span class="px-2 py-0.5 bg-emerald-50 text-emerald-600 text-xs font-medium rounded-full">8份需48h内响应</span>
            <span class="text-xs text-on-surface-variant ml-auto">防流失警戒</span>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">今日新增投递</span>
            <div class="w-9 h-9 rounded-xl bg-emerald-50 flex items-center justify-center">
              <span class="material-symbols-outlined text-emerald-600 text-lg">calendar_today</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-on-surface">46</span>
            <span class="text-xs text-on-surface-variant">份今日入池</span>
          </div>
          <div class="flex items-center justify-between mt-3 pt-3 border-t border-surface-container-high">
            <div class="flex items-center gap-1.5">
              <span class="material-symbols-outlined text-base text-on-surface-variant">schedule</span>
              <span class="text-xs text-on-surface-variant">1.4 工作日</span>
            </div>
            <span class="text-xs text-on-surface-variant">平均响应时效</span>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-2xl p-5 border border-surface-container-high">
          <div class="flex items-center justify-between mb-3">
            <span class="text-xs font-medium text-on-surface-variant">已发 Offer / 签约</span>
            <div class="w-9 h-9 rounded-xl bg-amber-50 flex items-center justify-center">
              <span class="material-symbols-outlined text-amber-600 text-lg">verified</span>
            </div>
          </div>
          <div class="flex items-baseline gap-2">
            <span class="text-4xl font-bold text-on-surface">28</span>
            <span class="text-xs text-on-surface-variant">人通过三方</span>
          </div>
          <div class="flex items-center justify-between mt-3 pt-3 border-t border-surface-container-high">
            <div class="flex items-center gap-1.5">
              <span class="material-symbols-outlined text-base text-primary">thumb_up</span>
              <span class="text-xs text-primary font-medium">89.3%</span>
            </div>
            <span class="text-xs text-on-surface-variant">签约意向达成率</span>
          </div>
        </div>
      </div>

      <!-- 筛选工具栏 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-4">
        <div class="flex flex-col lg:flex-row lg:items-center gap-3">
          <select class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 min-w-[180px]">
            <option>全部在招职位 (12个职位)</option>
          </select>
          <select class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 min-w-[120px]">
            <option>全部状态</option>
          </select>
          <select class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 min-w-[120px]">
            <option>院校层次: 不限</option>
          </select>
          <select class="h-10 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 min-w-[120px]">
            <option>学历要求: 不限</option>
          </select>
          <div class="relative flex-1">
            <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">search</span>
            <input type="text" placeholder="搜索候选人姓名、毕业院校或技能..."
              class="w-full h-10 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/60 text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
          </div>
        </div>
        <div class="flex items-center gap-3 mt-3 pt-3 border-t border-surface-container-high flex-wrap">
          <span class="text-xs text-on-surface-variant">快速标签:</span>
          <button class="px-3 py-1.5 rounded-lg text-xs font-medium bg-primary text-on-primary">今日投递 (46)</button>
          <button class="px-3 py-1.5 rounded-lg text-xs font-medium bg-surface-container text-on-surface-variant hover:bg-surface-container-high">AI评分90分以上 (184)</button>
          <button class="px-3 py-1.5 rounded-lg text-xs font-medium bg-surface-container text-on-surface-variant hover:bg-surface-container-high">国家奖学金获得者 (38)</button>
          <button class="px-3 py-1.5 rounded-lg text-xs font-medium bg-surface-container text-on-surface-variant hover:bg-surface-container-high">ACM/顶级竞赛获奖 (19)</button>
          <div class="flex items-center gap-2 ml-auto">
            <button class="text-xs text-on-surface-variant hover:text-on-surface">清空筛选</button>
            <button class="h-9 px-5 rounded-xl bg-primary text-on-primary text-xs font-semibold hover:bg-primary-container transition-colors">开始检索</button>
          </div>
        </div>
      </div>

      <!-- 批量操作栏 -->
      <div class="flex items-center justify-between">
        <div class="flex items-center gap-3">
          <label class="flex items-center gap-2 text-sm text-on-surface-variant cursor-pointer">
            <input type="checkbox" class="w-4 h-4 rounded border-surface-container-high text-primary" />
            全选
          </label>
          <span class="text-xs text-on-surface-variant">已选 <span class="font-medium text-on-surface">0</span> 份简历</span>
        </div>
        <div class="flex items-center gap-2">
          <button class="h-9 px-4 flex items-center gap-1.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-xs font-medium hover:bg-surface-container transition-colors">
            <span class="material-symbols-outlined text-base">drafts</span>
            批量标为已读
          </button>
          <button class="h-9 px-4 flex items-center gap-1.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-xs font-medium hover:bg-surface-container transition-colors">
            <span class="material-symbols-outlined text-base">event</span>
            批量面试邀请
          </button>
          <button class="h-9 px-4 flex items-center gap-1.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-xs font-medium hover:bg-surface-container transition-colors">
            <span class="material-symbols-outlined text-base">archive</span>
            批量打包简历(ZIP)
          </button>
          <button class="h-9 px-4 flex items-center gap-1.5 rounded-xl bg-red-50 text-red-600 text-xs font-medium hover:bg-red-100 transition-colors">
            <span class="material-symbols-outlined text-base">delete</span>
            批量淘汰/入库
          </button>
        </div>
      </div>

      <!-- 候选人列表表格 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-surface-container-low/50 border-b border-surface-container-high">
                <th class="text-left px-5 py-3.5 w-10">
                  <input type="checkbox" class="w-4 h-4 rounded border-surface-container-high text-primary" />
                </th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">求职者姓名 / 毕业院校</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">应聘职位</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">AI人岗契合度</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">投递时间</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">当前流转状态</th>
                <th class="text-left px-5 py-3.5 text-xs font-semibold text-on-surface-variant">HR 初筛评语/亮点</th>
                <th class="text-right px-5 py-3.5 text-xs font-semibold text-on-surface-variant">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="candidate in candidates" :key="candidate.id" class="border-b border-surface-container-high/50 hover:bg-surface-container-low/30 transition-colors">
                <td class="px-5 py-4">
                  <input type="checkbox" class="w-4 h-4 rounded border-surface-container-high text-primary" />
                </td>
                <td class="px-5 py-4">
                  <div class="flex items-center gap-3">
                    <div class="w-10 h-10 rounded-full flex items-center justify-center text-white font-bold text-sm shrink-0" :style="{ background: candidate.avatarColor }">
                      {{ candidate.name[0] }}
                    </div>
                    <div>
                      <div class="flex items-center gap-2">
                        <span class="font-semibold text-on-surface">{{ candidate.name }}</span>
                        <span class="px-1.5 py-0.5 bg-primary/10 text-primary text-xs font-semibold rounded">{{ candidate.schoolTag }}</span>
                        <span class="px-1.5 py-0.5 bg-secondary/10 text-secondary text-xs font-semibold rounded">{{ candidate.eduLevel }}</span>
                      </div>
                      <div class="text-xs text-on-surface-variant mt-0.5">{{ candidate.school }} · {{ candidate.major }} ({{ candidate.year }})</div>
                    </div>
                  </div>
                </td>
                <td class="px-5 py-4">
                  <div class="font-medium text-on-surface text-sm">{{ candidate.position }}</div>
                  <div class="text-xs text-on-surface-variant mt-0.5">{{ candidate.dept }}</div>
                </td>
                <td class="px-5 py-4">
                  <div class="flex items-center gap-2">
                    <span class="text-lg font-bold" :class="candidate.match >= 90 ? 'text-primary' : 'text-emerald-600'">{{ candidate.match }}%</span>
                    <span class="px-2 py-0.5 text-xs font-medium rounded-full" :class="candidate.match >= 95 ? 'bg-primary/10 text-primary' : 'bg-emerald-50 text-emerald-600'">
                      {{ candidate.match >= 95 ? '卓越契合' : '高度匹配' }}
                    </span>
                  </div>
                  <div class="text-xs text-on-surface-variant mt-1">{{ candidate.matchDetail }}</div>
                </td>
                <td class="px-5 py-4">
                  <div class="text-sm text-on-surface">{{ candidate.applyTime }}</div>
                  <div class="text-xs text-on-surface-variant mt-0.5">{{ candidate.applyRelative }}</div>
                </td>
                <td class="px-5 py-4">
                  <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-medium" :class="candidate.statusClass">
                    <span class="w-1.5 h-1.5 rounded-full" :class="candidate.dotClass"></span>
                    {{ candidate.status }}
                  </span>
                </td>
                <td class="px-5 py-4">
                  <div class="text-xs text-on-surface max-w-[200px]">{{ candidate.comment }}</div>
                </td>
                <td class="px-5 py-4">
                  <div class="flex items-center justify-end gap-1">
                    <button class="p-2 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="查看简历">
                      <span class="material-symbols-outlined text-lg">visibility</span>
                    </button>
                    <button @click="openProcessModal(candidate)" class="p-2 rounded-lg hover:bg-primary/10 transition-colors text-primary" title="处理流转">
                      <span class="material-symbols-outlined text-lg">open_in_new</span>
                    </button>
                    <button class="p-2 rounded-lg hover:bg-error/10 transition-colors text-error" title="不合适">
                      <span class="material-symbols-outlined text-lg">close</span>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- 分页 -->
        <div class="px-5 py-4 border-t border-surface-container-high flex flex-col sm:flex-row items-center justify-between gap-4">
          <div class="flex items-center gap-4 text-xs text-on-surface-variant">
            <span>共 <span class="font-medium text-on-surface">1,428</span> 条投递记录</span>
            <div class="flex items-center gap-2">
              <span>每页显示:</span>
              <select class="h-8 px-2 border border-surface-container-high rounded-lg text-xs bg-surface-container-lowest">
                <option>10 条/页</option>
              </select>
            </div>
          </div>
          <div class="flex items-center gap-1">
            <button class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high text-on-surface-variant disabled:opacity-40" disabled>
              <span class="material-symbols-outlined text-base">chevron_left</span>
            </button>
            <button class="w-8 h-8 flex items-center justify-center rounded-lg text-xs font-medium bg-primary text-on-primary">1</button>
            <button class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container text-xs">2</button>
            <button class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container text-xs">3</button>
            <span class="px-1 text-on-surface-variant text-xs">...</span>
            <button class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container text-xs">143</button>
            <button class="w-8 h-8 flex items-center justify-center rounded-lg border border-surface-container-high text-on-surface-variant hover:bg-surface-container">
              <span class="material-symbols-outlined text-base">chevron_right</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
  </div>

  <!-- 候选人处理模态框 -->
  <Teleport to="body">
    <div v-if="showProcessModal" class="fixed inset-0 z-[100] flex items-center justify-center p-4" @click.self="closeProcessModal">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm"></div>
      <div class="relative w-full max-w-4xl max-h-[90vh] bg-surface-container-lowest rounded-2xl shadow-2xl overflow-hidden flex flex-col">
        <!-- 模态框头部 -->
        <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high bg-surface-container-low/30">
          <div class="flex items-center gap-3">
            <div class="w-10 h-10 rounded-xl bg-primary/10 flex items-center justify-center">
              <span class="material-symbols-outlined text-primary text-xl">person_search</span>
            </div>
            <div>
              <div class="flex items-center gap-2">
                <h2 class="text-lg font-semibold text-on-surface">候选人投递处理与流转</h2>
                <span class="px-2 py-0.5 bg-primary/10 text-primary text-xs font-semibold rounded-full">校招专享通道</span>
              </div>
              <div class="flex items-center gap-4 mt-1 text-xs text-on-surface-variant">
                <span class="flex items-center gap-1">
                  <span class="w-2 h-2 rounded-full bg-amber-500"></span>
                  当前状态: 待初筛处理
                </span>
                <span>投递时间: 2025-03-08 11:24</span>
                <span>投递流水号: APP-20250308-8849</span>
              </div>
            </div>
          </div>
          <div class="flex items-center gap-2">
            <button class="w-9 h-9 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant">
              <span class="material-symbols-outlined text-lg">aspect_ratio</span>
            </button>
            <button class="w-9 h-9 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant">
              <span class="material-symbols-outlined text-lg">keyboard_arrow_up</span>
            </button>
            <button class="w-9 h-9 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant">
              <span class="material-symbols-outlined text-lg">keyboard_arrow_down</span>
            </button>
            <button @click="closeProcessModal" class="w-9 h-9 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant">
              <span class="material-symbols-outlined text-lg">close</span>
            </button>
          </div>
        </div>

        <!-- 模态框内容 -->
        <div class="flex-1 overflow-y-auto p-6 space-y-6">
          <!-- 候选人信息卡片 -->
          <div class="bg-surface-container-low rounded-xl p-5 border border-surface-container-high">
            <div class="flex items-start justify-between">
              <div class="flex items-start gap-4">
                <div class="w-14 h-14 rounded-full bg-primary flex items-center justify-center text-white font-bold text-xl shrink-0">
                  {{ selectedCandidate ? selectedCandidate.name[0] : '周' }}
                </div>
                <div>
                  <div class="flex items-center gap-2 flex-wrap">
                    <h3 class="text-xl font-bold text-on-surface">{{ selectedCandidate ? selectedCandidate.name : '周明翰' }}</h3>
                    <span class="px-2 py-0.5 bg-blue-50 text-blue-600 text-xs font-medium rounded">中共党员</span>
                    <span class="px-2 py-0.5 bg-purple-50 text-purple-600 text-xs font-medium rounded">保研免试生</span>
                    <span class="px-2 py-0.5 bg-amber-50 text-amber-600 text-xs font-medium rounded">国家奖学金获得者</span>
                  </div>
                  <p class="text-sm text-on-surface-variant mt-1">清华大学 · 软件学院 · 2025届硕士研究生（全日制统招）</p>
                  <div class="flex items-center gap-4 mt-2">
                    <div class="flex items-center gap-1.5">
                      <span class="px-2 py-0.5 bg-primary/10 text-primary text-xs font-semibold rounded">全职</span>
                      <span class="text-sm text-on-surface">意向岗位: 大模型算法工程师</span>
                    </div>
                    <div class="flex items-center gap-1.5">
                      <span class="material-symbols-outlined text-on-surface-variant text-base">payments</span>
                      <span class="text-sm text-on-surface">期望薪资: <span class="font-semibold text-primary">30k - 45k · 16薪</span></span>
                    </div>
                  </div>
                  <div class="flex items-center gap-1.5 mt-1.5">
                    <span class="material-symbols-outlined text-on-surface-variant text-base">location_on</span>
                    <span class="text-sm text-on-surface-variant">意向城市: 北京 / 杭州</span>
                  </div>
                </div>
              </div>
              <div class="flex flex-col gap-2 shrink-0">
                <button class="h-9 px-4 flex items-center gap-2 rounded-lg border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium hover:bg-surface-container transition-colors">
                  <span class="material-symbols-outlined text-base">description</span>
                  查看完整在线简历
                </button>
                <button class="h-9 px-4 flex items-center gap-2 rounded-lg border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium hover:bg-surface-container transition-colors">
                  <span class="material-symbols-outlined text-base">download</span>
                  下载附件 PDF (3.8MB)
                </button>
              </div>
            </div>
          </div>

          <!-- 第一步：选择流转处理状态 -->
          <div>
            <div class="flex items-center gap-2 mb-4">
              <span class="w-6 h-6 rounded-full bg-primary text-white text-xs font-bold flex items-center justify-center">1</span>
              <h3 class="text-sm font-semibold text-on-surface">第一步：选择流转处理状态</h3>
              <span class="text-xs text-on-surface-variant ml-auto">选择后系统将根据相应的招聘流转自动化规则</span>
            </div>
            <div class="grid grid-cols-5 gap-3">
              <button v-for="flow in flowOptions" :key="flow.key" @click="processFlow = flow.key"
                class="flex flex-col items-center gap-2 p-4 rounded-xl border-2 transition-all"
                :class="processFlow === flow.key ? 'border-primary bg-primary text-white' : 'border-surface-container-high bg-surface-container-lowest text-on-surface hover:border-gray-300'">
                <span class="material-symbols-outlined text-2xl">{{ flow.icon }}</span>
                <span class="text-sm font-medium">{{ flow.label }}</span>
              </button>
            </div>
          </div>

          <!-- 面试安排配置单 -->
          <div v-if="processFlow === 'arrange_interview'" class="bg-surface-container-low rounded-xl border border-surface-container-high">
            <div class="flex items-center justify-between px-5 py-4 border-b border-surface-container-high">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-lg">event_note</span>
                <h3 class="text-sm font-semibold text-on-surface">面试安排配置单</h3>
                <span class="px-2 py-0.5 bg-primary/10 text-primary text-xs font-semibold rounded-full">系统将同步推送候选人日历</span>
              </div>
              <span class="text-xs text-on-surface-variant">支持一键生成腾讯会议/平台面试</span>
            </div>
            <div class="p-5 space-y-5">
              <div class="grid grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-on-surface mb-3">面试考核轮次 *</label>
                  <div class="grid grid-cols-2 gap-2">
                    <button v-for="round in interviewRounds" :key="round.key" @click="interviewForm.round = round.key"
                      class="px-4 py-3 rounded-xl border-2 text-sm font-medium transition-all text-left"
                      :class="interviewForm.round === round.key ? 'border-primary bg-primary/5 text-primary' : 'border-surface-container-high text-on-surface hover:border-gray-300'">
                      {{ round.label }}
                    </button>
                  </div>
                </div>
                <div>
                  <label class="block text-sm font-medium text-on-surface mb-3">面试沟通形式 *</label>
                  <div class="space-y-2">
                    <label class="flex items-start gap-3 p-4 rounded-xl border-2 cursor-pointer transition-all"
                      :class="interviewForm.format === 'platform_video' ? 'border-primary bg-primary/5' : 'border-surface-container-high hover:border-gray-300'">
                      <input v-model="interviewForm.format" type="radio" value="platform_video" class="mt-0.5" />
                      <div class="flex-1">
                        <div class="flex items-center gap-2">
                          <span class="material-symbols-outlined text-primary text-lg">videocam</span>
                          <span class="text-sm font-semibold text-primary">平台内置视频考场（推荐）</span>
                        </div>
                        <p class="text-xs text-on-surface-variant mt-1">支持在线白板解释、代码协同与面试录制</p>
                      </div>
                    </label>
                    <label class="flex items-start gap-3 p-4 rounded-xl border-2 cursor-pointer transition-all border-surface-container-high hover:border-gray-300">
                      <input v-model="interviewForm.format" type="radio" value="offline" class="mt-0.5" />
                      <div class="flex-1">
                        <div class="flex items-center gap-2">
                          <span class="material-symbols-outlined text-on-surface-variant text-lg">meeting_room</span>
                          <span class="text-sm font-medium text-on-surface">线下到面（校招展位/园区会议室）</span>
                        </div>
                      </div>
                    </label>
                  </div>
                </div>
              </div>

              <div class="grid grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-on-surface mb-2">面试日期与时间区间 *</label>
                  <div class="flex items-center gap-2">
                    <div class="flex-1 relative">
                      <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">calendar_today</span>
                      <input v-model="interviewForm.date" type="text" class="w-full h-11 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
                    </div>
                    <input v-model="interviewForm.timeStart" type="time" class="h-11 px-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none" />
                    <span class="text-on-surface-variant">-</span>
                    <input v-model="interviewForm.timeEnd" type="time" class="h-11 px-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none" />
                  </div>
                  <p class="text-xs text-on-surface-variant mt-1.5">推荐开启校招高峰期或常规上班时间</p>
                </div>
                <div>
                  <label class="block text-sm font-medium text-on-surface mb-2">考场编号 / 会议室接入点 *</label>
                  <div class="flex items-center gap-2">
                    <div class="flex-1 relative">
                      <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">door_front</span>
                      <input v-model="interviewForm.roomId" type="text" class="w-full h-11 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
                    </div>
                    <input v-model="interviewForm.roomPassword" type="text" placeholder="密码" class="w-24 h-11 px-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none" />
                  </div>
                  <p class="text-xs text-on-surface-variant mt-1.5">面试开始前15分钟系统自动下发线上/入场密码</p>
                </div>
              </div>

              <div class="grid grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-on-surface mb-2">主试考官 / 业务负责人 *</label>
                  <div class="relative">
                    <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">person</span>
                    <input v-model="interviewForm.interviewer" type="text" class="w-full h-11 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
                  </div>
                </div>
                <div>
                  <label class="block text-sm font-medium text-on-surface mb-2">考务联系电话 / 应急热线 *</label>
                  <div class="relative">
                    <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">phone</span>
                    <input v-model="interviewForm.contactPhone" type="tel" class="w-full h-11 pl-10 pr-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm focus:outline-none focus:ring-2 focus:ring-primary/20" />
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 操作日志提示 -->
          <div class="flex items-center gap-2 text-xs text-on-surface-variant">
            <span class="material-symbols-outlined text-sm text-primary">info</span>
            操作日志将会全加密记入校园招聘可信链途
          </div>
        </div>

        <!-- 模态框底部 -->
        <div class="flex items-center justify-end gap-3 px-6 py-4 border-t border-surface-container-high bg-surface-container-low/30">
          <button @click="closeProcessModal" class="h-11 px-6 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface font-medium hover:bg-surface-container transition-colors text-sm">取消</button>
          <button @click="saveInterviewDraft" class="h-11 px-6 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface font-medium hover:bg-surface-container transition-colors text-sm flex items-center gap-2">
            <span class="material-symbols-outlined text-lg">save</span>
            暂存面试
          </button>
          <button @click="confirmProcess" class="h-11 px-6 rounded-xl bg-primary text-white font-semibold hover:bg-primary-container transition-colors shadow-sm flex items-center gap-2 text-sm">
            <span class="material-symbols-outlined text-lg">send</span>
            确认并发送面试邀请通知
          </button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import { ref } from 'vue'

const toast = ref('')
const showProcessModal = ref(false)
const selectedCandidate = ref(null)
const processFlow = ref('arrange_interview')

const interviewForm = ref({
  round: 'online_tech_2',
  format: 'platform_video',
  date: '2025-03-12 (周三)',
  timeStart: '15:30',
  timeEnd: '16:30',
  roomId: 'A-302',
  roomPassword: '8820',
  interviewer: '张明硕 (技术事业部负责人 / 首席架构师)',
  contactPhone: '+86 186 1024 8890'
})

const flowOptions = [
  { key: 'pending', label: '待查看', icon: 'mark_email_unread' },
  { key: 'read', label: '标为已阅', icon: 'drafts' },
  { key: 'arrange_interview', label: '安排面试邀约', icon: 'event_available' },
  { key: 'offer', label: '已录用/发Offer', icon: 'check_circle' },
  { key: 'reject', label: '不合适/入库', icon: 'archive' }
]

const interviewRounds = [
  { key: 'tech_screen', label: '技术初筛' },
  { key: 'online_tech_2', label: '线上技术二面' },
  { key: 'hr_final', label: 'HR综合终面' },
  { key: 'onsite', label: '线下到场专场' }
]

const candidates = ref([
  {
    id: 1, name: '周明翰', schoolTag: '985/211', eduLevel: '硕士', school: '清华大学', major: '软件工程', year: '2025届',
    position: '大模型算法工程师', dept: '北京 · 算法预研组', match: 96, matchDetail: '算法金牌·ACM区域金牌',
    applyTime: '今日 11:24', applyRelative: '1小时前', status: '待处理 (初筛)',
    statusClass: 'bg-primary/10 text-primary', dotClass: 'bg-primary',
    comment: '核心竞争金牌，顶尖工程能力，推荐立即直推一面', avatarColor: '#1D4ED8'
  },
  {
    id: 2, name: '李思琪', schoolTag: '985/211', eduLevel: '本科', school: '北京航空航天大学', major: '计算机系', year: '2025届',
    position: '前端开发实习生（可转正）', dept: '杭州 · 基础业务线', match: 92, matchDetail: '高度匹配',
    applyTime: '今日 09:40', applyRelative: '3小时前', status: '面试邀约中',
    statusClass: 'bg-amber-50 text-amber-600', dotClass: 'bg-amber-500',
    comment: '已发出线上一面邀请函，待选时段', avatarColor: '#0EA5E9'
  },
  {
    id: 3, name: '赵程安', schoolTag: '985/211', eduLevel: '硕士', school: '上海交通大学', major: '自动化系', year: '2025届',
    position: '大模型算法工程师', dept: '上海 · 基础模型研发组', match: 95, matchDetail: 'NeurIPS 2024 一作·强化学习方向',
    applyTime: '昨日 17:15', applyRelative: '昨天', status: '二面进行中',
    statusClass: 'bg-blue-50 text-blue-600', dotClass: 'bg-blue-500',
    comment: '一面技术官评级 A+，逻辑与推导极深', avatarColor: '#0037B0'
  },
  {
    id: 4, name: '孙楚越', schoolTag: '985/211', eduLevel: '硕士', school: '华中科技大学', major: '人工智能学院', year: '2025届',
    position: '计算机视觉研究员', dept: '深圳 · 智能驾驶组', match: 94, matchDetail: 'CVPR顶会录用·3D感知前沿',
    applyTime: '前天 18:30', applyRelative: '2天前', status: '已发Offer待签约',
    statusClass: 'bg-emerald-50 text-emerald-600', dotClass: 'bg-emerald-500',
    comment: '已下发薪酬Package意向函，双方沟通良好', avatarColor: '#059669'
  },
  {
    id: 5, name: '王嘉宁', schoolTag: '211', eduLevel: '本科', school: '北京邮电大学', major: '通信工程', year: '2025届',
    position: '后端开发工程师', dept: '北京 · 支付系统部', match: 82, matchDetail: '基本匹配',
    applyTime: '3天前', applyRelative: '10月24日', status: '不合适 / 人才库',
    statusClass: 'bg-gray-100 text-gray-600', dotClass: 'bg-gray-400',
    comment: '基础扎实但岗位HC已满，已归档备选池', avatarColor: '#64748B'
  }
])

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2000)
}

function openProcessModal(candidate) {
  selectedCandidate.value = candidate
  showProcessModal.value = true
  processFlow.value = 'arrange_interview'
}

function closeProcessModal() {
  showProcessModal.value = false
  selectedCandidate.value = null
}

function confirmProcess() {
  showToast('面试邀请已发送')
  closeProcessModal()
}

function saveInterviewDraft() {
  showToast('面试安排已暂存')
}
</script>
