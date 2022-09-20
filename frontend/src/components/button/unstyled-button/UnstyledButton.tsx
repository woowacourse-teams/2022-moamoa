import * as S from '@components/button/unstyled-button/UnstyledButton.style';

type UnstyledButtonProps = {
  children?: React.ReactNode;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const UnstyledButton: React.FC<UnstyledButtonProps> = ({ children, onClick }) => {
  return <S.UnstyledButton onClick={onClick}>{children}</S.UnstyledButton>;
};

export default UnstyledButton;
