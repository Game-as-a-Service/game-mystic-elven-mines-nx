// API
export interface IPlayer {
  id: string
  name: string
}

export interface IApiCreateGame {
  gameId: string
  host: {
    id: string // 開會這裡改 userId
    name: string
  }
}

export interface IApiQueryGame {
  players: IPlayer[]
}

export interface IApiJoinGame {
  players: IPlayer[]
  playerId: string
}

// 玩家打牌 棄牌
export interface IplayCard {
  playerId: string
  card: {
    x: number
    y: number
    cardIndex: number
    cardId: string
    cardName: string
  } //1張
}
