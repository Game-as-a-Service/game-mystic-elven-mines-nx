2023-10-05

- [ ] dayday:製作[開始遊戲按鈕]
- [ ] dayday:
- [ ] dayday:重構人物的store資料格式,用map:{playerName:{}} 

- [ ] dayday:動畫選卡
- [ ] dayday:動畫發牌到桌子上

- [ ] dayday:音效按鈕
- [ ] dayday:音效抽牌
- [ ] dayday:音效放牌

- [ ] 討論:PLAYER_JOINED PLAYER_LEFT資料 (改為playerNames string[] , 跟有一個playerMap的資料)
- [ ] 討論:GAME_NEXT_ROUND資料


現況socket:當有玩家離開遊戲 前端會收到離開玩家的userId
因為前端拿到userId資料沒有用, 給我名字吧.
PLAYER_JOINED跟PLAYER_LEFT可以接收一樣的資料格式
SocketChannel.PLAYER_JOINED
SocketChannel.PLAYER_LEFT

給前端
```
{
  players: []
  playerName: string (離開或是加入的人)
}
```


遊戲進程
```
GAME_STARTED     開始遊戲 當有人按下[開始遊戲],告訴前端輪到誰打牌
GAME_NEXT_ROUND  //待討論 打玩牌後,告訴前端輪到誰打牌
GAME_ENDED       結束遊戲 跳出遊戲結束, 顯示再玩一次嗎等提醒視窗
```

