import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'mobx-react';
import App from './components/App';
import {
  authStore,
  publishPaperStore,
  searchPapersStore
} from './state';

ReactDOM.render(
  <Provider
    authStore={authStore}
    publishPaperStore={publishPaperStore}
    searchPapersStore={searchPapersStore}
  >
    <App />
  </Provider>,
  document.getElementById('app'),
);
