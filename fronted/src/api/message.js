import request from './request'

// 创建/获取会话
export function getOrCreateConversation(data) {
  return request.post('/message/conversation', data)
}

// 获取会话列表
export function getConversationList() {
  return request.get('/message/conversations')
}

// 获取会话历史消息
export function getMessageHistory(conversationId, params) {
  return request.get(`/message/history/${conversationId}`, { params })
}

// 发送消息
export function sendMessage(data) {
  return request.post('/message/send', data)
}

// 获取未读消息总数
export function getUnreadCount() {
  return request.get('/message/unread-count')
}

// 标记会话消息已读
export function markAsRead(conversationId) {
  return request.put(`/message/read/${conversationId}`)
}

// 获取通知列表
export function getNotificationList(params) {
  return request.get('/message/notification/list', { params })
}

// 标记通知已读
export function markNotificationAsRead(id) {
  return request.put(`/message/notification/read/${id}`)
}

// 全部标记已读
export function markAllNotificationsAsRead() {
  return request.put('/message/notification/read-all')
}

// 获取未读通知数
export function getNotificationUnreadCount() {
  return request.get('/message/notification/unread-count')
}
