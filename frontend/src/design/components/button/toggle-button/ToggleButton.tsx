import * as S from '@design/components/button/toggle-button/ToggleButton.style';

export type ToggleButtonProps = {
  children: React.ReactNode;
  variant?: 'primary' | 'secondary';
  fluid?: boolean;
  checked: boolean;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const ToggleButton: React.FC<ToggleButtonProps> = ({
  children,
  checked,
  fluid = false,
  variant = 'primary',
  onClick: handleClick,
}) => {
  return (
    <S.ToggleButton type="button" fluid={fluid} checked={checked} onClick={handleClick} variant={variant}>
      {children}
    </S.ToggleButton>
  );
};

export default ToggleButton;
