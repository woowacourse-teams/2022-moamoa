import { type Theme } from '@emotion/react';

import { noop } from '@utils';

import type { CssLength } from '@custom-types';

import * as S from '@design/components/button/box-button/BoxButton.style';

export type BoxButtonProps = {
  variant?: 'primary' | 'secondary';
  children: string;
  type: 'submit' | 'button';
  fluid?: boolean;
  disabled?: boolean;
  padding?: `${CssLength} ${CssLength}` | CssLength;
  fontSize?: keyof Theme['fontSize'];
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const BoxButton: React.FC<BoxButtonProps> = ({
  children,
  type,
  variant = 'primary',
  fluid = true,
  disabled = false,
  padding = '20px 10px',
  fontSize = 'lg',
  onClick: handleClick = noop,
}) => {
  return (
    <S.BoxButton
      type={type}
      fluid={fluid}
      disabled={disabled}
      variant={variant}
      padding={padding}
      fontSize={fontSize}
      onClick={handleClick}
    >
      {children}
    </S.BoxButton>
  );
};

export default BoxButton;
