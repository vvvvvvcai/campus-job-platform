<template>
  <div class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
      <div class="max-w-md mx-auto">
        <h1 class="text-2xl font-bold text-gray-900 mb-6">修改密码</h1>
        
        <div class="card">
          <form @submit.prevent="handleChangePassword" class="space-y-4">
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">当前密码</label>
              <input
                v-model="form.oldPassword"
                type="password"
                placeholder="请输入当前密码"
                required
                class="input-field"
              />
            </div>
            
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">新密码</label>
              <input
                v-model="form.newPassword"
                type="password"
                placeholder="请输入新密码（6-20位）"
                required
                class="input-field"
              />
            </div>
            
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">确认新密码</label>
              <input
                v-model="confirmPassword"
                type="password"
                placeholder="请再次输入新密码"
                required
                class="input-field"
              />
            </div>
            
            <button
              type="submit"
              :disabled="loading"
              class="w-full btn-primary"
            >
              {{ loading ? '提交中...' : '确认修改' }}
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { updatePassword } from '../../api/user'
import { useUserStore } from '../../stores/user'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const loading = ref(false)
const confirmPassword = ref('')
const form = reactive({
  oldPassword: '',
  newPassword: ''
})

const handleChangePassword = async () => {
  if (form.newPassword !== confirmPassword.value) {
    ElMessage.warning('两次输入的密码不一致')
    return
  }
  
  loading.value = true
  try {
    await updatePassword(form.oldPassword, form.newPassword)
    ElMessage.success('密码修改成功')
    userStore.logout()
  } catch (error) {
    ElMessage.error(error.message || '修改失败')
  } finally {
    loading.value = false
  }
}
</script>
