import { component$, useSignal, useStore, useTask$, useVisibleTask$ } from '@builder.io/qwik'
import { queryGame } from '../../core/controllers/roomController'
import { IPlayer } from '../../core/network/api/type'
import { PlayerData } from './playerData'

// 左方玩家資料
export default component$(() => {
  const room = useStore<Record<'players', IPlayer[]>>({ players: [] })

  useVisibleTask$(async ({ track }) => {
    track(() => room)
    const data = await queryGame()
    room.players = data.players
  })

  return (
    <div class="fixed z-[100] top-2 left-2 border bg-[rgb(255,255,255,0.6)] p-5 rounded w-[300px]">
      <h1 class="text-2xl text-black">線上玩家</h1>
      {room.players?.map((player: IPlayer) => {
        return <PlayerData key={player.id} name={player.name} id={player.id} />
      })}
    </div>
  )
})
