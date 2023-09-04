import { Slot, component$, useVisibleTask$ } from '@builder.io/qwik'

export default component$(({ cb }: { cb?: any }) => {
  useVisibleTask$(() => console.log('cb', cb))
  return (
    <button class="bg-[#D9D9D9] w-[4rem] h-[4rem] font-bold text-sm p-2 rounded-full" onClick$={() => cb && cb()}>
      <Slot />
    </button>
  )
})
