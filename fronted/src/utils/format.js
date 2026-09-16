/**
 * 通用展示格式化工具（前端统一，后端数据为元/月整数）
 */

/** 薪资：15000-25000 → 15k-25k；缺省 → 面议 */
export function formatSalary(min, max) {
  if (min == null && max == null) return '面议'
  const k = v => {
    if (!v) return ''
    const n = v / 1000
    return (Number.isInteger(n) ? n : n.toFixed(1)) + 'k'
  }
  if (min != null && max != null) {
    if (min === max) return k(min)
    return `${k(min)}-${k(max)}`
  }
  return k(min != null ? min : max)
}

/** 手机号打码：13812345678 → 138****5678 */
export function maskPhone(phone) {
  const p = String(phone || '')
  if (p.length >= 7) return p.slice(0, 3) + '****' + p.slice(-4)
  return p
}

/** '2026-09-16T10:05:46' → '2026-09-16' */
export function formatDate(s) {
  return s ? String(s).substring(0, 10) : ''
}

/** '2026-09-16T10:05:46' → '2026-09-16 10:05' */
export function formatDateTime(s) {
  return s ? String(s).substring(0, 16).replace('T', ' ') : ''
}

/** 投递状态：0-待查看，1-已查看，2-面试邀请，3-不合适，4-已录用 */
export const APPLICATION_STATUS = {
  0: { label: '待查看', class: 'bg-amber-50 text-amber-600', dot: 'bg-amber-500' },
  1: { label: '已查看', class: 'bg-blue-50 text-blue-600', dot: 'bg-blue-500' },
  2: { label: '面试邀请', class: 'bg-purple-50 text-purple-600', dot: 'bg-purple-500' },
  3: { label: '不合适', class: 'bg-gray-100 text-gray-600', dot: 'bg-gray-400' },
  4: { label: '已录用', class: 'bg-emerald-50 text-emerald-600', dot: 'bg-emerald-500' }
}

/** 工作类型：1-全职，2-实习，3-兼职 */
export const JOB_TYPE_MAP = { 1: '全职', 2: '实习', 3: '兼职' }
