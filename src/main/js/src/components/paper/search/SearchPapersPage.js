import React, { PropTypes } from 'react';
import { expr } from 'mobx';
import { observer, inject } from 'mobx-react';
import SimpleSearchForm from './SimpleSearchForm';
import AdvancedSearchForm from './AdvancedSearchForm';
import PaperSearchResults from './PaperSearchResults';
import Button, { buttonTypes } from '../../common/Button';
import Protected from '../../security/Protected';
import { authorities } from '../../../state/auth/authStore';

const SearchPapersPage = ({ searchPapersStore }) => {
  const isAdvancedSearch = expr(() => searchPapersStore.isAdvancedSearch());

  const switchSearchText = isAdvancedSearch ? 'Simple' : 'Advanced';

  return (
    <div className='column is-three-quarters is-offset-1'>
      {isAdvancedSearch
        ? <AdvancedSearchForm />
        : <SimpleSearchForm />
      }
      <Button
        text={switchSearchText}
        type={buttonTypes.link}
        onClick={searchPapersStore.domHandlers.onClickSwitchSearch}
      />
      <PaperSearchResults />
    </div>
  );
};

SearchPapersPage.propTypes = {
  searchPapersStore: PropTypes.object
};

export default Protected(inject('searchPapersStore')(observer(SearchPapersPage)), [authorities.ROLE_ADMIN]);
