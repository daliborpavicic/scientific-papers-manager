import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Form from '../../../common/Form';
import TextInput from '../../../common/TextInput';
import TextArea from '../../../common/TextArea';
import SelectInput from '../../../common/SelectInput';
import Button, { buttonTypes } from '../../../common/Button';

const MetadataForm = ({ publishPaperStore }) => {
  const { paperMetadataForm, onClickPublish } = publishPaperStore;
  const {
    title,
    anAbstract,
    keywords,
    categoryName,
    text
  } = paperMetadataForm.getFields();

  return (
    <Form>
      <TextInput source={title} />
      <TextArea source={anAbstract} />
      <TextInput source={keywords} />
      <SelectInput
        source={categoryName}
        options={[
          { value: 'Cat1', text: 'Category 1' },
          { value: 'Cat2', text: 'Category 2' },
        ]}
      />
      <TextArea source={text} />
      <Button
        text='Publish'
        type={buttonTypes.primary}
        onClick={onClickPublish}
      />
    </Form>
  );
};

MetadataForm.propTypes = {
  publishPaperStore: PropTypes.object.isRequired
};

export default inject('publishPaperStore')(observer(MetadataForm));
