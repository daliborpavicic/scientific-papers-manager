import initAuthStore from './auth/authStore';
import initPublishPaperStore from './publish/publishPaperStore';
import initSearchPapersStore from './search/searchPapersStore';
import storageService from '../utils/storageService';
import initUiStore from './ui/uiStore';

const initStores = () => {
  const uiStore = initUiStore();
  const authStore = initAuthStore(storageService, uiStore);
  const publishPaperStore = initPublishPaperStore(uiStore);
  const searchPapersStore = initSearchPapersStore(uiStore);

  return {
    uiStore,
    authStore,
    publishPaperStore,
    searchPapersStore
  };
};

export default initStores;
