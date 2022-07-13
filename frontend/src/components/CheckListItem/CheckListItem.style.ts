import { css } from '@emotion/react';
import styled from '@emotion/styled';

interface ItemContainerProps {
  isChecked: boolean;
}

export const ItemContainer = styled.div<ItemContainerProps>`
  ${({ theme, isChecked }) => css`
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 8px;

    width: 100%;
    padding: 4px 8px;

    border-radius: 8px;
    background-color: ${isChecked ? theme.colors.secondary.light : theme.colors.white};

    &:hover,
    &:active {
      background-color: ${theme.colors.secondary.light};
    }

    svg {
      visibility: ${isChecked ? 'visible' : 'hidden'};
      stroke: ${theme.colors.primary.base};
    }
  `}
`;
