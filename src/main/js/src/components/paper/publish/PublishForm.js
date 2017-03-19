import React, { PropTypes } from 'react';
import { expr } from 'mobx';
import { observer, inject } from 'mobx-react';
import UploadForm from './UploadForm';
import MetadataForm from './MetadataForm';

const PublishForm = ({ publishPaperStore }) => {
  // TODO: use this to switch forms
  const isUploadVisible = expr(() => publishPaperStore.isUploadFormVisible());

  return (
    <div>
      <UploadForm />
      <MetadataForm />
    </div>
  );
};

PublishForm.propTypes = {
  publishPaperStore: PropTypes.object.isRequired
};

export default inject('publishPaperStore')(observer(PublishForm));
