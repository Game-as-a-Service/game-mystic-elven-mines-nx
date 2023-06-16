type fetcherType = { url: string; body?: any; type: 'GET' | 'POST' };

export const fetcher = async ({ url, body, type }: fetcherType) => {
  try {
    const token = 'your_token'; // 请替换为您的实际 token

    let response: Response;

    response = await fetch(url, {
      method: type,
      headers: {
        'Content-Type': 'application/json',
        //   Authorization: `Bearer ${token}`,
      },
    });
    if (body) {
      response = await fetch(url, {
        method: type,
        headers: {
          'Content-Type': 'application/json',
          //    Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(body),
      });
    }

    const apiResult = await response.json();
    interceptor(apiResult);
  } catch (error) {
    console.error('API Request Error:', error);
    // toast(error.message);
    // 处理请求错误的逻辑，例如显示错误提示或重试等
  }
};

const interceptor = (apiResult: any) => {
  if (apiResult.code !== '200') {
    console.error('收到後端api錯誤:');
    // toast(apiResult.message);
  }
  return apiResult;
};
