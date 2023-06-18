import { subscribeWithSelector } from 'zustand/middleware'
import { createStore } from 'zustand/vanilla'

export interface IGameStore {
  gameId: string
  // setGameId: (gameId: string) => void
}
const gameStore = createStore(
  subscribeWithSelector<IGameStore>(() => ({
    //
    gameId: 'nosGame',
    // setGameId: (gameId: string) => set({ gameId }),
  }))
)
export default gameStore
