import { io, Socket } from 'socket.io-client'
import { gameBase, setSocket } from '../../controllers/initGameBase'
import { IPlayerJoin, SocketChannel } from './types'
import { setGameActionPlayerName, setGameProgress } from '../../stores/storeRoom'
import { setToastMessage } from '../../stores/storeUI'
import { getGamePlayerMeData, getGamePlayersData } from '../../controllers/roomController'

export const connectRoomSocket = () => {
  const config = {
    autoConnect: true,
    reconnection: true,
    reconnectionDelayMax: 10000,
    reconnectionDelay: 1000,
    timeout: 10000,
    //path: path,
  }

  const socket = io('ws://localhost:8888/websocket', {
    ...config,
    query: { playerId: gameBase.playerId, gameId: gameBase.gameId },
  })
  onConnect(socket) // 連線相關
  onPlayersJoinLeft(socket) // 玩家資料
  onGameProgress(socket) // 遊戲進程
  onPlayerAction(socket) // 玩家動作
  setSocket(socket)

  return true
}

const onConnect = (socket: Socket) => {
  socket.on('connect', () => {
    console.log('%cSocket connected', 'color:lightgreen')
    getGamePlayersData()
  })
  socket.on('disconnect', () => {
    console.log('%cSocket disconnected', 'color:red')
  })
  socket.on('reconnect_attempt', (attempts) => {
    console.log('%cTry to reconnect at ' + attempts + ' attempt(s).', 'color:lightgreen')
    getGamePlayersData()
  })
}

const onPlayersJoinLeft = (socket: Socket) => {
  socket.on(SocketChannel.PLAYER_JOINED, (res: IPlayerJoin) => {
    console.log('%c玩家加入了遊戲', 'color:yellow', res.playerName)
    getGamePlayersData()
  })

  socket.on(SocketChannel.PLAYER_LEFT, (res: IPlayerJoin) => {
    console.log('%c玩家離開了遊戲', 'color:brown', res.playerName)
    getGamePlayersData()
  })
}

const onGameProgress = (socket: Socket) => {
  socket.on(SocketChannel.GAME_STARTED, (data: {nextPlayerName: string}) => {
    console.log('%c遊戲開始了', 'color:lightgreen', data)
    setGameProgress(SocketChannel.GAME_STARTED)
    getGamePlayerMeData()
    setToastMessage(`遊戲開始了, 等待${data.nextPlayerName}動作`)
    setGameActionPlayerName(data.nextPlayerName)

  })
  socket.on(SocketChannel.GAME_ENDED, (data: any) => {
    console.log('%c遊戲結束了', 'color:lightgreen', data)
    setToastMessage('遊戲結束了')
  })
}

const onPlayerAction = (socket: Socket) => {
  socket.on(SocketChannel.PLAYER_CARD_DREW, (userId: string) => {
    console.log('%c玩家抽了一張卡', 'color:lightgreen', userId)
  })
  socket.on(SocketChannel.PLAYER_CARD_PLACED, (userId: string) => {
    console.log('%c玩家放了一張卡', 'color:lightgreen', userId)
  })
}
