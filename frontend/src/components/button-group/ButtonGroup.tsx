import { MakeOptional } from '@custom-types';

import * as S from '@components/button-group/ButtonGroup.style';

export type ButtonGroupProps = {
  children: React.ReactNode;
  variation: 'flex-start' | 'flex-end';
};

type OptionalButtonGroupProps = MakeOptional<ButtonGroupProps, 'variation'>;

const ButtonGroup: React.FC<OptionalButtonGroupProps> = ({ children, variation = 'flex-end' }) => {
  return <S.ButtonGroup variation={variation}>{children}</S.ButtonGroup>;
};

export default ButtonGroup;
