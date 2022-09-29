import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

export const PreviewContainer = styled.div`
  position: relative;

  &:hover {
    & div {
      visibility: visible;
      opacity: 1;
    }
  }
`;

export const PreviewDomain = styled.div`
  ${({ theme }) => css`
    position: absolute;
    bottom: 82px;
    right: 8px;
    width: 110px;
    height: 30px;
    padding: 4px;

    background-color: ${theme.colors.white};
    border-radius: ${theme.radius.md};
    text-align: center;
    visibility: hidden;
    opacity: 0;
    transition: visibility 0.2s ease, opacity 0.2s ease;

    & > span {
      font-weight: ${theme.fontWeight.bold};
      font-size: ${theme.fontSize.sm};
    }
  `}

  ${nLineEllipsis(1)}
`;
