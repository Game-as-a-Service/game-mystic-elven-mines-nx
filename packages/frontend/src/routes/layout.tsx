import { component$, Slot } from '@builder.io/qwik';
import Header from '../app/components/header/header';

import '../core/styles/game.css';
export default component$(() => {
  return (
    <>
      <div class="game-bg"></div>
      <main>
        <Header />
        <section>
          <Slot />
        </section>
      </main>
    </>
  );
});
