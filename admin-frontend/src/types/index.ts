export interface UserInfo {
  userId: number
  phone: string
  username: string
  role: number
  avatar?: string
}

export interface KpiCard {
  title: string
  value: string
  trend?: string
  trendUp?: boolean
  icon: string
  iconBg: string
  iconColor: string
  details?: { label: string; value: string }[]
  footer?: { label: string; value: string }
  progress?: number
  subMetrics?: { label: string; value: string; color: string }[]
}

export type AuditStatus = 'pending' | 'approved' | 'rejected'

export interface Enterprise {
  id: number
  name: string
  creditCode: string
  industry: string
  scale: string
  legalPerson: string
  phone: string
  licenseOcr: 'matched' | 'unverified' | 'expired'
  licenseTag: string
  submitTime: string
  waitTime: string
  waitUrgent: boolean
  status: AuditStatus
  avatarText: string
  avatarBg: string
  avatarColor: string
  tags?: string[]
  verified?: boolean
}

export interface EnterpriseDetail {
  enterprise: Enterprise
  flowNo: string
  companyType: string
  industryCategory: string
  registeredCapital: string
  establishedDate: string
  website: string
  address: string
  statusLabel: string
}

export type JobAuditStatus = 'pending' | 'approved' | 'rejected' | 'offline'

export type JobType = 'intern' | 'campus'

export type RiskLevel = 'safe' | 'low' | 'high'

export interface JobAudit {
  id: string
  title: string
  jobType: JobType
  recruitCount: number
  education: string
  publishTime: string
  companyName: string
  companyCert: 'certified' | 'startup' | 'partner'
  companyCertLabel: string
  salary: string
  salaryHighlight: boolean
  city: string
  score: number
  riskLevel: RiskLevel
  riskLabel: string
  riskDetail: string
  status: JobAuditStatus
  selected?: boolean
}

export type UserStatus = 'active' | 'disabled'

export type UserRole = 'student' | 'enterprise'

export interface User {
  uid: string
  name: string
  email: string
  role: UserRole
  roleLabel: string
  roleIcon: string
  affiliation: string
  maskedPhone: string
  registerTime: string
  lastLoginTime: string
  lastLoginIp: string
  lastLoginLocation: string
  status: UserStatus
  avatarText: string
  avatarBg: string
  avatarColor: string
  passwordResets: number
  twoFactorActive: boolean
}
