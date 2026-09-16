<template>
  <div class="min-h-screen bg-background pb-8">
    <!-- 页面顶部横幅 -->
    <div class="bg-gradient-to-r from-primary via-primary-container to-primary py-4">
      <div class="max-w-5xl mx-auto px-6 flex items-center gap-2">
        <span class="material-symbols-outlined text-on-primary text-lg">verified_user</span>
        <span class="text-body-sm text-on-primary font-medium">国家教育部 / 人社部校园招聘合规联网备案流程</span>
      </div>
    </div>

    <!-- 页面标题 -->
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-5xl mx-auto px-6">
        <div class="flex items-start justify-between">
          <div>
            <h1 class="text-headline-xl font-bold text-on-surface">企业资质认证与高校入驻备案</h1>
            <p class="text-body-md text-on-surface-variant mt-1.5 max-w-3xl">根据高校毕业生就业招聘秩序管理流程规定，完成国家法定主体审核与高校就业指导中心核验后，用人单位将全面解锁全国高校双选会席位中、空中宣讲会发起、2025届中生人才库精准直推等权威权益。</p>
          </div>
          <div class="flex flex-col items-end gap-2">
            <div class="flex items-center gap-2 px-3 py-1.5 bg-emerald-50 rounded-lg">
              <span class="material-symbols-outlined text-emerald-600 text-base">lock</span>
              <span class="text-xs font-medium text-emerald-700">SSL 256-bit 营工数据级加密传输</span>
            </div>
            <div class="flex items-center gap-2 px-3 py-1.5 bg-primary/10 rounded-lg">
              <span class="material-symbols-outlined text-primary text-base">verified</span>
              <span class="text-xs font-medium text-primary">国家企业信用信息公示系统数据直连</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 表单内容 -->
    <div class="max-w-5xl mx-auto px-6 py-8">
      <!-- 进度步骤条 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6 mb-8">
        <div class="flex items-center justify-between relative">
          <!-- 进度线 -->
          <div class="absolute top-6 left-0 right-0 h-1 bg-surface-container-high z-0">
            <div class="h-full bg-primary transition-all duration-500" style="width: 33%"></div>
          </div>

          <!-- 步骤1：已完成 -->
          <div class="relative z-10 flex flex-col items-center">
            <div class="w-12 h-12 rounded-full bg-emerald-500 flex items-center justify-center">
              <span class="material-symbols-outlined text-white text-xl">check</span>
            </div>
            <div class="mt-3 text-center">
              <div class="flex items-center gap-2">
                <span class="text-body-sm font-semibold text-on-surface">1. 基础账号建立</span>
                <span class="px-2 py-0.5 bg-emerald-50 text-emerald-600 text-xs font-semibold rounded-full">已完成</span>
              </div>
              <p class="text-xs text-on-surface-variant mt-1">HR企业邮箱已激活验证，通行证绑定成功</p>
            </div>
          </div>

          <!-- 步骤2：当前 -->
          <div class="relative z-10 flex flex-col items-center">
            <div class="w-12 h-12 rounded-full bg-primary flex items-center justify-center ring-4 ring-primary/20">
              <span class="material-symbols-outlined text-white text-xl">description</span>
            </div>
            <div class="mt-3 text-center">
              <div class="flex items-center gap-2">
                <span class="text-body-sm font-semibold text-primary">2. 资质材料提交</span>
                <span class="px-2 py-0.5 bg-primary/10 text-primary text-xs font-semibold rounded-full">当前环节</span>
              </div>
              <p class="text-xs text-on-surface-variant mt-1">完善营业执照、校招授权经办书及保密声明</p>
            </div>
          </div>

          <!-- 步骤3：待审核 -->
          <div class="relative z-10 flex flex-col items-center">
            <div class="w-12 h-12 rounded-full bg-surface-container-high flex items-center justify-center">
              <span class="material-symbols-outlined text-on-surface-variant text-xl">schedule</span>
            </div>
            <div class="mt-3 text-center">
              <div class="flex items-center gap-2">
                <span class="text-body-sm font-semibold text-on-surface-variant">3. 校方联合审定</span>
                <span class="px-2 py-0.5 bg-surface-container text-on-surface-variant text-xs font-semibold rounded-full">待审核</span>
              </div>
              <p class="text-xs text-on-surface-variant mt-1">材料提交后1-2个工作日内由校就业中心初审</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 企业基本主体信息 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high mb-6">
        <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high">
          <div class="flex items-center gap-3">
            <span class="material-symbols-outlined text-primary text-xl">apartment</span>
            <div>
              <h2 class="text-headline-sm font-semibold text-on-surface">企业基本主体信息</h2>
              <p class="text-body-sm text-on-surface-variant mt-0.5">请按照《营业执照》登记信息严格填写，系统将同步工商数据校验</p>
            </div>
          </div>
          <span class="px-2.5 py-1 bg-primary/10 text-primary text-xs font-semibold rounded-full">国家市场监督管理总局数据核验中</span>
        </div>
        <div class="p-6 space-y-5">
          <!-- 企业全称 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">企业全称 <span class="text-body-sm text-on-surface-variant font-normal">（须与营业执照公章名称完全一致）</span></label>
            <div class="relative">
              <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-lg">search</span>
              <input v-model="form.companyName" type="text" placeholder="北京智谱华章科技有限公司"
                class="w-full h-11 pl-10 pr-16 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all" />
              <button class="absolute right-2 top-1/2 -translate-y-1/2 px-3 py-1.5 text-primary text-sm font-medium hover:bg-primary/10 rounded-lg transition-colors">查重</button>
            </div>
            <div class="flex items-center gap-2 mt-2">
              <span class="material-symbols-outlined text-emerald-500 text-base">check_circle</span>
              <span class="text-body-sm text-emerald-600">已匹配全国统一社会信用代码信息库（在营存续状态）</span>
            </div>
          </div>

          <!-- 统一社会信用代码 & 所属核心行业 -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">统一社会信用代码 <span class="text-error">*</span></label>
              <div class="relative">
                <input v-model="form.creditCode" type="text" placeholder="91110108MA01KPXY5Q"
                  class="w-full h-11 px-4 pr-10 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
                <span class="material-symbols-outlined absolute right-3 top-1/2 -translate-y-1/2 text-emerald-500 text-lg">verified</span>
              </div>
              <p class="text-xs text-on-surface-variant mt-1.5">通常印于最新版营业执照正本左上角或副本右上角</p>
            </div>
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">所属核心行业 <span class="text-error">*</span></label>
              <select v-model="form.industry"
                class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                <option value="">请选择行业</option>
                <option>互联网 / 人工智能与大模型</option>
                <option>金融 / 银行与保险</option>
                <option>教育 / 高等院校与科研</option>
                <option>制造 / 智能硬件与芯片</option>
                <option>医疗 / 生物医药与健康</option>
              </select>
              <p class="text-xs text-on-surface-variant mt-1.5">行业归类将决定推荐算法向对口专业的应届生进行定向匹配</p>
            </div>
          </div>

          <!-- 企业人员规模 & 总部所在地 -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">企业人员规模 <span class="text-error">*</span></label>
              <select v-model="form.scale"
                class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                <option value="">请选择规模</option>
                <option>0 - 49人（初创企业）</option>
                <option>50 - 99人（规模企业）</option>
                <option>100 - 499人（成长企业）</option>
                <option>500 - 999人（规模企业）</option>
                <option>1000 - 4999人（大型企业）</option>
                <option>5000人以上（集团企业）</option>
              </select>
            </div>
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">总部所在地 <span class="text-error">*</span></label>
              <div class="grid grid-cols-2 gap-2">
                <select v-model="form.province"
                  class="h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                  <option value="">省/直辖市</option>
                  <option>北京市</option>
                  <option>上海市</option>
                  <option>广东省</option>
                  <option>浙江省</option>
                  <option>江苏省</option>
                </select>
                <select v-model="form.district"
                  class="h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                  <option value="">区/县</option>
                  <option>海淀区</option>
                  <option>朝阳区</option>
                  <option>西湖区</option>
                  <option>南山区</option>
                </select>
              </div>
            </div>
          </div>

          <!-- 详细办公地址 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">详细办公地址 / 校招接待处 <span class="text-error">*</span></label>
            <input v-model="form.address" type="text" placeholder="中关村东路1号院清华科技园科技大厦B座8层"
              class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
          </div>

          <!-- 官方网站/招聘公众号 -->
          <div>
            <div class="flex items-center justify-between mb-2">
              <label class="text-body-sm font-medium text-on-surface">官方网站 / 招聘公众号 <span class="text-on-surface-variant font-normal">（选填）</span></label>
              <span class="text-xs text-on-surface-variant">有官网网页的高校审核通过率提升40%</span>
            </div>
            <input v-model="form.website" type="url" placeholder="https:// www.zhipuai.cn/careers"
              class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
          </div>
        </div>
      </div>

      <!-- 校园招聘对接人信息 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high mb-6">
        <div class="px-6 py-4 border-b border-surface-container-high">
          <div class="flex items-center gap-3">
            <span class="material-symbols-outlined text-primary text-xl">badge</span>
            <div>
              <h2 class="text-headline-sm font-semibold text-on-surface">校园招聘对接人信息</h2>
              <p class="text-body-sm text-on-surface-variant mt-0.5">校方就业指导中心教师联系及线下宣讲会进校确认人</p>
            </div>
          </div>
        </div>
        <div class="p-6 space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <!-- 招聘负责人/HR姓名 -->
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">招聘负责人 / HR 姓名 <span class="text-error">*</span></label>
              <input v-model="form.hrName" type="text" placeholder="陈雅静"
                class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
            </div>
            <!-- 直联系手机 -->
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">直联系手机 <span class="text-error">*</span> <span class="text-on-surface-variant font-normal">（用于接收高校就业办审核反馈短信）</span></label>
              <div class="flex">
                <span class="h-11 px-4 flex items-center rounded-l-xl border border-r-0 border-surface-container-high bg-surface-container-low text-on-surface-variant text-body-md">+86</span>
                <input v-model="form.hrPhone" type="tel" maxlength="11" placeholder="186 1024 8890"
                  class="flex-1 h-11 px-4 rounded-r-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
              </div>
            </div>
          </div>
          <!-- 企业工作邮箱 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">企业工作邮箱 <span class="text-error">*</span></label>
            <div class="relative">
              <input v-model="form.hrEmail" type="email" placeholder="yajing.chen@zhipuai.cn"
                class="w-full h-11 px-4 pr-10 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
              <span class="material-symbols-outlined absolute right-3 top-1/2 -translate-y-1/2 text-primary text-lg">mark_email_read</span>
            </div>
            <p class="text-xs text-on-surface-variant mt-1.5">请尽量提供带公司专属企业后缀的工作邮箱（如@zhipuai.cn），避免公共邮箱</p>
          </div>
        </div>
      </div>

      <!-- 企业资质证件上传 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high mb-6">
        <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high">
          <div class="flex items-center gap-3">
            <span class="material-symbols-outlined text-primary text-xl">upload_file</span>
            <div>
              <h2 class="text-headline-sm font-semibold text-on-surface">企业资质证件上传</h2>
              <p class="text-body-sm text-on-surface-variant mt-0.5">必须提供最新清晰彩色扫描件或加盖鲜章复印件</p>
            </div>
          </div>
          <div class="flex items-center gap-3">
            <button class="flex items-center gap-1 text-body-sm text-primary hover:underline">
              <span class="material-symbols-outlined text-base">visibility</span>
              查看规范示例图
            </button>
            <span class="px-2.5 py-1 bg-emerald-50 text-emerald-600 text-xs font-semibold rounded-full">直通国家一流高校绿通</span>
          </div>
        </div>
        <div class="p-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- 营业执照 -->
            <div>
              <div class="flex items-center justify-between mb-2">
                <label class="text-body-sm font-medium text-on-surface">营业执照扫描件（正本/副本） <span class="text-error">*</span></label>
                <span class="text-xs text-on-surface-variant">支持 JPG/PNG/PDF，最大 10MB</span>
              </div>
              <div v-if="!form.businessLicense"
                class="border-2 border-dashed border-surface-container-high rounded-2xl p-8 text-center cursor-pointer hover:border-primary/50 hover:bg-primary/5 transition-all"
                @click="triggerUpload('license')">
                <div class="w-16 h-16 mx-auto mb-4 rounded-full bg-primary/10 flex items-center justify-center">
                  <span class="material-symbols-outlined text-primary text-3xl">cloud_upload</span>
                </div>
                <p class="text-body-md text-on-surface font-medium">点击或将文件拖拽至此处上传</p>
                <p class="text-xs text-on-surface-variant mt-2">需保证企业名称、社会信用代码、法定代表人印章清晰无遮挡</p>
                <p class="text-xs text-on-surface-variant mt-2 flex items-center justify-center gap-1">
                  <span class="material-symbols-outlined text-sm text-primary">info</span>
                  仅用于高校实效审核，不对外公示展示
                </p>
              </div>
              <div v-else class="border border-surface-container-high rounded-2xl p-4">
                <div class="flex items-center gap-3">
                  <span class="material-symbols-outlined text-primary text-xl">description</span>
                  <div class="flex-1 min-w-0">
                    <p class="text-body-sm text-on-surface font-medium truncate">{{ form.businessLicense }}</p>
                    <p class="text-xs text-on-surface-variant">3.4 MB</p>
                  </div>
                  <span class="flex items-center gap-1 text-xs text-emerald-600 font-medium">
                    <span class="material-symbols-outlined text-sm">auto_fix_high</span>
                    OCR已解析
                  </span>
                  <button @click="form.businessLicense = ''" class="p-1 hover:bg-surface-container rounded-lg transition-colors">
                    <span class="material-symbols-outlined text-on-surface-variant text-lg">delete</span>
                  </button>
                </div>
              </div>
            </div>

            <!-- 法人身份证 -->
            <div>
              <div class="flex items-center justify-between mb-2">
                <label class="text-body-sm font-medium text-on-surface">法人身份证件或授权代表证件</label>
                <span class="text-xs text-on-surface-variant">（提前快速通道，可延后补充）</span>
              </div>
              <div class="grid grid-cols-2 gap-3">
                <!-- 人像面 -->
                <div v-if="!form.idCardFront"
                  class="border-2 border-dashed border-surface-container-high rounded-2xl p-6 text-center cursor-pointer hover:border-primary/50 hover:bg-primary/5 transition-all"
                  @click="triggerUpload('idcard-front')">
                  <div class="w-12 h-12 mx-auto mb-3 rounded-full bg-surface-container flex items-center justify-center">
                    <span class="material-symbols-outlined text-on-surface-variant text-xl">credit_card</span>
                  </div>
                  <p class="text-xs text-on-surface font-medium">身份证人像面</p>
                  <p class="text-xs text-on-surface-variant mt-1">点击上传</p>
                </div>
                <div v-else class="border border-surface-container-high rounded-2xl p-4">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-primary text-lg">credit_card</span>
                    <div class="flex-1 min-w-0">
                      <p class="text-xs text-on-surface font-medium truncate">{{ form.idCardFront }}</p>
                    </div>
                    <button @click="form.idCardFront = ''" class="p-1 hover:bg-surface-container rounded">
                      <span class="material-symbols-outlined text-on-surface-variant text-sm">close</span>
                    </button>
                  </div>
                </div>

                <!-- 国徽面 -->
                <div v-if="!form.idCardBack"
                  class="border-2 border-dashed border-surface-container-high rounded-2xl p-6 text-center cursor-pointer hover:border-primary/50 hover:bg-primary/5 transition-all"
                  @click="triggerUpload('idcard-back')">
                  <div class="w-12 h-12 mx-auto mb-3 rounded-full bg-surface-container flex items-center justify-center">
                    <span class="material-symbols-outlined text-on-surface-variant text-xl">credit_card</span>
                  </div>
                  <p class="text-xs text-on-surface font-medium">身份证国徽面</p>
                  <p class="text-xs text-on-surface-variant mt-1">点击上传</p>
                </div>
                <div v-else class="border border-surface-container-high rounded-2xl p-4">
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-primary text-lg">credit_card</span>
                    <div class="flex-1 min-w-0">
                      <p class="text-xs text-on-surface font-medium truncate">{{ form.idCardBack }}</p>
                    </div>
                    <button @click="form.idCardBack = ''" class="p-1 hover:bg-surface-container rounded">
                      <span class="material-symbols-outlined text-on-surface-variant text-sm">close</span>
                    </button>
                  </div>
                </div>
              </div>
              <p class="text-xs text-on-surface-variant mt-2">提示：若上传法人证件，将在各高校就业专场优先展示"直联标杆企业"金色认证徽标。</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 企业介绍与校招承诺 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high mb-6">
        <div class="px-6 py-4 border-b border-surface-container-high">
          <div class="flex items-center gap-3">
            <span class="material-symbols-outlined text-primary text-xl">article</span>
            <div>
              <h2 class="text-headline-sm font-semibold text-on-surface">企业介绍与校招承诺</h2>
              <p class="text-body-sm text-on-surface-variant mt-0.5">展示于各高校校招门户产生主页及双选会展位的企业官方导向</p>
            </div>
          </div>
        </div>
        <div class="p-6 space-y-5">
          <!-- 企业简述 -->
          <div>
            <div class="flex items-center justify-between mb-2">
              <label class="text-body-sm font-medium text-on-surface">企业简述及应届生发展环境 <span class="text-error">*</span></label>
              <span class="text-xs text-on-surface-variant">{{ form.intro.length }} / 500 字</span>
            </div>
            <textarea v-model="form.intro" rows="4" maxlength="500"
              class="w-full px-4 py-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 resize-none"
              placeholder="请输入企业简介..."></textarea>
          </div>

          <!-- 三严禁承诺 -->
          <div class="bg-primary/5 rounded-2xl p-5 border border-primary/10">
            <div class="flex items-center gap-2 mb-3">
              <span class="material-symbols-outlined text-primary text-lg">gavel</span>
              <h3 class="text-body-lg font-semibold text-on-surface">全国普通高等学校毕业生就业招聘"三严禁"与合规承诺</h3>
            </div>
            <p class="text-body-sm text-on-surface-variant leading-relaxed mb-4">为切实维护高校毕业生合法就业权益，用人单位须严格遵守教育部明确招聘规范：严禁发布含有性别、民族、宗教信仰等歧视性条款的招聘信息；严禁以任何名义向求职学生收取押金、报名费、体检费或培训费；严禁进行虚假岗位宣传。应届生求职者如遇强制签订不合理实习协议、超时发放报酬或强制签署人身意外...</p>
            <label class="flex items-center gap-3 cursor-pointer">
              <input v-model="form.agreed" type="checkbox" class="w-4 h-4 rounded border-surface-container-high text-primary focus:ring-primary/30" />
              <span class="text-body-sm text-on-surface">本单位郑重承诺严格遵守国家高校就业政策法规，绝不开展违规收费、虚假招聘及侵犯学生合法权益之行为，自愿接受高校就业指导中心及平台联合监督与履约考核。</span>
            </label>
          </div>
        </div>
      </div>

      <!-- 底部操作栏 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-4">
        <div class="flex items-center justify-between">
          <label class="flex items-center gap-2 text-body-sm text-on-surface-variant cursor-pointer">
            <input type="checkbox" v-model="form.agreeTerms" class="w-4 h-4 rounded border-surface-container-high text-primary focus:ring-primary/30" />
            已阅读并全权同意《校园招聘用人单位入驻守则及合规声明》与《全国高校招聘信息安全承诺书》
          </label>
          <div class="flex items-center gap-3">
            <button @click="saveDraft"
              class="h-11 px-6 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface font-medium hover:bg-surface-container transition-colors text-sm flex items-center gap-2">
              <span class="material-symbols-outlined text-lg">draft</span>
              保存草稿
            </button>
            <button @click="submitForm"
              class="h-11 px-8 rounded-xl bg-primary text-on-primary font-semibold hover:bg-primary-container transition-colors shadow-sm flex items-center gap-2 text-sm">
              <span class="material-symbols-outlined text-lg">send</span>
              提交资质审核
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-on-surface text-on-primary rounded-xl shadow-lg text-sm font-medium z-50">
      {{ toast }}
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'

