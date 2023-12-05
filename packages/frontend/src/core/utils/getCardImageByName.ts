// 後端卡牌名稱傳換成前端圖片路徑
export const getImageUrlByApiCardName = (name: string) => {
  if (name === 'Path_DeadEndCross') return '/images/cards/card_001.svg'
  return '/images/cards/card_front.svg'
}
