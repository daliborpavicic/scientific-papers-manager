const TOKEN_KEY = 'jwtToken';

const storageService = (() => {
  return {
    hasJwtToken: () => localStorage.getItem(TOKEN_KEY) !== null,
    getJwtToken: () => localStorage.getItem(TOKEN_KEY),
    setJwtToken: (token) => {
      localStorage.setItem(TOKEN_KEY, token);
    },
    removeJwtToken: () => {
      localStorage.removeItem(TOKEN_KEY);
    }
  };
})();

export default storageService;
