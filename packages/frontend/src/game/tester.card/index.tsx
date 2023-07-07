import { component$, $, useSignal, useVisibleTask$ } from '@builder.io/qwik'
import { CardTypeEnum, Paths } from '../../core/types/Card'
import gameStore from '../../core/stores'

export type SelectCardType = {
  cardName: Paths
  cardType: CardTypeEnum
}

export default component$(({ color, propLeft, name, type }: Record<string, string>) => {
  const isSelected = useSignal(false)
  const cardRef = useSignal<Element>()

  useVisibleTask$(({ track }) => {
    track(() => isSelected.value)

    console.log('拿了卡', gameStore.getState().selectedCard)
    console.log('task ', isSelected.value)

    if (isSelected.value) cardRef.value!.style.bottom = '100px'
    else if (!isSelected.value) cardRef.value!.style.bottom = '50px'
  })

  const selectCard = $(() => {
    const card: SelectCardType = { cardName: name, cardType: type }
    gameStore.setState({ selectedCard: card })
    isSelected.value = !isSelected.value
  })

  return (
    <button
      ref={cardRef}
      onClick$={() => selectCard()}
      class="absolute bottom-[5vh] w-[100px] h-[150px]"
      style={{ left: propLeft, background: color }}
    >
      卡
    </button>
  )
})
