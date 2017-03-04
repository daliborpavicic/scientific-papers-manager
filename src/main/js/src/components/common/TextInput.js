import React, { PropTypes } from 'react';
import { expr } from 'mobx';
import { observer } from 'mobx-react';
import InputErrorMessage from '../common/InputErrorMessage';

const TextInput = ({
  source
}) => {
  const onChange = (e) => {
    source.setValue(e.target.value);
  };

  const shouldDisplayError = expr(() => source.isTouched() && source.hasErrors());
  // TODO: add enable/disable to all inputs
  const isDisabled = expr(() => !source.isEnabled());

  return (
    <div>
      <label className='label'>
        {source.getLabel()}
      </label>
      <p className='control'>
        <input
          className='input'
          type='text'
          readOnly={isDisabled}
          value={source.getValue()}
          onChange={onChange}
          onBlur={onChange}
        />
        {shouldDisplayError && <InputErrorMessage text={source.getErrors()[0]} />}
      </p>
    </div>
  );
};

TextInput.propTypes = {
  source: PropTypes.object
};

export default observer(TextInput);
