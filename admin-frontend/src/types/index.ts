export interface Result<T> {
  code: number
  message: string
  data: T
}

export interface PageResult<T> {
  total: number
  page: number
  pageSize: number
  totalPages: number
  records: T[]
}

export interface UserInfo {
  userId: number
  phone: string
  username: string
  role: number
  avatar?: string
}

export interface LoginParams {
  phone: string
  password: string
}

export interface LoginResult {
  token: string
  userId: number
  phone: string
  username: string
  role: number
  avatar?: string
}

export interface UserItem {
  id: number
  phone: string
  username: string
  role: number
  roleLabel: string
  avatar?: string
  email?: string
  status: number
  statusLabel: string
  createTime: string
}

export interface UserDetail {
  id: number
  phone: string
  username: string
  realName?: string
  gender?: number
  genderLabel?: string
  email?: string
  avatar?: string
  role: number
  roleLabel: string
  status: number
  statusLabel: string
  createTime: string
  updateTime?: string
}

export interface UserCount {
  total: number
  byRole: {
    student: number
    hr: number
    admin: number
  }
  byStatus: {
    active: number
    disabled: number
  }
}

export interface CompanyItem {
  id: number
  userId: number
  companyName: string
  industry?: string
  scale?: string
  licenseUrl?: string
  auditStatus: number
  auditStatusLabel: string
  auditRemark?: string
  createTime: string
}

export interface JobAdminItem {
  id: number
  title: string
  category?: string
  city?: string
  salaryMin?: number
  salaryMax?: number
  education?: string
  jobType?: number
  headcount?: number
  status: number
  auditStatus: number
  auditRemark?: string
  viewCount?: number
  applyCount?: number
  createTime: string
}

export interface JobDetail {
  id: number
  companyId?: number
  companyName?: string
  title: string
  category?: string
  city?: string
  salaryMin?: number
  salaryMax?: number
  education?: string
  experience?: string
  jobType?: number
  headcount?: number
  description?: string
  requirement?: string
  benefits?: string
  status: number
  auditStatus: number
  auditRemark?: string
  auditTime?: string
  viewCount?: number
  applyCount?: number
  createTime: string
  updateTime?: string
}

export interface CompanyDetail {
  id: number
  userId?: number
  companyName: string
  industry?: string
  scale?: string
  nature?: string
  address?: string
  website?: string
  logo?: string
  description?: string
  licenseUrl?: string
  auditStatus: number
  auditRemark?: string
  auditTime?: string
  createTime: string
  updateTime?: string
  hrName?: string
  hrPhone?: string
  hrEmail?: string
}

export interface JobCount {
  total: number
  byStatus: {
    pending: number
    recruiting: number
    closed: number
  }
  byAuditStatus: {
    pending: number
    approved: number
    rejected: number
  }
}

export interface KpiCard {
  title: string
  value: string
  trend?: string
  trendUp?: boolean
  icon: string
  iconBg: string
  iconColor: string
}
