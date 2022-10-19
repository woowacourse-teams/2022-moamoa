import { Link } from 'react-router-dom';

import sthWentWrongImage from '@assets/images/sth-went-wrong.png';

import { PATH } from '@constants';

import { BoxButton } from '@shared/button';
import Flex from '@shared/flex/Flex';
import Image from '@shared/image/Image';

const ErrorPage: React.FC = () => {
  return (
    <Flex flexDirection="column" justifyContent="center" alignItems="center" rowGap="4px">
      <ErrorImage />
      <ErrorMessage />
      <HomeLink />
    </Flex>
  );
};

export default ErrorPage;

const ErrorImage = () => (
  <Image src={sthWentWrongImage} alt="잘못된 페이지" shape="rectangular" custom={{ width: 'auto', height: 'auto' }} />
);

const ErrorMessage = () => <p>잘못된 접근입니다.</p>;

const HomeLink: React.FC = () => (
  <Link to={PATH.MAIN} replace>
    <BoxButton type="button" custom={{ fontSize: 'lg' }} fluid={false}>
      홈으로 이동
    </BoxButton>
  </Link>
);
