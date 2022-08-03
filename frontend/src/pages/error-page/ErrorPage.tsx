import sthWentWrongImage from '@assets/images/sth-went-wrong.png';

import * as S from '@error-page/ErrorPage.style';
import useErrorPage from '@error-page/hooks/useErrorPage';

const ErrorPage: React.FC = () => {
  const { handleHomeButtonClick } = useErrorPage();

  return (
    <S.Page>
      <img src={sthWentWrongImage} alt="잘못된 페이지" />
      <p>잘못된 접근입니다.</p>
      <S.HomeButton type="button" onClick={handleHomeButtonClick}>
        홈으로 이동
      </S.HomeButton>
    </S.Page>
  );
};

export default ErrorPage;
