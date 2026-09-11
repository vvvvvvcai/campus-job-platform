import request from '../utils/request'

export function saveResume(data) {
  return request.post('/api/resume', data)
}

export function updateResume(id, data) {
  return request.put(`/api/resume/${id}`, data)
}

export function getResumeDetail(id) {
  return request.get(`/api/resume/${id}`)
}

export function deleteResume(id) {
  return request.delete(`/api/resume/${id}`)
}

export function getResumeList() {
  return request.get('/api/resume/list')
}

export function setDefaultResume(id) {
  return request.put(`/api/resume/default/${id}`)
}

export function submitApplication(data) {
  return request.post('/api/application/submit', data)
}

export function getApplicationList(params) {
  return request.get('/api/application/list', { params })
}

export function getApplicationDetail(id) {
  return request.get(`/api/application/detail/${id}`)
}

export function handleApplication(id, data) {
  return request.put(`/api/application/handle/${id}`, data)
}

export function getReceivedApplications(params) {
  return request.get('/api/application/received', { params })
}
