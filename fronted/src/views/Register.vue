<template>
  <div class="min-h-screen flex bg-background">
    <!-- Left Panel -->
    <div class="hidden lg:flex lg:w-5/12 relative overflow-hidden bg-gradient-to-br from-primary via-primary-container to-[#0a2472]">
      <div class="absolute inset-0 opacity-10 pointer-events-none">
        <svg class="w-full h-full" preserveAspectRatio="none" viewBox="0 0 100 100">
          <defs>
            <pattern id="grid2" height="10" patternUnits="userSpaceOnUse" width="10">
              <path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" stroke-width="0.5"></path>
            </pattern>
          </defs>
          <rect fill="url(#grid2)" height="100" width="100"></rect>
        </svg>
      </div>
      <div class="absolute -top-32 -left-32 w-96 h-96 rounded-full bg-primary-fixed/40 blur-3xl pointer-events-none"></div>
      <div class="absolute -bottom-40 -right-40 w-[28rem] h-[28rem] rounded-full bg-secondary-fixed/50 blur-3xl pointer-events-none"></div>
      <div class="relative z-10 flex flex-col justify-between p-12 text-on-primary w-full">
        <div class="space-y-8">
          <div class="flex items-center gap-3">
            <div class="w-11 h-11 rounded-lg bg-surface-container-lowest/15 backdrop-blur-md p-0.5 flex items-center justify-center shadow-sm">
              <span class="material-symbols-outlined text-surface-bright text-[28px]">school</span>
            </div>
            <div>
              <span class="text-sm text-surface-bright font-bold tracking-tight block">校园就业综合服务平台</span>
              <span class="text-[10px] text-on-primary-container tracking-[0.2em] uppercase">Campus Career Nexus</span>
            </div>
          </div>
          <div class="space-y-4">
            <div class="inline-flex items-center gap-1 px-3 py-1 rounded-full bg-surface-container-lowest/20 backdrop-blur-sm">
              <span class="w-2 h-2 rounded-full bg-secondary-container animate-pulse"></span>
              <span class="text-xs text-surface-bright font-medium">2025届 春季名企直聘专场进行中</span>
            </div>
            <h1 class="text-[2rem] font-bold leading-tight">
              加入我们，<br>找到理想工作
            </h1>
            <p class="text-sm text-on-primary-container leading-relaxed">
              汇聚全国名企高校资源，开启高质量实习就业通道，赋能青年人才职业跃升。
            </p>
          </div>
          <div class="space-y-4 pt-1">
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">bolt</span>
              </div>
              <div>
                <h4 class="text-sm text-surface-bright font-semibold">快速入职通道</h4>
                <p class="text-xs text-on-primary-container">海量世界500强与专精特新企业实习直通车，支持免笔试极速内推</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">auto_awesome</span>
              </div>
              <div>
                <h4 class="text-sm text-surface-bright font-semibold">AI 智能岗位匹配</h4>
                <p class="text-xs text-on-primary-container">基于简历与求职意向的大模型精准推荐，匹配度实时诊断</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">handshake</span>
              </div>
              <div>
                <h4 class="text-sm text-surface-bright font-semibold">校企直连保障</h4>
                <p class="text-xs text-on-primary-container">教育部合规备案，三方协议在线网签，档案转递无缝衔接</p>
              </div>
            </div>
          </div>
        </div>
        <div class="p-4 rounded-lg bg-surface-container-lowest/10 backdrop-blur-md">
          <div class="flex items-center justify-between">
            <div class="flex items-center gap-2">
              <span class="material-symbols-outlined text-tertiary-fixed-dim text-[20px]">workspace_premium</span>
              <span class="text-xs text-surface-bright font-medium">985/211 重点高校专属推荐通告</span>
            </div>
            <span class="text-[10px] text-secondary-fixed-dim font-semibold">网签直通</span>
          </div>
          <p class="text-xs text-on-primary-container mt-1">
            支持"全国高校毕业生网上签约系统"无缝同步与档案转递
          </p>
        </div>
      </div>
    </div>

    <!-- Right Panel -->
    <div class="w-full lg:w-7/12 flex items-center justify-center p-6 sm:p-12 overflow-y-auto">
      <div class="w-full max-w-md">
        <!-- Mobile Logo -->
        <div class="lg:hidden flex items-center gap-2 mb-8">
          <span class="material-symbols-outlined text-primary text-3xl">school</span>
          <span class="text-xl font-bold text-on-surface">校园就业平台</span>
        </div>

        <!-- Header -->
        <div class="pb-1">
          <span class="text-xs text-primary font-semibold tracking-wider uppercase">Student / Candidate</span>
          <h2 class="text-2xl text-on-surface font-bold mt-1">欢迎注册</h2>
          <p class="text-sm text-on-surface-variant mt-0.5">创建账号开始你的校招求职之旅</p>
        </div>

        <!-- Toast -->
        <Transition name="toast">
          <div v-if="toast.show" :class="[
            'fixed top-6 right-6 z-50 flex items-center gap-3 px-5 py-3 rounded-xl shadow-lg text-sm font-medium',
            toast.type === 'success' ? 'bg-emerald-50 text-emerald-700 border border-emerald-200' : 'bg-red-50 text-red-700 border border-red-200'
          ]">
            <span class="material-symbols-outlined text-xl">{{ toast.type === 'success' ? 'check_circle' : 'error' }}</span>
            {{ toast.message }}
          </div>
        </Transition>

        <!-- Role Toggle -->
        <div class="flex bg-surface-container-low rounded-lg p-1 mt-6 mb-6">
          <button
            @click="role = 'student'"
            :class="[
              'flex-1 py-2 rounded-md text-sm font-semibold transition-all',
              role === 'student' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface'
            ]"
          >学生注册</button>
          <button
            @click="role = 'hr'"
            :class="[
              'flex-1 py-2 rounded-md text-sm font-medium transition-all',
              role === 'hr' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface'
            ]"
          >用人单位注册</button>
        </div>

        <!-- Form -->
        <form @submit.prevent="handleRegister" class="space-y-4">
          <div class="space-y-1.5">
            <label class="block text-xs text-on-surface font-medium">手机号码</label>
            <div class="flex rounded-lg shadow-sm bg-surface-container-lowest">
              <div class="relative shrink-0">
                <select class="h-10 pl-3 pr-7 bg-surface-container-low text-on-surface text-sm rounded-l-lg appearance-none cursor-pointer focus:outline-none border-0">
                  <option>+86 (中国大陆)</option>
                </select>
                <span class="material-symbols-outlined pointer-events-none absolute right-1.5 top-2.5 text-on-surface-variant text-[18px]">arrow_drop_down</span>
              </div>
              <input
                v-model="form.phone"
                type="tel"
                maxlength="11"
                placeholder="请输入11位注册手机号"
                class="flex-1 h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-r-lg focus:outline-none border-0"
              />
            </div>
            <p v-if="errors.phone" class="text-error text-xs">{{ errors.phone }}</p>
          </div>

          <div class="space-y-1.5">
            <label class="block text-xs text-on-surface font-medium">短信验证码</label>
            <div class="flex gap-2">
              <input
                v-model="form.code"
                type="text"
                maxlength="6"
                placeholder="6位短信验证码"
                class="flex-1 h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border-0"
              />
              <button
                type="button"
                @click="sendSmsCode"
                :disabled="smsCooldown > 0"
                class="px-4 h-10 bg-surface-container-low text-primary hover:bg-surface-container text-xs font-semibold rounded-lg transition-colors whitespace-nowrap"
                :class="smsCooldown > 0 ? 'opacity-50 cursor-not-allowed' : ''"
              >
                {{ smsCooldown > 0 ? `${smsCooldown}秒` : '获取验证码' }}
              </button>
            </div>
            <p v-if="errors.code" class="text-error text-xs">{{ errors.code }}</p>
          </div>

          <div class="space-y-1.5">
            <label class="block text-xs text-on-surface font-medium">设置密码</label>
            <div class="relative">
              <input
                v-model="form.password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="请输入8-20位密码（包含大小写、数字与特殊符号）"
                class="w-full h-10 px-3 pr-10 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border-0"
              />
              <button type="button" @click="showPassword = !showPassword" class="absolute right-2.5 top-2.5 text-outline hover:text-on-surface transition-colors">
                <span class="material-symbols-outlined text-[20px]">{{ showPassword ? 'visibility' : 'visibility_off' }}</span>
              </button>
            </div>
            <p v-if="errors.password" class="text-error text-xs">{{ errors.password }}</p>
            <!-- Strength -->
            <div v-if="form.password" class="bg-surface-container-low rounded-lg p-3 space-y-2 mt-1">
              <div class="flex items-center justify-between text-xs">
                <span class="text-on-surface-variant font-medium">密码安全强度</span>
                <span :class="['font-semibold', strengthLabel.color]">{{ strengthLabel.text }}</span>
              </div>
              <div class="grid grid-cols-4 gap-1 h-1.5">
                <div v-for="i in 4" :key="i" class="rounded-full transition-colors duration-300"
                  :class="i <= strengthScore ? strengthBarColor : 'bg-surface-container'"></div>
              </div>
            </div>
          </div>

          <div class="space-y-1.5">
            <label class="block text-xs text-on-surface font-medium">确认密码</label>
            <div class="relative">
              <input
                v-model="form.confirmPassword"
                type="password"
                placeholder="请再次输入新密码"
                class="w-full h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border-0"
              />
            </div>
            <p v-if="form.confirmPassword && form.password !== form.confirmPassword" class="text-error text-xs">两次输入的密码不一致</p>
          </div>

          <div class="flex items-start gap-2 pt-1">
            <input v-model="form.agree" type="checkbox" class="mt-1 w-3.5 h-3.5 rounded accent-primary shrink-0 cursor-pointer" />
            <label class="text-sm text-on-surface-variant leading-snug cursor-pointer">
              我已认真阅读并同意
              <a href="#" class="text-primary hover:underline">《用户服务协议》</a>、
              <a href="#" class="text-primary hover:underline">《个人信息保护与隐私政策》</a>
              及高校毕业生求职诚信承诺。
            </label>
          </div>

          <button type="submit" class="w-full h-11 mt-2 bg-gradient-to-r from-primary-container to-primary text-on-primary hover:opacity-95 text-sm font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2">
            <span>立即注册</span>
            <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
          </button>
        </form>

        <!-- Footer -->
        <div class="flex items-center justify-between text-center mt-6 text-sm text-on-surface-variant">
          <span>已有学生账号？</span>
          <div class="space-x-3">
            <router-link to="/login" class="text-primary font-semibold hover:underline">立即登录</router-link>
            <span class="text-outline-variant">|</span>
            <router-link to="/login?role=hr" class="text-on-surface hover:text-primary font-medium">企业HR登录</router-link>
          </div>
        </div>

        <div class="pt-6 text-center text-xs text-outline">
          全国高校毕业生就业一体化指导服务体系认证支持
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { register as apiRegister, login as apiLogin } from '../api/user'

