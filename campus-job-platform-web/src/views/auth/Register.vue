<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
    <div class="w-full max-w-6xl bg-white rounded-xl shadow-xl overflow-hidden grid grid-cols-1 lg:grid-cols-12">
      <!-- Left Section - Branding -->
      <div class="lg:col-span-5 bg-gradient-to-br from-primary via-blue-600 to-blue-700 p-8 lg:p-10 flex flex-col justify-between relative overflow-hidden text-white">
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
            <h1 class="text-3xl lg:text-4xl font-bold leading-tight">
              加入我们<br/>找到理想工作
            </h1>
            <p class="text-blue-100 leading-relaxed">
              注册成为平台用户，开启你的职业发展之旅
            </p>
          </div>
        </div>
      </div>

      <!-- Right Section - Register Form -->
      <div class="lg:col-span-7 p-8 lg:p-10">
        <div class="w-full max-w-md mx-auto space-y-6">
          <div>
            <span class="text-sm text-primary font-semibold tracking-wide uppercase">Create Account</span>
            <h2 class="text-2xl font-bold text-gray-900 mt-1">用户注册</h2>
            <p class="text-sm text-gray-500 mt-0.5">填写以下信息完成注册</p>
          </div>

          <form @submit.prevent="handleRegister" class="space-y-4">
            <!-- Phone -->
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">手机号码 *</label>
              <input
                v-model="form.phone"
                type="tel"
                placeholder="请输入11位手机号"
                required
                class="input-field"
              />
            </div>

            <!-- Username -->
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">用户名 *</label>
              <input
                v-model="form.username"
                type="text"
                placeholder="请输入用户名"
                required
                class="input-field"
              />
            </div>

            <!-- Password -->
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">登录密码 *</label>
              <input
                v-model="form.password"
                type="password"
                placeholder="请设置6-20位密码"
                required
                class="input-field"
              />
            </div>

            <!-- Confirm Password -->
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">确认密码 *</label>
              <input
                v-model="confirmPassword"
                type="password"
                placeholder="请再次输入密码"
                required
                class="input-field"
              />
            </div>

            <!-- Role -->
            <div class="space-y-1.5">
              <label class="block text-sm font-medium text-gray-700">用户类型 *</label>
              <div class="flex gap-4">
                <label class="flex items-center gap-2 cursor-pointer">
                  <input v-model="form.role" type="radio" :value="0" class="w-4 h-4 text-primary focus:ring-0"/>
                  <span class="text-sm text-gray-700">学生</span>
                </label>
                <label class="flex items-center gap-2 cursor-pointer">
                  <input v-model="form.role" type="radio" :value="1" class="w-4 h-4 text-primary focus:ring-0"/>
                  <span class="text-sm text-gray-700">企业</span>
                </label>
              </div>
            </div>

            <!-- Submit -->
            <button
              type="submit"
              :disabled="loading"
              class="w-full h-11 bg-gradient-to-r from-blue-600 to-primary text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition-all disabled:opacity-50"
            >
              {{ loading ? '注册中...' : '立即注册' }}
            </button>

            <!-- Login Link -->
            <div class="text-center text-sm text-gray-500">
              已有账号？
              <router-link to="/login" class="text-primary font-semibold hover:underline">立即登录</router-link>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const confirmPassword = ref('')
const form = reactive({
  phone: '',
  username: '',
  password: '',
  role: 0
})

const handleRegister = async () => {
  if (form.password !== confirmPassword.value) {
    ElMessage.warning('两次输入的密码不一致')
    return
  }

  loading.value = true
  try {
    const result = await userStore.registerAction(form)
    if (result.success) {
      ElMessage.success('注册成功，请登录')
      router.push('/login')
    } else {
      ElMessage.error(result.message || '注册失败')
    }
  } finally {
    loading.value = false
  }
}
</script>
