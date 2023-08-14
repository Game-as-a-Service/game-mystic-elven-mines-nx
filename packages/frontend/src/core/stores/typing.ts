import { MapType } from '../../game/map/mapController'
import { IApiCreateGame } from '../network/api/type'
import { CardTypeEnum, Paths } from '../types/Card'

export type SelectCardType = {
  cardType: CardTypeEnum
  cardName: Paths
}
