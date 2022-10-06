import { useContext } from 'react';

import AccessTokenController from '@auth/accessTokenController';

import { useUserInfo } from '@hooks/useUserInfo';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = () => {
  const { isLoggedIn, setIsLoggedIn } = useContext(LoginContext);
  const { fetchUserInfo } = useUserInfo();

  const login = (accesssToken: string, expiredTime: number) => {
    AccessTokenController.save(accesssToken, expiredTime);
    setIsLoggedIn(true);
    fetchUserInfo();
  };

  const logout = () => {
    AccessTokenController.clear();
    setIsLoggedIn(false);
    alert('로그아웃 되었습니다.');
  };

  return { isLoggedIn, login, logout };
};
