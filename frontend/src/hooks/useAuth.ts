import { useContext } from 'react';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = () => {
  const { setIsLoggedIn } = useContext(LoginContext);

  const login = (accesssToken: string) => {
    window.sessionStorage.setItem('accessToken', accesssToken);
    setIsLoggedIn(true);
  };

  const logout = () => {
    window.sessionStorage.removeItem('accessToken');
    setIsLoggedIn(false);
  };

  return { login, logout };
};
