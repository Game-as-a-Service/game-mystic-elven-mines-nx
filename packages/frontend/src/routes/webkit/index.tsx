import {
  component$,
  useVisibleTask$,
  useStore,
  useStylesScoped$,
} from '@builder.io/qwik';
import { DocumentHead, useLocation } from '@builder.io/qwik-city';

export default component$(() => {
  return <>API</>;
});

export const head: DocumentHead = {
  title: 'API',
};
