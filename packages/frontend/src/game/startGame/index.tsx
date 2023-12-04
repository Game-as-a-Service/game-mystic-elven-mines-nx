import { component$, useStore, useVisibleTask$,$ } from '@builder.io/qwik'
import { gameStore } from '../../core/stores/index'
import clsx from 'clsx'
import api from '../../core/network/api'


import { gameBase } from '../../core/controllers/initGameBase'
export default component$(() => {

  const store = useStore(
    {
      isShow: false,
      moreThan3: false,

    },
    { deep: true }
  )


  useVisibleTask$(() => {
      gameStore.on('roomPlayerNameList', (list) => {{
      if(list.length >= 3){
        console.log('more than 3 ',store.moreThan3 = true)
        store.isShow= true
      }
      else store.moreThan3 = false
      }})

      gameStore.on('gameProgress', (progress) => {
        if(['GAME_STARTED'].includes(progress)) {
          store.isShow= false
        }

      })
  })

  const handleStartGame =$(()=>{
    const playerName = gameBase.playerName
      if(playerName) api.gameStart( { playerName })
  })

  return (
    store.isShow ? (
      <div class={clsx('fixed w-full h-full flex items-center')}>
        <div class="flex justify-center items-center w-full h-[40px] text-white text-center font-bold text-36 bg-[rgba(0,0,0,0.6)]">
         <button class="bg-blue-900 text-white p-6 "onPointerUp$={handleStartGame}>開始遊戲</button>
        </div>
      </div>
    ) : null
  )
})
