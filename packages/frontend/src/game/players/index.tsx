import { component$, useSignal, useStore, useTask$, useVisibleTask$ } from '@builder.io/qwik'
import { queryGame } from '../../core/controllers/roomController'
import { IPlayer } from '../../core/network/api/type'

// 玩家

// 這裡要串接webSocket玩家資料
export default component$(() => {
  const room = useStore<Record<'players', IPlayer[]>>({ players: [] })

  useVisibleTask$(async ({ track }) => {
    track(() => room)
    const data = await queryGame()
    console.log('data', data)
    room.players = data.players
  })

  return (
    <div class="fixed z-[100] top-2 left-2 border bg-[rgb(255,255,255,0.6)] p-5 rounded w-[300px]">
      <h1 class="text-2xl text-black">線上玩家</h1>
      {room.players?.map((p: IPlayer) => {
        return (
          <section key={p.id} class="text-black">
            <h2 class="font-bold">{p.name}</h2>
            <small class="text-sm">{p.id}</small>
          </section>
        )
      })}
    </div>
  )
})
