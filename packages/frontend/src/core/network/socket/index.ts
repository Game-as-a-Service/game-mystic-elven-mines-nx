import { io,Socket } from 'socket.io-client';
import { setSocket } from '../../gameBase'
import { SocketChannel } from './types'
import { IPlayer } from '../api/type';
import { setGameProgress, setRoomPlayerNameList, setRoomPlayers } from '../../stores/storeRoom';
import { setToastMessage } from '../../stores/storeUI';


interface IInitSocket {
  playerId: string
  gameId: string
}
export const connectRoomSocket = ({ playerId, gameId }: IInitSocket) => {
  const config = {
    //path: path,
    // autoConnect: true,
    // reconnection: true,
    reconnectionDelayMax: 10000,
    // reconnectionDelay: 1000,
    // timeout: 10000,
  }

  const socket = io("ws://localhost:8888/websocket",
    {query: {playerId: playerId, gameId: gameId}});
  onConnect(socket)         // 連線相關
  onPlayersJoinLeft(socket) // 玩家資料
  onGameProgress(socket)   // 遊戲進程
  onPlayerAction(socket)   // 玩家動作
  setSocket(socket)
}


const onConnect = (socket: Socket) => {
  socket.on('connect',  ()=> {
    console.log('%cSocket connected', 'color:lightgreen')
  })
  socket.on('disconnect', ()=>  {
    console.log('%cSocket disconnected','color:red');
  });
  socket.on('reconnect_attempt', (attempts) => {
    console.log('%cTry to reconnect at ' + attempts + ' attempt(s).','color:lightgreen');
  });
}

const onPlayersJoinLeft = (socket: Socket) => {
  socket.on(SocketChannel.PLAYER_JOINED, (players: IPlayer[]) => {
    console.log('%c玩家加入了遊戲', 'color:yellow', players)
    setRoomPlayerNameList(players.map(x=>x.playerName))
    setRoomPlayers(players)
  })

  socket.on(SocketChannel.PLAYER_LEFT, (players: IPlayer[]) => {
    console.log('%c玩家離開了遊戲', 'color:gray', players)
  })
}

const onGameProgress = (socket: Socket) => {
  socket.on(SocketChannel.GAME_STARTED, () => {
    console.log('%c遊戲開始了', 'color:lightgreen')
    setGameProgress(SocketChannel.GAME_STARTED)
    setToastMessage('遊戲開始了')
  })
  socket.on(SocketChannel.GAME_ENDED, () => {
    console.log('%c遊戲結束了', 'color:lightgreen')
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
