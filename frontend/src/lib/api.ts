import { env } from './env';

export async function api(
  input: string | URL | globalThis.Request,
  init?: RequestInit
): Promise<Response> {
  let url: string | URL;

  if (typeof input === 'string') {
    url = input.startsWith('http') ? input : `${env.VITE_API_URL}${input}`;
  } else if (input instanceof URL) {
    url = input;
  } else {
    const requestUrl =
      typeof input.url === 'string' ? input.url : String(input.url);
    url = requestUrl.startsWith('http')
      ? requestUrl
      : `${env.VITE_API_URL}${requestUrl}`;
  }

  return fetch(url, init);
}
