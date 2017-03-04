import 'whatwg-fetch';

const onSuccess = response => response.json();
const onError = error => console.log(error);

export function get(url) {
  return fetch(url).then(onSuccess, onError);
}

export function postJson(url, jsonData) {
  const request = new Request(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(jsonData)
  });

  return fetch(request).then(onSuccess, onError);
}

export function postFormData(url, formData) {
  const request = new Request(url, {
    method: 'POST',
    body: formData
  });

  return fetch(request).then(onSuccess, onError);
}
