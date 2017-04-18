import { observable, action } from 'mobx';
import { notificationTypes } from './constants';
import { getCategories } from '../../api/paperApi';

const uiStore = () => {
  const state = observable({
    isNotificationVisible: false,
    notification: {
      message: '',
      type: ''
    },
    paperCategoryOptions: []
  });

  const publicAPI = {
    domHandlers: {
      onDeleteNotificationClick: () => {
        state.isNotificationVisible = false;
      }
    },
    showNotification: action(({ message, type }) => {
      state.notification.message = message;
      state.notification.type = type;
      state.isNotificationVisible = true;
    }),
    fetchPaperCategories: action(() => {
      getCategories().then(action((categories) => {
        state.paperCategoryOptions = categories.map((category) => {
          return {
            value: category.name,
            text: category.name
          };
        });
      }));
    }),
    getPaperCategoryOptions: () => state.paperCategoryOptions,
    getNotification: () => state.notification,
    getNotificationTypes: () => notificationTypes,
    getErrorCallback: () => action((err) => {
      publicAPI.showNotification({
        message: `${err.error} ${err.status}`,
        type: notificationTypes.danger
      });
    }),
    isNotificationVisible: () => state.isNotificationVisible,
  };

  return publicAPI;
};

export default uiStore;
