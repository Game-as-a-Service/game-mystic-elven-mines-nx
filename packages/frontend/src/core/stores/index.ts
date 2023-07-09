import { subscribeWithSelector } from 'zustand/middleware'
import { createStore } from 'zustand/vanilla'
import { IGameStore } from './typing'

const gameStore = createStore(
  subscribeWithSelector<IGameStore>(() => ({
    gameId: 'nosGame',
    map: [[]],
    selectedCard: null,
    // setGameId: (gameId: string) => set({ gameId }),
  }))
)
export default gameStore
