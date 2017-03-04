import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';

const SelectInput = ({
  source,
  options = []
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

SelectInput.propTypes = {
  source: PropTypes.object,
  options: PropTypes.arrayOf(PropTypes.shape({
    value: PropTypes.string.isRequired,
    text: PropTypes.string.isRequired
  })).isRequired
};

export default observer(SelectInput);
