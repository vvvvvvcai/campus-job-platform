import request from '@/utils/request'
import type { Result, PageResult, UserItem, UserDetail, UserCount, TrendItem } from '@/types'

export function getUserList(params: {
  keyword?: string
  role?: number
  status?: number
  page?: number
  pageSize?: number
}) {
  return request.get<any, Result<PageResult<UserItem>>>('/api/user/list', { params })
}

export function updateUserStatus(id: number, status: number) {
  return request.put<any, Result<void>>(`/api/user/status/${id}`, null, { params: { status } })
}

export function getUserCount() {
  return request.get<any, Result<UserCount>>('/api/user/count')
}

export function getUserAdminDetail(id: number) {
  return request.get<any, Result<UserDetail>>(`/api/user/admin/detail/${id}`)
}

export function getUserTrend() {
  return request.get<any, Result<TrendItem[]>>('/api/user/count/trend')
}
