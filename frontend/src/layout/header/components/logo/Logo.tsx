import { css } from '@emotion/react';
import styled from '@emotion/styled';

import logoImage from '@assets/images/logo.png';

import { mqDown } from '@utils';

import Flex from '@components/flex/Flex';
import Image from '@components/image/Image';

const Logo: React.FC = () => {
  return (
    <Flex justifyContent="center" alignItems="center">
      <ImageContainer>
        <Image src={logoImage} alt="모아모아 로고" shape="circular" width="38px" height="38px" />
      </ImageContainer>
      <BorderText>MOAMOA</BorderText>
    </Flex>
  );
};

export const ImageContainer = styled.div`
  margin-right: 4px;

  ${mqDown('lg')} {
    margin-right: 0;
  }
`;

export const BorderText = styled.h1`
  ${({ theme }) => css`
    color: ${theme.colors.primary.base};
    font-size: ${theme.fontSize.xxxl};
    font-weight: ${theme.fontWeight.bolder};
    -webkit-text-stroke: 1px ${theme.colors.primary.dark};

    ${mqDown('lg')} {
      display: none;
    }
  `}
`;

export default Logo;
