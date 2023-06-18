import { component$ } from '@builder.io/qwik'
import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'
import { renderMap, CardType } from './mapController'
import { mockCards } from './mock'
import './map.css'

// 桌上地圖(格)
export default component$(() => {
  return <div class="map">{renderMap(mockCards)}</div>
})

// 桌上卡牌(一張)
export const MapCard = component$((card: CardType) => {
  return (
    <>
      <div class="relative text-left">
        <small class="absolute top-[50%]">{card.cardName}</small>
        <image src={getImageUrlByApiCardName(card.cardName)}></image>
      </div>
    </>
  )
})
