/*
 * WHAT IS THIS FILE?
 *
 * Development entry point using only client-side modules:
 * - Do not use this mode in production!
 * - No SSR
 * - No portion of the application is pre-rendered on the server.
 * - All of the application is running eagerly in the browser.
 * - More code is transferred to the browser than in SSR mode.
 * - Optimizer/Serialization/Deserialization code is not exercised!
 */

// 此文件用于纯客户端开发，不涉及服务器端渲染（SSR）。
// 应用程序的任何部分都不会在服务器上进行预渲染。
// 整个应用程序都在浏览器中运行，没有部分代码是在服务器上预渲染的。
// 与 SSR 模式相比，更多的代码会传输到浏览器端。
// 此模式下不会使用优化器/序列化/反序列化代码。

import { render, RenderOptions } from '@builder.io/qwik';
import Root from './root';

export default function (opts: RenderOptions) {
  return render(document, <Root />, opts);
}
