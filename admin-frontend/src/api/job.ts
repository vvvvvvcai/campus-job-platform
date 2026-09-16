import request from '@/utils/request'
import type { Result, PageResult, JobAdminItem, JobCount, JobDetail } from '@/types'

export function getJobAdminList(params: {
  keyword?: string
  auditStatus?: number
  status?: number
  category?: string
  city?: string
  page?: number
  pageSize?: number
}) {
  return request.get<any, Result<PageResult<JobAdminItem>>>('/api/job/admin/list', { params })
}

export function getJobAdminDetail(id: number) {
  return request.get<any, Result<JobDetail>>(`/api/job/admin/detail/${id}`)
}

export function auditJob(id: number, data: { auditStatus: number; remark?: string }) {
  return request.put<any, Result<void>>(`/api/job/audit/${id}`, data)
}

export function getJobCount() {
  return request.get<any, Result<JobCount>>('/api/job/count')
}
