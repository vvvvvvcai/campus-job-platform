import request from './request'

export function getRecommendJobs() {
  return request.get('/recommend/jobs')
}

export function refreshRecommend(data) {
  return request.post('/recommend/refresh', data)
}

export function recordClick(jobId) {
  return request.post('/recommend/click', null, { params: { jobId } })
}
