import { component$, $, useVisibleTask$ } from '@builder.io/qwik'
import type { DocumentHead } from '@builder.io/qwik-city'
import { gameStore, useGameStore } from '../core/stores/index'

import { setUIBg } from '../core/stores/storeUI'

export default component$(() => {
  setUIBg('create')

  const gotoCreatePage = $(() => {
    window.location.href = './create'
  })

  const gotoJoinPage = $(() => {
    window.location.href = './join/'
  })

  useVisibleTask$(() => {
    console.log('useVisibleTask$')
    gameStore.on('roomInfo', (v, t) => console.log('roomInfo', v, t))
  })

  return (
    <main class="flex flex-col items-center justify-center w-[500px] align-center h-screen">
      <section class="flex flex-col  justify-center bg-[rgb(0,0,0,0.5)] p-5">
        <h1 class="text-white">神秘精靈礦</h1>
        <small class="text-gray-300">遊戲大廳</small>
        <br />

        <div class="flex flex-col max-w-[500px] space-y-3">
          <button class="bg-white rounded p-3" onPointerUp$={gotoCreatePage}>
            新創遊戲房間
          </button>
          {/* <button class="bg-white rounded p-3" onPointerUp$={gotoJoinPage}>
            加入遊戲房間
          </button> */}
        </div>
      </section>
    </main>
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
