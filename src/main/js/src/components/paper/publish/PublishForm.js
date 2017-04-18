import React, { PropTypes } from 'react';
import { expr } from 'mobx';
import { observer, inject } from 'mobx-react';
import UploadForm from './UploadForm';
import MetadataForm from './MetadataForm';

const PublishForm = ({ publishPaperStore }) => {
  const isUploadVisible = expr(() => publishPaperStore.isUploadFormVisible());
  const isPublishing = expr(() => publishPaperStore.isPublishing());

  if (isPublishing) {
    return <div>Publish in progress...</div>;
  }

  return (
    <div>
      {isUploadVisible
        ? <UploadForm />
        : <MetadataForm />
      }
    </div>
  );
};

PublishForm.propTypes = {
  publishPaperStore: PropTypes.object.isRequired
};

export default inject('publishPaperStore')(observer(PublishForm));
