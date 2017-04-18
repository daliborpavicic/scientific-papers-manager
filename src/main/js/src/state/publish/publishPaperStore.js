import { observable, action } from 'mobx';
import formFactory from '../common/factories/formFactory';
import { uploadPaperFields, publishPaperFields } from './config';
import { uploadPaper, publishPaper } from '../../api/paperApi';

const uploadPaperForm = formFactory(uploadPaperFields);
const paperMetadataForm = formFactory(publishPaperFields);

const publishPaperStore = (uiStore) => {
  const state = observable({
    isUploadFormVisible: true,
    parsedPaper: null,
    isPublishing: false,
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

      state.isUploadFormVisible = false;
    });
  });

  return {
    uploadPaperForm,
    paperMetadataForm,
    onClickUpload,
    onClickPublish: action(() => {
      const paperMetadata = paperMetadataForm.getAllValues();
      const publishRequestData = Object.assign(state.parsedPaper, paperMetadata);

      state.isPublishing = true;

      publishPaper(publishRequestData).then((response) => {
        if (response.documentId) {
          uiStore.showNotification({
            type: uiStore.getNotificationTypes().success,
            message: 'Paper has been published successfully!'
          });
          uploadPaperForm.reset();
          paperMetadataForm.reset();
          state.isUploadFormVisible = true;
          state.isPublishing = false;
        }
      }, (err) => {
        state.isPublishing = false;
        uiStore.getErrorCallback().call(null, err);
      });
    }),
    isUploadFormVisible: () => state.isUploadFormVisible,
    isPublishing: () => state.isPublishing
  };
};

export default publishPaperStore;
