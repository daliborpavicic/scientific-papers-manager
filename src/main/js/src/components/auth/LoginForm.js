import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Form from '../common/Form';
import GroupedControl from '../common/GroupedControl';
import TextInput from '../common/TextInput';
import PasswordInput from '../common/PasswordInput';
import Button, { buttonTypes } from '../common/Button';

const LoginForm = ({authStore}) => {
  const {loginForm, onClickLogin} = authStore;
  const {username, password} = loginForm.getFields();

  return (
    <div className='column is-4 is-offset-4'>
      <Form>
        <TextInput source={username}/>
        <PasswordInput source={password}/>
        <GroupedControl>
          <Button
            text='Login'
            type={buttonTypes.info}
            isDisabled={!loginForm.isValid()}
            onClick={onClickLogin}
          />
        </GroupedControl>
      </Form>
    </div>
  );
};

LoginForm.propTypes = {
  authStore: PropTypes.object.isRequired
};

export default inject('authStore')(observer(LoginForm));
