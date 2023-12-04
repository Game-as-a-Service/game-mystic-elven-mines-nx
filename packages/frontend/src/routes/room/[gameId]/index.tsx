import { component$, useStore, useVisibleTask$, useSignal } from '@builder.io/qwik'
import { useLocation } from '@builder.io/qwik-city'

import { setToastMessage, setUIBg } from '../../../core/stores/storeUI'

import Map from '../../../game/map'
import PlayingHands from '../../../game/handCards'
import LeftPlayers from '../../../game/leftPlayers'
import PlayerData from '../../../game/leftPlayers/playerData'
import RightActions from '../../../game/rightActions'

import { initFirstTimeJoinRoom } from '../../../core/controllers/roomController'
import StartGame from 'packages/frontend/src/game/startGame'
import { connectRoomSocket } from 'packages/frontend/src/core/network/socket'
import { gameStore } from 'packages/frontend/src/core/stores'

interface IStore {
  playerName: string
  playersLength: number
  hasWelcome: boolean //第一次進房間會有歡迎詞
}

export default component$(() => {
  setUIBg('game')

  const loc = useLocation()
  const copyValue = useSignal<any>(loc.url.origin + '/join/' + loc.params.gameId || '')
  const store = useStore<IStore>({ playerName: '', playersLength: 0, hasWelcome: false })

  // 連接socket
  useVisibleTask$(async() => {
    await initFirstTimeJoinRoom(store)
    connectRoomSocket()
  })


  useVisibleTask$(async () => {
    gameStore.on('roomPlayerNameList', (list)=>{
      store.playersLength = list.length
    })
  })

  // welcome
  useVisibleTask$(({ track }) => {
    track(() => store.playersLength);
      if (store.playersLength > 0) {
        if (!store.hasWelcome) {
            setToastMessage('歡迎來到精靈礦坑,目前有' + store.playersLength + '位玩家在遊戲中')
            store.hasWelcome = true
          }
      }
  })



  return (
    <main class="relative over-flow-hidden h-screen flex flex-col">

      {/* Player Map Button */}
      <article class="flex flex-row justify-between over-flow-hidden">
        <section class="p-5 flex-1 flex flex-col gap-2">
          <div class="text-[#FFE794] font-bold text-center">神秘精靈礦</div>

          <textarea id="copy-area" class="fixed bottom-[100vh]">{copyValue}</textarea>
          <LeftPlayers />
        </section>
        <section class="p-5 flex-initial w-2/3 h-screen overflow-scroll no-scrollbar">
          <Map></Map>
          <div class="h-[5.5rem]" />
        </section>

        <RightActions/>
      </article>

      {/* Me HandCard  */}
      <article class="absolute bottom-0 w-full">
        <div class="relative w-full flex flex-row justify-between">
          <section class="p-5 flex-1 flex flex-col gap-2">
            <div class="h-[2rem]">
              <PlayerData key={'player-me'} playerName={store.playerName} color="me" />
            </div>
            <div class="h-5"></div>
          </section>
          <section class="p-5 flex-initial w-2/3 flex flex-row justify-center">
            <div class=" w-2/3 max-w-[650px] fixed bottom-[-2.5rem]">
              <PlayingHands />
            </div>
          </section>
          <section class="p-5 pl-0 flex-1 flex flex-row justify-between"> </section>
        </div>
      </article>

      <StartGame/>
    </main>
  )
})

const delay = (time: number) => new Promise((res) => setTimeout(res, time))

