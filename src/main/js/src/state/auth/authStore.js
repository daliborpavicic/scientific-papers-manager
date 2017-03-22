import { observable, action } from 'mobx';
import formFactory from '../common/factories/formFactory';
import { isRequired } from '../common/validation/supportedValidators';

const loginForm = formFactory({
  username: { label: 'Username', validators: [isRequired] },
  password: { label: 'Password', validators: [isRequired] }
});

const authStore = (() => {
  const state = observable({
    isAuthenticated: false,
    isAuthenticating: false
  });

  const onClickLogin = () => {
    authStore.authenticate({
      username: loginForm.getFieldValue('username'),
      password: loginForm.getFieldValue('password')
    })
    .then((result) => {
      console.log('Authentication result: ', result);
    });
  };

  return {
    authenticate: action((credentials = {}) => {
      return new Promise((resolve, reject) => {
        state.isAuthenticating = true;

        setTimeout(() => {
          state.isAuthenticated = true;
          state.isAuthenticating = false;
          resolve(state.isAuthenticated);
        }, 1000);
      });
    }),

    loginForm,
    onClickLogin,
    isAuthenticated: () => state.isAuthenticated,
    isAuthenticating: () => state.isAuthenticating
  };
})();

export default authStore;
