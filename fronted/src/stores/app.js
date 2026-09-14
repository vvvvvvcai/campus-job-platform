import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  const user = ref(null)
  const isLoggedIn = ref(false)
  const role = ref('student')

  function login(userData, userRole = 'student') {
    user.value = userData
    isLoggedIn.value = true
    role.value = userRole
    localStorage.setItem('campus_login', 'true')
    localStorage.setItem('campus_user', JSON.stringify(userData))
    localStorage.setItem('campus_role', userRole)
  }

  function logout() {
    user.value = null
    isLoggedIn.value = false
    role.value = 'student'
    localStorage.removeItem('campus_login')
    localStorage.removeItem('campus_user')
    localStorage.removeItem('campus_role')
  }

  function restoreLogin() {
    const saved = localStorage.getItem('campus_login')
    if (saved === 'true') {
      isLoggedIn.value = true
      try { user.value = JSON.parse(localStorage.getItem('campus_user')) } catch { user.value = { name: '用户' } }
      role.value = localStorage.getItem('campus_role') || 'student'
    }
  }

  return { user, isLoggedIn, role, login, logout, restoreLogin }
})
