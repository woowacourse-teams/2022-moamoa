import { useNavigate } from 'react-router-dom';

import sthWentWrongImage from '@assets/images/sth-went-wrong.png';

import { PATH } from '@constants';

import * as S from '@error-page/ErrorPage.style';

const ErrorPage: React.FC = () => {
  const navigate = useNavigate();

  const handleHomeButtonClick = () => {
    navigate(PATH.MAIN, { replace: true });
  };

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
