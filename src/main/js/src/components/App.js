import React, { PropTypes } from 'react';
import { observer, Provider } from 'mobx-react';
import { expr } from 'mobx';
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
import Notification from './common/Notification';

const stores = initStores();

window.state = stores;
window.mx = require('mobx');

const App = () => {
  const notificationVisible = expr(() => stores.uiStore.isNotificationVisible());

  return (
    <Router>
      <Provider {...stores}>
        <section className='hero is-light'>
          <Header />
          {notificationVisible &&
          <div className='column is-4 is-offset-8'>
            <Notification />
          </div>
          }
          <div className='hero-body'>
            <div className='container'>
              <div className='columns'>
                <div className='column'>
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