const router = useRouter()
const store = useAppStore()

const role = ref('student')
const showPassword = ref(false)
const loading = ref(false)
const smsCooldown = ref(0)
const toast = reactive({ show: false, type: 'success', message: '' })

function showToast(type, message) {
  toast.show = true
  toast.type = type
  toast.message = message
  setTimeout(() => { toast.show = false }, 3000)
}

const form = reactive({ phone: '', code: '', password: '', confirmPassword: '', agree: false })
const errors = reactive({ phone: '', code: '', password: '' })

const strengthScore = computed(() => {
  const p = form.password
  if (!p) return 0
  let s = 0
  if (p.length >= 8 && p.length <= 20) s++
  if (/[a-zA-Z]/.test(p)) s++
  if (/[0-9]/.test(p)) s++
  if (/[^a-zA-Z0-9]/.test(p)) s++
  return s
})

const strengthLabel = computed(() => {
  const s = strengthScore.value
  if (s <= 1) return { text: '弱', color: 'text-error' }
  if (s === 2) return { text: '一般', color: 'text-tertiary-container' }
  if (s === 3) return { text: '良好', color: 'text-secondary' }
  return { text: '强', color: 'text-primary' }
})

const strengthBarColor = computed(() => {
  const s = strengthScore.value
  if (s <= 1) return 'bg-error'
  if (s === 2) return 'bg-tertiary-container'
  if (s === 3) return 'bg-secondary'
  return 'bg-primary'
})

