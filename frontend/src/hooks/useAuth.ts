import type { AxiosError } from 'axios';
import { useContext } from 'react';
import { useQuery, useQueryClient } from 'react-query';
import type { QueryKey } from 'react-query';

import { ACCESS_TOKEN_KEY } from '@constants';

import type { GetTokenResponseData } from '@custom-types';

import getRefreshToken from '@api/getRefreshToken';

import { useUserInfo } from '@hooks/useUserInfo';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = (refetchKey?: QueryKey) => {
  const { isLoggedIn, setIsLoggedIn } = useContext(LoginContext);
  const { fetchUserInfo } = useUserInfo();

  const queryClient = useQueryClient();
  const { refetch: fetchRefreshToken } = useQuery<GetTokenResponseData, AxiosError>(
    ['refresh-token', refetchKey], // refetchKey를 key로 설정하지 않으면 같은 key가 사용된 횟수만큼 onError가 실행됨
    getRefreshToken,
    {
      onError: () => {
        logout();
        alert('로그인이 만료되었습니다.');
      },
      onSuccess: data => {
        login(data.accessToken);
        queryClient.invalidateQueries(refetchKey);
      },
      enabled: false,
      retry: false,
    },
  );

  const login = (accesssToken: string) => {
    window.sessionStorage.setItem(ACCESS_TOKEN_KEY, accesssToken);
    setIsLoggedIn(true);
    fetchUserInfo();
  };

  const logout = () => {
    window.sessionStorage.removeItem(ACCESS_TOKEN_KEY);
    setIsLoggedIn(false);
  };

  return { isLoggedIn, login, logout, fetchRefreshToken };
};
