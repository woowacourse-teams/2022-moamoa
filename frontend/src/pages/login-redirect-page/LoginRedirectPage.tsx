import { useQuery } from 'react-query';
import { Navigate, useParams } from 'react-router-dom';

import type { TokenQueryData } from '@custom-types/index';

import { getAccessToken } from '@api/getAccessToken';

import { useAuth } from '@hooks/useAuth';

import Wrapper from '@components/wrapper/Wrapper';

const LoginRedirectPage: React.FC = () => {
  const { code: codeParam } = useParams() as { code: string };
  const { login } = useAuth();

  const { data, isSuccess, isError, error } = useQuery<TokenQueryData, Error>(
    ['redirect', codeParam],
    () => getAccessToken(codeParam),
    {
      enabled: !!codeParam,
      cacheTime: 0,
    },
  );

  if (isSuccess) {
    login(data.token);
    return <Navigate to="/" replace={true} />;
  }

  if (isError) {
    alert(error.message);
    return <Navigate to="/" replace={true} />;
  }

  return (
    <Wrapper>
      <div>로그인 진행 중입니다...</div>
    </Wrapper>
  );
};

export default LoginRedirectPage;
