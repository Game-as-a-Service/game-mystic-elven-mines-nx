import io, { Socket } from 'socket.io-client'
import { setSocket } from '../../gameBase'

interface IInitSocket {
  userId: string
  gameId: string
}
export const connectRoomSocket = ({ userId, gameId }: IInitSocket) => {
  const config = {
    query: {
      userId,
      gameId,
    },
    //path: path,
    autoConnect: true,
    reconnection: true,
    reconnectionDelayMax: 3000,
    reconnectionDelay: 1000,
    timeout: 10000,
    extraHeaders: {
      // auth_token: token,
    },
  }
  const socket = io('ws://localhost:8888/websocket', config)
  setSocket(socket)
  onConnect(socket)
  onPlayerJoined(socket)
  onPlayerLeft(socket)
}

const onConnect = (socket: Socket) => {
  socket.on('connect', () => {
    if (socket.recovered) {
      console.log('%cSocket 恢復連線', 'color:lightgreen')
    }
    console.log('%cSocket Connected', 'color:lightgreen')
  })
}

const onPlayerJoined = (socket: Socket) => {
  // 监听玩家加入事件
  socket.on('PLAYER_JOINED', (userId: string) => {
    console.log('%c玩家加入了遊戲', 'color:lightgreen', userId)
  })
}

const onPlayerLeft = (socket: Socket) => {
  // 监听玩家离开事件
  socket.on('PLAYER_LEFT', (userId: string) => {
    console.log('玩家離開了遊戲', 'color:lightgreen', userId)
  })
}
