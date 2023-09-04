import { component$ } from '@builder.io/qwik'
import TesterCard from '../../../game/tester.card'
import { CardTypeEnum, Paths } from '../../../core/types/Card'
import { setUIBg } from '../../../core/stores/storeUI'

import Map from '../../../game/map'
import Players from '../../../game/players'

import BtnShareUI from '../../../game/components/btnShare'

export default component$(() => {
  setUIBg('game')

  return (
    <>
      <Players />
      <BtnShareUI />
      {/* 測試=====START */}
      {/* 狀態管理測試 */}
      {/* <TesterStore /> */}
      {/* api測試 */}
      {/* 測試=====END */}
      {/* 左 - 玩家資訊 */}
      <Map></Map> {/* 中 - 大地圖 */}0{/* 中 - 卡牌堆 */}
      {/* 下 - 玩家手牌 */}
      <TesterCard cName={Paths.Cross} cType={CardTypeEnum.Paths} left="100px" color="red" />
      <TesterCard cName={Paths.Cross} cType={CardTypeEnum.Paths} left="220px" color="green" />
      <TesterCard cName={Paths.Cross} cType={CardTypeEnum.Paths} left="340px" color="yellow" />
      {/* 右 - 系統按鈕 */}
      {/* 共用 - 通知列 */}
    </>
  )
})
