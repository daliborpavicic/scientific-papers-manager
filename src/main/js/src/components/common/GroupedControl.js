import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';

const GroupedControl = ({ children }) => {
  return (
    <div className={'control is-grouped'}>{children}</div>
  );
};

GroupedControl.propTypes = {};

export default observer(GroupedControl);
