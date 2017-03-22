import React, { PropTypes } from 'react';
import { observer } from 'mobx-react';
import Protected from '../../security/Protected';

const SearchPapersPage = () => {
  console.log('render search...');

  return (
    <div>SearchPapersPage</div>
  );
};

SearchPapersPage.propTypes = {};

export default Protected(observer(SearchPapersPage));
