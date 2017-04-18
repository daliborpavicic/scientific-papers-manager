import React, { PropTypes } from 'react';

const Form = ({ children }) => {
  return (
    <div className='form'>{children}</div>
  );
};

Form.propTypes = {
  children: PropTypes.node
};

export default Form;
