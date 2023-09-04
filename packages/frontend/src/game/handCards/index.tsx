import { component$ } from '@builder.io/qwik'

import { CardTypeEnum, Paths } from '../../core/types/Card'
import { CardData } from './cardData'

export default component$(() => (
  <div class="grid grid-cols-5 gap-2 w-full">
    <CardData cName={Paths.Cross} cType={CardTypeEnum.Paths} color="red" />
    <CardData cName={Paths.Cross} cType={CardTypeEnum.Paths} color="red" />
    <CardData cName={Paths.Cross} cType={CardTypeEnum.Paths} color="red" />
    <CardData cName={Paths.Cross} cType={CardTypeEnum.Paths} color="red" />
    <CardData cName={Paths.Cross} cType={CardTypeEnum.Paths} color="red" />
  </div>
))
