import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, register, getUserInfo } from '../api/user'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)

  const isLoggedIn = computed(() => !!token.value)
  const userId = computed(() => userInfo.value?.id)
  const role = computed(() => userInfo.value?.role)

  function setToken(newToken) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  function clearToken() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
  }

  async function loginAction(phone, password) {
    try {
      const res = await login({ phone, password })
      if (res.code === 200) {
        setToken(res.data.token)
        userInfo.value = res.data
        return { success: true }
      }
      return { success: false, message: res.message }
    } catch (error) {
      return { success: false, message: error.message || '登录失败' }
    }
  }

  async function registerAction(data) {
    try {
      const res = await register(data)
      if (res.code === 200) {
        return { success: true }
      }
      return { success: false, message: res.message }
    } catch (error) {
      return { success: false, message: error.message || '注册失败' }
    }
  }

  async function fetchUserInfo() {
    try {
      const res = await getUserInfo()
      if (res.code === 200) {
        userInfo.value = res.data
      }
    } catch (error) {
      console.error('Failed to fetch user info:', error)
    }
  }

  function logout() {
    clearToken()
    window.location.href = '/login'
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    userId,
    role,
    setToken,
    clearToken,
    loginAction,
    registerAction,
    fetchUserInfo,
    logout
  }
})
