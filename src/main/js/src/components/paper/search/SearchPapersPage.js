import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';
import SimpleSearchForm from './SimpleSearchForm';
import AdvancedSearchForm from './AdvancedSearchForm';

const SearchPapersPage = () => {
  return (
    <div>
      <SimpleSearchForm />
      <AdvancedSearchForm />
    </div>
  );
};

SearchPapersPage.propTypes = {};

export default (observer(SearchPapersPage));
