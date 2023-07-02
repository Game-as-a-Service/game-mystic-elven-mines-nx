import { component$ } from '@builder.io/qwik'
import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'
import { convertToMapRow } from './mapController'
import { mockCards } from './mock'
import './map.css'

// NOTE
// 目标x坐标 = 第10格的左边界 + （格子宽度 - 卡牌宽度）/ 2
// 目标y坐标 = 第10格的上边界 + （格子高度 - 卡牌高度）/ 2

// 取得地圖資料
const rowData = convertToMapRow(mockCards)

// 地圖
export default component$(() => {
  return (
    <div class="map">
      {Array(7)
        .fill('')
        .map((_, y) => (
          <div key={'row-' + y} class="map-row">
            {Array(11)
              .fill('')
              .map((_, x) => (
                <div key={'row-' + y + '-' + x} class="map-col">
                  <MapCard {...rowData[y][x]} />
                </div>
              ))}
          </div>
        ))}
    </div>
  )
})

// 桌上卡牌(一張)
export const MapCard = component$((props: any) => {
  return (
    props.hasCard && (
      <>
        <div class="relative text-left">
          <small class="absolute top-[50%]">{props.cardName}</small>
          <image src={getImageUrlByApiCardName(props.cardName)}></image>
        </div>
      </>
    )
  )
})
