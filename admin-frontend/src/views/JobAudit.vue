<template>
  <div class="space-y-6">
    <div class="bg-white rounded-xl shadow-sm p-6">
      <div class="flex flex-wrap gap-4 mb-6">
        <input
          v-model="filters.keyword"
          type="text"
          placeholder="搜索职位名称"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          @input="loadData"
        />
        <select
          v-model="filters.auditStatus"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          @change="loadData"
        >
          <option :value="undefined">全部审核状态</option>
          <option :value="0">待审核</option>
          <option :value="1">已通过</option>
          <option :value="2">已拒绝</option>
        </select>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead>
            <tr class="border-b border-gray-200">
              <th class="text-left py-3 px-4 font-medium text-gray-600">职位名称</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">城市</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">薪资</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">审核状态</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">发布时间</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in list" :key="item.id" class="border-b border-gray-100 hover:bg-gray-50">
              <td class="py-3 px-4">
                <div>{{ item.title }}</div>
                <div class="text-xs text-gray-400">{{ item.category || '-' }}</div>
              </td>
              <td class="py-3 px-4">{{ item.city || '-' }}</td>
              <td class="py-3 px-4">{{ item.salaryMin }}-{{ item.salaryMax }}</td>
              <td class="py-3 px-4">
                <span
                  class="px-2 py-1 rounded-full text-xs"
                  :class="{
                    'bg-yellow-100 text-yellow-800': item.auditStatus === 0,
                    'bg-green-100 text-green-800': item.auditStatus === 1,
                    'bg-red-100 text-red-800': item.auditStatus === 2
                  }"
                >
                  {{ item.auditStatus === 0 ? '待审核' : item.auditStatus === 1 ? '已通过' : '已拒绝' }}
                </span>
              </td>
              <td class="py-3 px-4 text-sm text-gray-500">{{ item.createTime }}</td>
              <td class="py-3 px-4">
                <div v-if="item.auditStatus === 0" class="flex gap-2">
                  <button
                    @click="handleApprove(item.id)"
                    class="px-3 py-1 bg-green-500 text-white rounded-lg text-sm hover:bg-green-600"
                  >
                    通过
                  </button>
                  <button
                    @click="showRejectModal(item.id)"
                    class="px-3 py-1 bg-red-500 text-white rounded-lg text-sm hover:bg-red-600"
                  >
                    拒绝
                  </button>
                </div>
                <span v-else class="text-gray-400 text-sm">-</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="flex justify-between items-center mt-6">
        <span class="text-sm text-gray-500">共 {{ total }} 条记录</span>
        <div class="flex gap-2">
          <button
            @click="changePage(page - 1)"
            :disabled="page <= 1"
            class="px-3 py-1 border border-gray-300 rounded-lg text-sm disabled:opacity-50"
          >
            上一页
          </button>
          <span class="px-3 py-1 text-sm">{{ page }} / {{ totalPages }}</span>
          <button
            @click="changePage(page + 1)"
            :disabled="page >= totalPages"
            class="px-3 py-1 border border-gray-300 rounded-lg text-sm disabled:opacity-50"
          >
            下一页
          </button>
        </div>
      </div>
    </div>

    <div v-if="showReject" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-96">
        <h3 class="text-lg font-semibold mb-4">拒绝原因</h3>
        <textarea
          v-model="rejectReason"
          class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none h-32 resize-none"
          placeholder="请输入拒绝原因"
        ></textarea>
        <div class="flex justify-end gap-2 mt-4">
          <button
            @click="showReject = false"
            class="px-4 py-2 border border-gray-300 rounded-lg text-sm"
          >
            取消
          </button>
          <button
            @click="handleReject"
            class="px-4 py-2 bg-red-500 text-white rounded-lg text-sm hover:bg-red-600"
          >
            确认拒绝
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getJobAdminList, auditJob } from '@/api/job'
import type { JobAdminItem } from '@/types'

const list = ref<JobAdminItem[]>([])
const total = ref(0)
const page = ref(1)
const pageSize = ref(10)
const totalPages = ref(1)
const filters = ref<{
  keyword?: string
  auditStatus?: number
}>({})

const showReject = ref(false)
const rejectId = ref(0)
const rejectReason = ref('')

async function loadData() {
  try {
    const res = await getJobAdminList({
      ...filters.value,
      page: page.value,
      pageSize: pageSize.value
    })
    if (res.code === 200) {
      list.value = res.data.records
      total.value = res.data.total
      totalPages.value = res.data.totalPages
    }
  } catch (e) {
    console.error('加载数据失败', e)
  }
}

function changePage(p: number) {
  page.value = p
  loadData()
}

async function handleApprove(id: number) {
  try {
    await auditJob(id, { auditStatus: 1 })
    loadData()
  } catch (e) {
    console.error('操作失败', e)
  }
}

function showRejectModal(id: number) {
  rejectId.value = id
  rejectReason.value = ''
  showReject.value = true
}

async function handleReject() {
  try {
    await auditJob(rejectId.value, { auditStatus: 2, remark: rejectReason.value })
    showReject.value = false
    loadData()
  } catch (e) {
    console.error('操作失败', e)
  }
}

onMounted(loadData)
</script>
