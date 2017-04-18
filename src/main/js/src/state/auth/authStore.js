import { observable, action } from 'mobx';
import jwtDecode from 'jwt-decode';
import formFactory from '../common/factories/formFactory';
import { isRequired } from '../common/validation/supportedValidators';
import { authenticate } from '../../api/authApi';

const loginForm = formFactory({
  username: { label: 'Username', validators: [isRequired] },
  password: { label: 'Password', validators: [isRequired] }
});

export const authorities = {
  ROLE_ADMIN: 'ROLE_ADMIN',
  ROLE_USER: 'ROLE_USER'
};

const authStore = (storageService, uiStore) => {
  const getUserFromToken = (token) => {
    const decodedToken = jwtDecode(token);

    return {
      name: decodedToken.sub,
      expirationDate: decodedToken.exp,
      authorities: decodedToken.authorities.map(authorityObj => authorityObj.authority),
    };
  };

  const state = observable({
    isAuthenticating: false,
    currentUser: storageService.hasJwtToken()
      ? getUserFromToken(storageService.getJwtToken())
      : null,
    get isAuthenticated() {
      return state.currentUser !== null;
    },
    get canSearchPapers() {
      return state.isAuthenticated
        && state.currentUser.authorities.indexOf(authorities.ROLE_ADMIN) !== -1;
    },
    get canPublishPapers() {
      return state.isAuthenticated
        && state.currentUser.authorities.indexOf(authorities.ROLE_USER) !== -1;
    },
  });

  const publicAPI = {
    onClickLogin: action(() => {
      publicAPI.authenticate({
        username: loginForm.getFieldValue('username'),
        password: loginForm.getFieldValue('password')
      });
    }),
    onClickLogout: action(() => {
      publicAPI.logout();
    }),
    authenticate: action((credentials) => {
      state.isAuthenticating = true;

      authenticate(credentials).then((authenticationResult) => {
        const token = authenticationResult.token;

        state.currentUser = getUserFromToken(token);
        storageService.setJwtToken(token);
        state.isAuthenticating = false;
        uiStore.fetchPaperCategories();
      }, uiStore.getErrorCallback());
    }),
    logout: action(() => {
      storageService.removeJwtToken();
      loginForm.reset();
      state.currentUser = null;
    }),
    loginForm,
    isAuthenticating: () => state.isAuthenticating,
    isAuthenticated: () => state.isAuthenticated,
    canSearchPapers: () => state.canSearchPapers,
    canPublishPapers: () => state.canPublishPapers,
    hasAuthority: (authorityName) => { // TODO: Does this need to be computed ?
      return state.isAuthenticated && state.currentUser.authorities.indexOf(authorityName) !== -1;
    },
  };

  return publicAPI;
};

export default authStore;
