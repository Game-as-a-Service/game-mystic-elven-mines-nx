import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import { createGame, findGame, helloTest } from '../../core/network/api'
import gameStore, { IGameStore } from '../../core/stores'

export default component$(() => {
  const store = useStore(gameStore.getState())

  return (
    <>
      <button class="bg-slate-300 p-2" onClick$={() => helloTest()}>
        api Hello
      </button>
      <button class="bg-slate-300 p-2" onClick$={() => createGame({ host: 'testPlayer' })}>
        api 建立遊戲
      </button>
      <button class="bg-slate-300 p-2" onClick$={() => getFindGame()}>
        api 查詢遊戲
      </button>
    </>
  )
})

const getFindGame = async () => {
  const data = await findGame()
  gameStore.setState({ gameId: data.id })
}
