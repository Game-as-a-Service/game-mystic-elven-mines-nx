import { Socket } from 'socket.io-client/build/esm/socket'
import { isNil } from 'ramda'

interface IGameBase {
  socket: Socket | null
  gameId: string
  playerName: string
  playerId: string
  api: any
}
export const gameBase: IGameBase = {
  socket: null,
  api: null,
  gameId: '',
  playerName: '',
  playerId: '',
}

// Set Socket.io
export const setSocket = (socket: Socket | null) => {
  if (isNil(socket)) gameBase.socket?.disconnect()
  gameBase.socket = socket
}
