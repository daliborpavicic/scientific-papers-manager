import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import GroupedControl from './GroupedControl';
import TextInput from './TextInput';
import SelectInput from './SelectInput';
import { boolOccurOptions, queryTypeOptions } from '../../state/search/config';

const QueryParamFields = ({ searchPapersStore, fieldName, label }) => {
  const queryParamFields = searchPapersStore.getQueryParamFormFields(fieldName);
  return (
    <GroupedControl>
      <SelectInput
        options={boolOccurOptions}
        source={queryParamFields.boolOccurrenceField}
      />
      <SelectInput
        options={queryTypeOptions}
        source={queryParamFields.queryTypeField}
      />
      <TextInput
        source={queryParamFields.queryStringField}
        placeholder={label}
        isExpanded
      />
    </GroupedControl>
  );
};

QueryParamFields.propTypes = {
  searchPapersStore: PropTypes.object.isRequired,
  fieldName: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired
};

export default inject('searchPapersStore')(observer(QueryParamFields));
