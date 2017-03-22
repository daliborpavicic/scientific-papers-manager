import React from 'react';
import { inject, observer } from 'mobx-react';
import { Redirect } from 'react-router-dom';

// TODO: Add support for permissions
const Protected = (Component) => {
  return inject('authStore')(observer((props) => {
    const authStore = props.authStore;

    const isAuthenticated = authStore.isAuthenticated();
    const isAuthenticating = authStore.isAuthenticating();
    const shouldRedirect = !isAuthenticated && !isAuthenticating;

    if (isAuthenticated) {
      return (
        <Component {...props} />
      );
    }

    return shouldRedirect
      ? <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
      : null;
  }));
};

export default Protected;
