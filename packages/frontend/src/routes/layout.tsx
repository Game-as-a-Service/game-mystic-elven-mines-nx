import { component$, Slot } from '@builder.io/qwik';
import Header from '../components/header/header';
import '../styles/game.css';

export default component$(() => {
  return (
    <>
      <main>
        <Header />
        <section>
          <Slot />
        </section>
      </main>

      <footer>
        Made with ♡ by
        <span class="developer text-cyan-400">泇吟</span>
        <span class="developer text-cyan-400">dayday</span>
        <span class="developer text-cyan-400">FrankVicky</span>
        <span class="developer text-cyan-400">Jacky</span>
        <span class="developer text-cyan-400">水球潘</span>
      </footer>
    </>
  );
});
