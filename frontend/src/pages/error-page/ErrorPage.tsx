import { Link } from 'react-router-dom';

import { Theme, useTheme } from '@emotion/react';

import sthWentWrongImage from '@assets/images/sth-went-wrong.png';

import { PATH } from '@constants';

import { BoxButton } from '@components/button';
import Flex from '@components/flex/Flex';
import Image from '@components/image/Image';

const ErrorPage: React.FC = () => {
  const theme = useTheme();

  return (
    <Flex flexDirection="column" justifyContent="center" alignItems="center" rowGap="4px">
      <Image
        src={sthWentWrongImage}
        alt="잘못된 페이지"
        shape="rectangular"
        custom={{ width: 'auto', height: 'auto' }}
      />
      <p>잘못된 접근입니다.</p>
      <GoToHomeButton theme={theme} />
    </Flex>
  );
};

type GoToHomeButtonProps = {
  theme: Theme;
};
const GoToHomeButton: React.FC<GoToHomeButtonProps> = ({ theme }) => (
  <Link to={PATH.MAIN} replace>
    <BoxButton type="button" custom={{ fontSize: theme.fontSize.lg }} fluid={false}>
      홈으로 이동
    </BoxButton>
  </Link>
);

export default ErrorPage;
