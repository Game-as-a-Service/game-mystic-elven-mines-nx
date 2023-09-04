import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import { queryGame } from '../../core/controllers/roomController'
import { IPlayer } from '../../core/network/api/type'
import { PlayerData } from './playerData'
import { setRoomPlayers } from '../../core/stores/storeRoom'

// 左方玩家資料
export default component$(() => {
  const room = useStore<Record<'players', IPlayer[]>>({ players: [] })

  useVisibleTask$(async ({ track }) => {
    track(() => room)
    const data = await queryGame()
    console.log('api queryGame', data)
    room.players = data.players
    setRoomPlayers(data.players)
  })

  return (
    <>
      {room.players?.map((player: IPlayer) => {
        return <PlayerData key={player.id} name={player.name} id={player.id} color="players" />
      })}
    </>
  )
})
