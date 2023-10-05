import useGameStore from '../../stores'
import { setRoomInfo } from '../../stores/storeRoom'
import { fetcher } from './fetcher'
import { IApiCreateGame, IApiGameStart, IApiJoinGame, IApiPlayCard, IApiGamePlayers , PlayCardType } from './type'
import Constants from '../../Constants'

const API_URL = import.meta.env.PUBLIC_API_URL

// [GET] 建立遊戲
const gameCreate = async (data: { playerName: string }) => {
  const url = `${API_URL}/games`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiCreateGame) => {
    return res
  })
}

// [POST] 加入遊戲
const gameJoin = async (data: { playerName: string }) => {
  const url = `${API_URL}/games/${Constants.gameId}`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiJoinGame) => {
    return res
  })
}

// [POST] 開始遊戲
const gameStart = async (data: { playerName: string }) => {
  const url = `${API_URL}/games/${Constants.gameId}:start`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiGameStart) => {
    return res
  })
}
// [POST] 出牌
const gamePlayCard = async (data: { playerName: string }) => {
  const url = `${API_URL}/games/${Constants.gameId}:start`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiPlayCard) => {
    return res
  })
}

// [GET] 查詢遊戲玩家資訊
const gamePlayers = async () => {
  const url = `${API_URL}/games/${Constants.gameId}/players`
  return fetcher({ type: 'GET', url }).then((res: IApiGamePlayers) => {
    return res
  })
}


//  X [GET] 查玩家手上有甚麼牌
export const getHandCards = async () => {
  const url = `${API_URL}//card/getHandCards?playerId={id}`
  return fetcher({ type: 'GET', url })
}

//  X [POST] 玩家打牌
export const playCard = async (body: PlayCardType) => {
  const url = `${API_URL}/games/${Constants.gameId}:playCard`
  return fetcher({ type: 'POST', url, body })
}

//  X [POST] 玩家棄牌
export const foldCard = async (body: PlayCardType) => {
  const url = `${API_URL}/games/${Constants.gameId}:foldCard`
  return fetcher({ type: 'POST', url, body })
}

//  X [GET] 取得地圖資訊
export const mapCards = async (body: PlayCardType) => {
  const url = `${API_URL}/games/${Constants.gameId}/mapCards`
  return fetcher({ type: 'POST', url, body })
}

//  X [GET] 取得玩家資訊
export const players = async (body: PlayCardType) => {
  const url = `${API_URL}/games/${Constants.gameId}/players`
  return fetcher({ type: 'POST', url, body })
}

//  X  TEST 測試後端有沒有正常運作
const helloTest = async () => {
  const url = `${API_URL}/test/hello`
  return fetcher({ type: 'GET', url })
}

export default {
  gameCreate,
  gameJoin,
  gamePlayers,
  helloTest,
}
