<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-[1400px] mx-auto px-4 md:px-6 lg:px-10 py-6">
      <!-- Header -->
      <div class="flex items-center justify-between mb-4">
        <div>
          <h1 class="text-2xl font-bold text-[var(--on-surface)]">编辑求职简历</h1>
        </div>
        <div class="flex items-center gap-3">
          <span class="text-xs text-[var(--on-surface-variant)] flex items-center gap-1.5">
            <span class="material-symbols-outlined text-sm text-[var(--primary)]">cloud_done</span>
            已于 00:38 自动保存为草稿
          </span>
          <button class="px-4 py-2 bg-white text-[var(--on-surface)] rounded-xl text-sm font-medium border border-gray-200 hover:bg-gray-50 transition-colors flex items-center gap-2 shadow-sm">
            <span class="material-symbols-outlined text-[16px]">visibility</span>
            预览效果
          </button>
          <button @click="handleSave" :disabled="saving" class="px-5 py-2 bg-[var(--primary)] text-white rounded-xl text-sm font-semibold hover:bg-[var(--primary-container)] transition-colors flex items-center gap-2 shadow-sm disabled:opacity-50">
            <span class="material-symbols-outlined text-[16px]">{{ saving ? 'hourglass_top' : 'check_circle' }}</span>
            {{ saving ? '保存中...' : '完成并保存简历' }}
          </button>
        </div>
      </div>

      <!-- Progress Stepper -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-4 mb-6">
        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center gap-6">
            <div v-for="(step, index) in steps" :key="step.key" class="flex items-center gap-2">
              <span :class="['w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold shrink-0',
                index < activeStep ? 'bg-[var(--primary)] text-white' : index === activeStep ? 'bg-[var(--primary)] text-white' : 'bg-gray-100 text-gray-400']">
                <span v-if="index < activeStep" class="material-symbols-outlined text-sm">check</span>
                <span v-else>{{ index + 1 }}</span>
              </span>
              <div>
                <span :class="['text-xs font-medium block', index <= activeStep ? 'text-[var(--on-surface)]' : 'text-gray-400']">{{ step.label }}</span>
                <span v-if="index < activeStep" class="text-[10px] text-[var(--primary)]">已完成</span>
                <span v-else-if="index === activeStep" class="text-[10px] text-[var(--primary)]">● 编辑中</span>
                <span v-else class="text-[10px] text-gray-400">待补充</span>
              </div>
            </div>
          </div>
        </div>
        <!-- Progress Bar -->
        <div class="flex items-center gap-4">
          <span class="text-xs text-[var(--on-surface-variant)] flex items-center gap-1.5">
            <span class="material-symbols-outlined text-sm text-[var(--primary)]">info</span>
            简历完善度：<span class="font-bold text-[var(--primary)]">88%</span>（建议补充第6步「项目经历」深度量化指标，以获得更多头部大厂与科研所 HR 青睐）
          </span>
          <div class="flex-1 h-2 bg-gray-100 rounded-full overflow-hidden">
            <div class="h-full bg-[var(--primary)] rounded-full transition-all" style="width: 88%"></div>
          </div>
          <span class="text-sm font-bold text-[var(--primary)]">88%</span>
        </div>
      </div>

      <!-- Main Content -->
      <div class="flex gap-6">
        <!-- Left Form Area -->
        <div class="flex-1 space-y-6">
          <!-- 基本信息与个人标签 -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm">
            <div class="flex items-center justify-between p-5 pb-0">
              <div class="flex items-center gap-2">
                <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
                <span class="material-symbols-outlined text-[var(--primary)]">person</span>
                <h3 class="font-bold text-[var(--on-surface)]">基本信息与个人标签</h3>
                <span class="px-2 py-0.5 bg-[var(--primary)]/10 text-[var(--primary)] text-[10px] font-semibold rounded-full border border-[var(--primary)]/20">已认证</span>
              </div>
            </div>
            <div class="p-5">
              <!-- Profile Card -->
              <div class="bg-gray-50 rounded-xl p-4 flex items-start gap-4 mb-5">
                <div class="relative">
                  <div class="w-16 h-16 rounded-xl bg-gradient-to-br from-blue-100 to-blue-50 flex items-center justify-center border-2 border-blue-100 overflow-hidden">
                    <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=80&h=80&fit=crop&crop=face" class="w-full h-full object-cover" />
                  </div>
                  <button class="absolute -bottom-1 -right-1 w-5 h-5 bg-[var(--primary)] rounded-full flex items-center justify-center shadow-sm">
                    <span class="material-symbols-outlined text-white text-[10px]">photo_camera</span>
                  </button>
                </div>
                <div class="flex-1">
                  <div class="flex items-center gap-2 mb-1">
                    <h4 class="font-bold text-[var(--on-surface)]">林晨</h4>
                    <span class="px-2 py-0.5 bg-blue-50 text-blue-700 text-[10px] font-semibold rounded">2025届硕士</span>
                    <span class="px-2 py-0.5 bg-gray-100 text-gray-600 text-[10px] font-semibold rounded">中共党员</span>
                  </div>
                  <p class="text-xs text-[var(--on-surface-variant)]">求职意向：全职算法研发 / 大模型架构工程师（随时到岗）</p>
                </div>
                <button class="text-xs text-[var(--primary)] hover:underline">更换照片</button>
              </div>
              <!-- Contact Fields -->
              <div class="grid grid-cols-3 gap-4">
                <div>
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">手机号码</label>
                  <input v-model="form.phone" type="tel" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
                </div>
                <div>
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">教育邮箱 (Edu Mail)</label>
                  <input v-model="form.email" type="email" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
                </div>
                <div>
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">出生年月 / 政治面貌</label>
                  <input v-model="form.birthDate" type="text" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
                </div>
              </div>
            </div>
          </div>

          <!-- 求职意向 -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center gap-2">
                <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
                <span class="material-symbols-outlined text-[var(--primary)]">target</span>
                <h3 class="font-bold text-[var(--on-surface)]">求职意向</h3>
              </div>
              <div class="flex items-center gap-3">
                <button class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">edit</span>修改意向
                </button>
                <button class="text-xs text-gray-400 hover:text-[var(--on-surface-variant)] flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">visibility_off</span>隐藏模块
                </button>
              </div>
            </div>
            <div class="grid grid-cols-3 gap-4">
              <div class="bg-[#eef2ff] rounded-xl p-4">
                <span class="text-[11px] text-[var(--on-surface-variant)]">期望城市</span>
                <div class="flex flex-wrap gap-1.5 mt-2">
                  <span class="px-2.5 py-1 bg-white border border-[var(--primary)] text-[var(--primary)] text-xs font-medium rounded-lg">北京（首选）</span>
                  <span class="px-2.5 py-1 bg-white/60 text-gray-600 text-xs font-medium rounded-lg">上海</span>
                  <span class="px-2.5 py-1 bg-white/60 text-gray-600 text-xs font-medium rounded-lg">深圳</span>
                </div>
              </div>
              <div class="bg-[#eef2ff] rounded-xl p-4">
                <span class="text-[11px] text-[var(--on-surface-variant)]">目标行业</span>
                <div class="flex flex-wrap gap-1.5 mt-2">
                  <span class="px-2.5 py-1 bg-white text-gray-700 text-xs font-medium rounded-lg">互联网 AI / 大模型</span>
                  <span class="px-2.5 py-1 bg-white text-gray-700 text-xs font-medium rounded-lg">高端硬科技智能研发</span>
                </div>
              </div>
              <div class="bg-[#eef2ff] rounded-xl p-4">
                <span class="text-[11px] text-[var(--on-surface-variant)]">期望月薪（税前）</span>
                <p class="text-sm font-bold text-[#1a56db] mt-2">25k - 40k · 16薪</p>
                <p class="text-[10px] text-[var(--on-surface-variant)] mt-0.5">包含校招 SSP/SP 研发档位</p>
              </div>
            </div>
          </div>

          <!-- 教育背景 -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center gap-2">
                <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
                <span class="material-symbols-outlined text-[var(--primary)]">school</span>
                <h3 class="font-bold text-[var(--on-surface)]">教育背景</h3>
              </div>
              <div class="flex items-center gap-3">
                <button class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">add</span>添加学历
                </button>
                <button class="text-xs text-gray-400 hover:text-[var(--on-surface-variant)] flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">visibility_off</span>隐藏模块
                </button>
              </div>
            </div>
            <div class="space-y-3">
              <div v-for="(edu, index) in educations" :key="index" class="bg-[#eef2ff] rounded-xl p-4 flex items-start justify-between">
                <div>
                  <div class="flex items-center gap-2 mb-1">
                    <span class="text-sm font-bold text-[var(--on-surface)]">{{ edu.school }}</span>
                    <span v-for="tag in edu.tags" :key="tag" class="px-2 py-0.5 bg-white/80 text-[var(--primary)] text-[10px] font-semibold rounded border border-[var(--primary)]/20">{{ tag }}</span>
                  </div>
                  <p class="text-xs text-[var(--on-surface-variant)]">{{ edu.major }}</p>
                  <p class="text-xs text-[var(--on-surface-variant)] mt-1">{{ edu.gpa }}</p>
                </div>
                <span class="text-xs text-[var(--on-surface-variant)] whitespace-nowrap">{{ edu.period }}</span>
              </div>
            </div>
          </div>

          <!-- 技能特长与技术栈 -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center gap-2">
                <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
                <span class="material-symbols-outlined text-[var(--primary)]">psychology</span>
                <h3 class="font-bold text-[var(--on-surface)]">技能特长与技术栈</h3>
              </div>
              <div class="flex items-center gap-3">
                <span class="text-xs text-[var(--on-surface-variant)]">已选 8 项核心技能</span>
                <button class="text-xs text-gray-400 hover:text-[var(--on-surface-variant)] flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">visibility_off</span>隐藏模块
                </button>
              </div>
            </div>
            <div class="flex flex-wrap gap-2 mb-3">
              <template v-for="(skill, i) in skills" :key="i">
                <span v-if="i < 2"
                  class="inline-flex items-center gap-1.5 px-3 py-1.5 bg-[#1a56db] text-white rounded-lg text-xs font-medium">
                  {{ skill }}
                  <button @click="skills.splice(i, 1)" class="hover:text-white/70"><span class="material-symbols-outlined text-[14px]">close</span></button>
                </span>
                <span v-else
                  class="inline-flex items-center gap-1.5 px-3 py-1.5 bg-[#eef2ff] text-[var(--primary)] rounded-lg text-xs font-medium border border-[var(--primary)]/20">
                  {{ skill }}
                  <button @click="skills.splice(i, 1)" class="hover:text-[var(--primary)]/70"><span class="material-symbols-outlined text-[14px]">close</span></button>
                </span>
              </template>
              <button class="px-3 py-1.5 bg-gray-50 text-gray-500 rounded-lg text-xs font-medium border border-dashed border-gray-300 hover:border-[var(--primary)] hover:text-[var(--primary)] transition-colors">
                + 自定义添加技能
              </button>
            </div>
            <div class="bg-[#eef2ff]/50 rounded-lg p-3 flex items-center gap-2">
              <span class="material-symbols-outlined text-[var(--primary)] text-sm">lightbulb</span>
              <span class="text-xs text-[var(--on-surface-variant)]">已自动关联大模型岗位高频检索词：8项技能全部命中校招算法岗 Top 10 核心标准。</span>
            </div>
          </div>

          <!-- 工作与实习经历 -->
          <div class="bg-white rounded-2xl border border-[var(--primary)]/20 shadow-sm p-5">
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center gap-2">
                <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
                <span class="material-symbols-outlined text-[var(--primary)]">work</span>
                <h3 class="font-bold text-[var(--on-surface)]">工作与实习经历</h3>
                <span class="px-2 py-0.5 bg-[var(--primary)]/10 text-[var(--primary)] text-[10px] font-semibold rounded-full border border-[var(--primary)]/20">当前聚焦编辑</span>
              </div>
              <div class="flex items-center gap-3">
                <span class="text-xs text-[var(--on-surface-variant)]">按照「时间倒序」撰写，建议结合 STAR 原则并突出可量化业务贡献</span>
                <div class="flex items-center gap-1">
                  <button class="w-7 h-7 rounded-lg border border-gray-200 flex items-center justify-center hover:bg-gray-50"><span class="material-symbols-outlined text-[14px]">expand_less</span></button>
                  <button class="w-7 h-7 rounded-lg border border-gray-200 flex items-center justify-center hover:bg-gray-50"><span class="material-symbols-outlined text-[14px]">expand_more</span></button>
                </div>
                <button class="text-xs text-gray-400 hover:text-[var(--on-surface-variant)] flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">visibility_off</span>隐藏模块
                </button>
              </div>
            </div>
            <!-- Experience Entry 1 - Expanded -->
            <div class="border border-[var(--primary)]/30 rounded-xl p-5 mb-4 bg-[#eef2ff]/30">
              <div class="flex items-center justify-between mb-4">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-[var(--primary)] text-lg">deployed_code</span>
                  <h4 class="font-bold text-sm text-[var(--on-surface)]">经历 #1 · 深度学习与大模型推理优化</h4>
                </div>
                <button class="text-xs text-red-500 hover:underline flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">delete</span>删除本项
                </button>
              </div>
              <div class="grid grid-cols-2 gap-4 mb-4">
                <div>
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">企业 / 科研院所名称 *</label>
                  <div class="relative">
                    <span class="absolute left-3 top-1/2 -translate-y-1/2 material-symbols-outlined text-gray-400 text-[16px]">apartment</span>
                    <input v-model="workExp1.company" type="text" class="w-full pl-9 pr-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
                  </div>
                </div>
                <div>
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">职位名称 *</label>
                  <div class="relative">
                    <span class="absolute left-3 top-1/2 -translate-y-1/2 material-symbols-outlined text-gray-400 text-[16px]">badge</span>
                    <input v-model="workExp1.position" type="text" class="w-full pl-9 pr-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
                  </div>
                </div>
                <div>
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">在职起止时间 *</label>
                  <div class="flex items-center gap-2">
                    <input v-model="workExp1.start" type="text" class="flex-1 px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
                    <span class="text-[var(--primary)] font-bold text-sm">至今（在职）</span>
                  </div>
                </div>
                <div>
                  <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">所属团队 / 导师</label>
                  <input v-model="workExp1.team" type="text" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
                </div>
              </div>
              <div>
                <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">工作内容与成果（遵循 STAR 法则：情境、任务、行动、结果）</label>
                <div class="border border-gray-200 rounded-xl overflow-hidden">
                  <!-- Toolbar -->
                  <div class="flex items-center gap-1 px-3 py-2 border-b border-gray-100 bg-gray-50">
                    <button class="w-7 h-7 rounded flex items-center justify-center hover:bg-gray-200 text-sm font-bold">B</button>
                    <button class="w-7 h-7 rounded flex items-center justify-center hover:bg-gray-200 text-sm italic">I</button>
                    <button class="w-7 h-7 rounded flex items-center justify-center hover:bg-gray-200"><span class="material-symbols-outlined text-[14px]">format_list_numbered</span></button>
                    <button class="w-7 h-7 rounded flex items-center justify-center hover:bg-gray-200"><span class="material-symbols-outlined text-[14px]">undo</span></button>
                    <button class="w-7 h-7 rounded flex items-center justify-center hover:bg-gray-200"><span class="material-symbols-outlined text-[14px]">redo</span></button>
                    <div class="h-4 w-px bg-gray-300 mx-1"></div>
                    <button class="flex items-center gap-1 px-2 py-1 text-[var(--primary)] text-xs font-medium hover:bg-[var(--primary)]/5 rounded">
                      <span class="material-symbols-outlined text-[14px]">auto_fix_high</span>AI 代写优化
                    </button>
                  </div>
                  <textarea v-model="workExp1.desc" rows="8"
                    class="w-full px-4 py-3 text-sm leading-relaxed focus:outline-none resize-none"></textarea>
                </div>
                <div class="flex items-center justify-between mt-2">
                  <span class="text-xs text-[var(--primary)] flex items-center gap-1">
                    <span class="material-symbols-outlined text-[12px]">check_circle</span>
                    已检测到 4 处高质量量化数据与关键成果指标
                  </span>
                  <span class="text-xs text-[var(--on-surface-variant)]">字数：238 / 1000</span>
                </div>
              </div>
            </div>
            <!-- Experience Entry 2 - Collapsed -->
            <div class="border border-gray-200 rounded-xl p-4 mb-4 hover:border-gray-300 transition-colors">
              <div class="flex items-center gap-3">
                <span class="material-symbols-outlined text-gray-400 text-lg">drag_indicator</span>
                <div class="flex-1">
                  <div class="flex items-center gap-2">
                    <span class="text-sm font-bold text-[var(--on-surface)]">腾讯科技（深圳）有限公司 · 后台开发实习生（微信事业群）</span>
                  </div>
                  <p class="text-xs text-[var(--on-surface-variant)] mt-0.5">2023.07 — 2023.10 | 核心业务接口高并发重构与微服务容器化</p>
                </div>
                <button class="text-xs text-[var(--primary)] hover:underline">展开修改</button>
              </div>
            </div>
            <!-- Add Button -->
            <button class="w-full py-3 border border-dashed border-gray-300 rounded-xl text-sm text-[var(--primary)] font-medium hover:bg-blue-50/30 transition-colors flex items-center justify-center gap-2">
              <span class="material-symbols-outlined text-[16px]">add_circle</span>
              添加一段工作 / 实习经历
            </button>
          </div>

          <!-- 项目经历 -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
            <div class="flex items-center justify-between mb-4">
              <div class="flex items-center gap-2">
                <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
                <span class="material-symbols-outlined text-[var(--primary)]">folder_open</span>
                <h3 class="font-bold text-[var(--on-surface)]">项目经历</h3>
              </div>
              <div class="flex items-center gap-3">
                <button class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">add</span>添加项目
                </button>
                <button class="text-xs text-gray-400 hover:text-[var(--on-surface-variant)] flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">visibility_off</span>隐藏模块
                </button>
              </div>
            </div>
            <!-- Project Card -->
            <div class="border border-gray-100 rounded-xl p-5 mb-4">
              <div class="flex items-start justify-between mb-2">
                <div>
                  <div class="flex items-center gap-2">
                    <span class="text-sm font-bold text-[var(--on-surface)]">"智绘校园"多模态智能问答与学术文献解析系统</span>
                    <span class="px-2 py-0.5 bg-[var(--primary)]/10 text-[var(--primary)] text-[10px] font-semibold rounded border border-[var(--primary)]/20">核心主导研发</span>
                  </div>
                </div>
                <span class="text-xs text-[var(--on-surface-variant)]">2023.11 — 2024.04</span>
              </div>
              <div class="flex items-center gap-4 text-xs text-[var(--on-surface-variant)] mb-3">
                <span class="flex items-center gap-1"><span class="material-symbols-outlined text-[12px]">link</span> Github: github.com/linchen/campus-rag-agent</span>
                <span class="flex items-center gap-1"><span class="text-[var(--primary)]">★</span> Star 1.2k+</span>
              </div>
              <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed mb-4">【项目背景与架构】该项目为北京大学产学研协同创新课题。针对校内跨学院万级 PDF 论文与学籍规章文档，设计基于 Milvus 向量检索与混合重排 (Hybrid BGE-Reranker) 的 RAG 智能中枢。</p>
              <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed mb-4">【个人核心产出】从零自研多模态文档切分与版面分析模块，准确率达 94.6%；引入动态上下文窗口自适应压缩策略，有效抑制大模型幻觉，使问答真实度提升 28.5%，服务全校逾 18,000 名师生。</p>
              <div class="flex items-center justify-end gap-3 pt-3 border-t border-gray-50">
                <button class="text-xs text-[var(--on-surface-variant)] hover:text-[var(--primary)] flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">edit</span>编辑
                </button>
                <button class="text-xs text-red-500 hover:underline flex items-center gap-1">
                  <span class="material-symbols-outlined text-[12px]">delete</span>删除
                </button>
              </div>
            </div>
            <!-- Add Button -->
            <button class="w-full py-3 border border-dashed border-gray-300 rounded-xl text-sm text-[var(--primary)] font-medium hover:bg-blue-50/30 transition-colors flex items-center justify-center gap-2">
              <span class="material-symbols-outlined text-[16px]">add</span>
              + 添加更多学术项目 / 个人独立开发开源作品
            </button>
          </div>

          <!-- Module Suggestions -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6 text-center">
            <span class="material-symbols-outlined text-3xl text-gray-300 mb-2 block">widgets</span>
            <h4 class="font-bold text-sm text-[var(--on-surface)] mb-1">丰富简历模块，打造高分竞争力</h4>
            <p class="text-xs text-[var(--on-surface-variant)] mb-4">点击下方卡片快速启用未开启模块，或在右侧控制台自由配置排序与显隐</p>
            <div class="flex items-center justify-center gap-3">
              <button class="px-4 py-2 bg-gray-50 text-gray-600 rounded-xl text-xs font-medium border border-gray-200 hover:bg-gray-100 transition-colors flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[14px]">emoji_events</span>+ 荣誉证书
              </button>
              <button class="px-4 py-2 bg-gray-50 text-gray-600 rounded-xl text-xs font-medium border border-gray-200 hover:bg-gray-100 transition-colors flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[14px]">edit_note</span>+ 自我评价
              </button>
              <button class="px-4 py-2 bg-gray-50 text-gray-600 rounded-xl text-xs font-medium border border-gray-200 hover:bg-gray-100 transition-colors flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[14px]">school</span>+ 校园经历
              </button>
              <button class="px-4 py-2 bg-gray-50 text-gray-600 rounded-xl text-xs font-medium border border-gray-200 hover:bg-gray-100 transition-colors flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[14px]">favorite</span>+ 兴趣爱好
              </button>
              <button class="px-4 py-2 bg-[var(--primary)]/10 text-[var(--primary)] rounded-xl text-xs font-medium border border-[var(--primary)]/20 hover:bg-[var(--primary)]/15 transition-colors flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[14px]">dashboard</span>+ 新增自定义模块
              </button>
            </div>
          </div>
        </div>

        <!-- Right Sidebar -->
        <div class="w-[280px] shrink-0 space-y-4">
          <!-- 信息模块 -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-4">
            <div class="flex items-center gap-4 mb-4 border-b border-gray-100 pb-3">
              <button class="text-xs font-bold text-[var(--primary)] border-b-2 border-[var(--primary)] pb-3 -mb-[13px] flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[14px]">view_module</span>信息模块
              </button>
              <button class="text-xs text-gray-400 flex items-center gap-1.5">
                <span class="material-symbols-outlined text-[14px]">sort</span>排序设置
              </button>
            </div>
            <div class="flex items-center justify-between mb-3">
              <p class="text-[11px] text-[var(--on-surface-variant)] flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">toggle_on</span> 开关控制简历页面显隐
              </p>
              <button class="text-[11px] text-[var(--primary)] flex items-center gap-1">
                <span class="material-symbols-outlined text-[12px]">add_circle</span>自定义模块
              </button>
            </div>
            <div class="space-y-1">
              <div v-for="mod in modules" :key="mod.key" class="flex items-center justify-between py-2.5 px-2 rounded-lg hover:bg-gray-50 transition-colors">
                <div class="flex items-center gap-2.5">
                  <span :class="['material-symbols-outlined text-[16px]', mod.enabled ? 'text-[var(--primary)]' : 'text-gray-400']">{{ mod.icon }}</span>
                  <span class="text-xs text-[var(--on-surface)] font-medium">{{ mod.label }}</span>
                  <span v-if="mod.required" class="px-1.5 py-0.5 bg-red-50 text-red-500 text-[9px] font-semibold rounded border border-red-100">必填</span>
                </div>
                <button @click="mod.enabled = !mod.enabled"
                  :class="['relative w-10 h-5 rounded-full transition-colors',
                    mod.enabled ? 'bg-[#1a56db]' : 'bg-[#c7d2fe]']">
                  <span :class="['absolute top-0.5 w-4 h-4 bg-white rounded-full shadow-sm transition-transform',
                    mod.enabled ? 'left-[22px]' : 'left-0.5']"></span>
                </button>
              </div>
            </div>
          </div>

          <!-- 2025届校招算法岗热度 -->
          <div class="bg-gradient-to-br from-blue-50/80 to-indigo-50/80 rounded-2xl border border-blue-100 p-5">
            <div class="flex items-center gap-2 mb-3">
              <span class="material-symbols-outlined text-[var(--primary)] text-lg">local_fire_department</span>
              <h4 class="font-bold text-sm text-[var(--on-surface)]">2025届校招算法岗热度</h4>
            </div>
            <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed">当前平台已有 <span class="font-bold text-[var(--primary)]">1,420+</span> 家校招合作企业发布大模型/算法岗，完成项目经历填写后将自动点亮「优先投递专享」内推免笔试特权。</p>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getResumeInfo, saveResume, updateResume } from '../api/resume'

