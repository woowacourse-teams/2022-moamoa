import { ReactNode } from 'react';

import * as S from '@create-study-page/components/meta-box/MetaBox.style';

const MetaBoxTitle = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <S.Title className={className}>{children}</S.Title>;
};

const MetaBoxContent = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <S.Content className={className}>{children}</S.Content>;
};

const MetaBox = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <S.MetaBox className={className}>{children}</S.MetaBox>;
};

export default Object.assign(MetaBox, {
  Title: MetaBoxTitle,
  Content: MetaBoxContent,
});
