import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';

const LogoutLink = ({ authStore }) => {
  return (
    <a onClick={authStore.onClickLogout}>
      Log out
    </a>
  );
};

LogoutLink.propTypes = {
  authStore: PropTypes.object,
};

export default inject('authStore')(observer(LogoutLink));
