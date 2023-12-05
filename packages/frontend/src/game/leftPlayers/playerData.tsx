import { component$, useSignal, useStore, useVisibleTask$ } from '@builder.io/qwik'
import clsx from 'clsx'
import useGameStore, { gameStore } from '../../core/stores'
import { ToolName } from '../../core/types/Card'
import { IPlayer } from '../../core/network/api/type'
import imgElven from './role_elven.jpg'
import imgGoblin from './role_goblin.jpg'
import imgDefault from './role_default.jpg'
import { isNil } from 'ramda'

type IPlayerData = { key: string; color: 'players' | 'me'; playerName: string }
export default component$(({ key, playerName, color }: IPlayerData) => {
  const ui = useStore({ show: false })

  // 準備打牌
  useVisibleTask$(() => {
    gameStore.on('gameActionPlayerName', (name, _) => {
      if (name == _) return
      if (name === playerName) ui.show = true
      else ui.show = false
    })
  })

  const bgCss = color === 'players' ? 'bg-white text-black' : 'bg-[#272727] text-white'
  return (
    <>
      {/* 玩家列表 */}
      {color === 'players' && (
        <section key={key} class={clsx('w-full p-2', bgCss)}>
          <div class="flex justify-between items-center">
            <small class="font-bold text-mg">{playerName} </small>
            <ReadyTipUI showTip={ui.show} />
          </div>
          <ToolsGroup playerName={playerName} />
        </section>
      )}

      {/* 我的狀態列 */}
      {color === 'me' && (
        <section key={key} class={clsx('w-full p-2', bgCss)}>
          <div class="flex flex-row justify-between items-center">
            <div class="w-1/2">
              <Face />
            </div>
            <div class="w-1/2 flex flex-col justify-center items-center">
              <ReadyTipUI showTip={ui.show} />
              <small class="font-bold text-mg">{playerName}</small>
            </div>
          </div>
          <ToolsGroup playerName={playerName} />
        </section>
      )}
    </>
  )
})

const Face = component$(() => {
  const role = useSignal<'ELVEN' | 'GOBLIN' | undefined>(undefined)

  useVisibleTask$(() => {
    gameStore.on('roomMyRole', (r, _) => {
      if (r == _) return
      role.value = r
    })
  })
  if (role.value === 'GOBLIN') return <img src={imgGoblin} />
  if (role.value == 'ELVEN') return <img src={imgElven} />
  return <img src={imgDefault} />
})

const ToolsGroup = component$((props: { playerName: string }) => {
  const tools = useSignal<IPlayer['tools']>([])

  useVisibleTask$(() => {
    tools.value = useGameStore.getState().roomPlayersMap[props.playerName]?.tools
    console.log('vis tools', tools.value)
  })

  return (
    <div id="tools-group" class="grid grid-cols-3 gap-3">
      {tools.value?.map((tool, index) => (
        <div key={'tool-' + index} class="bg-red-300 h-[1rem] w-[1rem] relative">
          <small class="absolute top-[-2px] left-[-1px]">{tool.available ? null : '❌'}</small>
        </div>
      ))}
    </div>
  )
})

const ReadyTipUI = (props: { showTip: boolean }) => {
  return props.showTip ? (
    <small class="inline-block p-[2px] rounded text-[8px] font-bold text-yellow-700 bg-yellow-300 ">準備打牌</small>
  ) : null
}
