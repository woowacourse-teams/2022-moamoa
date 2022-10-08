import PageWrapper from '@components/page-wrapper/PageWrapper';

import useLoginRedirectPage from '@login-redirect-page/hooks/useLoginRedirectPage';

const LoginRedirectPage: React.FC = () => {
  useLoginRedirectPage();

  return (
    <PageWrapper>
      <div>로그인 진행 중입니다...</div>
    </PageWrapper>
  );
};

export default LoginRedirectPage;
