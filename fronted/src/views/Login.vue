<template>
  <div class="min-h-screen flex bg-background">
    <!-- Left Panel -->
    <div class="hidden lg:flex lg:w-5/12 relative overflow-hidden bg-gradient-to-br from-primary via-primary-container to-[#0a2472]">
      <div class="absolute inset-0 opacity-10 pointer-events-none">
        <svg class="w-full h-full" preserveAspectRatio="none" viewBox="0 0 100 100">
          <defs>
            <pattern id="grid" height="10" patternUnits="userSpaceOnUse" width="10">
              <path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" stroke-width="0.5"></path>
            </pattern>
          </defs>
          <rect fill="url(#grid)" height="100" width="100"></rect>
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
              开启你的<br>职业梦想新征程
            </h1>
            <p class="text-sm text-on-primary-container leading-relaxed">
              汇聚产学优质资源，打通从校园实习到高质量就职的全链路闭环通道。
            </p>
          </div>
          <div class="space-y-4 pt-1">
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">verified</span>
              </div>
              <div>
                <h4 class="text-sm text-surface-bright font-semibold">12,000+ 知名企业直接触达</h4>
                <p class="text-xs text-on-primary-container">央企国企、行业龙头、科研院所全覆盖，HR 直播与专场直收</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">security</span>
              </div>
              <div>
                <h4 class="text-sm text-surface-bright font-semibold">全国高校实名认证背书</h4>
                <p class="text-xs text-on-primary-container">学籍真实联审，岗位资质与招聘安全性全流程双向核验保障</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">psychology</span>
              </div>
              <div>
                <h4 class="text-sm text-surface-bright font-semibold">AI 简历诊断与精准撮合</h4>
                <p class="text-xs text-on-primary-container">人岗语义匹配评分，配套一对一导师生涯规划与模拟面试辅导</p>
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
        <div class="flex items-center justify-between pb-1">
          <div>
            <span class="text-xs text-primary font-semibold tracking-wider uppercase">Student / Candidate</span>
            <h2 class="text-2xl text-on-surface font-bold mt-1">欢迎登录</h2>
            <p class="text-sm text-on-surface-variant mt-0.5">进入系统查看最新校招动态、投递进度与面试安排</p>
          </div>
          <div class="hidden sm:flex flex-col items-end">
            <div class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-surface-container text-xs text-on-surface font-medium">
              <span class="w-2 h-2 rounded-full bg-primary"></span>
              学生通道
            </div>
            <router-link to="/login?role=hr" class="text-xs text-primary hover:underline mt-1">切换为企业 HR 登录 →</router-link>
          </div>
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

        <!-- Tabs -->
        <div class="flex bg-surface-container-low rounded-lg p-1 mt-6 mb-6">
          <button
            @click="activeTab = 'password'"
            :class="[
              'flex-1 py-2 rounded-md text-sm font-semibold transition-all',
              activeTab === 'password'
                ? 'bg-surface-container-lowest text-primary shadow-sm'
                : 'text-on-surface-variant hover:text-on-surface'
            ]"
          >密码快捷登录</button>
          <button
            @click="smsUnavailable"
            class="flex-1 py-2 rounded-md text-sm font-medium transition-all text-on-surface-variant/50 cursor-not-allowed flex items-center justify-center gap-1"
            title="短信服务暂未开通，请使用密码登录"
          >手机验证码登录<span class="text-[10px]">(暂未开放)</span></button>
        </div>

        <!-- Password Login Form -->
        <form v-if="activeTab === 'password'" @submit.prevent="handlePasswordLogin" class="space-y-4">
          <div class="space-y-1.5">
            <label class="block text-xs text-on-surface font-medium">手机号码</label>
            <div class="flex rounded-lg shadow-sm bg-surface-container-lowest">
              <div class="relative shrink-0">
                <select class="h-10 pl-3 pr-7 bg-surface-container-low text-on-surface text-sm rounded-l-lg appearance-none cursor-pointer focus:outline-none border-0">
                  <option>+86 (中国大陆)</option>
                  <option>+852 (中国香港)</option>
                  <option>+886 (中国台湾)</option>
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
            <div class="flex items-center justify-between">
              <label class="block text-xs text-on-surface font-medium">登录密码</label>
              <a href="javascript:void(0)" @click="forgetUnavailable" class="text-xs text-primary hover:underline">忘记密码？</a>
            </div>
            <div class="relative">
              <input
                v-model="form.password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="请输入登录密码（6-20位字符）"
                class="w-full h-10 px-3 pr-10 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border-0"
              />
              <button type="button" @click="showPassword = !showPassword" class="absolute right-2.5 top-2.5 text-outline hover:text-on-surface transition-colors">
                <span class="material-symbols-outlined text-[20px]">{{ showPassword ? 'visibility' : 'visibility_off' }}</span>
              </button>
            </div>
            <p v-if="errors.password" class="text-error text-xs">{{ errors.password }}</p>
          </div>

          <div class="flex items-center justify-between pt-1">
            <label class="flex items-center gap-2 cursor-pointer select-none">
              <input v-model="form.remember" type="checkbox" class="w-4 h-4 rounded accent-primary cursor-pointer" />
              <span class="text-sm text-on-surface-variant">30天内记住登录状态</span>
            </label>
          </div>

          <button type="submit" class="w-full h-11 mt-2 bg-gradient-to-r from-primary-container to-primary text-on-primary hover:opacity-95 text-sm font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2">
            <span>立即登录</span>
            <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
          </button>

          <div class="flex items-start gap-2 pt-1">
            <input v-model="form.agree" type="checkbox" class="mt-1 w-3.5 h-3.5 rounded accent-primary shrink-0 cursor-pointer" />
            <label class="text-sm text-on-surface-variant leading-snug cursor-pointer">
              我已认真阅读并同意
              <a href="#" class="text-primary hover:underline">《用户服务协议》</a>、
              <a href="#" class="text-primary hover:underline">《个人信息保护与隐私政策》</a>
              及高校毕业生求职诚信承诺。
            </label>
          </div>
        </form>

        <!-- SMS Login Form -->
        <form v-else @submit.prevent="handleSmsLogin" class="space-y-4">
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
                v-model="smsForm.phone"
                type="tel"
                maxlength="11"
                placeholder="请输入11位注册手机号"
                class="flex-1 h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-r-lg focus:outline-none border-0"
              />
            </div>
            <p v-if="smsErrors.phone" class="text-error text-xs">{{ smsErrors.phone }}</p>
          </div>

          <div class="space-y-1.5">
            <label class="block text-xs text-on-surface font-medium">短信验证码</label>
            <div class="flex gap-2">
              <input
                v-model="smsForm.code"
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
                {{ smsCooldown > 0 ? `${smsCooldown}秒后重新获取` : '获取验证码' }}
              </button>
            </div>
            <p v-if="smsErrors.code" class="text-error text-xs">{{ smsErrors.code }}</p>
          </div>

          <div class="flex items-center pt-1">
            <label class="flex items-center gap-2 cursor-pointer select-none">
              <input v-model="smsForm.remember" type="checkbox" class="w-4 h-4 rounded accent-primary cursor-pointer" />
              <span class="text-sm text-on-surface-variant">30天内记住登录状态</span>
            </label>
          </div>

          <button type="submit" class="w-full h-11 mt-2 bg-gradient-to-r from-primary-container to-primary text-on-primary hover:opacity-95 text-sm font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2">
            <span>立即登录</span>
            <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
          </button>

          <div class="flex items-start gap-2 pt-1">
            <input v-model="smsForm.agree" type="checkbox" class="mt-1 w-3.5 h-3.5 rounded accent-primary shrink-0 cursor-pointer" />
            <label class="text-sm text-on-surface-variant leading-snug cursor-pointer">
              我已认真阅读并同意
              <a href="#" class="text-primary hover:underline">《用户服务协议》</a>、
              <a href="#" class="text-primary hover:underline">《个人信息保护与隐私政策》</a>
              及高校毕业生求职诚信承诺。
            </label>
          </div>
        </form>

        <!-- Footer Links -->
        <div class="flex items-center justify-between text-center mt-4 text-sm text-on-surface-variant">
          <span>还没有学生账号？</span>
          <div class="space-x-3">
            <router-link to="/register" class="text-primary font-semibold hover:underline">免费注册学生账号</router-link>
            <span class="text-outline-variant">|</span>
            <router-link to="/register?role=hr" class="text-on-surface hover:text-primary font-medium">用人单位注册</router-link>
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
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAppStore } from '../stores/app'
import { login as apiLogin } from '../api/user'

