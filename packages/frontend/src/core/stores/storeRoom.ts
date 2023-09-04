import useGameStore from '.'
import { IApiCreateGame } from '../network/api/type'

// Room
export const setGameInfo = (gameInfo: IApiCreateGame) => useGameStore.setState({ roomInfo: gameInfo })
  