const router = useRouter()
const route = useRoute()
const store = useAppStore()
const saving = ref(false)
const resumeId = computed(() => route.query.id ? Number(route.query.id) : null)

const activeStep = ref(0)

const steps = [
  { key: 'basic', label: '基本信息' },
  { key: 'career', label: '求职意向' },
  { key: 'education', label: '教育背景' },
  { key: 'skills', label: '技能特长' },
  { key: 'work', label: '工作经历' },
  { key: 'project', label: '项目经历' }
]

const form = reactive({
  title: '我的简历',
  name: '',
  gender: 0,
  birthDate: '',
  phone: '',
  email: '',
  education: 1,
  school: '',
  major: '',
  graduationYear: 2025,
  workStatus: 1,
  expectCity: '',
  expectIndustry: '',
  expectJobType: '',
  expectSalary: '',
  selfIntroduction: '',
  skills: '[]',
  experiences: '[]',
  projects: '[]',
  awards: '[]',
  attachments: '[]'
})

onMounted(async () => {
  if (resumeId.value) {
    try {
      const data = await getResumeInfo(resumeId.value)
      Object.keys(form).forEach(key => {
        if (data[key] !== null && data[key] !== undefined) form[key] = data[key]
      })
    } catch (e) {
      console.error('加载简历失败:', e)
    }
  } else if (store.user) {
    form.name = store.user.name || ''
    form.phone = store.user.phone || ''
  }
})

