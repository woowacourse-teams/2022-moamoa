import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { ThemeFontSize } from '@styles/theme';

export type UnstyledButtonProps = {
  children?: React.ReactNode;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  fontSize?: ThemeFontSize;
};

const UnstyledButton: React.FC<UnstyledButtonProps> = ({ children, onClick, fontSize = 'md' }) => {
  return (
    <Self onClick={onClick} fontSize={fontSize}>
      {children}
    </Self>
  );
};

export type StyledUnstyledButtonProps = Required<Pick<UnstyledButtonProps, 'fontSize'>>;

const Self = styled.button<StyledUnstyledButtonProps>`
  ${({ theme, fontSize }) => css`
    font-size: ${theme.fontSize[fontSize]};

    border: none;
    outline: none;
    background-color: transparent;
  `}
`;

export default UnstyledButton;
