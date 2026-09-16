<template>
  <div class="space-y-6">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div class="bg-white rounded-xl shadow-sm p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-gray-500">注册用户总数</p>
            <p class="text-3xl font-bold text-gray-800 mt-1">{{ userCount?.total || 0 }}</p>
          </div>
          <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
            <Users :size="24" class="text-blue-600" />
          </div>
        </div>
        <div class="mt-4 flex gap-4 text-sm">
          <span class="text-gray-500">学生: {{ userCount?.byRole?.student || 0 }}</span>
          <span class="text-gray-500">企业HR: {{ userCount?.byRole?.hr || 0 }}</span>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-gray-500">活跃职位数</p>
            <p class="text-3xl font-bold text-gray-800 mt-1">{{ jobCount?.total || 0 }}</p>
          </div>
          <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center">
            <Briefcase :size="24" class="text-green-600" />
          </div>
        </div>
        <div class="mt-4 flex gap-4 text-sm">
          <span class="text-gray-500">招聘中: {{ jobCount?.byStatus?.recruiting || 0 }}</span>
          <span class="text-gray-500">待审核: {{ jobCount?.byAuditStatus?.pending || 0 }}</span>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-gray-500">待审核企业</p>
            <p class="text-3xl font-bold text-gray-800 mt-1">{{ pendingCompanies }}</p>
          </div>
          <div class="w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center">
            <Clock :size="24" class="text-yellow-600" />
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-gray-500">禁用用户</p>
            <p class="text-3xl font-bold text-gray-800 mt-1">{{ userCount?.byStatus?.disabled || 0 }}</p>
          </div>
          <div class="w-12 h-12 bg-red-100 rounded-full flex items-center justify-center">
            <ShieldOff :size="24" class="text-red-600" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Users, Briefcase, Clock, ShieldOff } from 'lucide-vue-next'
import { getUserCount } from '@/api/user'
import { getJobCount } from '@/api/job'
import { getCompanyList } from '@/api/company'
import type { UserCount, JobCount } from '@/types'

const userCount = ref<UserCount | null>(null)
const jobCount = ref<JobCount | null>(null)
const pendingCompanies = ref(0)

onMounted(async () => {
  try {
    const [userRes, jobRes, companyRes] = await Promise.all([
      getUserCount(),
      getJobCount(),
      getCompanyList({ auditStatus: 0, pageSize: 1 })
    ])
    if (userRes.code === 200) userCount.value = userRes.data
    if (jobRes.code === 200) jobCount.value = jobRes.data
    if (companyRes.code === 200) pendingCompanies.value = companyRes.data.total
  } catch (e) {
    console.error('加载数据失败', e)
  }
})
</script>
