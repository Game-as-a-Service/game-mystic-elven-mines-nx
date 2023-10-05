import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import { gamePlayers } from '../../core/controllers/roomController'
import { IApiGamePlayers, IPlayer, } from '../../core/network/api/type'
import { PlayerData } from './playerData'
import { setRoomPlayerNameList, setRoomPlayers } from '../../core/stores/storeRoom'

// 左方玩家資料
export default component$(() => {
  const room = useStore<IApiGamePlayers>({ players: [] })

  useVisibleTask$(async ({ track }) => {
    track(() => room)
    const{players} = await gamePlayers()
    console.log('api gamePlayers', players)
    room.players = players
    setRoomPlayers(players)
    setRoomPlayerNameList(players.map(x=>x.playerName))
  })

  return (
    <>
      {room.players?.map((player: IPlayer) => {
        return <PlayerData key={player.playerName} playerName={player.playerName} id={player.playerName} color="players" />
      })}
    </>
  )
})
