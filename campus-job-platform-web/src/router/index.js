import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/Login.vue'),
    meta: { title: '登录', requiresAuth: false }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/auth/Register.vue'),
    meta: { title: '注册', requiresAuth: false }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../components/layout/AppLayout.vue'),
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('../views/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('../views/user/Profile.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      },
      {
        path: 'change-password',
        name: 'ChangePassword',
        component: () => import('../views/user/ChangePassword.vue'),
        meta: { title: '修改密码', requiresAuth: true }
      },
      {
        path: 'company/audit',
        name: 'CompanyAudit',
        component: () => import('../views/company/CompanyAudit.vue'),
        meta: { title: '企业认证', requiresAuth: true }
      },
      {
        path: 'company/center',
        name: 'CompanyCenter',
        component: () => import('../views/company/CompanyCenter.vue'),
        meta: { title: '企业管理中心', requiresAuth: true }
      },
      {
        path: 'jobs',
        name: 'JobSearch',
        component: () => import('../views/job/JobSearch.vue'),
        meta: { title: '职位搜索' }
      },
      {
        path: 'jobs/:id',
        name: 'JobDetail',
        component: () => import('../views/job/JobDetail.vue'),
        meta: { title: '职位详情' }
      },
      {
        path: 'jobs/publish',
        name: 'JobPublish',
        component: () => import('../views/job/JobPublish.vue'),
        meta: { title: '发布职位', requiresAuth: true }
      },
      {
        path: 'company/jobs',
        name: 'JobManage',
        component: () => import('../views/job/JobManage.vue'),
        meta: { title: '职位管理', requiresAuth: true }
      },
      {
        path: 'favorites',
        name: 'FavoriteList',
        component: () => import('../views/job/FavoriteList.vue'),
        meta: { title: '我的收藏', requiresAuth: true }
      },
      {
        path: 'resume',
        name: 'ResumeManage',
        component: () => import('../views/application/ResumeManage.vue'),
        meta: { title: '我的简历', requiresAuth: true }
      },
      {
        path: 'resume/edit',
        name: 'ResumeEdit',
        component: () => import('../views/application/ResumeEdit.vue'),
        meta: { title: '简历编辑', requiresAuth: true }
      },
      {
        path: 'applications',
        name: 'ApplicationList',
        component: () => import('../views/application/ApplicationList.vue'),
        meta: { title: '投递记录', requiresAuth: true }
      },
      {
        path: 'applications/:id',
        name: 'ApplicationDetail',
        component: () => import('../views/application/ApplicationDetail.vue'),
        meta: { title: '投递详情', requiresAuth: true }
      },
      {
        path: 'company/applications',
        name: 'ReceivedApplications',
        component: () => import('../views/application/ReceivedApplications.vue'),
        meta: { title: '收到的投递', requiresAuth: true }
      },
      {
        path: 'recommend',
        name: 'RecommendJobs',
        component: () => import('../views/recommend/RecommendJobs.vue'),
        meta: { title: '推荐职位', requiresAuth: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation guard
router.beforeEach((to, from, next) => {
  // Set page title
  document.title = to.meta.title ? `${to.meta.title} - 校园就业综合服务平台` : '校园就业综合服务平台'
  
  // Check auth
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next({ name: 'Login', query: { redirect: to.fullPath } })
  } else {
    next()
  }
})

export default router
