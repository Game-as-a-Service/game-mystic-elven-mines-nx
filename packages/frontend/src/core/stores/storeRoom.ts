import useGameStore from '.'
import { IApiCreateGame, IPlayer } from '../network/api/type'

// Room
export const setRoomInfo = (gameInfo: IApiCreateGame) => useGameStore.setState({ roomInfo: gameInfo })

export const setRoomPlayers = (players: IPlayer[]) => useGameStore.setState({ roomPlayers: players })

export const setRoomPlayerNameList = (list:string[])=> {
  const oldList = useGameStore.getState().roomPlayerNameList
    oldList.forEach((x)=>{
      if(!list.includes(x)) {
        console.log(x,'加入了遊戲')
      }
    }
  )
  useGameStore.setState({ roomPlayerNameList: list})
}
