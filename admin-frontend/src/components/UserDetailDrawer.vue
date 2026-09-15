<script setup lang="ts">
import { X, Lock, ShieldCheck } from 'lucide-vue-next'
import type { User } from '@/types'

defineProps<{
  user: User | null
}>()

const emit = defineEmits<{
  close: []
}>()
</script>

<template>
  <Teleport to="body">
    <Transition name="drawer">
      <div v-if="user" class="fixed inset-0 z-50">
        <div class="absolute inset-0 bg-inverse-surface/40 backdrop-blur-sm" @click="emit('close')"></div>
        <div class="absolute top-0 right-0 bottom-0 w-full max-w-md bg-surface-container-lowest shadow-2xl flex flex-col transition-transform duration-300">
          <div class="p-space-lg flex flex-col h-full">
            <!-- Header -->
            <div class="flex items-center justify-between pb-space-md">
              <div class="flex items-center gap-space-xs">
                <span class="material-symbols-outlined text-primary text-[22px]">badge</span>
                <span class="font-title-lg text-title-lg text-on-surface">账号档案全貌 (t_user)</span>
              </div>
              <button class="w-8 h-8 rounded-full bg-surface-container-low text-on-surface-variant flex items-center justify-center hover:bg-surface-container" @click="emit('close')">
                <X :size="18" />
              </button>
            </div>

            <!-- User Info Card -->
            <div class="mt-space-lg flex items-center gap-space-md p-space-md rounded-xl bg-surface-container-low">
              <div class="w-14 h-14 rounded-full bg-primary-container text-on-primary flex items-center justify-center font-metric-xl text-metric-xl font-bold shadow-sm">
                {{ user.avatarText }}
              </div>
              <div class="flex flex-col">
                <div class="flex items-center gap-space-xs">
                  <span class="font-title-lg text-title-lg text-on-surface">{{ user.name }}</span>
                  <span class="px-2 py-0.5 rounded-full font-label-sm text-label-sm font-medium"
                    :class="user.role === 'student' ? 'bg-primary-fixed text-primary' : 'bg-secondary-fixed text-secondary'">
                    {{ user.roleLabel }}
                  </span>
                </div>
                <div class="font-code-xs text-code-xs text-outline mt-0.5">{{ user.uid }}</div>
              </div>
            </div>

            <!-- Meta Details -->
            <div class="mt-space-lg space-y-space-md flex-1">
              <div class="flex flex-col gap-1 p-space-md rounded-lg bg-surface-container-low">
                <span class="font-label-sm text-label-sm text-outline">脱敏绑定手机（安全规则满足）</span>
                <div class="flex items-center gap-1 font-title-md text-title-md text-on-surface font-code-xs">
                  <Lock :size="14" class="text-tertiary" />
                  {{ user.maskedPhone }}
                </div>
              </div>
              <div class="flex flex-col gap-1 p-space-md rounded-lg bg-surface-container-low">
                <span class="font-label-sm text-label-sm text-outline">主体组织归属 / 身份凭据</span>
                <span class="font-body-md text-body-md text-on-surface">{{ user.affiliation }}</span>
              </div>
              <div class="flex flex-col gap-1 p-space-md rounded-lg bg-surface-container-low">
                <span class="font-label-sm text-label-sm text-outline">当前访问鉴权状态</span>
                <span class="font-title-md text-title-md"
                  :class="user.status === 'active' ? 'text-tertiary' : 'text-error'">
                  {{ user.status === 'active' ? '正常履约中' : '已禁用 (发布违规外挂言论)' }}
                </span>
              </div>
              <div class="flex flex-col gap-space-xs p-space-md rounded-lg bg-surface-container-low">
                <span class="font-label-sm text-label-sm text-outline">多端安全校验记录</span>
                <div class="flex justify-between font-body-sm text-body-sm text-on-surface-variant">
                  <span>密码重置历史：</span>
                  <span class="font-code-xs text-code-xs text-on-surface font-medium">{{ user.passwordResets }} 次管理员代重置</span>
                </div>
                <div class="flex justify-between font-body-sm text-body-sm text-on-surface-variant">
                  <span>双因子认证状态：</span>
                  <span class="font-medium" :class="user.twoFactorActive ? 'text-tertiary' : 'text-error'">
                    {{ user.twoFactorActive ? '已激活 (短信验证码)' : '未激活' }}
                  </span>
                </div>
              </div>
            </div>

            <!-- Footer -->
            <div class="pt-space-lg">
              <button class="w-full py-2 rounded-lg bg-surface-container text-on-surface font-label-md text-label-md hover:bg-surface-container-high transition-all" @click="emit('close')">
                关闭面板
              </button>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped>
.drawer-enter-active,
.drawer-leave-active {
  transition: all 0.3s ease;
}
.drawer-enter-from,
.drawer-leave-to {
  opacity: 0;
}
.drawer-enter-from .absolute:last-child,
.drawer-leave-to .absolute:last-child {
  transform: translateX(100%);
}
</style>
