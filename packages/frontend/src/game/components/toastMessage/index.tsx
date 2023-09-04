import { component$, useStore } from '@builder.io/qwik'

export default component$(() => {
  const msgData = useStore({
    show: false,
    txt: '',
  })
  return <div class="bg-black/20 w-full h-[200px] text-center font-bold text-20">{msgData.txt}</div>
})
