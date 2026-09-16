<template>
  <div class="min-h-screen bg-background pb-8">
    <!-- 页面头部 -->
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-4xl mx-auto px-6">
        <div class="flex items-start justify-between">
          <div>
            <div class="flex items-center gap-3">
              <h1 class="text-headline-xl font-bold text-on-surface">发布新校招 / 实习职位</h1>
              <span class="px-2.5 py-1 bg-emerald-50 text-emerald-600 text-xs font-semibold rounded-full">高校互联互通数据对接</span>
            </div>
            <p class="text-body-md text-on-surface-variant mt-1.5">面向全国高校应届毕业生及在校生精准推送，支持一键关联三方协议、网签流程及高校就业指导中心直属分发。</p>
          </div>
          <div class="flex items-center gap-2 px-4 py-2.5 bg-surface-container-lowest rounded-xl border border-surface-container-high">
            <span class="material-symbols-outlined text-primary text-xl">verified</span>
            <div>
              <div class="text-xs font-semibold text-on-surface">校企直通保障</div>
              <div class="text-xs text-on-surface-variant">已认证: 北京智航未来智能研究院</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 表单内容 -->
    <div class="max-w-4xl mx-auto px-6 py-8 space-y-6">
      <!-- 步骤01：基础信息 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high bg-surface-container-low/30">
          <div class="flex items-center gap-3">
            <div class="w-8 h-8 rounded-full bg-primary flex items-center justify-center">
              <span class="text-sm font-bold text-on-primary">01</span>
            </div>
            <h2 class="text-headline-sm font-semibold text-on-surface">基础信息</h2>
          </div>
          <span class="text-body-sm text-on-surface-variant">必填项用于生成教育部备案编码</span>
        </div>
        <div class="p-6 space-y-5">
          <!-- 职位名称 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">职位名称 <span class="text-error">*</span></label>
            <div class="relative">
              <input v-model="form.title" type="text" placeholder="2025届 大模型算法工程师（校招/实习）"
                class="w-full h-11 px-4 pr-16 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all" />
              <span class="absolute right-4 top-1/2 -translate-y-1/2 text-body-sm text-on-surface-variant">{{ form.title.length }}/80字</span>
            </div>
          </div>

          <!-- 职位类别 & 招聘人数 -->
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div class="md:col-span-2">
              <label class="block text-body-sm font-medium text-on-surface mb-2">职位类别（三级级联）<span class="text-error">*</span></label>
              <select v-model="form.category"
                class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                <option value="">请选择职位类别</option>
                <option>技术研发 > 人工智能 > 算法工程师</option>
                <option>技术研发 > 前端开发 > 前端工程师</option>
                <option>技术研发 > 后端开发 > 后端工程师</option>
                <option>产品策划 > 产品经理 > 产品经理</option>
              </select>
            </div>
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">招聘人数 <span class="text-error">*</span></label>
              <div class="relative">
                <input v-model="form.headcount" type="number" min="1"
                  class="w-full h-11 px-4 pr-8 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
                <span class="absolute right-4 top-1/2 -translate-y-1/2 text-body-sm text-on-surface-variant">人</span>
              </div>
            </div>
          </div>

          <!-- 工作类型 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">工作类型 <span class="text-error">*</span></label>
            <div class="grid grid-cols-3 gap-3">
              <label v-for="type in workTypes" :key="type.value"
                class="flex items-center gap-3 px-4 py-3 rounded-xl border cursor-pointer transition-all"
                :class="form.workType === type.value ? 'border-primary bg-primary/5' : 'border-surface-container-high hover:border-surface-container-high/80'">
                <input v-model="form.workType" type="radio" :value="type.value"
                  class="w-4 h-4 text-primary focus:ring-primary/30 border-surface-container-high" />
                <span class="text-body-sm" :class="form.workType === type.value ? 'text-primary font-medium' : 'text-on-surface'">{{ type.label }}</span>
              </label>
            </div>
          </div>
        </div>
      </div>

      <!-- 步骤02：岗位要求与薪资体系 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high bg-surface-container-low/30">
          <div class="flex items-center gap-3">
            <div class="w-8 h-8 rounded-full bg-primary flex items-center justify-center">
              <span class="text-sm font-bold text-on-primary">02</span>
            </div>
            <h2 class="text-headline-sm font-semibold text-on-surface">岗位要求与薪资体系</h2>
          </div>
          <span class="px-2.5 py-1 bg-primary/10 text-primary text-xs font-semibold rounded-full">符合教育部知网简历合规标准</span>
        </div>
        <div class="p-6 space-y-5">
          <!-- 工作城市与园区地点 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">工作城市与园区地点 <span class="text-error">*</span></label>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-3">
              <select v-model="form.city"
                class="h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                <option value="">请选择城市</option>
                <option>北京市</option>
                <option>上海市</option>
                <option>深圳市</option>
                <option>杭州市</option>
                <option>广州市</option>
                <option>成都市</option>
              </select>
              <input v-model="form.address" type="text" placeholder="海淀区 中关村软件园二期 智维大厦A座8层"
                class="h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
            </div>
          </div>

          <!-- 薪酬范围与薪数 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">薪酬范围与薪数 <span class="text-error">*</span></label>
            <div class="grid grid-cols-1 md:grid-cols-4 gap-3 items-end">
              <div>
                <label class="block text-xs text-on-surface-variant mb-1">最低:</label>
                <div class="relative">
                  <input v-model="form.salaryMin" type="number" min="0"
                    class="w-full h-11 px-4 pr-6 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
                  <span class="absolute right-3 top-1/2 -translate-y-1/2 text-body-sm text-on-surface-variant">k</span>
                </div>
              </div>
              <div class="flex items-center justify-center pb-1">
                <span class="text-on-surface-variant">-</span>
              </div>
              <div>
                <label class="block text-xs text-on-surface-variant mb-1">最高:</label>
                <div class="relative">
                  <input v-model="form.salaryMax" type="number" min="0"
                    class="w-full h-11 px-4 pr-6 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20" />
                  <span class="absolute right-3 top-1/2 -translate-y-1/2 text-body-sm text-on-surface-variant">k</span>
                </div>
              </div>
              <div>
                <label class="block text-xs text-on-surface-variant mb-1">&nbsp;</label>
                <select v-model="form.salaryType"
                  class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                  <option>15薪 (标准大厂)</option>
                  <option>14薪</option>
                  <option>16薪</option>
                  <option>12薪</option>
                </select>
              </div>
            </div>
            <p class="text-xs text-on-surface-variant mt-2 flex items-center gap-1">
              <span class="material-symbols-outlined text-sm text-primary">info</span>
              若选择实习/日薪制，可填写如 400-600元/天，系统等在卡片愿意标注"日结/月结/实习"。
            </p>
          </div>

          <!-- 最低学历门槛 & 面向届别 -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">最低学历门槛 <span class="text-error">*</span></label>
              <select v-model="form.education"
                class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 appearance-none cursor-pointer">
                <option>硕士研究生及以上</option>
                <option>本科及以上</option>
                <option>大专及以上</option>
                <option>不限学历</option>
              </select>
            </div>
            <div>
              <label class="block text-body-sm font-medium text-on-surface mb-2">面向届别（可多选）<span class="text-error">*</span></label>
              <div class="flex flex-wrap gap-2">
                <label v-for="year in yearOptions" :key="year"
                  class="flex items-center gap-2 px-3 py-2 rounded-lg border cursor-pointer transition-all text-sm"
                  :class="form.years.includes(year) ? 'border-primary bg-primary/5 text-primary' : 'border-surface-container-high text-on-surface-variant hover:border-surface-container-high/80'">
                  <input v-model="form.years" type="checkbox" :value="year" class="w-4 h-4 rounded text-primary focus:ring-primary/30 border-surface-container-high" />
                  {{ year }}
                </label>
              </div>
            </div>
          </div>

          <!-- 专业对口标签 -->
          <div>
            <label class="block text-body-sm font-medium text-on-surface mb-2">专业对口标签（系统智能匹配高校专业代码）</label>
            <div class="flex flex-wrap gap-2">
              <span v-for="(tag, idx) in form.majorTags" :key="idx"
                class="inline-flex items-center gap-1.5 px-3 py-1.5 bg-primary/10 text-primary text-sm font-medium rounded-lg">
                {{ tag }}
                <button @click="removeMajorTag(idx)" class="hover:bg-primary/20 rounded p-0.5 transition-colors">
                  <span class="material-symbols-outlined text-sm">close</span>
                </button>
              </span>
              <div class="relative" ref="majorTagDropdown">
                <button @click="showMajorDropdown = !showMajorDropdown"
                  class="inline-flex items-center gap-1 px-3 py-1.5 border border-dashed border-surface-container-high text-on-surface-variant text-sm rounded-lg hover:border-primary hover:text-primary transition-colors">
                  <span class="material-symbols-outlined text-sm">add</span>
                  添加专业
                </button>
                <div v-if="showMajorDropdown"
                  class="absolute left-0 top-full mt-1 w-56 bg-surface-container-lowest rounded-xl shadow-lg border border-surface-container-high py-1 z-10">
                  <button v-for="major in availableMajors" :key="major" @click="addMajorTag(major)"
                    class="w-full px-4 py-2 text-left text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                    {{ major }}
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 步骤03：职位职责与任职要求 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high bg-surface-container-low/30">
          <div class="flex items-center gap-3">
            <div class="w-8 h-8 rounded-full bg-primary flex items-center justify-center">
              <span class="text-sm font-bold text-on-primary">03</span>
            </div>
            <h2 class="text-headline-sm font-semibold text-on-surface">职位职责与任职要求</h2>
          </div>
          <span class="text-body-sm text-on-surface-variant">支持 Markdown 规范排版</span>
        </div>
        <div class="p-6 space-y-5">
          <!-- 岗位职责 -->
          <div>
            <div class="flex items-center justify-between mb-2">
              <label class="text-body-sm font-medium text-on-surface">岗位职责 (Key Responsibilities) <span class="text-error">*</span></label>
              <div class="flex items-center gap-1">
                <button class="p-1.5 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="加粗">
                  <span class="material-symbols-outlined text-lg">format_bold</span>
                </button>
                <button class="p-1.5 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="列表">
                  <span class="material-symbols-outlined text-lg">format_list_bulleted</span>
                </button>
                <button class="p-1.5 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="编号列表">
                  <span class="material-symbols-outlined text-lg">format_list_numbered</span>
                </button>
              </div>
            </div>
            <textarea v-model="form.responsibilities" rows="5"
              class="w-full px-4 py-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 resize-none font-mono text-sm leading-relaxed"
              placeholder="1. 参与百亿/千亿级多模态大模型的预训练、指令微调（SFT）以及基于人类反馈的强化学习（RLHF）;"></textarea>
          </div>

          <!-- 任职要求 -->
          <div>
            <div class="flex items-center justify-between mb-2">
              <label class="text-body-sm font-medium text-on-surface">任职要求 (Requirements) <span class="text-error">*</span></label>
              <div class="flex items-center gap-1">
                <button class="p-1.5 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="加粗">
                  <span class="material-symbols-outlined text-lg">format_bold</span>
                </button>
                <button class="p-1.5 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="列表">
                  <span class="material-symbols-outlined text-lg">format_list_bulleted</span>
                </button>
                <button class="p-1.5 rounded-lg hover:bg-surface-container transition-colors text-on-surface-variant" title="代码">
                  <span class="material-symbols-outlined text-lg">code</span>
                </button>
              </div>
            </div>
            <textarea v-model="form.requirements" rows="5"
              class="w-full px-4 py-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20 resize-none font-mono text-sm leading-relaxed"
              placeholder="1. 2025年毕业的海内外知名高校硕士及以上学历，计算机、AI、软件等相关专业;"></textarea>
          </div>
        </div>
      </div>

      <!-- 步骤04：校园福利与人才补贴 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high bg-surface-container-low/30">
          <div class="flex items-center gap-3">
            <div class="w-8 h-8 rounded-full bg-primary flex items-center justify-center">
              <span class="text-sm font-bold text-on-primary">04</span>
            </div>
            <h2 class="text-headline-sm font-semibold text-on-surface">校园福利与人才补贴</h2>
          </div>
          <span class="text-body-sm text-on-surface-variant">点击即可点亮或取消</span>
        </div>
        <div class="p-6 space-y-4">
          <!-- 已选福利标签 -->
          <div class="flex flex-wrap gap-2">
            <span v-for="(benefit, idx) in form.benefits" :key="idx"
              class="inline-flex items-center gap-1.5 px-3 py-1.5 bg-primary/10 text-primary text-sm font-medium rounded-lg">
              <span class="material-symbols-outlined text-sm">check</span>
              {{ benefit }}
              <button @click="removeBenefit(idx)" class="hover:bg-primary/20 rounded p-0.5 transition-colors">
                <span class="material-symbols-outlined text-sm">close</span>
              </button>
            </span>
          </div>

          <!-- 添加福利 -->
          <div class="flex items-center gap-3">
            <div class="flex-1 relative">
              <input v-model="newBenefit" type="text" placeholder="新增特色福利（如：MacBook顶配办公、报到答辩往返机票）"
                class="w-full h-11 px-4 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder:text-on-surface-variant/50 text-body-md focus:outline-none focus:ring-2 focus:ring-primary/20"
                @keyup.enter="addBenefit" />
            </div>
            <button @click="addBenefit"
              class="h-11 px-5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface text-sm font-medium hover:bg-surface-container transition-colors whitespace-nowrap">
              添加标签
            </button>
          </div>

          <!-- 快捷福利选项 -->
          <div>
            <label class="block text-body-sm text-on-surface-variant mb-2">常用福利（点击快速添加）:</label>
            <div class="flex flex-wrap gap-2">
              <button v-for="benefit in benefitOptions" :key="benefit" @click="quickAddBenefit(benefit)"
                class="px-3 py-1.5 border border-surface-container-high text-on-surface-variant text-sm rounded-lg hover:border-primary hover:text-primary transition-colors">
                + {{ benefit }}
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 底部操作栏 -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-4">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-2 text-body-sm text-on-surface-variant">
            <span class="material-symbols-outlined text-base text-primary">check_circle</span>
            <span v-if="lastSaveTime" class="text-xs text-on-surface-variant">上次发布：{{ lastSaveTime }}</span>
          </div>
          <div class="flex items-center gap-3">
            <button @click="router.push('/enterprise/jobs')"
              class="h-11 px-6 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface font-medium hover:bg-surface-container transition-colors text-sm">
              取消返回
            </button>
            <button @click="handlePublish" :disabled="saving"
              class="h-11 px-6 rounded-xl bg-primary text-on-primary font-semibold hover:bg-primary-container transition-colors shadow-sm flex items-center gap-2 text-sm disabled:opacity-50">
              <span class="material-symbols-outlined text-lg">send</span>
              {{ saving ? '发布中...' : (editingId ? '保存修改' : '立即发布职位') }}
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
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { publishJob, updateJob, getJobDetail } from '../api/job'
import { getCompanyInfo } from '../api/company'

