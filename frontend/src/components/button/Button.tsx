import { noop } from '@utils/index';

import { MakeOptional } from '@custom-types/index';

import * as S from '@components/button/Button.style';

export type ButtonProp = {
  className?: string;
  children: string;
  fluid: boolean;
  onClick: () => void;
};

type OptionalButtonProp = MakeOptional<ButtonProp, 'fluid' | 'onClick'>;

const Button: React.FC<OptionalButtonProp> = ({ className, children, onClick = noop, fluid = true }) => {
  return (
    <S.Button className={className} fluid={fluid} onClick={onClick}>
      {children}
    </S.Button>
  );
};

export default Button;
