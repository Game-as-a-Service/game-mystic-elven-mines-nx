import useGameStore, { gameStore } from '.'
import { IApiCreateGame, IHandCard, IPlayer, IPlayerMap } from '../network/api/type'

export const setRoomInfo = (gameInfo: IApiCreateGame) => useGameStore.setState({ roomInfo: gameInfo })

export const setRoomPlayerNameList = (list: string[]) => {
  const oldNameList = useGameStore.getState().roomPlayerNameList
  console.log('oldNameList', oldNameList)
  oldNameList.forEach((name) => {
    if (!list.includes(name)) {
      console.log(name, '加入了遊戲')
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

export const setGameActionPlayerName = (playerName: string) => {
  useGameStore.setState({ gameActionPlayerName: playerName })
}

export const setRoomMyCards = (cards: IHandCard[]) => {
  useGameStore.setState({ roomMyCards: { data: mockRoomMyCards } })
  //useGameStore.setState({ roomMyCards: { data: data } })
}

export const setRoomMyRole = (role: 'ELVEN' | 'GOBLIN' | undefined) => {
  useGameStore.setState({ roomMyRole: role })
}

export const mockRoomMyCards = [
  {
    type: 'PATH',
    name: 'Horizontal',
    flipped: false,
  },
  {
    type: 'PATH',
    name: 'Horizontal',
    flipped: false,
  },
  {
    type: 'PATH',
    name: 'Horizontal',
    flipped: false,
  },
  {
    type: 'PATH',
    name: 'Horizontal',
    flipped: false,
  },
  {
    type: 'PATH',
    name: 'Horizontal',
    flipped: false,
  },
]
