import request from '../utils/request'

export function getRecommendJobs() {
  return request.get('/api/recommend/jobs')
}

export function refreshRecommend(data) {
  return request.post('/api/recommend/refresh', data)
}

export function recordClick(jobId) {
  return request.post('/api/recommend/click', null, { params: { jobId } })
}
