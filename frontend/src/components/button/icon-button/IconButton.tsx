import { type ReactSVG } from 'react';

import type { CssLength } from '@custom-types';

import { type ThemeFontSize } from '@styles/theme';

import * as S from '@components/button/icon-button/IconButton.style';

export type IconButtonProps = {
  children: React.ReactElement<ReactSVG>;
  ariaLabel: string;
  variant?: 'primary' | 'secondary';
  width: CssLength;
  height: CssLength;
  fontSize?: ThemeFontSize;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const IconButton: React.FC<IconButtonProps> = ({
  children,
  ariaLabel,
  variant = 'primary',
  width,
  height,
  fontSize = 'md',
  onClick: handleClick,
}) => {
  return (
    <S.IconButton
      type="button"
      onClick={handleClick}
      variant={variant}
      aria-label={ariaLabel}
      width={width}
      height={height}
      fontSize={fontSize}
    >
      {children}
    </S.IconButton>
  );
};

export default IconButton;
