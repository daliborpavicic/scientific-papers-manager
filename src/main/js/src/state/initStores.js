import initAuthStore from './auth/authStore';
import selectOptionsStore from './common/selectOptionsStore';
import initPublishPaperStore from './publish/publishPaperStore';
import searchPapersStore from './search/searchPapersStore';
import storageService from '../utils/storageService';
import initUiStore from './ui/uiStore';

const initStores = () => {
  const authStore = initAuthStore(storageService);
  const uiStore = initUiStore();
  const publishPaperStore = initPublishPaperStore(uiStore);

  return {
    authStore,
    uiStore,
    publishPaperStore,
    selectOptionsStore,
    searchPapersStore
  };
};

export default initStores;
