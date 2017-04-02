import authStore from './auth/authStore';
import selectOptionsStore from './common/selectOptionsStore';
import publishPaperStore from './publish/publishPaperStore';
import searchPapersStore from './search/searchPapersStore';
import storageService from '../utils/storageService';

const initStores = () => {
  return {
    authStore: authStore(storageService),
    publishPaperStore: publishPaperStore,
    searchPapersStore: searchPapersStore,
    selectOptionsStore: selectOptionsStore,
  };
};

export default initStores;
