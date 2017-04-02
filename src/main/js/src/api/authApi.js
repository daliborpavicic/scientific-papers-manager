import { get, postJson } from './httpCalls';
import getBaseUrl from './getBaseUrl';

const baseUrl = getBaseUrl();

export function authenticate(credentials) {
  return postJson(`${baseUrl}/authentication`, credentials, false);
}

export function getUserDetails() {
  return get(`${baseUrl}/user`);
}
