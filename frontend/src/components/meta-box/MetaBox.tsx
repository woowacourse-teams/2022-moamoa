import { ReactNode } from 'react';

import * as S from '@components/meta-box/MetaBox.style';

const MetaBoxTitle = ({ children }: { children: ReactNode }) => {
  return <S.Title>{children}</S.Title>;
};

const MetaBoxContent = ({ children }: { children: ReactNode }) => {
  return <S.Content>{children}</S.Content>;
};

const MetaBox = ({ children }: { children: ReactNode }) => {
  return <S.MetaBox>{children}</S.MetaBox>;
};

export default Object.assign(MetaBox, {
  Title: MetaBoxTitle,
  Content: MetaBoxContent,
});
