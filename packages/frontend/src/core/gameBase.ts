import { Socket } from 'socket.io-client/build/esm/socket'
import { isNil } from 'ramda'

interface IGameBase {
  socket: Socket | null
}
export const gameBase: IGameBase = {
  socket: null,
}

// Set Socket.io
export const setSocket = (socket: Socket | null) => {
  if (isNil(socket)) gameBase.socket?.disconnect()
  gameBase.socket = socket
}
