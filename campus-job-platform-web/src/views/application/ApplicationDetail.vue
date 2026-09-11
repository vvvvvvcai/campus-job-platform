<template>
  <div class="min-h-screen bg-surface">
    <!-- Top Breadcrumb -->
    <section class="w-full bg-surface-container-lowest shadow-sm">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-2 flex flex-wrap items-center justify-between gap-2">
        <nav class="flex items-center gap-2 text-on-surface-variant text-sm">
          <router-link class="hover:text-primary transition-colors" to="/profile">个人中心</router-link>
          <span class="material-symbols-outlined text-[14px] text-outline-variant">chevron_right</span>
          <router-link class="hover:text-primary transition-colors" to="/applications">投递记录</router-link>
          <span class="material-symbols-outlined text-[14px] text-outline-variant">chevron_right</span>
          <span class="text-on-surface font-semibold">投递详情</span>
          <span class="ml-2 px-2.5 py-0.5 rounded-full bg-surface-container-highest text-on-surface-variant text-xs font-mono tracking-wider">
            {{ applicationId }}
          </span>
        </nav>
      </div>
    </section>

    <!-- Status Hero Banner -->
    <section class="w-full py-4 md:py-6">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="relative overflow-hidden rounded-xl bg-gradient-to-r from-primary-container via-surface-tint to-secondary text-on-primary p-6 md:p-8 shadow-xl">
          <div class="absolute -right-16 -top-16 w-96 h-96 bg-secondary-container/20 rounded-full blur-3xl pointer-events-none"></div>
          <div class="absolute right-1/3 -bottom-24 w-64 h-64 bg-inverse-primary/10 rounded-full blur-2xl pointer-events-none"></div>
          <div class="relative z-10 flex flex-col lg:flex-row lg:items-center justify-between gap-6">
            <div class="space-y-4 max-w-3xl">
              <div class="flex flex-wrap items-center gap-3">
                <span class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-surface-container-lowest/20 backdrop-blur-md text-surface-container-lowest text-sm tracking-wide uppercase">
                  <span class="w-2 h-2 rounded-full bg-secondary-container animate-ping"></span>
                  当前阶段
                </span>
                <span class="text-xl md:text-2xl tracking-tight font-bold text-surface-container-lowest">
                  面试进行中 · 线上技术二面
                </span>
              </div>
              <p class="text-base text-on-primary-container leading-relaxed">
                恭喜您已顺利通过初筛与技术一面，企业已发起第二轮深度技术面试。请提前完成设备自检并在开考前 10 分钟准时进入线上考场。
              </p>
              <div class="flex flex-wrap items-center gap-4 pt-1 text-sm text-surface-variant">
                <span class="inline-flex items-center gap-1">
                  <span class="material-symbols-outlined text-[18px]">schedule</span>
                  距离面试开始还有：
                  <strong class="text-surface-container-lowest font-semibold font-mono text-base">26小时 15分</strong>
                </span>
                <span class="inline-flex items-center gap-1">
                  <span class="material-symbols-outlined text-[18px]">verified_user</span>
                  双机位监考 / 实时代码协同已就绪
                </span>
              </div>
            </div>
            <div class="flex flex-wrap sm:flex-nowrap lg:flex-col xl:flex-row gap-3 shrink-0">
              <a class="flex-1 inline-flex items-center justify-center gap-2 px-6 py-3 bg-surface-container-lowest text-primary hover:bg-surface-container-lowest/90 transition-all duration-200 rounded-lg shadow-md text-base font-bold group" href="#video-room">
                <span class="material-symbols-outlined text-primary group-hover:scale-110 transition-transform">videocam</span>
                <span>进入线上视频考场</span>
              </a>
              <button class="inline-flex items-center justify-center gap-2 px-4 py-3 bg-surface-container-lowest/15 hover:bg-surface-container-lowest/25 text-surface-container-lowest backdrop-blur-md rounded-lg text-base transition-colors" type="button">
                <span class="material-symbols-outlined">chat</span>
                <span>与面试官直聊</span>
              </button>
              <button
                class="inline-flex items-center justify-center gap-2 px-4 py-3 bg-surface-container-lowest/15 hover:bg-surface-container-lowest/25 text-surface-container-lowest backdrop-blur-md rounded-lg text-base transition-colors"
                type="button"
                @click="calendarAdded = !calendarAdded"
              >
                <span class="material-symbols-outlined">{{ calendarAdded ? 'event_available' : 'event' }}</span>
                <span>{{ calendarAdded ? '已添加' : '添加至日历' }}</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Application Stepper Timeline -->
    <section class="w-full">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm space-y-6">
          <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2">
            <div>
              <h2 class="text-lg md:text-xl text-on-surface font-bold tracking-tight">求职推进轨迹</h2>
              <p class="text-sm text-on-surface-variant">系统全程存证保障，关键节点实时推送通知并同步高校就业系统</p>
            </div>
            <div class="flex items-center gap-2">
              <span class="w-2.5 h-2.5 rounded-full bg-primary animate-pulse"></span>
              <span class="text-sm font-medium text-primary">流程时效正常 (平均流转率超 92% 同类岗位)</span>
            </div>
          </div>

          <!-- Horizontal Stepper -->
          <div class="relative overflow-x-auto pb-4 pt-2">
            <div class="min-w-[980px] grid grid-cols-6 gap-4 relative">
              <!-- Connecting line background -->
              <div class="absolute top-5 left-8 right-8 h-0.5 bg-surface-container-high -z-0"></div>
              <!-- Progress line -->
              <div class="absolute top-5 left-8 w-7/12 h-0.5 bg-secondary -z-0"></div>

              <!-- Step 1: Completed -->
              <div class="relative z-10 flex flex-col space-y-2">
                <div class="flex items-center">
                  <div class="w-10 h-10 rounded-full bg-secondary text-on-secondary flex items-center justify-center shadow-sm">
                    <span class="material-symbols-outlined text-[20px]">check</span>
                  </div>
                </div>
                <div class="pt-1">
                  <span class="inline-block px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed text-xs font-semibold mb-1">已完成</span>
                  <h3 class="text-sm text-on-surface font-semibold leading-snug">投递成功</h3>
                  <p class="text-xs text-on-surface-variant font-mono">2025-03-08 10:15</p>
                  <p class="text-xs text-on-surface-variant mt-1.5 leading-relaxed line-clamp-3">
                    投递《算法专向简历》，系统校验学籍档案无误，直通核心库。
                  </p>
                </div>
              </div>

              <!-- Step 2: Completed -->
              <div class="relative z-10 flex flex-col space-y-2">
                <div class="flex items-center">
                  <div class="w-10 h-10 rounded-full bg-secondary text-on-secondary flex items-center justify-center shadow-sm">
                    <span class="material-symbols-outlined text-[20px]">check</span>
                  </div>
                </div>
                <div class="pt-1">
                  <span class="inline-block px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed text-xs font-semibold mb-1">匹配度 96%</span>
                  <h3 class="text-sm text-on-surface font-semibold leading-snug">HR 初筛通过</h3>
                  <p class="text-xs text-on-surface-variant font-mono">2025-03-08 16:30</p>
                  <p class="text-xs text-on-surface-variant mt-1.5 leading-relaxed line-clamp-3">
                    张经理查阅。评语："专业对口，具备高质量顶会成果与实习经历"。
                  </p>
                </div>
              </div>

              <!-- Step 3: Completed -->
              <div class="relative z-10 flex flex-col space-y-2">
                <div class="flex items-center">
                  <div class="w-10 h-10 rounded-full bg-secondary text-on-secondary flex items-center justify-center shadow-sm">
                    <span class="material-symbols-outlined text-[20px]">check</span>
                  </div>
                </div>
                <div class="pt-1">
                  <span class="inline-block px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed text-xs font-semibold mb-1">技术评分 A+</span>
                  <h3 class="text-sm text-on-surface font-semibold leading-snug">技术一面通过</h3>
                  <p class="text-xs text-on-surface-variant font-mono">2025-03-10 15:00</p>
                  <p class="text-xs text-on-surface-variant mt-1.5 leading-relaxed line-clamp-3">
                    李博士评定："算法功底敏捷，分布式工程视野宽广，推荐进入二面"。
                  </p>
                </div>
              </div>

              <!-- Step 4: Active -->
              <div class="relative z-10 flex flex-col space-y-2">
                <div class="flex items-center">
                  <div class="relative w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center shadow-md">
                    <span class="material-symbols-outlined text-[20px]">psychology</span>
                    <span class="absolute -inset-1 rounded-full bg-primary/20 animate-ping"></span>
                  </div>
                </div>
                <div class="pt-1">
                  <span class="inline-block px-2 py-0.5 rounded-full bg-primary-fixed text-on-primary-fixed text-xs font-bold mb-1">进行中</span>
                  <h3 class="text-sm text-primary font-bold leading-snug">技术二面邀约</h3>
                  <p class="text-xs text-on-surface-variant font-mono">2025-03-11 09:30</p>
                  <p class="text-xs text-on-surface-variant mt-1.5 leading-relaxed font-medium">
                    邀约确认已送达，定于03月12日15:30进行，首席科学家主持。
                  </p>
                </div>
              </div>

              <!-- Step 5: Pending -->
              <div class="relative z-10 flex flex-col space-y-2 opacity-60">
                <div class="flex items-center">
                  <div class="w-10 h-10 rounded-full bg-surface-container-high text-on-surface-variant flex items-center justify-center">
                    <span class="material-symbols-outlined text-[20px]">forum</span>
                  </div>
                </div>
                <div class="pt-1">
                  <span class="inline-block px-2 py-0.5 rounded-full bg-surface-container text-on-surface-variant text-xs mb-1">待解锁</span>
                  <h3 class="text-sm text-on-surface font-semibold leading-snug">HR 综合面谈</h3>
                  <p class="text-xs text-on-surface-variant font-mono">预计 03-14 开展</p>
                  <p class="text-xs text-on-surface-variant mt-1.5 leading-relaxed">
                    文化契合度综合考察及期望薪酬、福利保障意向沟通。
                  </p>
                </div>
              </div>

              <!-- Step 6: Pending -->
              <div class="relative z-10 flex flex-col space-y-2 opacity-60">
                <div class="flex items-center">
                  <div class="w-10 h-10 rounded-full bg-surface-container-high text-on-surface-variant flex items-center justify-center">
                    <span class="material-symbols-outlined text-[20px]">workspace_premium</span>
                  </div>
                </div>
                <div class="pt-1">
                  <span class="inline-block px-2 py-0.5 rounded-full bg-surface-container text-on-surface-variant text-xs mb-1">最终环节</span>
                  <h3 class="text-sm text-on-surface font-semibold leading-snug">Offer 与网签</h3>
                  <p class="text-xs text-on-surface-variant font-mono">待推进</p>
                  <p class="text-xs text-on-surface-variant mt-1.5 leading-relaxed">
                    生成官方录用函并直联教育部全国高校毕业去向登记系统。
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Main Content: 8/4 Split -->
    <section class="w-full pb-12">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
          <!-- Left Column (8 cols) -->
          <div class="lg:col-span-8 space-y-6">
            <!-- Card 1: Interview Details -->
            <div class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm space-y-4" id="video-room">
              <div class="flex items-center justify-between pb-2">
                <div class="flex items-center gap-2">
                  <div class="p-2 rounded-lg bg-primary-fixed text-on-primary-fixed">
                    <span class="material-symbols-outlined">video_camera_front</span>
                  </div>
                  <h3 class="text-lg text-on-surface font-bold">面试安排详情 (Interview Information)</h3>
                </div>
                <span class="px-2.5 py-1 rounded-full bg-secondary-fixed text-on-secondary-fixed text-xs font-semibold">
                  线上远程面试
                </span>
              </div>

              <!-- Key Info Grid -->
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 pt-2">
                <div class="p-4 rounded-xl bg-surface-container-low space-y-1">
                  <span class="text-xs text-on-surface-variant uppercase tracking-wider block">面试时间</span>
                  <p class="text-sm text-on-surface font-bold font-mono">2025年03月12日 (周三)</p>
                  <p class="text-base text-primary font-semibold font-mono">15:30 - 16:30 (预计60分钟)</p>
                </div>
                <div class="p-4 rounded-xl bg-surface-container-low space-y-1">
                  <span class="text-xs text-on-surface-variant uppercase tracking-wider block">考场号与接入码</span>
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-on-surface font-bold font-mono">虚拟考场 A-302</p>
                      <p class="text-base text-on-surface-variant">入会密码：<span class="font-mono font-bold text-on-surface">892011</span></p>
                    </div>
                    <button class="p-2 rounded-lg bg-surface-container-highest text-primary hover:bg-surface-container transition-colors" title="复制入会信息" type="button">
                      <span class="material-symbols-outlined text-[18px]">content_copy</span>
                    </button>
                  </div>
                </div>
                <div class="p-4 rounded-xl bg-surface-container-low space-y-1">
                  <span class="text-xs text-on-surface-variant uppercase tracking-wider block">面试考官</span>
                  <p class="text-base text-on-surface font-semibold">张经理 <span class="text-sm text-on-surface-variant font-normal">（技术招聘负责人）</span></p>
                  <p class="text-base text-on-surface font-semibold">赵教授 <span class="text-sm text-on-surface-variant font-normal">（首席科学家 / 博士生导师）</span></p>
                </div>
                <div class="p-4 rounded-xl bg-surface-container-low space-y-1">
                  <span class="text-xs text-on-surface-variant uppercase tracking-wider block">紧急保障专线</span>
                  <p class="text-sm text-on-surface font-semibold font-mono">+86 186 1024 8890</p>
                  <p class="text-xs text-on-surface-variant">技术与考务答疑 (09:00 - 18:30)</p>
                </div>
              </div>

              <!-- Exam Notice -->
              <div class="p-4 rounded-xl bg-surface-container space-y-2">
                <div class="flex items-center gap-2 text-primary text-sm">
                  <span class="material-symbols-outlined text-[20px]">info</span>
                  <span>考生须知与设备准备</span>
                </div>
                <ul class="space-y-1.5 text-sm text-on-surface-variant list-disc list-inside">
                  <li>请备齐<strong>身份证或学生证原件</strong>，开考前将进行人脸及双证核验。</li>
                  <li>面试包含 <strong>3-5 分钟技术自述</strong> 及代表作源码/Demo 屏幕演示，请提前开启所需IDE与工程。</li>
                  <li>考场支持内置在线协同代码编译器（支持 C++ / Python / CUDA 实时测试）。</li>
                </ul>
              </div>
            </div>

            <!-- Card 2: Job Info -->
            <div class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm space-y-4">
              <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-3 pb-2">
                <div class="flex items-center gap-3">
                  <div class="w-12 h-12 rounded-xl bg-surface-container flex items-center justify-center text-primary shrink-0">
                    <span class="material-symbols-outlined text-[28px]">hub</span>
                  </div>
                  <div>
                    <h3 class="text-lg text-on-surface font-bold">大模型算法工程师</h3>
                    <p class="text-sm text-on-surface-variant">前沿大模型核心实验室 · 2025届秋季校园直招 (可转正实习)</p>
                  </div>
                </div>
                <div class="text-left sm:text-right">
                  <p class="text-xl font-bold text-secondary-container font-mono">25k - 40k · 16薪</p>
                  <span class="text-xs text-on-surface-variant">房补 / 餐补 / 六险二金</span>
                </div>
              </div>
              <div class="flex flex-wrap gap-2 pt-1">
                <span class="px-3 py-1 rounded-md bg-surface-container text-sm text-on-surface">北京 · 中关村清华科技园</span>
                <span class="px-3 py-1 rounded-md bg-surface-container text-sm text-on-surface">硕博学历优先</span>
                <span class="px-3 py-1 rounded-md bg-surface-container text-sm text-on-surface">深度学习 / LLM Pretraining</span>
                <span class="px-3 py-1 rounded-md bg-surface-container text-sm text-on-surface">PyTorch / Megatron-LM</span>
              </div>
              <!-- Office Environment Photos -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4 pt-2">
                <div class="relative h-40 rounded-xl overflow-hidden shadow-sm">
                  <img class="w-full h-full object-cover" alt="实验室研发工位区" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAdpydCe0wBGZ0v33NI6keFdLGbqREo1OHW5QCd1F6nKAun5pU2l1hBS5KacEAFyQ9dHi61CZT6IQBn1v3WlEgl0uqKOq-VDseUCTaTt7ZQowAQn4INL8QKK_k01b4RScVsyh35pUbUvAR_ZzkLzZUORrdcCSsDnKvuYKrqQ0FbUd0NcIg--m2ClRL3tGjh0aJm-ngDk3NAG6odCcU20wqGdU2J03OKFVm9rg-DwlCWTUssujJn3vXrmA" />
                  <div class="absolute inset-0 bg-gradient-to-t from-on-background/80 via-transparent to-transparent flex items-end p-3">
                    <span class="text-surface-container-lowest text-sm font-medium">实验室研发工位区</span>
                  </div>
                </div>
                <div class="relative h-40 rounded-xl overflow-hidden shadow-sm">
                  <img class="w-full h-full object-cover" alt="万卡异构算力集群机房" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAJRYuLcq3kirhxzDgh6ir9STdkMU3tAJtPS_xeiB6w86maSolv2wsUbwpxoICI3YQjSxkeQ81Yg8lLXx7vW_F3txsfPh_2yf4m6Lyn46zsFpUYXl9FvHLWAZnAsaMO1y39O4KCnu4vmPZfzBnmNW06c3Oukvq8EhD7EyKgYnvHjm0qpusznCor6_X5gj3vAIOdcHCqPKLi6hve2h_wr13R43MgciYsp5tUlTLeRyQmeJZbM3_ucOU9BQ" />
                  <div class="absolute inset-0 bg-gradient-to-t from-on-background/80 via-transparent to-transparent flex items-end p-3">
                    <span class="text-surface-container-lowest text-sm font-medium">万卡异构算力集群机房</span>
                  </div>
                </div>
              </div>
              <div class="flex items-center justify-between pt-2">
                <router-link class="inline-flex items-center gap-1 text-base text-primary hover:underline font-semibold" to="/jobs">
                  <span>查看该岗位的完整任职要求与福利明细</span>
                  <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
                </router-link>
                <span class="text-sm text-on-surface-variant font-mono">岗位编号：REQ-2025-AI-018</span>
              </div>
            </div>

            <!-- Card 3: Resume Snapshot -->
            <div class="bg-surface-container-lowest rounded-xl p-6 md:p-8 shadow-sm space-y-4">
              <div class="flex items-center justify-between pb-2">
                <div class="flex items-center gap-2">
                  <div class="p-2 rounded-lg bg-surface-container text-primary">
                    <span class="material-symbols-outlined">picture_as_pdf</span>
                  </div>
                  <h3 class="text-lg text-on-surface font-bold">投递简历锁定快照</h3>
                </div>
                <span class="text-xs text-on-surface-variant flex items-center gap-1">
                  <span class="material-symbols-outlined text-[16px] text-secondary">lock</span>
                  投递后已按教育部规范固化存档
                </span>
              </div>
              <div class="p-4 rounded-xl bg-surface-container-low flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
                <div class="flex items-center gap-4">
                  <div class="w-12 h-14 rounded-lg bg-primary-container text-on-primary flex flex-col items-center justify-center font-mono font-bold text-[10px] shrink-0">
                    <span class="text-xs">PDF</span>
                    <span class="material-symbols-outlined text-[18px]">description</span>
                  </div>
                  <div>
                    <h4 class="text-sm text-on-surface font-semibold">林晨_2025届计算机系_大模型算法研发方向.pdf</h4>
                    <div class="flex flex-wrap items-center gap-2 text-sm text-on-surface-variant mt-0.5">
                      <span>文件大小：2.4 MB</span>
                      <span>·</span>
                      <span>版本哈希：sha256:7f3a8b...</span>
                      <span>·</span>
                      <span>学籍核验通过率：100%</span>
                    </div>
                  </div>
                </div>
                <div class="flex items-center gap-2 shrink-0 w-full md:w-auto justify-end">
                  <button class="inline-flex items-center gap-1 px-4 py-2 rounded-lg bg-primary text-on-primary text-sm font-semibold hover:bg-primary/90 transition-colors shadow-sm" type="button">
                    <span class="material-symbols-outlined text-[18px]">visibility</span>
                    <span>在线预览</span>
                  </button>
                  <button class="inline-flex items-center gap-1 px-4 py-2 rounded-lg bg-surface-container-highest text-on-surface text-sm font-semibold hover:bg-surface-container transition-colors" type="button">
                    <span class="material-symbols-outlined text-[18px]">download</span>
                    <span>下载原件</span>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Right Column (4 cols) -->
          <div class="lg:col-span-4 space-y-6">
            <!-- Device Diagnostics Widget -->
            <div class="bg-surface-container-lowest rounded-xl p-4 md:p-6 shadow-sm space-y-4">
              <div class="flex items-center justify-between">
                <div class="flex items-center gap-2">
                  <span class="material-symbols-outlined text-secondary">videocam</span>
                  <h3 class="text-sm text-on-surface font-bold">考前设备与网络诊断</h3>
                </div>
                <span class="px-2 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed text-xs font-semibold">状态极佳</span>
              </div>
              <div class="space-y-2">
                <!-- Camera -->
                <div class="p-3 rounded-lg bg-surface-container-low flex items-center justify-between">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-on-surface-variant text-[20px]">photo_camera</span>
                    <div>
                      <p class="text-sm font-medium text-on-surface">高清摄像头</p>
                      <p class="text-xs text-on-surface-variant">1080P 60FPS (内置广角)</p>
                    </div>
                  </div>
                  <span class="material-symbols-outlined text-secondary text-[20px]">check_circle</span>
                </div>
                <!-- Microphone -->
                <div class="p-3 rounded-lg bg-surface-container-low flex items-center justify-between">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-on-surface-variant text-[20px]">mic</span>
                    <div>
                      <p class="text-sm font-medium text-on-surface">降噪麦克风</p>
                      <p class="text-xs text-on-surface-variant">AI 拾音降噪已开启</p>
                    </div>
                  </div>
                  <span class="material-symbols-outlined text-secondary text-[20px]">check_circle</span>
                </div>
                <!-- Network -->
                <div class="p-3 rounded-lg bg-surface-container-low space-y-2">
                  <div class="flex items-center justify-between">
                    <div class="flex items-center gap-2">
                      <span class="material-symbols-outlined text-on-surface-variant text-[20px]">wifi</span>
                      <p class="text-sm font-medium text-on-surface">视频专线低延时</p>
                    </div>
                    <span class="font-mono font-bold text-sm text-secondary">18 ms</span>
                  </div>
                  <!-- Network mini chart -->
                  <div class="w-full h-8 flex items-center">
                    <svg class="w-full h-full text-secondary" fill="none" preserveAspectRatio="none" viewBox="0 0 200 32">
                      <path d="M0,20 L30,18 L60,22 L90,14 L120,16 L150,12 L180,18 L200,16" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2.5"></path>
                      <path d="M0,20 L30,18 L60,22 L90,14 L120,16 L150,12 L180,18 L200,16 L200,32 L0,32 Z" fill="currentColor" fill-opacity="0.1"></path>
                    </svg>
                  </div>
                </div>
              </div>
              <button class="w-full py-2 bg-surface-container text-primary text-sm font-semibold rounded-lg hover:bg-surface-container-high transition-colors text-center" type="button">
                重新运行全套硬件与网络自检
              </button>
            </div>

            <!-- HR Notes -->
            <div class="bg-surface-container-lowest rounded-xl p-4 md:p-6 shadow-sm space-y-4">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-primary">rate_review</span>
                <h3 class="text-sm text-on-surface font-bold">招聘团队考察备注</h3>
              </div>
              <div class="p-4 rounded-xl bg-surface-container-low border-l-4 border-l-primary space-y-2">
                <div class="flex items-center justify-between text-xs text-on-surface-variant">
                  <span>技术评审委员会</span>
                  <span class="font-mono">更新于 03-10 17:40</span>
                </div>
                <p class="text-sm text-on-surface leading-relaxed">
                  "候选人算法功底扎实，在代码测试阶段实现了最优时空复杂度。<strong>第二轮技术面将深入探讨在海量多模态预训练中的具体吞吐瓶颈与分布式优化实践</strong>，请候选人准备相关学术作品或实操实验分析。"
                </p>
              </div>
              <div class="flex items-center justify-between text-on-surface-variant text-sm pt-1">
                <span>综合评定星级</span>
                <div class="flex text-tertiary-fixed-dim">
                  <span class="material-symbols-outlined text-[18px]">star</span>
                  <span class="material-symbols-outlined text-[18px]">star</span>
                  <span class="material-symbols-outlined text-[18px]">star</span>
                  <span class="material-symbols-outlined text-[18px]">star</span>
                  <span class="material-symbols-outlined text-[18px]">star_half</span>
                </div>
              </div>
            </div>

            <!-- Compliance & Safety -->
            <div class="bg-surface-container-low rounded-xl p-4 md:p-6 space-y-3">
              <div class="flex items-center gap-2 text-primary">
                <span class="material-symbols-outlined text-[22px]">policy</span>
                <h3 class="text-sm font-bold">求职安全与网签合规保障</h3>
              </div>
              <p class="text-sm text-on-surface-variant leading-relaxed">
                本招聘流程由<strong>教育部直属高校就业平台及学校就业指导中心</strong>全程监管。
              </p>
              <div class="space-y-1.5 pt-2 text-sm text-on-surface-variant">
                <p class="flex items-start gap-1.5">
                  <span class="material-symbols-outlined text-secondary text-[16px] mt-0.5">verified</span>
                  <span>严禁以任何名目收取押金、体检费或培训费。</span>
                </p>
                <p class="flex items-start gap-1.5">
                  <span class="material-symbols-outlined text-secondary text-[16px] mt-0.5">verified</span>
                  <span>录用协议直联全国高校毕业生网上签约平台，具备完全法律效力。</span>
                </p>
              </div>
              <div class="pt-2">
                <a class="inline-flex items-center gap-1 text-xs text-primary hover:underline font-semibold" href="#">
                  <span>违规维权与一键向学校辅导员反馈通道</span>
                  <span class="material-symbols-outlined text-[16px]">open_in_new</span>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getApplicationDetail } from '../../api/application'

const route = useRoute()

const applicationId = ref('')
const calendarAdded = ref(false)

const fetchApplicationDetail = async () => {
  const id = route.params.id
  applicationId.value = id || 'APP-20250308-8849'
  try {
    await getApplicationDetail(id)
  } catch (e) {
    console.error('Failed to fetch application detail:', e)
  }
}

onMounted(() => {
  fetchApplicationDetail()
})
</script>
