<template>
  <div class="space-y-6">
    <div class="bg-white rounded-xl shadow-sm p-6">
      <div class="flex flex-wrap gap-4 mb-6">
        <input
          v-model="filters.keyword"
          type="text"
          placeholder="搜索用户名/手机号"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          @input="loadData"
        />
        <select
          v-model="filters.role"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          @change="loadData"
        >
          <option :value="undefined">全部角色</option>
          <option :value="0">学生</option>
          <option :value="1">企业HR</option>
          <option :value="2">管理员</option>
        </select>
        <select
          v-model="filters.status"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          @change="loadData"
        >
          <option :value="undefined">全部状态</option>
          <option :value="1">正常</option>
          <option :value="0">禁用</option>
        </select>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead>
            <tr class="border-b border-gray-200">
              <th class="text-left py-3 px-4 font-medium text-gray-600">用户</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">角色</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">手机号</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">注册时间</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">状态</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in list" :key="item.id" class="border-b border-gray-100 hover:bg-gray-50">
              <td class="py-3 px-4">
                <div class="flex items-center gap-3">
                  <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-medium">
                    {{ item.username?.charAt(0) || 'U' }}
                  </div>
                  <div>
                    <div class="font-medium">{{ item.username }}</div>
                    <div class="text-xs text-gray-400">{{ item.email || '-' }}</div>
                  </div>
                </div>
              </td>
              <td class="py-3 px-4">
                <span class="text-sm">{{ item.roleLabel }}</span>
              </td>
              <td class="py-3 px-4">{{ item.phone }}</td>
              <td class="py-3 px-4 text-sm text-gray-500">{{ item.createTime }}</td>
              <td class="py-3 px-4">
                <span
                  class="px-2 py-1 rounded-full text-xs"
                  :class="{
                    'bg-green-100 text-green-800': item.status === 1,
                    'bg-red-100 text-red-800': item.status === 0
                  }"
                >
                  {{ item.statusLabel }}
                </span>
              </td>
              <td class="py-3 px-4">
                <div class="flex gap-2">
                  <button
                    v-if="item.status === 1 && item.role !== 2"
                    @click="handleDisable(item.id)"
                    class="px-3 py-1 bg-red-500 text-white rounded-lg text-sm hover:bg-red-600"
                  >
                    禁用
                  </button>
                  <button
                    v-if="item.status === 0"
                    @click="handleEnable(item.id)"
                    class="px-3 py-1 bg-green-500 text-white rounded-lg text-sm hover:bg-green-600"
                  >
                    启用
                  </button>
                </div>
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
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getUserList, updateUserStatus } from '@/api/user'
import type { UserItem } from '@/types'

const list = ref<UserItem[]>([])
const total = ref(0)
const page = ref(1)
const pageSize = ref(20)
const totalPages = ref(1)
const filters = ref<{
  keyword?: string
  role?: number
  status?: number
}>({})

async function loadData() {
  try {
    const res = await getUserList({
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

async function handleDisable(id: number) {
  try {
    await updateUserStatus(id, 0)
    loadData()
  } catch (e) {
    console.error('操作失败', e)
  }
}

async function handleEnable(id: number) {
  try {
    await updateUserStatus(id, 1)
    loadData()
  } catch (e) {
    console.error('操作失败', e)
  }
}

onMounted(loadData)
</script>
