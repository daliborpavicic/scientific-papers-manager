import React, { PropTypes } from 'react';
import { observer, Provider } from 'mobx-react';
import {
  HashRouter as Router,
  Route,
  Link
} from 'react-router-dom';
import {
  authStore,
  selectOptionsStore,
  publishPaperStore,
  searchPapersStore
} from '../state';
import Protected from './security/Protected';
import LoginPage from './auth/LoginPage';
import PublishPaperPage from './paper/publish/PublishPaperPage';
import SearchPapersPage from './paper/search/SearchPapersPage';

const stores = {
  authStore, selectOptionsStore, publishPaperStore, searchPapersStore
};

window.state = stores;
window.mx = require('mobx');

const App = () => {
  return (
    <Router>
      <Provider {...stores}>
        <div>
          <h1>Scientific papers manager</h1>
          <ul>
            <li><Link to='/'>Home</Link></li>
            <li><Link to='/search'>Search</Link></li>
            <li><Link to='/publish'>Publish</Link></li>
            <li><Link to='/protected'>Protected</Link></li>
          </ul>
          <Route exact path={'/'} render={() => <p>Root - /</p>} />
          <Route exact path={'/login'} component={LoginPage} />
          <Route exact path={'/publish'} component={PublishPaperPage} />
          <Route exact path={'/search'} component={SearchPapersPage} />
          <Route exact path={'/protected'} component={Protected(() => <div>Protected route</div>)} />
        </div>
      </Provider>
    </Router>
  );
};

App.propTypes = {};

export default observer(App);
