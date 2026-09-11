<template>
  <div class="min-h-screen bg-surface">
    <!-- Top Breadcrumb -->
    <section class="w-full bg-surface-container-lowest shadow-sm">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-2 flex flex-wrap items-center justify-between gap-2">
        <nav class="flex items-center gap-2 text-on-surface-variant text-sm">
          <router-link class="hover:text-primary transition-colors flex items-center gap-1" to="/home">
            <span class="material-symbols-outlined text-[16px]">home</span>
            <span>首页</span>
          </router-link>
          <span class="material-symbols-outlined text-[14px] text-outline-variant">chevron_right</span>
          <router-link class="hover:text-primary transition-colors" to="/jobs">职位搜索</router-link>
          <span class="material-symbols-outlined text-[14px] text-outline-variant">chevron_right</span>
          <span class="hover:text-primary transition-colors">技术研发</span>
          <span class="material-symbols-outlined text-[14px] text-outline-variant">chevron_right</span>
          <span class="text-on-surface font-semibold truncate max-w-xs md:max-w-md">大模型算法工程师 (校招/实习)</span>
        </nav>
        <div class="flex items-center gap-3">
          <span class="inline-flex items-center gap-1 px-2.5 py-1 bg-surface-container-high rounded-full text-sm text-primary font-medium">
            <span class="material-symbols-outlined text-[14px] text-primary">verified_user</span>
            官方认证推荐
          </span>
          <span class="text-on-surface-variant text-sm">职位编号: JOD-20250918-042</span>
        </div>
      </div>
    </section>

    <!-- Hero Card: Job Overview & CTA -->
    <section class="w-full py-4 md:py-6">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="bg-surface-container-lowest rounded-xl p-4 md:p-8 shadow-md relative overflow-hidden">
          <div class="absolute -right-20 -top-20 w-80 h-80 bg-primary-container/10 rounded-full blur-3xl pointer-events-none"></div>
          <div class="absolute right-1/3 bottom-0 w-64 h-32 bg-secondary-container/10 rounded-full blur-2xl pointer-events-none"></div>
          <div class="relative z-10 grid grid-cols-1 lg:grid-cols-12 gap-6 lg:gap-8 items-start">
            <!-- Left: Job Specs -->
            <div class="lg:col-span-7 flex flex-col gap-3">
              <div class="flex flex-wrap items-center gap-2 mb-1">
                <span class="px-2.5 py-0.5 rounded-full bg-primary-container text-on-primary text-sm tracking-wide">2025届校招</span>
                <span class="px-2.5 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed-variant text-sm">可转正实习</span>
                <span class="px-2.5 py-0.5 rounded-full bg-surface-container text-on-surface-variant text-sm flex items-center gap-1">
                  <span class="material-symbols-outlined text-[13px] text-primary">school</span>
                  高校直聘示范岗
                </span>
              </div>
              <h1 class="text-3xl font-bold text-on-surface tracking-tight leading-tight">
                大模型算法工程师 <span class="text-xl font-normal text-on-surface-variant">(2025届校招/可转正实习)</span>
              </h1>
              <div class="flex flex-wrap items-baseline gap-3 mt-1">
                <div class="text-4xl text-primary tracking-tight font-bold">
                  25k - 40k <span class="text-lg text-primary font-medium">· 16薪</span>
                </div>
                <div class="px-2.5 py-1 rounded bg-tertiary-fixed text-on-tertiary-fixed text-sm">或 600-800元/天 (实习期)</div>
              </div>
              <div class="flex flex-wrap items-center gap-y-2 gap-x-4 text-on-surface-variant text-base pt-1">
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[18px] text-secondary">location_on</span>
                  北京 · 中关村清华科技园
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[18px] text-secondary">school</span>
                  硕士及以上
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[18px] text-secondary">group</span>
                  招聘 3 人
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[18px] text-secondary">schedule</span>
                  全职 / 实习均可
                </span>
                <span class="flex items-center gap-1">
                  <span class="material-symbols-outlined text-[18px] text-secondary">domain_verification</span>
                  2025届应届 / 2026届实习
                </span>
              </div>
              <div class="flex flex-wrap gap-2 pt-2">
                <div class="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-surface-container-low text-primary text-sm font-semibold">
                  <span class="material-symbols-outlined text-[16px]">military_tech</span>
                  校企联合育人基地
                </div>
                <div class="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-surface-container-low text-primary text-sm font-semibold">
                  <span class="material-symbols-outlined text-[16px]">handshake</span>
                  重点高校直聘通道
                </div>
                <div class="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-surface-container-low text-secondary text-sm font-semibold">
                  <span class="material-symbols-outlined text-[16px]">trending_up</span>
                  实习转正率 92%
                </div>
                <div class="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-surface-container-low text-on-surface-variant text-sm font-semibold">
                  <span class="material-symbols-outlined text-[16px]">draw</span>
                  支持网签三方协议
                </div>
              </div>
            </div>

            <!-- Right: Action Center -->
            <div class="lg:col-span-5 flex flex-col justify-between h-full bg-surface-container-low/70 rounded-xl p-4 md:p-6">
              <!-- HR Quick Insight -->
              <div class="flex items-center gap-3 pb-3">
                <div class="relative shrink-0">
                  <img class="w-12 h-12 rounded-full object-cover shadow-sm" src="https://lh3.googleusercontent.com/aida-public/AB6AXuA5QKKNVJp4zLX13nzIh6kTNk516-WZGscT9hvkG6AYpHlLe3ASy54tggmU4RwEk9i7XvoEoNpG2QZrNsZZA6h-yj65wrcmwkIZO5EluzMVm95rHcEp13GrqohClDp5NcG9Nx5tt-8utMzLex46jWmOMlQ_M53x05AIY1ZijXkUSiu7UhwMYGkOgWWErn3WohCQ3njVDOt9XHy23tiqJhcgH9_ozGdVX8hBfgQ9rpBD94ZYtvbGHRDFZA" alt="HR" />
                  <span class="absolute bottom-0 right-0 w-3.5 h-3.5 bg-secondary-container rounded-full ring-2 ring-surface-container-lowest" title="在线"></span>
                </div>
                <div class="flex flex-col min-w-0">
                  <div class="flex items-center gap-1.5">
                    <span class="text-lg text-on-surface font-semibold">张经理</span>
                    <span class="px-1.5 py-0.5 rounded bg-surface-container-high text-primary text-xs">校招技术招聘负责人</span>
                  </div>
                  <p class="text-sm text-on-surface-variant flex items-center gap-1 truncate mt-0.5">
                    <span class="inline-block w-2 h-2 rounded-full bg-primary animate-pulse"></span>
                    今日活跃 · 平均 24 小时内极速初筛
                  </p>
                </div>
              </div>

              <!-- Primary Actions -->
              <div class="flex flex-col gap-2 mt-3">
                <div class="flex items-center gap-2">
                  <button
                    class="flex-1 py-3 px-6 bg-gradient-to-r from-primary-container to-primary text-on-primary text-base rounded-lg shadow-md hover:shadow-lg hover:brightness-105 active:scale-[0.99] transition-all flex items-center justify-center gap-2"
                    @click="showApplyModal = true"
                  >
                    <span class="material-symbols-outlined text-[20px]">send</span>
                    <span>立即投递简历</span>
                  </button>
                  <button class="px-4 py-3 bg-surface-container-lowest text-primary text-base rounded-lg shadow-sm hover:bg-surface-container hover:text-on-primary-fixed transition-colors flex items-center justify-center gap-1.5 shrink-0">
                    <span class="material-symbols-outlined text-[20px]">forum</span>
                    <span>与HR直聊</span>
                  </button>
                </div>
                <div class="flex items-center justify-between gap-3 pt-1 text-on-surface-variant text-sm">
                  <button class="flex items-center gap-1.5 hover:text-primary transition-colors py-1 px-2 rounded hover:bg-surface-container-high" @click="toggleFavorite">
                    <span class="material-symbols-outlined text-[18px]"                     :style="isFavorited ? { 'font-variation-settings': 'FILL 1' } : {}">{{ isFavorited ? 'bookmark' : 'bookmark_border' }}</span>
                    <span>{{ isFavorited ? '已收藏' : '收藏职位' }}</span>
                  </button>
                  <button class="flex items-center gap-1.5 hover:text-primary transition-colors py-1 px-2 rounded hover:bg-surface-container-high" @click="shareLink">
                    <span class="material-symbols-outlined text-[18px]">share</span>
                    <span>{{ shareText }}</span>
                  </button>
                  <div class="flex items-center gap-1 text-on-surface-variant/80 text-xs">
                    <span class="material-symbols-outlined text-[15px]">visibility</span>
                    <span>3,418 次浏览</span>
                  </div>
                </div>
              </div>

              <!-- Match Score Banner -->
              <div class="mt-3 p-3 rounded-lg bg-surface-container-lowest flex items-center justify-between shadow-sm">
                <div class="flex items-center gap-2">
                  <div class="w-8 h-8 rounded-full bg-secondary-fixed flex items-center justify-center text-on-secondary-fixed">
                    <span class="material-symbols-outlined text-[18px]">auto_awesome</span>
                  </div>
                  <div class="flex flex-col">
                    <span class="text-sm text-on-surface font-semibold">林晨的档案与职位匹配度</span>
                    <span class="text-xs text-on-surface-variant">专业与AI算法项目背景高度契合</span>
                  </div>
                </div>
                <div class="text-right">
                  <span class="text-xl text-primary font-bold">96%</span>
                  <span class="text-xs text-secondary block -mt-1 font-medium">极高匹配</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Main Content: 70/30 Split -->
    <section class="w-full pb-12">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="grid grid-cols-1 lg:grid-cols-12 gap-6 lg:gap-8 items-start">
          <!-- LEFT COLUMN: Job Modules -->
          <main class="lg:col-span-8 flex flex-col gap-6">
            <!-- Module 1: Job Duties -->
            <article class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm">
              <header class="flex items-center justify-between pb-3 mb-4">
                <div class="flex items-center gap-2">
                  <div class="w-1.5 h-6 bg-primary rounded-full"></div>
                  <h2 class="text-xl font-semibold text-on-surface">工作内容 / 岗位职责</h2>
                </div>
                <span class="px-2.5 py-1 rounded bg-surface-container-low text-on-surface-variant text-xs">核心研发部门直聘</span>
              </header>
              <div class="space-y-4 text-on-surface-variant leading-relaxed">
                <p class="text-base text-on-surface">
                  你将加入智航未来基础大模型实验室（LLM Research Lab），同顶级科学家和工程架构师并肩，主导从基座模型预训练至千亿级参数多模态端到端落地。具体职责涵盖：
                </p>
                <div class="grid grid-cols-1 gap-3 pt-2">
                  <div v-for="(duty, index) in duties" :key="index" class="flex items-start gap-3 p-3 rounded-lg bg-surface-container-low/50 hover:bg-surface-container-low transition-colors">
                    <div class="w-7 h-7 rounded-md bg-primary-fixed text-on-primary-fixed flex items-center justify-center shrink-0 mt-0.5">
                      <span class="text-sm font-bold">{{ String(index + 1).padStart(2, '0') }}</span>
                    </div>
                    <div class="flex-1">
                      <h3 class="text-base text-on-surface font-medium">{{ duty.title }}</h3>
                      <p class="text-sm text-on-surface-variant mt-1">{{ duty.desc }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </article>

            <!-- Module 2: Job Requirements -->
            <article class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm">
              <header class="flex items-center justify-between pb-3 mb-4">
                <div class="flex items-center gap-2">
                  <div class="w-1.5 h-6 bg-primary rounded-full"></div>
                  <h2 class="text-xl font-semibold text-on-surface">任职要求</h2>
                </div>
                <span class="text-sm text-secondary flex items-center gap-1 font-medium">
                  <span class="material-symbols-outlined text-[16px]">verified</span>
                  学历要求可在线校招验证
                </span>
              </header>
              <ul class="space-y-3 text-on-surface-variant">
                <li v-for="(req, index) in requirements" :key="index" class="flex items-start gap-3">
                  <span class="material-symbols-outlined text-primary text-[20px] shrink-0 mt-0.5">check_circle</span>
                  <div>
                    <strong class="text-on-surface font-semibold">{{ req.label }}</strong>
                    {{ req.text }}
                  </div>
                </li>
              </ul>
              <div class="mt-6 pt-4 border-t border-surface-container flex flex-wrap items-center gap-2">
                <span class="text-xs text-on-surface-variant font-semibold uppercase tracking-wider mr-2">技术关键词:</span>
                <span v-for="tag in techTags" :key="tag" class="px-2.5 py-1 bg-surface-container rounded text-sm text-on-surface">{{ tag }}</span>
              </div>
            </article>

            <!-- Module 3: Benefits -->
            <article class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm">
              <header class="flex items-center justify-between pb-3 mb-4">
                <div class="flex items-center gap-2">
                  <div class="w-1.5 h-6 bg-primary rounded-full"></div>
                  <h2 class="text-xl font-semibold text-on-surface">福利待遇与培养通道</h2>
                </div>
                <span class="px-2 py-0.5 rounded bg-tertiary-fixed text-on-tertiary-fixed text-xs font-semibold">高校毕业生特别保障计划</span>
              </header>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div v-for="(benefit, index) in benefits" :key="index" class="p-4 rounded-xl bg-surface-container-low/70 flex flex-col justify-between">
                  <div>
                    <div class="w-10 h-10 rounded-lg flex items-center justify-center mb-2" :class="benefit.iconBg">
                      <span class="material-symbols-outlined text-[24px]" :class="benefit.iconColor">{{ benefit.icon }}</span>
                    </div>
                    <h3 class="text-base text-on-surface font-semibold">{{ benefit.title }}</h3>
                    <p class="text-sm text-on-surface-variant mt-2">{{ benefit.desc }}</p>
                  </div>
                  <div class="mt-3 pt-2 text-sm font-semibold flex items-center gap-1" :class="benefit.linkColor">
                    <span>{{ benefit.link }}</span>
                    <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
                  </div>
                </div>
              </div>
            </article>

            <!-- Module 4: Recruitment Process -->
            <article class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm">
              <header class="flex items-center justify-between pb-3 mb-4">
                <div class="flex items-center gap-2">
                  <div class="w-1.5 h-6 bg-primary rounded-full"></div>
                  <h2 class="text-xl font-semibold text-on-surface">校园招聘面试考核流程</h2>
                </div>
                <span class="text-sm text-on-surface-variant flex items-center gap-1">
                  <span class="material-symbols-outlined text-[16px] text-secondary">bolt</span>
                  全流程预计 10-14 个工作日
                </span>
              </header>
              <p class="text-sm text-on-surface-variant mb-6">
                支持全流程线上远程无纸化考核。如在京同学亦可选择清华科技园研发中心线下面谈，体验团队科研研发氛围。
              </p>
              <div class="relative py-2">
                <div class="grid grid-cols-2 md:grid-cols-6 gap-3 relative z-10">
                  <div v-for="(step, index) in processSteps" :key="index" class="flex flex-col items-center text-center p-2 bg-surface-container-low/40 rounded-xl hover:bg-surface-container-low transition-colors">
                    <div class="w-10 h-10 rounded-full flex items-center justify-center font-bold shadow-sm mb-2" :class="step.bg">
                      <span class="material-symbols-outlined text-[20px]" :class="step.iconColor">{{ step.icon }}</span>
                    </div>
                    <span class="text-xs text-on-surface font-semibold">{{ step.label }}</span>
                    <span class="text-xs text-on-surface-variant scale-90 mt-1">{{ step.sub }}</span>
                  </div>
                </div>
              </div>
              <div class="mt-4 p-3 rounded-lg bg-surface-container-low flex items-start gap-2">
                <span class="material-symbols-outlined text-secondary text-[20px] shrink-0 mt-0.5">info</span>
                <p class="text-sm text-on-surface-variant">
                  温馨提醒：已入围春招/秋招重点人才池的同学，凭获奖竞赛证书可申请免除「在线算法笔试」直接进入专家面环节。
                </p>
              </div>
            </article>
          </main>

          <!-- RIGHT COLUMN: Company & Recommendations -->
          <aside class="lg:col-span-4 flex flex-col gap-6">
            <!-- Enterprise Profile -->
            <div class="bg-surface-container-lowest rounded-xl p-4 md:p-6 shadow-sm">
              <div class="flex items-center gap-3 pb-3">
                <img class="w-14 h-14 rounded-xl object-contain p-1 bg-surface-container-low shadow-sm shrink-0" src="https://lh3.googleusercontent.com/aida-public/AB6AXuA_-JBh5jqIAF9_nfXNlpSVzRW1YHaqismDEgrlYkal64-fKyJkw4DNH_w43cQ1WhqEoFMLMMmSumqzjxoQIo6NEOgUo8VkYAsIYLTZir6HHSAu_9boHV1bBeKNgG8bjpJh2PU6xc2754Dr1JF-_nJvrGeT5Z7rd2SaLesJ4eysapUChbGAvc35N8f4djZ8ElrSj9_F0EaFLaq2PhSo3m5_TWUq2Bgnft8qgWkQHT6Sszh0kcENRLLCDw" alt="Logo" />
                <div class="flex flex-col min-w-0">
                  <div class="flex items-center gap-1">
                    <h3 class="text-base text-on-surface font-bold truncate">智航未来科技</h3>
                    <span class="material-symbols-outlined text-primary text-[18px]" title="高校联合官方认证企业">verified</span>
                  </div>
                  <span class="text-xs text-secondary font-medium mt-0.5">已通过教育部校企官方认证</span>
                </div>
              </div>
              <div class="grid grid-cols-2 gap-2 my-3 p-3 rounded-lg bg-surface-container-low text-center">
                <div>
                  <span class="text-sm text-on-surface-variant block">人员规模</span>
                  <span class="text-base text-on-surface font-semibold">500-999人</span>
                </div>
                <div>
                  <span class="text-sm text-on-surface-variant block">融资阶段</span>
                  <span class="text-base text-primary font-semibold">C轮 · 独角兽</span>
                </div>
              </div>
              <div class="space-y-2 text-sm text-on-surface-variant">
                <div class="flex items-start gap-2">
                  <span class="material-symbols-outlined text-[18px] text-outline shrink-0 mt-0.5">category</span>
                  <span>所属行业：人工智能 / 大模型与生成式AI</span>
                </div>
                <div class="flex items-start gap-2">
                  <span class="material-symbols-outlined text-[18px] text-outline shrink-0 mt-0.5">military_tech</span>
                  <span>资质荣誉：国家高新技术企业 · 专精特新"小巨人"</span>
                </div>
                <div class="flex items-start gap-2">
                  <span class="material-symbols-outlined text-[18px] text-outline shrink-0 mt-0.5">pin_drop</span>
                  <span class="line-clamp-2">北京市海淀区中关村东路1号清华科技园大厦B座8层</span>
                </div>
              </div>
              <div class="mt-4 rounded-xl overflow-hidden shadow-sm relative">
                <div class="w-full h-36 bg-cover bg-center" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuAqztn-enbBbvhc2kRm79utmJ4WXHKPCbR641wHRRY5aFgCSJ7Z_eQmwArl1sRyqorf75dwHCNzGkRYrtDap_Z7ZOKxtVs2Q9eLlB9gfO8BFFCWTWjPgS8hso3IgJuhX6BRW-mwkUvzXtb8TUaPNjSVzjg4YTuLpkzlM1oKZ-scEL42Tov7FvCvywBsO-fMkKDz93qOCSJGdTObTe5Cek7S9633VtMK-0wsPreesn8kDn_SlZhGl0Hc8A')"></div>
                <div class="absolute bottom-2 left-2 right-2 p-2 bg-surface-container-lowest/90 backdrop-blur-sm rounded-lg flex items-center justify-between text-xs">
                  <span class="text-sm text-on-surface font-medium truncate">地铁13号线 / 15号线直达园区</span>
                  <a class="text-primary text-sm hover:underline shrink-0" href="#">查看路线</a>
                </div>
              </div>
              <div class="mt-4 pt-2">
                <router-link class="w-full py-2.5 px-4 bg-surface-container hover:bg-surface-container-high text-primary text-sm font-semibold rounded-lg transition-colors flex items-center justify-center gap-1.5" to="/jobs">
                  <span>查看企业主页与全部在招校招职位</span>
                  <span class="px-1.5 py-0.2 bg-primary text-on-primary text-xs rounded-full">18</span>
                </router-link>
              </div>
            </div>

            <!-- Safety & Compliance -->
            <div class="bg-surface-container-lowest rounded-xl p-4 md:p-6 shadow-sm">
              <div class="flex items-center gap-2 mb-2 text-primary">
                <span class="material-symbols-outlined text-[24px]">gavel</span>
                <h3 class="text-base text-on-surface font-semibold">校招安全与合规保障</h3>
              </div>
              <p class="text-sm text-on-surface-variant mb-3">
                本平台所有发布的招聘信息均受教育部全国高校学生科技创业就业指导中心监察，保障学生合法权益。
              </p>
              <div class="space-y-2">
                <div class="flex items-center gap-2 p-2 rounded-lg bg-surface-container-low text-on-surface text-sm">
                  <span class="material-symbols-outlined text-secondary text-[18px]">verified</span>
                  <span>教育部全国高校就业中心审核备案</span>
                </div>
                <div class="flex items-center gap-2 p-2 rounded-lg bg-surface-container-low text-on-surface text-sm">
                  <span class="material-symbols-outlined text-secondary text-[18px]">fact_check</span>
                  <span>企业主体资质与真实在招承诺</span>
                </div>
                <div class="flex items-center gap-2 p-2 rounded-lg bg-surface-container-low text-on-surface text-sm">
                  <span class="material-symbols-outlined text-secondary text-[18px]">money_off</span>
                  <span>严正承诺：不收取任何培训或押金费用</span>
                </div>
              </div>
              <div class="mt-3 pt-2 text-right">
                <a class="text-on-surface-variant hover:text-error text-xs inline-flex items-center gap-1" href="#">
                  <span class="material-symbols-outlined text-[14px]">flag</span>
                  <span>职位违规举报通道</span>
                </a>
              </div>
            </div>

            <!-- Similar Jobs -->
            <div class="bg-surface-container-lowest rounded-xl p-4 md:p-6 shadow-sm">
              <div class="flex items-center justify-between mb-3">
                <div class="flex items-center gap-1.5">
                  <span class="material-symbols-outlined text-secondary text-[22px]">hub</span>
                  <h3 class="text-base text-on-surface font-semibold">相似优质职位推荐</h3>
                </div>
                <a class="text-xs text-primary hover:underline" href="#">换一批</a>
              </div>
              <div class="space-y-3">
                <router-link v-for="job in similarJobs" :key="job.title" class="block p-3 rounded-lg bg-surface-container-low/40 hover:bg-surface-container-low transition-all group" :to="`/jobs/${job.id}`">
                  <div class="flex items-start justify-between gap-2">
                    <h4 class="text-sm text-on-surface font-medium group-hover:text-primary transition-colors line-clamp-1">{{ job.title }}</h4>
                    <span class="text-sm text-primary font-bold shrink-0">{{ job.salary }}</span>
                  </div>
                  <div class="flex items-center justify-between text-xs text-on-surface-variant mt-1.5">
                    <span class="flex items-center gap-1">
                      <span class="material-symbols-outlined text-[14px]">corporate_fare</span>
                      {{ job.company }}
                    </span>
                    <span>{{ job.location }} · {{ job.edu }}</span>
                  </div>
                </router-link>
              </div>
              <div class="mt-4">
                <router-link class="w-full block text-center py-2 text-primary text-sm hover:bg-surface-container-low rounded-lg transition-colors" to="/jobs">
                  探索更多人工智能算法职位 →
                </router-link>
              </div>
            </div>
          </aside>
        </div>
      </div>
    </section>

    <!-- Mobile Bottom Bar -->
    <div class="lg:hidden fixed bottom-0 left-0 right-0 z-40 bg-surface-container-lowest shadow-xl p-3 flex items-center justify-between gap-3">
      <div class="flex flex-col">
        <span class="text-base text-primary font-bold">25k-40k · 16薪</span>
        <span class="text-xs text-on-surface-variant">智航未来科技 · 大模型岗</span>
      </div>
      <div class="flex items-center gap-2">
        <button class="p-2.5 bg-surface-container-high text-primary rounded-lg">
          <span class="material-symbols-outlined text-[20px]">forum</span>
        </button>
        <button class="px-5 py-2.5 bg-primary text-on-primary text-sm rounded-lg shadow-sm font-semibold flex items-center gap-1.5" @click="showApplyModal = true">
          <span class="material-symbols-outlined text-[18px]">send</span>
          <span>立即投递</span>
        </button>
      </div>
    </div>

    <!-- Apply Confirmation Modal -->
    <div
      v-if="showApplyModal"
      class="fixed inset-0 z-50 flex items-center justify-center bg-on-surface/50 backdrop-blur-sm transition-opacity duration-200"
      @click.self="showApplyModal = false"
    >
      <div class="bg-surface-container-lowest rounded-2xl max-w-md w-full mx-4 p-6 shadow-2xl transform transition-transform duration-200">
        <div class="flex items-center justify-between pb-3 border-b border-surface-container">
          <div class="flex items-center gap-2">
            <span class="material-symbols-outlined text-primary text-[24px]">task_alt</span>
            <h3 class="text-base text-on-surface font-bold">确认投递职位</h3>
          </div>
          <button class="text-on-surface-variant hover:text-on-surface" @click="showApplyModal = false">
            <span class="material-symbols-outlined text-[22px]">close</span>
          </button>
        </div>
        <div class="py-4 space-y-3">
          <p class="text-base text-on-surface">
            您正在向 <strong class="text-primary">智航未来科技有限公司</strong> 投递以下岗位：
          </p>
          <div class="p-3 bg-surface-container-low rounded-lg">
            <p class="text-base text-on-surface font-semibold">大模型算法工程师 (2025届校招/可转正实习)</p>
            <p class="text-sm text-on-surface-variant mt-1">默认附带简历：<strong>林晨_计算机硕士_算法方向.pdf</strong></p>
          </div>
          <label class="flex items-center gap-2 text-on-surface-variant text-sm pt-2">
            <input checked type="checkbox" class="w-4 h-4 rounded text-primary focus:ring-primary" />
            <span>同时开启企业HR极速在线交流通道</span>
          </label>
        </div>
        <div class="flex items-center justify-end gap-2 pt-3">
          <button class="px-4 py-2 bg-surface-container text-on-surface rounded-lg text-sm hover:bg-surface-container-high transition-colors" @click="showApplyModal = false">
            取消
          </button>
          <button
            class="px-5 py-2 bg-primary text-on-primary rounded-lg text-sm font-semibold hover:bg-primary/90 transition-colors shadow-sm"
            :disabled="applying"
            @click="confirmApply"
          >
            {{ applying ? '投递中...' : '确认投递' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useJobStore } from '../../stores/job'
import { addFavorite, removeFavorite } from '../../api/job'
import { submitApplication } from '../../api/application'

const route = useRoute()
const router = useRouter()
const jobStore = useJobStore()

const job = ref(null)
const showApplyModal = ref(false)
const isFavorited = ref(false)
const applying = ref(false)
const shareText = ref('分享岗位')

const duties = [
  { title: '千亿级大模型预训练与优化', desc: '深入参与千亿级多模态大语言模型（LLM）的预训练数据飞轮构建、高质量合成数据清洗、指令微调（SFT）与基于人类反馈的强化学习（RLHF / DPO）算法迭代。' },
  { title: '垂直行业 Agent 与 RAG 体系构建', desc: '负责复杂业务场景下的自主 Agent 工作流编排、工具调用推理（Tool Use / Function Calling）以及基于语义图谱的高精度 RAG 知识检索增强引擎技术攻坚。' },
  { title: '前沿学术突破与论文发表', desc: '持续探索国际前沿学术进展（包括长上下文建模、思维链 CoT、自省式对齐等），协助发表顶级人工智能学术会议论文（NeurIPS, ICML, ICLR, CVPR, ACL 等）并申请核心发明专利。' }
]

const requirements = [
  { label: '学历与专业背景：', text: '2025届毕业生或2026届在读研究生，计算机科学、人工智能、自动化、应用数学、软件工程或电子信息等相关专业硕士或博士学历。' },
  { label: '技术栈掌握深度：', text: '熟练掌握 Python 与现代 C++ 语言，对数据结构与算法有扎实体会；深入理解 PyTorch 框架，熟悉 Megatron-LM、DeepSpeed、vLLM 或 TensorRT-LLM 等主流分布式预训练与量化推理框架。' },
  { label: '竞赛与顶会优先（加分项）：', text: '在 ACM-ICPC、CCPC、Kaggle、天池或国内外知名顶会前沿竞赛中取得区域金奖/前列名次者优先；有以第一作者身份在 NeurIPS、ICML、CVPR、ACL 等顶级会议发表过学术论文者优先。' },
  { label: '自我驱动与协同：', text: '对大模型与通用人工智能（AGI）保有强烈的好奇心与技术激情，具备纯熟的英文学术文献检索与快速复现能力，有严谨的代码工程习惯与跨学科协同作战素养。' }
]

const techTags = ['Python', 'PyTorch', 'DeepSpeed', 'Megatron-LM', 'RLHF / PPO', 'RAG 检索增强', 'Agent 开发']

const benefits = [
  { icon: 'payments', title: '丰厚薪资与安居支持', desc: '年度年终奖（3-6个月）、高比例六险二金（公积金全额按12%顶格缴纳）。正式入职满一年即可享受首套房 50 万元免息安家购房贷款借款计划。', link: '六险二金顶格配置', iconBg: 'bg-primary-container text-on-primary', linkColor: 'text-primary' },
  { icon: 'supervisor_account', title: '一对一顶级专家导师', desc: '应届生与实习生全员配备 Staff / Principal Engineer 级别学术技术专家，全程定制成长路径，从工程落地到学术论文全链路传帮带。', link: '专属双轨成长晋升线', iconBg: 'bg-secondary-fixed-dim text-on-secondary-fixed', linkColor: 'text-secondary' },
  { icon: 'apartment', title: '生活全方位关怀', desc: '北京户口择优重点申报指标；提供青年专属品质公寓或每月 2,500 元租房补贴；三餐自助免费、星级下午茶、园区免费健身房与高端三甲体检。', link: '应届落户指标择优统筹', iconBg: 'bg-surface-container text-on-surface', linkColor: 'text-on-surface' },
  { icon: 'assignment_turned_in', title: '转正保障与三方签订', desc: '实习期满考核合格直接转正，历史转正率高达 92%；通过全国高校就业平台直接在线网签《全国高校毕业生就业协议书》，毕业无缝入职。', link: '极速办理电子三方', iconBg: 'bg-tertiary-fixed text-on-tertiary-fixed', linkColor: 'text-tertiary' }
]

const processSteps = [
  { icon: 'upload_file', label: '01 投递简历', sub: '1-2日内初审', bg: 'bg-primary text-on-primary', iconColor: '' },
  { icon: 'terminal', label: '02 算法笔试', sub: '周末线上统考', bg: 'bg-surface-container text-on-surface', iconColor: '' },
  { icon: 'code', label: '03 技术初面', sub: '核心技术导师', bg: 'bg-surface-container text-on-surface', iconColor: '' },
  { icon: 'psychology', label: '04 专家复面', sub: '首席科学家/负责人', bg: 'bg-surface-container text-on-surface', iconColor: '' },
  { icon: 'support_agent', label: '05 HR综合面', sub: '薪酬与意向洽谈', bg: 'bg-surface-container text-on-surface', iconColor: '' },
  { icon: 'celebration', label: '06 发放Offer', sub: '网签三方协议', bg: 'bg-secondary-fixed text-on-secondary-fixed', iconColor: '' }
]

const similarJobs = [
  { id: 1, title: '具身智能算法工程师 (实习)', salary: '20k-35k', company: '智元机器人', location: '北京 / 上海', edu: '硕士' },
  { id: 2, title: 'AIGC多模态研发实习生', salary: '400-600元/天', company: '腾讯云智研发中心', location: '北京', edu: '本硕在校' },
  { id: 3, title: '深度学习平台开发工程师', salary: '22k-38k', company: '华为中央研究院', location: '北京', edu: '2025应届' }
]

const fetchJobDetail = async () => {
  const id = route.params.id
  if (id) {
    await jobStore.fetchJobDetail(id)
    job.value = jobStore.currentJob
  }
}

const confirmApply = async () => {
  applying.value = true
  try {
    const id = route.params.id
    await submitApplication({ jobId: id })
    showApplyModal.value = false
  } catch (e) {
    console.error('Failed to apply:', e)
  } finally {
    applying.value = false
  }
}

const toggleFavorite = async () => {
  try {
    const id = route.params.id
    if (isFavorited.value) {
      await removeFavorite(id)
    } else {
      await addFavorite(id)
    }
    isFavorited.value = !isFavorited.value
  } catch (e) {
    console.error('Failed to toggle favorite:', e)
  }
}

const shareLink = () => {
  if (navigator.clipboard) {
    navigator.clipboard.writeText(window.location.href)
    shareText.value = '链接已复制'
    setTimeout(() => { shareText.value = '分享岗位' }, 2000)
  }
}

onMounted(() => {
  fetchJobDetail()
})
</script>
