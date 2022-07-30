import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { TabButtonProps } from './TabButton';

export const TabButton = styled.button<Pick<TabButtonProps, 'isSelected'>>`
  ${({ theme, isSelected }) => css`
    width: 100%;
    padding: 8px 4px;

    font-size: 18px;
    font-weight: 700;
    color: ${isSelected ? theme.colors.primary.base : theme.colors.secondary.dark};
    border: none;
    border-bottom: 2px solid ${isSelected ? theme.colors.primary.base : 'transparent'};
    background-color: transparent;

    &:hover {
      border-bottom: 2px solid ${isSelected ? theme.colors.primary.base : theme.colors.secondary.base};
    }
  `}
`;
