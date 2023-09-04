import { $, component$, useVisibleTask$ } from '@builder.io/qwik'

import { joinRoomByNameAndId, setGameIdToLocal } from '../../../core/controllers/roomController'
import { useLocation, useNavigate } from '@builder.io/qwik-city'
import api from 'packages/frontend/src/core/network/api'
import { gameBase } from 'packages/frontend/src/core/gameBase'
import { connectRoomSocket } from 'packages/frontend/src/core/network/socket'

export default component$(() => {
  const loc = useLocation()
  const gameId = loc.params.gameId

  useVisibleTask$(async () => {
    const gameIdEl = document.querySelector('#game-id') as HTMLInputElement
    gameIdEl.value = gameId
  })

  const nav = useNavigate()
  const goRoom = $(async () => {
    const name: string = (document.querySelector('#name') as HTMLInputElement)?.value || ''
    const gameId: string = (document.querySelector('#game-id') as HTMLInputElement)?.value || ''
    const res = await joinRoomByNameAndId(name, gameId)
    if (res) nav(`/room/${gameId}`)
  })

  return (
    <main class="flex flex-col items-center justify-center align-center h-screen">
      <section class="flex flex-col   justify-center bg-[rgb(0,0,0,0.5)] p-5">
        <h1 class="text-white">加入目前存在的遊戲</h1>
        <small class="text-gray-300">輸入你的名字</small>
        <br />

        <div class="flex flex-col max-w-[500px] space-y-3">
          <input id="name" class="border mb-1" placeholder="你的名字"></input>
          <input id="game-id" class="border mb-1" placeholder="房間Id"></input>
          <button onClick$={goRoom} class="bg-slate-300 p-1 rounded-sm">
            送出
          </button>
        </div>
      </section>
    </main>
  )
})
