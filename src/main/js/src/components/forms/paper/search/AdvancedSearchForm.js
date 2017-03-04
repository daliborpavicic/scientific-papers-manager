import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Form from '../../../common/Form';
import TextInput from '../../../common/TextInput';
import Button, { buttonTypes } from '../../../common/Button';

const AdvancedSearchForm = ({ searchPapersStore }) => {
  const { advancedSearchForm } = searchPapersStore;
  const { title } = advancedSearchForm.getFields();

  return (
    <Form>
      <TextInput source={title} />
      <Button
        text='Search'
        type={buttonTypes.warning}
      />
    </Form>
  );
};

AdvancedSearchForm.propTypes = {
  searchPapersStore: PropTypes.object.isRequired
};

export default inject('searchPapersStore')(observer(AdvancedSearchForm));
