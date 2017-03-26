import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Form from '../../common/Form';
import TextInput from '../../common/TextInput';
import Button, { buttonTypes } from '../../common/Button';

const SimpleSearchForm = ({ searchPapersStore }) => {
  const {
    simpleSearchForm,
    domHandlers: {
      onSearchSimple
    }
  } = searchPapersStore;
  const { query } = simpleSearchForm.getFields();

  return (
    <Form>
      <TextInput source={query} />
      <Button
        text='Search'
        type={buttonTypes.success}
        onClick={onSearchSimple}
      />
    </Form>
  );
};

SimpleSearchForm.propTypes = {
  searchPapersStore: PropTypes.object.isRequired
};

export default inject('searchPapersStore')(observer(SimpleSearchForm));