function validatePhone(phone) {
  if (!phone) return '请输入手机号'
  if (!/^1[3-9]\d{9}$/.test(phone)) return '手机号格式不正确'
  return ''
}

function handleRegister() {
  errors.phone = validatePhone(form.phone)
  errors.code = !form.code ? '请输入验证码' : form.code.length < 6 ? '验证码为6位数字' : ''
  errors.password = !form.password ? '请输入密码' : form.password.length < 8 ? '密码至少8位' : ''
  if (errors.phone || errors.code || errors.password) return
  if (form.password !== form.confirmPassword) { showToast('error', '两次输入的密码不一致'); return }
  if (!form.agree) { showToast('error', '请先阅读并同意用户协议与隐私政策'); return }
  loading.value = true
  const roleNum = role.value === 'hr' ? 1 : 0
  apiRegister({ phone: form.phone, password: form.password, username: form.phone, role: roleNum })
    .then(() => apiLogin({ phone: form.phone, password: form.password }))
    .then(data => {
      const roleStr = data.role === 1 ? 'hr' : 'student'
      store.login(
        { name: data.username, phone: form.phone, avatar: data.avatar },
        roleStr,
        data.token,
        data.userId
      )
      showToast('success', '注册成功，正在跳转...')
      setTimeout(() => router.push('/'), 800)
    })
    .catch(err => {
      showToast('error', err.message || '注册失败，请稍后重试')
    })
    .finally(() => { loading.value = false })
}

function sendSmsCode() {
  const phoneError = validatePhone(form.phone)
  if (phoneError) { errors.phone = phoneError; return }
  errors.phone = ''
  smsCooldown.value = 60
  const timer = setInterval(() => {
    smsCooldown.value--
    if (smsCooldown.value <= 0) clearInterval(timer)
  }, 1000)
  showToast('success', '验证码已发送')
}
</script>

<style scoped>
.toast-enter-active, .toast-leave-active { transition: all 0.3s ease; }
.toast-enter-from, .toast-leave-to { opacity: 0; transform: translateY(-12px); }
</style>
