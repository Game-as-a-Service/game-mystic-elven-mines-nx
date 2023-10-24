import api from '../network/api'
import { gameBase } from '../gameBase'
import { setRoomPlayerNameList, setRoomPlayers } from '../stores/storeRoom'

// 跟遊戲房間有關的Controllers

export const initRoomControllers = () => {
  const id = localStorage.getItem(LocalStorageKey.GAME_ID)
  if (id) setGameIdToLocal(id)
  const name = localStorage.getItem(LocalStorageKey.PLAYER_NAME)
  if (name) setPlayerNameToLocal(name)
}

export const createGameAndGetId = async (playerName: string) => {
  const res = await api.gameCreate({ playerName: playerName })
  setPlayerNameToLocal(res.player.playerName)
  setGameIdToLocal(res.gameId)
  setPlayerIdToLocal(res.playerId || '')
  return res.gameId
}

export const joinRoomByNameAndId = async (playerName: string, gameId: string): Promise<{playerId: string}> => {
  const res = await api.gameJoin({ playerName: playerName })
  setGameIdToLocal(gameId)
  setPlayerNameToLocal(playerName)
  setPlayerIdToLocal(res.playerId || '')
  return { playerId: res.playerId ||''}
}

export const getGamePlayersData = async () => {
  const {players} = await api.gamePlayers()
  const playerNameList = players.map(x=>x.playerName)
  setRoomPlayers(players)
  setRoomPlayerNameList(playerNameList)

}

export enum LocalStorageKey {
  GAME_ID='gameId',
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

export const getGameInfo=()=>{
  const gameId  = localStorage.getItem(LocalStorageKey.GAME_ID)
  const playerName  = localStorage.getItem(LocalStorageKey.PLAYER_NAME)
  const playerId  = localStorage.getItem(LocalStorageKey.PLAYER_ID)
  gameBase.gameId = gameId || ''
  gameBase.playerName = playerName|| ''
  gameBase.playerId = playerId|| ''
  return {gameId,playerName,playerId}
}

export const resetGameInfo =()=>{
  localStorage.removeItem(LocalStorageKey.GAME_ID)
  localStorage.removeItem(LocalStorageKey.PLAYER_NAME)
  localStorage.removeItem(LocalStorageKey.PLAYER_ID)
  gameBase.gameId = ''
  gameBase.playerName =''
  gameBase.playerId = ''
}

