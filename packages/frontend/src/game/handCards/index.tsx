import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'

import { CardData } from './cardData'
import { gameStore } from '../../core/stores'
import { IHandCard } from '../../core/network/api/type'

export default component$(() => {
  const cards = useStore<{ data: IHandCard[]; waterId: number }>({ data: [], waterId: 0 })

  useVisibleTask$(({ track, cleanup }) => {
    track(() => cards.waterId)
    console.log('useVisibleTask')
    const off = gameStore.on('roomMyCards', (roomCard) => {
      console.log('useVisibleTask', roomCard)

      cards.data = roomCard.data
      cards.waterId++
    })

    cleanup(() => {
      off.unsubscribe()
      console.log('clean up')
    })
  })
  return (
    <div class="grid grid-cols-5 gap-2 w-full">
      {cards.data.map((card, i) => {
        return <CardData key={'card-' + i + cards.waterId} cName={card.name} cType={card.type} />
      })}
    </div>
  )
})
