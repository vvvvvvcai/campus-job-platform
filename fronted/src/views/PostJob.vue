<template>
  <div class="min-h-screen bg-background">
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-7xl mx-auto px-6 flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-on-surface">发布职位</h1>
          <p class="text-on-surface-variant mt-1">填写职位信息，快速触达目标人才</p>
        </div>
        <div class="flex gap-3">
          <button @click="saveDraft" class="px-5 py-2 border border-surface-container-high text-on-surface font-medium rounded-xl hover:bg-surface-container transition-all text-sm">
            保存草稿
          </button>
          <button @click="publishJob" class="px-5 py-2 bg-primary text-on-primary font-semibold rounded-xl hover:bg-primary-container transition-all text-sm">
            发布职位
          </button>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-6 py-8">
      <div class="grid lg:grid-cols-5 gap-6">
        <!-- Form -->
        <div class="lg:col-span-3 space-y-6">
          <!-- Basic Info -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h2 class="text-lg font-bold text-on-surface mb-5 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">info</span>
              基本信息
            </h2>
            <div class="grid md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">职位名称 *</label>
                <input v-model="form.title" type="text" placeholder="如：前端开发工程师" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm" />
                <p v-if="errors.title" class="text-error text-xs mt-1">{{ errors.title }}</p>
              </div>
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">所属部门 *</label>
                <input v-model="form.department" type="text" placeholder="如：技术研发部" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm" />
                <p v-if="errors.department" class="text-error text-xs mt-1">{{ errors.department }}</p>
              </div>
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">职位类型 *</label>
                <select v-model="form.type" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm">
                  <option value="">请选择</option>
                  <option>全职</option>
                  <option>实习</option>
                  <option>兼职</option>
                </select>
                <p v-if="errors.type" class="text-error text-xs mt-1">{{ errors.type }}</p>
              </div>
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">招聘人数 *</label>
                <input v-model="form.headcount" type="number" min="1" placeholder="如：5" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm" />
              </div>
            </div>
          </div>

          <!-- Job Description -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h2 class="text-lg font-bold text-on-surface mb-5 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">description</span>
              职位描述
            </h2>
            <div>
              <textarea
                v-model="form.description"
                rows="6"
                placeholder="请详细描述职位的工作内容、职责要求..."
                class="w-full px-4 py-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary resize-none text-sm"
              ></textarea>
              <p v-if="errors.description" class="text-error text-xs mt-1">{{ errors.description }}</p>
            </div>
          </div>

          <!-- Requirements -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h2 class="text-lg font-bold text-on-surface mb-5 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">checklist</span>
              任职要求
            </h2>
            <div>
              <textarea
                v-model="form.requirements"
                rows="5"
                placeholder="请列出职位的技能要求、经验要求等..."
                class="w-full px-4 py-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary resize-none text-sm"
              ></textarea>
            </div>
          </div>

          <!-- Salary & Location -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h2 class="text-lg font-bold text-on-surface mb-5 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">paid</span>
              薪资与地点
            </h2>
            <div class="grid md:grid-cols-3 gap-4">
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">最低薪资(K) *</label>
                <input v-model="form.salaryMin" type="number" min="0" placeholder="如：15" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm" />
              </div>
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">最高薪资(K) *</label>
                <input v-model="form.salaryMax" type="number" min="0" placeholder="如：30" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm" />
              </div>
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">工作地点 *</label>
                <input v-model="form.location" type="text" placeholder="如：北京市" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm" />
                <p v-if="errors.location" class="text-error text-xs mt-1">{{ errors.location }}</p>
              </div>
            </div>
          </div>

          <!-- Benefits & Requirements -->
          <div class="bg-surface-container-lowest rounded-2xl p-6 border border-surface-container-high shadow-sm">
            <h2 class="text-lg font-bold text-on-surface mb-5 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">stars</span>
              福利与要求
            </h2>
            <div class="mb-4">
              <label class="block text-sm font-medium text-on-surface mb-2">福利标签</label>
              <div class="flex flex-wrap gap-2">
                <button
                  v-for="benefit in benefitOptions"
                  :key="benefit"
                  @click="toggleBenefit(benefit)"
                  class="px-3 py-1.5 rounded-lg text-sm font-medium transition-all"
                  :class="form.benefits.includes(benefit) ? 'bg-primary text-on-primary' : 'bg-surface-container text-on-surface-variant hover:bg-surface-container-high'"
                >
                  {{ benefit }}
                </button>
              </div>
            </div>
            <div class="grid md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">学历要求</label>
                <select v-model="form.education" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm">
                  <option value="">不限</option>
                  <option>大专</option>
                  <option>本科</option>
                  <option>硕士</option>
                  <option>博士</option>
                </select>
              </div>
              <div>
                <label class="block text-sm font-medium text-on-surface mb-1.5">经验要求</label>
                <select v-model="form.experience" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary text-sm">
                  <option value="">不限</option>
                  <option>应届生</option>
                  <option>1-3年</option>
                  <option>3-5年</option>
                  <option>5-10年</option>
                  <option>10年以上</option>
                </select>
              </div>
            </div>
          </div>
        </div>

        <!-- Preview Panel -->
        <div class="lg:col-span-2">
          <div class="sticky top-6">
            <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high shadow-sm overflow-hidden">
              <div class="bg-gradient-to-r from-primary to-secondary p-5 text-on-primary">
                <h3 class="text-lg font-bold">职位预览</h3>
              </div>
              <div class="p-5">
                <div class="mb-4">
                  <h4 class="text-xl font-bold text-on-surface">{{ form.title || '职位名称' }}</h4>
                  <p class="text-on-surface-variant text-sm mt-1">{{ form.department || '所属部门' }} · {{ form.type || '职位类型' }}</p>
                </div>
                <div class="flex items-center gap-3 mb-4">
                  <span class="text-primary font-bold text-lg">{{ form.salaryMin && form.salaryMax ? form.salaryMin + '-' + form.salaryMax + 'K' : '薪资面议' }}</span>
                  <span class="text-on-surface-variant text-sm">· {{ form.location || '工作地点' }}</span>
                </div>

                <div v-if="form.benefits.length" class="flex flex-wrap gap-2 mb-4">
                  <span v-for="b in form.benefits" :key="b" class="px-2.5 py-1 bg-surface-container rounded-lg text-xs text-on-surface-variant">{{ b }}</span>
                </div>

                <div class="border-t border-surface-container-high pt-4 mb-4">
                  <h5 class="font-bold text-on-surface text-sm mb-2">职位描述</h5>
                  <p class="text-on-surface-variant text-sm whitespace-pre-line">{{ form.description || '暂无描述' }}</p>
                </div>

                <div class="border-t border-surface-container-high pt-4 mb-4">
                  <h5 class="font-bold text-on-surface text-sm mb-2">任职要求</h5>
                  <p class="text-on-surface-variant text-sm whitespace-pre-line">{{ form.requirements || '暂无要求' }}</p>
                </div>

                <div class="border-t border-surface-container-high pt-4 flex gap-3 text-sm text-on-surface-variant">
                  <span v-if="form.education">{{ form.education }}</span>
                  <span v-if="form.experience">{{ form.experience }}</span>
                  <span>· 招聘 {{ form.headcount || 1 }} 人</span>
                </div>
              </div>
            </div>
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

