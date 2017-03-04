import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';

const PasswordInput = ({
  source
}) => {
  const onChange = (e) => {
    source.setValue(e.target.value);
  };

  return (
    <div>
      <label className='label'>
        {source.getLabel()}
      </label>
      <p className='control'>
        <input
          className='input'
          type='password'
          value={source.getValue()}
          onChange={onChange}
          onBlur={onChange}
        />
      </p>
    </div>
  );
};

PasswordInput.propTypes = {
  source: PropTypes.object
};

export default observer(PasswordInput);
