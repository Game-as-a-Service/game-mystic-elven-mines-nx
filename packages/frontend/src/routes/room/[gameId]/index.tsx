import { component$, useVisibleTask$ } from '@builder.io/qwik'

import { setUIBg } from '../../../core/stores/storeUI'
import { connectRoomSocket } from 'packages/frontend/src/core/network/socket'

import Map from '../../../game/map'
import Players from '../../../game/players'
import PlayingHands from '../../../game/handCards'
import BtnShare from '../../../game/components/btnShare'

export default component$(() => {
  setUIBg('game')

  useVisibleTask$(() => {
    
    // 連接socket
    const gameId = localStorage.getItem('gameId') || ''
    const userId = localStorage.getItem('userId') || ''
    if (gameId && userId) connectRoomSocket({ gameId, userId })
  })

  return (
    <>
      <BtnShare />
      <Players />
      <PlayingHands />
      <Map></Map>
    </>
  )
})
