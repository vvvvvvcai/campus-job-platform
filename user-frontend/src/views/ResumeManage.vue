<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-[1400px] mx-auto px-4 md:px-6 lg:px-10 py-8">
      <!-- Page Header -->
      <div class="flex items-center justify-between mb-6">
        <div>
          <div class="flex items-center gap-3">
            <h1 class="text-2xl md:text-3xl font-bold text-[var(--on-surface)]">我的简历档案库</h1>
            <span class="px-3 py-1 bg-blue-50 text-blue-600 text-xs font-semibold rounded-full border border-blue-100">3/5 已创建</span>
          </div>
          <p class="text-sm text-[var(--on-surface-variant)] mt-1">支持维护多份针对性简历，根据算法、前端或央国企企精准投递，告别「千人一面」，有效提升面试邀约率。</p>
        </div>
        <div class="flex items-center gap-3">
          <button class="px-4 py-2.5 bg-white text-[var(--on-surface)] rounded-xl text-sm font-medium border border-gray-200 hover:bg-gray-50 transition-colors flex items-center gap-2 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">download</span>
            一键导出 PDF
          </button>
          <button @click="createResume" class="px-5 py-2.5 bg-[var(--primary)] text-white rounded-xl text-sm font-semibold hover:bg-[var(--primary-container)] transition-colors flex items-center gap-2 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">add</span>
            新建简历
          </button>
        </div>
      </div>

      <div class="flex gap-6">
        <!-- Left Sidebar - Resume List -->
        <div class="w-[380px] shrink-0 space-y-4">
          <!-- Section Header -->
          <div class="flex items-center justify-between">
            <span class="text-sm font-bold text-[var(--on-surface)]">简历列表</span>
            <span class="text-xs text-[var(--on-surface-variant)]">点击卡片切换右侧预览</span>
          </div>

          <!-- Resume Cards -->
          <div v-for="resume in resumes" :key="resume.id"
            @click="selectedResume = resume"
            :class="['rounded-2xl border p-5 cursor-pointer transition-all relative',
              selectedResume?.id === resume.id
                ? 'bg-white border-[var(--primary)] ring-2 ring-[var(--primary)]/10 shadow-sm'
                : 'bg-white border-gray-100 hover:border-gray-200 shadow-sm hover:shadow']">
            <!-- 使用中 Badge -->
            <div v-if="selectedResume?.id === resume.id" class="absolute -top-2 -right-2 px-3 py-1 bg-[var(--primary)] text-white text-[10px] font-bold rounded-full shadow-sm">
              使用中
            </div>
            <!-- Tags -->
            <div class="flex items-center gap-2 mb-3">
              <span v-for="tag in resume.tags" :key="tag.label"
                :class="['px-2.5 py-0.5 rounded-full text-[10px] font-semibold border', tag.class]">
                {{ tag.label }}
              </span>
            </div>
            <!-- Title -->
            <h3 class="font-bold text-[var(--on-surface)] text-sm mb-3">{{ resume.title }}</h3>
            <!-- Completeness Bar -->
            <div class="flex items-center justify-between mb-2">
              <span class="text-[11px] text-[var(--on-surface-variant)]">简历完整度</span>
              <span class="text-xs font-bold" :class="resume.completeness >= 90 ? 'text-[var(--primary)]' : resume.completeness >= 70 ? 'text-emerald-500' : 'text-amber-500'">{{ resume.completeness }}%</span>
            </div>
            <div class="h-1.5 bg-gray-100 rounded-full overflow-hidden mb-3">
              <div :class="['h-full rounded-full transition-all',
                resume.completeness >= 90 ? 'bg-[var(--primary)]' : resume.completeness >= 70 ? 'bg-emerald-500' : 'bg-amber-500']"
                :style="{ width: resume.completeness + '%' }"></div>
            </div>
            <!-- Date -->
            <div class="flex items-center justify-between mb-3">
              <span class="text-[11px] text-[var(--on-surface-variant)] flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">schedule</span>
                {{ resume.updatedAt }}
              </span>
              <span v-if="resume.isDefault" class="text-[11px] text-[var(--primary)] flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">check_circle</span>
                默认简历不可删除
              </span>
            </div>
            <!-- Actions -->
            <div class="flex items-center justify-between pt-3 border-t border-gray-50">
              <span v-if="selectedResume?.id === resume.id" class="text-[11px] text-[var(--primary)] font-medium flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">visibility</span>
                当前显示中
              </span>
              <div class="flex items-center gap-3 ml-auto">
                <router-link to="/resume/editor" class="text-[11px] text-[var(--on-surface-variant)] hover:text-[var(--primary)] flex items-center gap-1 transition-colors">
                  <span class="material-symbols-outlined text-[14px]">edit</span>
                  编辑内容
                </router-link>
                <button class="text-[11px] text-[var(--on-surface-variant)] hover:text-[var(--primary)] flex items-center gap-1 transition-colors">
                  <span class="material-symbols-outlined text-[14px]">download</span>
                </button>
              </div>
            </div>
          </div>

          <!-- New Resume Card -->
          <button @click="createResume" class="w-full rounded-2xl border-2 border-dashed border-gray-200 p-6 flex flex-col items-center gap-2 hover:border-[var(--primary)]/30 hover:bg-blue-50/30 transition-all">
            <span class="material-symbols-outlined text-3xl text-gray-300">add_circle</span>
            <span class="text-sm font-medium text-[var(--on-surface-variant)]">新建简历版本</span>
            <span class="text-[11px] text-gray-400">还可创建 {{ 5 - resumes.length }} 份专用简历（上限 5 份）</span>
          </button>

          <!-- Advisor Tip Box -->
          <div class="bg-blue-50/60 rounded-2xl border border-blue-100 p-5">
            <div class="flex items-center gap-2 mb-2">
              <span class="material-symbols-outlined text-[var(--primary)] text-lg">lightbulb</span>
              <span class="text-sm font-bold text-[var(--on-surface)]">就业导师建议</span>
            </div>
            <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed">算法类岗位重点考察顶会论文、竞赛名次与模型调优实际业务指标；央国企则注重专业课程成绩排位与综合素质认证。建议保持 2 份以上差异化版本！</p>
          </div>
        </div>

        <!-- Right Panel - Resume Preview -->
        <div class="flex-1 min-w-0">
          <div v-if="selectedResume" class="bg-white rounded-2xl border border-gray-100 shadow-sm">
            <!-- Preview Header -->
            <div class="flex items-center justify-between px-6 py-4 border-b border-gray-100">
              <div class="flex items-center gap-3">
                <span class="w-2 h-2 rounded-full bg-[var(--primary)]"></span>
                <span class="text-sm font-medium text-[var(--on-surface)]">实时高清真排版预览（标准 A4 · 紧凑单页）</span>
              </div>
              <div class="flex items-center gap-2 text-xs text-[var(--on-surface-variant)]">
                <span>缩放：100%</span>
              </div>
            </div>

            <!-- Resume Content -->
            <div class="p-8 max-h-[700px] overflow-y-auto">
              <div class="max-w-3xl mx-auto">
                <!-- Profile Header -->
                <div class="flex items-start gap-6 mb-6 pb-6 border-b border-gray-200">
                  <!-- Avatar -->
                  <div class="w-20 h-20 rounded-xl bg-gradient-to-br from-blue-100 to-blue-50 flex items-center justify-center border-2 border-blue-100 shrink-0 overflow-hidden">
                    <span class="text-2xl text-blue-300 font-bold">林</span>
                  </div>
                  <!-- Info -->
                  <div class="flex-1">
                    <div class="flex items-center gap-3 mb-1">
                      <h2 class="text-xl font-bold text-[var(--on-surface)]">林晨</h2>
                      <span class="px-2.5 py-0.5 bg-emerald-50 text-emerald-700 text-[10px] font-semibold rounded-full border border-emerald-100">在校·积极求职中（随时到岗）</span>
                    </div>
                    <p class="text-sm font-semibold text-[var(--primary)] mb-2">求职意向：大模型算法工程师 / 全栈架构研发</p>
                    <p class="text-xs text-[var(--on-surface-variant)] mb-2">北京科技重点大学 · 计算机科学与技术 · 2025届统招工学硕士</p>
                    <div class="flex items-center gap-4 text-xs text-[var(--on-surface-variant)]">
                      <span class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px]">phone</span> 188****6820</span>
                      <span class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px]">email</span> linchen.cs@campus.edu.cn</span>
                      <span class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px]">location_on</span> 北京·海淀区</span>
                    </div>
                  </div>
                  <!-- Score Circle -->
                  <div class="relative w-16 h-16 shrink-0">
                    <svg class="w-full h-full -rotate-90" viewBox="0 0 64 64">
                      <circle cx="32" cy="32" r="28" fill="none" stroke="#e5e7eb" stroke-width="4" />
                      <circle cx="32" cy="32" r="28" fill="none" stroke="var(--primary)" stroke-width="4" stroke-linecap="round"
                        :stroke-dasharray="`${175.93 * 96 / 100} 175.93`" />
                    </svg>
                    <div class="absolute inset-0 flex flex-col items-center justify-center">
                      <span class="text-lg font-bold text-[var(--primary)]">96</span>
                    </div>
                    <p class="text-[9px] text-[var(--on-surface-variant)] text-center -mt-0.5">算法岗匹配</p>
                  </div>
                </div>

                <!-- Summary Bar -->
                <div class="grid grid-cols-3 gap-4 bg-gray-50 rounded-xl p-4 mb-8">
                  <div>
                    <span class="text-[11px] text-[var(--on-surface-variant)]">求职务性质</span>
                    <p class="text-xs font-semibold text-[var(--on-surface)] mt-1">2025届全职校招 / 实习转正</p>
                  </div>
                  <div>
                    <span class="text-[11px] text-[var(--on-surface-variant)]">期望城市</span>
                    <p class="text-xs font-semibold text-[var(--on-surface)] mt-1">北京、上海、深圳（支持驻场）</p>
                  </div>
                  <div>
                    <span class="text-[11px] text-[var(--on-surface-variant)]">期望薪资范围</span>
                    <p class="text-xs font-semibold text-[var(--primary)] mt-1">25k - 40k / 月（可面议）</p>
                  </div>
                </div>

                <!-- Education Background -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    教育背景
                  </h3>
                  <div class="space-y-4">
                    <!-- School 1 -->
                    <div class="flex items-start justify-between">
                      <div>
                        <div class="flex items-center gap-2">
                          <span class="text-sm font-bold text-[var(--on-surface)]">北京科技重点大学</span>
                          <span class="px-2 py-0.5 bg-blue-50 text-blue-600 text-[10px] font-semibold rounded border border-blue-100">双一流/985高校</span>
                        </div>
                        <p class="text-xs text-[var(--on-surface-variant)] mt-1">计算机学院·计算机科学与技术（学术硕士）</p>
                      </div>
                      <span class="text-xs text-[var(--on-surface-variant)]">2022.09 — 2025.06（硕士毕业）</span>
                    </div>
                    <div class="flex items-center gap-6 text-xs text-[var(--on-surface-variant)]">
                      <span>专业成绩：<span class="font-semibold text-[var(--on-surface)]">前 5%</span></span>
                      <span>荣誉：<span class="font-semibold text-[var(--on-surface)]">国家励志奖学金、校级一等奖学业奖学金</span></span>
                    </div>
                    <!-- School 2 -->
                    <div class="flex items-start justify-between pt-2 border-t border-gray-50">
                      <div>
                        <div class="flex items-center gap-2">
                          <span class="text-sm font-bold text-[var(--on-surface)]">北京邮电大学</span>
                          <span class="px-2 py-0.5 bg-amber-50 text-amber-700 text-[10px] font-semibold rounded border border-amber-100">教育部直属高校</span>
                        </div>
                        <p class="text-xs text-[var(--on-surface-variant)] mt-1">计算机与软件学院·软件工程（工学学士）</p>
                      </div>
                      <span class="text-xs text-[var(--on-surface-variant)]">2018.09 — 2022.06（学士）</span>
                    </div>
                    <div class="text-xs text-[var(--on-surface-variant)]">
                      <span>综合绩点：<span class="font-semibold text-[var(--on-surface)]">3.82 / 4.0</span></span>
                      <span class="ml-4">荣誉：<span class="font-semibold text-[var(--on-surface)]">北京市优秀毕业生</span></span>
                    </div>
                  </div>
                </div>

                <!-- Professional Skills -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    专业技术栈与工具
                  </h3>
                  <div class="flex flex-wrap gap-2">
                    <span v-for="skill in skills" :key="skill"
                      class="px-3 py-1.5 bg-blue-50 text-blue-700 text-xs font-medium rounded-lg border border-blue-100">
                      {{ skill }}
                    </span>
                  </div>
                </div>

                <!-- Core Internship Experience -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    核心实习经历
                  </h3>
                  <div class="space-y-6">
                    <!-- Intern 1 -->
                    <div>
                      <div class="flex items-start justify-between mb-2">
                        <div>
                          <div class="flex items-center gap-2">
                            <span class="text-sm font-bold text-[var(--on-surface)]">智维未来人工智能研究院</span>
                            <span class="px-2 py-0.5 bg-emerald-50 text-emerald-700 text-[10px] font-semibold rounded border border-emerald-100">大模型算法实习生</span>
                          </div>
                        </div>
                        <span class="text-xs text-[var(--on-surface-variant)]">2024.06 — 至今（北京）</span>
                      </div>
                      <ul class="space-y-1.5">
                        <li class="text-xs text-[var(--on-surface-variant)] flex items-start gap-2">
                          <span class="w-1 h-1 rounded-full bg-[var(--primary)] mt-1.5 shrink-0"></span>
                          主导基于 Llama-3 与 Qwen-2 百亿量级多模态模型的指令微调（SFT）数据工程构建，清洗高价值多轮对齐语料 450万+ 条。
                        </li>
                        <li class="text-xs text-[var(--on-surface-variant)] flex items-start gap-2">
                          <span class="w-1 h-1 rounded-full bg-[var(--primary)] mt-1.5 shrink-0"></span>
                          结合 DeepSpeed ZeRO-3 与 FlashAttention-2 优化集群分布式通信拓扑，显存占用降低 <span class="font-semibold text-[var(--primary)]">28%</span>，训练集群吞吐提升 <span class="font-semibold text-[var(--primary)]">35%</span>。
                        </li>
                        <li class="text-xs text-[var(--on-surface-variant)] flex items-start gap-2">
                          <span class="w-1 h-1 rounded-full bg-[var(--primary)] mt-1.5 shrink-0"></span>
                          引入 AWQ 4-bit 权重激活感知量化算法与 vLLM 连续批处理（Continuous Batching）推理框架，服务首字响应延迟降低至 45ms，并发能力提升 2.4 倍。
                        </li>
                      </ul>
                    </div>
                    <!-- Intern 2 -->
                    <div class="pt-4 border-t border-gray-50">
                      <div class="flex items-start justify-between mb-2">
                        <div>
                          <div class="flex items-center gap-2">
                            <span class="text-sm font-bold text-[var(--on-surface)]">字节跳动（ByteDance）</span>
                            <span class="px-2 py-0.5 bg-blue-50 text-blue-700 text-[10px] font-semibold rounded border border-blue-100">后端架构研发实习生</span>
                          </div>
                        </div>
                        <span class="text-xs text-[var(--on-surface-variant)]">2023.07 — 2023.11（北京）</span>
                      </div>
                      <ul class="space-y-1.5">
                        <li class="text-xs text-[var(--on-surface-variant)] flex items-start gap-2">
                          <span class="w-1 h-1 rounded-full bg-[var(--primary)] mt-1.5 shrink-0"></span>
                          深度参与高并发信息流推荐服务重构，基于 Go + gRPC 微服务链路设计多级本地缓存架构（LRU + Redis Cluster）。
                        </li>
                        <li class="text-xs text-[var(--on-surface-variant)] flex items-start gap-2">
                          <span class="w-1 h-1 rounded-full bg-[var(--primary)] mt-1.5 shrink-0"></span>
                          定位并治理网络 IO 阻塞热点，通过协程池精细化调度与内存逃逸优化，使得核心服务 P99 延迟由 120ms 压降至 42ms，保障千万级 QPS 稳定运行。
                        </li>
                        <li class="text-xs text-[var(--on-surface-variant)] flex items-start gap-2">
                          <span class="w-1 h-1 rounded-full bg-[var(--primary)] mt-1.5 shrink-0"></span>
                          主笔输出《微服务高可用熔断与兜底降级方案白皮书》，荣获实习生最佳工程技术分享奖。
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>

                <!-- Project Experience -->
                <div class="mb-8">
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    项目经历
                  </h3>
                  <div class="space-y-6">
                    <!-- Project 1 -->
                    <div>
                      <div class="flex items-start justify-between mb-2">
                        <div>
                          <span class="text-sm font-bold text-[var(--on-surface)]">"智绘校园"基于大模型的智能问答协同助理（国家级大学生创新创业计划）</span>
                        </div>
                        <div class="text-right shrink-0 ml-4">
                          <span class="px-2 py-0.5 bg-amber-50 text-amber-700 text-[10px] font-semibold rounded border border-amber-100">核心主导</span>
                          <span class="text-xs text-[var(--on-surface-variant)] ml-2">2023.12 — 2024.05</span>
                        </div>
                      </div>
                      <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed">针对高校庞杂办事指南与校级公文检索痛点，设计多模态 RAG（检索增强生成）检索流水线，构建基于 Milvus 向量库与 BGE-Reranker 的二次重排机制，准确率达 <span class="font-semibold text-[var(--primary)]">94.2%</span>。服务目前已全校试运行，累计解答师生政策咨询超 20 万次。</p>
                    </div>
                    <!-- Project 2 -->
                    <div class="pt-4 border-t border-gray-50">
                      <div class="flex items-start justify-between mb-2">
                        <div>
                          <span class="text-sm font-bold text-[var(--on-surface)]">ACM-ICPC 算法训练平台分布式沙箱评测引擎</span>
                        </div>
                        <div class="text-right shrink-0 ml-4">
                          <span class="px-2 py-0.5 bg-purple-50 text-purple-700 text-[10px] font-semibold rounded border border-purple-100">独立开发者</span>
                          <span class="text-xs text-[var(--on-surface-variant)] ml-2">2022.10 — 2023.04</span>
                        </div>
                      </div>
                      <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed">使用 Linux cgroups 及 seccomp 构建微秒级安全沙箱隔离环境，支持 C++ / Java / Python 多语言判题与并发资源压制。系统开源至 GitHub 获得 <span class="font-semibold text-[var(--primary)]">1.2k+ Star</span>，被全国多所高校集训队采用。</p>
                    </div>
                  </div>
                </div>

                <!-- Awards & Certifications -->
                <div>
                  <h3 class="text-sm font-bold text-[var(--on-surface)] mb-4 flex items-center gap-2">
                    <span class="w-1 h-4 bg-[var(--primary)] rounded-full"></span>
                    重要荣誉与语言资质
                  </h3>
                  <div class="grid grid-cols-2 gap-3">
                    <div class="flex items-center gap-3 bg-gray-50 rounded-xl p-3">
                      <span class="text-lg">🏆</span>
                      <div>
                        <p class="text-xs font-semibold text-[var(--on-surface)]">ACM-ICPC 国际大学生程序设计竞赛 亚洲区域赛金牌（2021）</p>
                      </div>
                    </div>
                    <div class="flex items-center gap-3 bg-gray-50 rounded-xl p-3">
                      <span class="text-lg">🏆</span>
                      <div>
                        <p class="text-xs font-semibold text-[var(--on-surface)]">全国研究生数学建模竞赛 国家一等奖（2023）</p>
                      </div>
                    </div>
                    <div class="flex items-center gap-3 bg-gray-50 rounded-xl p-3">
                      <span class="text-lg">📝</span>
                      <div>
                        <p class="text-xs font-semibold text-[var(--on-surface)]">大学英语六级（CET-6）：612 分（流利查阅外文前沿论文）</p>
                      </div>
                    </div>
                    <div class="flex items-center gap-3 bg-gray-50 rounded-xl p-3">
                      <span class="text-lg">💻</span>
                      <div>
                        <p class="text-xs font-semibold text-[var(--on-surface)]">计算机技术与软件专业资格：系统分析师（高级）</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Empty State -->
          <div v-else class="bg-white rounded-2xl border border-gray-100 shadow-sm p-12 text-center">
            <span class="material-symbols-outlined text-5xl text-gray-200 mb-4 block">description</span>
            <p class="text-sm text-[var(--on-surface-variant)]">选择一份简历查看预览</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const selectedResume = ref(null)

