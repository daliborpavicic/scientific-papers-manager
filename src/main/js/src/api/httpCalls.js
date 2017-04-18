import 'whatwg-fetch';
import download from 'downloadjs';
import storageService from '../utils/storageService';

const onSuccess = (response) => {
  if (response.status >= 200 && response.status < 300) {
    return response.json().then(json => Promise.resolve(json));
  }

  return response.json().then(json => Promise.reject(json));
};

const onError = error => console.log(error); // eslint-disable-line no-console

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


export function downloadFile(config) {
  const {
    url,
    withAuthorization = true,
    fileName = 'default_file'
  } = config;

  const requestParams = {
    method: 'GET',
    headers: {}
  };

  if (withAuthorization) {
    Object.assign(requestParams.headers, createAuthorizationHeader().headers);
  }

  const request = new Request(url, requestParams);

  return fetch(request)
    .then((response) => {
      if (response.status >= 200 && response.status < 300) {
        return response.blob();
      }

      return Promise.reject(response.statusText);
    })
    .then(blob => download(blob, fileName), onError);
}
