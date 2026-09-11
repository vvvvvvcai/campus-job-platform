<template>
  <header class="w-full bg-white shadow-sm sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
      <div class="flex items-center justify-between h-16">
        <!-- Logo -->
        <router-link to="/" class="flex items-center gap-2">
          <div class="w-9 h-9 rounded-lg bg-primary flex items-center justify-center">
            <span class="material-symbols-outlined text-white text-xl">school</span>
          </div>
          <span class="font-bold text-lg text-gray-900 hidden sm:block">校园就业综合服务平台</span>
        </router-link>

        <!-- Navigation -->
        <nav class="hidden md:flex items-center gap-6">
          <router-link to="/home" class="text-gray-600 hover:text-primary font-medium transition-colors">
            首页
          </router-link>
          <router-link to="/jobs" class="text-gray-600 hover:text-primary font-medium transition-colors">
            职位搜索
          </router-link>
          <router-link v-if="userStore.isLoggedIn" to="/recommend" class="text-gray-600 hover:text-primary font-medium transition-colors">
            推荐职位
          </router-link>
        </nav>

        <!-- User Actions -->
        <div class="flex items-center gap-3">
          <template v-if="userStore.isLoggedIn">
            <el-dropdown trigger="click">
              <div class="flex items-center gap-2 cursor-pointer">
                <el-avatar :size="32" class="bg-primary">
                  {{ userStore.userInfo?.username?.charAt(0) || 'U' }}
                </el-avatar>
                <span class="hidden sm:block text-sm font-medium text-gray-700">
                  {{ userStore.userInfo?.username }}
                </span>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="router.push('/profile')">
                    <span class="material-symbols-outlined text-sm mr-2">person</span>
                    个人中心
                  </el-dropdown-item>
                  <el-dropdown-item @click="router.push('/applications')">
                    <span class="material-symbols-outlined text-sm mr-2">description</span>
                    我的投递
                  </el-dropdown-item>
                  <el-dropdown-item @click="router.push('/favorites')">
                    <span class="material-symbols-outlined text-sm mr-2">favorite</span>
                    我的收藏
                  </el-dropdown-item>
                  <el-dropdown-item divided @click="userStore.logout()">
                    <span class="material-symbols-outlined text-sm mr-2">logout</span>
                    退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <router-link to="/login" class="btn-secondary text-sm">
              登录
            </router-link>
            <router-link to="/register" class="btn-primary text-sm">
              注册
            </router-link>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'

const router = useRouter()
const userStore = useUserStore()
</script>
