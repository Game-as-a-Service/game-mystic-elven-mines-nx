import { $, component$ } from '@builder.io/qwik'
import { useLocation } from '@builder.io/qwik-city'

export default component$(({ gameId }: { gameId: string }) => {
  const loc = useLocation()

  const handleClick = $(async () => {
    const url = loc.url.origin + '/join/' + gameId

    await navigator.clipboard.writeText(url) // copy url to clipboard
    alert('url已複製到剪貼簿, 請分享給朋友')
  })

  return (
    <main
      class="fixed z-[100] top-5 right-5 bg-green-200 rounded-full w-[100px] h-[100px] flex justify-center text-center items-center cursor-pointer"
      onClick$={handleClick}
    >
      <h1 class="text-black text-xl">邀請朋友</h1>
    </main>
  )
})
