import { Theme } from '@emotion/react';

import * as S from '@design/components/button/text-button/TextButton.style';

export type TextButtonProps = {
  children: React.ReactNode;
  fluid?: boolean;
  fontSize: keyof Theme['fontSize'];
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
