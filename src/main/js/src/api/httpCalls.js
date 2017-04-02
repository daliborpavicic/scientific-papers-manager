import 'whatwg-fetch';
import download from 'downloadjs';
import storageService from '../utils/storageService';

const onSuccess = response => response.json();
const onError = error => console.log(error);

const createAuthorizationHeader = () => {
  return {
    headers: {
      'Authorization': `Bearer ${storageService.getJwtToken()}`
    }
  };
};

export function get(url, withAuthorization = true) {
  const requestParams = {
    method: 'GET',
  };

  if (withAuthorization) {
    Object.assign(requestParams, createAuthorizationHeader());
  }
  const request = new Request(url, requestParams);

  return fetch(request).then(onSuccess, onError);
}

export function postJson(url, jsonData, withAuthorization = true) {
  const requestParams = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(jsonData)
  };

  if (withAuthorization) {
    Object.assign(requestParams.headers, createAuthorizationHeader().headers);
  }
  const request = new Request(url, requestParams);

  return fetch(request).then(onSuccess, onError);
}

export function postFormData(url, formData, withAuthorization = true) {
  const requestParams = {
    method: 'POST',
    body: formData
  };

  if (withAuthorization) {
    Object.assign(requestParams, createAuthorizationHeader());
  }
  const request = new Request(url, requestParams);

  return fetch(request).then(onSuccess, onError);
}

export function downloadFile(url, withAuthorization = true) {
  const requestParams = {
    method: 'GET',
    headers: {}
  };

  if (withAuthorization) {
    Object.assign(requestParams.headers, createAuthorizationHeader().headers);
  }

  const request = new Request(url, requestParams);

  return fetch(request)
    .then((response => response.blob()), onError)
    .then(blob => download(blob));
}
