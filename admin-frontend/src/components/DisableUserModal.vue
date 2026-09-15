<script setup lang="ts">
import { ref } from 'vue'
import { Gavel } from 'lucide-vue-next'
import { disableReasons } from '@/mock/users'

defineProps<{
  visible: boolean
  userName: string
  uid: string
}>()

const emit = defineEmits<{
  close: []
  confirm: [reason: string, note: string]
}>()

const selectedReason = ref(disableReasons[0])
const note = ref('')

function handleConfirm() {
  emit('confirm', selectedReason.value, note.value)
  note.value = ''
}
</script>

<template>
  <Teleport to="body">
    <Transition name="modal">
      <div v-if="visible" class="fixed inset-0 z-50 flex items-center justify-center p-gutter">
        <div class="absolute inset-0 bg-inverse-surface/40 backdrop-blur-sm" @click="emit('close')"></div>
        <div class="relative bg-surface-container-lowest w-full max-w-lg rounded-xl shadow-2xl p-space-lg flex flex-col z-10">
          <!-- Header -->
          <div class="flex items-center gap-space-md">
            <div class="w-11 h-11 rounded-full bg-error-container text-on-error-container flex items-center justify-center shrink-0">
              <Gavel :size="24" />
            </div>
            <div class="flex flex-col">
              <span class="font-title-lg text-title-lg text-on-surface">账号安全限制：禁用确认</span>
              <span class="font-body-sm text-body-sm text-outline">操作将即时终止该账号的所有合法 Token 会话</span>
            </div>
          </div>

          <!-- Warning -->
          <div class="mt-space-md p-space-md rounded-lg bg-error-container/20 text-error font-body-sm text-body-sm">
            您正准备对用户 <strong class="font-bold">{{ userName }}</strong> (<span class="font-code-xs">{{ uid }}</span>) 执行风控禁用！
          </div>

          <!-- Reason -->
          <div class="mt-space-md flex flex-col gap-space-xs">
            <label class="font-label-sm text-label-sm text-on-surface font-medium">请选择禁用原因并记录审计日志：</label>
            <select v-model="selectedReason" class="w-full h-10 px-space-md bg-surface-container-low text-on-surface font-body-sm text-body-sm rounded-lg focus:outline-none">
              <option v-for="reason in disableReasons" :key="reason" :value="reason">{{ reason }}</option>
            </select>
            <textarea
              v-model="note"
              class="mt-space-xs w-full p-space-sm bg-surface-container-low text-on-surface font-body-sm text-body-sm rounded-lg focus:outline-none resize-none"
              placeholder="补充详细封禁说明，将记录于系统审计库 (可选)..."
              rows="3"
            ></textarea>
          </div>

          <!-- Actions -->
          <div class="mt-space-lg flex items-center justify-end gap-space-sm">
            <button class="px-space-md py-2 rounded-lg bg-surface-container text-on-surface font-label-md text-label-md hover:bg-surface-container-high transition-all" @click="emit('close')">
              取消
            </button>
            <button class="px-space-md py-2 rounded-lg bg-error text-on-error font-label-md text-label-md shadow-sm hover:opacity-90 transition-all" @click="handleConfirm">
              确认立即封禁
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: all 0.2s ease;
}
.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}
.modal-enter-from > div:last-child,
.modal-leave-to > div:last-child {
  transform: scale(0.95);
}
</style>