const route = useRoute()
const router = useRouter()
const toast = ref('')
const saving = ref(false)
const editingId = computed(() => route.query.id ? Number(route.query.id) : null)
const lastSaveTime = ref('')
const showMajorDropdown = ref(false)
const majorTagDropdown = ref(null)
const newBenefit = ref('')

const workTypes = [
  { value: 'fulltime', label: '全职校招（应届正式）' },
  { value: 'intern', label: '实习（可转正）' },
  { value: 'seasonal', label: '寒暑期日常实习' }
]

const yearOptions = ['2024届往届（补录）', '2025届应届生', '2026届实习生']

const majorOptions = ['计算机科学与技术', '人工智能/智能科学', '软件工程', '自动化控制', '应用数学/统计学', '电子信息工程', '数据科学', '网络安全']

const benefitOptions = ['五险一金', '转正机会90%', '导师1对1带教', '租房补贴（2500/月）', '免费精品三餐', '落户指标申报通道', '弹性工时/不打卡', '定期学术休假', '毕业设计专属辅导']

const educationMap = { '硕士研究生及以上': '硕士', '本科及以上': '本科', '大专及以上': '大专', '不限学历': '不限' }

const form = reactive({
  title: '',
  category: '',
  headcount: 5,
  workType: 'fulltime',
  city: '',
  address: '',
  salaryMin: null,
  salaryMax: null,
  salaryType: '15薪 (标准大厂)',
  education: '本科及以上',
  years: [],
  majorTags: [],
  responsibilities: '',
  requirements: '',
  benefits: []
})

