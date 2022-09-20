import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import { useDeleteLogout, usePostLogin } from '@api/auth';

import { useUserInfo } from '@hooks/useUserInfo';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = () => {
  const navigate = useNavigate();
  const { isLoggedIn, setIsLoggedIn } = useContext(LoginContext);
  const { fetchUserInfo } = useUserInfo();

  const { mutate: loginMutate } = usePostLogin();
  const { mutate: logoutMutate } = useDeleteLogout();

  const login = (code: string) => {
    loginMutate(
      { code },
      {
        onError: () => {
          alert('로그인에 실패했습니다.');
        },
        onSuccess: () => {
          setIsLoggedIn(true);
          fetchUserInfo();
        },
        onSettled: () => {
          navigate(PATH.MAIN, { replace: true });
        },
      },
    );
  };

  const logout = () => {
    logoutMutate(null, {
      onError: () => {
        alert('로그아웃에 실패했습니다. 새로고침 해주세요.');
      },
      onSuccess: () => {
        setIsLoggedIn(false);
        alert('로그아웃 되었습니다.');
      },
    });
  };

  return { isLoggedIn, login, logout };
};
