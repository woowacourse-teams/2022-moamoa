import type { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { PATH } from '@constants';

import { PostTokenRequestParams, PostTokenResponseData } from '@custom-types';

import { postAccessToken } from '@api';

import { useAuth } from '@hooks/useAuth';

const useLoginRedirectPage = () => {
  const [searchParams] = useSearchParams();
  const codeParam = searchParams.get('code') as string;
  const navigate = useNavigate();

  const { login } = useAuth();

  const { mutate } = useMutation<PostTokenResponseData, AxiosError, PostTokenRequestParams>(postAccessToken);

  useEffect(() => {
    if (!codeParam) {
      alert('잘못된 접근입니다.');
      navigate(PATH.MAIN, { replace: true });
      return;
    }

    mutate(
      { code: codeParam },
      {
        onError: error => {
          alert(error.message ?? '로그인에 실패했습니다.');
          navigate(PATH.MAIN, { replace: true });
        },
        onSuccess: data => {
          login(data.token);
          navigate(PATH.MAIN, { replace: true });
        },
      },
    );
  }, []);
};

export default useLoginRedirectPage;
