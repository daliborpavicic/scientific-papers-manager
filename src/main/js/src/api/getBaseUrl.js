export default function getBaseUrl() {
  return process.env.NODE_ENV === 'production'
    ? 'http://api.production' // TODO: replace this with real API url
    : 'http://localhost:8080/api';
}
