import React, { PropTypes } from 'react';
import { observer, inject } from 'mobx-react';

const Notification = ({ uiStore }) => {
  const notification = uiStore.getNotification();

  return (
    <div className={`notification is-${notification.type}`}>
      <button
        className='delete'
        onClick={uiStore.domHandlers.onDeleteNotificationClick}
      />
      {notification.message}
    </div>
  );
};

Notification.propTypes = {
  uiStore: PropTypes.object,
};

export default inject('uiStore')(observer(Notification));
