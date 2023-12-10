import { CardTypeEnum, PathCard } from '../../core/types/Card'

export interface IMapCard {
  row: number
  col: number
  cardName: PathCard | string
  cardType: CardTypeEnum | string
}

export type ColType = IMapCard & { hasCard: boolean }
export type ColListType = Array<ColType[]>

export const convertToMapData = (mapCardList: IMapCard[]): ColListType => {
  const map = getDefaultMap()
  for (const card of mapCardList) {
    const { row, col, cardName, cardType } = card
    if (row <= map.length && col <= map[row - 1].length) {
      map[row - 1][col - 1].hasCard = true
      map[row - 1][col - 1].cardName = cardName
      map[row - 1][col - 1].cardType = cardType
    }
  }
  return map
}
const getDefaultMap = (): ColListType => {
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
