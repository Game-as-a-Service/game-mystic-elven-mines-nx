import { component$,$ } from '@builder.io/qwik'

import BtnCircleUI from '../components/btnCircleUI'
import { gameBase } from '../../core/gameBase';
import { resetGameInfo } from '../../core/controllers/roomController';
import { useNavigate } from '@builder.io/qwik-city';
import { setToastMessage } from '../../core/stores/storeUI';
export default component$(() => {



  const nav = useNavigate()

  // Actions click
  const clickShareToFriend = $(async () => {

    const textArea = document.getElementById('copy-area');
    if(textArea){
    try {
      textArea?.select();
      document.execCommand('copy');
      } catch (err) {
        console.error('Unable to copy', err);
      }
      finally{
        setToastMessage('url已複製到剪貼簿, 請分享給朋友')
        textArea.blur();
      }
    }
  }
  )
  const clickExit = $(() => {
    console.log('EXIT')
    gameBase.socket?.disconnect() //斷開socket
    resetGameInfo() //清除玩家資料
    nav('../../')
  })


  return (

    <section class="p-5 pl-0 flex-1 flex flex-row justify-between">
    <div class="opacity-0">_</div>
    <div class="flex flex-col gap-3">
      <BtnCircleUI>1/3</BtnCircleUI>
      <BtnCircleUI cb={clickExit}>EXIT</BtnCircleUI>
      <BtnCircleUI cb={clickShareToFriend}>邀請朋友</BtnCircleUI>
    </div>
  </section>

  )
})
function nav(arg0: string) {
  throw new Error('Function not implemented.');
}

