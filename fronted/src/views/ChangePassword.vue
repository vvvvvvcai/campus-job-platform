<template>
  <div class="min-h-screen bg-background">
    <div class="max-w-2xl mx-auto px-6 py-8">
      <!-- Header Card -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden mb-6">
        <!-- Blue Top Bar -->
        <div class="h-1.5 bg-gradient-to-r from-primary to-primary/60"></div>

        <div class="p-6">
          <!-- Title Section -->
          <div class="flex items-start gap-4 mb-6">
            <div class="w-12 h-12 rounded-xl bg-primary flex items-center justify-center shrink-0">
              <span class="material-symbols-outlined text-on-primary text-2xl">lock</span>
            </div>
            <div>
              <div class="flex items-center gap-2">
                <h1 class="text-xl font-bold text-on-surface">修改账号密码</h1>
                <span class="px-2 py-0.5 bg-primary/10 text-primary text-[10px] font-semibold rounded border border-primary/20">SSL 256位加密传输</span>
              </div>
              <p class="text-sm text-on-surface-variant mt-1">定期更新密码有助于保护您的个人简历隐私、校招签约意向与高校三方协议安全</p>
            </div>
          </div>

          <!-- Status Bar -->
          <div class="flex items-center justify-between p-4 bg-primary/5 rounded-xl border border-primary/10 mb-6">
            <div class="flex items-center gap-2">
              <span class="material-symbols-outlined text-primary text-lg">schedule</span>
              <span class="text-sm text-on-surface">当前密码已连续使用 <strong class="text-primary">60</strong> 天</span>
              <span class="text-sm text-on-surface-variant">建议每 90 天定期更新</span>
            </div>
            <div class="flex items-center gap-2">
              <span class="text-xs text-on-surface-variant">综合安全评级：</span>
              <span class="px-2.5 py-1 bg-primary text-on-primary text-xs font-semibold rounded-lg flex items-center gap-1">
                <span class="w-1.5 h-1.5 rounded-full bg-on-primary"></span>
                良好
              </span>
            </div>
          </div>

          <!-- Toast -->
          <Transition name="toast">
            <div v-if="toast.show" :class="[
              'fixed top-6 right-6 z-50 flex items-center gap-3 px-5 py-3 rounded-xl shadow-lg text-sm font-medium',
              toast.type === 'success' ? 'bg-primary/10 text-primary border border-primary/20' : 'bg-red-50 text-red-700 border border-red-200'
            ]">
              <span class="material-symbols-outlined text-xl">{{ toast.type === 'success' ? 'check_circle' : 'error' }}</span>
              {{ toast.message }}
            </div>
          </Transition>

          <form @submit.prevent="handleSubmit" class="space-y-5">
            <!-- Current Password -->
            <div>
              <div class="flex items-center justify-between mb-1.5">
                <label class="text-sm font-semibold text-on-surface">当前登录密码 <span class="text-primary">*</span></label>
                <button type="button" class="text-xs text-primary hover:underline flex items-center gap-1">
                  忘记当前密码？
                  <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
                </button>
              </div>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-xl">lock</span>
                <input v-model="form.current" :type="showCurrent ? 'text' : 'password'" placeholder="请输入当前登录密码"
                  :class="['w-full pl-11 pr-12 py-3 rounded-xl border text-sm bg-surface-container-low text-on-surface placeholder:text-on-surface-variant/50', errors.current ? 'border-red-400' : 'border-surface-container-high focus:border-primary focus:ring-2 focus:ring-primary/10']" />
                <button type="button" @click="showCurrent = !showCurrent" class="absolute right-3 top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface">
                  <span class="material-symbols-outlined text-xl">{{ showCurrent ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
              <p v-if="errors.current" class="text-red-500 text-xs mt-1.5">{{ errors.current }}</p>
              <p v-else class="text-xs text-on-surface-variant mt-1.5">若由辅导员分发的初始密码，请及时更新以开启全国高校就业派遣网签授权</p>
            </div>

            <!-- New Password -->
            <div>
              <label class="block text-sm font-semibold text-on-surface mb-1.5">设置新密码 <span class="text-primary">*</span></label>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-xl">link</span>
                <input v-model="form.newPassword" :type="showNew ? 'text' : 'password'" placeholder="请输入8-20位新密码（包含大小写、数字与特殊符号）"
                  :class="['w-full pl-11 pr-12 py-3 rounded-xl border text-sm bg-surface-container-low text-on-surface placeholder:text-on-surface-variant/50', errors.newPassword ? 'border-red-400' : 'border-surface-container-high focus:border-primary focus:ring-2 focus:ring-primary/10']" />
                <button type="button" @click="showNew = !showNew" class="absolute right-3 top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface">
                  <span class="material-symbols-outlined text-xl">{{ showNew ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
              <p v-if="errors.newPassword" class="text-red-500 text-xs mt-1.5">{{ errors.newPassword }}</p>

              <!-- Password Strength -->
              <div class="mt-4 p-4 bg-surface-container-low rounded-xl border border-surface-container-high">
                <div class="flex items-center justify-between mb-3">
                  <span class="text-xs text-on-surface-variant">密码安全强度监测</span>
                  <span class="text-xs text-on-surface-variant">{{ form.newPassword ? strengthConfig[strengthLevel - 1]?.label || '未输入' : '未输入' }}</span>
                </div>
                <div class="flex gap-1.5 mb-4">
                  <div v-for="i in 4" :key="i"
                    :class="['h-1.5 flex-1 rounded-full transition-all duration-300',
                      strengthLevel >= i ? strengthConfig[strengthLevel - 1].color : 'bg-surface-container-high']">
                  </div>
                </div>
                <div class="grid grid-cols-2 gap-2">
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', form.newPassword.length >= 8 ? 'border-primary bg-primary' : 'border-on-surface-variant/30']">
                      <span v-if="form.newPassword.length >= 8" class="material-symbols-outlined text-on-primary text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-on-surface-variant">长度在 8-20 个字符之间</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', /[A-Z]/.test(form.newPassword) && /[a-z]/.test(form.newPassword) ? 'border-primary bg-primary' : 'border-on-surface-variant/30']">
                      <span v-if="/[A-Z]/.test(form.newPassword) && /[a-z]/.test(form.newPassword)" class="material-symbols-outlined text-on-primary text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-on-surface-variant">包含大写或小写英文字母</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', /\d/.test(form.newPassword) ? 'border-primary bg-primary' : 'border-on-surface-variant/30']">
                      <span v-if="/\d/.test(form.newPassword)" class="material-symbols-outlined text-on-primary text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-on-surface-variant">包含数字（0-9）</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', /[^A-Za-z0-9]/.test(form.newPassword) ? 'border-primary bg-primary' : 'border-on-surface-variant/30']">
                      <span v-if="/[^A-Za-z0-9]/.test(form.newPassword)" class="material-symbols-outlined text-on-primary text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-on-surface-variant">包含特殊标点符号（!@#$%^&*等）</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Confirm Password -->
            <div>
              <label class="block text-sm font-semibold text-on-surface mb-1.5">确认新密码 <span class="text-primary">*</span></label>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-xl">refresh</span>
                <input v-model="form.confirm" :type="showConfirm ? 'text' : 'password'" placeholder="请再次输入新密码以进行一致性确认"
                  :class="['w-full pl-11 pr-12 py-3 rounded-xl border text-sm bg-surface-container-low text-on-surface placeholder:text-on-surface-variant/50', errors.confirm ? 'border-red-400' : 'border-surface-container-high focus:border-primary focus:ring-2 focus:ring-primary/10']" />
                <button type="button" @click="showConfirm = !showConfirm" class="absolute right-3 top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface">
                  <span class="material-symbols-outlined text-xl">{{ showConfirm ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
              <p v-if="errors.confirm" class="text-red-500 text-xs mt-1.5">{{ errors.confirm }}</p>
              <p v-else-if="form.confirm && form.newPassword === form.confirm" class="text-primary text-xs mt-1.5 flex items-center gap-1">
                <span class="material-symbols-outlined text-sm">check_circle</span> 密码匹配
              </p>
            </div>

            <!-- Revoke Sessions -->
            <label class="flex items-start gap-3 p-4 bg-primary/5 rounded-xl cursor-pointer border border-primary/10">
              <input v-model="form.revokeSessions" type="checkbox" class="w-4 h-4 mt-0.5 rounded border-on-surface-variant/30 text-primary focus:ring-primary" />
              <div>
                <span class="text-sm font-semibold text-on-surface block">修改成功后退出其他设备上的登录状态</span>
                <span class="text-xs text-on-surface-variant mt-0.5 block">推荐勾选：避免因在高校机房、图书馆或公用招聘展位电脑登录后遗留未注销风险</span>
              </div>
            </label>

            <!-- Buttons -->
            <div class="flex gap-3 pt-2">
              <button type="submit" :disabled="loading"
                class="flex-1 py-3 bg-primary text-on-primary rounded-xl font-medium text-sm hover:bg-primary/90 transition-colors disabled:opacity-50 flex items-center justify-center gap-2">
                <span class="material-symbols-outlined text-lg">shield</span>
                <span v-if="loading">提交中...</span>
                <span v-else>确认修改密码</span>
              </button>
              <button type="button" @click="$router.back()"
                class="px-6 py-3 border border-surface-container-high text-on-surface rounded-xl font-medium text-sm hover:bg-surface-container-low transition-colors flex items-center gap-2">
                <span class="material-symbols-outlined text-lg">arrow_back</span>
                取消并返回
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- Security Tips Section -->
      <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high overflow-hidden">
        <div class="h-1 bg-gradient-to-r from-primary to-primary/60"></div>
        <div class="p-6">
          <h3 class="font-bold text-on-surface flex items-center gap-2 mb-4">
            <span class="material-symbols-outlined text-primary">gpp_good</span>
            校园招聘与签约安全常识
          </h3>
          <div class="grid grid-cols-3 gap-4 mb-4">
            <div v-for="(tip, index) in securityTips" :key="index" class="p-4 bg-surface-container-low rounded-xl border border-surface-container-high">
              <div class="flex items-center gap-2 mb-2">
                <span :class="['material-symbols-outlined text-lg', tip.iconColor]">{{ tip.icon }}</span>
                <span class="text-sm font-semibold text-on-surface">{{ tip.title }}</span>
              </div>
              <p class="text-xs text-on-surface-variant leading-relaxed">{{ tip.desc }}</p>
            </div>
          </div>
          <div class="flex items-center justify-between pt-2 border-t border-surface-container-high">
            <p class="text-xs text-on-surface-variant flex items-center gap-1">
              <span class="material-symbols-outlined text-[14px]">info</span>
              如遇非本人操作或异常登录提醒，请立即联系校园就业中心保卫通道：400-820-2025
            </p>
            <button class="text-xs text-primary hover:underline flex items-center gap-1">
              查看最近登录日志
              <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'

const showCurrent = ref(false)
const showNew = ref(false)
const showConfirm = ref(false)
const loading = ref(false)

const toast = reactive({ show: false, type: 'success', message: '' })
function showToast(type, message) {
  toast.show = true; toast.type = type; toast.message = message
  setTimeout(() => { toast.show = false }, 3000)
}

const form = reactive({ current: '', newPassword: '', confirm: '', revokeSessions: false })
const errors = reactive({ current: '', newPassword: '', confirm: '' })

const strengthConfig = [
  { color: 'bg-red-500', label: '弱' },
  { color: 'bg-orange-500', label: '一般' },
  { color: 'bg-yellow-500', label: '较强' },
  { color: 'bg-primary', label: '强' }
]

const strengthLevel = computed(() => {
  const pwd = form.newPassword
  if (!pwd) return 0
  let s = 0
  if (pwd.length >= 8) s++
  if (/[A-Z]/.test(pwd) && /[a-z]/.test(pwd)) s++
  if (/\d/.test(pwd)) s++
  if (/[^A-Za-z0-9]/.test(pwd)) s++
  return Math.max(1, Math.min(4, s))
})

const securityTips = [
  { icon: 'gpp_maybe', iconColor: 'text-primary', title: '防范钓鱼及冒名', desc: '平台官方及辅导员绝不会以"职位内推"、"资格审查"为由向您索取平台登录密码或短信验证码。' },
  { icon: 'password', iconColor: 'text-primary', title: '规避弱口令陷阱', desc: '请避免使用学号、生日、宿舍号或123456等易被推导组合，确保独立密码凭据。' },
  { icon: 'verified_user', iconColor: 'text-primary', title: '保障网签法律效力', desc: '修改密码后，与用人单位签署的三方就业协议和电子用工存证将自动关联最新的安全密钥保护。' }
]

function handleSubmit() {
  errors.current = !form.current ? '请输入当前密码' : ''
  errors.newPassword = !form.newPassword ? '请输入新密码' : form.newPassword.length < 8 ? '密码至少8位' : form.newPassword === form.current ? '新密码不能与当前密码相同' : ''
  errors.confirm = form.newPassword !== form.confirm ? '两次密码输入不一致' : ''
  if (errors.current || errors.newPassword || errors.confirm) return
  loading.value = true
  setTimeout(() => { loading.value = false; showToast('success', '密码修改成功！') }, 1500)
}
</script>

<style scoped>
.toast-enter-active, .toast-leave-active { transition: all 0.3s ease; }
.toast-enter-from, .toast-leave-to { opacity: 0; transform: translateY(-12px); }
</style>
