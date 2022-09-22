import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import { TabButtonProps } from '@study-room-page/components/tab-button/TabButton';

const applySelectedStyle = (theme: Theme) => css`
  color: ${theme.colors.primary.base};
  border-bottom: 2px solid ${theme.colors.primary.base};

  &:hover {
    border-bottom: 2px solid ${theme.colors.primary.base};
  }
`;

export const TabButton = styled.button<Pick<TabButtonProps, 'isSelected'>>`
  ${({ theme, isSelected }) => css`
    width: 100%;
    padding: 8px 4px;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
    color: ${theme.colors.secondary.dark};
    border: none;
    border-bottom: 2px solid transparent;
    background-color: transparent;

    &:hover {
      border-bottom: 2px solid ${theme.colors.secondary.base};
    }

    ${isSelected && applySelectedStyle(theme)}
  `}
`;
