import { useContext } from 'react';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = () => {
  const { setIsLoggedIn } = useContext(LoginContext);

  const login = (accesssToken: string) => {
    localStorage.setItem('accessToken', accesssToken);
    setIsLoggedIn(true);
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    setIsLoggedIn(false);
  };

  return { login, logout };
};
