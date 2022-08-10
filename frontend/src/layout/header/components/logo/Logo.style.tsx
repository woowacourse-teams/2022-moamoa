import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const Row = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const ImageContainer = styled.div`
  display: flex;
  align-items: center;

  width: 40px;
  margin-right: 4px;
  img {
    width: 100%;
  }

  ${mqDown('lg')} {
    margin-right: 0;
  }
`;

export const BorderText = styled.h1`
  ${({ theme }) => css`
    color: ${theme.colors.primary.base};
    font-size: 40px;
    font-weight: 800;
    -webkit-text-stroke: 1px ${theme.colors.primary.dark};

    ${mqDown('lg')} {
      display: none;
    }
  `}
`;
