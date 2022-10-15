import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { PATH } from '@constants';

import { usePostLogin } from '@api/auth';

import { useAuth } from '@hooks/useAuth';

const useLoginRedirectPage = () => {
  const [searchParams] = useSearchParams();
  const codeParam = searchParams.get('code');
  const navigate = useNavigate();

  const { login } = useAuth();

  const { mutate } = usePostLogin();

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
        onSuccess: ({ accessToken, expiredTime }) => {
          login(accessToken, expiredTime);
          navigate(-1);
        },
      },
    );
  }, []);
};

export default useLoginRedirectPage;
