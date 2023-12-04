import { component$, useStore, useVisibleTask$ } from '@builder.io/qwik'
import clsx from 'clsx'
import useGameStore, { gameStore } from '../../core/stores';
import {ToolName} from '../../core/types/Card'
import { gameBase } from '../../core/controllers/initGameBase';

type IPlayerData = { key: string; color: 'players' | 'me'; playerName:string }
export default component$(({ key, playerName, color }: IPlayerData) => {

  const tools = useStore({
    0:useGameStore.getState().roomPlayersMap[playerName]?.tools?.[0] || null,
    1:useGameStore.getState().roomPlayersMap[playerName]?.tools?.[1] || null,
    2:useGameStore.getState().roomPlayersMap[playerName]?.tools?.[2] || null,
  })

  const ui = useStore({show:false})

  useVisibleTask$(() => {
    gameStore.on('gameActionPlayerName', (name, _) => {
      if (name == _) return
      if (name === playerName) ui.show = true
      else ui.show = false
    }
    )
  })

  const css = color === 'players' ? 'bg-white text-black' : 'bg-[#272727] text-white'
  return (
    <section key={key} class={clsx('w-full p-2', css)}>
     <div class="flex justify-between items-center"><small class="font-bold text-mg">{playerName} </small><ReadyTipUI showTip={ui.show} /></div>
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

const ReadyTipUI = (props: { showTip: boolean }) => {
  return props.showTip ? (
    <small class="inline-block p-[2px] rounded text-[8px] font-bold text-yellow-700 bg-yellow-300 ">準備打牌</small>
  ) : null
}
