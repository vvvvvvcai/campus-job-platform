import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as apiLogin, getUserInfo } from '@/api/auth'
import type { UserInfo } from '@/types'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('admin_token') || '')
  const user = ref<UserInfo | null>(
    JSON.parse(localStorage.getItem('admin_user') || 'null')
  )

  async function login(phone: string, password: string) {
    const res = await apiLogin({ phone, password })
    if (res.code === 200 && res.data) {
      token.value = res.data.token
      user.value = {
        userId: res.data.userId,
        phone: res.data.phone,
        username: res.data.username,
        role: res.data.role,
        avatar: res.data.avatar
      }
      localStorage.setItem('admin_token', res.data.token)
      localStorage.setItem('admin_user', JSON.stringify(user.value))
      return true
    }
    return false
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('admin_token')
    localStorage.removeItem('admin_user')
  }

  return { token, user, login, logout }
})
