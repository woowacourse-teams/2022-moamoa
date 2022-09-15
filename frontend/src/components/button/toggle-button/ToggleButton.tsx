import { ThemeFontSize } from '@styles/theme';

import * as S from '@components/button/toggle-button/ToggleButton.style';

export type ToggleButtonProps = {
  children: React.ReactNode;
  variant?: 'primary' | 'secondary';
  fluid?: boolean;
  checked: boolean;
  fontSize?: ThemeFontSize;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const ToggleButton: React.FC<ToggleButtonProps> = ({
  children,
  checked,
  fluid = false,
  variant = 'primary',
  fontSize = 'md',
  onClick: handleClick,
}) => {
  return (
    <S.ToggleButton
      type="button"
      fluid={fluid}
      checked={checked}
      onClick={handleClick}
      variant={variant}
      fontSize={fontSize}
    >
      {children}
    </S.ToggleButton>
  );
};

export default ToggleButton;
