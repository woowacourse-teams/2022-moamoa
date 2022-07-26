import { noop } from '@utils/index';

import { MakeOptional } from '@custom-types/index';

import * as S from '@components/button/Button.style';

export type ButtonProp = {
  className?: string;
  children: string;
  fluid: boolean;
  disabled?: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

type OptionalButtonProp = MakeOptional<ButtonProp, 'fluid' | 'onClick'>;

const Button: React.FC<OptionalButtonProp> = ({
  className,
  children,
  fluid = true,
  disabled = false,
  onClick = noop,
}) => {
  return (
    <S.Button className={className} fluid={fluid} disabled={disabled} onClick={onClick}>
      {children}
    </S.Button>
  );
};

export default Button;
