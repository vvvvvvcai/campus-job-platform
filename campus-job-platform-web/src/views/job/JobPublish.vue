<template>
  <div class="w-full bg-surface-container-lowest shadow-sm">
    <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-6">
      <nav class="flex items-center gap-1 text-sm text-on-surface-variant mb-2">
        <router-link class="hover:text-primary transition-colors" to="/">企业管理中心</router-link>
        <span class="material-symbols-outlined text-[16px] text-outline-variant">chevron_right</span>
        <router-link class="hover:text-primary transition-colors" to="/jobs">职位管理</router-link>
        <span class="material-symbols-outlined text-[16px] text-outline-variant">chevron_right</span>
        <span class="text-primary font-medium">发布新校招/实习职位</span>
      </nav>

      <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
        <div>
          <div class="flex items-center gap-2">
            <h1 class="text-2xl text-on-surface font-bold tracking-tight">发布新校招 / 实习职位</h1>
            <span class="inline-flex items-center px-2 py-0.5 rounded-full bg-primary-fixed text-on-primary-fixed text-xs font-semibold">
              高校互联通道已就绪
            </span>
          </div>
          <p class="text-sm text-on-surface-variant mt-1">
            面向全国高校应届毕业生及在校生精准推送，支持一键关联三方协议、网签流程及高校就业指导中心直联分发。
          </p>
        </div>
        <div class="flex items-center gap-2 self-start md:self-auto bg-surface-container-high px-4 py-2 rounded-lg">
          <span class="material-symbols-outlined text-primary text-[20px]">verified_user</span>
          <div class="flex flex-col">
            <span class="text-xs text-primary font-bold">校企直通保障</span>
            <span class="text-xs text-on-surface-variant">已认证：北京智维人工智能研究院</span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-6 w-full">
    <div class="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
      <div class="lg:col-span-7 flex flex-col gap-6">
        <div class="bg-surface-container-lowest rounded-xl p-6 shadow-sm">
          <div class="flex items-center justify-between pb-2 mb-4">
            <div class="flex items-center gap-2">
              <span class="flex items-center justify-center w-7 h-7 rounded-lg bg-primary-container text-on-primary text-xs font-bold">01</span>
              <h2 class="text-lg text-on-surface font-bold">基础信息</h2>
            </div>
            <span class="text-xs text-on-surface-variant">必填项用于生成教育部备案编码</span>
          </div>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1" for="job-title-input">
                职位名称 <span class="text-error">*</span>
              </label>
              <div class="relative">
                <input
                  v-model="jobTitle"
                  class="w-full h-10 px-4 bg-surface-container-low rounded-lg text-sm text-on-surface focus:bg-surface-container-lowest focus:outline-none focus:ring-2 focus:ring-primary shadow-inner"
                  id="job-title-input"
                  placeholder="例如：2025届 研发管培生 / 全栈软件工程师"
                  type="text"
                />
                <span class="absolute right-3 top-2.5 text-xs text-outline">{{ jobTitle.length }}/50字</span>
              </div>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-3 gap-4">
              <div class="sm:col-span-2">
                <label class="block text-sm font-medium text-on-surface mb-1" for="job-category-select">
                  职位类别 (三级级联) <span class="text-error">*</span>
                </label>
                <div class="relative">
                  <select
                    v-model="jobCategory"
                    class="w-full h-10 px-4 bg-surface-container-low rounded-lg text-sm text-on-surface appearance-none focus:outline-none focus:ring-2 focus:ring-primary"
                    id="job-category-select"
                  >
                    <option>技术研发 > 人工智能 > 算法工程师</option>
                    <option>技术研发 > 后端开发 > Java开发工程师</option>
                    <option>产品设计 > 产品策划 > AI智能产品经理</option>
                    <option>运营市场 > 高校生态 > 产学研管培生</option>
                  </select>
                  <span class="material-symbols-outlined absolute right-3 top-2.5 pointer-events-none text-outline">expand_more</span>
                </div>
              </div>
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1" for="hire-count">
                  招聘人数 <span class="text-error">*</span>
                </label>
                <div class="relative">
                  <input
                    v-model.number="hireCount"
                    class="w-full h-10 px-4 bg-surface-container-low rounded-lg text-sm text-on-surface focus:outline-none focus:ring-2 focus:ring-primary"
                    id="hire-count"
                    max="500"
                    min="1"
                    type="number"
                  />
                  <span class="absolute right-3 top-2.5 text-xs text-outline">人</span>
                </div>
              </div>
            </div>

            <div>
              <span class="block text-sm font-medium text-on-surface mb-2">
                工作类型 <span class="text-error">*</span>
              </span>
              <div class="grid grid-cols-1 sm:grid-cols-3 gap-2">
                <label class="flex items-center gap-2 p-2 rounded-lg bg-surface-container cursor-pointer transition-colors hover:bg-surface-container-high">
                  <input v-model="jobNature" class="accent-primary w-4 h-4" type="radio" value="全职校招 (应届正式)" />
                  <span class="text-sm text-on-surface font-medium">全职校招 (应届正式)</span>
                </label>
                <label class="flex items-center gap-2 p-2 rounded-lg bg-surface-container-low cursor-pointer transition-colors hover:bg-surface-container-high">
                  <input v-model="jobNature" class="accent-primary w-4 h-4" type="radio" value="实习 (可转正)" />
                  <span class="text-sm text-on-surface">实习 (可转正)</span>
                </label>
                <label class="flex items-center gap-2 p-2 rounded-lg bg-surface-container-low cursor-pointer transition-colors hover:bg-surface-container-high">
                  <input v-model="jobNature" class="accent-primary w-4 h-4" type="radio" value="寒暑期日常实习" />
                  <span class="text-sm text-on-surface">寒暑期日常实习</span>
                </label>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-xl p-6 shadow-sm">
          <div class="flex items-center justify-between pb-2 mb-4">
            <div class="flex items-center gap-2">
              <span class="flex items-center justify-center w-7 h-7 rounded-lg bg-primary-container text-on-primary text-xs font-bold">02</span>
              <h2 class="text-lg text-on-surface font-bold">岗位要求与薪资体系</h2>
            </div>
            <span class="text-xs text-secondary bg-secondary-fixed px-2 py-0.5 rounded-full font-medium">符合教育部招聘透明度标准</span>
          </div>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1" for="work-city-input">
                工作城市与园区地点 <span class="text-error">*</span>
              </label>
              <div class="grid grid-cols-1 sm:grid-cols-3 gap-2">
                <div class="relative">
                  <select
                    v-model="workCity"
                    class="w-full h-10 px-4 bg-surface-container-low rounded-lg text-sm text-on-surface appearance-none focus:outline-none focus:ring-2 focus:ring-primary"
                    id="work-city-input"
                  >
                    <option>北京市</option>
                    <option>上海市</option>
                    <option>深圳市</option>
                    <option>杭州市</option>
                    <option>成都市</option>
                  </select>
                  <span class="material-symbols-outlined absolute right-3 top-2.5 pointer-events-none text-outline">arrow_drop_down</span>
                </div>
                <div class="sm:col-span-2">
                  <input
                    v-model="workAddress"
                    class="w-full h-10 px-4 bg-surface-container-low rounded-lg text-sm text-on-surface focus:outline-none focus:ring-2 focus:ring-primary"
                    id="work-address-input"
                    placeholder="详细地址，如写字楼/工业园区"
                    type="text"
                  />
                </div>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-on-surface mb-1">
                薪酬范围与薪数 <span class="text-error">*</span>
              </label>
              <div class="grid grid-cols-1 sm:grid-cols-12 gap-2 items-center">
                <div class="sm:col-span-4 relative">
                  <span class="absolute left-3 top-2.5 text-xs text-outline">最低:</span>
                  <input
                    v-model.number="salaryMin"
                    class="w-full h-10 pl-12 pr-8 bg-surface-container-low rounded-lg text-sm text-on-surface focus:outline-none focus:ring-2 focus:ring-primary font-bold"
                    id="salary-min-input"
                    type="number"
                  />
                  <span class="absolute right-3 top-2.5 text-xs text-outline">k</span>
                </div>
                <div class="sm:col-span-1 text-center text-lg text-outline hidden sm:block">-</div>
                <div class="sm:col-span-4 relative">
                  <span class="absolute left-3 top-2.5 text-xs text-outline">最高:</span>
                  <input
                    v-model.number="salaryMax"
                    class="w-full h-10 pl-12 pr-8 bg-surface-container-low rounded-lg text-sm text-on-surface focus:outline-none focus:ring-2 focus:ring-primary font-bold"
                    id="salary-max-input"
                    type="number"
                  />
                  <span class="absolute right-3 top-2.5 text-xs text-outline">k</span>
                </div>
                <div class="sm:col-span-3 relative">
                  <select
                    v-model="salaryMonths"
                    class="w-full h-10 px-4 bg-surface-container-low rounded-lg text-sm text-on-surface appearance-none focus:outline-none focus:ring-2 focus:ring-primary"
                    id="salary-months-input"
                  >
                    <option>12薪 (基本)</option>
                    <option>13薪</option>
                    <option>14薪</option>
                    <option>15薪 (标准大厂)</option>
                    <option>16薪</option>
                  </select>
                  <span class="material-symbols-outlined absolute right-2.5 top-2.5 pointer-events-none text-outline text-[18px]">expand_more</span>
                </div>
              </div>
              <p class="text-xs text-on-surface-variant mt-1.5 flex items-center gap-1">
                <span class="material-symbols-outlined text-[16px] text-secondary">info</span>
                若选择实习日薪制，可填写如 400-600元/天，系统将在卡片高亮标注"日结/月结实习"。
              </p>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1" for="degree-select">
                  最低学历门槛 <span class="text-error">*</span>
                </label>
                <div class="relative">
                  <select
                    v-model="degree"
                    class="w-full h-10 px-4 bg-surface-container-low rounded-lg text-sm text-on-surface appearance-none focus:outline-none focus:ring-2 focus:ring-primary"
                    id="degree-select"
                  >
                    <option>大专及以上</option>
                    <option>大学本科及以上</option>
                    <option>硕士研究生及以上</option>
                    <option>博士研究生</option>
                    <option>学历不限</option>
                  </select>
                  <span class="material-symbols-outlined absolute right-3 top-2.5 pointer-events-none text-outline">expand_more</span>
                </div>
              </div>
              <div>
                <span class="block text-sm font-medium text-on-surface mb-1">
                  面向届别 (可多选) <span class="text-error">*</span>
                </span>
                <div class="flex flex-wrap gap-2 mt-1">
                  <label v-for="item in yearOptions" :key="item.value" class="cursor-pointer">
                    <input v-model="selectedYears" class="sr-only peer" type="checkbox" :value="item.value" />
                    <span class="inline-flex items-center px-2 py-1 rounded-md bg-surface-container-low text-on-surface text-xs peer-checked:bg-primary-container peer-checked:text-on-primary transition-all">
                      {{ item.label }}
                    </span>
                  </label>
                </div>
              </div>
            </div>

            <div>
              <span class="block text-sm font-medium text-on-surface mb-1.5">
                专业对口标签 (系统智能匹配高校专业代码)
              </span>
              <div class="flex flex-wrap gap-2">
                <span v-for="tag in majorTags" :key="tag" class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full bg-secondary-fixed text-on-secondary-fixed text-xs">
                  {{ tag }}
                  <span class="material-symbols-outlined text-[14px] cursor-pointer hover:opacity-75" @click="removeMajorTag(tag)">close</span>
                </span>
                <span v-for="suggestion in suggestedMajors" :key="suggestion" class="inline-flex items-center gap-1 px-2.5 py-1 rounded-full bg-surface-container text-on-surface-variant text-xs cursor-pointer hover:bg-surface-container-high" @click="addMajorTag(suggestion)">
                  + {{ suggestion }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-xl p-6 shadow-sm">
          <div class="flex items-center justify-between pb-2 mb-4">
            <div class="flex items-center gap-2">
              <span class="flex items-center justify-center w-7 h-7 rounded-lg bg-primary-container text-on-primary text-xs font-bold">03</span>
              <h2 class="text-lg text-on-surface font-bold">职位职责与任职要求</h2>
            </div>
            <span class="text-xs text-on-surface-variant">支持 Markdown 规范排版</span>
          </div>
          <div class="space-y-4">
            <div>
              <div class="flex items-center justify-between mb-1">
                <label class="text-sm font-medium text-on-surface" for="job-resp-textarea">
                  岗位职责 (Key Responsibilities) <span class="text-error">*</span>
                </label>
                <div class="flex items-center gap-1 text-on-surface-variant">
                  <button class="p-1 rounded hover:bg-surface-container" type="button"><span class="material-symbols-outlined text-[18px]">format_bold</span></button>
                  <button class="p-1 rounded hover:bg-surface-container" type="button"><span class="material-symbols-outlined text-[18px]">format_list_bulleted</span></button>
                  <button class="p-1 rounded hover:bg-surface-container" type="button"><span class="material-symbols-outlined text-[18px]">format_list_numbered</span></button>
                </div>
              </div>
              <textarea
                v-model="jobResponsibilities"
                class="w-full p-4 bg-surface-container-low rounded-lg text-sm text-on-surface focus:outline-none focus:ring-2 focus:ring-primary leading-relaxed"
                id="job-resp-textarea"
                rows="5"
              ></textarea>
            </div>
            <div>
              <div class="flex items-center justify-between mb-1">
                <label class="text-sm font-medium text-on-surface" for="job-req-textarea">
                  任职要求 (Requirements) <span class="text-error">*</span>
                </label>
                <div class="flex items-center gap-1 text-on-surface-variant">
                  <button class="p-1 rounded hover:bg-surface-container" type="button"><span class="material-symbols-outlined text-[18px]">format_bold</span></button>
                  <button class="p-1 rounded hover:bg-surface-container" type="button"><span class="material-symbols-outlined text-[18px]">format_list_bulleted</span></button>
                  <button class="p-1 rounded hover:bg-surface-container" type="button"><span class="material-symbols-outlined text-[18px]">code</span></button>
                </div>
              </div>
              <textarea
                v-model="jobRequirements"
                class="w-full p-4 bg-surface-container-low rounded-lg text-sm text-on-surface focus:outline-none focus:ring-2 focus:ring-primary leading-relaxed"
                id="job-req-textarea"
                rows="6"
              ></textarea>
            </div>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-xl p-6 shadow-sm">
          <div class="flex items-center justify-between pb-2 mb-4">
            <div class="flex items-center gap-2">
              <span class="flex items-center justify-center w-7 h-7 rounded-lg bg-primary-container text-on-primary text-xs font-bold">04</span>
              <h2 class="text-lg text-on-surface font-bold">校园福利与人才补贴</h2>
            </div>
            <span class="text-xs text-on-surface-variant">点击即可点亮或取消</span>
          </div>
          <div class="space-y-4">
            <div class="flex flex-wrap gap-2">
              <span
                v-for="tag in welfareTags"
                :key="tag.label"
                class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg font-label-md text-label-md cursor-pointer select-none transition-all"
                :class="tag.active ? 'bg-primary-fixed text-on-primary-fixed' : 'bg-surface-container text-on-surface-variant hover:bg-surface-container-high'"
                @click="tag.active = !tag.active"
              >
                <span v-if="tag.active" class="material-symbols-outlined text-[16px]">check</span>
                <span v-else>+</span>
                {{ tag.label }}
              </span>
            </div>
            <div class="flex items-center gap-2 pt-2">
              <input
                v-model="customWelfare"
                class="flex-1 h-9 px-4 bg-surface-container-low rounded-lg text-xs text-on-surface focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="新增特色福利（如：MacBook顶配办公、报销答辩往返机票）"
                type="text"
              />
              <button
                class="h-9 px-4 bg-surface-container text-primary text-xs rounded-lg hover:bg-surface-container-high transition-colors"
                type="button"
                @click="addCustomWelfare"
              >
                添加标签
              </button>
            </div>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-xl p-6 shadow-sm flex flex-col sm:flex-row items-center justify-between gap-4">
          <div class="flex items-center gap-2 text-on-surface-variant text-xs">
            <span class="material-symbols-outlined text-[20px] text-secondary">cloud_done</span>
            <span>表单已自动保存于 {{ currentTime }}</span>
          </div>
          <div class="flex items-center gap-3 w-full sm:w-auto">
            <button class="flex-1 sm:flex-none h-11 px-6 rounded-lg bg-surface-container text-on-surface text-sm hover:bg-surface-container-high transition-colors" type="button">
              保存为草稿
            </button>
            <button
              class="flex-1 sm:flex-none h-11 px-8 rounded-lg bg-primary-container text-on-primary text-sm hover:bg-surface-tint shadow-md transition-all flex items-center justify-center gap-2"
              type="button"
              @click="publishJob"
            >
              <span class="material-symbols-outlined text-[20px]">rocket_launch</span>
              <span>立即发布至全国高校就业网</span>
            </button>
          </div>
        </div>
      </div>

      <div class="lg:col-span-5 lg:sticky lg:top-20 space-y-4">
        <div class="flex items-center justify-between px-4 py-2 bg-surface-container rounded-lg">
          <div class="flex items-center gap-2">
            <span class="relative flex h-2.5 w-2.5">
              <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-secondary opacity-75"></span>
              <span class="relative inline-flex rounded-full h-2.5 w-2.5 bg-secondary"></span>
            </span>
            <span class="text-xs text-on-surface font-semibold">学生端职位卡片实时渲染</span>
          </div>
          <span class="text-xs text-on-surface-variant">1:1 视口真实映射</span>
        </div>

        <div class="bg-surface-container-lowest rounded-2xl p-6 shadow-xl relative overflow-hidden transition-all duration-300">
          <div class="absolute -top-16 -right-16 w-48 h-48 bg-primary-fixed/20 rounded-full blur-3xl pointer-events-none"></div>

          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center gap-3">
              <div class="w-12 h-12 rounded-xl object-cover shadow-sm bg-surface-container flex items-center justify-center">
                <span class="material-symbols-outlined text-2xl text-primary">business</span>
              </div>
              <div>
                <div class="flex items-center gap-1">
                  <h3 class="text-lg text-on-surface font-bold">智维人工智能研究院</h3>
                  <span class="material-symbols-outlined text-secondary text-[18px]" title="全国高校官方认证签约用人单位">verified</span>
                </div>
                <p class="text-xs text-on-surface-variant">人工智能 · 前沿科技 / 500-1000人 / 国有参股独角兽</p>
              </div>
            </div>
            <span class="px-2 py-1 rounded-full bg-error-container text-on-error-container text-xs font-bold uppercase">
              校招热招中
            </span>
          </div>

          <div class="mb-2">
            <h2 class="text-xl text-on-surface font-bold tracking-tight">
              {{ jobTitle || '未填写职位名称' }}
            </h2>
          </div>

          <div class="flex items-baseline gap-2 mb-4">
            <span class="text-3xl text-primary font-bold">{{ salaryMin || 0 }}k - {{ salaryMax || 0 }}k</span>
            <span class="text-sm text-primary font-semibold">· {{ salaryMonths }}</span>
            <span class="ml-2 text-xs text-on-surface-variant bg-surface-container px-2 py-0.5 rounded">
              五险一金全额
            </span>
          </div>

          <div class="flex flex-wrap gap-2 mb-6">
            <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-md bg-surface-container text-on-surface-variant text-xs font-medium">
              <span class="material-symbols-outlined text-[16px]">location_on</span>
              {{ workCity }} · {{ workAddress ? workAddress.split(' ').pop() : '' }}
            </span>
            <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-md bg-surface-container text-on-surface-variant text-xs font-medium">
              <span class="material-symbols-outlined text-[16px]">school</span>
              {{ degree }}
            </span>
            <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-md bg-surface-container text-on-surface-variant text-xs font-medium">
              <span class="material-symbols-outlined text-[16px]">group</span>
              招 {{ hireCount }} 人
            </span>
            <span class="inline-flex items-center gap-1 px-2.5 py-1 rounded-md bg-secondary-fixed text-on-secondary-fixed text-xs font-semibold">
              {{ jobNature }}
            </span>
          </div>

          <div class="p-4 rounded-xl bg-surface-container-low mb-6">
            <div class="flex items-center justify-between mb-2">
              <span class="text-xs text-on-surface font-bold flex items-center gap-1">
                <span class="material-symbols-outlined text-secondary text-[18px]">auto_awesome</span>
                高校学生画像 AI 拟合度
              </span>
              <span class="text-xs text-secondary font-bold">96% 高度契合</span>
            </div>
            <div class="w-full bg-surface-container-highest rounded-full h-2 overflow-hidden">
              <div class="bg-secondary h-2 rounded-full transition-all duration-500" style="width: 96%"></div>
            </div>
            <p class="text-xs text-on-surface-variant mt-1.5">
              该岗位画像优先推送给：清华大学、北京大学、北京航空航天大学、中国科学院大学计算机与人工智能学院。
            </p>
          </div>

          <div class="mb-6">
            <span class="block text-xs text-outline font-bold uppercase mb-2 tracking-wider">专属校园福利</span>
            <div class="flex flex-wrap gap-1.5">
              <span v-for="tag in activeWelfareLabels" :key="tag" class="px-2 py-0.5 rounded bg-surface-container text-on-surface text-xs">{{ tag }}</span>
            </div>
          </div>

          <div class="space-y-2 mb-6">
            <span class="block text-xs text-outline font-bold uppercase tracking-wider">岗位概要</span>
            <div class="text-xs text-on-surface-variant leading-relaxed line-clamp-3">
              {{ jobSummaryPreview }}
            </div>
          </div>

          <div class="pt-4 flex items-center gap-3">
            <button class="w-12 h-11 flex items-center justify-center rounded-xl bg-surface-container text-on-surface-variant hover:text-primary transition-colors" type="button">
              <span class="material-symbols-outlined text-[22px]">bookmark_border</span>
            </button>
            <button class="w-12 h-11 flex items-center justify-center rounded-xl bg-surface-container text-on-surface-variant hover:text-primary transition-colors" type="button">
              <span class="material-symbols-outlined text-[22px]">share</span>
            </button>
            <div class="flex-1 h-11 rounded-xl bg-primary-container text-on-primary text-sm font-bold flex items-center justify-center gap-2 shadow-md">
              <span>一键投递校园电子简历</span>
              <span class="material-symbols-outlined text-[18px]">send</span>
            </div>
          </div>

          <div class="mt-4 pt-2 flex items-center justify-center gap-1 text-xs text-outline">
            <span class="material-symbols-outlined text-[14px]">assured_workload</span>
            <span>已接入全国普通高校毕业生就业网格化备案通道</span>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-xl p-4 shadow-sm">
          <div class="flex items-start gap-3">
            <span class="material-symbols-outlined text-secondary text-[24px] mt-0.5">lightbulb</span>
            <div>
              <h4 class="text-sm text-on-surface font-bold">校招发布高转化建议</h4>
              <p class="text-xs text-on-surface-variant mt-1 leading-relaxed">
                清晰注明"导师机制"与"转正比例"可使优质应届生投递意愿提高 <strong class="text-primary font-semibold">42%</strong>；明确标注入职第一年保底薪酬能有效降低面试爽约率。
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const jobTitle = ref('2025届 大模型算法工程师 (校招/实习)')
const jobCategory = ref('技术研发 > 人工智能 > 算法工程师')
const hireCount = ref(5)
const jobNature = ref('全职校招 (应届正式)')

const workCity = ref('北京市')
const workAddress = ref('海淀区 中关村软件园二期 智维大厦A座8层')

const salaryMin = ref(20)
const salaryMax = ref(35)
const salaryMonths = ref('15薪 (标准大厂)')

const degree = ref('硕士研究生及以上')
const selectedYears = ref(['2025届应届生', '2026届实习生'])
const yearOptions = [
  { label: '2024届往届 (补录)', value: '2024届往届 (补录)' },
  { label: '2025届应届生', value: '2025届应届生' },
  { label: '2026届实习生', value: '2026届实习生' },
]

const majorTags = ref(['计算机科学与技术', '人工智能 / 智能科学', '软件工程'])
const suggestedMajors = ref(['自动化控制', '应用数学 / 统计学', '电子信息工程'])

function removeMajorTag(tag) {
  majorTags.value = majorTags.value.filter((t) => t !== tag)
}
function addMajorTag(tag) {
  if (!majorTags.value.includes(tag)) {
    majorTags.value.push(tag)
    suggestedMajors.value = suggestedMajors.value.filter((t) => t !== tag)
  }
}

const jobResponsibilities = ref(
  '1. 参与百亿/千亿级多模态大模型的预训练、指令微调（SFT）以及基于人类反馈的强化学习（RLHF）；\n' +
    '2. 负责面向校园智能教育助手的算法落地，优化推理延迟与显存占用；\n' +
    '3. 协助推进前沿大语言模型（LLM）对齐算法研发及学术论文产出；\n' +
    '4. 与工程团队密切协作，搭建高可用模型蒸馏与低精度量化部署工作流。'
)

const jobRequirements = ref(
  '1. 2025年毕业的海内外知名高校硕士及以上学历，计算机、AI、软件等相关专业；\n' +
    '2. 熟练掌握 Python，深入理解 PyTorch/DeepSpeed/Megatron 等主流分布式训练框架；\n' +
    '3. 具备坚实的数据结构与算法基础，熟悉 Transformer 架构内部机制与 Attention 优化；\n' +
    '4. 在 NeurIPS、ICLR、ACL、CVPR 等顶级 AI 会议发表第一作者论文者，或 Kaggle / 天池大模型赛道高分选手优先；\n' +
    '5. 善于沟通探索，对 AGI 技术保持极致热爱，具备优秀的团队协作抗压能力。'
)

const welfareTags = ref([
  { label: '五险一金', active: true },
  { label: '转正机会90%', active: true },
  { label: '导师1对1带教', active: true },
  { label: '租房补贴 (2500/月)', active: true },
  { label: '免费精品三餐', active: true },
  { label: '落户指标申报通道', active: true },
  { label: '弹性工时/不打卡', active: true },
  { label: '定期学术休假', active: false },
  { label: '毕业设计专属辅导', active: false },
])
const customWelfare = ref('')
function addCustomWelfare() {
  if (customWelfare.value.trim()) {
    welfareTags.value.push({ label: customWelfare.value.trim(), active: true })
    customWelfare.value = ''
  }
}

const activeWelfareLabels = computed(() => welfareTags.value.filter((t) => t.active).map((t) => t.label))

const jobSummaryPreview = computed(() => {
  const lines = jobResponsibilities.value.trim().split('\n')
  return lines.slice(0, 2).join(' ') || '暂无职责说明'
})

const currentTime = ref('')
function updateTime() {
  const now = new Date()
  currentTime.value = now.toLocaleTimeString('zh-CN', { hour12: false })
}
updateTime()
setInterval(updateTime, 1000)

function publishJob() {
  alert('职位已通过全国高校联合网签认证体系，并推送至 2025 届应届生推荐流！')
}
</script>
