import request from './request'

export function addFavorite(jobId) {
  return request.post('/favorite', null, { params: { jobId } })
}

export function removeFavorite(jobId) {
  return request.delete(`/favorite/${jobId}`)
}

export function getFavoriteList() {
  return request.get('/favorite/list')
}
