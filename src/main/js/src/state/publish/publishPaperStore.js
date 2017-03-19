import { observable, action } from 'mobx';
import formFactory from '../common/factories/formFactory';
import { uploadPaperFields, publishPaperFields } from './config';
import { uploadPaper, publishPaper } from '../../api/paperApi';

const uploadPaperForm = formFactory(uploadPaperFields);
const paperMetadataForm = formFactory(publishPaperFields);

const publishPaperStore = (() => {
  const state = observable({
    isUploadFormVisible: true,
  });

  const onClickUpload = action('click upload', () => {
    const paperFile = uploadPaperForm.getFieldValue('paperFile');

    uploadPaper(paperFile).then((parsedPaper) => {
      const fieldsToModify = ['titleQuery', 'keywords', 'text'];

      paperMetadataForm.modifyFields((field) => {
        const fieldName = field.getName();
        const valueToSet = parsedPaper[fieldName];

        if (valueToSet) {
          field.setValue(valueToSet);
          field.disable();
        }
      }, fieldsToModify);

      // state.isUploadFormVisible = false;
    });
  });

  const onClickPublish = () => {
    const paperMetadata = paperMetadataForm.getAllValues();

    publishPaper(paperMetadata).then((json) => {
    });
  };

  return {
    uploadPaperForm,
    paperMetadataForm,
    onClickUpload,
    onClickPublish,
    isUploadFormVisible: () => state.isUploadFormVisible
  };
})();

export default publishPaperStore;
