<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const phone = ref('13800138000')
const password = ref('AdminPass@2024')
const showPassword = ref(false)
const rememberMe = ref(true)
const isLoading = ref(false)

async function handleLogin() {
  if (!phone.value || !password.value) return
  isLoading.value = true
  const success = await authStore.login(phone.value, password.value)
  isLoading.value = false
  if (success) {
    router.push('/dashboard')
  }
}
</script>

<template>
  <div class="min-h-screen bg-background font-body-md text-on-surface antialiased flex flex-col">
    <!-- Header bar -->
    <header class="w-full h-14 px-gutter flex items-center bg-surface/80 backdrop-blur-xl border-b border-surface-container-high/60">
      <div class="flex items-center gap-space-sm">
        <div class="w-8 h-8 rounded-lg bg-primary-container flex items-center justify-center">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 40 40" width="20" height="20" fill="none">
            <defs>
              <linearGradient id="logoGrad2" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="0%" stop-color="#4F46E5"/>
                <stop offset="100%" stop-color="#7C3AED"/>
              </linearGradient>
            </defs>
            <rect width="40" height="40" rx="10" fill="url(#logoGrad2)"/>
            <path d="M12 28L20 12L28 28" stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M15 23H25" stroke="white" stroke-width="3" stroke-linecap="round"/>
            <circle cx="20" cy="9" r="2.5" fill="#38BDF8"/>
          </svg>
        </div>
        <div class="flex flex-col">
          <div class="flex items-center gap-space-xs">
            <span class="font-title-md text-title-md text-on-surface">大学生实习就业综合服务平台</span>
            <span class="px-space-xs py-0.5 rounded bg-surface-container-high text-on-surface-variant font-code-xs text-code-xs">管理控制台</span>
          </div>
          <span class="font-body-sm text-body-sm text-outline hidden sm:inline">Institutional Career Governance Architecture</span>
        </div>
      </div>
    </header>

    <!-- Main content -->
    <main class="w-full pt-16 bg-surface flex-1 flex flex-col">
      <div class="relative w-full py-space-xl px-margin flex items-center justify-center overflow-hidden">
        <!-- Ambient glow backgrounds -->
        <div class="absolute -top-32 -left-20 w-96 h-96 rounded-full bg-primary-fixed blur-3xl opacity-60 pointer-events-none"></div>
        <div class="absolute top-1/2 -right-32 w-80 h-80 rounded-full bg-surface-container blur-2xl opacity-70 pointer-events-none"></div>
        <div class="absolute -bottom-24 left-1/3 w-96 h-96 rounded-full bg-surface-container-high blur-3xl opacity-50 pointer-events-none"></div>

        <!-- Login card -->
        <div class="relative w-full max-w-md rounded-xl shadow-xl bg-surface-container-lowest overflow-hidden p-space-lg sm:p-space-xl border border-surface-container-high/60">
          <!-- Header -->
          <div class="text-center mb-space-lg">
            <div class="inline-flex items-center justify-center w-12 h-12 rounded-xl bg-primary-fixed text-primary mb-space-sm shadow-sm">
              <span class="material-symbols-outlined text-[26px]">admin_panel_settings</span>
            </div>
            <span class="font-code-xs text-code-xs uppercase tracking-wider text-primary font-semibold block mb-0.5">Institutional Access Portal</span>
            <h1 class="font-display-md text-display-md text-on-surface">管理员登录</h1>
            <p class="font-body-sm text-body-sm text-on-surface-variant mt-1">请输入已授权的机构管理员手机号及密码核验登录</p>
          </div>

          <!-- Form -->
          <form class="space-y-space-md" @submit.prevent="handleLogin">
            <!-- Phone -->
            <div>
              <label class="block font-label-sm text-label-sm text-on-surface-variant mb-1">手机号</label>
              <div class="relative flex items-center">
                <span class="material-symbols-outlined absolute left-3 text-outline text-[18px]">phone_iphone</span>
                <input
                  v-model="phone"
                  type="tel"
                  placeholder="请输入管理员绑定的手机号"
                  required
                  class="w-full pl-10 pr-3 py-2.5 rounded-lg bg-surface-container-low text-on-surface font-body-md text-body-md outline-none focus:bg-surface-container-lowest focus:shadow-[0_0_0_2px_#4f46e5] transition-all"
                />
              </div>
            </div>

            <!-- Password -->
            <div>
              <div class="flex items-center justify-between mb-1">
                <label class="font-label-sm text-label-sm text-on-surface-variant">登录密码</label>
              </div>
              <div class="relative flex items-center">
                <span class="material-symbols-outlined absolute left-3 text-outline text-[18px]">lock</span>
                <input
                  v-model="password"
                  :type="showPassword ? 'text' : 'password'"
                  placeholder="请输入登录密码"
                  required
                  class="w-full pl-10 pr-10 py-2.5 rounded-lg bg-surface-container-low text-on-surface font-body-md text-body-md outline-none focus:bg-surface-container-lowest focus:shadow-[0_0_0_2px_#4f46e5] transition-all"
                />
                <button
                  type="button"
                  class="absolute right-3 text-outline hover:text-on-surface transition-colors"
                  @click="showPassword = !showPassword"
                >
                  <span class="material-symbols-outlined text-[18px]">{{ showPassword ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
            </div>

            <!-- Remember & Forgot -->
            <div class="flex items-center justify-between pt-1">
              <label class="flex items-center gap-space-xs cursor-pointer select-none">
                <input v-model="rememberMe" type="checkbox" class="w-4 h-4 rounded text-primary focus:ring-0" />
                <span class="font-label-sm text-label-sm text-on-surface-variant">记住登录状态</span>
              </label>
              <a class="font-label-sm text-label-sm text-primary hover:underline" href="#">忘记密码</a>
            </div>

            <!-- Submit -->
            <div class="pt-2">
              <button
                type="submit"
                :disabled="isLoading"
                class="w-full py-2.5 rounded-lg bg-gradient-to-r from-primary via-primary-container to-secondary text-on-primary font-title-md text-title-md shadow-md hover:shadow-lg hover:brightness-105 active:scale-[0.99] transition-all flex items-center justify-center gap-space-xs disabled:opacity-70"
              >
                <span v-if="isLoading" class="material-symbols-outlined text-[20px] animate-spin">refresh</span>
                <span v-else class="material-symbols-outlined text-[20px]">login</span>
                <span>{{ isLoading ? '鉴权校验中...' : '登录控制台' }}</span>
              </button>
            </div>
          </form>

          <!-- Register link -->
          <div class="pt-space-md mt-space-sm text-center font-body-sm text-body-sm text-on-surface-variant">
            暂无管理员授权？
            <a class="text-primary font-semibold hover:underline" href="#">申请开通管理员权限</a>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>
