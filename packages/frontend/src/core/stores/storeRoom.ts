import useGameStore, { gameStore } from '.'
import { IApiCreateGame, IPlayer, IPlayerMap } from '../network/api/type'

// Room
export const setRoomInfo = (gameInfo: IApiCreateGame) => useGameStore.setState({ roomInfo: gameInfo })


export const setRoomPlayerNameList = (list:string[])=> {
  const oldNameList = useGameStore.getState().roomPlayerNameList
  oldNameList.forEach((name)=>{
      if(!list.includes(name)) {
        console.log(name,'加入了遊戲')
      }
    }
  )
  useGameStore.setState({ roomPlayerNameList: list})
}

export const setRoomPlayers = (players: IPlayer[]) =>{
  console.log('players',players)
  useGameStore.setState({ roomPlayers: players })
  players.forEach((player)=>{
    setSinglePlayerMap(player)
  })
}

export const setSinglePlayerMap = (player:IPlayer) =>{
  const roomPlayersMap:IPlayerMap = gameStore.get('roomPlayersMap')
  roomPlayersMap[player.playerName] = {...player}
  useGameStore.setState({ roomPlayersMap })
  console.log('playerMap updated',gameStore.get('roomPlayersMap'))
}


export const setGameProgress = (progress:string) =>{
  useGameStore.setState({gameProgress:progress})
}
