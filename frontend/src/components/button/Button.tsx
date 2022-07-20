import { noop } from '@utils/index';

import { MakeOptional } from '@custom-types/index';

import * as S from '@components/button/Button.style';

export type ButtonProp = {
  className?: string;
  children: string;
  fluid: boolean;
  outline: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

type OptionalButtonProp = MakeOptional<ButtonProp, 'fluid' | 'onClick' | 'outline'>;

const Button: React.FC<OptionalButtonProp> = ({
  className,
  children,
  fluid = true,
  outline = false,
  onClick = noop,
}) => {
  return (
    <S.Button className={className} fluid={fluid} outline={outline} onClick={onClick}>
      {children}
    </S.Button>
  );
};

export default Button;
