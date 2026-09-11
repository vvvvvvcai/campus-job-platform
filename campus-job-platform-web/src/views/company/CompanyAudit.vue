<template>
  <div class="relative w-full bg-gradient-to-b from-primary-fixed/30 via-surface to-surface pb-space-2xl">
    <div class="max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop pt-space-lg">
      <!-- Breadcrumb Navigation -->
      <nav aria-label="Breadcrumb" class="flex items-center gap-space-xs text-on-surface-variant font-label-md text-label-md mb-space-md">
        <router-link class="hover:text-primary transition-colors flex items-center gap-1" to="/company-center">
          <span class="material-symbols-outlined text-[16px]">apartment</span>
          <span>企业管理中心</span>
        </router-link>
        <span class="material-symbols-outlined text-[14px] text-outline">chevron_right</span>
        <a class="hover:text-primary transition-colors" href="#">入驻合规备案</a>
        <span class="material-symbols-outlined text-[14px] text-outline">chevron_right</span>
        <span class="text-on-surface font-medium">企业资质与校招入驻认证</span>
      </nav>

      <!-- Page Header & Trust Badges -->
      <div class="flex flex-col lg:flex-row lg:items-end justify-between gap-space-lg pb-space-lg">
        <div class="max-w-3xl space-y-space-xs">
          <div class="inline-flex items-center gap-space-xs px-3 py-1 rounded-full bg-surface-container text-primary font-label-sm text-label-sm">
            <span class="material-symbols-outlined text-[16px]">verified_user</span>
            <span>国家教育部 / 人社部校园招聘合规联网备案流程</span>
          </div>
          <h1 class="font-headline-xl text-headline-xl text-on-surface font-bold tracking-tight">企业资质认证与高校入驻备案</h1>
          <p class="font-body-md text-body-md text-on-surface-variant leading-relaxed">
            根据高校毕业生就业招聘秩序管理规定，完成国家法定主体审核与高校就业指导中心核验后，用人单位将全面解锁全国高校双选会席位直申、空中宣讲会发起、2025届毕业生人才库精准直推等权威权益。
          </p>
        </div>
        <!-- Security & Realtime Query Telemetry Badge -->
        <div class="flex flex-wrap lg:flex-col items-start lg:items-end gap-2 shrink-0">
          <div class="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-lg bg-surface-container-lowest shadow-sm text-on-surface-variant font-label-sm text-label-sm">
            <span class="w-2 h-2 rounded-full bg-emerald-500 animate-pulse"></span>
            <span class="font-semibold text-on-surface">SSL 256-bit</span>
            <span>军工级数据传输加密</span>
          </div>
          <div class="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-lg bg-surface-container-lowest shadow-sm text-on-surface-variant font-label-sm text-label-sm">
            <span class="material-symbols-outlined text-secondary text-[16px]">sync_alt</span>
            <span>国家企业信用信息公示系统直连核验</span>
          </div>
        </div>
      </div>

      <!-- Stepper / Status Flow Visualizer -->
      <div class="mt-space-md bg-surface-container-lowest rounded-xl p-space-md lg:p-space-lg shadow-sm">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-space-md relative">
          <!-- Step 1: Done -->
          <div class="flex items-start gap-space-sm">
            <div class="w-10 h-10 rounded-full bg-emerald-100 text-emerald-700 flex items-center justify-center shrink-0 shadow-sm">
              <span class="material-symbols-outlined text-[20px]">check</span>
            </div>
            <div class="min-w-0">
              <div class="flex items-center gap-2">
                <span class="font-headline-sm text-headline-sm text-on-surface font-semibold">1. 基础账号建立</span>
                <span class="px-2 py-0.5 rounded-full bg-emerald-50 text-emerald-700 font-label-sm text-label-sm">已完成</span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant mt-1">HR企业邮箱已激活验证，通行证绑定成功</p>
            </div>
          </div>
          <!-- Step 2: Active / Current Focus -->
          <div class="flex items-start gap-space-sm relative">
            <div class="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center shrink-0 shadow-md ring-4 ring-primary/10">
              <span class="material-symbols-outlined text-[20px]">description</span>
            </div>
            <div class="min-w-0">
              <div class="flex items-center gap-2">
                <span class="font-headline-sm text-headline-sm text-primary font-bold">2. 资质材料提交</span>
                <span class="px-2 py-0.5 rounded-full bg-primary/10 text-primary font-label-sm text-label-sm font-semibold">当前环节</span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant mt-1">完善营业执照、校招授权经办书及保障声明</p>
            </div>
          </div>
          <!-- Step 3: Pending -->
          <div class="flex items-start gap-space-sm opacity-60">
            <div class="w-10 h-10 rounded-full bg-surface-container-high text-on-surface-variant flex items-center justify-center shrink-0">
              <span class="material-symbols-outlined text-[20px]">verified</span>
            </div>
            <div class="min-w-0">
              <div class="flex items-center gap-2">
                <span class="font-headline-sm text-headline-sm text-on-surface font-semibold">3. 校方联合审定</span>
                <span class="px-2 py-0.5 rounded-full bg-surface-container text-on-surface-variant font-label-sm text-label-sm">待审核</span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant mt-1">材料提交后1-2个工作日内高校就业中心初审</p>
            </div>
          </div>
        </div>
        <!-- Progress Bar Underline -->
        <div class="w-full bg-surface-container h-1.5 rounded-full mt-space-md overflow-hidden">
          <div class="bg-primary h-full rounded-full transition-all duration-500" style="width: 50%;"></div>
        </div>
      </div>
    </div>

    <!-- Main Content Form Section -->
    <div class="max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop -mt-space-lg w-full pb-space-3xl">
      <form class="space-y-space-lg" @submit.prevent="handleSubmit">
        <!-- Group 1: Legal Entity Details -->
        <section class="bg-surface-container-lowest rounded-xl p-space-md lg:p-space-xl shadow-sm">
          <div class="flex items-center justify-between pb-space-md">
            <div class="flex items-center gap-space-xs">
              <span class="material-symbols-outlined text-primary text-[24px]">domain</span>
              <div>
                <h2 class="font-headline-md text-headline-md text-on-surface font-bold">企业基本主体信息</h2>
                <p class="font-body-sm text-body-sm text-on-surface-variant">请按照《营业执照》登记信息严格填写，系统将同步工商数据校验</p>
              </div>
            </div>
            <span class="font-label-sm text-label-sm text-secondary bg-secondary-fixed/50 px-2.5 py-1 rounded-full font-medium hidden md:inline-flex">国家市场监督管理总局数据核验中</span>
          </div>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-space-md pt-space-xs">
            <!-- Company Name -->
            <div class="space-y-1.5 relative md:col-span-2">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>企业全称</span>
                <span class="text-error">*</span>
                <span class="font-label-sm text-label-sm text-on-surface-variant font-normal">(须与营业执照公章名称完全一致)</span>
              </label>
              <div class="relative">
                <input v-model="form.companyName" class="w-full h-10 px-3 pl-9 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all" placeholder="请输入国家企查登记全称" type="text" />
                <span class="material-symbols-outlined absolute left-2.5 top-2.5 text-[18px] text-outline">search</span>
                <button class="absolute right-2.5 top-2 text-primary font-label-sm text-label-sm flex items-center gap-0.5 hover:underline" type="button">
                  <span class="material-symbols-outlined text-[16px]">refresh</span>
                  <span>查验</span>
                </button>
              </div>
              <p class="font-body-sm text-body-sm text-emerald-700 flex items-center gap-1 pt-0.5">
                <span class="material-symbols-outlined text-[16px]">check_circle</span>
                已匹配全国统一社会信用代码信息库库容记录（在营存续状态）
              </p>
            </div>

            <!-- Unified Social Credit Code -->
            <div class="space-y-1.5">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>统一社会信用代码</span>
                <span class="text-error">*</span>
              </label>
              <div class="relative">
                <input v-model="form.creditCode" class="w-full h-10 px-3 uppercase bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all" maxlength="18" placeholder="18位大写英文字母与阿拉伯数字" type="text" />
                <span class="material-symbols-outlined absolute right-2.5 top-2.5 text-[18px] text-emerald-600">verified</span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant">通常印制于最新版营业执照正本左上角或副本右上角</p>
            </div>

            <!-- Industry Category -->
            <div class="space-y-1.5">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>所属核心行业</span>
                <span class="text-error">*</span>
              </label>
              <div class="relative">
                <select v-model="form.industry" class="w-full h-10 px-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all appearance-none cursor-pointer">
                  <option value="ai">互联网 / 人工智能与大模型</option>
                  <option value="semi">集成电路 / 芯片研发与半导体</option>
                  <option value="manufacturing">高端智能制造 / 新能源科技</option>
                  <option value="fin">金融科技 / 证券投资与保险</option>
                  <option value="bio">生物医药 / 医疗器械与大健康</option>
                  <option value="edu">教育科研 / 高校智库支持</option>
                </select>
                <span class="material-symbols-outlined absolute right-2.5 top-2.5 text-[18px] text-outline pointer-events-none">expand_more</span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant">行业归类将决定推荐算法向对口专业的应届生进行定向匹配</p>
            </div>

            <!-- Personnel Scale -->
            <div class="space-y-1.5">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>企业人员规模</span>
                <span class="text-error">*</span>
              </label>
              <div class="relative">
                <select v-model="form.scale" class="w-full h-10 px-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all appearance-none cursor-pointer">
                  <option value="1">20 - 99人（初创成长型）</option>
                  <option value="2">100 - 499人（中型企业）</option>
                  <option value="3">500 - 999人（规模型企业）</option>
                  <option value="4">1000 - 9999人（大型集团）</option>
                  <option value="5">10000人以上（超大型跨国或行业领军）</option>
                </select>
                <span class="material-symbols-outlined absolute right-2.5 top-2.5 text-[18px] text-outline pointer-events-none">expand_more</span>
              </div>
            </div>

            <!-- Headquarters City -->
            <div class="space-y-1.5">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>总部所在地</span>
                <span class="text-error">*</span>
              </label>
              <div class="grid grid-cols-2 gap-2">
                <div class="relative">
                  <select v-model="form.province" class="w-full h-10 px-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none">
                    <option value="北京市">北京市</option>
                    <option value="上海市">上海市</option>
                    <option value="广东省">广东省</option>
                    <option value="浙江省">浙江省</option>
                    <option value="江苏省">江苏省</option>
                    <option value="四川省">四川省</option>
                  </select>
                  <span class="material-symbols-outlined absolute right-2 top-2.5 text-[18px] text-outline pointer-events-none">expand_more</span>
                </div>
                <div class="relative">
                  <select v-model="form.district" class="w-full h-10 px-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none">
                    <option value="海淀区">海淀区</option>
                    <option value="朝阳区">朝阳区</option>
                    <option value="西城区">西城区</option>
                    <option value="东城区">东城区</option>
                    <option value="昌平区">昌平区</option>
                  </select>
                  <span class="material-symbols-outlined absolute right-2 top-2.5 text-[18px] text-outline pointer-events-none">expand_more</span>
                </div>
              </div>
            </div>

            <!-- Detailed Workplace Address -->
            <div class="space-y-1.5 md:col-span-2">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>详细办公地址 / 校招接待处</span>
                <span class="text-error">*</span>
              </label>
              <input v-model="form.address" class="w-full h-10 px-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all" placeholder="楼宇、门牌号及校企对接接待专区" type="text" />
            </div>

            <!-- Official Website / Official Channels -->
            <div class="space-y-1.5 md:col-span-2">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center justify-between">
                <span class="flex items-center gap-1">
                  <span>官方网站 / 招聘公众号</span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant font-normal">（选填）</span>
                </span>
                <span class="font-label-sm text-label-sm text-on-surface-variant hidden md:inline">有官网主页的高校审核通过率提升40%</span>
              </label>
              <div class="relative">
                <span class="absolute left-3 top-2.5 text-outline font-label-md text-label-md">https://</span>
                <input v-model="form.website" class="w-full h-10 pl-20 pr-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all" placeholder="www.yourcompany.com" type="text" />
              </div>
            </div>
          </div>
        </section>

        <!-- Group 2: Campus Recruitment Officer Info -->
        <section class="bg-surface-container-lowest rounded-xl p-space-md lg:p-space-xl shadow-sm">
          <div class="flex items-center gap-space-xs pb-space-md">
            <span class="material-symbols-outlined text-primary text-[24px]">badge</span>
            <div>
              <h2 class="font-headline-md text-headline-md text-on-surface font-bold">校园招聘对接人信息</h2>
              <p class="font-body-sm text-body-sm text-on-surface-variant">校方就业指导中心教师联系及线下宣讲会进校确认人</p>
            </div>
          </div>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-space-md">
            <!-- Recruiter Name -->
            <div class="space-y-1.5">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>招聘负责人 / HR 姓名</span>
                <span class="text-error">*</span>
              </label>
              <input v-model="form.hrName" class="w-full h-10 px-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all" placeholder="请输入经办人真实中文姓名" type="text" />
            </div>
            <!-- Recruiter Mobile -->
            <div class="space-y-1.5">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>直联联系手机</span>
                <span class="text-error">*</span>
                <span class="font-label-sm text-label-sm text-on-surface-variant font-normal">（用于接收高校就业办审核反馈短信）</span>
              </label>
              <div class="relative">
                <span class="absolute left-3 top-2.5 text-on-surface font-label-md text-label-md">+86</span>
                <input v-model="form.hrPhone" class="w-full h-10 pl-12 pr-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all" maxlength="13" placeholder="经办HR联系手机" type="tel" />
              </div>
            </div>
            <!-- Recruiter Official Work Email -->
            <div class="space-y-1.5">
              <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                <span>企业工作邮箱</span>
                <span class="text-error">*</span>
              </label>
              <div class="relative">
                <input v-model="form.hrEmail" class="w-full h-10 px-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all" placeholder="hr@yourcompany.com" type="email" />
                <span class="material-symbols-outlined absolute right-2.5 top-2.5 text-[18px] text-emerald-600">mail</span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant">请尽量提供带公司专属企业后缀的工作邮箱（如@zhipuai.cn），避免公共邮箱</p>
            </div>
            <!-- Auth Document Upload -->
            <div class="space-y-1.5">
              <div class="flex items-center justify-between">
                <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                  <span>经办人工牌或授权委托书</span>
                  <span class="text-error">*</span>
                </label>
                <a class="font-label-sm text-label-sm text-primary flex items-center gap-0.5 hover:underline" href="#">
                  <span class="material-symbols-outlined text-[14px]">download</span>
                  <span>下载标准高校入驻授权书模板</span>
                </a>
              </div>
              <div class="flex items-center gap-2">
                <label class="flex-1 h-10 px-3 bg-surface-container-low hover:bg-surface-container transition-colors rounded-md text-on-surface-variant font-label-md text-label-md flex items-center justify-between cursor-pointer">
                  <span class="flex items-center gap-1.5 text-on-surface truncate">
                    <span class="material-symbols-outlined text-[18px] text-primary">attach_file</span>
                    <span>校招授权委托书_加盖公章.pdf (1.2MB)</span>
                  </span>
                  <span class="text-primary font-semibold hover:underline shrink-0">重新上传</span>
                  <input accept=".pdf,.png,.jpg,.jpeg" class="hidden" type="file" />
                </label>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant">加盖用人单位公章或HR人事专章后拍照/扫描上传</p>
            </div>
          </div>
        </section>

        <!-- Group 3: Company Credentials & Uploads -->
        <section class="bg-surface-container-lowest rounded-xl p-space-md lg:p-space-xl shadow-sm">
          <div class="flex items-center justify-between pb-space-md">
            <div class="flex items-center gap-space-xs">
              <span class="material-symbols-outlined text-primary text-[24px]">verified</span>
              <div>
                <h2 class="font-headline-md text-headline-md text-on-surface font-bold">企业资质证件上传</h2>
                <p class="font-body-sm text-body-sm text-on-surface-variant">必须提供最新清晰彩色扫描件或加盖鲜章复印件</p>
              </div>
            </div>
            <button class="font-label-sm text-label-sm text-secondary hover:underline flex items-center gap-1" type="button">
              <span class="material-symbols-outlined text-[16px]">visibility</span>
              <span>查看规范范例图</span>
            </button>
          </div>
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-space-lg">
            <!-- Business License Upload Area -->
            <div class="space-y-2">
              <div class="flex items-center justify-between">
                <label class="font-label-lg text-label-lg text-on-surface font-semibold flex items-center gap-1">
                  <span>营业执照扫描件（正本/副本）</span>
                  <span class="text-error">*</span>
                </label>
                <span class="font-label-sm text-label-sm text-on-surface-variant">支持 JPG/PNG/PDF, 最大 10MB</span>
              </div>
              <div class="group relative bg-surface-container-low hover:bg-surface-container rounded-xl p-space-lg flex flex-col items-center justify-center text-center transition-all cursor-pointer">
                <div class="w-16 h-16 rounded-full bg-primary/10 text-primary flex items-center justify-center mb-space-xs group-hover:scale-105 transition-transform">
                  <span class="material-symbols-outlined text-[32px]">cloud_upload</span>
                </div>
                <p class="font-label-lg text-label-lg text-on-surface font-medium">点击或将文件拖拽至此处上传</p>
                <p class="font-body-sm text-body-sm text-on-surface-variant mt-1">需保证企业名称、社会信用代码、法定代表人印章清晰无遮挡</p>
                <div class="mt-space-sm flex items-center gap-2">
                  <span class="px-2.5 py-1 bg-surface-container-lowest rounded-full font-label-sm text-label-sm text-on-surface-variant shadow-sm flex items-center gap-1">
                    <span class="material-symbols-outlined text-[14px] text-emerald-600">lock</span>
                    仅用于校企实名备案，不作商业展示
                  </span>
                </div>
                <input accept=".jpg,.jpeg,.png,.pdf" class="absolute inset-0 opacity-0 cursor-pointer" type="file" />
              </div>
              <!-- Uploaded preview mock item -->
              <div class="flex items-center justify-between p-2.5 rounded-lg bg-surface-container text-on-surface font-body-sm text-body-sm">
                <div class="flex items-center gap-2 truncate">
                  <span class="material-symbols-outlined text-primary text-[20px]">picture_as_pdf</span>
                  <span class="font-medium truncate">北京智谱华章科技有限公司_营业执照副本2024.pdf</span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant shrink-0">(3.4 MB)</span>
                </div>
                <div class="flex items-center gap-2 shrink-0">
                  <span class="text-emerald-700 font-label-sm text-label-sm font-semibold flex items-center gap-0.5">
                    <span class="material-symbols-outlined text-[14px]">check</span> OCR已解析
                  </span>
                  <button class="text-on-surface-variant hover:text-error p-1 transition-colors" title="删除重传" type="button">
                    <span class="material-symbols-outlined text-[18px]">delete</span>
                  </button>
                </div>
              </div>
            </div>

            <!-- Legal Representative ID -->
            <div class="space-y-2">
              <div class="flex items-center justify-between">
                <label class="font-label-lg text-label-lg text-on-surface font-semibold flex items-center gap-1">
                  <span>法人身份证件或授权代表证件</span>
                  <span class="font-label-sm text-label-sm text-on-surface-variant font-normal">（增强快速通道，可延后补充）</span>
                </label>
                <span class="font-label-sm text-label-sm text-secondary">直通国家双一流高校绿通</span>
              </div>
              <div class="grid grid-cols-2 gap-space-sm">
                <!-- Front Side -->
                <div class="relative bg-surface-container-low hover:bg-surface-container rounded-xl p-space-md flex flex-col items-center justify-center text-center transition-all cursor-pointer min-h-[140px]">
                  <span class="material-symbols-outlined text-outline text-[28px] mb-1">id_card</span>
                  <span class="font-label-md text-label-md text-on-surface font-medium">身份证人像面</span>
                  <span class="font-body-sm text-body-sm text-on-surface-variant mt-0.5">点击上传</span>
                  <input accept="image/*" class="absolute inset-0 opacity-0 cursor-pointer" type="file" />
                </div>
                <!-- Back Side -->
                <div class="relative bg-surface-container-low hover:bg-surface-container rounded-xl p-space-md flex flex-col items-center justify-center text-center transition-all cursor-pointer min-h-[140px]">
                  <span class="material-symbols-outlined text-outline text-[28px] mb-1">credit_card</span>
                  <span class="font-label-md text-label-md text-on-surface font-medium">身份证国徽面</span>
                  <span class="font-body-sm text-body-sm text-on-surface-variant mt-0.5">点击上传</span>
                  <input accept="image/*" class="absolute inset-0 opacity-0 cursor-pointer" type="file" />
                </div>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant">提示：若上传法人证件，将在各高校就业专场优先展示"直聘标杆企业"金色认证微标。</p>
            </div>
          </div>
        </section>

        <!-- Group 4: Company Profile & Campus Hiring Pledge -->
        <section class="bg-surface-container-lowest rounded-xl p-space-md lg:p-space-xl shadow-sm">
          <div class="flex items-center gap-space-xs pb-space-md">
            <span class="material-symbols-outlined text-primary text-[24px]">description</span>
            <div>
              <h2 class="font-headline-md text-headline-md text-on-surface font-bold">企业介绍与校招承诺</h2>
              <p class="font-body-sm text-body-sm text-on-surface-variant">展示于各高校校招门户主页及双选会展位的企业官方向导</p>
            </div>
          </div>
          <div class="space-y-space-md">
            <!-- Company Bio / Intro -->
            <div class="space-y-1.5">
              <div class="flex items-center justify-between">
                <label class="font-label-lg text-label-lg text-on-surface flex items-center gap-1">
                  <span>企业简述及应届生发展环境</span>
                  <span class="text-error">*</span>
                </label>
                <span :class="['font-label-sm text-label-sm', charCount >= 500 ? 'text-error' : 'text-on-surface-variant']">
                  {{ charCount }} / 500 字
                </span>
              </div>
              <textarea
                v-model="form.companyIntro"
                class="w-full p-3 bg-surface-container-lowest rounded-md text-on-surface font-body-md text-body-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all leading-relaxed"
                maxlength="500"
                placeholder="请输入企业简介，包括主营业务、行业地位、应届生培养通道与导师机制..."
                rows="4"
              ></textarea>
            </div>

            <!-- Compliance & Student Safety Pledge Box -->
            <div class="rounded-xl bg-surface-container-low p-space-md space-y-space-sm">
              <div class="flex items-center gap-2 text-primary font-headline-sm text-headline-sm font-semibold">
                <span class="material-symbols-outlined text-[20px]">policy</span>
                <span>全国普通高等学校毕业生就业招聘"三严禁"与合规承诺</span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed">
                为切实维护高校毕业生合法就业权益，入驻用人单位须严格遵守教育部招聘规定：严禁发布含有性别、民族、宗教信仰等歧视性条款的招聘信息；严禁以任何名目向求职学生收取押金、报名费、体检代办费或培训贷；严禁进行虚假职位宣传；应届实习生到岗须依法签订合规实习协议，并依约发放报酬并缴纳人身意外险。
              </p>
              <div class="pt-2">
                <label class="flex items-start gap-2.5 cursor-pointer">
                  <input v-model="form.pledgeChecked" type="checkbox" class="mt-0.5 h-4 w-4 rounded text-primary focus:ring-primary accent-primary cursor-pointer" />
                  <span class="font-label-md text-label-md text-on-surface font-medium leading-tight">
                    本单位郑重承诺严格恪守国家高校就业政策法规，绝不开展违规收费、虚假招聘及侵犯学生合法权益之行为，自愿接受高校就业指导中心及平台联合监督与履约考核。
                  </span>
                </label>
              </div>
            </div>
          </div>
        </section>

        <!-- Bottom Submission & Agreement Area -->
        <div class="bg-surface-container-lowest rounded-xl p-space-md lg:p-space-lg shadow-sm flex flex-col md:flex-row items-center justify-between gap-space-md">
          <div class="flex items-center gap-2">
            <input v-model="form.protocolChecked" type="checkbox" class="h-4 w-4 rounded text-primary focus:ring-primary accent-primary cursor-pointer" />
            <label class="font-body-sm text-body-sm text-on-surface-variant cursor-pointer">
              已阅读并全权同意
              <a class="text-primary hover:underline font-medium" href="#">《校园直聘用人单位入驻守则及合规声明》</a>
              与
              <a class="text-primary hover:underline font-medium" href="#">《全国高校招聘信息安全承诺书》</a>
            </label>
          </div>
          <div class="flex items-center gap-space-sm w-full md:w-auto shrink-0">
            <button class="flex-1 md:flex-initial h-10 px-5 rounded-md bg-surface-container text-on-surface hover:bg-surface-container-high transition-colors font-label-lg text-label-lg flex items-center justify-center gap-1.5 shadow-sm" type="button">
              <span class="material-symbols-outlined text-[18px]">save</span>
              <span>保存草稿</span>
            </button>
            <button
              :disabled="submitting"
              class="flex-1 md:flex-initial h-10 px-8 rounded-md bg-gradient-to-r from-primary to-primary-container text-on-primary hover:opacity-95 active:scale-98 transition-all font-label-lg text-label-lg font-semibold flex items-center justify-center gap-2 shadow-md disabled:opacity-50"
              type="submit"
            >
              <span v-if="submitting" class="material-symbols-outlined animate-spin text-[18px]">autorenew</span>
              <span v-if="submitting">国家信保数据库核验中...</span>
              <template v-else>
                <span>提交资质审核</span>
                <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
              </template>
            </button>
          </div>
        </div>
      </form>

      <!-- FAQ Section -->
      <div class="mt-space-xl bg-surface-container-lowest rounded-xl p-space-md lg:p-space-xl shadow-sm">
        <div class="flex items-center gap-space-xs mb-space-md">
          <span class="material-symbols-outlined text-secondary text-[22px]">help_center</span>
          <h3 class="font-headline-sm text-headline-sm text-on-surface font-bold">企业高校入驻资质认证常见疑问 (FAQ)</h3>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-space-md">
          <div class="p-space-md rounded-lg bg-surface-container-low space-y-1">
            <div class="flex items-center gap-2 text-on-surface font-label-lg text-label-lg font-semibold">
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              <span>资质审核一般需要多长时间生效？</span>
            </div>
            <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed pl-3.5">
              在工作日提交的认证材料，平台技术初审在2小时内完成，各对口重点高校就业中心老师核验通常在1-2个工作日内反馈结果。遇春招/秋招高峰期可能会顺延半个工作日。
            </p>
          </div>
          <div class="p-space-md rounded-lg bg-surface-container-low space-y-1">
            <div class="flex items-center gap-2 text-on-surface font-label-lg text-label-lg font-semibold">
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              <span>哪些情况会导致企业认证审核被驳回？</span>
            </div>
            <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed pl-3.5">
              营业执照超出有效营业期限、执照复印件未加盖企业实体红色鲜章、企查存在严重违法失信名单（黑名单）、招聘经办人未获得单位正式入驻授权或邮箱为临时公共邮箱。
            </p>
          </div>
          <div class="p-space-md rounded-lg bg-surface-container-low space-y-1">
            <div class="flex items-center gap-2 text-on-surface font-label-lg text-label-lg font-semibold">
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              <span>通过认证后，如何在多所高校开宣讲会？</span>
            </div>
            <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed pl-3.5">
              资质认证成功后，您将在全国高校就业联盟库获得唯一的"认证企业信用码"，无需逐校重复上传营业执照，可一键向清华、北大、浙大等合作高校提交场地宣讲申请。
            </p>
          </div>
          <div class="p-space-md rounded-lg bg-surface-container-low space-y-1">
            <div class="flex items-center gap-2 text-on-surface font-label-lg text-label-lg font-semibold">
              <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
              <span>企业法人或名称发生工商变更怎么办？</span>
            </div>
            <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed pl-3.5">
              您可在"企业管理中心 > 资质档案"发起变更申请，重新上传最新的电子营业执照即可重新核验，已发布的有效校招岗位在换证期间不会受到下架影响。
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const form = ref({
  companyName: '北京智谱华章科技有限公司',
  creditCode: '91110108MA01KPXY5Q',
  industry: 'ai',
  scale: '3',
  province: '北京市',
  district: '海淀区',
  address: '中关村东路1号院清华科技园科技大厦B座8层',
  website: 'www.zhipuai.cn/careers',
  hrName: '陈雅静',
  hrPhone: '186 1024 8890',
  hrEmail: 'yajing.chen@zhipuai.cn',
  companyIntro: '北京智谱华章科技有限公司（智谱AI）致力于打造新一代认知智能通用模型，由清华大学计算机系技术成果转化而来。公司核心团队曾主导GLM系列大模型研发，在学术科研与产业赋能方面处于领先水平。我们为2025届应届毕业生设立专项技术管培生计划，配备顶尖博士导师带教与前沿算力资源，提供极具竞争力的成长通道与薪酬保障。',
  pledgeChecked: true,
  protocolChecked: true,
})

const submitting = ref(false)

const charCount = computed(() => form.value.companyIntro.length)

const handleSubmit = () => {
  if (!form.value.pledgeChecked || !form.value.protocolChecked) {
    ElMessage.warning('请先勾选并同意《高校三严禁合规承诺》及《校园直聘用人单位入驻守则》')
    return
  }

  submitting.value = true

  setTimeout(() => {
    submitting.value = false
    ElMessage.success('资质材料已成功提交！审核结果将在1-2个工作日内以短信形式通知经办人。')
  }, 1200)
}
</script>
