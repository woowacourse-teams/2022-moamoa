import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { PATH } from '@constants';

import type { PostTokenRequestParams, PostTokenResponseData } from '@custom-types';

import { postAccessToken } from '@api';

import { useAuth } from '@hooks/useAuth';

import Wrapper from '@components/wrapper/Wrapper';

const LoginRedirectPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const codeParam = searchParams.get('code') as string;
  const navigate = useNavigate();

  const { login } = useAuth();

  const { mutate } = useMutation<PostTokenResponseData, Error, PostTokenRequestParams>(postAccessToken);

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

  return (
    <Wrapper>
      <div>로그인 진행 중입니다...</div>
    </Wrapper>
  );
};

export default LoginRedirectPage;