const availableMajors = computed(() => majorOptions.filter(m => !form.majorTags.includes(m)))

function removeMajorTag(idx) {
  form.majorTags.splice(idx, 1)
}

function addMajorTag(major) {
  if (!form.majorTags.includes(major)) {
    form.majorTags.push(major)
  }
  showMajorDropdown.value = false
}

function removeBenefit(idx) {
  form.benefits.splice(idx, 1)
}

function addBenefit() {
  if (newBenefit.value.trim() && !form.benefits.includes(newBenefit.value.trim())) {
    form.benefits.push(newBenefit.value.trim())
    newBenefit.value = ''
  }
}

function quickAddBenefit(benefit) {
  if (!form.benefits.includes(benefit)) {
    form.benefits.push(benefit)
  }
}

async function handlePublish() {
  if (!form.title.trim()) { showToast('请填写职位名称'); return }
  if (!form.category) { showToast('请选择岗位类型'); return }
  if (!form.city) { showToast('请选择工作城市'); return }
  if (!form.salaryMin || !form.salaryMax) { showToast('请填写薪资范围'); return }
  saving.value = true
  try {
    let companyId = null
    try {
      const info = await getCompanyInfo()
      companyId = info && info.id
    } catch (e) {
      console.error('获取企业信息失败:', e)
    }
    if (!companyId) {
      showToast('未找到企业信息，请先完成企业认证')
      saving.value = false
      return
    }
    const payload = {
      companyId,
      title: form.title.trim(),
      category: form.category,
      city: form.city,
      salaryMin: Math.round(form.salaryMin * 1000),
      salaryMax: Math.round(form.salaryMax * 1000),
      education: educationMap[form.education] || form.education,
      experience: form.years.join(' / '),
      jobType: form.workType === 'fulltime' ? 1 : 2,
      description: form.responsibilities,
      requirement: form.requirements,
      benefits: form.benefits.join(','),
      headcount: form.headcount || 1
    }
    if (editingId.value) {
      await updateJob(editingId.value, payload)
      showToast('职位已更新')
    } else {
      await publishJob(payload)
      showToast('职位发布成功，等待平台审核')
    }
    lastSaveTime.value = new Date().toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
    setTimeout(() => router.push('/enterprise/jobs'), 800)
  } catch (e) {
    showToast(e.message || '发布失败，请重试')
  } finally {
    saving.value = false
  }
}

async function loadForEdit() {
  if (!editingId.value) return
  try {
    const j = await getJobDetail(editingId.value)
    form.title = j.title || ''
    form.category = j.category || ''
    form.city = j.city || ''
    form.salaryMin = j.salaryMin ? Math.round(j.salaryMin / 1000) : null
    form.salaryMax = j.salaryMax ? Math.round(j.salaryMax / 1000) : null
    form.education = j.education || '本科及以上'
    form.headcount = j.headcount || 1
    form.workType = j.jobType === 2 ? 'intern' : 'fulltime'
    form.responsibilities = j.description || ''
    form.requirements = j.requirement || ''
    form.benefits = j.benefits ? j.benefits.split(/[,，、]/).map(s => s.trim()).filter(Boolean) : []
    if (j.experience) form.years = j.experience.split(' / ').filter(y => yearOptions.includes(y))
  } catch (e) {
    showToast('加载职位失败：' + (e.message || '请重试'))
  }
}

onMounted(loadForEdit)

function showToast(msg) {
  toast.value = msg
  setTimeout(() => toast.value = '', 2000)
}
</script>
