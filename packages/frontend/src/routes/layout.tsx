import { component$, Slot, useVisibleTask$, useSignal } from '@builder.io/qwik'
import useGameStore, { gameStore } from '../core/stores/index'

import '../core/styles/game.css'
import ToastMessage from '../game/toastMessage'
import StartGame from '../game/startGame'

export default component$(() => {
  const bgFileName = useSignal<'bg-game-01' | 'bg-create'>(useGameStore.getState().uiBgImg)

  useVisibleTask$(({ cleanup }) => {
    const sub = gameStore.on('uiBgImg', (v) => (bgFileName.value = v))

    cleanup(() => {
      sub.unsubscribe()
      console.log('clean up')
    })
  })

  return (
    <>
      <div
        id="game-bg"
        class="game-bg"
        style={{
          backgroundImage: `url('/images/bg/${bgFileName.value}.jpg')`,
        }}
      >
        <Slot />
      </div>

      <ToastMessage />
    </>
  )
})
