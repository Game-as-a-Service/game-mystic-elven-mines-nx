import { CardType } from './mapController'
import { CardTypeEnum, Paths } from '../../core/types/Card'
import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'

export const mockCards: CardType[] = [
  { row: 1, col: 2, cardName: Paths.Cross, cardType: CardTypeEnum.Paths },
  {
    row: 1,
    col: 4,
    cardName: Paths.DeadEndCross,
    cardType: CardTypeEnum.Paths,
  },
  { row: 5, col: 5, cardName: Paths.LeftCurve, cardType: CardTypeEnum.Paths },
]
