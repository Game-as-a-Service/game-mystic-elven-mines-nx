import { Slot, component$, useSignal, useStore } from '@builder.io/qwik'
import { identical, isNil } from 'ramda'
import { isZero } from '../../core/utils/isZero'
import clsx from 'clsx'

interface IRatioProps {
  ratio: number // 16/9  寬/高 等等
  width?: number
  height?: number
}
export const Ratio = component$(({ ratio, width, height }: IRatioProps) => {
  const store = useStore({
    w: 'w-[' + width + 'px]',
    h: 'h-[' + height + 'px]',
  })
  if (isNil(width) && isZero(width)) store.w = 'w-full'
  if (isNil(height) && isZero(height)) store.w = 'h-full'

  return (
    <div class={clsx(store.w, store.h, 'relative')}>
      <div class="absolute top-0 left-0 right-0 bottom-0">
        <Slot />
      </div>
    </div>
  )
})
