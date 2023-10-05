import { component$, $, useStore, useVisibleTask$ } from '@builder.io/qwik'

import { isServer } from '@builder.io/qwik/build'

import { setToastMessage, setUIBg } from '../../../core/stores/storeUI'
import { connectRoomSocket } from '../../../core/network/socket'

import Map from '../../../game/map'
import Players from '../../../game/players'
import PlayingHands from '../../../game/handCards'
import BtnCircleUI from '../../../game/components/btnCircleUI'
import { useLocation, useNavigate } from '@builder.io/qwik-city'
import { gameBase } from '../../../core/gameBase'
import { PlayerData } from '../../../game/players/playerData'
import { gameStore } from '../../../core/stores'
import { IPlayer } from 'packages/frontend/src/core/network/api/type'
import { LocalStorageKey } from 'packages/frontend/src/core/controllers/roomController'

interface IStore {
  playerName: string
  players: IPlayer[]
  hasWelcome: boolean //第一次進房間會有歡迎詞
}

export default component$(() => {
  setUIBg('game')
  const store = useStore<IStore>({ playerName: '', players: [], hasWelcome: false })
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

  // 連接socket
  useVisibleTask$(() => {
    gameStore.on('roomPlayers', (list) => (store.players = list))
    const gameId = localStorage.getItem(LocalStorageKey.GAME_ID) || ''
    const playerId = localStorage.getItem(LocalStorageKey.PLAYER_ID) || ''
    store.playerName = localStorage.getItem(LocalStorageKey.PLAYER_NAME) || ''

    console.log('準備打socket',{gameId,playerId})
    if (gameId && playerId) connectRoomSocket({ gameId,playerId })
  })

  // welcome
  useVisibleTask$(({ track }) => {
    if (!store.hasWelcome) {
      track(() => store.players)
      const value = store.players
      const update = () => (store.players = value)
      isServer
        ? update() // don't delay on server render value as part of SSR
        : delay(2000).then(update) // Delay in browser
      if (value?.length > 0) {
        setToastMessage('歡迎來到精靈礦坑,目前有' + value?.length + '位玩家在遊戲中')
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
              <PlayerData key={'player-me'} playerName={store.playerName} id={'player-me'} color="me" />
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
    </main>
  )
})

const delay = (time: number) => new Promise((res) => setTimeout(res, time))
