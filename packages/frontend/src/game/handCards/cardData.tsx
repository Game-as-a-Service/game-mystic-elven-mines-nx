import { component$, $, useSignal } from '@builder.io/qwik'

import { Image } from '@unpic/qwik'

import { getImageUrlByApiCardName } from '../../core/utils/getCardImageByName'

import { CardTypeEnum, Paths } from '../../core/types/Card'
import { gameStore } from '../../core/stores'
import { SelectCardType } from '../../core/stores/typing'
import clsx from 'clsx'

export const CardData = component$(({ color, cName, cType }: { color: string; cName: Paths; cType: CardTypeEnum }) => {
  const isSelected = useSignal(false)

  const selectCard = $(() => {
    const card: SelectCardType = { cardName: cName, cardType: cType }

    isSelected.value = !isSelected.value
    if (isSelected.value) gameStore.set('selectedCard', card)
    else if (!isSelected.value) gameStore.set('selectedCard', null)
  })

  return (
    // <Ratio ratio={63 / 88} width={100} height={150}>
    <div class={clsx('shadow-lg', 'w-full h-full')} onPointerUp$={() => selectCard()}>
      <small class="absolute top-[50%]">{cName}</small>
      <Image src={getImageUrlByApiCardName(cName)} alt="地圖卡"></Image>
    </div>
    // </Ratio>
  )
})
