import React from 'react';
import { inject, observer } from 'mobx-react';
import { Redirect } from 'react-router-dom';

const Protected = (Component, authorities = []) => {
  return inject('authStore')(observer((props) => {
    const authStore = props.authStore;

    const shouldRenderComponent = authorities.every(authority => authStore.hasAuthority(authority));
    const isAuthenticating = authStore.isAuthenticating();
    const shouldRedirect = !shouldRenderComponent && !isAuthenticating;

    if (shouldRenderComponent) {
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
