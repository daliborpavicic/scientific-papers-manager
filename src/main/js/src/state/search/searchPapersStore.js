import { observable, action } from 'mobx';
import formFactory from '../common/factories/formFactory';
import {
  getQueryStringFieldName,
  getBoolOccurrenceFieldName,
  getQueryTypeFieldName,
  simpleSearch,
  advancedSearch
} from './config';

const simpleSearchForm = formFactory(simpleSearch);
const advancedSearchForm = formFactory(advancedSearch);

const searchPapersStore = (() => {
  const getQueryParamFormFields = (paperFieldName) => {
    return {
      queryStringField: advancedSearchForm.getField(getQueryStringFieldName(paperFieldName)),
      queryTypeField: advancedSearchForm.getField(getQueryTypeFieldName(paperFieldName)),
      boolOccurrenceField: advancedSearchForm.getField(getBoolOccurrenceFieldName(paperFieldName))
    };
  };

  const onClickSearch = action(() => {
    const searchParams = advancedSearchForm.getAllValues();
    console.log('search params: ', searchParams);
  });

  return {
    onClickSearch,
    simpleSearchForm,
    advancedSearchForm,
    getQueryParamFormFields,
  };
})();

export default searchPapersStore;
