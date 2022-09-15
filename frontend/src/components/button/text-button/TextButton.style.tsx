import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type TextButtonProps } from '@components/button/text-button/TextButton';

type StyledTextButtonProps = Required<Pick<TextButtonProps, 'fluid' | 'variant' | 'fontSize'>>;

export const TextButton = styled.button<StyledTextButtonProps>`
  ${({ theme, fluid, variant, fontSize }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 8px 4px;

    font-size: ${theme.fontSize[fontSize]};
    font-weight: ${theme.fontWeight.normal};
    color: ${theme.colors.primary.base};
    background-color: transparent;
    border: none;
    border-bottom: 2px solid transparent;
    background-color: transparent;

    ${variant === 'secondary' && `color: ${theme.colors.black};`}

    transition: font-weight 0.2s ease;

    &:hover,
    &:active {
      font-weight: ${theme.fontWeight.bold};
    }
  `}
`;
