import { Link } from 'react-router-dom';

import sthWentWrongImage from '@assets/images/sth-went-wrong.png';

import { PATH } from '@constants';

import { BoxButton } from '@components/button';
import Flex from '@components/flex/Flex';
import Image from '@components/image/Image';

const ErrorPage: React.FC = () => {
  return (
    <Flex flexDirection="column" justifyContent="center" alignItems="center" rowGap="4px">
      <ErrorImage />
      <ErrorMessage />
      <GoToHomeButton />
    </Flex>
  );
};

const ErrorImage = () => (
  <Image src={sthWentWrongImage} alt="잘못된 페이지" shape="rectangular" custom={{ width: 'auto', height: 'auto' }} />
);

const ErrorMessage = () => <p>잘못된 접근입니다.</p>;

const GoToHomeButton: React.FC = () => (
  <Link to={PATH.MAIN} replace>
    <BoxButton type="button" custom={{ fontSize: 'lg' }} fluid={false}>
      홈으로 이동
    </BoxButton>
  </Link>
);

export default ErrorPage;
