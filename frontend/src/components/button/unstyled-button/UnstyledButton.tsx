import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { ThemeFontSize } from '@styles/theme';

export type UnstyledButtonProps = {
  children?: React.ReactNode;
  className?: string;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  fontSize?: ThemeFontSize;
  type?: 'button' | 'submit';
};

const UnstyledButton: React.FC<UnstyledButtonProps> = ({
  className,
  children,
  type = 'button',
  onClick,
  fontSize = 'md',
}) => {
  return (
    <Self className={className} onClick={onClick} fontSize={fontSize} type={type}>
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
