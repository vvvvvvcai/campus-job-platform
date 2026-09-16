import request from './request'

export function auditCompany(data) {
  return request.post('/company/audit', data)
}

export function getCompanyInfo() {
  return request.get('/company/info')
}

export function updateCompanyInfo(data) {
  return request.put('/company/info', data)
}
