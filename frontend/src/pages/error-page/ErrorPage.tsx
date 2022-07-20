import sthWentWrongImage from '@assets/images/sth-went-wrong.png';

import * as S from '@pages/error-page/ErrorPage.style';

const ErrorPage: React.FC = () => {
  return (
    <S.Page>
      <img src={sthWentWrongImage} alt="잘못된 페이지" />
    </S.Page>
  );
};

export default ErrorPage;
