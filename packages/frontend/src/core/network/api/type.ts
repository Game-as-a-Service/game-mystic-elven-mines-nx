// API

export type IPlayer = {
  id: 'UUID'
  name: '$playerName'
}

export interface IApiCreateGame {
  gameId: 'string'
  host: {
    id: 'string'
    name: 'string'
  }
}

export interface IApiQueryGame {
  players: IPlayer[]
}

export interface IApiJoinGame {
  players: IPlayer[]
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
