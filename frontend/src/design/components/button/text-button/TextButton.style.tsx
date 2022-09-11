import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type TextButtonProps } from '@design/components/button/text-button/TextButton';

type StyleTextButtonProps = Pick<TextButtonProps, 'fluid' | 'variant' | 'fontSize'>;

export const TextButton = styled.button<StyleTextButtonProps>`
  ${({ theme, fluid, variant, fontSize }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 8px 4px;

    font-size: ${theme.fontSize[fontSize]};
    font-weight: ${theme.fontWeight.normal};
    color: ${variant === 'secondary' ? theme.colors.black : theme.colors.primary.base};
    background-color: transparent;
    border: none;
    border-bottom: 2px solid transparent;
    background-color: transparent;

    transition: font-weight 0.2s ease;

    &:hover,
    &:active {
      font-weight: ${theme.fontWeight.bold};
    }
  `}
`;
