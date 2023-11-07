import useGameStore, { gameStore } from '.'
import { IApiCreateGame, IPlayer, IPlayerMap } from '../network/api/type'

export const setRoomInfo = (gameInfo: IApiCreateGame) => useGameStore.setState({ roomInfo: gameInfo })

export const setRoomPlayerNameList = (list: string[]) => {
  const oldNameList = useGameStore.getState().roomPlayerNameList
  oldNameList.forEach((name) => {
    if (!list.includes(name)) {
      console.log(name, '加入了遊戲', list)
    }
  })
  useGameStore.setState({ roomPlayerNameList: list })
}

export const setRoomPlayers = (players: IPlayer[]) => {
  useGameStore.setState({ roomPlayers: players })
  players.forEach((player, index) => {
    console.log('player' + index, player)
    setSinglePlayerMap(player)
  })
}

export const setSinglePlayerMap = (player: IPlayer) => {
  const roomPlayersMap: IPlayerMap = gameStore.get('roomPlayersMap')
  roomPlayersMap[player.playerName] = { ...player }
  useGameStore.setState({ roomPlayersMap })
}

export const setGameProgress = (progress: string) => {
  useGameStore.setState({ gameProgress: progress })
}
