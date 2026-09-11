<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Hero Section -->
    <section class="bg-gradient-to-r from-primary to-blue-600 text-white py-16">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="max-w-3xl">
          <h1 class="text-4xl font-bold mb-4">开启你的职业梦想新征程</h1>
          <p class="text-xl text-blue-100 mb-8">
            汇聚12,000+知名企业，为高校毕业生提供一站式就业服务
          </p>
          <div class="flex gap-4">
            <router-link to="/jobs" class="px-6 py-3 bg-white text-primary font-semibold rounded-lg hover:bg-gray-100 transition-colors">
              浏览职位
            </router-link>
            <router-link to="/register" class="px-6 py-3 bg-white/10 backdrop-blur-sm text-white font-semibold rounded-lg border border-white/30 hover:bg-white/20 transition-colors">
              立即注册
            </router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- Stats Section -->
    <section class="py-12 bg-white">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-8">
          <div class="text-center">
            <div class="text-3xl font-bold text-primary mb-2">12,000+</div>
            <div class="text-gray-500">合作企业</div>
          </div>
          <div class="text-center">
            <div class="text-3xl font-bold text-primary mb-2">50,000+</div>
            <div class="text-gray-500">职位数量</div>
          </div>
          <div class="text-center">
            <div class="text-3xl font-bold text-primary mb-2">100,000+</div>
            <div class="text-gray-500">注册学生</div>
          </div>
          <div class="text-center">
            <div class="text-3xl font-bold text-primary mb-2">95%</div>
            <div class="text-gray-500">就业率</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Hot Jobs Section -->
    <section class="py-12">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="flex items-center justify-between mb-8">
          <h2 class="text-2xl font-bold text-gray-900">热门职位</h2>
          <router-link to="/jobs" class="text-primary font-semibold hover:underline flex items-center gap-1">
            查看更多
            <span class="material-symbols-outlined text-lg">arrow_forward</span>
          </router-link>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="job in hotJobs" :key="job.id" class="bg-white rounded-xl shadow-sm p-6 hover:shadow-md transition-shadow cursor-pointer" @click="router.push(`/jobs/${job.id}`)">
            <div class="flex items-start justify-between mb-4">
              <div>
                <h3 class="font-semibold text-lg text-gray-900 mb-1">{{ job.title }}</h3>
                <p class="text-gray-500 text-sm">{{ job.companyName }}</p>
              </div>
              <span class="badge badge-primary">{{ job.jobType === 1 ? '全职' : job.jobType === 2 ? '实习' : '兼职' }}</span>
            </div>
            <div class="flex flex-wrap gap-2 mb-4">
              <span class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">{{ job.city }}</span>
              <span class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">{{ job.education }}</span>
              <span class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">{{ job.experience }}</span>
            </div>
            <div class="flex items-center justify-between">
              <span class="text-primary font-bold text-lg">{{ job.salaryMin/1000 }}k-{{ job.salaryMax/1000 }}k</span>
              <span class="text-gray-400 text-sm">{{ job.createTime }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="py-12 bg-white">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <h2 class="text-2xl font-bold text-gray-900 text-center mb-12">平台特色</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="text-center p-6">
            <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <span class="material-symbols-outlined text-primary text-3xl">smart_toy</span>
            </div>
            <h3 class="font-semibold text-lg mb-2">AI智能匹配</h3>
            <p class="text-gray-500">基于简历和求职意向，智能推荐最适合的职位</p>
          </div>
          <div class="text-center p-6">
            <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <span class="material-symbols-outlined text-green-600 text-3xl">verified</span>
            </div>
            <h3 class="font-semibold text-lg mb-2">企业实名认证</h3>
            <p class="text-gray-500">所有企业经过严格审核，确保招聘信息真实可靠</p>
          </div>
          <div class="text-center p-6">
            <div class="w-16 h-16 bg-yellow-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <span class="material-symbols-outlined text-yellow-600 text-3xl">speed</span>
            </div>
            <h3 class="font-semibold text-lg mb-2">一键投递</h3>
            <p class="text-gray-500">简历一键投递，实时跟踪投递状态和面试安排</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { searchJobs } from '../api/job'

const router = useRouter()
const hotJobs = ref([])

onMounted(async () => {
  try {
    const res = await searchJobs({ pageNum: 1, pageSize: 6 })
    if (res.code === 200) {
      hotJobs.value = res.data.records || []
    }
  } catch (error) {
    console.error('Failed to fetch hot jobs:', error)
  }
})
</script>
