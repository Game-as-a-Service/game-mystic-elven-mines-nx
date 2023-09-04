import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import { gameStore } from '../../../core/stores/index'
import { isNil } from 'ramda'
import clsx from 'clsx'
import { setToastMessage } from '../../../core/stores/storeUI'

// const variants = {
//   open: { opacity: 1, x: 0 },
//   closed: { opacity: 0, x: '-100%' },
// }

export default component$(() => {
  const store = useStore(
    {
      isShow: false,
      txt: '',
    },
    { deep: true }
  )

  useVisibleTask$(() => {
    gameStore.on('toastMessage', (msg) => {
      console.log('toastMessage listen:', msg)
      if (msg) {
        store.isShow = true
        store.txt = msg
        setTimeout(() => {
          store.isShow = false
          store.txt = ''
        }, 1000)
      } else if (isNil(msg)) {
        store.isShow = false
        store.txt = ''
      }
      //setTimeout(() => setToastMessage(null), 1000)
    })
  })
  return (
    // <div class="fixed w-full h-full flex items-center" animate={store.show ? 'open' : 'closed'} variants={variants}>
    //   <div class="bg-[rgba(0,0,0,0.6)] w-full h-[150px] text-center font-bold text-20">{store.txt}</div>
    // </div>
    store.isShow ? (
      <div class={clsx('fixed w-full h-full flex items-center')}>
        <div class="flex justify-center items-center w-full h-[40px] text-white text-center font-bold text-36 bg-[rgba(0,0,0,0.6)]">
          {store.txt}
        </div>
      </div>
    ) : null
  )
})
