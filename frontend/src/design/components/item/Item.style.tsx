import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

export const Item = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    column-gap: 8px;

    padding: 8px;

    background: ${theme.colors.secondary.light};
    border-radius: ${theme.radius.sm};
  `}
`;

export const ItemHeading = styled.p`
  ${({ theme }) => css`
    font-weight: ${theme.fontWeight.bold};
  `}

  ${nLineEllipsis(1)};
`;

export const ItemContent = styled.p`
  ${({ theme }) => css`
    color: ${theme.colors.secondary.dark};
    font-size: ${theme.fontSize.sm};
  `}

  ${nLineEllipsis(1)};
`;
