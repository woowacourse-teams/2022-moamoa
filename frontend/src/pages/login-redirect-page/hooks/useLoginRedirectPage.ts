import { useEffect } from 'react';
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';

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

    // 외부 사이트에서 리다이렉트하는 것이기 때문에 react-router의 location state를 사용할 수 없어 sessionStorage를 이용
    const prevPath = window.sessionStorage.getItem('prevPath') || PATH.MAIN;

    mutate(
      { code: codeParam },
      {
        onError: () => {
          alert('로그인에 실패했습니다.');
          navigate(prevPath, { replace: true });
        },
        onSuccess: ({ accessToken, expiredTime }) => {
          login(accessToken, expiredTime);
          navigate(prevPath, { replace: true });
        },
      },
    );
  }, []);
};

export default useLoginRedirectPage;
