// API
export interface IRoomHost {
  gameId: 'string'
  playerId: 'string'
  player: {
    playerName: 'string'
  }
}

export interface IApiCreateGame {
  gameId: string
  player: {
    playerName: string
  }
}

export interface IApiQueryGame {
  players: { playerName: 'string' }[]
}

export interface IApiJoinGame {
  players: { playerName: 'string' }[]
  playerId: 'string'
}

export interface ICard {
  cardType: 'string'
  targetPlayerId: 'string'
  destinationCardIndex: 0
  row: 0
  col: 0
  flipped: true
}
// 玩家打牌 棄牌
export type PlayCardType = {
  playerId: string
  handIndex: number
} & ICard

// card: {
//   x: number
//   y: number
//   cardIndex: number
//   cardId: string
//   cardName: string
// } //1張
