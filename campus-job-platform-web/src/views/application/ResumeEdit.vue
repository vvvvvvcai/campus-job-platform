<template>
  <div class="relative w-full max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-md">
    <div class="absolute top-10 left-1/3 w-96 h-72 bg-secondary-container/10 rounded-full blur-3xl pointer-events-none -z-10"></div>
    <div class="absolute top-48 right-1/4 w-80 h-80 bg-primary-fixed/30 rounded-full blur-3xl pointer-events-none -z-10"></div>

    <!-- 1. Top Breadcrumb & Actions Header -->
    <div class="flex flex-col md:flex-row md:items-center justify-between gap-space-md mb-space-lg">
      <div class="space-y-space-2xs">
        <nav aria-label="Breadcrumb" class="flex items-center gap-space-xs font-label-md text-label-md text-on-surface-variant">
          <router-link class="hover:text-primary transition-colors" to="/profile">个人中心</router-link>
          <span class="material-symbols-outlined text-sm opacity-60">chevron_right</span>
          <router-link class="hover:text-primary transition-colors" to="/resume">我的简历</router-link>
          <span class="material-symbols-outlined text-sm opacity-60">chevron_right</span>
          <span class="text-on-surface font-medium truncate max-w-[240px] sm:max-w-md">2025届计算机研发与算法专向简历</span>
        </nav>
        <div class="flex items-center gap-space-sm flex-wrap">
          <h1 class="font-headline-xl text-headline-xl text-on-surface font-bold tracking-tight">编辑求职简历</h1>
          <div class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-surface-container-low text-on-surface-variant font-label-sm text-label-sm">
            <span class="inline-block w-2 h-2 rounded-full bg-secondary"></span>
            <span>已于 {{ autoSaveTime }} 自动保存为草稿</span>
          </div>
        </div>
      </div>
      <div class="flex items-center gap-space-sm shrink-0">
        <button class="inline-flex items-center gap-space-xs px-4 h-10 rounded-lg bg-surface-container-lowest text-on-surface font-label-lg text-label-lg shadow-sm hover:bg-surface-container-low transition-all" type="button">
          <span class="material-symbols-outlined text-[18px] text-on-surface-variant">visibility</span>
          <span>预览效果</span>
        </button>
        <button class="inline-flex items-center gap-space-xs px-5 h-10 rounded-lg bg-primary text-on-primary font-label-lg text-label-lg shadow-sm hover:bg-surface-tint active:scale-[0.99] transition-all" type="button">
          <span class="material-symbols-outlined text-[18px]">check_circle</span>
          <span>完成并保存简历</span>
        </button>
      </div>
    </div>

    <!-- 2. Stepper & Completion Card -->
    <div class="bg-surface-container-lowest rounded-xl p-space-md md:p-space-lg shadow-sm mb-space-xl">
      <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-6 gap-space-sm items-center relative">
        <a class="flex items-center gap-space-xs group" href="#section-basic">
          <div class="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center font-label-md text-label-md shrink-0 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">done</span>
          </div>
          <div class="min-w-0">
            <p class="font-label-md text-label-md text-on-surface font-semibold truncate group-hover:text-primary transition-colors">1. 基本信息</p>
            <p class="font-label-sm text-label-sm text-secondary font-medium">已完成</p>
          </div>
        </a>
        <a class="flex items-center gap-space-xs group" href="#section-intention">
          <div class="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center font-label-md text-label-md shrink-0 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">done</span>
          </div>
          <div class="min-w-0">
            <p class="font-label-md text-label-md text-on-surface font-semibold truncate group-hover:text-primary transition-colors">2. 求职意向</p>
            <p class="font-label-sm text-secondary text-label-sm font-medium">已完成</p>
          </div>
        </a>
        <a class="flex items-center gap-space-xs group" href="#section-education">
          <div class="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center font-label-md text-label-md shrink-0 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">done</span>
          </div>
          <div class="min-w-0">
            <p class="font-label-md text-label-md text-on-surface font-semibold truncate group-hover:text-primary transition-colors">3. 教育背景</p>
            <p class="font-label-sm text-secondary text-label-sm font-medium">已完成</p>
          </div>
        </a>
        <a class="flex items-center gap-space-xs group" href="#section-skills">
          <div class="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center font-label-md text-label-md shrink-0 shadow-sm">
            <span class="material-symbols-outlined text-[18px]">done</span>
          </div>
          <div class="min-w-0">
            <p class="font-label-md text-label-md text-on-surface font-semibold truncate group-hover:text-primary transition-colors">4. 技能特长</p>
            <p class="font-label-sm text-secondary text-label-sm font-medium">已完成</p>
          </div>
        </a>
        <a class="flex items-center gap-space-xs bg-primary-container/10 p-1.5 rounded-lg -m-1.5" href="#section-experience">
          <div class="w-8 h-8 rounded-full bg-primary-container text-on-primary flex items-center justify-center font-label-md text-label-md shrink-0 shadow-md">
            5
          </div>
          <div class="min-w-0">
            <p class="font-label-md text-label-md text-primary font-bold truncate">5. 工作经历</p>
            <p class="font-label-sm text-label-sm text-primary font-medium flex items-center gap-1">
              <span class="w-1.5 h-1.5 rounded-full bg-primary-container animate-ping"></span>
              编辑中
            </p>
          </div>
        </a>
        <a class="flex items-center gap-space-xs group opacity-75 hover:opacity-100 transition-opacity" href="#section-projects">
          <div class="w-8 h-8 rounded-full bg-surface-container text-on-surface-variant flex items-center justify-center font-label-md text-label-md shrink-0">
            6
          </div>
          <div class="min-w-0">
            <p class="font-label-md text-label-md text-on-surface truncate group-hover:text-primary transition-colors">6. 项目经历</p>
            <p class="font-label-sm text-label-sm text-on-surface-variant">待补充</p>
          </div>
        </a>
      </div>
      <div class="mt-space-md pt-space-sm bg-surface-container-low/60 rounded-lg p-space-sm flex flex-col sm:flex-row sm:items-center justify-between gap-space-sm">
        <div class="flex items-center gap-space-sm min-w-0">
          <div class="w-2.5 h-2.5 rounded-full bg-secondary shrink-0"></div>
          <p class="font-body-sm text-body-sm text-on-surface truncate">
            简历完善度：<span class="font-semibold text-primary">{{ completeness }}%</span>
            <span class="text-on-surface-variant ml-1.5 hidden md:inline">（建议补充第6步"项目经历"深度量化指标，以获得更多头部大厂与科研所 HR 青睐）</span>
          </p>
        </div>
        <div class="flex items-center gap-3 shrink-0 w-full sm:w-48">
          <div class="w-full bg-surface-container h-2 rounded-full overflow-hidden">
            <div class="bg-primary h-full rounded-full transition-all duration-500" :style="{ width: completeness + '%' }"></div>
          </div>
          <span class="font-label-sm text-label-sm font-bold text-primary shrink-0">{{ completeness }}%</span>
        </div>
      </div>
    </div>

    <!-- 3. Two-Column Workspace -->
    <div class="grid grid-cols-1 lg:grid-cols-12 gap-space-lg items-start">
      <!-- MAIN WORKSPACE -->
      <div class="lg:col-span-8 flex flex-col gap-space-lg">
        <!-- Module 1: Basic Information -->
        <section class="bg-surface-container-lowest rounded-xl p-space-lg shadow-sm transition-all" id="section-basic">
          <div class="flex items-center justify-between mb-space-md">
            <div class="flex items-center gap-space-sm">
              <span class="material-symbols-outlined text-primary text-[22px]">badge</span>
              <h2 class="font-headline-md text-headline-md text-on-surface font-bold">基本信息与个人标签</h2>
              <span class="px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed font-label-sm text-label-sm font-medium">已认证</span>
            </div>
            <button class="text-on-surface-variant hover:text-primary transition-colors p-1" @click="basicContentVisible = !basicContentVisible" type="button">
              <span class="material-symbols-outlined">{{ basicContentVisible ? 'expand_less' : 'expand_more' }}</span>
            </button>
          </div>
          <div v-show="basicContentVisible" class="grid grid-cols-1 md:grid-cols-3 gap-space-md pt-space-xs">
            <div class="flex items-center gap-space-md md:col-span-3 pb-space-sm bg-surface-container-low p-space-sm rounded-lg">
              <img class="w-16 h-16 rounded-full object-cover shadow-sm shrink-0" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAUJmdemab04nRmRxhxNgSKka28S1VsgLJaxIENEX0EjUZXZT-Ynrd2JSmGaxkzJ90pNYoOqJK9Cw6Ewd937pvWfVJkJuOSr3tlkcFcEMTuMC4emw-XPglt87osgSM94fBAMsWa7fDPpzU2ZzAaASzilKj9YJqmtBwr8OMNNIvOMO1w7IbKxBrPKMb_K1miCqLfKQLDFdYySbGjxc53lWmmrIwY7TgibeIongE-YiNTA5x4PNnGHyhQMA" alt="头像" />
              <div class="flex-1 min-w-0">
                <div class="flex items-center gap-space-xs flex-wrap">
                  <span class="font-headline-sm text-headline-sm text-on-surface font-bold">林晨</span>
                  <span class="px-2 py-0.5 rounded-full bg-primary-fixed text-on-primary-fixed font-label-sm text-label-sm">2025届硕士</span>
                  <span class="px-2 py-0.5 rounded-full bg-surface-container-highest text-on-surface-variant font-label-sm text-label-sm">中共党员</span>
                </div>
                <p class="font-body-sm text-body-sm text-on-surface-variant mt-1 truncate">求职意愿：全职算法研发 / 大模型架构工程师（随时可到岗）</p>
              </div>
              <button class="text-primary hover:text-surface-tint font-label-sm text-label-sm font-semibold shrink-0" type="button">更换照片</button>
            </div>
            <div class="space-y-1">
              <label class="font-label-sm text-label-sm text-on-surface-variant">手机号码</label>
              <input v-model="formData.phone" class="w-full h-10 px-3 rounded-lg bg-surface-container-low text-on-surface font-body-md text-body-md focus:bg-surface-container-lowest focus:outline-none focus:ring-2 focus:ring-primary/20" type="text" />
            </div>
            <div class="space-y-1">
              <label class="font-label-sm text-label-sm text-on-surface-variant">教育邮箱 (Edu Mail)</label>
              <input v-model="formData.email" class="w-full h-10 px-3 rounded-lg bg-surface-container-low text-on-surface font-body-md text-body-md focus:bg-surface-container-lowest focus:outline-none focus:ring-2 focus:ring-primary/20" type="email" />
            </div>
            <div class="space-y-1">
              <label class="font-label-sm text-label-sm text-on-surface-variant">出生年月 / 政治面貌</label>
              <input v-model="formData.birthPolitical" class="w-full h-10 px-3 rounded-lg bg-surface-container-low text-on-surface font-body-md text-body-md focus:bg-surface-container-lowest focus:outline-none focus:ring-2 focus:ring-primary/20" type="text" />
            </div>
          </div>
        </section>

        <!-- Module 2: Job Intentions -->
        <section class="bg-surface-container-lowest rounded-xl p-space-lg shadow-sm" id="section-intention">
          <div class="flex items-center justify-between mb-space-md">
            <div class="flex items-center gap-space-sm">
              <span class="material-symbols-outlined text-primary text-[22px]">explore</span>
              <h2 class="font-headline-md text-headline-md text-on-surface font-bold">求职意向</h2>
            </div>
            <button class="text-primary hover:underline font-label-md text-label-md flex items-center gap-0.5" type="button">
              <span class="material-symbols-outlined text-sm">edit</span> 修改意向
            </button>
          </div>
          <div class="grid grid-cols-1 sm:grid-cols-3 gap-space-md">
            <div class="bg-surface-container-low/70 p-space-sm rounded-lg">
              <span class="font-label-sm text-label-sm text-on-surface-variant block mb-1">期望城市</span>
              <div class="flex flex-wrap gap-1.5">
                <span class="px-2 py-0.5 rounded-full bg-surface-container-lowest text-primary font-label-sm text-label-sm shadow-sm font-medium">北京 (首选)</span>
                <span class="px-2 py-0.5 rounded-full bg-surface-container-lowest text-on-surface font-label-sm text-label-sm shadow-sm">上海</span>
                <span class="px-2 py-0.5 rounded-full bg-surface-container-lowest text-on-surface font-label-sm text-label-sm shadow-sm">深圳</span>
              </div>
            </div>
            <div class="bg-surface-container-low/70 p-space-sm rounded-lg">
              <span class="font-label-sm text-label-sm text-on-surface-variant block mb-1">目标行业</span>
              <div class="flex flex-wrap gap-1.5">
                <span class="px-2 py-0.5 rounded-full bg-surface-container-lowest text-on-surface font-label-sm text-label-sm shadow-sm">互联网 AI / 大模型</span>
                <span class="px-2 py-0.5 rounded-full bg-surface-container-lowest text-on-surface font-label-sm text-label-sm shadow-sm">高端硬科技智能研发</span>
              </div>
            </div>
            <div class="bg-surface-container-low/70 p-space-sm rounded-lg">
              <span class="font-label-sm text-label-sm text-on-surface-variant block mb-1">期望月薪 (税前)</span>
              <p class="font-headline-sm text-headline-sm text-primary font-bold mt-0.5">25k - 40k · 16薪</p>
              <span class="font-label-sm text-label-sm text-on-surface-variant">包含校招 SSP/SP 研发档位</span>
            </div>
          </div>
        </section>

        <!-- Module 3: Education Background -->
        <section class="bg-surface-container-lowest rounded-xl p-space-lg shadow-sm" id="section-education">
          <div class="flex items-center justify-between mb-space-md">
            <div class="flex items-center gap-space-sm">
              <span class="material-symbols-outlined text-primary text-[22px]">school</span>
              <h2 class="font-headline-md text-headline-md text-on-surface font-bold">教育背景</h2>
            </div>
            <button class="text-primary hover:underline font-label-md text-label-md flex items-center gap-0.5" type="button">
              <span class="material-symbols-outlined text-sm">add</span> 添加学历
            </button>
          </div>
          <div class="space-y-space-md">
            <div class="flex flex-col sm:flex-row sm:items-start justify-between p-space-sm rounded-lg bg-surface-container-low gap-space-xs">
              <div class="space-y-1">
                <div class="flex items-center gap-space-xs flex-wrap">
                  <span class="font-headline-sm text-headline-sm text-on-surface font-bold">北京大学</span>
                  <span class="px-2 py-0.5 rounded-full bg-primary-fixed text-on-primary-fixed font-label-sm text-label-sm font-semibold">985 / 双一流</span>
                  <span class="text-on-surface-variant font-body-sm text-body-sm">计算机软件与理论 · 硕士研究生</span>
                </div>
                <p class="font-body-sm text-body-sm text-on-surface-variant">综合绩点：<strong class="text-on-surface font-semibold">GPA 3.82 / 4.0</strong> (专业前 5%) · 国家奖学金获得者 · 研究方向为大规模语言模型与高效微调</p>
              </div>
              <div class="text-right shrink-0">
                <span class="font-label-md text-label-md text-on-surface-variant font-medium">2022.09 — 2025.06 (预计)</span>
              </div>
            </div>
            <div class="flex flex-col sm:flex-row sm:items-start justify-between p-space-sm rounded-lg bg-surface-container-low/50 gap-space-xs">
              <div class="space-y-1">
                <div class="flex items-center gap-space-xs flex-wrap">
                  <span class="font-headline-sm text-headline-sm text-on-surface font-bold">哈尔滨工业大学</span>
                  <span class="px-2 py-0.5 rounded-full bg-surface-container-highest text-on-surface-variant font-label-sm text-label-sm">C9 联盟</span>
                  <span class="text-on-surface-variant font-body-sm text-body-sm">计算机科学与技术 · 工学学士</span>
                </div>
                <p class="font-body-sm text-body-sm text-on-surface-variant">综合绩点：<strong class="text-on-surface font-semibold">GPA 3.78 / 4.0</strong> · 校级一等学术奖学金 · ACM-ICPC 区域赛银奖</p>
              </div>
              <div class="text-right shrink-0">
                <span class="font-label-md text-label-md text-on-surface-variant font-medium">2018.09 — 2022.06</span>
              </div>
            </div>
          </div>
        </section>

        <!-- Module 4: Skills & Specialties -->
        <section class="bg-surface-container-lowest rounded-xl p-space-lg shadow-sm" id="section-skills">
          <div class="flex items-center justify-between mb-space-md">
            <div class="flex items-center gap-space-sm">
              <span class="material-symbols-outlined text-primary text-[22px]">psychology</span>
              <h2 class="font-headline-md text-headline-md text-on-surface font-bold">技能特长与技术栈</h2>
            </div>
            <span class="font-label-sm text-label-sm text-on-surface-variant">已选 {{ skills.length }} 项核心技能</span>
          </div>
          <div class="flex flex-wrap gap-space-xs mb-space-md">
            <span v-for="(skill, index) in skills" :key="index" class="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-full font-label-md text-label-md font-medium" :class="getSkillClass(index)">
              {{ skill }}
              <button class="material-symbols-outlined text-[14px] hover:opacity-80" type="button" @click="removeSkill(index)">close</button>
            </span>
            <button class="inline-flex items-center gap-1 px-3 py-1.5 rounded-full bg-surface-container text-primary font-label-md text-label-md font-semibold hover:bg-surface-container-high transition-colors" type="button">
              <span class="material-symbols-outlined text-sm">add</span> 自定义添加技能
            </button>
          </div>
          <div class="flex items-center gap-2 text-on-surface-variant font-body-sm text-body-sm bg-surface-container-low p-2 rounded-lg">
            <span class="material-symbols-outlined text-[18px] text-secondary">tips_and_updates</span>
            <span>已自动关联大模型岗位高频检索词：{{ skills.length }}项技能全部命中校招算法岗 Top 10 核心标准。</span>
          </div>
        </section>

        <!-- Module 5: Work & Internship Experience -->
        <section class="bg-surface-container-lowest rounded-xl p-space-lg shadow-md ring-2 ring-primary/20 relative" id="section-experience">
          <div class="flex items-center justify-between mb-space-md">
            <div class="flex items-center gap-space-sm">
              <div class="w-8 h-8 rounded-lg bg-primary-container text-on-primary flex items-center justify-center">
                <span class="material-symbols-outlined text-[20px]">work</span>
              </div>
              <div>
                <div class="flex items-center gap-2">
                  <h2 class="font-headline-md text-headline-md text-on-surface font-bold">工作与实习经历</h2>
                  <span class="px-2 py-0.5 rounded-full bg-primary-container text-on-primary font-label-sm text-label-sm font-semibold">当前聚焦编辑</span>
                </div>
                <p class="font-body-sm text-body-sm text-on-surface-variant">按照"时间倒序"撰写，建议结合 STAR 原则并突出可量化业务贡献</p>
              </div>
            </div>
            <div class="flex items-center gap-space-xs">
              <button class="p-2 text-on-surface-variant hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors" title="上移此模块" type="button">
                <span class="material-symbols-outlined text-[20px]">arrow_upward</span>
              </button>
              <button class="p-2 text-on-surface-variant hover:text-on-surface hover:bg-surface-container-low rounded-lg transition-colors" title="下移此模块" type="button">
                <span class="material-symbols-outlined text-[20px]">arrow_downward</span>
              </button>
            </div>
          </div>
          <!-- Active Edit Item 1 -->
          <div class="bg-surface-container-low p-space-md rounded-xl space-y-space-md mb-space-md shadow-sm">
            <div class="flex items-center justify-between border-b border-surface-container-highest pb-space-xs">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary text-[20px]">business_center</span>
                <span class="font-headline-sm text-headline-sm text-on-surface font-bold">经历 #1 · 深度学习与大模型推理优化</span>
              </div>
              <button class="text-error font-label-sm text-label-sm hover:underline flex items-center gap-0.5" type="button">
                <span class="material-symbols-outlined text-sm">delete</span> 删除本项
              </button>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-space-md">
              <div class="space-y-1">
                <label class="font-label-sm text-label-sm text-on-surface-variant font-medium">企业 / 科研院所名称 <span class="text-error">*</span></label>
                <div class="relative">
                  <input v-model="experience.company" class="w-full h-10 px-3 pl-9 rounded-lg bg-surface-container-lowest text-on-surface font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary shadow-sm" type="text" />
                  <span class="material-symbols-outlined text-on-surface-variant text-[18px] absolute left-2.5 top-2.5">apartment</span>
                </div>
              </div>
              <div class="space-y-1">
                <label class="font-label-sm text-label-sm text-on-surface-variant font-medium">职位名称 <span class="text-error">*</span></label>
                <div class="relative">
                  <input v-model="experience.position" class="w-full h-10 px-3 pl-9 rounded-lg bg-surface-container-lowest text-on-surface font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary shadow-sm" type="text" />
                  <span class="material-symbols-outlined text-on-surface-variant text-[18px] absolute left-2.5 top-2.5">engineering</span>
                </div>
              </div>
              <div class="space-y-1">
                <label class="font-label-sm text-label-sm text-on-surface-variant font-medium">在职起止时间 <span class="text-error">*</span></label>
                <div class="grid grid-cols-2 gap-2">
                  <input v-model="experience.startDate" class="w-full h-10 px-3 rounded-lg bg-surface-container-lowest text-on-surface font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary shadow-sm" type="text" />
                  <input v-model="experience.endDate" class="w-full h-10 px-3 rounded-lg bg-surface-container-lowest text-on-surface font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary shadow-sm font-medium text-primary" type="text" />
                </div>
              </div>
              <div class="space-y-1">
                <label class="font-label-sm text-label-sm text-on-surface-variant font-medium">所属团队 / 导师</label>
                <input v-model="experience.team" class="w-full h-10 px-3 rounded-lg bg-surface-container-lowest text-on-surface font-body-md text-body-md focus:outline-none focus:ring-2 focus:ring-primary shadow-sm" type="text" />
              </div>
            </div>
            <div class="space-y-1.5">
              <div class="flex items-center justify-between">
                <label class="font-label-sm text-label-sm text-on-surface-variant font-medium">工作内容与成果 (遵循 STAR 法则：情境、任务、行动、结果)</label>
                <div class="flex items-center gap-1 bg-surface-container-lowest px-2 py-1 rounded-md shadow-sm">
                  <button class="p-1 hover:bg-surface-container-low rounded text-on-surface-variant font-bold text-xs" title="加粗" type="button">B</button>
                  <button class="p-1 hover:bg-surface-container-low rounded text-on-surface-variant italic text-xs" title="斜体" type="button">I</button>
                  <button class="p-1 hover:bg-surface-container-low rounded text-on-surface-variant" title="无序列表" type="button"><span class="material-symbols-outlined text-[14px]">format_list_bulleted</span></button>
                  <button class="p-1 hover:bg-surface-container-low rounded text-on-surface-variant" title="撤销" type="button"><span class="material-symbols-outlined text-[14px]">undo</span></button>
                  <button class="p-1 hover:bg-surface-container-low rounded text-on-surface-variant" title="重做" type="button"><span class="material-symbols-outlined text-[14px]">redo</span></button>
                  <span class="w-px h-3 bg-outline-variant mx-1"></span>
                  <button class="inline-flex items-center gap-1 text-primary font-label-sm text-label-sm font-semibold hover:underline" type="button">
                    <span class="material-symbols-outlined text-[14px]">auto_fix_high</span> AI 代写优化
                  </button>
                </div>
              </div>
              <div class="relative">
                <textarea v-model="experience.description" class="w-full p-3.5 rounded-lg bg-surface-container-lowest text-on-surface font-body-md text-body-md leading-relaxed focus:outline-none focus:ring-2 focus:ring-primary shadow-sm font-mono" rows="5"></textarea>
              </div>
              <div class="flex items-center justify-between text-on-surface-variant font-label-sm text-label-sm px-1">
                <span class="text-secondary flex items-center gap-1 font-medium">
                  <span class="material-symbols-outlined text-sm">verified</span> 已检测到 {{ quantifiedCount }} 处高质量量化数据与关键成果指标
                </span>
                <span>字数：{{ charCount }} / 1000</span>
              </div>
            </div>
          </div>
          <!-- Secondary Inactive Item 2 -->
          <div class="p-space-sm rounded-lg bg-surface-container-low/60 flex items-center justify-between hover:bg-surface-container-low transition-colors">
            <div class="flex items-center gap-space-sm">
              <span class="material-symbols-outlined text-on-surface-variant">drag_indicator</span>
              <div>
                <p class="font-headline-sm text-headline-sm text-on-surface font-semibold">腾讯科技 (深圳) 有限公司 · 后台开发实习生 (微信事业群)</p>
                <p class="font-body-sm text-body-sm text-on-surface-variant">2023.07 — 2023.10 | 核心业务接口高并发重构与微服务容器化</p>
              </div>
            </div>
            <button class="text-primary hover:underline font-label-sm text-label-sm shrink-0" type="button">展开修改</button>
          </div>
          <!-- Add Experience CTA -->
          <div class="mt-space-md">
            <button class="w-full py-2.5 rounded-lg bg-surface-container-lowest hover:bg-surface-container-low text-primary font-label-lg text-label-lg font-semibold transition-all flex items-center justify-center gap-space-xs shadow-sm" type="button">
              <span class="material-symbols-outlined text-[20px]">add_circle</span>
              <span>添加一段工作 / 实习经历</span>
            </button>
          </div>
        </section>

        <!-- Module 6: Project Experience -->
        <section class="bg-surface-container-lowest rounded-xl p-space-lg shadow-sm" id="section-projects">
          <div class="flex items-center justify-between mb-space-md">
            <div class="flex items-center gap-space-sm">
              <div class="w-8 h-8 rounded-lg bg-surface-container text-on-surface-variant flex items-center justify-center">
                <span class="material-symbols-outlined text-[20px]">terminal</span>
              </div>
              <h2 class="font-headline-md text-headline-md text-on-surface font-bold">项目经历</h2>
            </div>
            <button class="inline-flex items-center gap-1 text-primary hover:underline font-label-md text-label-md font-medium" type="button">
              <span class="material-symbols-outlined text-sm">add</span> 添加项目
            </button>
          </div>
          <div class="bg-surface-container-low/70 p-space-md rounded-xl space-y-space-sm mb-space-sm">
            <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-1">
              <div class="flex items-center gap-space-xs flex-wrap">
                <span class="font-headline-sm text-headline-sm text-on-surface font-bold">"智绘校园"多模态智能问答与学术文献解析系统</span>
                <span class="px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed font-label-sm text-label-sm font-semibold">核心主导研发</span>
              </div>
              <span class="font-label-md text-label-md text-on-surface-variant shrink-0">2023.11 — 2024.04</span>
            </div>
            <div class="flex items-center gap-space-md font-body-sm text-body-sm text-on-surface-variant">
              <span class="flex items-center gap-1"><span class="material-symbols-outlined text-sm">link</span> Github: github.com/linchen/campus-rag-agent</span>
              <span class="flex items-center gap-1 text-secondary font-medium"><span class="material-symbols-outlined text-sm">star</span> Star 1.2k+</span>
            </div>
            <p class="font-body-md text-body-md text-on-surface leading-relaxed">
              <strong>【项目背景与架构】</strong>该项目为北京大学产学协同创新课题。针对校内跨学院万级 PDF 论文与学籍规章文档，设计基于 Milvus 向量检索与混合重排 (Hybrid BGE-Reranker) 的 RAG 智能中枢。
              <br/>
              <strong>【个人核心产出】</strong>从零自研多模态文档切分与版面分析模块，准确率达 94.6%；引入动态上下文窗口自适应压缩策略，有效抑制大模型幻觉，使问答真实度提升 28.5%，服务全校逾 18,000 名师生。
            </p>
            <div class="flex items-center justify-end gap-3 pt-2">
              <button class="text-on-surface-variant hover:text-primary font-label-sm text-label-sm flex items-center gap-1" type="button">
                <span class="material-symbols-outlined text-sm">edit</span> 编辑
              </button>
              <button class="text-error hover:underline font-label-sm text-label-sm flex items-center gap-1" type="button">
                <span class="material-symbols-outlined text-sm">delete</span> 删除
              </button>
            </div>
          </div>
          <button class="w-full py-2.5 rounded-lg bg-surface-container-low hover:bg-surface-container text-primary font-label-lg text-label-lg font-semibold transition-all flex items-center justify-center gap-space-xs" type="button">
            <span class="material-symbols-outlined text-[20px]">add</span>
            <span>+ 添加更多学术项目 / 个人独立开发开源作品</span>
          </button>
        </section>
      </div>

      <!-- RIGHT SIDEBAR -->
      <aside class="lg:col-span-4 space-y-space-md lg:sticky lg:top-20">
        <!-- AI Resume Companion Card -->
        <div class="bg-gradient-to-br from-surface-container-lowest via-surface-container-low to-secondary-container/10 rounded-xl p-space-lg shadow-md relative overflow-hidden">
          <div class="absolute -right-4 -bottom-4 w-28 h-28 bg-primary/5 rounded-full blur-xl pointer-events-none"></div>
          <div class="flex items-center justify-between mb-space-sm">
            <div class="flex items-center gap-space-xs">
              <div class="w-7 h-7 rounded-lg bg-primary text-on-primary flex items-center justify-center shadow-sm">
                <span class="material-symbols-outlined text-[18px]">neurology</span>
              </div>
              <h3 class="font-headline-sm text-headline-sm text-on-surface font-bold">AI 智能求职伴侣</h3>
            </div>
            <span class="px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed font-label-sm text-label-sm font-semibold">校招智评</span>
          </div>
          <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm mb-space-md flex items-center justify-between">
            <div>
              <p class="font-label-sm text-label-sm text-on-surface-variant">综合竞争力评分</p>
              <div class="flex items-baseline gap-1 mt-0.5">
                <span class="font-display-hero-mobile text-display-hero-mobile text-primary font-bold">{{ score }}</span>
                <span class="font-label-md text-label-md text-on-surface-variant">/ 100</span>
              </div>
              <p class="font-label-sm text-label-sm text-secondary font-semibold mt-1">超越 94% 同届算法应聘者</p>
            </div>
            <div class="w-16 h-16 relative flex items-center justify-center">
              <svg class="w-full h-full -rotate-90" viewBox="0 0 36 36">
                <path class="text-surface-container stroke-current" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" fill="none" stroke-width="3.5"></path>
                <path class="text-primary stroke-current" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" fill="none" :stroke-dasharray="score + ', 100'" stroke-linecap="round" stroke-width="3.5"></path>
              </svg>
              <span class="material-symbols-outlined text-primary text-[20px] absolute">verified_user</span>
            </div>
          </div>
          <div class="space-y-space-sm font-body-sm text-body-sm">
            <div class="p-space-sm rounded-lg bg-surface-container-lowest shadow-sm space-y-1">
              <div class="flex items-center gap-1.5 text-secondary font-label-md text-label-md font-bold">
                <span class="material-symbols-outlined text-[18px]">check_circle</span>
                <span>量化成果表达极佳</span>
              </div>
              <p class="text-on-surface-variant leading-relaxed">
                在"智维未来"实习经历中，成功提炼了<strong>"TTFT 降低 42%"</strong>与<strong>"并发吞吐提升 35%"</strong>，能够直接击中技术主管评估痛点。
              </p>
            </div>
            <div class="p-space-sm rounded-lg bg-surface-container-lowest shadow-sm space-y-1">
              <div class="flex items-center gap-1.5 text-tertiary-container font-label-md text-label-md font-bold">
                <span class="material-symbols-outlined text-[18px]">flag</span>
                <span>待优化：增加底层性能关键词</span>
              </div>
              <p class="text-on-surface-variant leading-relaxed">
                建议在项目经历中补充说明显存占用降低量级（如由 24GB 降至 11GB），并标明 CUDA 算子或分布式通信优化细节。
              </p>
              <button class="text-primary hover:underline font-label-sm text-label-sm font-semibold pt-1 inline-block" type="button">
                一键应用推荐润色语料 →
              </button>
            </div>
          </div>
        </div>

        <!-- Quick Jump Anchor Navigator -->
        <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm">
          <h4 class="font-headline-sm text-headline-sm text-on-surface font-semibold mb-space-sm flex items-center gap-1.5">
            <span class="material-symbols-outlined text-on-surface-variant text-[18px]">format_list_bulleted</span>
            <span>简历模块导航</span>
          </h4>
          <nav class="space-y-1 font-body-md text-body-md">
            <a class="flex items-center justify-between px-3 py-2 rounded-lg text-on-surface-variant hover:bg-surface-container-low hover:text-primary transition-colors" href="#section-basic">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[18px]">person</span>基本信息</span>
              <span class="material-symbols-outlined text-sm text-secondary">check</span>
            </a>
            <a class="flex items-center justify-between px-3 py-2 rounded-lg text-on-surface-variant hover:bg-surface-container-low hover:text-primary transition-colors" href="#section-intention">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[18px]">explore</span>求职意向</span>
              <span class="material-symbols-outlined text-sm text-secondary">check</span>
            </a>
            <a class="flex items-center justify-between px-3 py-2 rounded-lg text-on-surface-variant hover:bg-surface-container-low hover:text-primary transition-colors" href="#section-education">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[18px]">school</span>教育背景</span>
              <span class="material-symbols-outlined text-sm text-secondary">check</span>
            </a>
            <a class="flex items-center justify-between px-3 py-2 rounded-lg text-on-surface-variant hover:bg-surface-container-low hover:text-primary transition-colors" href="#section-skills">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[18px]">psychology</span>技能特长</span>
              <span class="material-symbols-outlined text-sm text-secondary">check</span>
            </a>
            <a class="flex items-center justify-between px-3 py-2 rounded-lg bg-surface-container-low text-primary font-semibold" href="#section-experience">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[18px]">work</span>工作与实习经历</span>
              <span class="w-2 h-2 rounded-full bg-primary animate-pulse"></span>
            </a>
            <a class="flex items-center justify-between px-3 py-2 rounded-lg text-on-surface-variant hover:bg-surface-container-low hover:text-primary transition-colors" href="#section-projects">
              <span class="flex items-center gap-2"><span class="material-symbols-outlined text-[18px]">terminal</span>项目经历</span>
              <span class="font-label-sm text-label-sm text-on-surface-variant opacity-70">待完善</span>
            </a>
          </nav>
        </div>

        <!-- Campus Recruiting Benchmark Mini Card -->
        <div class="bg-surface-container-low rounded-xl p-space-md">
          <div class="flex items-center gap-2 mb-2">
            <span class="material-symbols-outlined text-primary text-[20px]">insights</span>
            <span class="font-label-lg text-label-lg text-on-surface font-semibold">2025届校招算法岗热度</span>
          </div>
          <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed">
            当前平台已有 1,420+ 家校招合作企业发布大模型/算法岗，完成项目经历填写后将自动点亮"优先投递专区"内推免笔试特权。
          </p>
        </div>
      </aside>
    </div>

    <!-- 4. Persistent Bottom Action Bar -->
    <div class="mt-space-2xl pt-space-md border-t border-surface-container sticky bottom-4 z-40">
      <div class="bg-surface-container-lowest/95 backdrop-blur-md rounded-xl p-space-md shadow-xl flex items-center justify-between gap-space-md">
        <div class="flex items-center gap-space-xs">
          <router-link class="inline-flex items-center gap-1.5 px-4 h-10 rounded-lg bg-surface-container-low text-on-surface font-label-lg text-label-lg hover:bg-surface-container transition-colors" to="/resume/skills">
            <span class="material-symbols-outlined text-[18px]">arrow_back</span>
            <span>上一步：技能特长</span>
          </router-link>
          <span class="font-label-sm text-label-sm text-on-surface-variant hidden sm:inline ml-2">步骤 5 / 6</span>
        </div>
        <div class="flex items-center gap-space-sm">
          <button class="inline-flex items-center gap-1 px-4 h-10 rounded-lg bg-surface-container-lowest hover:bg-surface-container-low text-on-surface font-label-lg text-label-lg transition-colors" type="button">
            <span class="material-symbols-outlined text-[18px] text-on-surface-variant">save</span>
            <span>暂存草稿</span>
          </button>
          <router-link class="inline-flex items-center gap-1.5 px-6 h-10 rounded-lg bg-primary text-on-primary font-label-lg text-label-lg shadow-md hover:bg-surface-tint active:scale-[0.98] transition-all font-semibold" to="/resume/projects">
            <span>下一步：完善项目经历</span>
            <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted } from 'vue'

