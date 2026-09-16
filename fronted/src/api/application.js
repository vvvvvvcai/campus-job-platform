import request from './request'

export function submitApplication(data) {
  return request.post('/application/submit', data)
}

export function getApplicationList(params) {
  return request.get('/application/list', { params })
}

export function getApplicationDetail(id) {
  return request.get(`/application/detail/${id}`)
}

export function handleApplication(id, data) {
  return request.put(`/application/handle/${id}`, data)
}

export function getReceivedApplications(params) {
  return request.get('/application/received', { params })
}

export function getCandidateResume(applicationId) {
  return request.get(`/application/resume/${applicationId}`)
}
