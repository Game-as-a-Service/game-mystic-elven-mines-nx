import useGameStore from '.'

// UI
export const setUIBg = (key: 'game' | 'create') => {
  if (key === 'game') useGameStore.setState({ uiBgImg: 'bg-game-01' })
  if (key === 'create') useGameStore.setState({ uiBgImg: 'bg-create' })
}
