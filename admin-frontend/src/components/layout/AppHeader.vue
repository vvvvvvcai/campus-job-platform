<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

function handleLogout() {
  authStore.logout()
  router.push('/login')
}
</script>

<template>
  <header class="fixed top-0 left-64 right-0 z-40 bg-surface/80 backdrop-blur-xl shadow-[0_1px_8px_rgba(0,0,0,0.04)]">
    <div class="h-14 w-full px-gutter-lg flex items-center justify-between gap-space-md">
      <!-- Search -->
      <div class="flex items-center gap-space-md flex-1 max-w-lg">
        <div class="relative w-full">
          <span class="material-symbols-outlined absolute left-space-sm top-1/2 -translate-y-1/2 text-outline text-[20px]">search</span>
          <input
            class="w-full h-9 pl-9 pr-space-md rounded-lg bg-surface-container-low text-on-surface placeholder:text-outline font-body-sm text-body-sm focus:outline-none focus:bg-surface-container-lowest transition-all"
            placeholder="全局搜索：企业名称、学生学号、岗位关键词..."
            type="text"
          />
        </div>
      </div>

      <!-- Right section -->
      <div class="flex items-center gap-space-md">
        <!-- Quick links -->
        <div class="hidden md:flex items-center gap-space-xs">
          <router-link
            to="/enterprise-audit"
            class="flex items-center gap-space-xs px-space-sm py-space-xs rounded-full bg-surface-container text-on-surface-variant hover:bg-surface-container-high hover:text-on-surface transition-all"
          >
            <span class="material-symbols-outlined text-[16px] text-error">corporate_fare</span>
            <span class="font-label-sm text-label-sm">待审企业</span>
            <span class="font-label-sm text-label-sm px-1.5 rounded-full bg-error text-on-error">3</span>
          </router-link>
          <router-link
            to="/job-audit"
            class="flex items-center gap-space-xs px-space-sm py-space-xs rounded-full bg-surface-container text-on-surface-variant hover:bg-surface-container-high hover:text-on-surface transition-all"
          >
            <span class="material-symbols-outlined text-[16px] text-secondary">work</span>
            <span class="font-label-sm text-label-sm">待审职位</span>
            <span class="font-label-sm text-label-sm px-1.5 rounded-full bg-secondary-container text-on-secondary-container">8</span>
          </router-link>
        </div>

        <div class="h-4 w-px bg-outline-variant"></div>

        <!-- Notifications -->
        <button class="w-8 h-8 rounded-full flex items-center justify-center text-on-surface-variant hover:bg-surface-container-high hover:text-on-surface transition-all relative" type="button">
          <span class="material-symbols-outlined text-[20px]">notifications</span>
          <span class="absolute top-1.5 right-1.5 w-2 h-2 rounded-full bg-error"></span>
        </button>

        <div class="h-4 w-px bg-outline-variant"></div>

        <!-- User info -->
        <div class="flex items-center gap-space-sm">
          <div class="text-right hidden sm:block">
            <div class="font-title-md text-title-md text-on-surface leading-none">超级管理员</div>
            <div class="font-label-sm text-label-sm text-on-surface-variant">运营团队管理员</div>
          </div>
          <div class="w-8 h-8 rounded-full bg-primary flex items-center justify-center">
            <span class="material-symbols-outlined text-on-primary text-[18px]">person</span>
          </div>
          <button
            class="w-8 h-8 rounded-lg flex items-center justify-center text-on-surface-variant hover:bg-error-container hover:text-on-error-container transition-all"
            title="安全登出"
            type="button"
            @click="handleLogout"
          >
            <span class="material-symbols-outlined text-[18px]">logout</span>
          </button>
        </div>
      </div>
    </div>
  </header>
</template>
