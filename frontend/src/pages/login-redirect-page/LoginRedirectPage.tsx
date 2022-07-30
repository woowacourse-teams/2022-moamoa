import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { PATH } from '@constants';

import type { TokenQueryData } from '@custom-types/index';

import { getAccessToken } from '@api/getAccessToken';

import { useAuth } from '@hooks/useAuth';

import Wrapper from '@components/wrapper/Wrapper';

const LoginRedirectPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const codeParam = searchParams.get('code') as string;
  const navigate = useNavigate();

  const { login } = useAuth();

  const { mutate } = useMutation<TokenQueryData, Error, string>(getAccessToken);

  useEffect(() => {
    if (!codeParam) {
      alert('잘못된 접근입니다.');
      navigate(PATH.MAIN, { replace: true });
      return;
    }

    mutate(codeParam, {
      onError: error => {
        alert(error.message);
        navigate(PATH.MAIN, { replace: true });
      },
      onSuccess: data => {
        login(data.token);
        navigate(PATH.MAIN, { replace: true });
      },
    });
  }, []);

  return (
    <Wrapper>
      <div>로그인 진행 중입니다...</div>
    </Wrapper>
  );
};

export default LoginRedirectPage;
