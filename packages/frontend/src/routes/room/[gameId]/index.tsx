import { component$, useVisibleTask$ } from '@builder.io/qwik'
import TesterCard from '../../../game/tester.card'
import { CardTypeEnum, Paths } from '../../../core/types/Card'
import TesterApi from '../../../game/tester.api'
import { setUIBg } from '../../../core/stores/storeUI'

import { routeLoader$, useLocation } from '@builder.io/qwik-city'
import api from '../../../core/network/api'
import { setGameIdToLocal } from '../../../core/controllers/roomController'
import Map from '../../../game/map'
import Players from 'packages/frontend/src/game/players'

export default component$(() => {
  setUIBg('game')

  const loc = useLocation()
  const gameId = loc.params.gameId

  useVisibleTask$(async () => {
    setGameIdToLocal(gameId)
    const result = await api.queryGame()
  })

  return (
    <>
      <Players />
      {/* 測試=====START */}
      {/* 狀態管理測試 */}
      {/* <TesterStore /> */}
      {/* api測試 */}
      {/* <TesterApi /> */}
      {/* 測試=====END */}
      {/* 左 - 玩家資訊 */}
      <Map></Map> {/* 中 - 大地圖 */}0{/* 中 - 卡牌堆 */}
      {/* 下 - 玩家手牌 */}
      {/* <TesterCard cName={Paths.Cross} cType={CardTypeEnum.Paths} left="100px" color="red" />
      <TesterCard cName={Paths.Cross} cType={CardTypeEnum.Paths} left="220px" color="green" />
      <TesterCard cName={Paths.Cross} cType={CardTypeEnum.Paths} left="340px" color="yellow" /> */}
      {/* 右 - 系統按鈕 */}
      {/* 共用 - 通知列 */}
    </>
  )
})