const resumes = ref([
  {
    id: 1,
    title: '2025届计算机研发与算法专向简历',
    updatedAt: '2025-03-08 14:20',
    completeness: 95,
    isDefault: true,
    tags: [{ label: '默认投递', class: 'bg-[var(--primary)]/10 text-[var(--primary)] border-[var(--primary)]/20' }]
  },
  {
    id: 2,
    title: '互联网全栈 / 前端开发通用简历',
    updatedAt: '2025-03-02 09:15',
    completeness: 88,
    isDefault: false,
    tags: [{ label: '研发岗专用', class: 'bg-emerald-50 text-emerald-700 border-emerald-200' }]
  },
  {
    id: 3,
    title: '央国企及科研院所投递专版（附政审与成绩单）',
    updatedAt: '2025-02-25 18:40',
    completeness: 90,
    isDefault: false,
    tags: [{ label: '院所专版', class: 'bg-amber-50 text-amber-700 border-amber-200' }, { label: '央国企及科研院所投递专版', class: 'bg-purple-50 text-purple-700 border-purple-200' }]
  }
])

const skills = [
  'Python（熟练 / 核心研发语言）',
  'PyTorch & DeepSpeed',
  'C++ 17（高并发与底层系统）',
  '大语言模型微调（SFT / LoRA / RLHF）',
  'vLLM / TensorRT-LLM 加速推理',
  'Linux 内核优化 & Docker / K8s',
  '分布式通信 NCCL & Ray',
  'Gitflow / CI·CD Pipeline'
]

function createResume() {
  const newResume = {
    id: Date.now(),
    title: '新简历版本',
    updatedAt: '刚刚',
    completeness: 10,
    isDefault: false,
    tags: [{ label: '新建', class: 'bg-gray-100 text-gray-500 border-gray-200' }]
  }
  resumes.value.unshift(newResume)
  selectedResume.value = newResume
}
</script>
