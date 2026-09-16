<template>
  <aside class="w-64 bg-slate-900 text-white flex flex-col">
    <div class="p-4 border-b border-slate-700">
      <h1 class="text-lg font-bold">管理控制台</h1>
    </div>
    <nav class="flex-1 p-4">
      <router-link
        v-for="item in menuItems"
        :key="item.path"
        :to="item.path"
        class="flex items-center gap-3 px-4 py-3 rounded-lg mb-2 hover:bg-slate-700 transition-colors"
        :class="{ 'bg-slate-700': $route.path === item.path }"
      >
        <component :is="item.icon" :size="20" />
        <span>{{ item.label }}</span>
      </router-link>
    </nav>
  </aside>
</template>

<script setup lang="ts">
import { LayoutDashboard, Building2, Briefcase, Users } from 'lucide-vue-next'

const menuItems = [
  { path: '/dashboard', label: '数据看板', icon: LayoutDashboard },
  { path: '/enterprise-audit', label: '企业资质审核', icon: Building2 },
  { path: '/job-audit', label: '职位信息审核', icon: Briefcase },
  { path: '/user-management', label: '用户权限管理', icon: Users }
]
</script>
