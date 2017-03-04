import { observable, action } from 'mobx';
import formFactory from '../factories/formFactory';
import { uploadPaper, publishPaper } from '../../api/paperApi';

const uploadPaperForm = formFactory({
  paperFile: { label: 'No file chosen' }
});

const paperMetadataForm = formFactory({
  title: { label: 'Title' },
  anAbstract: { label: 'Abstract' },
  keywords: { label: 'Keywords' },
  categoryName: { label: 'Category' },
  text: { label: 'Text' }
});

const publishPaperStore = (() => {
  const state = observable({
    isUploadFormVisible: true,
  });

  const onClickUpload = action('click upload', () => {
    const paperFile = uploadPaperForm.getFieldValue('paperFile');

    uploadPaper(paperFile).then((parsedPaper) => {
      const fieldsToModify = ['title', 'keywords', 'text'];

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
