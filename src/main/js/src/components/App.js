import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';

const App = () => {
  return (
    <div>
      <h1>Scientific papers manager</h1>
    </div>
  );
};

App.propTypes = {
};

export default (observer(App));
