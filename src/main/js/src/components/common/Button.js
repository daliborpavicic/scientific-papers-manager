import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';

export const buttonTypes = {
  primary: 'primary',
  info: 'info',
  success: 'success',
  warning: 'warning',
  danger: 'danger',
  link: 'link'
};

const buttonTypeValues = Object.keys(buttonTypes).map(type => buttonTypes[type]);

const Button = ({ text, type = buttonTypes.primary, isDisabled = false, onClick }) => {
  return (
    <p className='control'>
      <button className={`button is-${type}`} onClick={onClick} disabled={isDisabled}>{text}</button>
    </p>
  );
};

Button.propTypes = {
  text: PropTypes.string.isRequired,
  type: PropTypes.oneOf(buttonTypeValues),
  isDisabled: PropTypes.bool,
  onClick: PropTypes.func
};

export default observer(Button);
