import { component$, $, useSignal, useVisibleTask$, Slot } from '@builder.io/qwik'
import { CardTypeEnum, Paths } from '../../core/types/Card'
import useGameStore from '../../core/stores'
import { SelectCardType } from '../../core/stores/typing'
import { Ratio } from '../components/ratio'
import clsx from 'clsx'

export const CardData = component$(({ color, cName, cType }: { color: string; cName: Paths; cType: CardTypeEnum }) => {
  const isSelected = useSignal(false)

  const selectCard = $(() => {
    const card: SelectCardType = { cardName: cName, cardType: cType }

    isSelected.value = !isSelected.value
    if (isSelected.value) useGameStore.setState({ selectedCard: card })
    else if (!isSelected.value) useGameStore.setState({ selectedCard: null })
  })

  return (
    <Ratio ratio={63 / 88} width={100} height={150}>
      <div class={clsx('w-full h-full bg-white')} onClick$={() => selectCard()}></div>卡
    </Ratio>
  )
})
