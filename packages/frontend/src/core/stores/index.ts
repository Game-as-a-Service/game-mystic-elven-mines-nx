import { subscribeWithSelector } from 'zustand/middleware'
import { createStore } from 'zustand/vanilla'
import { IGameStore } from './typing'
import { IApiGames } from '../network/api/type'

const gameStore = createStore(
  subscribeWithSelector<IGameStore>(() => ({
    gameInfo: null,
    map: [[]],
    selectedCard: null,
  }))
)
export default gameStore

// Setters
export const setGameInfo = (gameInfo: IApiGames) => gameStore.setState({ gameInfo })

// Debug
if (typeof window !== 'undefined') window.gameStore = gameStore
