import authStore from './authStore';
import token from './tokenMock';

const storageServiceMock = {
  hasJwtToken: jest.fn().mockReturnValue(true),
  getJwtToken: jest.fn().mockReturnValue(token),
  setJwtToken: jest.fn(),
  removeJwtToken: jest.fn()
};

const store = authStore(storageServiceMock);

describe('authStore actions', () => {
  it('should authenticate the user if there is a token in a storage', () => {
    expect(storageServiceMock.getJwtToken).toBeCalled();
    expect(store.isAuthenticated()).toBe(true);
  });
});
