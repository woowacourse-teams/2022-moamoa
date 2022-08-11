import type { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { PATH } from '@constants';

import { PostLoginRequestParams, PostLoginResponseData } from '@custom-types';

import { postLogin } from '@api';

import AccessTokenController from '@auth/accessToken';

import { useAuth } from '@hooks/useAuth';

const useLoginRedirectPage = () => {
  const [searchParams] = useSearchParams();
  const codeParam = searchParams.get('code') as string;
  const navigate = useNavigate();

  const { login } = useAuth();

  const { mutate } = useMutation<PostLoginResponseData, AxiosError, PostLoginRequestParams>(postLogin);

  useEffect(() => {
    if (!codeParam) {
      alert('잘못된 접근입니다.');
      navigate(PATH.MAIN, { replace: true });
      return;
    }

    mutate(
      { code: codeParam },
      {
        onError: () => {
          alert('로그인에 실패했습니다.');
          navigate(PATH.MAIN, { replace: true });
        },
        onSuccess: data => {
          login(data.accessToken);
          AccessTokenController.setTokenExpiredMsTime(data.expiredTime);

          setTimeout(() => {
            AccessTokenController.fetchAccessTokenWithRefresh();
          }, AccessTokenController.tokenExpiredMsTime);

          navigate(PATH.MAIN, { replace: true });
        },
      },
    );
  }, []);
};

export default useLoginRedirectPage;
