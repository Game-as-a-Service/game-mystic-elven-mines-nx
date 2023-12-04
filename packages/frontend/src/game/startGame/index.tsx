import { component$, useStore, useVisibleTask$,$ } from '@builder.io/qwik'
import { gameStore } from '../../core/stores/index'
import clsx from 'clsx'
import api from '../../core/network/api'


import { gameBase } from '../../core/controllers/initGameBase'
export default component$(() => {

  const store = useStore(
    {
      isShowStartBtn: false,
      moreThan3: false,
    },
    { deep: true }
  )


  useVisibleTask$(() => {
    gameStore.on('roomPlayerNameList', (list, preList) => {
      {
        if(list === preList)return
        if (list.length >= 3) {
          console.log('more than 3 ',store.moreThan3 = true)
          store.isShowStartBtn = true
        }
        else store.moreThan3 = false
      }})

      gameStore.on('gameProgress', (progress) => {
        if(['GAME_STARTED'].includes(progress)) {
          store.isShowStartBtn= false
        }
      })
  })

  const handleStartGame =$(()=>{
    const playerName = gameBase.playerName
      if(playerName) api.gameStart( { playerName })
  })

  return (
    store.isShowStartBtn ? (
      <div class={clsx('fixed w-full h-full flex items-center justify-center')}>

          <button class="px-6 h-[40px] bg-blue-900 text-white text-center font-bold text-36"onPointerUp$={handleStartGame}>開始遊戲</button>

      </div>
    ) : null
  )
})
