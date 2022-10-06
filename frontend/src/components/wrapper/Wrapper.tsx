import type { CssLength } from '@custom-types';

import * as S from '@components/wrapper/Wrapper.style';

export type WrapperProps = {
  children: React.ReactNode;
  space?: CssLength;
};

const Wrapper: React.FC<WrapperProps> = ({ children, space = '20px' }) => {
  return <S.Wrapper space={space}>{children}</S.Wrapper>;
};

export default Wrapper;
