import { useContext } from 'react';

import { ACCESS_TOKEN_KEY } from '@constants';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = () => {
  const { setIsLoggedIn } = useContext(LoginContext);

  const login = (accesssToken: string) => {
    window.sessionStorage.setItem(ACCESS_TOKEN_KEY, accesssToken);
    setIsLoggedIn(true);
  };

  const logout = () => {
    window.sessionStorage.removeItem(ACCESS_TOKEN_KEY);
    setIsLoggedIn(false);
  };

  return { login, logout };
};
