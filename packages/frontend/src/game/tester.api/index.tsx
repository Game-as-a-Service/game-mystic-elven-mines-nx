import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import api from '../../core/network/api'
import gameStore from '../../core/stores'

export default component$(() => {
  const store = useStore(gameStore.getState())

  return (
    <>
      <button class="bg-slate-300 p-2" onClick$={() => api.helloTest()}>
        api Hello
      </button>
      <button class="bg-slate-300 p-2" onClick$={() => api.createGame({ host: 'testPlayer' })}>
        api 建立遊戲
      </button>
      <button class="bg-slate-300 p-2" onClick$={() => api.queryGame()}>
        api 查詢遊戲
      </button>
    </>
  )
})
