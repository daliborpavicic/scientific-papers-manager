import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Form from '../../common/Form';
import FileInput from '../../common/FileInput';
import Button, { buttonTypes } from '../../common/Button';

const UploadPaperForm = ({ publishPaperStore }) => {
  const { uploadPaperForm, onClickUpload } = publishPaperStore;
  const { paperFile } = uploadPaperForm.getFields();

  return (
    <Form>
      <FileInput source={paperFile} />
      <Button
        text='Upload'
        type={buttonTypes.primary}
        onClick={onClickUpload}
      />
    </Form>
  );
};

UploadPaperForm.propTypes = {
  publishPaperStore: PropTypes.object.isRequired
};

export default inject('publishPaperStore')(observer(UploadPaperForm));
