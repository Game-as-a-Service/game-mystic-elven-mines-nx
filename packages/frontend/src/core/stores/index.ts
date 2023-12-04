import { createStore } from 'zustand/vanilla'
import { IApiCreateGame, IHandCard, IPlayer, IPlayerMap } from '../network/api/type'
import { ColListType } from '../../game/map/mapController'
import { gameBase } from '../controllers/initGameBase'

interface IGameState {
  // Room
  roomInfo: IApiCreateGame | null
  roomPlayers: IPlayer[]
  roomPlayersMap: IPlayerMap //{playerName:{資料}}
  roomPlayerNameList: string[]
  roomMyCards: { data: IHandCard[] } //我的手牌

  // Game
  map: ColListType
  selectedCard: IHandCard | null
  gameProgress: string
  gameActionPlayerName: string //打牌玩家

  // UI
  uiBgImg: 'bg-game-01' | 'bg-create'
  toastMessage: string | null //null就是關閉
}

export const useGameStore = createStore<IGameState>(() => ({
  // Room
  roomInfo: null,
  roomPlayers: [],
  roomPlayersMap: {},
  roomPlayerNameList: [],
  roomMyCards: { data: [] },

  // Game
  map: [[]],
  gameProgress: '',
  gameActionPlayerName: '',

  // Player
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
  getAll: () => T
}

export const gameStore: IGameStore<IGameState> = {
  on: (selector, callback) => {
    const unsubscribe = useGameStore.subscribe((state, prevState) => callback(state[selector], prevState[selector]))
    return { unsubscribe }
  },
  set: (selector, value) => useGameStore.setState({ [selector]: value }),
  get: (selector) => useGameStore.getState()[selector],
  getAll: () => useGameStore.getState(),
}

export default useGameStore
