import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  const user = ref(null)
  const isLoggedIn = ref(false)
  const role = ref('student')
  const token = ref('')
  const userId = ref(null)

  function login(userData, userRole = 'student', userToken = '', uid = null) {
    user.value = userData
    isLoggedIn.value = true
    role.value = userRole
    token.value = userToken
    userId.value = uid
    localStorage.setItem('campus_login', 'true')
    localStorage.setItem('campus_user', JSON.stringify(userData))
    localStorage.setItem('campus_role', userRole)
    if (userToken) localStorage.setItem('campus_token', userToken)
    if (uid) localStorage.setItem('campus_user_id', String(uid))
  }

  function logout() {
    user.value = null
    isLoggedIn.value = false
    role.value = 'student'
    token.value = ''
    userId.value = null
    localStorage.removeItem('campus_login')
    localStorage.removeItem('campus_user')
    localStorage.removeItem('campus_role')
    localStorage.removeItem('campus_token')
    localStorage.removeItem('campus_user_id')
  }

  function restoreLogin() {
    const saved = localStorage.getItem('campus_login')
    if (saved === 'true') {
      isLoggedIn.value = true
      try { user.value = JSON.parse(localStorage.getItem('campus_user')) } catch { user.value = { name: '用户' } }
      role.value = localStorage.getItem('campus_role') || 'student'
      token.value = localStorage.getItem('campus_token') || ''
      userId.value = localStorage.getItem('campus_user_id') || null
    }
  }

  return { user, isLoggedIn, role, token, userId, login, logout, restoreLogin }
})
