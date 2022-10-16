import { css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';

export type TextButtonProps = {
  children: React.ReactNode;
  fluid?: boolean;
  variant: 'primary' | 'secondary';
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  custom?: CustomCSS<'fontSize'>;
};

const TextButton: React.FC<TextButtonProps> = ({
  children,
  fluid = false,
  variant = 'primary',
  onClick: handleClick,
  custom,
}) => {
  const theme = useTheme();
  return (
    <Self type="button" fluid={fluid} onClick={handleClick} variant={variant} css={resolveCustomCSS(custom, theme)}>
      {children}
    </Self>
  );
};

export default TextButton;

type StyledTextButtonProps = Required<Pick<TextButtonProps, 'fluid' | 'variant'>>;

export const Self = styled.button<StyledTextButtonProps>`
  ${({ theme, fluid, variant }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 8px 4px;

    font-size: ${theme.fontSize.md};
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
