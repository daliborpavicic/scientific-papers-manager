import React, { PropTypes } from 'react';
import { observer, Provider } from 'mobx-react';
import {
  HashRouter as Router,
  Route,
} from 'react-router-dom';
import initStores from '../state/initStores';
import Header from './Header';
import HomePage from './home/HomePage';
import LoginPage from './auth/LoginPage';
import PublishPaperPage from './paper/publish/PublishPaperPage';
import SearchPapersPage from './paper/search/SearchPapersPage';

const stores = initStores();

window.state = stores;
window.mx = require('mobx');

const App = () => {
  return (
    <Router>
      <Provider {...stores}>
        <section className='hero is-light'>
          <Header />
          <div className='hero-body'>
            <div className='container'>
              <div className='columns'>
                <div className='column is-half is-offset-one-quarter'>
                  <Route exact path={'/'} component={HomePage} />
                  <Route exact path={'/login'} component={LoginPage} />
                  <Route exact path={'/publish'} component={PublishPaperPage} />
                  <Route exact path={'/search'} component={SearchPapersPage} />
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
