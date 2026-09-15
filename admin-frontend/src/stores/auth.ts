import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserInfo } from '@/types'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('admin_token'))
  const user = ref<UserInfo | null>(null)

  function login(phone: string, _password: string): Promise<boolean> {
    return new Promise((resolve) => {
      setTimeout(() => {
        if (phone && _password) {
          token.value = 'mock_token_' + Date.now()
          user.value = {
            userId: 1,
            phone,
            username: '超级管理员',
            role: 2,
          }
          localStorage.setItem('admin_token', token.value)
          resolve(true)
        } else {
          resolve(false)
        }
      }, 1000)
    })
  }

  function logout() {
    token.value = null
    user.value = null
    localStorage.removeItem('admin_token')
  }

  return { token, user, login, logout }
})
