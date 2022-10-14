import { css } from '@emotion/react';
import styled from '@emotion/styled';

import logoImage from '@assets/images/logo.png';

import { mqDown } from '@styles/responsive';

import Center from '@shared/center/Center';
import Image from '@shared/image/Image';

const Logo: React.FC = () => {
  return (
    <Center>
      <ImageContainer>
        <Image src={logoImage} alt="모아모아 로고" shape="circular" custom={{ width: '38px', height: '38px' }} />
      </ImageContainer>
      <BorderText>MOAMOA</BorderText>
    </Center>
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
