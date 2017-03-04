import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';
import guid from '../../utils/guid';

// hide default browser upload button to be able to customize styles
const fileInputStyles = {
  width: '0.1px',
  height: '0.1px',
  opacity: 0,
  overflow: 'hidden',
  position: 'absolute',
  zIndex: -1
};

const FileInput = ({
  id = guid(),
  source,
}) => {
  const onChange = (e) => {
    const hasFiles = e.target.files && e.target.files.length;

    if (hasFiles) {
      const fileName = e.target.value.split('\\').pop();
      source.setLabel(fileName);
      source.setValue(e.target.files[0]);
    }
  };

  return (
    <div>
      <label className='label' />
      <p className='control' style={{ display: 'inline-flex', alignItems: 'center' }}>
        {/* Only label is visible and used to trigger a click event on file input */}
        <label htmlFor={id} className='button'>{'Choose File'}</label>
        <span style={{ paddingLeft: '10px' }}>{source.getLabel() || 'No file chosen'}</span>
        <input
          id={id}
          style={fileInputStyles}
          type='file'
          onChange={onChange}
          onBlur={onChange}
        />
      </p>
    </div>
  );
};

FileInput.propTypes = {
  id: PropTypes.string,
  source: PropTypes.object,
};

export default observer(FileInput);
