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
                <div class="flex gap-2">
                  <button
                    @click="showDetailModal(item.id)"
                    class="px-3 py-1 bg-blue-500 text-white rounded-lg text-sm hover:bg-blue-600"
                  >
                    详情
                  </button>
                  <template v-if="item.auditStatus === 0">
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
                  </template>
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

    <!-- 职位详情弹窗 -->
    <div v-if="showDetail" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50" @click.self="closeDetail">
      <div class="bg-white rounded-xl w-full max-w-3xl max-h-[90vh] flex flex-col">
        <div class="flex items-center justify-between border-b border-gray-100 px-6 py-4">
          <h3 class="text-lg font-semibold">职位详情</h3>
          <button @click="closeDetail" class="text-gray-400 hover:text-gray-600 text-2xl leading-none">&times;</button>
        </div>
        <div v-if="detailLoading" class="p-12 text-center text-gray-400">加载中...</div>
        <div v-else-if="detail" class="p-6 space-y-6 overflow-y-auto">
          <!-- 基本信息 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">基本信息</h4>
            <div class="grid grid-cols-2 gap-x-6 gap-y-4">
              <div>
                <div class="text-xs text-gray-400">职位名称</div>
                <div class="mt-1">{{ detail.title }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">所属企业</div>
                <div class="mt-1">{{ detail.companyName || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">岗位类别</div>
                <div class="mt-1">{{ detail.category || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">工作城市</div>
                <div class="mt-1">{{ detail.city || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">薪资范围</div>
                <div class="mt-1">{{ detail.salaryMin }}-{{ detail.salaryMax }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">学历要求</div>
                <div class="mt-1">{{ detail.education || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">经验要求</div>
                <div class="mt-1">{{ detail.experience || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">工作类型</div>
                <div class="mt-1">{{ jobTypeLabel(detail.jobType) }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">招聘人数</div>
                <div class="mt-1">{{ detail.headcount ?? '-' }} 人</div>
              </div>
            </div>
          </div>

          <!-- 内容信息 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">职位描述</h4>
            <div class="text-sm text-gray-700 whitespace-pre-line">{{ detail.description || '-' }}</div>
          </div>
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">任职要求</h4>
            <div class="text-sm text-gray-700 whitespace-pre-line">{{ detail.requirement || '-' }}</div>
          </div>
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">福利待遇</h4>
            <div v-if="benefitsTags.length" class="flex flex-wrap gap-2">
              <span
                v-for="(tag, index) in benefitsTags"
                :key="index"
                class="px-2 py-1 bg-blue-50 text-blue-700 rounded-lg text-xs"
              >
                {{ tag }}
              </span>
            </div>
            <div v-else class="text-sm text-gray-700 whitespace-pre-line">{{ detail.benefits || '-' }}</div>
          </div>

          <!-- 审核信息 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">审核信息</h4>
            <div class="grid grid-cols-2 gap-x-6 gap-y-4">
              <div>
                <div class="text-xs text-gray-400">审核状态</div>
                <div class="mt-1">
                  <span
                    class="px-2 py-1 rounded-full text-xs"
                    :class="{
                      'bg-yellow-100 text-yellow-800': detail.auditStatus === 0,
                      'bg-green-100 text-green-800': detail.auditStatus === 1,
                      'bg-red-100 text-red-800': detail.auditStatus === 2
                    }"
                  >
                    {{ detail.auditStatus === 0 ? '待审核' : detail.auditStatus === 1 ? '已通过' : '已拒绝' }}
                  </span>
                </div>
              </div>
              <div>
                <div class="text-xs text-gray-400">审核时间</div>
                <div class="mt-1">{{ detail.auditTime || '-' }}</div>
              </div>
              <div class="col-span-2">
                <div class="text-xs text-gray-400">审核备注</div>
                <div class="mt-1">{{ detail.auditRemark || '-' }}</div>
              </div>
            </div>
          </div>

          <!-- 状态统计 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">状态统计</h4>
            <div class="grid grid-cols-3 gap-x-6 gap-y-4">
              <div>
                <div class="text-xs text-gray-400">职位状态</div>
                <div class="mt-1">{{ detail.status === 1 ? '招聘中' : detail.status === 2 ? '已关闭' : '未上架' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">浏览量</div>
                <div class="mt-1">{{ detail.viewCount ?? 0 }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">投递量</div>
                <div class="mt-1">{{ detail.applyCount ?? 0 }}</div>
              </div>
            </div>
          </div>

          <!-- 时间 -->
          <div class="flex gap-8 text-xs text-gray-400">
            <span>创建时间：{{ detail.createTime }}</span>
            <span>更新时间：{{ detail.updateTime || '-' }}</span>
          </div>
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
import { ref, computed, onMounted } from 'vue'
import { getJobAdminList, getJobAdminDetail, auditJob } from '@/api/job'
import type { JobAdminItem, JobDetail } from '@/types'

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

const showDetail = ref(false)
const detailLoading = ref(false)
const detail = ref<JobDetail | null>(null)

const benefitsTags = computed<string[]>(() => {
  if (!detail.value?.benefits) return []
  try {
    const parsed = JSON.parse(detail.value.benefits)
    return Array.isArray(parsed) ? parsed : []
  } catch (e) {
    return []
  }
})

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

async function showDetailModal(id: number) {
  showDetail.value = true
  detailLoading.value = true
  detail.value = null
  try {
    const res = await getJobAdminDetail(id)
    if (res.code === 200) {
      detail.value = res.data
    }
  } catch (e) {
    console.error('加载职位详情失败', e)
    showDetail.value = false
  } finally {
    detailLoading.value = false
  }
}

function closeDetail() {
  showDetail.value = false
  detail.value = null
}

function jobTypeLabel(type?: number) {
  if (type === 1) return '全职'
  if (type === 2) return '实习'
  if (type === 3) return '兼职'
  return '-'
}

onMounted(loadData)
</script>