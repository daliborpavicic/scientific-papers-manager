import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import Form from '../../common/Form';
import GroupedControl from '../../common/GroupedControl';
import SelectInput from '../../common/SelectInput';
import TextInput from '../../common/TextInput';
import QueryParamFields from '../../common/QueryParamFields';
import Button, { buttonTypes } from '../../common/Button';
import { paperFields } from '../../../state/search/config';

const AdvancedSearchForm = ({ searchPapersStore, selectOptionsStore }) => {
  const {
    advancedSearchForm,
    domHandlers: {
      onSearchAdvanced
    }
  } = searchPapersStore;
  const categoryOptions = selectOptionsStore.getPaperCategoryOptions();
  const { title, anAbstract, keywords, text, authorName } = paperFields;
  const { category, dateFrom, dateTo } = advancedSearchForm.getFields();

  return (
    <Form>
      <QueryParamFields fieldName={title.name} label={title.label} />
      <QueryParamFields fieldName={anAbstract.name} label={anAbstract.label} />
      <QueryParamFields fieldName={keywords.name} label={keywords.label} />
      <QueryParamFields fieldName={text.name} label={text.label} />
      <QueryParamFields fieldName={authorName.name} label={authorName.label} />
      <SelectInput options={categoryOptions} source={category} />
      <GroupedControl>
        <TextInput source={dateFrom} />
        <TextInput source={dateTo} />
      </GroupedControl>
      <GroupedControl>
        <Button
          text='Search'
          type={buttonTypes.info}
          isDisabled={!advancedSearchForm.isValid()}
          onClick={onSearchAdvanced}
        />
        <Button
          text='Reset'
          type={buttonTypes.link}
          onClick={() => advancedSearchForm.reset()}
        />
      </GroupedControl>
    </Form>
  );
};

AdvancedSearchForm.propTypes = {
  searchPapersStore: PropTypes.object.isRequired,
  selectOptionsStore: PropTypes.object.isRequired
};

export default inject('searchPapersStore', 'selectOptionsStore')(observer(AdvancedSearchForm));
