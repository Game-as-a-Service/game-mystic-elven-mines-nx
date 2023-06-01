# [GET]查玩家手上有甚麼牌 `/api/games`/`{gameId}`/players/`{`player`Id}`/cards

- 只有自己會知道自己的牌, 所以用 api
- 要 check 如果前端 A 作弊要玩家 B 的牌, 後台要不給牌
- 是不是有特殊情況可以看別人的牌? 需討論條件

query

```json
card/getHandCards?playerId={id}
```

Response

```ts
data:{
	handCards:{
		cardId: string,
		cardName: string,
    handIndex: number,
		pathAvailable:[{x:number,y:number}]
	}[],
  playerId: string //此玩家手上的牌
}
```

# [POST]玩家打牌棄牌

payload

```ts
data:{
	playerId: string,
	card:{
					x:number,
					y:number,
					cardIndex: number,
					cardId: string,
					cardName: string,
				} //1張
}
```

# [GET] 取得地圖資訊 /api/games/{gameId}/mapCards

Response

```ts
cards: [
  {
    x: number, //卡牌離起始牌的距離
    y: number, //卡牌離起始牌的距離
    cardName: string, // "Path_Start", 道路牌方向不同,有各種方向
    cardType: string, // "path",  這裡的情況一定是路徑
    // pathAvailable:{x:number,y:number}[]
  },
];
```

# [GET] 取得玩家資訊 /api/games/{gameId}/players
