import { subscribeWithSelector } from 'zustand/middleware'
import { createStore } from 'zustand/vanilla'
import { SelectCardType } from './typing'
import { IApiCreateGame, IApiQueryGame } from '../network/api/type'
import { MapType } from '../../game/map/mapController'

interface IGameStore {
  roomInfo: IApiCreateGame | null
  roomPlayers: IApiQueryGame | null
  map: MapType
  selectedCard: SelectCardType | null
  uiBgImg: 'bg-game-01' | 'bg-create'
}

const gameStore = createStore(
  subscribeWithSelector<IGameStore>(() => ({
    //Room
    roomInfo: null,
    roomPlayers: null,

    //Game
    map: [[]],
    selectedCard: null,

    //UI
    uiBgImg: 'bg-game-01',
  }))
)
export default gameStore

// Debug
if (typeof window !== 'undefined') Object.assign(window, { gameStore })
