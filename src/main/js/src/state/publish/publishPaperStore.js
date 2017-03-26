import { observable, action } from 'mobx';
import formFactory from '../common/factories/formFactory';
import { uploadPaperFields, publishPaperFields } from './config';
import { uploadPaper, publishPaper } from '../../api/paperApi';

const uploadPaperForm = formFactory(uploadPaperFields);
const paperMetadataForm = formFactory(publishPaperFields);

const publishPaperStore = (() => {
  const state = observable({
    isUploadFormVisible: true,
    parsedPaper: null,
  });

  const onClickUpload = action('click upload', () => {
    const paperFile = uploadPaperForm.getFieldValue('paperFile');

    uploadPaper(paperFile).then((parsedPaper) => {
      const fieldsToModify = ['title', 'keywords', 'text'];
      state.parsedPaper = parsedPaper;

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
    const publishRequestData = Object.assign(state.parsedPaper, paperMetadata);

    publishPaper(publishRequestData).then((json) => {
      console.log('publish response', json);
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
