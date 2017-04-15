import { observable, action } from 'mobx';
import { notificationTypes } from './constants';

const uiStore = () => {
  const state = observable({
    isNotificationVisible: false,
    notification: {
      message: '',
      type: ''
    }
  });

  return {
    domHandlers: {
      onDeleteNotificationClick: (e) => {
        state.isNotificationVisible = false;
      }
    },
    showNotification: action(({ message, type }) => {
      state.notification.message = message;
      state.notification.type = type;
      state.isNotificationVisible = true;
    }),
    getNotification: () => state.notification,
    getNotificationTypes: () => notificationTypes,
    isNotificationVisible: () => state.isNotificationVisible,
  };
};

export default uiStore;
