// 卡片
export enum CardTypeEnum {
  Paths = 'Card_Path', // 道路卡
  Actions = 'Card_Actions', // 功能卡
  Repair = 'Card_Repair', // 修復卡
  Broken = 'Card_Broken', // 破壞卡
  Skills = 'Card_Skills', // 技能卡
}

export enum FinishGroup {
  MagicCrystal = 'MagicCrystal', //魔法水晶
  // PreciousGemstone = 'PreciousGemstone', // 珍貴寶石
  SacredRelic = 'SacredRelic', //神聖遺物
  // UndergroundCave = 'UndergroundCave', // 地下洞窟
  // RockCollapse = 'RockCollapse', // 岩石塌方
  MagicalInterference = 'MagicalInterference', // 魔法干擾
}
export enum Actions {
  //原本遊戲的
  Rockfall = 'Action_Rockfall', // 落石卡
  Map = 'Action_Map', //地圖卡,

  //討論選一
  FlyingBoots = 'Action_FlyingBoots', //飛行靴
  IllusionCrystalBall = 'Action_IllusionCrystalBall', //幻視水晶球
  HarpOfHarmony = 'Action_HarpOfHarmony', //音樂之琴
  RainbowCloak = 'Action_RainbowCloak', //彩虹斗篷
  StarlightWand = 'Action_StarlightWand', //星光之杖
  MysticMap = 'Action_MysticMap', //神秘地圖

  // 飛行靴:這對靴子能賦予精靈飛行的能力,讓他們能夠越過障礙物或跨越深淵。它們可以幫助精靈達到高處或遠離危險的地方,同時也提供了更大的行動自由度。
  // 幻視水晶球:這個水晶球能讓精靈看見隱藏在迷霧中或魔法屏障後的事物。它提供了洞察力,幫助精靈發現隱藏的寶藏、迷宮中的通道或敵人的陷阱。
  // 音樂之琴:這把特殊的琴弦樂器能釋放出迷人的音樂力量,讓精靈能夠操縱自然元素或影響周遭環境。精靈可以使用音樂之琴激發風、水、火或土地的力量,改變場景中的情境,並為他們帶來利益或幫助。
  // 彩虹斗篷:這件斗篷賦予精靈能力,在需要時隱形或變身為其他生物。它可以讓精靈在冒險中躲避敵人的注意,探索禁地,或與其他生物進行溝通。
  // 星光之杖:這把寶石鑲嵌的法杖能釋放出閃耀的星光能量,具有治療和恢復的效果。精靈可以使用星光之杖為自己或隊友恢復生命力,並對抗黑暗力量的影響。
  // 神秘地圖:這張地圖隱藏了精靈世界中的重要位置和秘密寶藏的線索。精靈需要解讀地圖上的符號和謎題,找到寶藏或者尋找前進的道路。
}
export enum Paths {
  Start = 'Path_Start', // 起始卡
  Finish = FinishGroup.MagicCrystal, // 終點卡

  Cross = 'Path_Cross', // 十字路口
  DeadEndCross = 'Path_DeadEndCross', // 死路十字路口

  //討論彎
  LeftCurve = 'Path_LeftCurve', //左彎
  DeadEndLeftCurve = 'Path_DeadEndLeftCurve', //左彎死路
  RightCurve = 'Path_RightCurve', //右彎
  DeadEndRightCurve = 'Path_DeadEndRightCurve', // 右彎死路
  //討論彎
  Curve1 = 'Path_Curve1', // 彎1
  Curve2 = 'Path_Curve2', // 彎2
  DeadEndCurve1 = 'Path_DeadEndCurve1', // 彎死路1
  DeadEndCurve2 = 'Path_DeadEndCurve2', // 彎死路2

  Horizontal = 'Path_Horizontal', // 橫線
  DeadEndHorizontal1 = 'Path_DeadEndHorizontal1', // 橫線死路1
  DeadEndHorizontal2 = 'Path_DeadEndHorizontal2', // 橫線死路2

  Straight = 'Path_Straight', // 直線
  DeadEndStraight1 = 'Path_DeadEndStraight1', // 直線死路1
  DeadEndStraight2 = 'Path_DeadEndStraight2', // 直線死路2

  HorizontalT = 'Path_HorizontalT', //橫T
  DeadEndHorizontalT = 'Path_DeadEndHorizontalT', //橫T死路
  StraightT = 'Path_StraightT', //直T
  DeadEndStraightT = 'Path_DeadEndStraightT', //直T死路
}

export enum Repair {
  //實作可能只需要一種
  Fix_1 = 'Fix_1', // 修復1
  Fix_2 = 'Fix_2', // 修復2
  Fix_3 = 'Fix_3', // 修復3
}
export enum Broken {
  //實作可能只需要一種
  Broken_1 = 'Broken_1', // 破畫卡1
  Broken_2 = 'Broken_2', // 破畫卡2
  Broken_3 = 'Broken_3', // 破畫卡3

  //   飛行靴 (Flying Boots)
  // 幻視水晶球 (Illusion Crystal Ball)
  // 音樂之琴 (Harp of Harmony)
  // 彩虹斗篷 (Rainbow Cloak)
  // 星光之杖 (Starlight Wand)
  // 神秘地圖 (Mystic Map)
}
export enum Skills {
  InsightfulRevelation = 'Skill_InsightfulRevelation', // 洞悉真相
  TacticalExchange = 'Skill_TacticalExchange', // 計策交換
  EndpointSwap = 'Skill_EndpointSwap', // 終點換位
  MapShift = 'Skill_MapShift', // 地圖遷移
  SpatialMagic = 'Skill_SpatialMagic', // 空間魔法
  ForeseeFuture = 'Skill_ForeseeFuture', // 預知未來
  EnhancedAbility = 'Skill_EnhancedAbility', // 倍增能力
  ItemDestruction = 'Skill_ItemDestruction', // 道具破壞
  TimeFreeze = 'Skill_TimeFreeze', // 時間凍結
}

export interface CardType {
  name: string;
  description: string;
  type: CardTypeEnum;
}
