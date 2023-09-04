import { component$ } from '@builder.io/qwik'
import { IPlayer } from '../../core/network/api/type'
import clsx from 'clsx'

type PropType = IPlayer & { key: string; color: 'players' | 'me' }

export const PlayerData = component$(({ key, name, id, color }: PropType) => {
  const css = color === 'players' ? 'bg-white text-black' : 'bg-[#272727] text-white'
  return (
    <section key={key} class={clsx('w-full p-2', css)}>
      <small class="font-bold text-mg">{name}</small>
      <div class="grid grid-cols-5 gap-2">
        <div class="bg-gray-400 h-[1rem] w-[1rem]"></div>
        <div class="bg-gray-400 h-[1rem] w-[1rem]"></div>
        <div class="bg-gray-400 h-[1rem] w-[1rem]"></div>
      </div>
    </section>
  )
})
