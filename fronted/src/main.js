import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import { createPinia } from 'pinia'
import App from './App.vue'
import './style.css'

const routes = [
  { path: '/', name: 'Home', component: () => import('./views/Home.vue') },
  { path: '/login', name: 'Login', component: () => import('./views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('./views/Register.vue') },
  { path: '/profile', name: 'Profile', component: () => import('./views/Profile.vue'), meta: { requiresAuth: true } },
  { path: '/change-password', name: 'ChangePassword', component: () => import('./views/ChangePassword.vue'), meta: { requiresAuth: true } },
  { path: '/jobs', name: 'JobSearch', component: () => import('./views/JobSearch.vue'), meta: { requiresAuth: true } },
  { path: '/ai-recommend', name: 'AiRecommend', component: () => import('./views/Recommendations.vue'), meta: { requiresAuth: true } },
  { path: '/jobs/:id', name: 'JobDetail', component: () => import('./views/JobDetail.vue'), meta: { requiresAuth: true } },
  { path: '/resume/editor', name: 'ResumeEditor', component: () => import('./views/ResumeEditor.vue'), meta: { requiresAuth: true } },
  { path: '/resume/manage', name: 'ResumeManage', component: () => import('./views/ResumeManage.vue'), meta: { requiresAuth: true } },
  { path: '/applications', name: 'Applications', component: () => import('./views/Applications.vue'), meta: { requiresAuth: true } },
  { path: '/favorites', name: 'Favorites', component: () => import('./views/Favorites.vue'), meta: { requiresAuth: true } },
  { path: '/enterprise/cert', name: 'EnterpriseCert', component: () => import('./views/EnterpriseCert.vue'), meta: { requiresAuth: true, roles: ['hr'] } },
  { path: '/enterprise/dashboard', name: 'HRDashboard', component: () => import('./views/HRDashboard.vue'), meta: { requiresAuth: true, roles: ['hr'] } },
  { path: '/enterprise/jobs', name: 'EnterpriseJobs', component: () => import('./views/EnterpriseJobs.vue'), meta: { requiresAuth: true, roles: ['hr'] } },
  { path: '/enterprise/post-job', name: 'PostJob', component: () => import('./views/PostJob.vue'), meta: { requiresAuth: true, roles: ['hr'] } },
  { path: '/enterprise/candidates', name: 'Candidates', component: () => import('./views/Candidates.vue'), meta: { requiresAuth: true, roles: ['hr'] } },
  { path: '/enterprise/applications', name: 'ApplicationProcess', component: () => import('./views/ApplicationProcess.vue'), meta: { requiresAuth: true, roles: ['hr'] } },
  { path: '/chat/:toUserId', name: 'ChatRoom', component: () => import('./views/ChatRoom.vue'), meta: { requiresAuth: true } },
  { path: '/messages', name: 'MessageCenter', component: () => import('./views/MessageCenter.vue'), meta: { requiresAuth: true } },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() { return { top: 0 } }
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    const isLoggedIn = localStorage.getItem('campus_login') === 'true'
    if (!isLoggedIn) {
      next({ path: '/', query: { auth: 'login', redirect: to.fullPath } })
      return
    }
    // 角色守卫：企业端页面仅企业HR可访问
    if (to.meta.roles && !to.meta.roles.includes(localStorage.getItem('campus_role') || 'student')) {
      next('/')
      return
    }
  }
  next()
})

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.mount('#app')
