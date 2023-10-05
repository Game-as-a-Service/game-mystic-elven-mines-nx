import { component$ } from '@builder.io/qwik'

import BtnCircleUI from '../components/btnCircleUI'
export default component$((props:any) => {
  return (

    <section class="p-5 pl-0 flex-1 flex flex-row justify-between">
    <div class="opacity-0">_</div>
    <div class="flex flex-col gap-3">
      <BtnCircleUI>1/3</BtnCircleUI>
      <BtnCircleUI cb={props.data.clickExit}>EXIT</BtnCircleUI>
      <BtnCircleUI cb={props.data.clickShareToFriend}>邀請朋友</BtnCircleUI>
    </div>
  </section>

  )
})
