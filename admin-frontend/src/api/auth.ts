import request from '@/utils/request'
import type { Result, LoginParams, LoginResult } from '@/types'

export function login(data: LoginParams) {
  return request.post<any, Result<LoginResult>>('/api/user/login', data)
}

export function getUserInfo() {
  return request.get<any, Result<any>>('/api/user/info')
}
