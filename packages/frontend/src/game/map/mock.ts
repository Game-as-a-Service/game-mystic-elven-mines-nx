import { IMapCard } from './mapController'
import { CardTypeEnum, PathCard } from '../../core/types/Card'
import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'

export const mockCards: IMapCard[] = [
  { row: 1, col: 2, cardName: PathCard.Cross, cardType: CardTypeEnum.Path },
  {
    row: 2,
    col: 2,
    cardName: PathCard.DeadEndCross,
    cardType: CardTypeEnum.Path,
  },
  { row: 3, col: 3, cardName: PathCard.LeftCurve, cardType: CardTypeEnum.Path },
]
