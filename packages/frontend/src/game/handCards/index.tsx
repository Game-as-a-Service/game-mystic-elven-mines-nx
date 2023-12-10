import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'

import { CardData } from './cardData'
import { gameStore } from '../../core/stores'
import { IHandCard } from '../../core/network/api/type'
import { mockRoomMyCards } from '../../core/stores/storeRoom'
import { CardTypeEnum, PathCard } from '../../core/types/Card'

export default component$(() => {
  const cards = useStore<{ data: IHandCard[] }>({ data: mockRoomMyCards })

  useVisibleTask$(() => {
    console.log('handCards')
    const off = gameStore.on('roomMyCards', (roomCard) => {
      console.log('gameStore roomMyCards', roomCard)
      //  cards.data = roomCard.data
    })
    // FIXME 當卸載的時候unscribe?

    console.log('mockRoomMyCards', mockRoomMyCards)
  })
  return (
    <div class="grid grid-cols-5 gap-2 w-full">
      <CardData cName={PathCard.HORIZONTAL} cType={CardTypeEnum.Path} />
      <CardData cName={PathCard.HORIZONTAL} cType={CardTypeEnum.Path} />
      <CardData cName={PathCard.HORIZONTAL} cType={CardTypeEnum.Path} />
      <CardData cName={PathCard.HORIZONTAL} cType={CardTypeEnum.Path} />
      <CardData cName={PathCard.HORIZONTAL} cType={CardTypeEnum.Path} />
      {/* {cards.data.map((card, i) => {
        return <CardData key={'card-' + i} cName={card.name} cType={card.type} />
      })} */}
    </div>
  )
})
