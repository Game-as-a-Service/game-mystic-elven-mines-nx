import { component$, useStore, useVisibleTask$,task$ } from '@builder.io/qwik'
import { gamePlayers } from '../../core/controllers/roomController'
import  PlayerData  from './playerData'
import { setRoomPlayerNameList, setRoomPlayers } from '../../core/stores/storeRoom'
import { gameStore } from '../../core/stores'

// 左方玩家資料
type IPlayerLeftInfo =  { list:string[] }

export default component$(() => {
  const room = useStore<IPlayerLeftInfo>({list:[]})

  useVisibleTask$(async () => {
    const {players} = await gamePlayers()
    const playerNameList = players.map(x=>x.playerName)
    setRoomPlayers(players)
    setRoomPlayerNameList(playerNameList)

    gameStore.on('roomPlayerNameList', (list)=>{
      room.list = list
    })
  })

  return (
    <>
      {room.list?.map((playerName: string) => {
        return <PlayerData key={playerName} playerName={playerName} color="players" />
      })}
    </>
  )
})
function Task$(arg0: ({ track }: { track: any }) => void) {
  throw new Error('Function not implemented.')
}

