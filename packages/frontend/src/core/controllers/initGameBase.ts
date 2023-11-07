import { Socket } from 'socket.io-client/build/esm/socket'
import { isNil } from 'ramda'
import { LocalStorageKey, setGameIdToLocal, setPlayerNameToLocal } from './roomController'
import api from '../network/api/'
import { gameStore } from '../stores'

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

export const initGameBase = () => {
  console.log('initGameBase')
  const id = localStorage.getItem(LocalStorageKey.GAME_ID)
  if (id) setGameIdToLocal(id)
  const name = localStorage.getItem(LocalStorageKey.PLAYER_NAME)
  if (name) setPlayerNameToLocal(name)
  Object.assign(gameBase, { api, gameStore })
}

// Set Socket.io
export const setSocket = (socket: Socket | null) => {
  if (isNil(socket)) gameBase.socket?.disconnect()
  gameBase.socket = socket
}
