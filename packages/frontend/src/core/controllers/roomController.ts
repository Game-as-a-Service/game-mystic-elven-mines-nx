import api from '../network/api'
import Constants from '../Constants'

// 跟遊戲房間有關的Controllers

export const initRoomControllers = () => {
  const localGameId = localStorage.getItem('gameId')
  if (localGameId) setGameIdToLocal(localGameId)
  const localMyPlayerName = localStorage.getItem('myPlayerName')
  if (localMyPlayerName) setMyPlayerNameToLocal(localMyPlayerName)
}

export const createGameAndGetId = async (playerName: string) => {
  const res = await api.createGame({ playerName: playerName })
  setMyPlayerNameToLocal(playerName)
  setGameIdToLocal(res.gameId)
  setUserIdToLocal(res.player.playerName)
  return res.gameId
}

export const queryGame = async () => {
  return await api.queryGame()
}

export const joinRoomByNameAndId = async (playerName: string, gameId: string) => {
  const res = await api.joinGame({ playerName: playerName })
  console.log('join', res)
  setGameIdToLocal(gameId)
  setMyPlayerNameToLocal(playerName)

  // setUserIdToLocal(res.playersId) or setUserIdToLocal(res.userId)
  return res
}

// set to localStorage
export const setGameIdToLocal = (gameId: string) => {
  localStorage.setItem('gameId', gameId)
  Constants.gameId = gameId
}
export const setMyPlayerNameToLocal = (playerName: string) => {
  localStorage.setItem('playerName', playerName)
  Constants.myPlayerName = playerName
}
export const setUserIdToLocal = (userId: string) => {
  localStorage.setItem('userId', userId)
  Constants.userId = userId
}
