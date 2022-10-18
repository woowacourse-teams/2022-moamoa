import { useContext } from 'react';

import AccessTokenController from '@auth/accessTokenController';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = () => {
  const { isLoggedIn, setIsLoggedIn } = useContext(LoginContext);

  const login = (accesssToken: string, expiredTime: number) => {
    AccessTokenController.save(accesssToken, expiredTime);
    setIsLoggedIn(true);
  };

  const logout = () => {
    AccessTokenController.clear();
    setIsLoggedIn(false);
    alert('로그아웃 되었습니다.');
    window.location.reload();
  };

  return { isLoggedIn, login, logout };
};
