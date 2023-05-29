import { component$ } from '@builder.io/qwik';
import type { DocumentHead } from '@builder.io/qwik-city';
import { Link } from '@builder.io/qwik-city';

const API_URL = process.env.PUBLIC_API_URL;
console.log('API_URL', API_URL);

export default component$(() => {
  return (
    <div>
      <h1>
        <label class="p-1 bg-green-200">打後端的api</label> {API_URL}
      </h1>

      <h1>
        Welcome frontend <span class="lightning">⚡️</span>
      </h1>
    </div>
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
