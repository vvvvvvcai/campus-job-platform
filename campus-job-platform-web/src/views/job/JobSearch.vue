<template>
  <div class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
      <!-- Search Filters -->
      <div class="card mb-6">
        <div class="flex flex-col md:flex-row gap-4">
          <div class="flex-1">
            <div class="relative">
              <span class="material-symbols-outlined absolute left-3 top-2.5 text-gray-400">search</span>
              <input
                v-model="searchParams.keyword"
                type="text"
                placeholder="搜索职位名称、公司名称"
                class="input-field pl-10"
                @keyup.enter="handleSearch"
              />
            </div>
          </div>
          <div class="flex gap-3 flex-wrap">
            <select v-model="searchParams.city" class="input-field w-auto">
              <option value="">全部城市</option>
              <option value="北京">北京</option>
              <option value="上海">上海</option>
              <option value="深圳">深圳</option>
              <option value="杭州">杭州</option>
            </select>
            <select v-model="searchParams.jobType" class="input-field w-auto">
              <option value="">全部类型</option>
              <option :value="1">全职</option>
              <option :value="2">实习</option>
              <option :value="3">兼职</option>
            </select>
            <button @click="handleSearch" class="btn-primary">
              搜索
            </button>
          </div>
        </div>
      </div>

      <!-- Results Header -->
      <div class="flex items-center justify-between mb-4">
        <span class="text-gray-500">共找到 <span class="font-semibold text-gray-900">{{ totalCount }}</span> 个职位</span>
        <div class="flex items-center gap-2">
          <span class="text-sm text-gray-500">排序：</span>
          <select v-model="searchParams.sortBy" class="text-sm border-0 bg-transparent text-primary font-semibold focus:outline-none cursor-pointer">
            <option value="latest">最新发布</option>
            <option value="salary">薪资最高</option>
          </select>
        </div>
      </div>

      <!-- Job List -->
      <div class="space-y-4">
        <div
          v-for="job in jobList"
          :key="job.id"
          class="card cursor-pointer hover:border-primary transition-colors"
          @click="router.push(`/jobs/${job.id}`)"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center gap-3 mb-2">
                <h3 class="font-semibold text-lg text-gray-900 hover:text-primary">{{ job.title }}</h3>
                <span class="badge badge-primary">{{ job.jobType === 1 ? '全职' : job.jobType === 2 ? '实习' : '兼职' }}</span>
              </div>
              <div class="text-gray-500 text-sm mb-3">{{ job.companyName || '未知公司' }}</div>
              <div class="flex flex-wrap gap-2">
                <span class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">{{ job.city }}</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">{{ job.education }}</span>
                <span class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">{{ job.experience }}</span>
              </div>
            </div>
            <div class="text-right">
              <div class="text-primary font-bold text-lg mb-2">
                {{ job.salaryMin/1000 }}k-{{ job.salaryMax/1000 }}k
              </div>
              <button
                @click.stop="toggleFavorite(job)"
                class="text-gray-400 hover:text-red-500 transition-colors"
              >
                <span class="material-symbols-outlined">
                  {{ job.isFavorite ? 'favorite' : 'favorite_border' }}
                </span>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="jobList.length === 0 && !loading" class="text-center py-16">
        <span class="material-symbols-outlined text-6xl text-gray-300 mb-4">search_off</span>
        <p class="text-gray-500">未找到相关职位</p>
      </div>

      <!-- Pagination -->
      <div v-if="totalCount > 0" class="flex justify-center mt-8">
        <el-pagination
          v-model:current-page="searchParams.pageNum"
          :page-size="searchParams.pageSize"
          :total="totalCount"
          layout="prev, pager, next"
          @current-change="handleSearch"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useJobStore } from '../../stores/job'
import { addFavorite, removeFavorite } from '../../api/job'

const router = useRouter()
const jobStore = useJobStore()

const loading = ref(false)
const jobList = ref([])
const totalCount = ref(0)
const searchParams = reactive({
  keyword: '',
  city: '',
  jobType: '',
  pageNum: 1,
  pageSize: 10,
  sortBy: 'latest'
})

const handleSearch = async () => {
  loading.value = true
  try {
    await jobStore.searchJobsAction(searchParams)
    jobList.value = jobStore.jobList
    totalCount.value = jobStore.totalCount
  } finally {
    loading.value = false
  }
}

const toggleFavorite = async (job) => {
  try {
    if (job.isFavorite) {
      await removeFavorite(job.id)
      job.isFavorite = false
    } else {
      await addFavorite(job.id)
      job.isFavorite = true
    }
  } catch (error) {
    console.error('Failed to toggle favorite:', error)
  }
}

onMounted(() => {
  handleSearch()
})
</script>
