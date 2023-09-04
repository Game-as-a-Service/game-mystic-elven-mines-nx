import { component$, useStore } from '@builder.io/qwik'
import { gameStore } from '../../../core/stores/index'
import { isNil } from 'ramda'
//import { motion } from 'framer-motion'
import clsx from 'clsx'

const variants = {
  open: { opacity: 1, x: 0 },
  closed: { opacity: 0, x: '-100%' },
}

export default component$(() => {
  const store = useStore({
    show: false,
    txt: '',
  })

  gameStore.on('toastMessage', (msg) => {
    console.log('toastMessage listen:', msg)
    if (isNil(msg)) store.show = false
    else {
      store.show = true
      store.txt = msg
    }
  })

  return (
    // <div class="fixed w-full h-full flex items-center" animate={store.show ? 'open' : 'closed'} variants={variants}>
    //   <div class="bg-[rgba(0,0,0,0.6)] w-full h-[150px] text-center font-bold text-20">{store.txt}</div>
    // </div>
    <div class={clsx(store.show ? '' : 'hidden', 'fixed w-full h-full flex items-center')}>
      <div class="bg-[rgba(0,0,0,0.6)] w-full h-[80px] text-center font-bold text-36">{store.txt}</div>
    </div>
  )
})
