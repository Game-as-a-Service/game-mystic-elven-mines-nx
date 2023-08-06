import { $, component$ } from '@builder.io/qwik'
import { DocumentHead } from '@builder.io/qwik-city'

import { createGame } from '../../core/network/api'

// import createRoom.css
import './createRoom.css'

export default component$(() => {
  const handleClick = $(async (element: any) => {
    // TODO 串接名字
    // console.log('element.target.value', element.target.value)
    createGame({ host: 'testPlayer' })
  })
  return (
    <>
      <main class="createRoom">
        <div class="flex flex-col w-1/3 ">
          <h1 class="text-white">輸入你的名字</h1>
          <input class="border mb-1"></input>

          <button onClick$={handleClick} class="bg-slate-300 p-1 rounded-sm">
            送出
          </button>
        </div>
      </main>
    </>
  )
})

export const head: DocumentHead = {
  title: 'Demo',
}
