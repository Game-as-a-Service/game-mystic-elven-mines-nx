import useGameStore from '../../stores'
import { setRoomInfo } from '../../stores/storeRoom'
import { fetcher } from './fetcher'
import { IApiCreateGame, IApiJoinGame, IApiQueryGame, IplayCard } from './type'
import Constants from '../../Constants'

const API_URL = import.meta.env.PUBLIC_API_URL

// [GET] 建立遊戲
const createGame = async (data: { host: string }) => {
  const url = `${API_URL}/games`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiCreateGame) => {
    return res
  })
}

// [GET] 查詢遊戲
const queryGame = async () => {
  const url = `${API_URL}/games/${Constants.gameId}`
  return fetcher({ type: 'GET', url }).then((res: IApiQueryGame) => {
    return res
  })
}

// [POST] 加入遊戲
const joinGame = async (data: { name: string }) => {
  const url = `${API_URL}/games/${Constants.gameId}`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiJoinGame) => {
    return res
  })
}

//  X [GET] 查玩家手上有甚麼牌
export const getHandCards = async () => {
  const url = `${API_URL}//card/getHandCards?playerId={id}`
  return fetcher({ type: 'GET', url })
}

//  X [POST] 玩家打牌
export const playCard = async (body: IplayCard) => {
  const url = `${API_URL}/games/${Constants.gameId}:playCard`
  return fetcher({ type: 'POST', url, body })
}

//  X [POST] 玩家棄牌
export const foldCard = async (body: IplayCard) => {
  const url = `${API_URL}/games/${Constants.gameId}:foldCard`
  return fetcher({ type: 'POST', url, body })
}

//  X [GET] 取得地圖資訊
export const mapCards = async (body: IplayCard) => {
  const url = `${API_URL}/games/${Constants.gameId}/mapCards`
  return fetcher({ type: 'POST', url, body })
}

//  X [GET] 取得玩家資訊
export const players = async (body: IplayCard) => {
  const url = `${API_URL}/games/${Constants.gameId}/players`
  return fetcher({ type: 'POST', url, body })
}

//  X  TEST 測試後端有沒有正常運作
const helloTest = async () => {
  const url = `${API_URL}/test/hello`
  return fetcher({ type: 'GET', url })
}

export default {
  createGame,
  joinGame,
  queryGame,
  helloTest,
}
