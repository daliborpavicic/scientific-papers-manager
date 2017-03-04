import React from 'react';
import LoginForm from './forms/LoginForm';
import PublishForm from './forms/paper/publish/PublishForm';
import SimpleSearchForm from './forms/paper/search/SimpleSearchForm';
import AdvancedSearchForm from './forms/paper/search/AdvancedSearchForm';

const App = () => {
  return (
    <div>
      <h1>Scientific papers manager</h1>
      <SimpleSearchForm />
      <AdvancedSearchForm />
      <PublishForm />
      <LoginForm />
    </div>
  );
};

export default App;
