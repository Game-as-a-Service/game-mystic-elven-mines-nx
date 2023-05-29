import { component$, useStylesScoped$ } from '@builder.io/qwik';
import { QwikLogo } from '../icons/qwik';
import styles from './header.css?inline';

export default component$(() => {
  useStylesScoped$(styles);
  //why use useStylesScoped$

  return (
    <header>
      <div class="logo">神秘精靈</div>
    </header>
  );
});
