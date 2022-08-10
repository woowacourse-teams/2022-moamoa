import type { AxiosError } from 'axios';
import { useContext } from 'react';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import type { QueryKey } from 'react-query';

import type { EmptyObject, GetTokenResponseData } from '@custom-types';

import { deleteRefreshToken, getAccessToken } from '@api';

import AccessTokenController from '@auth/accessToken';

import { useUserInfo } from '@hooks/useUserInfo';

import { LoginContext } from '@context/login/LoginProvider';

export const useAuth = (refetchKey?: QueryKey) => {
  const { isLoggedIn, setIsLoggedIn } = useContext(LoginContext);
  const { fetchUserInfo } = useUserInfo();

  const queryClient = useQueryClient();
  const { refetch: fetchAccessTokenWithRefresh } = useQuery<GetTokenResponseData, AxiosError>(
    ['refresh-token', refetchKey], // refetchKey를 key로 설정하지 않으면 같은 key가 사용된 횟수만큼 onError가 실행됨
    getAccessToken,
    {
      onError: error => {
        // TODO: 만약 refreshToken이 만료되었다는 코드가 오면
        if (error.response?.data.message === '리프레시 토큰 만료') {
          logout();
        }
      },
      onSuccess: data => {
        login(data.accessToken);
        queryClient.invalidateQueries(refetchKey);
      },
      enabled: false,
      retry: false,
    },
  );
  const { mutate } = useMutation<EmptyObject, AxiosError, null>(deleteRefreshToken);

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

  return { isLoggedIn, login, logout, fetchAccessTokenWithRefresh };
};
