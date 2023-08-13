import gameStore from '.'

// UI
export const setUIBg = (key: 'game' | 'create') => {
  if (key === 'game') gameStore.setState({ uiBgImg: 'bg-game-01' })
  if (key === 'create') gameStore.setState({ uiBgImg: 'bg-create' })
}
