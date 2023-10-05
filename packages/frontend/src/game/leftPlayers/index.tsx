import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import { gamePlayers } from '../../core/controllers/roomController'
import {  IPlayer, } from '../../core/network/api/type'
import  PlayerData  from './playerData'
import { setRoomPlayerNameList, setRoomPlayers } from '../../core/stores/storeRoom'

// 左方玩家資料
type IPlayerLeftInfo =  { players: IPlayer[],playerNameList:string[] }

export default component$(() => {
  const room = useStore<IPlayerLeftInfo>({players:[],playerNameList:[]})

  useVisibleTask$(async () => {
    const {players} = await gamePlayers()
    const playerNameList = players.map(x=>x.playerName)
    setRoomPlayers(players)
    setRoomPlayerNameList(playerNameList)
    room.players = players
    room.playerNameList =playerNameList

  })

  return (
    <>
      {room.playerNameList?.map((playerName: string) => {
        return <PlayerData key={playerName} playerName={playerName} color="players" />
      })}
    </>
  )
})
