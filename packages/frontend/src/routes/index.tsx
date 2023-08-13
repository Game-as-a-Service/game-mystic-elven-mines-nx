import { component$, $ } from '@builder.io/qwik'
import type { DocumentHead } from '@builder.io/qwik-city'

import { setUIBg } from '../core/stores/storeUI'

export default component$(() => {
  setUIBg('create')

  const gotoCreatePage = $(() => {
    window.location.href = './create'
  })

  const gotoJoinPage = $(() => {
    window.location.href = './join'
  })

  return (
    <main class="flex flex-col items-center justify-center w-[500px] align-center h-screen">
      <section class="flex flex-col  justify-center bg-[rgb(0,0,0,0.5)] p-5">
        <h1 class="text-white">神秘精靈礦</h1>
        <small class="text-gray-300">遊戲大廳</small>
        <br />

        <div class="flex flex-col max-w-[500px] space-y-3">
          <button class="bg-white rounded p-3" onClick$={gotoCreatePage}>
            新創遊戲房間
          </button>
          <button class="bg-white rounded p-3" onClick$={gotoJoinPage}>
            加入遊戲房間
          </button>
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
