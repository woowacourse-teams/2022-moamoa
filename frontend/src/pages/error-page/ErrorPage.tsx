import sthWentWrongImage from '@assets/images/sth-went-wrong.png';

import { BoxButton } from '@components/button';
import Flex from '@components/flex/Flex';
import Image from '@components/image/Image';

import useErrorPage from '@error-page/hooks/useErrorPage';

const ErrorPage: React.FC = () => {
  const { handleHomeButtonClick } = useErrorPage();

  return (
    <Flex flexDirection="column" justifyContent="center" alignItems="center" rowGap="4px">
      <Image src={sthWentWrongImage} alt="잘못된 페이지" shape="rectangular" width="auto" height="auto" />
      <p>잘못된 접근입니다.</p>
      <BoxButton type="button" fontSize="lg" onClick={handleHomeButtonClick} fluid={false}>
        홈으로 이동
      </BoxButton>
    </Flex>
  );
};

export default ErrorPage;