const router = useRouter()
const route = useRoute()
const store = useAppStore()

const activeTab = ref('password')
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

const form = reactive({ phone: '', password: '', remember: true, agree: false })
const errors = reactive({ phone: '', password: '' })
const smsForm = reactive({ phone: '', code: '', remember: true, agree: false })
const smsErrors = reactive({ phone: '', code: '' })

function validatePhone(phone) {
  if (!phone) return '请输入手机号'
  if (!/^1[3-9]\d{9}$/.test(phone)) return '手机号格式不正确'
  return ''
}

function handlePasswordLogin() {
  errors.phone = validatePhone(form.phone)
  errors.password = !form.password ? '请输入密码' : form.password.length < 6 ? '密码至少6位' : ''
  if (errors.phone || errors.password) return
  if (!form.agree) { showToast('error', '请先阅读并勾选用户服务与隐私协议'); return }
  loading.value = true
  apiLogin({ phone: form.phone, password: form.password })
    .then(data => {
      const roleStr = data.role === 1 ? 'hr' : 'student'
      store.login(
        { name: data.username, phone: form.phone, avatar: data.avatar },
        roleStr,
        data.token,
        data.userId
      )
      showToast('success', '登录成功，正在跳转...')
      const redirect = route.query.redirect || (roleStr === 'hr' ? '/enterprise/dashboard' : '/')
      setTimeout(() => router.push(redirect), 800)
    })
    .catch(err => {
      showToast('error', err.message || '登录失败，请检查手机号和密码')
    })
    .finally(() => { loading.value = false })
}

