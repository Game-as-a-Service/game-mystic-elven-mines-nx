import useGameStore from '.'
import { IApiCreateGame, IPlayer } from '../network/api/type'

// Room
export const setRoomInfo = (gameInfo: IApiCreateGame) => useGameStore.setState({ roomInfo: gameInfo })

export const setRoomPlayers = (players: IPlayer[]) => useGameStore.setState({ roomPlayers: players })

