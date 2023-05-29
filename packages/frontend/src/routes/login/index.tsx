import {
  component$,
  useVisibleTask$,
  useStore,
  useStylesScoped$,
} from '@builder.io/qwik';
import { DocumentHead, useLocation } from '@builder.io/qwik-city';
// import login.css
import './login.css';

export default component$(() => {
  return (
    <>
      <main class="login">
        <div class="flex flex-col w-1/3 ">
          <h1>輸入你的名字</h1>
          <input class="border mb-1"></input>

          <button class="bg-slate-300 p-1 rounded-sm">送出</button>
        </div>
      </main>
    </>
  );
});

export const head: DocumentHead = {
  title: 'Demo',
};
