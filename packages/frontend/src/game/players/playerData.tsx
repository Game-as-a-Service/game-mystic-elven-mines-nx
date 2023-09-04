import { component$ } from '@builder.io/qwik'
import { IPlayer } from '../../core/network/api/type'

type PropType = IPlayer & { key: string }

export const PlayerData = component$(({ key, name, id }: PropType) => {
  return (
    <section key={key} class="text-black">
      <h2 class="font-bold">{name}</h2>
      <small class="text-sm">{id}</small>
    </section>
  )
})
