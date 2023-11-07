import { Socket } from 'socket.io-client/build/esm/socket'
import { isNil } from 'ramda'
import api from '../network/api/'
import { gameStore } from '../stores'

export enum LocalStorageKey {
  //後端
  GAME_ID = 'gameId',
  PLAYER_NAME = 'playerName',
  PLAYER_ID = 'playerId',
}

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

// set to localStorage
export const setGameIdToLocal = (gameId: string) => {
  localStorage.setItem(LocalStorageKey.GAME_ID, gameId)
  gameBase.gameId = gameId
}
export const setPlayerNameToLocal = (playerName: string) => {
  localStorage.setItem(LocalStorageKey.PLAYER_NAME, playerName)
  gameBase.playerName = playerName
}
export const setPlayerIdToLocal = (playerId: string) => {
  localStorage.setItem(LocalStorageKey.PLAYER_ID, playerId)
  gameBase.playerId = playerId
}

export const getGameInfo = () => {
  const gameId = localStorage.getItem(LocalStorageKey.GAME_ID) || ''
  const playerName = localStorage.getItem(LocalStorageKey.PLAYER_NAME) || ''
  const playerId = localStorage.getItem(LocalStorageKey.PLAYER_ID) || ''
  gameBase.gameId = gameId
  gameBase.playerName = playerName
  gameBase.playerId = playerId

  return { gameId, playerName, playerId }
}

export const resetGameInfo = () => {
  localStorage.removeItem(LocalStorageKey.GAME_ID)
  localStorage.removeItem(LocalStorageKey.PLAYER_NAME)
  localStorage.removeItem(LocalStorageKey.PLAYER_ID)
  gameBase.gameId = ''
  gameBase.playerName = ''
  gameBase.playerId = ''
}
