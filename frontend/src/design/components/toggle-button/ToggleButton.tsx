import * as S from '@design/components/toggle-button/ToggleButton.style';

export type ToggleButtonProps = {
  children: React.ReactNode;
  fluid?: boolean;
  checked: boolean;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const ToggleButton: React.FC<ToggleButtonProps> = ({ children, checked, fluid = false, onClick: handleClick }) => {
  return (
    <S.ToggleButton type="button" fluid={fluid} checked={checked} onClick={handleClick}>
      {children}
    </S.ToggleButton>
  );
};

export default ToggleButton;
