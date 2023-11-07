import api from '../network/api'
import { gameBase } from './initGameBase'
import { setRoomPlayerNameList, setRoomPlayers } from '../stores/storeRoom'
import { useGameStore } from '../stores'
import { connectRoomSocket } from '../network/socket'
import { IPlayerJoin } from '../network/socket/types'

// 跟遊戲房間有關的Controllers

export const createGameAndGetId = async (playerName: string) => {
  const res = await api.gameCreate({ playerName: playerName })
  console.log('api gameCreate', res)
  setPlayerNameToLocal(res.player.playerName)
  setGameIdToLocal(res.gameId)
  setPlayerIdToLocal(res.playerId || '')
  return res.gameId
}

export const joinRoomAndGetPlayerId = async (playerName: string) => {
  const res = await api.gameJoin({ playerName: playerName })
  setPlayerIdToLocal(res.playerId || '')
  return { playerId: res.playerId, players: res.players }
}

export const getGamePlayersData = async () => {
  //socket連線成功後 取得玩家資料
  const { players } = await api.gamePlayers()
  const playerNameList = players.map((x) => x.playerName)
  setRoomPlayers(players)
  setRoomPlayerNameList(playerNameList)
}

export const gamePlayersMeData = async () => {
  const gamePlayersMeDataRes = await api.gamePlayersMe()
  console.log('gamePlayersMeDataRes', gamePlayersMeDataRes)
}

export enum LocalStorageKey {
  //後端
  GAME_ID = 'gameId',
  PLAYER_NAME = 'playerName',
  PLAYER_ID = 'playerId',
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

export const initFirstTimeJoinRoom = async (store: any) => {
  const { gameId, playerId, playerName } = await getGameInfo()
  console.log('initFirstTimeJoinRoom', { gameId, playerId, playerName })
  store.playerName = playerName || ''

  if (playerName === '') {
    console.log('[遊客模式]')
  }
  if (gameId !== '' && playerId !== '' && playerName !== '') {
    console.log('[加入遊戲Join]')
    const res = await joinRoomAndGetPlayerId(playerName)
    console.log('res', res)

    connectRoomSocket()
  }
}
