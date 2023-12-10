import { PathCard } from '../types/Card'
// 後端卡牌名稱傳換成前端圖片路徑
export const getImageUrlByApiCardName = (name: string) => {
  console.log('getImageUrlByApiCardName', name)
  return `/images/cards/${name}.jpg`
}
