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
    return Promise.reject(error)
  }
)

export default request
