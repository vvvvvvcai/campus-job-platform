<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-5xl mx-auto px-6 py-8">
      <!-- Header -->
      <div class="flex items-center justify-between mb-8">
        <div>
          <h1 class="text-2xl font-bold text-[var(--on-surface)]">收藏职位</h1>
          <p class="text-sm text-[var(--on-surface-variant)] mt-1">管理你感兴趣的职位</p>
        </div>
        <div class="flex items-center gap-3">
          <div v-if="selectedIds.length > 0" class="flex items-center gap-2">
            <span class="text-xs text-[var(--on-surface-variant)]">已选 {{ selectedIds.length }} 项</span>
            <button @click="batchApply" class="px-4 py-2 bg-[var(--primary)] text-white rounded-xl text-xs font-medium hover:bg-[var(--primary-container)] transition-colors">
              批量投递
            </button>
            <button @click="batchRemove" class="px-4 py-2 border border-red-200 text-red-500 rounded-xl text-xs font-medium hover:bg-red-50 transition-colors">
              批量取消
            </button>
          </div>
        </div>
      </div>

      <!-- Filter Tabs & Search -->
      <div class="flex items-center justify-between mb-6">
        <div class="flex bg-[var(--surface-container-low)] rounded-xl p-1">
          <button v-for="tab in filterTabs" :key="tab.key" @click="activeFilter = tab.key"
            :class="['px-4 py-2 rounded-lg text-xs font-medium transition-all',
              activeFilter === tab.key ? 'bg-white text-[var(--primary)] shadow-sm' : 'text-[var(--on-surface-variant)]']">
            {{ tab.label }}
          </button>
        </div>
        <div class="relative">
          <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-[var(--on-surface-variant)] text-lg">search</span>
          <input v-model="searchQuery" type="text" placeholder="搜索收藏职位..."
            class="pl-10 pr-4 py-2 rounded-xl border border-gray-200 text-xs bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10 w-56" />
        </div>
      </div>

      <!-- Select All -->
      <div class="flex items-center gap-3 mb-4">
        <label class="flex items-center gap-2 cursor-pointer">
          <input type="checkbox" :checked="isAllSelected" @change="toggleSelectAll"
            class="w-4 h-4 rounded border-gray-300 text-[var(--primary)] focus:ring-[var(--primary)]" />
          <span class="text-xs text-[var(--on-surface-variant)]">全选</span>
        </label>
      </div>

      <!-- Job Cards -->
      <div class="space-y-4">
        <div v-for="job in filteredJobs" :key="job.id"
          :class="['bg-white rounded-2xl border shadow-sm p-5 transition-all',
            selectedIds.includes(job.id) ? 'border-[var(--primary)] ring-1 ring-[var(--primary)]/10' : 'border-gray-100 hover:border-gray-200']">
          <div class="flex items-start gap-4">
            <input type="checkbox" :checked="selectedIds.includes(job.id)" @change="toggleSelect(job.id)"
              class="w-4 h-4 mt-1 rounded border-gray-300 text-[var(--primary)] focus:ring-[var(--primary)]" />
            <div :class="['w-12 h-12 rounded-xl flex items-center justify-center text-white font-bold text-lg shrink-0', job.logoBg]">
              {{ job.logo }}
            </div>
            <div class="flex-1 min-w-0">
              <div class="flex items-start justify-between">
                <div>
                  <h3 class="font-bold text-[var(--on-surface)] text-sm">{{ job.title }}</h3>
                  <p class="text-xs text-[var(--on-surface-variant)] mt-0.5">{{ job.company }}</p>
                </div>
                <div class="flex items-center gap-2">
                  <span class="text-[var(--primary)] font-bold text-sm">{{ job.salary }}</span>
                  <button @click="removeFavorite(job.id)" class="text-[var(--on-surface-variant)] hover:text-red-500 transition-colors">
                    <span class="material-symbols-outlined text-lg">favorite</span>
                  </button>
                </div>
              </div>
              <div class="flex items-center gap-3 mt-2">
                <span class="inline-flex items-center gap-1 text-xs text-[var(--on-surface-variant)]">
                  <span class="material-symbols-outlined text-sm">location_on</span>{{ job.location }}
                </span>
                <span class="inline-flex items-center gap-1 text-xs text-[var(--on-surface-variant)]">
                  <span class="material-symbols-outlined text-sm">business</span>{{ job.experience }}
                </span>
                <span class="inline-flex items-center gap-1 text-xs text-[var(--on-surface-variant)]">
                  <span class="material-symbols-outlined text-sm">school</span>{{ job.education }}
                </span>
              </div>
              <div class="flex items-center justify-between mt-3">
                <div class="flex flex-wrap gap-1.5">
                  <span v-for="tag in job.tags" :key="tag"
                    class="px-2 py-0.5 rounded-md text-xs bg-[var(--surface-container-low)] text-[var(--on-surface-variant)]">{{ tag }}</span>
                </div>
                <div class="flex items-center gap-2">
                  <span :class="['px-2 py-0.5 rounded-full text-[10px] font-medium',
                    job.status === 'open' ? 'bg-emerald-50 text-emerald-600' : 'bg-gray-100 text-gray-500']">
                    {{ job.status === 'open' ? '投递开放中' : '已招满' }}
                  </span>
                  <span class="text-[10px] text-[var(--on-surface-variant)]">收藏于 {{ job.savedAt }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="filteredJobs.length === 0" class="bg-white rounded-2xl border border-gray-100 shadow-sm p-12 text-center">
        <span class="material-symbols-outlined text-5xl text-gray-200 mb-4 block">bookmark_border</span>
        <p class="text-sm text-[var(--on-surface-variant)]">{{ searchQuery ? '没有找到匹配的收藏职位' : '暂无收藏职位' }}</p>
        <router-link to="/jobs" class="mt-4 inline-block px-5 py-2 bg-[var(--primary)] text-white rounded-xl text-sm font-medium hover:bg-[var(--primary-container)] transition-colors">
          去发现职位
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const activeFilter = ref('all')
const searchQuery = ref('')
const selectedIds = ref([])

const filterTabs = [
  { key: 'all', label: '全部' },
  { key: 'open', label: '投递开放中' },
  { key: 'full', label: '已招满' }
]

const jobs = ref([
  { id: 1, title: '高级前端开发工程师', company: '字节跳动', logo: '字', logoBg: 'bg-gradient-to-br from-blue-500 to-blue-600', salary: '25k-40k', location: '北京', experience: '3-5年', education: '本科', tags: ['Vue.js', 'TypeScript', 'React'], status: 'open', savedAt: '09-12' },
  { id: 2, title: 'Java后端开发', company: '阿里巴巴', logo: '阿', logoBg: 'bg-gradient-to-br from-orange-500 to-red-500', salary: '20k-35k', location: '杭州', experience: '1-3年', education: '本科', tags: ['Java', 'Spring', '微服务'], status: 'open', savedAt: '09-10' },
  { id: 3, title: '产品经理', company: '腾讯', logo: '腾', logoBg: 'bg-gradient-to-br from-emerald-500 to-teal-500', salary: '22k-38k', location: '深圳', experience: '2-4年', education: '本科', tags: ['B端产品', '数据分析', 'Axure'], status: 'open', savedAt: '09-08' },
  { id: 4, title: '算法工程师', company: '美团', logo: '美', logoBg: 'bg-gradient-to-br from-yellow-500 to-amber-500', salary: '30k-50k', location: '北京', experience: '3-5年', education: '硕士', tags: ['机器学习', 'Python', 'TensorFlow'], status: 'full', savedAt: '09-05' },
  { id: 5, title: 'UI/UX设计师', company: '网易', logo: '网', logoBg: 'bg-gradient-to-br from-red-500 to-rose-500', salary: '15k-25k', location: '广州', experience: '1-3年', education: '本科', tags: ['Figma', 'Sketch', '设计系统'], status: 'open', savedAt: '09-03' },
  { id: 6, title: '数据分析师', company: '京东', logo: '京', logoBg: 'bg-gradient-to-br from-red-600 to-red-700', salary: '18k-30k', location: '北京', experience: '1-3年', education: '本科', tags: ['SQL', 'Python', 'Tableau'], status: 'full', savedAt: '09-01' }
])

const filteredJobs = computed(() => {
  let result = jobs.value
  if (activeFilter.value !== 'all') result = result.filter(j => j.status === activeFilter.value)
  if (searchQuery.value) {
    const q = searchQuery.value.toLowerCase()
    result = result.filter(j => j.title.toLowerCase().includes(q) || j.company.toLowerCase().includes(q))
  }
  return result
})

const isAllSelected = computed(() => filteredJobs.value.length > 0 && filteredJobs.value.every(j => selectedIds.value.includes(j.id)))

function toggleSelect(id) {
  const idx = selectedIds.value.indexOf(id)
  if (idx >= 0) selectedIds.value.splice(idx, 1); else selectedIds.value.push(id)
}

function toggleSelectAll() {
  if (isAllSelected.value) selectedIds.value = []
  else selectedIds.value = filteredJobs.value.map(j => j.id)
}

function removeFavorite(id) {
  jobs.value = jobs.value.filter(j => j.id !== id)
  selectedIds.value = selectedIds.value.filter(i => i !== id)
}

function batchApply() { selectedIds.value = [] }
function batchRemove() {
  jobs.value = jobs.value.filter(j => !selectedIds.value.includes(j.id))
  selectedIds.value = []
}
</script>
