import React, { PropTypes } from 'react';
import { expr } from 'mobx';
import { observer } from 'mobx-react';

const TextArea = ({
  source,
}) => {
  const onChange = (e) => {
    source.setValue(e.target.value);
  };

  const isDisabled = expr(() => !source.isEnabled());

  return (
    <div>
      <label className='label'>
        {source.getLabel()}
      </label>
      <p className='control'>
        <textarea
          className='textarea'
          type='text'
          readOnly={isDisabled}
          value={source.getValue()}
          onChange={onChange}
          onBlur={onChange}
        />
      </p>
    </div>
  );
};

TextArea.propTypes = {
  source: PropTypes.object
};

export default observer(TextArea);
