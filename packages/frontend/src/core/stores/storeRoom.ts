import gameStore from '.'
import { IApiCreateGame } from '../network/api/type'

// Room
export const setGameInfo = (gameInfo: IApiCreateGame) => gameStore.setState({ roomInfo: gameInfo })
