import { observable, action } from 'mobx';
import formFactory from '../common/factories/formFactory';
import {
  searchSimple,
  searchAdvanced,
} from '../../api/paperApi';
import {
  getQueryStringFieldName,
  getBoolOccurrenceFieldName,
  getQueryTypeFieldName,
  simpleSearch,
  advancedSearch,
  paperFields
} from './config';

const simpleSearchForm = formFactory(simpleSearch);
const advancedSearchForm = formFactory(advancedSearch);

const searchPapersStore = (() => {
  const state = observable({
    searchResults: []
  });

  const getQueryParamFormFields = (paperFieldName) => {
    return {
      queryStringField: advancedSearchForm.getField(getQueryStringFieldName(paperFieldName)),
      queryTypeField: advancedSearchForm.getField(getQueryTypeFieldName(paperFieldName)),
      boolOccurrenceField: advancedSearchForm.getField(getBoolOccurrenceFieldName(paperFieldName))
    };
  };

  const createAdvancedSearchRequestData = () => {
    const request = {};

    Object.keys(paperFields).forEach((paperFieldKey) => {
      const paperFieldName = paperFields[paperFieldKey].name;

      if (paperFieldKey === 'categoryName') {
        request[paperFieldName] = {
          queryString: advancedSearchForm.getFieldValue('category'),
        };
      } else if (paperFieldKey === 'publishDate') {
        const dateFromValue = advancedSearchForm.getFieldValue('dateFrom');
        const dateToValue = advancedSearchForm.getFieldValue('dateTo');
        let dateQuery = '';

        if (dateFromValue && dateToValue) {
          dateQuery = `${dateFromValue}|${dateToValue}`;
        }

        request[paperFieldName] = {
          queryString: dateQuery,
          queryType: 'RANGE'
        };
      } else {
        request[paperFieldName] = {
          queryString: advancedSearchForm.getFieldValue(getQueryStringFieldName(paperFieldName)),
          queryType: advancedSearchForm.getFieldValue(getQueryTypeFieldName(paperFieldName)),
          boolOccurrence: advancedSearchForm.getFieldValue(getBoolOccurrenceFieldName(paperFieldName))
        };
      }
    });

    return request;
  };

  return {
    domHandlers: {
      onSearchSimple: (e) => {
        const query = simpleSearchForm.getFieldValue('query');
        searchSimple(query).then((results) => {
          console.log('results: ', results);
        });
      },
      onSearchAdvanced: (e) => {
        const advancedSearchParams = createAdvancedSearchRequestData();
        searchAdvanced(advancedSearchParams).then((results) => {
          console.log('results: ', results);
        });
      }
    },
    simpleSearchForm,
    advancedSearchForm,
    getQueryParamFormFields,
    getSearchResults: () => state.searchResults
  };
})();

export default searchPapersStore;
