import { Slot, component$, useSignal, useStore, useVisibleTask$ } from '@builder.io/qwik'
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
  useVisibleTask$(() => {
    //store

    if (isNil(width) && isZero(width)) store.w = 'w-full'
    if (isNil(height) && isZero(height)) store.w = 'h-full'
    if (width && height && width > height) {
      store.h = 'h-[' + width / ratio + 'px]'
    }

    if (width && height && width < height) {
      store.w = 'w-[' + height * ratio + 'px]'
    }
  })

  return (
    <div class={clsx(store.w, store.h, 'relative')}>
      <div class="absolute top-0 left-0 right-0 bottom-0">
        <Slot />
      </div>
    </div>
  )
})
