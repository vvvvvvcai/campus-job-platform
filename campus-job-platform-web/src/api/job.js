import request from '../utils/request'

export function searchJobs(params) {
  return request.get('/api/job/search', { params })
}

export function getJobDetail(id) {
  return request.get(`/api/job/detail/${id}`)
}

export function publishJob(data) {
  return request.post('/api/job/publish', data)
}

export function updateJob(id, data) {
  return request.put(`/api/job/update`, data, { params: { id } })
}

export function updateJobStatus(id, status) {
  return request.put(`/api/job/status/${id}`, null, { params: { status } })
}

export function addFavorite(jobId) {
  return request.post('/api/favorite', null, { params: { jobId } })
}

export function removeFavorite(jobId) {
  return request.delete(`/api/favorite/${jobId}`)
}

export function getFavoriteList() {
  return request.get('/api/favorite/list')
}
