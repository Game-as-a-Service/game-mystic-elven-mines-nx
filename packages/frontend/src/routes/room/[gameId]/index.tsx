import { component$, $, useStore, useVisibleTask$, useSignal } from '@builder.io/qwik'
import { useLocation, useNavigate } from '@builder.io/qwik-city'
import { isServer } from '@builder.io/qwik/build'

import { setToastMessage, setUIBg } from '../../../core/stores/storeUI'
import { connectRoomSocket } from '../../../core/network/socket'


import { gameBase } from '../../../core/gameBase'

import Map from '../../../game/map'
import PlayingHands from '../../../game/handCards'
import LeftPlayers from '../../../game/leftPlayers'
import PlayerData from '../../../game/leftPlayers/playerData'
import RightActions from '../../../game/rightActions'

import { IPlayer } from '../../../core/network/api/type'
import { LocalStorageKey } from '../../../core/controllers/roomController'
import StartGame from 'packages/frontend/src/game/startGame'

interface IStore {
  playerName: string
  players: IPlayer[]
  hasWelcome: boolean //第一次進房間會有歡迎詞
}

export default component$(() => {
  setUIBg('game')

  const nav = useNavigate()
  const loc = useLocation()
  const copyValue = useSignal<any>(loc.url.origin + '/join/' + loc.params.gameId || '')
  const store = useStore<IStore>({ playerName: '', players: [], hasWelcome: false })

  // 連接socket
  useVisibleTask$(() => {
    const gameId = localStorage.getItem(LocalStorageKey.GAME_ID) || ''
    const playerId = localStorage.getItem(LocalStorageKey.PLAYER_ID) || ''
    store.playerName = localStorage.getItem(LocalStorageKey.PLAYER_NAME) || ''
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


  // Actions click
  const clickShareToFriend = $(async () => {

    const textArea = document.getElementById('copy-area');
    if(textArea){
    try {
      textArea?.select();
      document.execCommand('copy');
      } catch (err) {
        console.error('Unable to copy', err);
      }
      finally{
        setToastMessage('url已複製到剪貼簿, 請分享給朋友')
        textArea.blur();
      }
    }
  }
  )
  const clickExit = $(() => {
    gameBase.socket?.disconnect() //斷開socket
    nav(`/`)
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

        <RightActions data={{clickShareToFriend,clickExit}}/>
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

