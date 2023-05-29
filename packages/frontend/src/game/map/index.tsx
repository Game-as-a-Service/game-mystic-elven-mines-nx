import { component$ } from '@builder.io/qwik';
import { renderMap, CardType } from './mapController';
import { CardTypeEnum, Paths } from '../../core/types/Card';
import './map.css';
import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName';

const mockCards: CardType[] = [
  { row: 1, col: 2, cardName: Paths.Cross, cardType: CardTypeEnum.Paths },
  {
    row: 1,
    col: 4,
    cardName: Paths.DeadEndCross,
    cardType: CardTypeEnum.Paths,
  },
  { row: 5, col: 5, cardName: Paths.LeftCurve, cardType: CardTypeEnum.Paths },
];

export default component$(() => {
  return <div class="map">{renderMap(mockCards)}</div>;
});

// 桌上的卡牌
export const MapCard = component$((card: CardType) => {
  return (
    <>
      <div class="relative text-left">
        <small class="absolute top-[50%]">{card.cardName}</small>
        <image src={getImageUrlByApiCardName(card.cardName)}></image>
      </div>
    </>
  );
});
