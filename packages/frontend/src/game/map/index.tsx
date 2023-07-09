import { $, component$, Slot, useSignal, useVisibleTask$ } from '@builder.io/qwik'

import { Image } from '@unpic/qwik'

import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'
import { convertToMapRow, MapType, MapCardType } from './mapController'
import { mockCards } from './mock'
import './map.css'

import gameStore from '../../core/stores'

// NOTE
// 目标x坐标 = 第10格的左边界 + （格子宽度 - 卡牌宽度）/ 2
// 目标y坐标 = 第10格的上边界 + （格子高度 - 卡牌高度）/ 2

// 資料
const mockData = convertToMapRow(mockCards)

// 地圖
export default component$(() => {
  const data = useSignal<MapType>(mockData)

  useVisibleTask$(() => {
    gameStore.setState({ map: mockData })
    gameStore.subscribe(({ map }) => (data.value = map))
  })

  return (
    <div class="map">
      {data.value.map((r, y) => (
        <div key={`row-${y}`} class="map-row">
          {r.map((col, x) => (
            <Col {...{ x, y, ...col }}>
              <MapCard q:slot="SlotCard" key={`mapCard-${x}-${col.cardName}`} {...col} />
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

export const MapCard = component$((props: MapCardType & { hasCard: boolean }) => {
  let propsData = useSignal(props)
  useVisibleTask$(() => {
    //  console.log('useVisibleTask MapCard', propsData.value)
  })
  return (
    propsData?.value?.hasCard && (
      <button class="relative text-left">
        <small class="absolute top-[50%]">{propsData.value.cardName}</small>
        <Image src={getImageUrlByApiCardName(propsData.value.cardName)} alt="地圖卡"></Image>
      </button>
    )
  )
})
