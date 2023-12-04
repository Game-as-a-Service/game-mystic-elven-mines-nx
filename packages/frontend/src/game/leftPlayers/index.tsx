import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import PlayerData from './playerData'
import { gameStore } from '../../core/stores'

// 左方玩家資料
type IPlayerLeftInfo =  { list:string[] }

export default component$(() => {
  const room = useStore<IPlayerLeftInfo>({list:[]})

  useVisibleTask$(async () => {
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
