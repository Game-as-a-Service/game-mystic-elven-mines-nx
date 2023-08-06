import { MapType } from '../../game/map/mapController'
import { IApiGames } from '../network/api/type'
import { CardTypeEnum, Paths } from '../types/Card'

export type SelectCardType = {
  cardType: CardTypeEnum
  cardName: Paths
}

export interface IGameStore {
  gameInfo: IApiGames | null
  map: MapType
  selectedCard: SelectCardType | null
  // setGameId: (gameId: string) => void
}
