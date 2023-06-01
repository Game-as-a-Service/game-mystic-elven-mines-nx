import { component$ } from '@builder.io/qwik';
import type { DocumentHead } from '@builder.io/qwik-city';
import Map from '../game/map';
import { createGame, findGame, helloTest, players } from '../core/network/api';

export default component$(() => {
  return (
    <>
      <button class="bg-slate-300 p-2" onClick$={() => helloTest()}>
        api Hello
      </button>

      <button class="bg-slate-300 p-2" onClick$={() => createGame()}>
        api 建立遊戲
      </button>

      <button class="bg-slate-300 p-2" onClick$={() => findGame()}>
        api 查詢遊戲
      </button>

      {/* 左 - 玩家資訊 */}

      {/* 中 - 大地圖 */}
      <Map></Map>

      {/* 中 - 卡牌堆 */}

      {/* 下 - 玩家手牌 */}

      {/* 右 - 系統按鈕 */}

      {/* 共用 - 通知列 */}
    </>
  );
});

export const head: DocumentHead = {
  title: '神秘精靈礦 Mystic Elven Mines',
  meta: [
    {
      name: '神秘精靈礦 Mystic Elven Mines - 水球軟體學院遊戲. Qwik & Java',
      content: 'Qwik site description',
    },
  ],
};
