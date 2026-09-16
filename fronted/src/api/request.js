import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('campus_token')
    const userId = localStorage.getItem('campus_user_id')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    if (userId) {
      config.headers['X-User-Id'] = userId
    }
    return config
  },
  error => Promise.reject(error)
)

request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res.data
    }
    return Promise.reject(new Error(res.message || '请求失败'))
  },
  error => {
    console.error('API Error:', error)
    // 拼上真实请求路径与状态码，便于定位 404/401 等错误来源
    const url = error.config && error.config.url ? error.config.url : 'unknown'
    const status = error.response ? error.response.status : 'no-response'
    const detail = error.response && error.response.data && error.response.data.message
      ? error.response.data.message
      : (status === 'no-response' ? '无法连接到服务器' : error.message)
    return Promise.reject(new Error(`[${url}] HTTP ${status}${detail ? '：' + detail : ''}`))
  }
)

export default request
