import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';
import PublishForm from './PublishForm';

const PublishPaperPage = () => {
  return (
    <div>
      <PublishForm />
    </div>
  );
};

PublishPaperPage.propTypes = {};

export default observer(PublishPaperPage);
