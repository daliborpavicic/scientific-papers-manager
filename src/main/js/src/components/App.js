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
        <section className='hero'>
          <div className='hero-head'>
            <header className='nav'>
              <div className='container is-primary'>
                <div className='nav-left'>
                  <div className='nav-item'>
                    <em>Scientific papers manager</em>
                  </div>
                </div>
                <div className='nav-right'>
                  <span className='nav-item'><Link to='/'>Home</Link></span>
                  <span className='nav-item'><Link to='/search'>Search</Link></span>
                  <span className='nav-item'><Link to='/publish'>Publish</Link></span>
                  <span className='nav-item'><Link to='/protected'>Protected</Link></span>
                </div>
              </div>
            </header>
          </div>
          <div className='hero-body'>
            <div className='container'>
              <div className='columns'>
                <div className='column is-half is-offset-one-quarter'>
                  <Route exact path={'/'} render={() => <p>Root - /</p>} />
                  <Route exact path={'/login'} component={LoginPage} />
                  <Route exact path={'/publish'} component={PublishPaperPage} />
                  <Route exact path={'/search'} component={SearchPapersPage} />
                  <Route exact path={'/protected'} component={Protected(() => <div>Protected route</div>)} />
                </div>
              </div>
            </div>
          </div>
        </section>
      </Provider>
    </Router>
  );
};

App.propTypes = {};

export default observer(App);
