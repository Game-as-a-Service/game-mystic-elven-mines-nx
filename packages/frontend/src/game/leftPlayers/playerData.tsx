import { component$, useStore } from '@builder.io/qwik'
import clsx from 'clsx'
import useGameStore from '../../core/stores';
import {ToolName} from '../../core/types/Card'

type IPlayerData = { key: string; color: 'players' | 'me'; playerName:string }
export default component$(({ key, playerName, color }: IPlayerData) => {

  const tools = useStore({
    0:useGameStore.getState().roomPlayersMap[playerName]?.tools?.[0] || null,
    1:useGameStore.getState().roomPlayersMap[playerName]?.tools?.[1] || null,
    2:useGameStore.getState().roomPlayersMap[playerName]?.tools?.[2] || null,
  })
  const css = color === 'players' ? 'bg-white text-black' : 'bg-[#272727] text-white'
  return (
    <section key={key} class={clsx('w-full p-2', css)}>
      <small class="font-bold text-mg">{playerName}</small>
      <div class="grid grid-cols-5 gap-2">
        {tools[0]?.toolName == ToolName.FLYING_BOOTS && <ToolFlyingBoots isAvailable={tools[0]?.available}/>}
        {tools[1]?.toolName == ToolName.HARP_OF_HARMONY && <HarpOfHarmony isAvailable={tools[1]?.available}/>}
        {tools[2]?.toolName == ToolName.STARLIGHT_WAND &&<StarLightWand isAvailable={tools[2]?.available}/>}
      </div>
    </section>
  )
})

const ToolFlyingBoots=(props:{isAvailable:boolean})=>{
  return(
    <div class="bg-red-300 h-[1rem] w-[1rem] relative">
      <small class="absolute top-[-2px] left-[-1px]">
      {props.isAvailable?null:'❌'}
      </small>
    </div>
  )
}
const HarpOfHarmony=(props:{isAvailable:boolean})=>{
  return(
  <div class="bg-yellow-300 h-[1rem] w-[1rem] relative">
      <small class="absolute top-[-2px] left-[-1px]">
      {props.isAvailable?null:'❌'}
      </small>
  </div>)
}
const StarLightWand=(props:{isAvailable:boolean})=>{
  return(
  <div class="bg-blue-300 h-[1rem] w-[1rem] relative">
      <small class="absolute top-[-2px] left-[-1px]">
      {props.isAvailable?null:'❌'}
      </small>
  </div>)
}

