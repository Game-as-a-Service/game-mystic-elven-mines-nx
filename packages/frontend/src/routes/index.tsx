import { component$ } from '@builder.io/qwik'
import type { DocumentHead } from '@builder.io/qwik-city'

import Map from '../game/map'
import TesterStore from '../game/tester.zustand'
import TesterApi from '../game/tester.api'

export default component$(() => {
  return (
    <>
      {/* 測試=====START */}

      {/* 狀態管理測試 */}
      <TesterStore />

      {/* api測試 */}
      <TesterApi />

      {/* 測試=====END */}

      {/* 左 - 玩家資訊 */}
      <Map></Map> {/* 中 - 大地圖 */}

      {/* 中 - 卡牌堆 */}

      {/* 下 - 玩家手牌 */}

      {/* 右 - 系統按鈕 */}

      {/* 共用 - 通知列 */}
    </>
  )
})

export const head: DocumentHead = {
  title: '神秘精靈礦 Mystic Elven Mines',
  meta: [
    {
      name: '神秘精靈礦 Mystic Elven Mines - 水球軟體學院遊戲. Qwik & Java',
      content: 'Qwik site description',
    },
  ],
}
