<template>
  <footer class="w-full bg-gray-900 text-white mt-auto">
    <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-12">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
        <!-- Brand -->
        <div class="space-y-4">
          <div class="flex items-center gap-2">
            <span class="material-symbols-outlined text-primary text-2xl">school</span>
            <span class="font-bold text-lg">校园就业综合服务平台</span>
          </div>
          <p class="text-gray-400 text-sm leading-relaxed">
            服务高校大学生实习求职与校企直聘的数字化一站式就业枢纽平台。
          </p>
        </div>

        <!-- Quick Links -->
        <div>
          <h4 class="font-semibold mb-4">快速链接</h4>
          <ul class="space-y-2 text-gray-400 text-sm">
            <li><a href="#" class="hover:text-white transition-colors">关于我们</a></li>
            <li><a href="#" class="hover:text-white transition-colors">联系方式</a></li>
            <li><a href="#" class="hover:text-white transition-colors">隐私保护政策</a></li>
            <li><a href="#" class="hover:text-white transition-colors">用户服务协议</a></li>
          </ul>
        </div>

        <!-- For Students -->
        <div>
          <h4 class="font-semibold mb-4">学生服务</h4>
          <ul class="space-y-2 text-gray-400 text-sm">
            <li><router-link to="/jobs" class="hover:text-white transition-colors">职位搜索</router-link></li>
            <li><router-link to="/recommend" class="hover:text-white transition-colors">智能推荐</router-link></li>
            <li><router-link to="/resume" class="hover:text-white transition-colors">简历管理</router-link></li>
            <li><router-link to="/applications" class="hover:text-white transition-colors">投递记录</router-link></li>
          </ul>
        </div>

        <!-- For Companies -->
        <div>
          <h4 class="font-semibold mb-4">企业服务</h4>
          <ul class="space-y-2 text-gray-400 text-sm">
            <li><router-link to="/company/audit" class="hover:text-white transition-colors">企业认证</router-link></li>
            <li><router-link to="/jobs/publish" class="hover:text-white transition-colors">发布职位</router-link></li>
            <li><router-link to="/company/jobs" class="hover:text-white transition-colors">职位管理</router-link></li>
            <li><router-link to="/company/applications" class="hover:text-white transition-colors">收到的投递</router-link></li>
          </ul>
        </div>
      </div>

      <div class="border-t border-gray-800 mt-8 pt-8 text-center text-gray-500 text-sm">
        <p>&copy; 2026 校园就业综合服务平台. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>
