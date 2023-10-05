import { Slot, component$,$ } from '@builder.io/qwik'

export default component$(({ cb }: { cb?: any }) => {

  const handleCb =$(()=>{
    cb && cb()
  })

  return (
    <button class="bg-[#D9D9D9] w-[4rem] h-[4rem] font-bold text-sm p-2 rounded-full" onPointerUp$={handleCb}>
      <Slot />
    </button>
  )
})
