import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';

const SelectInput = ({
  source,
  options = []
}) => {
  const onChange = (e) => {
    source.setValue(e.target.value);
  };

  const labelText = source.getLabel();

  return (
    <div className='control'>
      {labelText && <label className='label'>{labelText}</label>}
      <p className='control'>
        <span className='select'>
          <select value={source.getValue()} onChange={onChange}>
            {options.map(option =>
              <option key={`${option.value}`} value={option.value}>{option.text}</option>)
            }
          </select>
        </span>
      </p>
    </div>
  );
};

const optionShape = {
  value: PropTypes.string.isRequired,
  text: PropTypes.string.isRequired
};

SelectInput.propTypes = {
  source: PropTypes.object,
  options: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.shape(optionShape)),
    PropTypes.object // observable array is object like array
  ]).isRequired
};

export default observer(SelectInput);
