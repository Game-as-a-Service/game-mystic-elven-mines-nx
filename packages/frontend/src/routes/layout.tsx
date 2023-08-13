import { component$, Slot, useTask$, $, useVisibleTask$, useSignal } from '@builder.io/qwik'
import Header from '../app/components/header/header'
import gameStore from '../core/stores/index'

import '../core/styles/game.css'
import { setUIBg } from '../core/stores/storeUI'
export default component$(() => {
  let bgFileName = useSignal<string>(gameStore.getState().uiBgImg)

  useVisibleTask$(({ cleanup }) => {
    const bgSub = gameStore.subscribe(
      (s) => s.uiBgImg,
      (v) => (bgFileName.value = v)
    )

    cleanup(() => {
      console.log('clean up')
      bgSub.unSubscribe()
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
      ></div>
      <main>
        <Slot />
      </main>
    </>
  )
})
