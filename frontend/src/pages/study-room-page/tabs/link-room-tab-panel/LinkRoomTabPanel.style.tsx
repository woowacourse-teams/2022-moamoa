import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const LinkAddButtonContainer = styled.div`
  margin-bottom: 16px;
  padding: 4px 0;
`;

export const PlusSvgContainer = styled.span`
  ${({ theme }) => css`
    width: 20px;
    height: 20px;
    margin-right: 4px;

    background-color: ${theme.colors.primary.base};
    border-radius: 50%;

    & > svg {
      fill: ${theme.colors.white};
    }
  `}
`;

export const LinkAddButton = styled.button`
  ${({ theme }) => css`
    display: flex;
    align-items: center;

    margin-left: auto;

    color: ${theme.colors.primary.base};
    background-color: transparent;
    border: none;

    & > span {
      font-size: ${theme.fontSize.lg};
      color: ${theme.colors.primary.base};
    }

    &:hover,
    &:active {
      & > span {
        color: ${theme.colors.primary.light};
      }
    }
  `}
`;

export const LinkList = styled.ul`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;

  ${mqDown('lg')} {
    grid-template-columns: repeat(2, 1fr);
  }

  ${mqDown('sm')} {
    grid-template-columns: repeat(1, 1fr);
  }
`;
