import { fetcher } from './fetcher'
import {
  IApiCreateGame,
  IApiGameStart,
  IApiJoinGame,
  IApiPlayCard,
  IApiGamePlayers,
  PlayCardType,
  IPlayer,
} from './type'
import { gameBase } from '../../controllers/initGameBase'

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
  const url = `${API_URL}/games/${gameBase.gameId}`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiJoinGame) => {
    return res
  })
}

// [POST] 開始遊戲
const gameStart = async (data: { playerName: string }) => {
  const url = `${API_URL}/games/${gameBase.gameId}:start`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiGameStart) => {
    return res
  })
}

// [POST] 出牌
const gamePlayCard = async (data: { playerName: string }) => {
  const url = `${API_URL}/games/${gameBase.gameId}:start`
  return fetcher({ type: 'POST', url, body: data }).then((res: IApiPlayCard) => {
    return res
  })
}

// [GET] 查詢遊戲玩家資訊
const gamePlayers = async () => {
  const url = `${API_URL}/games/${gameBase.gameId}/players`
  return fetcher({ type: 'GET', url }).then((res: IApiGamePlayers) => {
    return res
  })
}

// [GET] 查詢遊戲本人資料
const gamePlayerMe = async () => {
  const url = `${API_URL}/games/${gameBase.gameId}/player/${gameBase.playerId}`
  return fetcher({ type: 'GET', url }).then((res: IPlayer) => {
    return res
  })
}

// //  X [GET] 查玩家手上有甚麼牌
// export const getHandCards = async () => {
//   const url = `${API_URL}/card/getHandCards?playerId={id}`
//   return fetcher({ type: 'GET', url })
// }

// //  X [POST] 玩家打牌
// export const playCard = async (body: PlayCardType) => {
//   const url = `${API_URL}/games/${gameBase.gameId}:playCard`
//   return fetcher({ type: 'POST', url, body })
// }

// //  X [POST] 玩家棄牌
// export const foldCard = async (body: PlayCardType) => {
//   const url = `${API_URL}/games/${gameBase.gameId}:foldCard`
//   return fetcher({ type: 'POST', url, body })
// }

// //  X [GET] 取得地圖資訊
// export const mapCards = async (body: PlayCardType) => {
//   const url = `${API_URL}/games/${gameBase.gameId}/mapCards`
//   return fetcher({ type: 'POST', url, body })
// }

// //  X [GET] 取得玩家資訊
// export const players = async (body: PlayCardType) => {
//   const url = `${API_URL}/games/${gameBase.gameId}/players`
//   return fetcher({ type: 'POST', url, body })
// }

// //  X  TEST 測試後端有沒有正常運作
// const helloTest = async () => {
//   const url = `${API_URL}/test/hello`
//   return fetcher({ type: 'GET', url })
// }

const defaultData = {
  gameCreate,
  gameJoin,
  gamePlayers,
  gamePlayerMe,
  // helloTest,
  gameStart,
  gamePlayCard,
}

export default { ...defaultData }
