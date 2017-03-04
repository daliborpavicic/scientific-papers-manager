import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Form from '../../../common/Form';
import TextInput from '../../../common/TextInput';
import Button, { buttonTypes } from '../../../common/Button';

const SimpleSearchForm = ({ searchPapersStore }) => {
  const { simpleSearchForm } = searchPapersStore;
  const { query } = simpleSearchForm.getFields();

  return (
    <Form>
      <TextInput source={query} />
      <Button
        text='Search'
        type={buttonTypes.success}
      />
    </Form>
  );
};

SimpleSearchForm.propTypes = {
  searchPapersStore: PropTypes.object.isRequired
};

export default inject('searchPapersStore')(observer(SimpleSearchForm));
