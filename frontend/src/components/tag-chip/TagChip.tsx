import type { FC } from 'react';

import * as S from '@components/tag-chip/TagChip.style';

export type TagChipProps = {
  className?: string;
  children: string;
};

const TagChip: FC<TagChipProps> = ({ className, children }) => {
  return <S.TagChip className={className}>{children}</S.TagChip>;
};

export default TagChip;
