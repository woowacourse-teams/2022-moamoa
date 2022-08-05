import { useContext } from 'react';

import { ACCESS_TOKEN_KEY } from '@constants';

import { LoginContext } from '@context/login/LoginProvider';

import { useUserInfo } from './useUserInfo';

export const useAuth = () => {
  const { isLoggedIn, setIsLoggedIn } = useContext(LoginContext);
  const { fetchUserInfo } = useUserInfo();

  const login = (accesssToken: string) => {
    window.sessionStorage.setItem(ACCESS_TOKEN_KEY, accesssToken);
    setIsLoggedIn(true);
    fetchUserInfo();
  };

  const logout = () => {
    window.sessionStorage.removeItem(ACCESS_TOKEN_KEY);
    setIsLoggedIn(false);
  };

  return { isLoggedIn, login, logout };
};
