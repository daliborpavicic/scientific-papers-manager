import React from 'react';
import { observer } from 'mobx-react';
import PublishForm from './PublishForm';
import Protected from '../../security/Protected';
import { authorities } from '../../../state/auth/authStore';

const PublishPaperPage = () => {
  return (
    <div className='column is-half'>
      <PublishForm />
    </div>
  );
};

PublishPaperPage.propTypes = {};

export default Protected(observer(PublishPaperPage), [authorities.ROLE_USER]);
