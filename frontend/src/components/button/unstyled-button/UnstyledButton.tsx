import * as S from '@components/button/unstyled-button/UnstyledButton.style';

type UnstyledButtonProps = {
  children?: React.ReactNode;
};

const UnstyledButton: React.FC<UnstyledButtonProps> = ({ children }) => {
  return <S.UnstyledButton>{children}</S.UnstyledButton>;
};

export default UnstyledButton;
