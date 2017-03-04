import { observable, action } from 'mobx';
import formFactory from '../factories/formFactory';

const simpleSearchForm = formFactory({
  query: { label: 'Search query' }
});

const advancedSearchForm = formFactory({
  title: { label: 'Title' }
});

const searchPapersStore = (() => {
  return {
    simpleSearchForm,
    advancedSearchForm
  };
})();

export default searchPapersStore;
