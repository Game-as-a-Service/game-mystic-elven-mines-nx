import gameStore, { setGameInfo } from '../../stores'
import { fetcher } from './fetcher'
import { IApiGames, IplayCard } from './type'

const API_URL = import.meta.env.PUBLIC_API_URL
console.log({ API_URL })

let gameId = 51651561561

// [GET] 建立遊戲
export const createGame = async (data: { host: string }) => {
  const url = `${API_URL}/games`
  fetcher({ type: 'POST', url, body: data }).then((res: IApiGames) => {
    setGameInfo(res)
    return res
  })
}

// [GET] 查詢遊戲
export const findGame = async (gameId: string = 'gameId01') => {
  const url = `${API_URL}/test/findGame/${gameId}`
  return await fetcher({ type: 'GET', url })
}

// [GET] 查玩家手上有甚麼牌
export const getHandCards = async () => {
  const url = `${API_URL}//card/getHandCards?playerId={id}`
  return await fetcher({ type: 'GET', url })
}

// [POST] 玩家打牌
export const playCard = async (body: IplayCard) => {
  const url = `${API_URL}/games/${gameId}:playCard`
  return await fetcher({ type: 'POST', url, body })
}

// [POST] 玩家棄牌
export const foldCard = async (body: IplayCard) => {
  const url = `${API_URL}/games/${gameId}:foldCard`
  return await fetcher({ type: 'POST', url, body })
}

// [GET] 取得地圖資訊
export const mapCards = async (body: IplayCard) => {
  const url = `${API_URL}/games/${gameId}/mapCards`
  return await fetcher({ type: 'POST', url, body })
}

// [GET] 取得玩家資訊
export const players = async (body: IplayCard) => {
  const url = `${API_URL}/games/${gameId}/players`
  return await fetcher({ type: 'POST', url, body })
}

// TEST 測試後端有沒有正常運作
export const helloTest = async () => {
  const url = `${API_URL}/test/hello`
  return await fetcher({ type: 'GET', url })
}
