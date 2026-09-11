<template>
  <div class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
      <!-- Breadcrumb -->
      <div class="flex items-center gap-2 text-sm text-gray-500 mb-6">
        <router-link to="/home" class="hover:text-primary">首页</router-link>
        <span class="material-symbols-outlined text-sm">chevron_right</span>
        <span class="text-gray-900 font-medium">个人中心</span>
      </div>

      <!-- Profile Header -->
      <section class="bg-white rounded-xl shadow-sm p-6 lg:p-8 mb-6">
        <div class="flex flex-col lg:flex-row items-start lg:items-center justify-between gap-6">
          <div class="flex items-start gap-6">
            <div class="relative">
              <el-avatar :size="96" class="bg-primary text-white text-3xl">
                {{ userInfo?.username?.charAt(0) || 'U' }}
              </el-avatar>
              <button class="absolute bottom-0 right-0 w-8 h-8 rounded-full bg-primary text-white flex items-center justify-center shadow-md hover:bg-blue-600 transition-colors">
                <span class="material-symbols-outlined text-lg">photo_camera</span>
              </button>
            </div>
            <div>
              <div class="flex items-center gap-3 mb-2">
                <h1 class="text-2xl font-bold text-gray-900">{{ userInfo?.username || '用户' }}</h1>
                <span class="badge badge-primary">
                  <span class="material-symbols-outlined text-xs mr-1">school</span>
                  2025届毕业生
                </span>
              </div>
              <p class="text-gray-500 mb-3">期望从事全栈开发/大模型算法研发实习</p>
              <div class="flex items-center gap-2">
                <span class="w-2.5 h-2.5 rounded-full bg-green-500 animate-pulse"></span>
                <span class="text-sm font-medium text-gray-700">正在找实习/校招（随时到岗）</span>
              </div>
            </div>
          </div>
          <div class="flex gap-3">
            <button class="btn-primary flex items-center gap-2">
              <span class="material-symbols-outlined text-lg">edit_note</span>
              编辑资料
            </button>
            <button class="btn-secondary flex items-center gap-2">
              <span class="material-symbols-outlined text-lg">picture_as_pdf</span>
              简历导出
            </button>
          </div>
        </div>
      </section>

      <!-- Main Content -->
      <div class="grid grid-cols-1 lg:grid-cols-12 gap-6">
        <!-- Left Column -->
        <div class="lg:col-span-8 space-y-6">
          <!-- Basic Info -->
          <article class="card">
            <div class="flex items-center justify-between pb-4 mb-4 border-b">
              <div class="flex items-center gap-3">
                <div class="w-8 h-8 rounded-lg bg-blue-100 text-primary flex items-center justify-center">
                  <span class="material-symbols-outlined text-xl">badge</span>
                </div>
                <h2 class="font-bold text-lg">基本信息</h2>
              </div>
              <button class="text-primary font-semibold text-sm hover:underline flex items-center gap-1">
                <span class="material-symbols-outlined text-sm">edit</span>
                修改
              </button>
            </div>
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
              <div>
                <span class="text-xs text-gray-500 block mb-1 uppercase tracking-wider">真实姓名</span>
                <span class="font-semibold text-gray-900">{{ userInfo?.realName || '未设置' }}</span>
              </div>
              <div>
                <span class="text-xs text-gray-500 block mb-1 uppercase tracking-wider">性别</span>
                <span class="text-gray-700">{{ userInfo?.gender === 1 ? '男' : userInfo?.gender === 2 ? '女' : '未设置' }}</span>
              </div>
              <div>
                <span class="text-xs text-gray-500 block mb-1 uppercase tracking-wider">联系手机</span>
                <div class="flex items-center gap-1.5">
                  <span class="text-gray-700">{{ maskPhone(userInfo?.phone) }}</span>
                  <span class="material-symbols-outlined text-green-500 text-sm">check_circle</span>
                </div>
              </div>
              <div>
                <span class="text-xs text-gray-500 block mb-1 uppercase tracking-wider">电子邮箱</span>
                <span class="text-gray-700">{{ userInfo?.email || '未设置' }}</span>
              </div>
            </div>
          </article>

          <!-- Job Preference -->
          <article class="card">
            <div class="flex items-center justify-between pb-4 mb-4 border-b">
              <div class="flex items-center gap-3">
                <div class="w-8 h-8 rounded-lg bg-green-100 text-green-600 flex items-center justify-center">
                  <span class="material-symbols-outlined text-xl">explore</span>
                </div>
                <h2 class="font-bold text-lg">求职意向</h2>
              </div>
              <button class="text-primary font-semibold text-sm hover:underline flex items-center gap-1">
                <span class="material-symbols-outlined text-sm">tune</span>
                设置偏好
              </button>
            </div>
            <div class="space-y-4">
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div class="bg-gray-50 p-4 rounded-lg">
                  <span class="text-xs text-gray-500 block mb-1">期望工作性质</span>
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-primary text-lg">work_history</span>
                    <span class="font-semibold">全职应届 / 实习</span>
                  </div>
                </div>
                <div class="bg-gray-50 p-4 rounded-lg">
                  <span class="text-xs text-gray-500 block mb-1">期望薪资范围</span>
                  <div class="flex items-center gap-2">
                    <span class="material-symbols-outlined text-yellow-600 text-lg">payments</span>
                    <span class="font-bold text-yellow-600">15k - 25k/月</span>
                  </div>
                </div>
              </div>
              <div>
                <span class="text-xs text-gray-500">期望工作地点</span>
                <div class="flex flex-wrap gap-2 mt-2">
                  <span class="px-3 py-1 bg-gray-100 rounded-full text-sm flex items-center gap-1">
                    <span class="material-symbols-outlined text-primary text-sm">pin_drop</span>
                    北京
                  </span>
                  <span class="px-3 py-1 bg-gray-100 rounded-full text-sm flex items-center gap-1">
                    <span class="material-symbols-outlined text-primary text-sm">pin_drop</span>
                    上海
                  </span>
                  <span class="px-3 py-1 bg-gray-100 rounded-full text-sm flex items-center gap-1">
                    <span class="material-symbols-outlined text-primary text-sm">pin_drop</span>
                    深圳
                  </span>
                </div>
              </div>
            </div>
          </article>
        </div>

        <!-- Right Column -->
        <aside class="lg:col-span-4 space-y-6">
          <!-- Application Stats -->
          <div class="card">
            <h3 class="font-bold text-lg mb-4 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">analytics</span>
              我的求职概览
            </h3>
            <div class="grid grid-cols-2 gap-4 mb-4">
              <div class="bg-gray-50 p-4 rounded-lg text-center">
                <span class="text-xs text-gray-500 block mb-1">已投递简历</span>
                <div class="text-2xl font-bold text-gray-900">12<span class="text-sm font-normal text-gray-500">次</span></div>
              </div>
              <div class="bg-gray-50 p-4 rounded-lg text-center">
                <span class="text-xs text-gray-500 block mb-1">面试邀请</span>
                <div class="text-2xl font-bold text-primary">3<span class="text-sm font-normal text-primary">场</span></div>
              </div>
            </div>
          </div>

          <!-- Quick Actions -->
          <div class="card">
            <h3 class="font-bold text-lg mb-4 flex items-center gap-2">
              <span class="material-symbols-outlined text-primary">apps</span>
              快捷服务中心
            </h3>
            <div class="space-y-2">
              <router-link to="/resume" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 transition-colors group">
                <div class="flex items-center gap-3">
                  <div class="w-10 h-10 rounded-lg bg-blue-100 text-primary flex items-center justify-center">
                    <span class="material-symbols-outlined">description</span>
                  </div>
                  <div>
                    <span class="font-semibold text-gray-900 group-hover:text-primary block">我的简历</span>
                    <span class="text-xs text-gray-500">管理个人简历信息</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-gray-400 group-hover:translate-x-1 transition-transform">chevron_right</span>
              </router-link>
              <router-link to="/applications" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 transition-colors group">
                <div class="flex items-center gap-3">
                  <div class="w-10 h-10 rounded-lg bg-green-100 text-green-600 flex items-center justify-center">
                    <span class="material-symbols-outlined">send</span>
                  </div>
                  <div>
                    <span class="font-semibold text-gray-900 group-hover:text-primary block">投递记录</span>
                    <span class="text-xs text-gray-500">查看投递状态</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-gray-400 group-hover:translate-x-1 transition-transform">chevron_right</span>
              </router-link>
              <router-link to="/favorites" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 transition-colors group">
                <div class="flex items-center gap-3">
                  <div class="w-10 h-10 rounded-lg bg-red-100 text-red-600 flex items-center justify-center">
                    <span class="material-symbols-outlined">favorite</span>
                  </div>
                  <div>
                    <span class="font-semibold text-gray-900 group-hover:text-primary block">我的收藏</span>
                    <span class="text-xs text-gray-500">收藏的职位列表</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-gray-400 group-hover:translate-x-1 transition-transform">chevron_right</span>
              </router-link>
              <router-link to="/change-password" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 transition-colors group">
                <div class="flex items-center gap-3">
                  <div class="w-10 h-10 rounded-lg bg-yellow-100 text-yellow-600 flex items-center justify-center">
                    <span class="material-symbols-outlined">lock</span>
                  </div>
                  <div>
                    <span class="font-semibold text-gray-900 group-hover:text-primary block">修改密码</span>
                    <span class="text-xs text-gray-500">账号安全设置</span>
                  </div>
                </div>
                <span class="material-symbols-outlined text-gray-400 group-hover:translate-x-1 transition-transform">chevron_right</span>
              </router-link>
            </div>
          </div>
        </aside>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../../stores/user'

const userStore = useUserStore()
const userInfo = ref(null)

const maskPhone = (phone) => {
  if (!phone) return '未设置'
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

onMounted(async () => {
  await userStore.fetchUserInfo()
  userInfo.value = userStore.userInfo
})
</script>
