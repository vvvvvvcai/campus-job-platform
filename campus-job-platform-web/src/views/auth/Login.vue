<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
    <div class="w-full max-w-6xl bg-white rounded-xl shadow-xl overflow-hidden grid grid-cols-1 lg:grid-cols-12">
      <!-- Left Section - Branding -->
      <div class="lg:col-span-5 bg-gradient-to-br from-primary via-blue-600 to-blue-700 p-8 lg:p-10 flex flex-col justify-between relative overflow-hidden text-white">
        <!-- Decorative elements -->
        <div class="absolute inset-0 opacity-10 pointer-events-none">
          <svg class="w-full h-full" preserveAspectRatio="none" viewBox="0 0 100 100">
            <defs>
              <pattern id="grid" height="10" patternUnits="userSpaceOnUse" width="10">
                <path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" stroke-width="0.5"/>
              </pattern>
            </defs>
            <rect fill="url(#grid)" height="100" width="100"/>
          </svg>
        </div>

        <div class="relative z-10 space-y-8">
          <div class="flex items-center gap-3">
            <div class="w-11 h-11 rounded-lg bg-white/15 backdrop-blur-md p-2 flex items-center justify-center">
              <span class="material-symbols-outlined text-2xl">school</span>
            </div>
            <div>
              <span class="font-bold text-lg block">校园就业综合服务平台</span>
              <span class="text-xs text-blue-200 tracking-widest uppercase">Campus Career Nexus</span>
            </div>
          </div>

          <div class="space-y-4">
            <div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-white/20 backdrop-blur-sm">
              <span class="w-2 h-2 rounded-full bg-green-400 animate-pulse"></span>
              <span class="text-sm font-medium">2025届 春季名企直聘专场进行中</span>
            </div>
            <h1 class="text-3xl lg:text-4xl font-bold leading-tight">
              开启你的<br/>职业梦想新征程
            </h1>
            <p class="text-blue-100 leading-relaxed">
              汇聚产学优质资源，打通从校园实习到高质量就职的全链路闭环通道。
            </p>
          </div>

          <div class="space-y-4 pt-2">
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-white/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-lg">verified</span>
              </div>
              <div>
                <h4 class="font-semibold">12,000+ 知名企业直接触达</h4>
                <p class="text-sm text-blue-200">央企国企、行业龙头、科研院所全覆盖</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-white/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-lg">security</span>
              </div>
              <div>
                <h4 class="font-semibold">全国高校实名认证背书</h4>
                <p class="text-sm text-blue-200">学籍真实联审，岗位资质全流程核验</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-8 h-8 rounded-lg bg-white/15 flex items-center justify-center shrink-0 mt-0.5">
                <span class="material-symbols-outlined text-lg">psychology</span>
              </div>
              <div>
                <h4 class="font-semibold">AI 简历诊断与精准撮合</h4>
                <p class="text-sm text-blue-200">人岗语义匹配评分，一对一导师辅导</p>
              </div>
            </div>
          </div>
        </div>

        <div class="relative z-10 pt-6 mt-6">
          <div class="p-4 rounded-lg bg-white/10 backdrop-blur-md">
            <div class="flex items-center justify-between">
              <div class="flex items-center gap-2">
                <span class="material-symbols-outlined text-yellow-400">workspace_premium</span>
                <span class="text-sm font-medium">985/211 重点高校专属推荐</span>
              </div>
              <span class="text-xs font-semibold text-yellow-300">网签直通</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Section - Login Form -->
      <div class="lg:col-span-7 p-8 lg:p-10 flex flex-col justify-between">
        <div class="w-full max-w-md mx-auto space-y-6">
          <div class="flex items-center justify-between pb-2">
            <div>
              <span class="text-sm text-primary font-semibold tracking-wide uppercase">Student / Candidate</span>
              <h2 class="text-2xl font-bold text-gray-900 mt-1">欢迎登录</h2>
              <p class="text-sm text-gray-500 mt-0.5">进入系统查看最新校招动态、投递进度与面试安排</p>
            </div>
            <div class="hidden sm:flex flex-col items-end">
              <div class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-gray-100 text-sm font-medium">
                <span class="w-2 h-2 rounded-full bg-primary"></span>
                学生通道
              </div>
              <span class="text-xs text-primary mt-1 cursor-pointer hover:underline">切换为企业 HR 登录 →</span>
            </div>
          </div>

          <!-- Login Tabs -->
          <div class="flex p-1 bg-gray-100 rounded-lg">
            <button
              @click="loginType = 'password'"
              :class="[
                'flex-1 py-2 rounded-md text-sm font-semibold transition-all text-center',
                loginType === 'password' ? 'bg-white text-primary shadow-sm' : 'text-gray-500 hover:text-gray-700'
              ]"
            >
              密码快捷登录
            </button>
            <button
              @click="loginType = 'sms'"
              :class="[
                'flex-1 py-2 rounded-md text-sm font-medium transition-all text-center',
                loginType === 'sms' ? 'bg-white text-primary shadow-sm' : 'text-gray-500 hover:text-gray-700'
              ]"
            >
              手机验证码登录
            </button>
          </div>

          <!-- Login Form -->
          <form @submit.prevent="handleLogin" class="space-y-4">
            <!-- Phone Input -->
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">手机号码</label>
              <div class="flex rounded-lg shadow-sm bg-white">
                <select v-model="countryCode" class="h-10 pl-3 pr-7 bg-gray-50 text-gray-700 text-sm rounded-l-lg border-0 focus:outline-none cursor-pointer">
                  <option value="+86">+86 (中国大陆)</option>
                  <option value="+852">+852 (中国香港)</option>
                  <option value="+853">+853 (中国澳门)</option>
                  <option value="+886">+886 (中国台湾)</option>
                </select>
                <input
                  v-model="phone"
                  type="tel"
                  placeholder="请输入11位注册手机号"
                  required
                  class="flex-1 h-10 px-3.5 bg-white text-gray-900 placeholder:text-gray-400 text-sm rounded-r-lg border-0 focus:outline-none"
                />
              </div>
            </div>

            <!-- Password Input -->
            <div v-if="loginType === 'password'" class="space-y-1.5">
              <div class="flex items-center justify-between">
                <label class="block text-sm font-medium text-gray-700">登录密码</label>
                <a class="text-sm text-primary hover:underline cursor-pointer">忘记密码？</a>
              </div>
              <div class="relative">
                <input
                  v-model="password"
                  :type="showPassword ? 'text' : 'password'"
                  placeholder="请输入登录密码（6-20位字符）"
                  required
                  class="w-full h-10 pl-3.5 pr-10 bg-white text-gray-900 placeholder:text-gray-400 text-sm rounded-lg shadow-sm border border-gray-200 focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/10"
                />
                <button
                  type="button"
                  @click="showPassword = !showPassword"
                  class="absolute right-2.5 top-2.5 text-gray-400 hover:text-gray-600"
                >
                  <span class="material-symbols-outlined text-xl">
                    {{ showPassword ? 'visibility' : 'visibility_off' }}
                  </span>
                </button>
              </div>
            </div>

            <!-- SMS Code Input -->
            <div v-if="loginType === 'sms'" class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">短信验证码</label>
              <div class="flex gap-2">
                <input
                  v-model="smsCode"
                  type="text"
                  maxlength="6"
                  placeholder="6位短信验证码"
                  class="flex-1 h-10 px-3.5 bg-white text-gray-900 placeholder:text-gray-400 text-sm rounded-lg shadow-sm border border-gray-200 focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/10"
                />
                <button
                  type="button"
                  @click="sendSmsCode"
                  :disabled="countdown > 0"
                  :class="[
                    'px-4 h-10 bg-gray-100 text-primary font-semibold rounded-lg transition-colors whitespace-nowrap',
                    countdown > 0 ? 'opacity-50 cursor-not-allowed' : 'hover:bg-gray-200'
                  ]"
                >
                  {{ countdown > 0 ? `${countdown}秒后重试` : '获取验证码' }}
                </button>
              </div>
            </div>

            <!-- Remember Me -->
            <div class="flex items-center justify-between pt-1">
              <label class="flex items-center gap-2 cursor-pointer select-none">
                <input v-model="rememberMe" type="checkbox" class="w-4 h-4 rounded text-primary focus:ring-0 cursor-pointer"/>
                <span class="text-sm text-gray-500">30天内记住登录状态</span>
              </label>
            </div>

            <!-- Submit Button -->
            <button
              type="submit"
              :disabled="loading"
              class="w-full h-11 mt-2 bg-gradient-to-r from-blue-600 to-primary text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2 disabled:opacity-50"
            >
              <span v-if="loading" class="material-symbols-outlined animate-spin text-lg">refresh</span>
              <span>立即登录</span>
              <span v-if="!loading" class="material-symbols-outlined text-lg">arrow_forward</span>
            </button>

            <!-- Terms -->
            <div class="flex items-start gap-2 pt-1">
              <input v-model="agreeTerms" type="checkbox" required class="mt-1 w-3.5 h-3.5 rounded text-primary focus:ring-0 cursor-pointer"/>
              <label class="text-sm text-gray-500 leading-snug cursor-pointer">
                我已认真阅读并同意
                <a class="text-primary hover:underline">《用户服务协议》</a>、
                <a class="text-primary hover:underline">《个人信息保护与隐私政策》</a>
                及高校毕业生求职诚信承诺。
              </label>
            </div>
          </form>

          <!-- Third Party Login -->
          <div class="space-y-4 pt-2">
            <div class="relative flex items-center justify-center">
              <div class="w-full h-px bg-gray-200"></div>
              <span class="absolute px-3 bg-white text-sm text-gray-500">高校统一认证与快捷登录</span>
            </div>
            <div class="grid grid-cols-3 gap-3">
              <button class="flex flex-col items-center justify-center gap-1.5 p-2.5 rounded-lg bg-gray-50 hover:bg-gray-100 transition-all text-gray-600">
                <span class="material-symbols-outlined text-primary text-xl">account_balance</span>
                <span class="text-xs font-medium">校园统一认证</span>
              </button>
              <button class="flex flex-col items-center justify-center gap-1.5 p-2.5 rounded-lg bg-gray-50 hover:bg-gray-100 transition-all text-gray-600">
                <span class="material-symbols-outlined text-green-600 text-xl">qr_code_scanner</span>
                <span class="text-xs font-medium">微信扫码</span>
              </button>
              <button class="flex flex-col items-center justify-center gap-1.5 p-2.5 rounded-lg bg-gray-50 hover:bg-gray-100 transition-all text-gray-600">
                <span class="material-symbols-outlined text-yellow-600 text-xl">badge</span>
                <span class="text-xs font-medium">学信网实名</span>
              </button>
            </div>
            <div class="flex items-center justify-between text-center pt-2 text-sm text-gray-500">
              <span>还没有学生账号？</span>
              <div class="space-x-3">
                <router-link to="/register" class="text-primary font-semibold hover:underline">免费注册毕业生账号</router-link>
                <span class="text-gray-300">|</span>
                <a class="text-gray-700 hover:text-primary font-medium cursor-pointer">用人单位注册</a>
              </div>
            </div>
          </div>
        </div>

        <div class="pt-6 text-center text-sm text-gray-400">
          全国高校毕业生就业一体化指导服务体系认证支持
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../../stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const loginType = ref('password')
const countryCode = ref('+86')
const phone = ref('')
const password = ref('')
const smsCode = ref('')
const showPassword = ref(false)
const rememberMe = ref(true)
const agreeTerms = ref(false)
const loading = ref(false)
const countdown = ref(0)

let timer = null

const sendSmsCode = () => {
  if (!phone.value || phone.value.length < 7) {
    ElMessage.warning('请输入有效的手机号后再获取验证码')
    return
  }
  
  countdown.value = 60
  ElMessage.success('验证码已发送至您的手机，请查收')
  
  timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
    }
  }, 1000)
}

const handleLogin = async () => {
  if (!agreeTerms.value) {
    ElMessage.warning('请先阅读并勾选用户服务与隐私协议')
    return
  }

  loading.value = true
  try {
    const result = await userStore.loginAction(phone.value, password.value)
    if (result.success) {
      ElMessage.success('登录成功')
      const redirect = route.query.redirect || '/home'
      router.push(redirect)
    } else {
      ElMessage.error(result.message || '登录失败')
    }
  } finally {
    loading.value = false
  }
}
</script>