const skills = computed(() => {
  try { return JSON.parse(form.skills || '[]') } catch { return [] }
})

async function handleSave() {
  saving.value = true
  try {
    if (resumeId.value) {
      await updateResume(resumeId.value, form)
    } else {
      const newId = await saveResume(form)
      router.replace({ query: { id: newId } })
    }
    alert('保存成功')
  } catch (e) {
    console.error('保存简历失败:', e)
    alert(e.message || '保存失败，请重试')
  } finally {
    saving.value = false
  }
}

const educations = reactive([
  { school: '', tags: [], major: '', gpa: '', period: '' }
])

const workExp1 = reactive({
  company: '', position: '', start: '', end: '', team: '', desc: ''
})

const modules = reactive([
  { key: 'basic', label: '基本信息', icon: 'person', enabled: true, required: true },
  { key: 'career', label: '求职意向', icon: 'flag', enabled: true, required: false },
  { key: 'education', label: '教育经历', icon: 'school', enabled: true, required: false },
  { key: 'work', label: '工作经验', icon: 'work', enabled: true, required: false },
  { key: 'project', label: '项目经验', icon: 'folder_open', enabled: true, required: false },
  { key: 'intern', label: '实习经验', icon: 'business_center', enabled: true, required: false },
  { key: 'campus', label: '校园经历', icon: 'groups', enabled: false, required: false },
  { key: 'skills', label: '技能特长', icon: 'psychology', enabled: true, required: false },
  { key: 'awards', label: '荣誉证书', icon: 'emoji_events', enabled: false, required: false },
  { key: 'self', label: '自我评价', icon: 'edit_note', enabled: false, required: false },
  { key: 'hobby', label: '兴趣爱好', icon: 'favorite', enabled: false, required: false },
  { key: 'custom', label: '自定义模块', icon: 'dashboard', enabled: false, required: false }
])

function prevStep() {
  if (activeStep.value > 0) activeStep.value--
}

function nextStep() {
  if (activeStep.value < steps.length - 1) activeStep.value++
}
</script>
