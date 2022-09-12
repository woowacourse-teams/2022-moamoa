import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

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
