import * as S from '@create-study-page/components/meta-box/MetaBox.style';
import cn from 'classnames';
import { ReactNode } from 'react';

const MetaBoxTitle = ({ className, children }: { className?: string; children: string }) => {
  return <h2 className={cn('title', className)}>{children}</h2>;
};

const MetaBoxContent = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <div className={cn('title', className)}>{children}</div>;
};

const MetaBox = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <S.MetaBox className={className}>{children}</S.MetaBox>;
};

export default Object.assign(MetaBox, {
  Title: MetaBoxTitle,
  Content: MetaBoxContent,
});
