<template>
  <div class="space-y-6">
    <div class="bg-white rounded-xl shadow-sm p-6">
      <div class="flex flex-wrap gap-4 mb-6">
        <input
          v-model="filters.keyword"
          type="text"
          placeholder="搜索企业名称"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          @input="loadData"
        />
        <select
          v-model="filters.auditStatus"
          class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          @change="loadData"
        >
          <option :value="undefined">全部状态</option>
          <option :value="0">待审核</option>
          <option :value="1">已通过</option>
          <option :value="2">已拒绝</option>
        </select>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead>
            <tr class="border-b border-gray-200">
              <th class="text-left py-3 px-4 font-medium text-gray-600">企业名称</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">行业</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">规模</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">审核状态</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">提交时间</th>
              <th class="text-left py-3 px-4 font-medium text-gray-600">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in list" :key="item.id" class="border-b border-gray-100 hover:bg-gray-50">
              <td class="py-3 px-4">{{ item.companyName }}</td>
              <td class="py-3 px-4">{{ item.industry || '-' }}</td>
              <td class="py-3 px-4">{{ item.scale || '-' }}</td>
              <td class="py-3 px-4">
                <span
                  class="px-2 py-1 rounded-full text-xs"
                  :class="{
                    'bg-yellow-100 text-yellow-800': item.auditStatus === 0,
                    'bg-green-100 text-green-800': item.auditStatus === 1,
                    'bg-red-100 text-red-800': item.auditStatus === 2
                  }"
                >
                  {{ item.auditStatusLabel }}
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

    <!-- 企业详情弹窗 -->
    <div v-if="showDetail" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50" @click.self="closeDetail">
      <div class="bg-white rounded-xl w-full max-w-3xl max-h-[90vh] flex flex-col">
        <div class="flex items-center justify-between border-b border-gray-100 px-6 py-4">
          <h3 class="text-lg font-semibold">企业详情</h3>
          <button @click="closeDetail" class="text-gray-400 hover:text-gray-600 text-2xl leading-none">&times;</button>
        </div>
        <div v-if="detailLoading" class="p-12 text-center text-gray-400">加载中...</div>
        <div v-else-if="detail" class="p-6 space-y-6 overflow-y-auto">
          <!-- 基本信息 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">基本信息</h4>
            <div class="grid grid-cols-2 gap-x-6 gap-y-4">
              <div>
                <div class="text-xs text-gray-400">企业名称</div>
                <div class="mt-1">{{ detail.companyName }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">所属行业</div>
                <div class="mt-1">{{ detail.industry || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">企业规模</div>
                <div class="mt-1">{{ detail.scale || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">企业性质</div>
                <div class="mt-1">{{ detail.nature || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">企业地址</div>
                <div class="mt-1">{{ detail.address || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">企业官网</div>
                <div class="mt-1">
                  <a
                    v-if="detail.website"
                    :href="detail.website"
                    target="_blank"
                    class="text-blue-500 hover:underline"
                  >
                    {{ detail.website }}
                  </a>
                  <span v-else>-</span>
                </div>
              </div>
            </div>
          </div>

          <!-- 认证信息 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">认证信息</h4>
            <div class="grid grid-cols-2 gap-x-6 gap-y-4">
              <div>
                <div class="text-xs text-gray-400">企业Logo</div>
                <div class="mt-1">
                  <img
                    v-if="detail.logo"
                    :src="detail.logo"
                    class="h-16 w-16 object-contain border border-gray-200 rounded-lg"
                    alt="企业Logo"
                  />
                  <span v-else>-</span>
                </div>
              </div>
              <div>
                <div class="text-xs text-gray-400">营业执照</div>
                <div class="mt-1">
                  <a
                    v-if="detail.licenseUrl"
                    :href="detail.licenseUrl"
                    target="_blank"
                    class="inline-block"
                  >
                    <img
                      :src="detail.licenseUrl"
                      class="h-16 object-cover border border-gray-200 rounded-lg hover:opacity-80"
                      alt="营业执照"
                    />
                  </a>
                  <span v-else>-</span>
                </div>
                <div class="mt-1 text-xs text-blue-500">
                  <a v-if="detail.licenseUrl" :href="detail.licenseUrl" target="_blank">点击查看大图</a>
                </div>
              </div>
            </div>
          </div>

          <!-- 企业简介 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">企业简介</h4>
            <div class="text-sm text-gray-700 whitespace-pre-line">{{ detail.description || '-' }}</div>
          </div>

          <!-- HR联系方式 -->
          <div>
            <h4 class="text-sm font-semibold text-gray-400 mb-3">HR联系方式</h4>
            <div class="grid grid-cols-3 gap-x-6 gap-y-4">
              <div>
                <div class="text-xs text-gray-400">联系人姓名</div>
                <div class="mt-1">{{ detail.hrName || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">联系电话</div>
                <div class="mt-1">{{ detail.hrPhone || '-' }}</div>
              </div>
              <div>
                <div class="text-xs text-gray-400">电子邮箱</div>
                <div class="mt-1">{{ detail.hrEmail || '-' }}</div>
              </div>
            </div>
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
import { ref, onMounted } from 'vue'
import { getCompanyList, getCompanyAdminDetail, approveCompany, rejectCompany } from '@/api/company'
import type { CompanyItem, CompanyDetail } from '@/types'

const list = ref<CompanyItem[]>([])
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
const detail = ref<CompanyDetail | null>(null)

async function loadData() {
  try {
    const res = await getCompanyList({
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
    await approveCompany(id)
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
    await rejectCompany(rejectId.value, rejectReason.value)
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
    const res = await getCompanyAdminDetail(id)
    if (res.code === 200) {
      detail.value = res.data
    }
  } catch (e) {
    console.error('加载企业详情失败', e)
    showDetail.value = false
  } finally {
    detailLoading.value = false
  }
}

function closeDetail() {
  showDetail.value = false
  detail.value = null
}

onMounted(loadData)
</script>