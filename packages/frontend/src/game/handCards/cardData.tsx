import { component$, $, useSignal } from '@builder.io/qwik'

import { Image } from '@unpic/qwik'

import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'

import { CardTypeEnum, PathCard } from '../../core/types/Card'
import { gameStore } from '../../core/stores'
import clsx from 'clsx'
import { IHandCard } from '../../core/network/api/type'

export const CardData = component$(({ cName, cType }: { cName: PathCard; cType: CardTypeEnum }) => {
  console.log('cardData', cName)
  const isSelected = useSignal(false)

  const selectCard = $(() => {
    console.log('select card')
    const card: IHandCard = { name: cName, type: cType, flipped: false } //FIXME flipped
    isSelected.value = !isSelected.value
    if (isSelected.value) gameStore.set('selectedCard', card)
    else if (!isSelected.value) gameStore.set('selectedCard', null)
  })

  return (
    <div class={clsx('shadow-lg', 'w-full h-full')} onPointerUp$={() => selectCard()}>
      <small class="absolute top-[50%]">{cName}</small>
      <Image src={getImageUrlByApiCardName(cName)} alt="地圖卡"></Image>
    </div>
  )
})
