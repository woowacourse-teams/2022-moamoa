import * as S from '@components/button/unstyled-button/UnstyledButton.style';

type UnstyledButtonProps = {
  className?: string;
  children?: React.ReactNode;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const UnstyledButton: React.FC<UnstyledButtonProps> = ({ className, children, onClick }) => {
  return (
    <S.UnstyledButton className={className} onClick={onClick}>
      {children}
    </S.UnstyledButton>
  );
};

export default UnstyledButton;
