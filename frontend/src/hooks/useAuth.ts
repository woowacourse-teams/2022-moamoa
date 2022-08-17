import { useContext } from 'react';

import { useDeleteLogout } from '@api/auth';

import AccessTokenController from '@auth/accessToken';

import { useUserInfo } from '@hooks/useUserInfo';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = () => {
  const { isLoggedIn, setIsLoggedIn } = useContext(LoginContext);
  const { fetchUserInfo } = useUserInfo();

  const { mutate } = useDeleteLogout();

  const login = (accesssToken: string) => {
    AccessTokenController.setAccessToken(accesssToken);
    setIsLoggedIn(true);
    fetchUserInfo();
  };

  const logout = () => {
    mutate(null, {
      onError: () => {
        alert('로그아웃에 실패했습니다. 새로고침 해주세요.');
      },
      onSuccess: () => {
        AccessTokenController.removeAccessToken();
        setIsLoggedIn(false);
        alert('로그아웃 되었습니다.');
      },
    });
  };

  return { isLoggedIn, login, logout };
};
