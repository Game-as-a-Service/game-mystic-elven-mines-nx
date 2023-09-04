import { component$ } from '@builder.io/qwik'

import { CardTypeEnum, Paths } from '../../core/types/Card'
import { CardData } from './cardData'

export default component$(() => (
  <div class="fixed bottom-10 bg-black grid grid-cols-5 gap-4">
    <CardData cName={Paths.Cross} cType={CardTypeEnum.Paths} color="red" />
  </div>
))
