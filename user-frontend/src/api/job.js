import request from './request'

export function searchJobs(params) {
  return request.get('/job/search', { params })
}

export function getJobDetail(id) {
  return request.get(`/job/detail/${id}`)
}

export function getJobCategories() {
  return request.get('/job/categories')
}

export function publishJob(data) {
  return request.post('/job/publish', data)
}

export function updateJob(id, data) {
  return request.put('/job/update', data, { params: { id } })
}

export function updateJobStatus(id, status) {
  return request.put(`/job/status/${id}`, null, { params: { status } })
}
