import request from './request'

export function saveResume(data) {
  return request.post('/resume', data)
}

export function updateResume(id, data) {
  return request.put(`/resume/${id}`, data)
}

export function getResumeInfo(id) {
  return request.get(`/resume/${id}`)
}

export function deleteResume(id) {
  return request.delete(`/resume/${id}`)
}

export function getResumeList() {
  return request.get('/resume/list')
}

export function setDefaultResume(id) {
  return request.put(`/resume/default/${id}`)
}
