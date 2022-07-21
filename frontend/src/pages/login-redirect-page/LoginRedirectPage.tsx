import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { Navigate, useSearchParams } from 'react-router-dom';

import type { TokenQueryData } from '@custom-types/index';

import { getAccessToken } from '@api/getAccessToken';

import { useAuth } from '@hooks/useAuth';

import Wrapper from '@components/wrapper/Wrapper';

const LoginRedirectPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const codeParam = searchParams.get('code') as string;

  const { login } = useAuth();

  const { data, mutate, isSuccess, isError, error } = useMutation<TokenQueryData, Error, string>(getAccessToken);

  useEffect(() => {
    mutate(codeParam);
  }, []);

  useEffect(() => {
    if (isSuccess) {
      login(data.token);
    }
  }, [isSuccess]);

  if (!codeParam) {
    alert('잘못된 접근입니다.');
    return <Navigate to="/" replace={true} />;
  }

  if (isError) {
    alert(error.message);
    return <Navigate to="/" replace={true} />;
  }

  if (isSuccess) {
    return <Navigate to="/" replace={true} />;
  }

  return (
    <Wrapper>
      <div>로그인 진행 중입니다...</div>
    </Wrapper>
  );
};

export default LoginRedirectPage;
