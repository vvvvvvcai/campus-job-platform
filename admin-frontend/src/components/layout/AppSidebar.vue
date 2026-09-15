<script setup lang="ts">
import { useRoute } from 'vue-router'
import logoUrl from '@/assets/images/logo.svg'

const route = useRoute()

const menuItems = [
  {
    path: '/dashboard',
    label: '数据统计与统计',
    icon: 'analytics',
    badge: null,
    tag: 'P1',
  },
  {
    path: '/enterprise-audit',
    label: '企业资质审核',
    icon: 'verified_user',
    badge: 3,
    badgeColor: 'bg-error text-on-error',
  },
  {
    path: '/job-audit',
    label: '职位信息审核',
    icon: 'fact_check',
    badge: 8,
    badgeColor: 'bg-secondary-container text-on-secondary-container',
  },
  {
    path: '/user-management',
    label: '用户权限管理',
    icon: 'manage_accounts',
    badge: null,
    tag: 'P0',
  },
]

function isActive(path: string): boolean {
  return route.path === path
}
</script>

<template>
  <aside class="fixed left-0 top-0 h-full w-64 bg-inverse-surface z-50 flex flex-col justify-between shadow-[0_1px_8px_rgba(0,0,0,0.04)]">
    <div class="flex flex-col">
      <!-- Logo -->
      <div class="h-14 px-gutter flex items-center gap-space-sm">
        <div class="w-8 h-8 rounded-lg bg-primary-container flex items-center justify-center">
          <img :src="logoUrl" class="w-5 h-5" alt="Logo" />
        </div>
        <div class="flex flex-col">
          <span class="font-title-md text-title-md text-inverse-on-surface tracking-tight leading-tight">管理控制台</span>
          <span class="font-code-xs text-code-xs text-outline-variant uppercase tracking-wider">Uni-Career Admin</span>
        </div>
      </div>

      <!-- Status -->
      <div class="px-space-md py-space-sm">
        <div class="px-space-md py-space-xs rounded bg-surface-container-highest/10 flex items-center justify-between">
          <span class="font-label-sm text-label-sm text-inverse-on-surface/80">运营状态 · 实时同步</span>
          <span class="flex h-2 w-2 relative">
            <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-tertiary-fixed-dim opacity-75"></span>
            <span class="relative inline-flex rounded-full h-2 w-2 bg-tertiary-fixed"></span>
          </span>
        </div>
      </div>

      <!-- Navigation -->
      <nav class="px-space-md space-y-space-xs">
        <router-link
          v-for="item in menuItems"
          :key="item.path"
          :to="item.path"
          class="flex items-center justify-between px-space-md py-space-sm rounded-xl transition-all group"
          :class="
            isActive(item.path)
              ? 'bg-primary-container text-on-primary font-title-md shadow-[0_1px_8px_rgba(0,0,0,0.04)]'
              : 'text-inverse-on-surface/75 hover:bg-surface-container-highest/15 hover:text-inverse-on-surface'
          "
        >
          <div class="flex items-center gap-space-sm">
            <span class="material-symbols-outlined text-[20px]">{{ item.icon }}</span>
            <span class="font-body-md text-body-md">{{ item.label }}</span>
          </div>
          <span
            v-if="item.badge"
            class="font-label-sm text-label-sm px-space-xs rounded-full font-bold"
            :class="item.badgeColor"
          >
            {{ item.badge }}
          </span>
          <span
            v-else-if="item.tag"
            class="font-code-xs text-code-xs px-space-xs rounded bg-surface-container-highest/20 text-inverse-on-surface/90"
          >
            {{ item.tag }}
          </span>
        </router-link>
      </nav>
    </div>

    <div class="p-space-md"></div>
  </aside>
</template>
