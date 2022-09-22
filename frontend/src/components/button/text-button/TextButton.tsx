import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type ThemeFontSize } from '@styles/theme';

export type TextButtonProps = {
  children: React.ReactNode;
  fluid?: boolean;
  fontSize?: ThemeFontSize;
  variant: 'primary' | 'secondary';
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const TextButton: React.FC<TextButtonProps> = ({
  children,
  fluid = false,
  fontSize = 'md',
  variant = 'primary',
  onClick: handleClick,
}) => {
  return (
    <Self type="button" fluid={fluid} onClick={handleClick} fontSize={fontSize} variant={variant}>
      {children}
    </Self>
  );
};

type StyledTextButtonProps = Required<Pick<TextButtonProps, 'fluid' | 'variant' | 'fontSize'>>;

export const Self = styled.button<StyledTextButtonProps>`
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

export default TextButton;
