import {
  get,
  postJson,
  postFormData,
  downloadFile,
} from './httpCalls';
import getBaseUrl from './getBaseUrl';

const baseUrl = getBaseUrl();

export const getDownloadLink = fileName => `${baseUrl}/paper/${fileName}`;

export function uploadPaper(paperFile) {
  const formData = new FormData();
  formData.append('paperFile', paperFile);

  return postFormData(`${baseUrl}/paper/upload`, formData);
}

export function getCategories() {
  return get(`${baseUrl}/paper/categories`);
}

export function publishPaper(paperMetadata) {
  return postJson(`${baseUrl}/paper/publish`, paperMetadata);
}

export function searchSimple(query) {
  return get(`${baseUrl}/paper/search?q=${query}`);
}

export function searchAdvanced(searchParams) {
  return postJson(`${baseUrl}/paper/search`, searchParams);
}

export function searchMoreLikeThis(paperId) {
  return get(`${baseUrl}/paper/search/mlt?documentId=${paperId}`);
}

export function downloadPaper(fileName) {
  return downloadFile({
    url: `${baseUrl}/paper/${fileName}`,
    fileName
  });
}

