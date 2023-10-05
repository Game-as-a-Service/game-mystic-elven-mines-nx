import useGameStore from '.'
import { ColListType } from '../../game/map/mapController'

// UI
export const setUIBg = (key: 'game' | 'create') => {
  if (key === 'game') useGameStore.setState({ uiBgImg: 'bg-game-01' })
  if (key === 'create') useGameStore.setState({ uiBgImg: 'bg-create' })
}

export const setToastMessage = (msg: string | null) => {
  useGameStore.setState({ toastMessage: msg })
}

export const setMap = (map: ColListType) => {
  useGameStore.setState({ map })
}

