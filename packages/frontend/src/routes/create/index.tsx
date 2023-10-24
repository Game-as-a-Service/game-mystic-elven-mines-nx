import { $, component$, useTask$ } from '@builder.io/qwik'
import { DocumentHead, useNavigate } from '@builder.io/qwik-city'

import { setUIBg } from '../../core/stores/storeUI'
import { createGameAndGetId } from '../../core/controllers/roomController'
import { $dom } from '../../core/utils/dom.util'

export default component$(() => {
  const nav = useNavigate()
  const handleClick = $(async () => {

    const playerName: string = ($dom('#player-name') as HTMLInputElement)?.value || ''
    const gameId = await createGameAndGetId(playerName)
    console.log('創建房間',gameId)

    nav(`/room/${gameId}`)
  })

  useTask$(() => {
    console.log('背景create')
    setUIBg('create')
  })
  return (
    <main class="flex flex-col items-center justify-center align-center h-screen">
      <section class="flex flex-col   justify-center bg-[rgb(0,0,0,0.5)] p-5">
        <h1 class="text-white">創建一個新遊戲</h1>
        <small class="text-gray-300">輸入你的名字</small>
        <br />

        <div class="flex flex-col max-w-[500px] space-y-3">
          <input id="player-name" class="border mb-1"></input>
          <button onPointerUp$={handleClick} class="bg-green-300  p-1 rounded-sm">
            送出
          </button>
        </div>
      </section>
    </main>
  )
})

export const head: DocumentHead = {
  title: 'Demo',
}
