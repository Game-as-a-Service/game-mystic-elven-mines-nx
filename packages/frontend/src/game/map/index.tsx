import { $, component$, render, Slot, useStore as useQwikStore, useSignal, useVisibleTask$ } from '@builder.io/qwik'
import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'
import { convertToMapRow, MapType, ColType } from './mapController'
import { mockCards } from './mock'
import './map.css'

import gameStore, { IGameStore } from '../../core/stores'

// NOTE
// 目标x坐标 = 第10格的左边界 + （格子宽度 - 卡牌宽度）/ 2
// 目标y坐标 = 第10格的上边界 + （格子高度 - 卡牌高度）/ 2

// 資料
const mockData = convertToMapRow(mockCards)

// 地圖
export default component$(() => {
  let data = useSignal<MapType>(mockData)

  useVisibleTask$(() => {
    gameStore.setState({ map: mockData })
    gameStore.subscribe(({ map }) => {
      data.value = map
      console.log('changed map', map)
    })
  })

  return (
    <div class="map">
      {data.value.map((dataRow, y) => (
        <div key={y + 'row'} class="map-row">
          {dataRow.map((col, x) => (
            <Col {...{ x, y, ...col }}>
              <div q:slot="SlotCard">
                <MapCard key={x + 'mapCard'} {...col} />
              </div>
            </Col>
          ))}
        </div>
      ))}
    </div>
  )
})

const Col = component$((props: any) => {
  console.log('props', props)
  // 格子
  const { selfY, selfX, hasCard } = { ...props, selfX: props.x + 1, selfY: props.y + 1, hasCard: props.hasCard }

  // click
  const clickCol = $(() => {
    console.log({ selfY, selfX })
    if (hasCard) return

    const hasSelectedCard = Boolean(gameStore.getState().selectedCard?.cardName)
    if (!hasSelectedCard) return

    const map = gameStore.getState().map
    const card = gameStore.getState().selectedCard

    const mapCards = map.map((r) => {
      r.map((mapCard) => {
        if (mapCard.row === selfY && mapCard.col === selfX) {
          mapCard.hasCard = true
          mapCard.cardName = card.cardName || 'no name'
          mapCard.cardType = card.cardType || 'no path name'
        }
      })
      return r
    })

    gameStore.setState({ map: mapCards })
  })

  return (
    <div key={'row-' + selfY + '-' + selfX} class="map-col" onClick$={() => clickCol()}>
      <Slot name="SlotCard" />
    </div>
  )
})

export const MapCard = component$((props: any) => {
  return (
    props.hasCard && (
      <button class="relative text-left">
        <small class="absolute top-[50%]">{props.cardName}</small>
        <image src={getImageUrlByApiCardName(props.cardName)}></image>
      </button>
    )
  )
})
