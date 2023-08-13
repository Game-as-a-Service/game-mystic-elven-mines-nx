import { component$, $, useSignal, useVisibleTask$ } from '@builder.io/qwik'
import { CardTypeEnum, Paths } from '../../core/types/Card'
import gameStore from '../../core/stores'
import { SelectCardType } from '../../core/stores/typing'

export default component$(
  ({ color, left, cName, cType }: { color: string; left: string; cName: Paths; cType: CardTypeEnum }) => {
    const isSelected = useSignal(false)
    const cardRef = useSignal<Element>()

    useVisibleTask$(({ track }) => {
      track(() => isSelected.value)
      //console.log('拿了卡', gameStore.getState().selectedCard)
      //console.log('task ', isSelected.value)
      if (isSelected.value) cardRef.value!.style.bottom = '100px'
      else if (!isSelected.value) cardRef.value!.style.bottom = '50px'
    })

    const selectCard = $(() => {
      const card: SelectCardType = { cardName: cName, cardType: cType }

      isSelected.value = !isSelected.value
      if (isSelected.value) gameStore.setState({ selectedCard: card })
      else if (!isSelected.value) gameStore.setState({ selectedCard: null })
    })

    return (
      <button
        ref={cardRef}
        onClick$={() => selectCard()}
        class="absolute bottom-[5vh] w-[100px] h-[150px]"
        style={{ left, background: color }}
      >
        卡
      </button>
    )
  }
)
