<template>
  <div class="min-h-screen bg-background">
    <!-- Header -->
    <div class="bg-surface-container-low py-6 border-b border-surface-container-high">
      <div class="max-w-5xl mx-auto px-6">
        <h1 class="text-2xl font-bold text-on-surface">企业认证</h1>
        <p class="text-on-surface-variant mt-1">完成认证后即可发布职位、接收简历</p>
      </div>
    </div>

    <div class="max-w-5xl mx-auto px-6 py-8">
      <!-- Stepper -->
      <div class="mb-10">
        <div class="flex items-center justify-between relative">
          <div class="absolute top-5 left-0 right-0 h-0.5 bg-surface-container-high z-0">
            <div class="h-full bg-primary transition-all duration-500" :style="{ width: ((currentStep - 1) / 2) * 100 + '%' }"></div>
          </div>
          <div v-for="(step, index) in steps" :key="index" class="relative z-10 flex flex-col items-center">
            <div
              class="w-10 h-10 rounded-full flex items-center justify-center text-sm font-bold transition-all"
              :class="currentStep > index + 1 ? 'bg-primary text-on-primary' : currentStep === index + 1 ? 'bg-primary text-on-primary ring-4 ring-primary/20' : 'bg-surface-container-high text-on-surface-variant'"
            >
              <span v-if="currentStep > index + 1" class="material-symbols-outlined text-lg">check</span>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <span class="mt-2 text-xs font-medium" :class="currentStep >= index + 1 ? 'text-primary' : 'text-on-surface-variant'">{{ step }}</span>
          </div>
        </div>
      </div>

      <!-- Progress Bar -->
      <div class="mb-8">
        <div class="flex justify-between text-sm text-on-surface-variant mb-2">
          <span>认证进度</span>
          <span>{{ Math.round((currentStep / 3) * 100) }}%</span>
        </div>
        <div class="w-full h-2 bg-surface-container-high rounded-full overflow-hidden">
          <div class="h-full bg-primary rounded-full transition-all duration-500" :style="{ width: (currentStep / 3) * 100 + '%' }"></div>
        </div>
      </div>

      <!-- Step 1: Basic Info -->
      <div v-if="currentStep === 1" class="space-y-8">
        <!-- Company Basic Info -->
        <div class="bg-surface-container-lowest rounded-2xl p-6 shadow-sm border border-surface-container-high">
          <h2 class="text-lg font-bold text-on-surface mb-6 flex items-center gap-2">
            <span class="material-symbols-outlined text-primary">apartment</span>
            公司基本信息
          </h2>
          <div class="grid md:grid-cols-2 gap-5">
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">公司名称 *</label>
              <input v-model="form.companyName" type="text" placeholder="请输入公司全称" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary" />
              <p v-if="errors.companyName" class="text-error text-xs mt-1">{{ errors.companyName }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">统一社会信用代码 *</label>
              <input v-model="form.creditCode" type="text" placeholder="18位信用代码" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary" />
              <p v-if="errors.creditCode" class="text-error text-xs mt-1">{{ errors.creditCode }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">所属行业 *</label>
              <select v-model="form.industry" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary">
                <option value="">请选择行业</option>
                <option v-for="ind in industries" :key="ind" :value="ind">{{ ind }}</option>
              </select>
              <p v-if="errors.industry" class="text-error text-xs mt-1">{{ errors.industry }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">企业规模 *</label>
              <select v-model="form.scale" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary">
                <option value="">请选择规模</option>
                <option v-for="s in scales" :key="s" :value="s">{{ s }}</option>
              </select>
              <p v-if="errors.scale" class="text-error text-xs mt-1">{{ errors.scale }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">所在城市 *</label>
              <input v-model="form.city" type="text" placeholder="如：北京市" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary" />
              <p v-if="errors.city" class="text-error text-xs mt-1">{{ errors.city }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">详细地址 *</label>
              <input v-model="form.address" type="text" placeholder="请输入公司地址" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary" />
              <p v-if="errors.address" class="text-error text-xs mt-1">{{ errors.address }}</p>
            </div>
          </div>
        </div>

        <!-- Recruiter Info -->
        <div class="bg-surface-container-lowest rounded-2xl p-6 shadow-sm border border-surface-container-high">
          <h2 class="text-lg font-bold text-on-surface mb-6 flex items-center gap-2">
            <span class="material-symbols-outlined text-primary">person</span>
            招聘联系人信息
          </h2>
          <div class="grid md:grid-cols-2 gap-5">
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">联系人姓名 *</label>
              <input v-model="form.recruiterName" type="text" placeholder="请输入姓名" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary" />
              <p v-if="errors.recruiterName" class="text-error text-xs mt-1">{{ errors.recruiterName }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-on-surface mb-1.5">手机号码 *</label>
              <input v-model="form.phone" type="tel" placeholder="请输入手机号" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary" />
              <p v-if="errors.phone" class="text-error text-xs mt-1">{{ errors.phone }}</p>
            </div>
            <div class="md:col-span-2">
              <label class="block text-sm font-medium text-on-surface mb-1.5">电子邮箱 *</label>
              <input v-model="form.email" type="email" placeholder="请输入邮箱" class="w-full px-4 py-2.5 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary" />
              <p v-if="errors.email" class="text-error text-xs mt-1">{{ errors.email }}</p>
            </div>
          </div>
        </div>

        <div class="flex justify-end">
          <button @click="validateStep1" class="px-8 py-2.5 bg-primary text-on-primary font-semibold rounded-xl hover:bg-primary-container transition-all">
            下一步
          </button>
        </div>
      </div>

      <!-- Step 2: Document Upload -->
      <div v-if="currentStep === 2" class="space-y-8">
        <div class="bg-surface-container-lowest rounded-2xl p-6 shadow-sm border border-surface-container-high">
          <h2 class="text-lg font-bold text-on-surface mb-6 flex items-center gap-2">
            <span class="material-symbols-outlined text-primary">upload_file</span>
            资质材料上传
          </h2>
          <div class="grid md:grid-cols-2 gap-6">
            <!-- Business License -->
            <div>
              <label class="block text-sm font-medium text-on-surface mb-2">营业执照 *</label>
              <div
                class="border-2 border-dashed rounded-xl p-8 text-center transition-all cursor-pointer"
                :class="form.businessLicense ? 'border-primary bg-primary/5' : 'border-surface-container-high hover:border-primary/50'"
                @click="triggerUpload('license')"
              >
                <template v-if="form.businessLicense">
                  <span class="material-symbols-outlined text-4xl text-primary mb-2">description</span>
                  <p class="text-on-surface text-sm font-medium">{{ form.businessLicense }}</p>
                </template>
                <template v-else>
                  <span class="material-symbols-outlined text-4xl text-on-surface-variant mb-2">cloud_upload</span>
                  <p class="text-on-surface-variant text-sm">点击上传营业执照</p>
                  <p class="text-on-surface-variant/60 text-xs mt-1">支持 JPG、PNG、PDF，不超过 10MB</p>
                </template>
              </div>
              <p v-if="errors.businessLicense" class="text-error text-xs mt-1">{{ errors.businessLicense }}</p>
            </div>

            <!-- ID Card -->
            <div>
              <label class="block text-sm font-medium text-on-surface mb-2">法人身份证 *</label>
              <div
                class="border-2 border-dashed rounded-xl p-8 text-center transition-all cursor-pointer"
                :class="form.idCard ? 'border-primary bg-primary/5' : 'border-surface-container-high hover:border-primary/50'"
                @click="triggerUpload('idcard')"
              >
                <template v-if="form.idCard">
                  <span class="material-symbols-outlined text-4xl text-primary mb-2">badge</span>
                  <p class="text-on-surface text-sm font-medium">{{ form.idCard }}</p>
                </template>
                <template v-else>
                  <span class="material-symbols-outlined text-4xl text-on-surface-variant mb-2">cloud_upload</span>
                  <p class="text-on-surface-variant text-sm">点击上传法人身份证</p>
                  <p class="text-on-surface-variant/60 text-xs mt-1">支持 JPG、PNG、PDF，不超过 10MB</p>
                </template>
              </div>
              <p v-if="errors.idCard" class="text-error text-xs mt-1">{{ errors.idCard }}</p>
            </div>
          </div>
        </div>

        <!-- Company Intro -->
        <div class="bg-surface-container-lowest rounded-2xl p-6 shadow-sm border border-surface-container-high">
          <h2 class="text-lg font-bold text-on-surface mb-6 flex items-center gap-2">
            <span class="material-symbols-outlined text-primary">info</span>
            公司简介
          </h2>
          <div>
            <textarea
              v-model="form.intro"
              rows="5"
              maxlength="500"
              placeholder="请简要介绍公司情况，包括主营业务、企业文化等..."
              class="w-full px-4 py-3 rounded-xl border border-surface-container-high bg-surface-container-lowest text-on-surface placeholder-on-surface-variant focus:outline-none focus:ring-2 focus:ring-primary/30 focus:border-primary resize-none"
            ></textarea>
            <div class="flex justify-between mt-1">
              <p v-if="errors.intro" class="text-error text-xs">{{ errors.intro }}</p>
              <span class="text-on-surface-variant text-xs ml-auto">{{ form.intro.length }}/500</span>
            </div>
          </div>
        </div>

        <div class="flex justify-between">
          <button @click="currentStep = 1" class="px-8 py-2.5 border border-surface-container-high text-on-surface font-semibold rounded-xl hover:bg-surface-container transition-all">
            上一步
          </button>
          <button @click="validateStep2" class="px-8 py-2.5 bg-primary text-on-primary font-semibold rounded-xl hover:bg-primary-container transition-all">
            下一步
          </button>
        </div>
      </div>

      <!-- Step 3: Compliance & Submit -->
      <div v-if="currentStep === 3" class="space-y-8">
        <div class="bg-surface-container-lowest rounded-2xl p-6 shadow-sm border border-surface-container-high">
          <h2 class="text-lg font-bold text-on-surface mb-6 flex items-center gap-2">
            <span class="material-symbols-outlined text-primary">verified_user</span>
            合规承诺
          </h2>
          <div class="bg-surface-container rounded-xl p-5 text-sm text-on-surface-variant leading-relaxed mb-6">
            <p class="mb-3">本公司郑重承诺：</p>
            <ol class="list-decimal list-inside space-y-2">
              <li>所提供的全部信息和材料均真实、准确、完整；</li>
              <li>严格遵守国家法律法规及平台招聘管理规定；</li>
              <li>不发布虚假职位信息，不收取求职者任何费用；</li>
              <li>尊重求职者个人信息，不泄露或非法使用个人数据；</li>
              <li>如有违反，愿承担相应法律责任及平台处罚。</li>
            </ol>
          </div>
          <label class="flex items-start gap-3 cursor-pointer">
            <input v-model="form.agreed" type="checkbox" class="mt-0.5 w-4 h-4 rounded border-surface-container-high text-primary focus:ring-primary/30" />
            <span class="text-on-surface text-sm">我已阅读并同意上述合规承诺内容 *</span>
          </label>
          <p v-if="errors.agreed" class="text-error text-xs mt-1 ml-7">{{ errors.agreed }}</p>
        </div>

        <!-- Summary -->
        <div class="bg-surface-container-lowest rounded-2xl p-6 shadow-sm border border-surface-container-high">
          <h2 class="text-lg font-bold text-on-surface mb-4">提交信息摘要</h2>
          <div class="grid md:grid-cols-2 gap-4 text-sm">
            <div><span class="text-on-surface-variant">公司名称：</span><span class="text-on-surface font-medium">{{ form.companyName || '-' }}</span></div>
            <div><span class="text-on-surface-variant">信用代码：</span><span class="text-on-surface font-medium">{{ form.creditCode || '-' }}</span></div>
            <div><span class="text-on-surface-variant">所属行业：</span><span class="text-on-surface font-medium">{{ form.industry || '-' }}</span></div>
            <div><span class="text-on-surface-variant">企业规模：</span><span class="text-on-surface font-medium">{{ form.scale || '-' }}</span></div>
            <div><span class="text-on-surface-variant">所在城市：</span><span class="text-on-surface font-medium">{{ form.city || '-' }}</span></div>
            <div><span class="text-on-surface-variant">联系人：</span><span class="text-on-surface font-medium">{{ form.recruiterName || '-' }}</span></div>
          </div>
        </div>

        <div class="flex justify-between">
          <button @click="currentStep = 2" class="px-8 py-2.5 border border-surface-container-high text-on-surface font-semibold rounded-xl hover:bg-surface-container transition-all">
            上一步
          </button>
          <button @click="handleSubmit" class="px-10 py-2.5 bg-primary text-on-primary font-bold rounded-xl hover:bg-primary-container transition-all shadow-md">
            <span class="material-symbols-outlined text-base align-middle mr-1">send</span>
            提交认证
          </button>
        </div>
      </div>

      <!-- Success State -->
      <div v-if="submitted" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
        <div class="bg-surface-container-lowest rounded-3xl p-10 max-w-md mx-4 text-center">
          <div class="w-20 h-20 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-6">
            <span class="material-symbols-outlined text-5xl text-primary">check_circle</span>
          </div>
          <h3 class="text-xl font-bold text-on-surface mb-2">认证已提交</h3>
          <p class="text-on-surface-variant text-sm mb-8">我们将在 1-3 个工作日内完成审核，请耐心等待</p>
          <button @click="submitted = false; currentStep = 1" class="px-8 py-2.5 bg-primary text-on-primary font-semibold rounded-xl">
            返回首页
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'

const currentStep = ref(1)
const submitted = ref(false)

const steps = ['基础账号建立', '资质材料提交', '校方联合审定']

const industries = ['互联网/IT', '金融', '教育', '医疗', '制造', '房地产', '零售', '其他']
const scales = ['0-50人', '50-200人', '200-500人', '500-1000人', '1000-5000人', '5000人以上']

const form = reactive({
  companyName: '',
  creditCode: '',
  industry: '',
  scale: '',
  city: '',
  address: '',
  recruiterName: '',
  phone: '',
  email: '',
  businessLicense: '',
  idCard: '',
  intro: '',
  agreed: false
})

const errors = reactive({})

function clearErrors() {
  Object.keys(errors).forEach(k => delete errors[k])
}

function validateStep1() {
  clearErrors()
  let valid = true
  if (!form.companyName.trim()) { errors.companyName = '请输入公司名称'; valid = false }
  if (!form.creditCode.trim()) { errors.creditCode = '请输入信用代码'; valid = false }
  if (!form.industry) { errors.industry = '请选择行业'; valid = false }
  if (!form.scale) { errors.scale = '请选择规模'; valid = false }
  if (!form.city.trim()) { errors.city = '请输入城市'; valid = false }
  if (!form.address.trim()) { errors.address = '请输入地址'; valid = false }
  if (!form.recruiterName.trim()) { errors.recruiterName = '请输入联系人姓名'; valid = false }
  if (!form.phone.trim()) { errors.phone = '请输入手机号'; valid = false }
  if (!form.email.trim()) { errors.email = '请输入邮箱'; valid = false }
  if (valid) currentStep.value = 2
}

function validateStep2() {
  clearErrors()
  let valid = true
  if (!form.businessLicense) { errors.businessLicense = '请上传营业执照'; valid = false }
  if (!form.idCard) { errors.idCard = '请上传法人身份证'; valid = false }
  if (!form.intro.trim()) { errors.intro = '请填写公司简介'; valid = false }
  if (valid) currentStep.value = 3
}

function triggerUpload(type) {
  if (type === 'license') form.businessLicense = '营业执照_2026.pdf'
  else form.idCard = '法人身份证.jpg'
}

function handleSubmit() {
  clearErrors()
  if (!form.agreed) { errors.agreed = '请同意合规承诺'; return }
  submitted.value = true
}
</script>
