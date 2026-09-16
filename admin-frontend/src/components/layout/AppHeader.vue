<template>
  <header class="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-6">
    <div class="flex items-center gap-4">
      <h2 class="text-lg font-semibold text-gray-800">{{ $route.meta.title || '数据看板' }}</h2>
    </div>
    <div class="flex items-center gap-4">
      <span class="text-sm text-gray-600">{{ user?.username || '管理员' }}</span>
      <button
        @click="handleLogout"
        class="px-4 py-2 text-sm text-red-600 hover:bg-red-50 rounded-lg transition-colors"
      >
        退出登录
      </button>
    </div>
  </header>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { computed } from 'vue'

const router = useRouter()
const authStore = useAuthStore()
const user = computed(() => authStore.user)

function handleLogout() {
  authStore.logout()
  router.push('/login')
}
</script>