const toast = ref('')

const form = reactive({
  companyName: '',
  creditCode: '',
  industry: '',
  scale: '',
  province: '北京市',
  district: '海淀区',
  address: '',
  website: '',
  hrName: '陈雅静',
  hrPhone: '18610248890',
  hrEmail: 'yajing.chen@zhipuai.cn',
  businessLicense: '北京智谱华章科技有限公司_营业执照副本2024.pdf',
  idCardFront: '',
  idCardBack: '',
  intro: '北京智谱华章科技有限公司（智谱AI）致力于打造新一代认知智能通用模型，由清华大学计算机系技术成果转化而来。公司核心团队曾主导GLM系列大模型研发，在学术科研与产业赋能层面处于领先水平。我们为2025届应届毕业生设立了专项技术管培生计划，配备顶尖博士导师带教与前沿算力资源，提供极具竞争力的成长通道与薪酬保障。',
  agreed: true,
  agreeTerms: true
})

function triggerUpload(type) {
  if (type === 'license') {
    form.businessLicense = '营业执照_2024.pdf'
  } else if (type === 'idcard-front') {
    form.idCardFront = '法人身份证人像面.jpg'
  } else {
    form.idCardBack = '法人身份证国徽面.jpg'
  }
}

function saveDraft() {
  showToast('草稿已保存')
}

function submitForm() {
  showToast('资质审核已提交')
}

function showToast(msg) {
  toast.value = msg
  setTimeout(() => toast.value = '', 2000)
}
</script>
