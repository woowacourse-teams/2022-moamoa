import { noop } from '@utils';

import * as S from '@design/components/button/box-button/BoxButton.style';

export type BoxButtonProps = {
  variant?: 'primary' | 'secondary';
  children: string;
  type: 'submit' | 'button';
  fluid?: boolean;
  disabled?: boolean;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const BoxButton: React.FC<BoxButtonProps> = ({
  children,
  type,
  variant = 'primary',
  fluid = true,
  disabled = false,
  onClick: handleClick = noop,
}) => {
  return (
    <S.BoxButton type={type} fluid={fluid} disabled={disabled} variant={variant} onClick={handleClick}>
      {children}
    </S.BoxButton>
  );
};

export default BoxButton;
