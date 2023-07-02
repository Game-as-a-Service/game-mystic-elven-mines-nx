import { CardTypeEnum, Paths } from '../../core/types/Card'

export interface CardType {
  row: number
  col: number
  cardName: Paths | string
  cardType: CardTypeEnum | string
}

export type ColType = CardType & { hasCard: boolean }
type MapType = ColType[][]

export const convertToMapRow = (cardData: CardType[]): MapType => {
  const dataMap = getDefaultMap()
  for (const card of cardData) {
    const { row, col, cardName, cardType } = card
    if (row <= dataMap.length && col <= dataMap[row - 1].length) {
      dataMap[row - 1][col - 1].hasCard = true
      dataMap[row - 1][col - 1].cardName = cardName
      dataMap[row - 1][col - 1].cardType = cardType
    }
  }
  return dataMap
}
const getDefaultMap = (): MapType => {
  const map = Array(7)
    .fill('7行11格')
    .map((_, rIdx) => {
      const col = Array(11)
        .fill('格子')
        .map((_, cIdx) => {
          return {
            row: rIdx + 1,
            col: cIdx + 1,
            hasCard: false,
            cardName: '',
            cardType: '',
          } as ColType
        })

      return col
    })

  return map
}
