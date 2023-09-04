import { $, component$, Slot, useSignal, useVisibleTask$ } from '@builder.io/qwik'
import { Image } from '@unpic/qwik'

import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'
import { convertToMapData, ColListType, IMapCard } from './mapController'
import { mockCards } from './mock'
import './map.css'

import useGameStore, { gameStore } from '../../core/stores'
import { setMap } from '../../core/stores/storeUI'

// NOTE
// 目标x坐标 = 第10格的左边界 + （格子宽度 - 卡牌宽度）/ 2
// 目标y坐标 = 第10格的上边界 + （格子高度 - 卡牌高度）/ 2

// 資料
const mockData = convertToMapData(mockCards)

// 地圖
export default component$(() => {
  const data = useSignal<ColListType>(mockData)

  useVisibleTask$(() => {
    // gameStore.set('map', mockData)
    // gameStore.on('map', (v) => (data.value = v))
    // gameStore.on('map', (v) => console.log('map', v))
    // useGameStore.subscribe(({ map }) => (data.value = map))
  })

  return (
    <div class="map">
      {data.value.map((r, y) => (
        <div key={`row-${y}`} class="map-row">
          {r.map((col, x) => (
            <ColData key={'col-' + x} {...{ x, y, ...col }}>
              <MapCard q:slot="SlotCard" key={`mapCard-${x}-${col.cardName}`} {...col} />
            </ColData>
          ))}
        </div>
      ))}
    </div>
  )
})

const ColData = component$((props: any) => {
  //console.log('props', props)
  // 格子
  const { selfY, selfX, hasCard } = { ...props, selfX: props.x + 1, selfY: props.y + 1, hasCard: props.hasCard }

  // click
  const clickCol = $(() => {
    //console.log({ selfY, selfX })
    if (hasCard) return

    const hasSelectedCard = Boolean(useGameStore.getState().selectedCard?.cardName)
    if (!hasSelectedCard) return

    const map = gameStore.get('map')
    const card = gameStore.get('selectedCard')

    const mapCards = map.map((r) => {
      r.map((mapCard) => {
        if (mapCard.row === selfY && mapCard.col === selfX) {
          mapCard.hasCard = true
          mapCard.cardName = card?.cardName || 'no name'
          mapCard.cardType = card?.cardType || 'no path name'
        }
      })
      return r
    })

    setMap(mapCards)
  })

  return (
    <div key={'row-' + selfY + '-' + selfX} class="map-col" onClick$={() => clickCol()}>
      <Slot name="SlotCard" />
    </div>
  )
})

export const MapCard = component$((props: IMapCard & { hasCard: boolean }) => {
  return props?.hasCard ? (
    <button class="absolute w-full h-full left-0 right-0">
      <Image class="w-full" src={getImageUrlByApiCardName(props.cardName)} alt="地圖卡"></Image>
    </button>
  ) : (
    <div class="opacity-0">
      <Image src={getImageUrlByApiCardName(props.cardName)} alt="地圖卡"></Image>
    </div>
  )
})
