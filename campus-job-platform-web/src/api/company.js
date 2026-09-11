import request from '../utils/request'

export function getCompanyInfo() {
  return request.get('/api/company/info')
}

export function auditCompany(data) {
  return request.post('/api/company/audit', data)
}

export function updateCompanyInfo(data) {
  return request.put('/api/company/info', data)
}
