<template>
  <div class="space-y-6">
    <!-- 统计卡片 -->
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

    <!-- 近7天趋势 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <!-- 用户注册趋势 -->
      <div class="bg-white rounded-xl shadow-sm p-6">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-lg font-semibold text-gray-800">近7天用户注册趋势</h3>
          <div class="flex items-center gap-2 text-sm text-gray-500">
            <span class="w-3 h-3 bg-blue-500 rounded-sm"></span>
            <span>注册人数</span>
          </div>
        </div>
        <div class="flex items-end justify-between h-40 gap-2">
          <div
            v-for="item in userTrend"
            :key="item.date"
            class="flex-1 flex flex-col items-center gap-2"
          >
            <span class="text-xs text-gray-600 font-medium">{{ item.count }}</span>
            <div class="w-full flex justify-center">
              <div
                class="w-8 bg-blue-500 rounded-t-md transition-all duration-500 hover:bg-blue-600"
                :style="{ height: getUserBarHeight(item.count) }"
              ></div>
            </div>
            <span class="text-xs text-gray-500">{{ item.date }}</span>
          </div>
        </div>
      </div>

      <!-- 职位发布趋势 -->
      <div class="bg-white rounded-xl shadow-sm p-6">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-lg font-semibold text-gray-800">近7天职位发布趋势</h3>
          <div class="flex items-center gap-2 text-sm text-gray-500">
            <span class="w-3 h-3 bg-green-500 rounded-sm"></span>
            <span>发布数量</span>
          </div>
        </div>
        <div class="flex items-end justify-between h-40 gap-2">
          <div
            v-for="item in jobTrend"
            :key="item.date"
            class="flex-1 flex flex-col items-center gap-2"
          >
            <span class="text-xs text-gray-600 font-medium">{{ item.count }}</span>
            <div class="w-full flex justify-center">
              <div
                class="w-8 bg-green-500 rounded-t-md transition-all duration-500 hover:bg-green-600"
                :style="{ height: getJobBarHeight(item.count) }"
              ></div>
            </div>
            <span class="text-xs text-gray-500">{{ item.date }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 快捷操作 -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <router-link
        to="/enterprise-audit"
        class="bg-white rounded-xl shadow-sm p-6 hover:shadow-md transition-shadow cursor-pointer group"
      >
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-4">
            <div class="w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center group-hover:bg-yellow-200 transition-colors">
              <Building2 :size="24" class="text-yellow-600" />
            </div>
            <div>
              <p class="text-sm text-gray-500">待审核企业</p>
              <p class="text-2xl font-bold text-gray-800">{{ pendingCompanies }}</p>
            </div>
          </div>
          <div class="text-gray-400 group-hover:text-gray-600 transition-colors">
            <ChevronRight :size="24" />
          </div>
        </div>
        <p class="mt-3 text-sm text-gray-500">点击查看待审核的企业资质认证</p>
      </router-link>

      <router-link
        to="/job-audit"
        class="bg-white rounded-xl shadow-sm p-6 hover:shadow-md transition-shadow cursor-pointer group"
      >
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-4">
            <div class="w-12 h-12 bg-purple-100 rounded-full flex items-center justify-center group-hover:bg-purple-200 transition-colors">
              <FileCheck :size="24" class="text-purple-600" />
            </div>
            <div>
              <p class="text-sm text-gray-500">待审核职位</p>
              <p class="text-2xl font-bold text-gray-800">{{ jobCount?.byAuditStatus?.pending || 0 }}</p>
            </div>
          </div>
          <div class="text-gray-400 group-hover:text-gray-600 transition-colors">
            <ChevronRight :size="24" />
          </div>
        </div>
        <p class="mt-3 text-sm text-gray-500">点击查看待审核的职位信息</p>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Users, Briefcase, Clock, ShieldOff, Building2, FileCheck, ChevronRight } from 'lucide-vue-next'
import { getUserCount, getUserTrend } from '@/api/user'
import { getJobCount, getJobTrend } from '@/api/job'
import { getCompanyList } from '@/api/company'
import type { UserCount, JobCount, TrendItem } from '@/types'

const userCount = ref<UserCount | null>(null)
const jobCount = ref<JobCount | null>(null)
const pendingCompanies = ref(0)
const userTrend = ref<TrendItem[]>([])
const jobTrend = ref<TrendItem[]>([])

const maxUserCount = ref(1)
const maxJobCount = ref(1)

function getUserBarHeight(count: number): string {
  if (maxUserCount.value === 0) return '8px'
  const height = Math.max(8, (count / maxUserCount.value) * 120)
  return `${height}px`
}

function getJobBarHeight(count: number): string {
  if (maxJobCount.value === 0) return '8px'
  const height = Math.max(8, (count / maxJobCount.value) * 120)
  return `${height}px`
}

onMounted(async () => {
  try {
    const [userRes, jobRes, companyRes, userTrendRes, jobTrendRes] = await Promise.all([
      getUserCount(),
      getJobCount(),
      getCompanyList({ auditStatus: 0, pageSize: 1 }),
      getUserTrend(),
      getJobTrend()
    ])
    if (userRes.code === 200) userCount.value = userRes.data
    if (jobRes.code === 200) jobCount.value = jobRes.data
    if (companyRes.code === 200) pendingCompanies.value = companyRes.data.total
    if (userTrendRes.code === 200) {
      userTrend.value = userTrendRes.data
      maxUserCount.value = Math.max(1, ...userTrendRes.data.map((item: TrendItem) => item.count))
    }
    if (jobTrendRes.code === 200) {
      jobTrend.value = jobTrendRes.data
      maxJobCount.value = Math.max(1, ...jobTrendRes.data.map((item: TrendItem) => item.count))
    }
  } catch (e) {
    console.error('加载数据失败', e)
  }
})
</script>
