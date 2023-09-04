import { component$, $, useStore, useVisibleTask$ } from '@builder.io/qwik'

import { setToastMessage, setUIBg } from '../../../core/stores/storeUI'
import { connectRoomSocket } from '../../../core/network/socket'

import Map from '../../../game/map'
import Players from '../../../game/players'
import PlayingHands from '../../../game/handCards'
import BtnCircleUI from '../../../game/components/btnCircleUI'
import { useLocation, useNavigate } from '@builder.io/qwik-city'
import { gameBase } from '../../../core/gameBase'
import { PlayerData } from '../../../game/players/playerData'

export default component$(() => {
  setUIBg('game')
  const store = useStore({ userName: '' })
  const nav = useNavigate()
  const loc = useLocation()

  const clickShareToFriend = $(async () => {
    const url = loc.url.origin + '/join/' + loc.params.gameId
    await navigator.clipboard.writeText(url) // copy url to clipboard
    setToastMessage('url已複製到剪貼簿, 請分享給朋友')
  })

  const clickExit = $(() => {
    gameBase.socket?.disconnect() //斷開socket
    nav(`/`)
  })

  useVisibleTask$(() => {
    // 連接socket
    const gameId = localStorage.getItem('gameId') || ''
    const userId = localStorage.getItem('userId') || ''
    store.userName = localStorage.getItem('userName') || ''
    console.log('store.userName', store.userName)

    if (gameId && userId) connectRoomSocket({ gameId, userId })
  })

  return (
    <main class="relative over-flow-hidden h-screen flex flex-col">
      {/* Player Map Button */}
      <article class="flex flex-row justify-between over-flow-hidden">
        <section class="p-5 flex-1 flex flex-col gap-2">
          <div class="text-[#FFE794] font-bold text-center">神秘精靈礦</div>
          <Players />
        </section>
        <section class="p-5 flex-initial w-2/3 h-screen overflow-scroll no-scrollbar">
          <Map></Map>
          <div class="h-[5.5rem]" />
        </section>
        <section class="p-5 pl-0 flex-1 flex flex-row justify-between">
          <div class="opacity-0">_</div>
          <div class="flex flex-col gap-3">
            <BtnCircleUI>1/3</BtnCircleUI>
            <BtnCircleUI cb={clickExit}>EXIT</BtnCircleUI>
            <BtnCircleUI cb={clickShareToFriend}>邀請朋友</BtnCircleUI>
          </div>
        </section>
      </article>

      {/* Me HandCard  */}
      <article class="absolute bottom-0 w-full">
        <div class="relative w-full flex flex-row justify-between">
          <section class="p-5 flex-1 flex flex-col gap-2">
            <div class="h-[2rem]">
              <PlayerData key={'player-me'} name={store.userName} id={'player-me'} color="me" />
            </div>
            <div class="h-5"></div>
          </section>
          <section class="p-5 flex-initial w-2/3 flex flex-row justify-center">
            <div class=" w-2/3 fixed bottom-[-2.5rem]">
              <PlayingHands />
            </div>
          </section>
          <section class="p-5 pl-0 flex-1 flex flex-row justify-between"> </section>
        </div>
      </article>
    </main>
  )
})
