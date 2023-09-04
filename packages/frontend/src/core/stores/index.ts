import { createStore } from 'zustand/vanilla'
import { SelectCardType } from './typing'
import { IApiCreateGame, IPlayer } from '../network/api/type'
import { ColListType } from '../../game/map/mapController'
import { gameBase } from '../../core/gameBase'

interface IGameState {
  roomInfo: IApiCreateGame | null
  roomPlayers: IPlayer[]
  map: ColListType
  selectedCard: SelectCardType | null
  uiBgImg: 'bg-game-01' | 'bg-create'
  toastMessage: string | null //null就是關閉
}

export const useGameStore = createStore<IGameState>((set) => ({
  // Room
  roomInfo: null,
  roomPlayers: [],

  // Game
  map: [[]],
  selectedCard: null,

  // UI
  uiBgImg: 'bg-game-01',
  toastMessage: null,
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
  getAll: () => useGameStore.getState(),
}

Object.assign(gameBase, { gameStore })
export default useGameStore
