import api from '../network/api'
import { gameBase } from '../gameBase'

// 跟遊戲房間有關的Controllers

export const initRoomControllers = () => {
  const id = localStorage.getItem(LocalStorageKey.GAME_ID)
  if (id) setGameIdToLocal(id)
  const name = localStorage.getItem(LocalStorageKey.PLAYER_NAME)
  if (name) setPlayerNameToLocal(name)
}

export const createGameAndGetId = async (playerName: string) => {
  const res = await api.gameCreate({ playerName: playerName })
  console.log('建立遊戲並拿到gameId',res)
  setPlayerNameToLocal(playerName)
  setGameIdToLocal(res.gameId)
  setPlayerIdToLocal(res.playerId || '')
  return res.gameId
}

export const gamePlayers = async () => {
  return await api.gamePlayers()
}

export const joinRoomByNameAndId = async (playerName: string, gameId: string) => {
  const res = await api.gameJoin({ playerName: playerName })
  setGameIdToLocal(gameId)
  setPlayerNameToLocal(playerName)
  setPlayerIdToLocal(res.playerId)
  return true
}

export enum LocalStorageKey {
  GAME_ID='gameID',
  PLAYER_NAME='playerName',
  PLAYER_ID='playerId'
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
