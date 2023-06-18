type fetcherType = { url: string; body?: any; type: 'GET' | 'POST' }
type IConfig = RequestInit

export const fetcher = async ({ url, body, type }: fetcherType) => {
  try {
    const token = 'your_token' // 请替换为您的实际 token
    const config: IConfig = {
      method: type,
      headers: {
        'Content-Type': 'application/json',
        //   Authorization: `Bearer ${token}`,
      },
    }
    if (body) config.body = JSON.stringify(body)

    const response = await fetch(url, config)
    interceptor(response)

    // TODO跟後端討論一下api
    // 目前沒有統一傳來前端的格式
    // 前端預想接收到的格式
    return await response.json()
  } catch (error) {
    //

    console.error('API Request Error:', error)
    // toast(error.message);
  }
}

const interceptor = (response: any) => {
  if (response.status !== 200) {
    console.log('error')
    // toast(response.message);
  }

  return response
}
