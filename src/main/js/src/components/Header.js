import React, { PropTypes } from 'react';
import { expr } from 'mobx';
import { observer, inject } from 'mobx-react';
import { Link } from 'react-router-dom';
import LogoutLink from './auth/LogoutLink';

const renderNavItem = navItem => <div className='nav-item'>{navItem}</div>;

const Header = ({ authStore }) => {
  const isAuthenticated = expr(() => authStore.isAuthenticated());
  const canSearch = expr(() => authStore.canSearchPapers());
  const canPublish = expr(() => authStore.canPublishPapers());
  const activeLinkClassName = 'is-active';

  return (
    <div className='hero-head'>
      <header className='nav'>
        <div className='container'>
          <div className='nav-left'>
            {renderNavItem(<Link to='/'><em>Scientific papers manager</em></Link>)}
            {renderNavItem(<Link to='/'>Home</Link>)}
            {canSearch && renderNavItem(
              <Link to='/search'>Search</Link>
            )}
            {canPublish && renderNavItem(
              <Link to='/publish'>Publish</Link>
            )}
            {isAuthenticated
              ? renderNavItem(<LogoutLink />)
              : renderNavItem(
                <Link to='/login'>Log in</Link>
              )}
          </div>
        </div>
      </header>
    </div>
  );
};

Header.propTypes = {
  authStore: PropTypes.object,
};

export default inject('authStore')(observer(Header));
