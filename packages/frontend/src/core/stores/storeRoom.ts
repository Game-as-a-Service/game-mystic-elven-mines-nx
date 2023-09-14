import useGameStore from '.'
import { IApiCreateGame, IRoomHost } from '../network/api/type'

// Room
export const setRoomInfo = (gameInfo: IApiCreateGame) => useGameStore.setState({ roomInfo: gameInfo })

export const setRoomPlayers = (players: IRoomHost[]) => useGameStore.setState({ roomPlayers: players })