function handleSmsLogin() {
  smsErrors.phone = validatePhone(smsForm.phone)
  smsErrors.code = !smsForm.code ? '请输入验证码' : smsForm.code.length < 6 ? '验证码为6位数字' : ''
  if (smsErrors.phone || smsErrors.code) return
  if (!smsForm.agree) { showToast('error', '请先阅读并勾选用户服务与隐私协议'); return }
  loading.value = true
  apiLogin({ phone: smsForm.phone, password: smsForm.code })
    .then(data => {
      const roleStr = data.role === 1 ? 'hr' : 'student'
      store.login(
        { name: data.username, phone: smsForm.phone, avatar: data.avatar },
        roleStr,
        data.token,
        data.userId
      )
      showToast('success', '登录成功，正在跳转...')
      const redirect = route.query.redirect || '/'
      setTimeout(() => router.push(redirect), 800)
    })
    .catch(err => {
      showToast('error', err.message || '登录失败，请检查手机号和验证码')
    })
    .finally(() => { loading.value = false })
}

function smsUnavailable() {
  showToast('error', '短信登录暂未开放，请使用密码登录')
}

function forgetUnavailable() {
  showToast('error', '忘记密码功能暂未开放，请联系管理员重置')
}

function sendSmsCode() {
  const phoneError = validatePhone(smsForm.phone)
  if (phoneError) { smsErrors.phone = phoneError; return }
  smsErrors.phone = ''
  smsCooldown.value = 60
  const timer = setInterval(() => {
    smsCooldown.value--
    if (smsCooldown.value <= 0) clearInterval(timer)
  }, 1000)
  showToast('success', '验证码已发送至您的手机，请查收')
}
</script>

<style scoped>
.toast-enter-active, .toast-leave-active { transition: all 0.3s ease; }
.toast-enter-from, .toast-leave-to { opacity: 0; transform: translateY(-12px); }
</style>
