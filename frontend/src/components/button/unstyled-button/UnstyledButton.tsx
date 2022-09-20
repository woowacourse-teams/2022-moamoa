import { ThemeFontSize } from '@styles/theme';

import * as S from '@components/button/unstyled-button/UnstyledButton.style';

export type UnstyledButtonProps = {
  children?: React.ReactNode;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  fontSize?: ThemeFontSize;
};

const UnstyledButton: React.FC<UnstyledButtonProps> = ({ children, onClick, fontSize = 'md' }) => {
  return (
    <S.UnstyledButton onClick={onClick} fontSize={fontSize}>
      {children}
    </S.UnstyledButton>
  );
};

export default UnstyledButton;
