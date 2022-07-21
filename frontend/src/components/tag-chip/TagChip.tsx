import type { FC } from 'react';

import * as S from '@components/tag-chip/TagChip.style';

export type TagChipProps = {
  children: string;
};

const TagChip: FC<TagChipProps> = ({ children }) => {
  return <S.TagChip>{children}</S.TagChip>;
};

export default TagChip;
