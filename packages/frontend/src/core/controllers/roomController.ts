import api from '../network/api'
import { setRoomMyCards, setRoomPlayerNameList, setRoomPlayers } from '../stores/storeRoom'
import { getGameInfo, setGameIdToLocal, setPlayerIdToLocal } from './initGameBase'

// 跟遊戲房間有關的Controllers

export const createGameAndGetId = async (playerName: string) => {
  const res = await api.gameCreate({ playerName: playerName })
  console.log('api gameCreate', res)
  setGameIdToLocal(res.gameId)
  setPlayerIdToLocal(res.playerId || '')
  return res.gameId
}

export const getGamePlayersData = async () => {
  //socket連線成功後 取得玩家資料
  const { players } = await api.gamePlayers()
  const playerNameList = players.map((x) => x.playerName)
  setRoomPlayers(players)
  setRoomPlayerNameList(playerNameList)
}

// 主要是要知道自己手牌
export const getGamePlayerMeData = async () => {
  const res = await api.gamePlayerMe()
  console.log('res', res)
  setRoomMyCards(res.cards)
}

export const initFirstTimeJoinRoom = async (store: any) => {
  const { gameId, playerId, playerName } = await getGameInfo()
  console.log('initFirstTimeJoinRoom', { gameId, playerId, playerName })

  store.playerName = playerName

  if (playerName === '') console.log('[遊客模式]')
  if (playerName !== '') console.log('[玩家模式]')

  if (playerName !== '') {
    try {
      const res = await api.gameJoin({ playerName: playerName })
      if (res) {
        setPlayerIdToLocal(res.playerId || '')
        console.log('加入遊戲中...')
      }
    } catch {
      console.log('加入遊戲發生錯誤, 可能是資料損壞, 或是你是房主')
    }
  }
}
