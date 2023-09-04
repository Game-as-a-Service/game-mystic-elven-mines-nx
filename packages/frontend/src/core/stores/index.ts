import { StoreApi, createStore } from 'zustand/vanilla'
import { SelectCardType } from './typing'
import { IApiCreateGame, IApiQueryGame } from '../network/api/type'
import { ColListType } from '../../game/map/mapController'

interface IGameState {
  roomInfo: IApiCreateGame | null
  roomPlayers: IApiQueryGame | null
  map: ColListType
  selectedCard: SelectCardType | null
  uiBgImg: 'bg-game-01' | 'bg-create'
  showToastMessage: string | null
}

export const useGameStore = createStore<IGameState>((set) => ({
  // Room
  roomInfo: null,
  roomPlayers: null,

  // Game
  map: [[]],
  selectedCard: null,

  // UI
  uiBgImg: 'bg-game-01',
  showToastMessage: null,
}))

// 封裝的功能
type cbType<T> = (state: T, prevState: T) => any
interface IGameStore<T> {
  on: <K extends keyof T>(
    selector: K,
    callback: cbType<T[K]>
  ) => {
    /**
     * 取消訂閱
     * @returns {void}
     */
    unsubscribe: () => void
  }
  set: <K extends keyof T>(selector: K, value: T[K]) => void
  get: <K extends keyof T>(selector: K) => T[K]
}

export const gameStore: IGameStore<IGameState> = {
  on: (selector, callback) => {
    const unsubscribe = useGameStore.subscribe((state, prevState) => callback(state[selector], prevState[selector]))
    return unsubscribe
  },
  set: (selector, value) => useGameStore.setState({ [selector]: value }),
  get: (selector) => useGameStore.getState()[selector],
}

export default useGameStore
