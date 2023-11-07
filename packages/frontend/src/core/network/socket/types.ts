export enum SocketChannel {
  PLAYER_JOINED = 'PLAYER_JOINED',
  PLAYER_LEFT = 'PLAYER_LEFT',
  GAME_STARTED = 'GAME_STARTED',
  GAME_ENDED = 'GAME_ENDED',
  PLAYER_CARD_DREW = 'PLAYER_CARD_DREW',
  PLAYER_CARD_PLACED = 'PLAYER_CARD_PLACED',
}

export type IPlayerJoin = {
  playerName: string
  players: string[]
}
