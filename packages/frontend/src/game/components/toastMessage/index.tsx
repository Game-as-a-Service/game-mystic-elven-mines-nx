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
    console.log('init toastMessage')
    const off = gameStore.on('toastMessage', (msg) => {
      if (msg) {
        console.log('toastMessage listen:', msg)
        store.isShow = true
        store.txt = msg
        setTimeout(() => {
          store.isShow = false
          store.txt = ''
          setToastMessage(null)
        },200)
      }
    })

    console.log('off',off)
  })
  return (
    store.isShow ? (
      <div class={clsx('fixed w-full h-full flex items-center')}>
        <div class="flex justify-center items-center w-full h-[40px] text-white text-center font-bold text-100 bg-[rgba(0,0,0,0.6)]">
          {store.txt}
        </div>
      </div>
    ) : null
  )
})
