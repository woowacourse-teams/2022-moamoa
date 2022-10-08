import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';

export type UnstyledButtonProps = {
  children?: React.ReactNode;
  className?: string;
  type?: 'button' | 'submit';
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  custom?: CustomCSS<
    | 'display'
    | 'width'
    | 'height'
    | 'padding'
    | 'textAlign'
    | 'alignItems'
    | 'fontSize'
    | 'borderRadius'
    | 'paddingLeft'
    | 'paddingRight'
  >;
};

const UnstyledButton: React.FC<UnstyledButtonProps> = ({ custom, children, type = 'button', onClick }) => {
  return (
    <Self onClick={onClick} type={type} css={resolveCustomCSS(custom)}>
      {children}
    </Self>
  );
};

const Self = styled.button`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.md};

    border: none;
    outline: none;
    background-color: transparent;
  `}
`;

export default UnstyledButton;
