import React, { PropTypes } from 'react';
import { observer, Provider } from 'mobx-react';
import {
  HashRouter as Router,
  Route
} from 'react-router-dom';
import {
  authStore,
  publishPaperStore,
  searchPapersStore
} from '../state';
import LoginPage from './auth/LoginPage';
import PublishPaperPage from './paper/publish/PublishPaperPage';
import SearchPapersPage from './paper/search/SearchPapersPage';

const stores = {
  authStore, publishPaperStore, searchPapersStore
};

window.state = stores;
window.mx = require('mobx');

const App = () => {
  return (
    <Router>
      <Provider {...stores}>
        <div>
          <h1>Scientific papers manager</h1>
          <Route exact path={'/'} render={() => <p>Root - /</p>} />
          <Route exact path={'/login'} component={LoginPage} />
          <Route exact path={'/publish'} component={PublishPaperPage} />
          <Route exact path={'/search'} component={SearchPapersPage} />
        </div>
      </Provider>
    </Router>
  );
};

App.propTypes = {};

export default observer(App);
