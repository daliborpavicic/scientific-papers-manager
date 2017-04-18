import React, { PropTypes } from 'react';
import { expr } from 'mobx';
import { observer } from 'mobx-react';
import InputErrorMessage from '../common/InputErrorMessage';

const TextInput = ({
  source,
  placeholder = '',
  isExpanded = false,
}) => {
  const onChange = (e) => {
    source.setValue(e.target.value);
  };

  const shouldDisplayError = expr(() => source.isTouched() && source.hasErrors());
  // TODO: add enable/disable to all inputs
  const isDisabled = expr(() => !source.isEnabled());
  const labelText = source.getLabel();

  return (
    <div className={`${isExpanded ? 'is-expanded' : ''} control`}>
      {labelText && <label className='label'>{labelText}</label>}
      <p className='control'>
        <input
          className='input'
          type='text'
          readOnly={isDisabled}
          value={source.getValue()}
          placeholder={placeholder}
          onChange={onChange}
          onBlur={onChange}
        />
        {shouldDisplayError && <InputErrorMessage text={source.getErrors()[0]} />}
      </p>
    </div>
  );
};

TextInput.propTypes = {
  source: PropTypes.object,
  placeholder: PropTypes.string,
  isExpanded: PropTypes.bool
};

export default observer(TextInput);
