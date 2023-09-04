import io, { Socket } from 'socket.io-client'
import { gameBase, setSocket } from '../../gameBase'

import { isNil } from 'ramda'

interface IInitSocket {
  userId: string
  gameId: string
}
export const connectRoomSocket = ({ userId, gameId }: IInitSocket) => {
  if (!isNil(gameBase.socket)) setSocket(null)

  const socket = io('http://127.0.0.1:8888/websocket', {
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
  })

  setSocket(socket)
  onPlayerJoined(socket)
  onPlayerLeft(socket)
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
