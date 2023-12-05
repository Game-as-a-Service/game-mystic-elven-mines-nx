import { IMapCard } from './mapController'
import { CardTypeEnum, Paths } from '../../core/types/Card'
import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'

export const mockCards: IMapCard[] = [
  { row: 1, col: 2, cardName: Paths.Cross, cardType: CardTypeEnum.Path },
  {
    row: 2,
    col: 2,
    cardName: Paths.DeadEndCross,
    cardType: CardTypeEnum.Path,
  },
  { row: 3, col: 3, cardName: Paths.LeftCurve, cardType: CardTypeEnum.Path },
]
