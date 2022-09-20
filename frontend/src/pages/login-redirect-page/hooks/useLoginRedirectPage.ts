import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useAuth } from '@hooks/useAuth';

const useLoginRedirectPage = () => {
  const [searchParams] = useSearchParams();
  const codeParam = searchParams.get('code');
  const navigate = useNavigate();

  const { login } = useAuth();

  useEffect(() => {
    if (!codeParam) {
      alert('잘못된 접근입니다.');
      navigate(PATH.MAIN, { replace: true });
      return;
    }

    login(codeParam);
  }, []);
};

export default useLoginRedirectPage;