const benefitOptions = ['五险一金', '年终奖', '带薪年假', '弹性工作', '免费三餐', '股票期权', '培训机会', '团建活动', '交通补贴', '住房补贴']

const form = reactive({
  title: '',
  department: '',
  type: '',
  headcount: 1,
  description: '',
  requirements: '',
  salaryMin: '',
  salaryMax: '',
  location: '',
  benefits: [],
  education: '',
  experience: ''
})

const errors = reactive({})

function toggleBenefit(b) {
  const idx = form.benefits.indexOf(b)
  if (idx >= 0) form.benefits.splice(idx, 1)
  else form.benefits.push(b)
}

function validate() {
  Object.keys(errors).forEach(k => delete errors[k])
  let valid = true
  if (!form.title.trim()) { errors.title = '请输入职位名称'; valid = false }
  if (!form.department.trim()) { errors.department = '请输入部门'; valid = false }
  if (!form.type) { errors.type = '请选择类型'; valid = false }
  if (!form.description.trim()) { errors.description = '请输入描述'; valid = false }
  if (!form.location.trim()) { errors.location = '请输入地点'; valid = false }
  return valid
}

function saveDraft() {
  if (!validate()) return
  showToast('草稿已保存')
}

function publishJob() {
  if (!validate()) return
  showToast('职位已发布')
}

function showToast(msg) {
  toast.value = msg
  setTimeout(() => toast.value = '', 2000)
}
</script>
