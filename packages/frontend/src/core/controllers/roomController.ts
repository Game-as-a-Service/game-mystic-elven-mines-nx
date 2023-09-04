import api from '../network/api'
import Constants from '../Constants'

// 跟遊戲房間有關的Controllers

export const initRoomControllers = () => {
  const localGameId = localStorage.getItem('gameId')
  if (localGameId) setGameIdToLocal(localGameId)
  const localMyName = localStorage.getItem('myName')
  if (localMyName) setUserNameToLocal(localMyName)
}

export const createGameAndGetId = async (host: string) => {
  const res = await api.createGame({ host })
  setUserNameToLocal(host)
  setGameIdToLocal(res.gameId)
  setUserIdToLocal(res.host.id)
  return res.gameId
}

export const queryGame = async () => {
  return await api.queryGame()
}

export const joinRoomByNameAndId = async (name: string, gameId: string) => {
  const res = await api.joinGame({ name })
  console.log('join', res)
  setGameIdToLocal(gameId)
  setUserNameToLocal(name)
  setUserIdToLocal(res.players[res.players.length - 1].id)
  // setUserIdToLocal(res.playersId) or setUserIdToLocal(res.userId)
  return res
}

// set to localStorage
export const setGameIdToLocal = (gameId: string) => {
  localStorage.setItem('gameId', gameId)
  Constants.gameId = gameId
}
export const setUserNameToLocal = (name: string) => {
  localStorage.setItem('userName', name)
  Constants.myName = name
}
export const setUserIdToLocal = (userId: string) => {
  localStorage.setItem('userId', userId)
  Constants.userId = userId
}