const basicContentVisible = ref(true)
const autoSaveTime = ref('10:42')
const completeness = ref(88)
const score = ref(88)

const skills = ref([
  'Python / C++',
  'PyTorch & DeepSpeed',
  'Transformer 架构',
  'LangChain & RAG',
  'vLLM / TensorRT-LLM',
  '知识图谱与向量数据库',
  'Linux / Docker / K8s'
])

const formData = reactive({
  phone: '138-8800-2025',
  email: 'linchen_ai@pku.edu.cn',
  birthPolitical: '2000.11 / 中共党员'
})

const experience = reactive({
  company: '智维未来人工智能研究院 (北京) 有限公司',
  position: '大模型算法实习生 (LLM Algorithm Intern)',
  startDate: '2024年06月',
  endDate: '至今 (在职)',
  team: '通用生成智能实验室 · 导师：张教授',
  description: '1. 【高效微调架构】负责 70B 参数量开源基座模型的 LoRA 与 QLoRA 参数高效微调管线搭建，针对多轮垂直领域代码问答任务构建高质量合成数据集（20万条）。\n2. 【吞吐与显存优化】基于 vLLM 实施 PagedAttention 算子级推理优化，重构 KV Cache 缓存淘汰机制；在单机 8×A800 集群上使高并发首字时延 (TTFT) 降低 42%，单卡并发吞吐提升 35%。\n3. 【模型评估体系】联合算法导师研发自动化评测 Benchmark，覆盖逻辑推理、指令遵循与长上下文检索三大维度，输出专利 1 篇（第一学生发明人）。'
})

const charCount = computed(() => experience.description.length)
const quantifiedCount = computed(() => {
  const matches = experience.description.match(/\d+[%万篇条]/g)
  return matches ? matches.length : 0
})

function getSkillClass(index) {
  const classes = [
    'bg-primary text-on-primary shadow-sm',
    'bg-primary text-on-primary shadow-sm',
    'bg-primary-fixed text-on-primary-fixed',
    'bg-primary-fixed text-on-primary-fixed',
    'bg-secondary-fixed text-on-secondary-fixed',
    'bg-surface-container-high text-on-surface',
    'bg-surface-container-high text-on-surface'
  ]
  return classes[index % classes.length]
}

function removeSkill(index) {
  skills.value.splice(index, 1)
}

let saveInterval = null

onMounted(() => {
  saveInterval = setInterval(() => {
    const now = new Date()
    const hrs = String(now.getHours()).padStart(2, '0')
    const mins = String(now.getMinutes()).padStart(2, '0')
    autoSaveTime.value = `${hrs}:${mins}`
  }, 30000)
})

onUnmounted(() => {
  if (saveInterval) clearInterval(saveInterval)
})
</script>
