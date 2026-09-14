<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-2xl mx-auto px-6 py-8">
      <!-- Header Card -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden mb-6">
        <!-- Blue Top Bar -->
        <div class="h-1.5 bg-gradient-to-r from-[var(--primary)] to-[#4f8cff]"></div>
        
        <div class="p-6">
          <!-- Title Section -->
          <div class="flex items-start gap-4 mb-6">
            <div class="w-12 h-12 rounded-xl bg-[var(--primary)] flex items-center justify-center flex-shrink-0">
              <span class="material-symbols-outlined text-white text-2xl">lock</span>
            </div>
            <div>
              <div class="flex items-center gap-2">
                <h1 class="text-xl font-bold text-[var(--on-surface)]">修改账号密码</h1>
                <span class="px-2 py-0.5 bg-[var(--primary)]/10 text-[var(--primary)] text-[10px] font-semibold rounded border border-[var(--primary)]/20">SSL 256位加密传输</span>
              </div>
              <p class="text-sm text-[var(--on-surface-variant)] mt-1">定期更新密码有助于保护您的个人简历隐私、校招签约意向与高校三方协议安全</p>
            </div>
          </div>

          <!-- Status Bar -->
          <div class="flex items-center justify-between p-4 bg-[#eef2ff]/50 rounded-xl border border-[var(--primary)]/10 mb-6">
            <div class="flex items-center gap-2">
              <span class="material-symbols-outlined text-[var(--primary)] text-lg">schedule</span>
              <span class="text-sm text-[var(--on-surface)]">当前密码已连续使用 <strong class="text-[var(--primary)]">60</strong> 天</span>
              <span class="text-sm text-[var(--on-surface-variant)]">建议每 90 天定期更新</span>
            </div>
            <div class="flex items-center gap-2">
              <span class="text-xs text-[var(--on-surface-variant)]">综合安全评级：</span>
              <span class="px-2.5 py-1 bg-[var(--primary)] text-white text-xs font-semibold rounded-lg">良好</span>
            </div>
          </div>

          <!-- Toast -->
          <Transition name="toast">
            <div v-if="toast.show" :class="[
              'fixed top-6 right-6 z-50 flex items-center gap-3 px-5 py-3 rounded-xl shadow-lg text-sm font-medium',
              toast.type === 'success' ? 'bg-[var(--primary)]/10 text-[var(--primary)] border border-[var(--primary)]/20' : 'bg-red-50 text-red-700 border border-red-200'
            ]">
              <span class="material-symbols-outlined text-xl">{{ toast.type === 'success' ? 'check_circle' : 'error' }}</span>
              {{ toast.message }}
            </div>
          </Transition>

          <form @submit.prevent="handleSubmit" class="space-y-5">
            <!-- Current Password -->
            <div>
              <label class="block text-sm font-semibold text-[var(--on-surface)] mb-1.5">当前登录密码 <span class="text-[var(--primary)]">*</span></label>
              <div class="flex items-center justify-between mb-2">
                <span></span>
                <button type="button" class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
                  忘记当前密码？ <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
                </button>
              </div>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 text-xl">lock</span>
                <input v-model="form.current" :type="showCurrent ? 'text' : 'password'" placeholder="请输入当前登录密码"
                  :class="['w-full pl-11 pr-12 py-3 rounded-xl border text-sm bg-white', errors.current ? 'border-red-400' : 'border-gray-200 focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10']" />
                <button type="button" @click="showCurrent = !showCurrent" class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-[var(--on-surface)]">
                  <span class="material-symbols-outlined text-xl">{{ showCurrent ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
              <p v-if="errors.current" class="text-red-500 text-xs mt-1.5">{{ errors.current }}</p>
              <p v-else class="text-xs text-[var(--on-surface-variant)] mt-1.5">若由辅导员分发的初始密码，请及时更新以开启全国高校就业派遣网签授权</p>
            </div>

            <!-- New Password -->
            <div>
              <label class="block text-sm font-semibold text-[var(--on-surface)] mb-1.5">设置新密码 <span class="text-[var(--primary)]">*</span></label>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 text-xl">key</span>
                <input v-model="form.newPassword" :type="showNew ? 'text' : 'password'" placeholder="请输入8-20位新密码（包含大小写、数字与特殊符号）"
                  :class="['w-full pl-11 pr-12 py-3 rounded-xl border text-sm bg-white', errors.newPassword ? 'border-red-400' : 'border-gray-200 focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10']" />
                <button type="button" @click="showNew = !showNew" class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-[var(--on-surface)]">
                  <span class="material-symbols-outlined text-xl">{{ showNew ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
              <p v-if="errors.newPassword" class="text-red-500 text-xs mt-1.5">{{ errors.newPassword }}</p>

              <!-- Password Strength -->
              <div class="mt-4 p-4 bg-gray-50 rounded-xl">
                <div class="flex items-center justify-between mb-3">
                  <span class="text-xs text-[var(--on-surface-variant)]">密码安全强度监测</span>
                  <span class="text-xs text-[var(--on-surface-variant)]">{{ form.newPassword ? strengthConfig[strengthLevel - 1]?.label || '未输入' : '未输入' }}</span>
                </div>
                <div class="flex gap-1.5 mb-4">
                  <div v-for="i in 4" :key="i"
                    :class="['h-1.5 flex-1 rounded-full transition-all duration-300',
                      strengthLevel >= i ? strengthConfig[strengthLevel - 1].color : 'bg-gray-200']">
                  </div>
                </div>
                <div class="grid grid-cols-2 gap-2">
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', form.newPassword.length >= 8 ? 'border-[var(--primary)] bg-[var(--primary)]' : 'border-gray-300']">
                      <span v-if="form.newPassword.length >= 8" class="material-symbols-outlined text-white text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-[var(--on-surface-variant)]">长度在 8-20 个字符之间</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', /[A-Z]/.test(form.newPassword) && /[a-z]/.test(form.newPassword) ? 'border-[var(--primary)] bg-[var(--primary)]' : 'border-gray-300']">
                      <span v-if="/[A-Z]/.test(form.newPassword) && /[a-z]/.test(form.newPassword)" class="material-symbols-outlined text-white text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-[var(--on-surface-variant)]">包含大写或小写英文字母</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', /\d/.test(form.newPassword) ? 'border-[var(--primary)] bg-[var(--primary)]' : 'border-gray-300']">
                      <span v-if="/\d/.test(form.newPassword)" class="material-symbols-outlined text-white text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-[var(--on-surface-variant)]">包含数字（0-9）</span>
                  </div>
                  <div class="flex items-center gap-2">
                    <div :class="['w-4 h-4 rounded-full border-2 flex items-center justify-center', /[^A-Za-z0-9]/.test(form.newPassword) ? 'border-[var(--primary)] bg-[var(--primary)]' : 'border-gray-300']">
                      <span v-if="/[^A-Za-z0-9]/.test(form.newPassword)" class="material-symbols-outlined text-white text-[12px]">check</span>
                    </div>
                    <span class="text-xs text-[var(--on-surface-variant)]">包含特殊标点符号（!@#$%^&*等）</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Confirm Password -->
            <div>
              <label class="block text-sm font-semibold text-[var(--on-surface)] mb-1.5">确认新密码 <span class="text-[var(--primary)]">*</span></label>
              <div class="relative">
                <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 text-xl">key</span>
                <input v-model="form.confirm" :type="showConfirm ? 'text' : 'password'" placeholder="请再次输入新密码以进行一致性确认"
                  :class="['w-full pl-11 pr-12 py-3 rounded-xl border text-sm bg-white', errors.confirm ? 'border-red-400' : 'border-gray-200 focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10']" />
                <button type="button" @click="showConfirm = !showConfirm" class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-[var(--on-surface)]">
                  <span class="material-symbols-outlined text-xl">{{ showConfirm ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
              <p v-if="errors.confirm" class="text-red-500 text-xs mt-1.5">{{ errors.confirm }}</p>
              <p v-else-if="form.confirm && form.newPassword === form.confirm" class="text-[var(--primary)] text-xs mt-1.5 flex items-center gap-1">
                <span class="material-symbols-outlined text-sm">check_circle</span> 密码匹配
              </p>
            </div>

            <!-- Revoke Sessions -->
            <label class="flex items-start gap-3 p-4 bg-[#eef2ff]/30 rounded-xl cursor-pointer border border-[var(--primary)]/10">
              <input v-model="form.revokeSessions" type="checkbox" class="w-4 h-4 mt-0.5 rounded border-gray-300 text-[var(--primary)] focus:ring-[var(--primary)]" />
              <div>
                <span class="text-sm font-semibold text-[var(--on-surface)] block">修改成功后退出其他设备上的登录状态</span>
                <span class="text-xs text-[var(--on-surface-variant)] mt-0.5 block">推荐勾选：避免因在高校机房、图书馆或公用招聘展位电脑登录后遗留未注销风险</span>
              </div>
            </label>

            <!-- Buttons -->
            <div class="flex gap-3 pt-2">
              <button type="submit" :disabled="loading"
                class="flex-1 py-3 bg-[var(--primary)] text-white rounded-xl font-medium text-sm hover:bg-[var(--primary-container)] transition-colors disabled:opacity-50 flex items-center justify-center gap-2">
                <span class="material-symbols-outlined text-lg">shield</span>
                <span v-if="loading">提交中...</span>
                <span v-else>确认修改密码</span>
              </button>
              <button type="button" @click="$router.back()"
                class="px-6 py-3 border border-gray-200 text-[var(--on-surface)] rounded-xl font-medium text-sm hover:bg-gray-50 transition-colors flex items-center gap-2">
                <span class="material-symbols-outlined text-lg">arrow_back</span>
                取消并返回
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- Security Tips Section -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
        <div class="h-1 bg-gradient-to-r from-[var(--primary)] to-[#4f8cff]"></div>
        <div class="p-6">
          <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-4">
            <span class="material-symbols-outlined text-[var(--primary)]">gpp_good</span>
            校园招聘与签约安全常识
          </h3>
          <div class="grid grid-cols-3 gap-4 mb-4">
            <div v-for="(tip, index) in securityTips" :key="index" class="p-4 bg-gray-50 rounded-xl">
              <div class="flex items-center gap-2 mb-2">
                <span :class="['material-symbols-outlined text-lg', tip.iconColor]">{{ tip.icon }}</span>
                <span class="text-sm font-semibold text-[var(--on-surface)]">{{ tip.title }}</span>
              </div>
              <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed">{{ tip.desc }}</p>
            </div>
          </div>
          <div class="flex items-center justify-between pt-2 border-t border-gray-100">
            <p class="text-xs text-[var(--on-surface-variant)] flex items-center gap-1">
              <span class="material-symbols-outlined text-[14px]">info</span>
              如遇非本人操作或异常登录提醒，请立即联系校园就业中心保卫通道：400-820-2025
            </p>
            <button class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
              查看最近登录日志 <span class="material-symbols-outlined text-[14px]">arrow_forward</span>
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
  { color: 'bg-red-500', textColor: 'text-red-500', label: '弱' },
  { color: 'bg-orange-500', textColor: 'text-orange-500', label: '一般' },
  { color: 'bg-yellow-500', textColor: 'text-yellow-600', label: '较强' },
  { color: 'bg-[var(--primary)]', textColor: 'text-[var(--primary)]', label: '强' }
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
  { icon: 'gpp_maybe', iconColor: 'text-[var(--primary)]', title: '防范钓鱼及冒名', desc: '平台官方及辅导员绝不会以"职位内推"、"资格审查"为由向您索取平台登录密码或短信验证码。' },
  { icon: 'password', iconColor: 'text-[var(--primary)]', title: '规避弱口令陷阱', desc: '请避免使用学号、生日、宿舍号或123456等易被推导组合，确保独立密码凭据。' },
  { icon: 'verified_user', iconColor: 'text-[var(--primary)]', title: '保障网签法律效力', desc: '修改密码后，与用人单位签署的三方就业协议和电子用工存证将自动关联最新的安全密钥保护。' }
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
