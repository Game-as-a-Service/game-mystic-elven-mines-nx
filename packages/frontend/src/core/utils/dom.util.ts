export function $dom(selector: string) {
  const elements = document.querySelectorAll(selector)

  // 如果只有一個元素，則返回該元素
  if (elements.length === 1) {
    return elements[0]
  }

  // 如果有多個元素，則返回一個包含所有元素的 NodeList
  return elements
}
