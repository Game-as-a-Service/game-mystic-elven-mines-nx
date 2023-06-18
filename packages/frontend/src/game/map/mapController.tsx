import { JSXChildren } from '@builder.io/qwik'
import { MapCard } from './index'

export type CardType = {
  row: number
  col: number
  cardName: string
  cardType: string
}

// TODO: 這裡需要優化, 因為UI跟資料沒有完全抽離

export const renderMap = (mockCards: CardType[]) => {
  console.log('renderMap')
  const map: JSXChildren[] = []

  Array(7)
    .fill('0')
    .forEach((_, rowIndex) => {
      const rowDiv = <div class="map-row">{renderMapGrid(rowIndex, mockCards)}</div>
      map.push(rowDiv)
    })

  return map
}

export const renderMapGrid = (rowIndex: number, mockCards: CardType[]) => {
  const mapGrid: JSXChildren[] = []

  Array(11)
    .fill('0')
    .forEach((_, currentGrid) => {
      const card = mockCards.find((card) => card.row === rowIndex && card.col === currentGrid)

      if (card) {
        const hasCardDiv = (
          <div class="map-grid">
            <MapCard {...card} />
          </div>
        )
        mapGrid.push(hasCardDiv)
      } else {
        const noCardDiv = <div class="map-grid"></div>
        mapGrid.push(noCardDiv)
      }
    })

  return mapGrid
}
