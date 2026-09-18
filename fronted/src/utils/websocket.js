import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client/dist/sockjs'

let stompClient = null
let connected = false

export function connectWebSocket(userId, onMessage, onNotification) {
  if (stompClient && connected) return

  stompClient = new Client({
    webSocketFactory: () => new SockJS('/ws'),
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    onConnect: () => {
      connected = true
      stompClient.subscribe('/user/' + userId + '/queue/messages', (msg) => {
        if (onMessage) onMessage(JSON.parse(msg.body))
      })
      stompClient.subscribe('/user/' + userId + '/queue/notifications', (msg) => {
        if (onNotification) onNotification(JSON.parse(msg.body))
      })
    },
    onDisconnect: () => {
      connected = false
    },
    onStompError: (frame) => {
      connected = false
      console.error('STOMP error:', frame.headers['message'])
    }
  })

  stompClient.activate()
}

export function disconnectWebSocket() {
  if (stompClient) {
    stompClient.deactivate()
    stompClient = null
    connected = false
  }
}

export function isConnected() {
  return connected
}
