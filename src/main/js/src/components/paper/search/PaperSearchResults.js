import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';
import SingleSearchResult from './SingleSearchResult';

const PaperSearchResults = ({ searchPapersStore }) => {
  return (
    <div>
      {searchPapersStore.getSearchResults()
        .map(searchResult => <SingleSearchResult key={searchResult.id} hit={searchResult} />)
      }
    </div>
  );
};

PaperSearchResults.propTypes = {};

export default inject('searchPapersStore')(observer(PaperSearchResults));
