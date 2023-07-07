import { subscribeWithSelector } from 'zustand/middleware'
import { createStore } from 'zustand/vanilla'
import { MapCardType, MapType } from '../../game/map/mapController'
import { SelectCardType } from '../../game/tester.card'
import { CardTypeEnum, Paths } from '../types/Card'

export interface IGameStore {
  gameId: string
  map: MapType
  selectedCard: SelectCardType
  // setGameId: (gameId: string) => void
}
const gameStore = createStore(
  subscribeWithSelector<IGameStore>(() => ({
    gameId: 'nosGame',
    map: [[]],
    selectedCard: { cardName: Paths.Empty, cardType: CardTypeEnum.Empty },
    // setGameId: (gameId: string) => set({ gameId }),
  }))
)
const { getState, setState, subscribe } = gameStore

export default gameStore
