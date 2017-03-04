import { postJson, postFormData } from './httpCalls';
import getBaseUrl from './getBaseUrl';

const baseUrl = getBaseUrl();

export function uploadPaper(paperFile) {
  const formData = new FormData();
  formData.append('paperFile', paperFile);

  return postFormData(`${baseUrl}/paper/upload`, formData);
}

export function publishPaper(paperMetadata) {
  return postJson(`${baseUrl}/paper/publish`, paperMetadata);
}
