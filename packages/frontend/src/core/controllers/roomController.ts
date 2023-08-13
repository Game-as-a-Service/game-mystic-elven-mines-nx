import api from '../network/api'
import Constants from '../Constants'

// 跟遊戲房間有關的Controllers

export const initRoomControllers = () => {
  const localGameId = localStorage.getItem('gameId')
  if (localGameId) setGameIdToLocal(localGameId)
  const localMyName = localStorage.getItem('myName')
  if (localMyName) setMyNameToLocal(localMyName)
}

export const createGameAndGetId = async (host: string) => {
  const res = await api.createGame({ host })
  setMyNameToLocal(host)
  setGameIdToLocal(res.gameId)
  return res.gameId
}

export const queryGame = async () => {
  const gamePlayers = await api.queryGame()
  return gamePlayers
}

export const joinRoomByNameAndId = async (name: string, gameId: string) => {
  const res = await api.joinGame({ name })
  setGameIdToLocal(gameId)
  setMyNameToLocal(name)
  return res
}

// set to localStorage
export const setGameIdToLocal = (gameId: string) => {
  localStorage.setItem('gameId', gameId)
  Constants.gameId = gameId
}
export const setMyNameToLocal = (name: string) => {
  localStorage.setItem('myName', name)
  Constants.myName = name
}
