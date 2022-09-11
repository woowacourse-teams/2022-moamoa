import Wrapper from '@design/components/wrapper/Wrapper';

import useLoginRedirectPage from '@login-redirect-page/hooks/useLoginRedirectPage';

const LoginRedirectPage: React.FC = () => {
  useLoginRedirectPage();

  return (
    <Wrapper>
      <div>로그인 진행 중입니다...</div>
    </Wrapper>
  );
};

export default LoginRedirectPage;
