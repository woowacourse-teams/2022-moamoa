import { type ThemeFontSize } from '@styles/theme';

import * as S from '@components/button/text-button/TextButton.style';

export type TextButtonProps = {
  children: React.ReactNode;
  fluid?: boolean;
  fontSize?: ThemeFontSize;
  variant: 'primary' | 'secondary';
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const TextButton: React.FC<TextButtonProps> = ({
  children,
  fluid = false,
  fontSize = 'md',
  variant = 'primary',
  onClick: handleClick,
}) => {
  return (
    <S.TextButton type="button" fluid={fluid} onClick={handleClick} fontSize={fontSize} variant={variant}>
      {children}
    </S.TextButton>
  );
};

export default TextButton;
