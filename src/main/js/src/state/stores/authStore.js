import formFactory from '../factories/formFactory';
import { isRequired } from '../validation/supportedValidators';

const loginForm = formFactory({
  username: { label: 'Username', validators: [isRequired] },
  password: { label: 'Password', validators: [isRequired] }
});

const authStore = (() => {
  const onClickLogin = () => {
    console.log('perform login with: ', loginForm.getAllValues());
  };

  return {
    loginForm,
    onClickLogin,
  };
})();

export default authStore;