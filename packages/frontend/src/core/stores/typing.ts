import { MapType } from '../../game/map/mapController'
import { CardTypeEnum, Paths } from '../types/Card'

export type SelectCardType = {
  cardType: CardTypeEnum
  cardName: Paths
}

export interface IGameStore {
  gameId: string
  map: MapType
  selectedCard: SelectCardType | null
  // setGameId: (gameId: string) => void
}
