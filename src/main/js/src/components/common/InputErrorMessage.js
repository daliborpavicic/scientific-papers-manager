import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';

const InputErrorMessage = ({ text }) => {
  return (
    <span className='help is-danger'>{text}</span>
  );
};

InputErrorMessage.propTypes = {
  text: PropTypes.string.isRequired
};

export default observer(InputErrorMessage);
