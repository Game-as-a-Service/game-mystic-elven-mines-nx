import { component$, useStore, useTask$, useVisibleTask$ } from '@builder.io/qwik'

import gameStore, { IGameStore } from '../../core/stores'

export default component$(() => {
  const store = useStore(gameStore.getState())

  useVisibleTask$(({ cleanup }) => {
    counter(cleanup)
    gameStore.subscribe(({ gameId }) => (store.gameId = gameId))
  })

  return (
    <div class="border-2 border-slate-300 p-2 ">
      <span class="text-yellow-300">store tester: </span>
      <span class="text-white">{store.gameId}</span>
    </div>
  )
})

const counter = (cleanup: any) => {
  let count = 0
  const interval = setInterval(() => {
    count++
    gameStore.setState({ gameId: count + '' })
  }, 1000)
  cleanup(() => clearInterval(interval))
}
