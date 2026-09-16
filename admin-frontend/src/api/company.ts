import request from '@/utils/request'
import type { Result, PageResult, CompanyItem } from '@/types'

export function getCompanyList(params: {
  keyword?: string
  auditStatus?: number
  industry?: string
  page?: number
  pageSize?: number
}) {
  return request.get<any, Result<PageResult<CompanyItem>>>('/api/company/list', { params })
}

export function approveCompany(id: number) {
  return request.put<any, Result<void>>(`/api/company/audit/approve/${id}`)
}

export function rejectCompany(id: number, remark: string) {
  return request.put<any, Result<void>>(`/api/company/audit/reject/${id}`, { remark })
}
