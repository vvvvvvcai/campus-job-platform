import request from './request'

/**
 * 上传本地图片，返回可访问的 URL（/uploads/...）
 */
export function uploadImage(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/upload/image', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
