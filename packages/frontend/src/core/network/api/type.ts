import { CardTypeEnum, PathCard } from '../../types/Card'

// API
export interface IApiCreateGame {
  gameId: string
  player: {
    playerName: IPlayer['playerName']
    cardNum: number
    tools: { toolName: string; available: boolean }[]
  }
  playerId?: IPlayer['playerId']
}

export interface IApiJoinGame {
  players: {
    playerName: IPlayer['playerName']
    cardNum: number
    tools: { toolName: string; available: boolean }[]
  }[]
  playerId?: IPlayer['playerId']
}

export type IPlayer = {
  playerName: string
  playerId?: string
  cardNum: number
  tools: {
    toolName: string
    available: boolean
  }[]
  role?: 'ELVEN' | 'GOBLIN'
  cards?: IHandCard[]
}
export type IHandCard = {
  type: CardTypeEnum
  name: PathCard
  flipped: boolean
}

export type IPlayerMap = Record<IPlayer['playerName'], IPlayer>
export interface IApiGamePlayers {
  players: IPlayer[]
}

export interface IApiGameStart {
  message: string
}

export interface IApiPlayCard {
  playerId: string
  handIndex: number
  cardType: string
  targetPlayerId: string
  destinationCardIndex: number
  row: number
  col: number
  flipped: boolean
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
