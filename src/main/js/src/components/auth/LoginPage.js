import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import { Redirect } from 'react-router-dom';
import LoginForm from './LoginForm';

const LoginPage = ({ authStore }) => {
  const shouldRedirect = authStore.isAuthenticated() && !authStore.isAuthenticating();

  if (authStore.isAuthenticating()) {
    return <p>Logging In...</p>;
  }

  return shouldRedirect ? <Redirect to='/' /> : <LoginForm />;
};

LoginPage.propTypes = {
  authStore: PropTypes.object.isRequired
};

export default inject('authStore')(observer(LoginPage));
