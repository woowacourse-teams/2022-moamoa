import { noop } from '@utils';

import type { CssLength } from '@custom-types';

import { type ThemeFontSize } from '@styles/theme';

import * as S from '@components/button/box-button/BoxButton.style';

export type BoxButtonProps = {
  variant?: 'primary' | 'secondary';
  children: string | number;
  type: 'submit' | 'button';
  fluid?: boolean;
  disabled?: boolean;
  padding?: `${CssLength} ${CssLength}` | CssLength;
  fontSize?: ThemeFontSize;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const BoxButton: React.FC<BoxButtonProps> = ({
  children,
  type,
  variant = 'primary',
  fluid = true,
  disabled = false,
  padding = '20px 10px',
  fontSize = 'md',
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
